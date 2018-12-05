import java.util.ArrayList;
import java.util.List;

/* Lab Slot
 * A custom class to ease the storing of labs in a data structure
 * Takes an abstract data type of form: Day StartTime CourseMax CourseMinn
 * Contains get methods for all private variables
 */
public class LabSlot{

    public final String day;
    public final String start;
    public final int max;
    public final int min;
    public int count;
    public List<Lab> labs = new ArrayList<>();

    public LabSlot(String day, String start, int max, int min) {
        this.day = day;
        this.start = start;
        this.max = max;
        this.min = min;
        this.count = 0;
    }
    public LabSlot(String day, String start, int max, int min, Lab l) {
        this.day = day;
        this.start = start;
        this.max = max;
        this.min = min;
        this.count = 1;
        this.labs.add(l);
    }

    public String getDay() { return day; }
    public String getStart() { return start; }
    public int getMax() { return max; }
    public int getMin() { return min; }
    public int getCount() { return count; }
}