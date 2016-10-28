package ilp.innovations.tcs.com.myascent.beans;

/**
 * Created by 1007546 on 20-07-2016.
 */
public class UserBean {
    private String userName;
    private String employeeId;
    private String location;
    private String email;

    public UserBean() {
    }

    public UserBean(String userName, String employeeId, String location, String email) {
        this.userName = userName;
        this.employeeId = employeeId;
        this.location = location;
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
