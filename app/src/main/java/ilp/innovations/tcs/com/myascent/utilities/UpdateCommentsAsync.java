package ilp.innovations.tcs.com.myascent.utilities;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * Created by maverick on 12/14/2015.
 */
public class UpdateCommentsAsync extends AsyncTask<String, Void, String> {
    private OnService onService;
    private Context mContext;

    public UpdateCommentsAsync(Context mContext, OnService onService) {
        this.mContext = mContext;
        this.onService = onService;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected String doInBackground(String... arg0) {
        String param1 = arg0[0];
        String param2 = arg0[1];
        String param3 = arg0[2];
        String param4 = arg0[3];
        String param5 = arg0[4];
        String param6 = arg0[5];
        String param7 = arg0[6];
        String param8 = arg0[7];
        String param9 = arg0[8];
        String param10 = arg0[9];
        String param11 = arg0[10];
        String param12 = arg0[11];
        String param13 = arg0[12];
        String param14 = arg0[13];
        String param15 = arg0[14];

        try {
            URL url = new URL("http://theinspirer.in/ascent/feedService.php");
            Map<String, Object> params = new LinkedHashMap<>();
            params.put("scheduleId", param1);
            params.put("employeeId", param2);
            params.put("knowledge", param3);
            params.put("articulation", param4);
            params.put("time_management", param5);
            params.put("interaction", param6);
            params.put("quality_of_course", param7);
            params.put("course_objectives", param8);
            params.put("time", param9);
            params.put("toAdd", param10);
            params.put("toRemove", param11);
            params.put("willImplement", param12);
            params.put("videoFeedback", param13);
            params.put("engaging", param14);
            params.put("rating", param15);


            Log.d("Hitting the server", "" + url);
            StringBuilder postData = new StringBuilder();
            for (Map.Entry<String, Object> param : params.entrySet()) {
                if (postData.length() != 0) postData.append('&');
                postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
            }
            String urlParameters = postData.toString();
            URLConnection conn = url.openConnection();

            conn.setDoOutput(true);

            OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());

            writer.write(urlParameters);
            writer.flush();

            BufferedReader reader = new BufferedReader
                    (new InputStreamReader(conn.getInputStream()));
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

        onService.onService(result);
    }

    public interface OnService {
        void onService(String string);
    }

}
