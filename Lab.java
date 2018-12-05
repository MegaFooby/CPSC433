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
        if(arr.length > 4) {
            int count = 0;
            for (String st : arr) {
                if (st.equals("")) {
                    count += 1;
                }
            }
            String[] ndata = new String[arr.length - count];
            int i = 0;
            for (String st : arr){
                if(!st.equals("")){
                    ndata[i] = st;
                    i += 1;
                }
            }
            arr = ndata;
        }
        if(arr.length == 6){
            this.faculty = arr[0].trim();
            this.cnum = Integer.parseInt(arr[1].trim());
            this.lsec = Integer.parseInt(arr[3].trim());
            this.tsec = Integer.parseInt(arr[5].trim());
        }
        else if(arr.length == 4){
            this.faculty = arr[0].trim();
            this.cnum = Integer.parseInt(arr[1].trim());
            if(arr[2].trim().equals("TUT") | arr[2].trim().equals("LAB")){
                this.tsec = Integer.parseInt(arr[3].trim());
                this.lsec = -1;
            }
            else {
                this.lsec = Integer.parseInt(arr[3].trim());
                this.tsec = -1;
            }
        }
        else{
            throw new Error("Lab " + s + " not of correct format. Should be Faculty CourseNum LEC LecNum TUT TutNum OR Faculty CourseNum TUT TutNum");
        }

    }

    public Lab(String faculty, int cnum, int lsec, int tsec){
        this.faculty = faculty;
        this.cnum = cnum;
        this.lsec = lsec;
        this.tsec = tsec;
    }
    public String getFaculty() { return this.faculty; }
    public int getCourseNum() { return this.cnum; }
    public int getLecSec() { return this.lsec; }
    public int getTutSec() { return this.tsec; }
}
