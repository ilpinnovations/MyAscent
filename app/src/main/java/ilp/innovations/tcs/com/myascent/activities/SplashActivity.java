package ilp.innovations.tcs.com.myascent.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ilp.innovations.tcs.com.myascent.R;
import ilp.innovations.tcs.com.myascent.beans.UserBean;
import ilp.innovations.tcs.com.myascent.utilities.ConnectionDetector;
import ilp.innovations.tcs.com.myascent.utilities.ManageUserDetails;


public class SplashActivity extends AppCompatActivity {
    private String url = "http://theinspirer.in/ascent/get_location.php";
    private String TAG = "Splash Screen Tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ManageUserDetails manageUserDetails = new ManageUserDetails();
        UserBean userBean = manageUserDetails.getUserDetails(SplashActivity.this);

        if (userBean.getUserName().equalsIgnoreCase("")) {
            ConnectionDetector connectionDetector = new ConnectionDetector(SplashActivity.this);

            if (connectionDetector.isConnectingToInternet()) {
                JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                        url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());

                        try {
                            JSONArray jsonArray = response.getJSONArray("result");

                            Intent intent = new Intent(SplashActivity.this, RegisterActivity.class);
                            intent.putExtra("locations", jsonArray.toString());
                            startActivity(intent);
                            finish();


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "Error: " + error.getMessage());

                        new AlertDialog.Builder(SplashActivity.this)
                                .setTitle("My Ascent")
                                .setMessage("There is no internet connectivity, please connect to internet and try again later.")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                })
                                .setCancelable(false)
                                .setIcon(R.mipmap.ic_launcher)
                                .show();


                    }
                });


                RequestQueue queue = Volley.newRequestQueue(this);
                queue.add(jsonObjReq);

            } else {
                new AlertDialog.Builder(SplashActivity.this)
                        .setTitle("My Ascent")
                        .setMessage("There is no internet connectivity, please connect to internet and try again later.")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .setCancelable(false)
                        .setIcon(R.mipmap.ic_launcher)
                        .show();
            }

        } else {

            int SPLASH_TIME_OUT = 2000;

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }, SPLASH_TIME_OUT);

        }

    }
}
