/* Unwanted
 * A custom class used to store information about unwanted courses/labs
 * Takes a string as input
 * Contains get methods for all private variables
 */

public class Unwanted {
    private final String faculty;
    private final int cnum;
    private final int lsec;
    private final String day;
    private final String time;

    public Unwanted(String s){
        String[] arr = s.split(",");
        this.day = arr[1].trim();
        this.time = arr[2].trim();
        String[] arr1 = arr[0].trim().split(" ");
        if(arr1.length > 4) {
            int count = 0;
            for (String st : arr1) {
                if (st.equals("")) {
                    count += 1;
                }
            }
            String[] ndata = new String[arr1.length - count];
            int i = 0;
            for (String st : arr1){
                if(!st.equals("")){
                    ndata[i] = st;
                    i += 1;
                }
            }
            arr1 = ndata;
        }
        this.faculty = arr1[0].trim();
        this.cnum = Integer.parseInt(arr1[1].trim());
        this.lsec = Integer.parseInt(arr1[3].trim());
    }

    public String getFaculty() { return faculty; }
    public int getCourseNum() { return cnum; }
    public int getLecSec() { return lsec; }
    public String getDay() { return day; }
    public String getTime() { return time; }
}
