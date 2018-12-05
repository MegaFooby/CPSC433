import java.util.ArrayList;
import java.util.List;

/* Course Slot
* A custom class to ease the storing of courses in a data structure
* Takes an abstract data type of form: Day StartTime CourseMax CourseMinn
* Contains get methods for all private variables
 */
public class CourseSlot {

    public final String day;
    public final String start;
    public final int max;
    public final int min;
    public int count;
    public List<Course> courses = new ArrayList<>();

    public CourseSlot(String day, String start, int max, int min) {
        this.day = day;
        this.start = start;
        this.max = max;
        this.min = min;
        this.count = 0;
    }

    public CourseSlot(String day, String start, int max, int min, Course c) {
        this.day = day;
        this.start = start;
        this.max = max;
        this.min = min;
        this.count = 1;
        this.courses.add(c);
    }


    public String getDay() { return this.day; }
    public String getStart() { return this.start; }
    public int getMax() { return this.max; }
    public int getMin() { return this.min;}
    public int getCount() { return this.count; }
}