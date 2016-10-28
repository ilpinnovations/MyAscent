package ilp.innovations.tcs.com.myascent.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import ilp.innovations.tcs.com.myascent.R;
import ilp.innovations.tcs.com.myascent.activities.FeedbackActivity;
import ilp.innovations.tcs.com.myascent.beans.ScheduleBean;
import ilp.innovations.tcs.com.myascent.beans.UserBean;
import ilp.innovations.tcs.com.myascent.utilities.ConnectionDetector;
import ilp.innovations.tcs.com.myascent.utilities.DividerItemDecorator;
import ilp.innovations.tcs.com.myascent.utilities.ManageUserDetails;

public class DisplayScheduleFragment extends Fragment {
    private View view1;
    private TextView messageTextView;
    private FloatingActionButton fab;
    private String date;
    private String month;
    private String year;
    private String url;
    private RecyclerView recyclerView;
    private TextView tv1;
    View mCoordinatorLayout;
    private String TAG = "Display Schedule Fragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.schedule_fragment, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        messageTextView = (TextView) view.findViewById(R.id.messageTextView);
        view1 = view.findViewById(R.id.view1);
        tv1 = (TextView) view.findViewById(R.id.date_textView);
        mCoordinatorLayout = view.findViewById(R.id.coordinatorLayout);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(new Date());
        String[] dat = formattedDate.split("-");
        year = dat[0];
        month = dat[1];
        date = dat[2];

        getData();
        setHasOptionsMenu(true);

        LinearLayoutManager lm = new LinearLayoutManager(getActivity());
        recyclerView = (RecyclerView) view.findViewById(R.id.scheduleRecyclerView);
        recyclerView.setLayoutManager(lm);
        recyclerView.addItemDecoration(new DividerItemDecorator(getActivity(), LinearLayoutManager.VERTICAL));

        fab = (FloatingActionButton) view.findViewById(R.id.fab);

        final DatePickerDialog.OnDateSetListener mDatesetListener = new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
                arg2 = arg2 + 1;


                year = String.valueOf(arg1);
                month = String.valueOf(arg2);
                date = String.valueOf(arg3);

                getData();
            }
        };
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calender = Calendar.getInstance();
                Dialog mDialog = new DatePickerDialog(getActivity(), mDatesetListener, calender.get(Calendar.YEAR), calender.get(Calendar.MONTH), calender.get(Calendar.DAY_OF_MONTH));
                mDialog.show();
            }
        });
    }

    private void getData() {
        UserBean userBean = new ManageUserDetails().getUserDetails(getActivity());
        url = "http://theinspirer.in/ascent/get_sessions.php?date=" + year + "-" + month + "-" + date + "&regionId=" + userBean.getLocation() + "&emp_id=" + userBean.getEmployeeId();
        Log.d("URL", url);
        tv1.setText(date + "-" + month + "-" + year);


        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setMessage("Loading schedule...");

        JsonArrayRequest req = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        pd.dismiss();
                        Log.d(TAG, response.toString());
                        ArrayList<ScheduleBean> data = new ArrayList<>();
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject object = (JSONObject) response.get(i);
                                ScheduleBean scheduleBean = new ScheduleBean();
                                scheduleBean.setId(Integer.parseInt(object.getString("sched_id")));
                                scheduleBean.setCourseTitle(object.getString("sched_activity"));
                                scheduleBean.setInstructor(object.getString("sched_faculty"));
                                scheduleBean.setTimmings(object.getString("sched_time"));
                                scheduleBean.setIsFeedback(object.getString("is_feedback"));
                                data.add(scheduleBean);
                            }
                            if (data.size() > 0) {
                                view1.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.VISIBLE);
                                recyclerView.setAdapter(new CustomAdapter(data));
                            } else {
                                view1.setVisibility(View.VISIBLE);
                                recyclerView.setVisibility(View.GONE);
                                messageTextView.setText("No schedule available for current date....");
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.dismiss();
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                new AlertDialog.Builder(getActivity())
                        .setTitle("My Ascent")
                        .setMessage("There is no internet connectivity, please connect to internet and try again later.")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setCancelable(false)
                        .setIcon(R.mipmap.ic_launcher)
                        .show();


                view1.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                messageTextView.setText("There is no internet connectivity, please connect to internet and try again later.");
            }
        });
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(req);
        pd.show();


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.schedule_fragment_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            getData();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }

    private class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

        private ArrayList<ScheduleBean> data = new ArrayList<>();


        public CustomAdapter(ArrayList<ScheduleBean> data) {
            this.data = data;
        }

        @Override
        public CustomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.schedule_row1, null);
            ViewHolder viewHolder = new ViewHolder(itemLayoutView);
            return viewHolder;
        }


        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            holder.courseTitle.setText(data.get(position).getCourseTitle());
            holder.instructor.setText(data.get(position).getInstructor());
            holder.timmings.setText(data.get(position).getTimmings());

            setupImage(data.get(position).getCourseTitle(), holder);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (data.get(position).getCourseTitle().equalsIgnoreCase("Break")) {
                        Toast.makeText(getActivity(), "It is your break time.", Toast.LENGTH_LONG).show();
                        return;
                    } else if (data.get(position).getCourseTitle().equalsIgnoreCase("Lunch")) {
                        Toast.makeText(getActivity(), "It is your lunch time.", Toast.LENGTH_LONG).show();
                        return;
                    } else if (data.get(position).getIsFeedback().equalsIgnoreCase("true")) {
                        Toast.makeText(getActivity(), "You have already submitted feedback for this session.", Toast.LENGTH_LONG).show();
                        return;
                    }

                    Intent intent = new Intent(getActivity(), FeedbackActivity.class);
                    intent.putExtra("courseId", data.get(position).getId() + "");
                    intent.putExtra("date", date + "-" + month + "-" + year);
                    startActivity(intent);
                }
            });
        }

        private void setupImage(String courseTitle, ViewHolder holder) {
            if (courseTitle.contains("Break")) {
                holder.titleImage.setImageDrawable(getResources().getDrawable(R.drawable.break_ic));
            } else if (courseTitle.contains("Lunch")) {
                holder.titleImage.setImageDrawable(getResources().getDrawable(R.drawable.lunch_ic));
            } else {
                holder.titleImage.setImageDrawable(getResources().getDrawable(R.drawable.cal_ic));
            }
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView courseTitle, instructor, timmings;
            public ImageView titleImage;

            public ViewHolder(View itemView) {
                super(itemView);
                courseTitle = (TextView) itemView.findViewById(R.id.courseTitle);
                instructor = (TextView) itemView.findViewById(R.id.courseInstructor);
                timmings = (TextView) itemView.findViewById(R.id.courseTimmings);
                titleImage = (ImageView) itemView.findViewById(R.id.imageView1);
            }
        }
    }

}