/* Course Slot
* A custom class to ease the storing of courses in a data structure
* Takes an abstract data type of form: Day StartTime CourseMax CourseMinn
* Contains get methods for all private variables
 */

public class CourseSlot<Day, Start, Max, Min> {

    private final Day day;
    private final Start start;
    private final Max max;
    private final Min min;

    public CourseSlot(Day day, Start start, Max max, Min min) {
        this.day = day;
        this.start = start;
        this.max = max;
        this.min = min;
    }

    public Day getDay() { return day; }
    public Start getStart() { return start; }
    public Max getMax() { return max; }
    public Min getMin() { return min;}
}