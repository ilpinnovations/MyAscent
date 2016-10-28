package ilp.innovations.tcs.com.myascent.fragments;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import ilp.innovations.tcs.com.myascent.R;

/**
 * Created by maverick on 12/6/2015.
 */
public class UserInformationFragment extends Fragment {
    private TextView mTv1, mTv2, mTv3, mTv4;
    private Button mButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.user_information_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences prefs = getActivity().getSharedPreferences("EmployeeDetails", getActivity().MODE_PRIVATE);
        String employeeId = prefs.getString("EmployeeId", "");
        String employeeName = prefs.getString("EmployeeName", "");
        String employeeEmail = prefs.getString("EmployeeEmail", "");
        String employeeLocation = prefs.getString("EmployeeLocation", "");
        mTv1 = (TextView) view.findViewById(R.id.name);
        mTv1.setText("Name: " + employeeName);
        mTv2 = (TextView) view.findViewById(R.id.id);
        mTv2.setText("Employee id: " + employeeId);
        mTv3 = (TextView) view.findViewById(R.id.email);
        mTv3.setText("Email: " + employeeEmail);
        mTv4 = (TextView) view.findViewById(R.id.location);
        mTv4.setText("Location: " + employeeLocation);

        mButton = (Button) view.findViewById(R.id.logout);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sf = getActivity().getSharedPreferences("EmployeeDetails", getActivity().MODE_PRIVATE);
                SharedPreferences.Editor editor = sf.edit();
                editor.clear().commit();
                getActivity().finish();
            }
        });

    }
}
