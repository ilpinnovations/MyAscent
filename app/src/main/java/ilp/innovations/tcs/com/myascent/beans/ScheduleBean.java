package ilp.innovations.tcs.com.myascent.beans;

/**
 * Created by maverick on 12/5/2015.
 */
public class ScheduleBean {

    private String courseTitle;
    private String instructor;
    private String timmings;
    private int id;
    private String isFeedback;

    public ScheduleBean() {
    }

    public ScheduleBean(String courseTitle, String instructor, String timmings, int id, String isFeedback) {
        this.courseTitle = courseTitle;
        this.instructor = instructor;
        this.timmings = timmings;
        this.id = id;
        this.isFeedback = isFeedback;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public String getTimmings() {
        return timmings;
    }

    public void setTimmings(String timmings) {
        this.timmings = timmings;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIsFeedback() {
        return isFeedback;
    }

    public void setIsFeedback(String isFeedback) {
        this.isFeedback = isFeedback;
    }
}
