import org.apache.commons.cli.Option;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Assignments {
    //Has course/Lab and courseslot / labslot
    public Course course;
    public CourseSlot courseSlot;
    public Lab lab;
    public LabSlot labSlot;

    public Assignments(Course c, CourseSlot cs){
        this.course = c;
        this.courseSlot = cs;
        this.lab = null;
        this.labSlot = null;
    }

    public Assignments(Lab l, LabSlot ls){
        this.course = null;
        this.courseSlot = null;
        this.lab = l;
        this.labSlot = ls;
    }

    public Course getCourse() {
        return this.course;
    }

    public CourseSlot getCourseSlot() {
        return this.courseSlot;
    }

    public Lab getLab() {
        return this.lab;
    }

    public LabSlot getLabSlot() {
        return this.labSlot;
    }
}
