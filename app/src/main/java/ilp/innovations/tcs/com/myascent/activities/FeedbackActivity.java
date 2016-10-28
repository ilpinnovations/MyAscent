package ilp.innovations.tcs.com.myascent.activities;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import ilp.innovations.tcs.com.myascent.R;
import ilp.innovations.tcs.com.myascent.beans.UserBean;
import ilp.innovations.tcs.com.myascent.utilities.ConnectionDetector;
import ilp.innovations.tcs.com.myascent.utilities.ManageUserDetails;
import ilp.innovations.tcs.com.myascent.utilities.UpdateCommentsAsync;

public class FeedbackActivity extends AppCompatActivity {

    private TextView mName, mId, mDate;
    private Button mButton;
    private EditText e1, e2, e3, e4;
    private RatingBar rt1, rt2, rt3, rt4, rt5, rt6;
    private RadioButton rd1, rd2, rd3, rd4, rd5, rd6, rd7;
    private CoordinatorLayout mCoordinatorLayout;
    private String date;
    private String courseId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback_activity);


        Bundle args = getIntent().getExtras();
        if (args != null) {
            courseId = args.getString("courseId");
            date = args.getString("date");
        }

        mName = (TextView) findViewById(R.id.employeeName);
        mId = (TextView) findViewById(R.id.emploeeId);
        mDate = (TextView) findViewById(R.id.date);
        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

        UserBean userBean = new ManageUserDetails().getUserDetails(FeedbackActivity.this);
        mName.setText(userBean.getUserName());
        mId.setText(userBean.getEmployeeId());
        mDate.setText(date);

        e1 = (EditText) findViewById(R.id.editText1);
        e2 = (EditText) findViewById(R.id.editText2);
        e3 = (EditText) findViewById(R.id.editText3);
        e4 = (EditText) findViewById(R.id.editText4);

        rt1 = (RatingBar) findViewById(R.id.ratingBar1);
        rt2 = (RatingBar) findViewById(R.id.ratingBar2);
        rt3 = (RatingBar) findViewById(R.id.ratingBar3);
        rt4 = (RatingBar) findViewById(R.id.ratingBar4);
        rt5 = (RatingBar) findViewById(R.id.ratingBar5);
        rt6 = (RatingBar) findViewById(R.id.ratingBar6);

        rd1 = (RadioButton) findViewById(R.id.radioButton1);
        rd2 = (RadioButton) findViewById(R.id.radioButton2);
        rd3 = (RadioButton) findViewById(R.id.radioButton3);
        rd4 = (RadioButton) findViewById(R.id.radioButton4);
        rd5 = (RadioButton) findViewById(R.id.radioButton5);
        rd6 = (RadioButton) findViewById(R.id.radioButton6);
        rd7 = (RadioButton) findViewById(R.id.radioButton7);

        mButton = (Button) findViewById(R.id.button1);


        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toAddCourse = e1.getText().toString().trim();
                String toRemoveCourse = e2.getText().toString().trim();
                String toImplement = e3.getText().toString().trim();
                String exploreVideosFeedback = e4.getText().toString().trim();
                String knowledge = rt1.getRating() + "";
                String articulation = rt2.getRating() + "";
                String timeManagement = rt3.getRating() + "";
                String interaction = rt4.getRating() + "";
                String qualityOfCourse = rt5.getRating() + "";
                String isVideoInformative = "";
                String courseObjectives = "";
                String videoRating = rt6.getRating() + "";

                if (rd1.isChecked()) {
                    courseObjectives += "yes";
                } else if (rd2.isChecked()) {
                    courseObjectives += "no";
                }

                if (rd6.isChecked()) {
                    isVideoInformative = String.valueOf(true);
                } else if (rd7.isChecked()) {
                    isVideoInformative = String.valueOf(false);
                }
                String time = "";

                if (rd3.isChecked()) {
                    time += "insufficient";
                } else if (rd4.isChecked()) {
                    time += "adequate";
                } else if (rd5.isChecked()) {
                    time += "too long";
                }

                if (toImplement.trim().length() == 0 || knowledge.trim().length() == 0 || articulation.trim().length() == 0 || timeManagement.trim().length() == 0 || interaction.trim().length() == 0 || qualityOfCourse.trim().length() == 0 || courseObjectives.trim().length() == 0 || time.trim().length() == 0 || exploreVideosFeedback.length() == 0) {
                    Snackbar snackbar = Snackbar.make(mCoordinatorLayout, "Please enter all fields", Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else {
                    if (new ConnectionDetector(FeedbackActivity.this).isConnectingToInternet()) {
                        final ProgressDialog pd = new ProgressDialog(FeedbackActivity.this);
                        pd.setMessage("Submitting Feedback...");
                        UpdateCommentsAsync updateCommentsAsync = new UpdateCommentsAsync(FeedbackActivity.this, new UpdateCommentsAsync.OnService() {
                            @Override
                            public void onService(String string) {
                                pd.dismiss();
                                if (string != null) {
                                    Log.d("Message from server ", string);
                                    if (string.contains("Successfully registered the feedback")) {
                                        Toast.makeText(FeedbackActivity.this, "Your feedback has been successfully submitted.", Toast.LENGTH_LONG).show();
                                        finish();
                                    } else {
                                        new AlertDialog.Builder(FeedbackActivity.this)
                                                .setTitle("My Ascent")
                                                .setMessage("Your feedback could not be submitted, please try again later.")
                                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.dismiss();
                                                    }
                                                })
                                                .setIcon(android.R.drawable.ic_dialog_alert)
                                                .show();
                                    }
                                } else {
                                    new AlertDialog.Builder(FeedbackActivity.this)
                                            .setTitle("My Ascent")
                                            .setMessage("There is no internet connectivity, please connect to internet and try again later.")
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                }
                                            })
                                            .setIcon(android.R.drawable.ic_dialog_alert)
                                            .show();
                                }

                            }

                        });

                        updateCommentsAsync.execute(String.valueOf(courseId), mId.getText().toString() + "", knowledge, articulation, timeManagement, interaction, qualityOfCourse, courseObjectives, time, toAddCourse, toRemoveCourse, toImplement, exploreVideosFeedback, isVideoInformative, videoRating);
                        pd.show();
                    } else {
                        new AlertDialog.Builder(FeedbackActivity.this)
                                .setTitle("My Ascent")
                                .setMessage("There is no internet connectivity, please connect to internet and try again later.")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    }
                }
            }
        });


    }

}
