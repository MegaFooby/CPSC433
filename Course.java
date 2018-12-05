/* Course
* A custom class used to store information about courses
* Takes a string of form: Faculty Course# LEC LecNum
* Contains get methods for all private variables
 */


public class Course {


    private final String faculty;
    private final int cnum;
    private final int lsec;

    public Course(String s){
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
            this.faculty = arr[0].trim();
            this.cnum = Integer.parseInt(arr[1].trim());
            this.lsec = Integer.parseInt(arr[3].trim());
    }

    public Course(String faculty, int cnum, int lsec){
        this.faculty = faculty;
        this.cnum = cnum;
        this.lsec = lsec;
    }
    public String getFaculty() { return this.faculty; }
    public int getCourseNum() { return this.cnum; }
    public int getLecSec() { return this.lsec; }
}
