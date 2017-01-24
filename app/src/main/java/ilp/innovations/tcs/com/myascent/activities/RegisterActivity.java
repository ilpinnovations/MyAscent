package ilp.innovations.tcs.com.myascent.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ilp.innovations.tcs.com.myascent.R;
import ilp.innovations.tcs.com.myascent.beans.UserBean;
import ilp.innovations.tcs.com.myascent.utilities.ConnectionDetector;
import ilp.innovations.tcs.com.myascent.utilities.ManageUserDetails;
import ilp.innovations.tcs.com.myascent.utilities.SignUpAsync;


public class RegisterActivity extends AppCompatActivity {

    private EditText mEditText1;
    private EditText mEditText2;
    private EditText mEditText3;
    private Spinner mSpinner;
    private Button mButton;
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@tcs.com", Pattern.CASE_INSENSITIVE);
    private ArrayList<String> locations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final Intent intent = getIntent();
        String jsonArray = intent.getStringExtra("locations");

        try {
            locations = new ArrayList<>();
            JSONArray array = new JSONArray(jsonArray);

            if (array != null) {
                for (int i = 0; i < array.length(); i++) {
                    locations.add(array.get(i).toString());
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        mEditText1 = (EditText) findViewById(R.id.input_employeeid);
        mEditText2 = (EditText) findViewById(R.id.input_name);
        mEditText3 = (EditText) findViewById(R.id.input_email);
        mSpinner = (Spinner) findViewById(R.id.spinner1);
        mButton = (Button) findViewById(R.id.submit);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, locations);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(dataAdapter);


        mButton.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           final ProgressDialog pd = new ProgressDialog(RegisterActivity.this);
                                           pd.setCancelable(false);
                                           pd.setMessage("Registering...");
                                           ConnectionDetector connectionDetector = new ConnectionDetector(RegisterActivity.this);

                                           if (ifFormValid()) {
                                               if (connectionDetector.isConnectingToInternet()) {
                                                   String empId = mEditText1.getText().toString().trim();
                                                   String empName = mEditText2.getText().toString().trim();
                                                   String empEmail = mEditText3.getText().toString().trim();
                                                   String location = (String) mSpinner.getSelectedItem();

                                                   final UserBean userBean = new UserBean();
                                                   userBean.setEmail(empEmail);
                                                   userBean.setUserName(empName);
                                                   userBean.setEmployeeId(empId);
                                                   userBean.setLocation(location);

                                                   SignUpAsync signUpAsync = new SignUpAsync(RegisterActivity.this, new SignUpAsync.ServiceResponse() {

                                                       @Override
                                                       public void serviceResponse(String string) {
                                                           if (string != null) {
                                                               pd.dismiss();
                                                               Log.d("Response from Server", string);
                                                               if (string.contains("User has already registered before")) {
                                                                   Toast.makeText(RegisterActivity.this, "You are already registered, you may proceed.", Toast.LENGTH_LONG).show();
                                                                   ManageUserDetails manageUserDetails = new ManageUserDetails();
                                                                   manageUserDetails.saveUserDetails(RegisterActivity.this, userBean);

                                                                   Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                                                   startActivity(intent);
                                                                   finish();

                                                               } else {
                                                                   Toast.makeText(RegisterActivity.this, "You are already registered, you may proceed.", Toast.LENGTH_LONG).show();
                                                                   ManageUserDetails manageUserDetails = new ManageUserDetails();
                                                                   manageUserDetails.saveUserDetails(RegisterActivity.this, userBean);

                                                                   Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                                                   startActivity(intent);
                                                                   finish();
                                                               }
                                                           } else {
                                                               new AlertDialog.Builder(RegisterActivity.this)
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
                                                           }

                                                       }
                                                   });
                                                   signUpAsync.execute(empId, empName, location, empEmail);
                                                   pd.show();

                                               } else {
                                                   pd.dismiss();
                                                   new AlertDialog.Builder(RegisterActivity.this)
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


                                               }
                                           }
                                       }


                                   }

        );


    }

    private boolean ifFormValid() {
        String empId = mEditText1.getText().toString().trim();
        String empName = mEditText2.getText().toString().trim();
        String email = mEditText3.getText().toString().trim();

        if (empId.length() == 0) {
            mEditText1.setError("Please enter a valid employee id.");
            return false;
        } else if (empName.length() == 0) {
            mEditText2.setError("Please enter a valid name.");
            return false;
        } else if (email.length() == 0 || !(validate(email))) {
            mEditText3.setError("Please enter a valid email id.");
            return false;
        }

        return true;
    }


    public static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }
}
