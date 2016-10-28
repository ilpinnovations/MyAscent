package ilp.innovations.tcs.com.myascent.utilities;

import android.content.Context;
import android.content.SharedPreferences;

import ilp.innovations.tcs.com.myascent.beans.UserBean;

/**
 * Created by 1007546 on 20-07-2016.
 */
public class ManageUserDetails {

    public boolean saveUserDetails(Context mContext, UserBean userBean) {
        SharedPreferences prefs = mContext.getSharedPreferences("EmployeeDetails", mContext.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("EmployeeId", userBean.getEmployeeId());
        editor.putString("EmployeeName", userBean.getUserName());
        editor.putString("EmployeeLocation", userBean.getLocation());
        editor.putString("EmployeeEmail", userBean.getEmail());
        editor.commit();
        return true;
    }

    public UserBean getUserDetails(Context mContext) {
        UserBean userBean = new UserBean();
        SharedPreferences pref = mContext.getSharedPreferences("EmployeeDetails", mContext.MODE_PRIVATE);
        String employeeId = pref.getString("EmployeeId", "");
        String employeeName = pref.getString("EmployeeName", "");
        String location = pref.getString("EmployeeLocation", "");
        String email = pref.getString("EmployeeEmail", "");

        userBean.setUserName(employeeName);
        userBean.setEmployeeId(employeeId);
        userBean.setLocation(location);
        userBean.setEmail(email);

        return userBean;
    }

    public boolean clearUserDetails(Context mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences("EmployeeDetails", mContext.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.commit();
        return true;
    }

}
