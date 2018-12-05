/* Partial
 * A custom class used to store information about partial solutions
 * Takes a string as input
 * Contains get methods for all private variables
 */

public class Partial {

    private final String day;
    private final String time;
    private String faculty;
    private int cnum;
    private int lsec;
    private int tsec;


    public Partial(String s){
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
        if(arr1.length == 4){
            if(arr1[2].trim().equals("TUT") | arr1[2].trim().equals("LAB")){
                this.faculty = arr1[0].trim();
                this.cnum = Integer.parseInt(arr1[1].trim());
                this.lsec = -1;
                this.tsec = Integer.parseInt(arr1[3].trim());

            }
            else if(arr1[2].equals("LEC")){
                this.faculty = arr1[0].trim();
                this.cnum = Integer.parseInt(arr1[1].trim());
                this.lsec = Integer.parseInt(arr1[3].trim());
                this.tsec = -1;
            }
        }
        else if(arr1.length == 6){
            if(arr1[2].equals("LEC")) {
                this.faculty = arr1[0].trim();
                this.cnum = Integer.parseInt(arr1[1].trim());
                this.lsec = Integer.parseInt(arr1[3].trim());
                this.tsec = Integer.parseInt(arr1[5].trim());
            }
            else if(arr1[4].equals("LEC")){
                this.faculty = arr1[0].trim();
                this.cnum = Integer.parseInt(arr1[1].trim());
                this.tsec = Integer.parseInt(arr1[3].trim());
                this.lsec = Integer.parseInt(arr1[5].trim());
            }
        }
    }

    public String getDay(){ return this.day; }
    public String getTime() { return this.time; }
    public String getFaculty() { return this.faculty; }
    public int getCourseNum() { return this.cnum; }
    public int getLecSec() { return this.lsec; }
    public int getTutSec() { return this.tsec; }

}
