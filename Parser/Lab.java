/* Lab
* A custom class used to store information about labs
* Takes a string as input of form: Faculty Course# Lec Lec# TUT Tut# OR Faculty Course# TUT Tut#
* Contains get methods for all private variables
 */

public class Lab {


    private final String faculty;
    private final int cnum;
    private final int lsec;
    private final int tsec;

    public Lab(String s){
        String[] arr = s.split(" ");
        if(arr.length == 6){
            this.faculty = arr[0];
            this.cnum = Integer.parseInt(arr[1]);
            this.lsec = Integer.parseInt(arr[3]);
            this.tsec = Integer.parseInt(arr[5]);
        }
        else if(arr.length == 4){
            this.faculty = arr[0];
            this.cnum = Integer.parseInt(arr[1]);
            this.lsec = Integer.parseInt(arr[3]);
            this.tsec = -1;
        }
        else{
            throw new Error("Lab " + s + " not of correct format. Should be Faculty CourseNum LEC LecNum TUT TutNum OR Faculty CourseNum TUT TutNum");
        }

    }
    public String getFaculty() { return this.faculty; }
    public int getCourseNum() { return this.cnum; }
    public int getLecSec() { return this.lsec; }
    public int getTutSec() { return this.tsec; }
}
