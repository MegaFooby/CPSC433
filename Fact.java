import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

class Fact{
    public int score = 0;
    List<CourseSlot> courseSlotList= new ArrayList<>();
    List<LabSlot> labsSlotList = new ArrayList<>();
    List<Course> unassCourse = new ArrayList<>();
    List<Lab> unassLab = new ArrayList<>();
    List<Assignments> assignments = new ArrayList<>();
    List<Course> conflict = new ArrayList<>();

    public Fact(List<CourseSlot> courseSlotList,
                List<LabSlot> labSlotList, List<Course> unassCourse, List<Lab> unassLab){
        this.courseSlotList = courseSlotList;
        this.labsSlotList = labSlotList;
        this.unassCourse = unassCourse;
        this.unassLab = unassLab;

    }

}