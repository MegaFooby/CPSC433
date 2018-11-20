public class Course {


    private final String faculty;
    private final int cnum;
    private final int lsec;

    public Course(String s){
        String[] arr = s.split(" ");
        if(arr.length != 4){
            throw new Error("Course " + s + "not of right format. Should be Faculty CourseNum LEC LecNum");
        }
        else {
            this.faculty = arr[0];
            this.cnum = Integer.parseInt(arr[1]);
            this.lsec = Integer.parseInt(arr[3]);
        }
    }
    public String getFaculty() { return this.faculty; }
    public int getCourseNum() { return this.cnum; }
    public int getLecSec() { return this.lsec; }
}
