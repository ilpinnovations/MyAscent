package ilp.innovations.tcs.com.myascent.utilities;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by maverick on 12/14/2015.
 */
public class SignUpAsync extends AsyncTask<String, Void, String> {
    private ServiceResponse mServiceResponse;
    private Context mContext;

    private static int responseCode = 0;

    public SignUpAsync(Context mContext, ServiceResponse mServiceResponse) {
        this.mContext = mContext;
        this.mServiceResponse = mServiceResponse;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... arg0) {
        try {
            String link = "http://theinspirer.in/ascent?action=register&empId=" + arg0[0] + "&empName=" + arg0[1].replace(" ", "%20") + "&regionId=" + arg0[2] + "&emailId=" + arg0[3];
            Log.d("myTag", link);
            URL url = new URL(link.trim().replace(" ", "xyzzyspoonshift1"));
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.flush();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read Server Response
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                break;
            }
            return sb.toString();
        } catch (Exception e) {
            return new String(e.getMessage() + "Exception: null");
        }
    }

    @Override
    protected void onPostExecute(String result) {
        mServiceResponse.serviceResponse(result);
    }


    public interface ServiceResponse {
        void serviceResponse(String string);
    }

}
