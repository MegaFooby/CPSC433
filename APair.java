/* APair
* Custom class to represent pairs of (class, labs), (class, class), or (lab, lab)
* Takes a string which is of the form: Faculty Course# LEC LecSec TUT TutSec, Faculty Course# LEC LecSec TUT TutSec
* Contains get methods for each private variable
 */



public class APair {

    private String fac1;
    private String fac2;
    private int cnum1;
    private int lsec1;
    private int cnum2;
    private int lsec2;
    private int tsec1;
    private int tsec2;

    public APair(String s){
        String[] arr = s.split(",");
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
        String[] arr2 = arr[1].trim().split(" ");
        if(arr2.length > 4) {
            int count = 0;
            for (String st : arr2) {
                if (st.equals("")) {
                    count += 1;
                }
            }
            String[] ndata = new String[arr2.length - count];
            int i = 0;
            for (String st : arr2){
                if(!st.equals("")){
                    ndata[i] = st;
                    i += 1;
                }
            }
            arr2 = ndata;
        }
        if(arr1[2].equals("LEC") && arr2[2].equals("LEC")){
            this.fac1 = arr1[0].trim();
            this.fac2 = arr2[0].trim();
            this.cnum1 = Integer.parseInt(arr1[1].trim());
            this.cnum2 = Integer.parseInt(arr2[1].trim());
            this.lsec1 = Integer.parseInt(arr1[3].trim());
            this.lsec2 = Integer.parseInt(arr2[3].trim());
            this.tsec1 = -1;
            this.tsec2 = -1;
        }
        else if(arr1[2].equals("LEC") && arr2[2].equals("TUT")){
            this.fac1 = arr1[0].trim();
            this.fac2 = arr2[0].trim();
            this.cnum1 = Integer.parseInt(arr1[1].trim());
            this.cnum2 = Integer.parseInt(arr2[1].trim());
            this.lsec1 = Integer.parseInt(arr1[3].trim());
            this.lsec2 = -1;
            this.tsec1 = -1;
            this.tsec2 = Integer.parseInt(arr2[3].trim());
        }

        else if(arr1[2].equals("TUT") && arr2[2].equals("TUT")){
            this.fac1 = arr1[0].trim();
            this.fac2 = arr2[0].trim();
            this.cnum1 = Integer.parseInt(arr1[1].trim());
            this.cnum2 = Integer.parseInt(arr2[1].trim());
            this.lsec1 = -1;
            this.lsec2 = -1;
            this.tsec1 = Integer.parseInt(arr1[3].trim());
            this.tsec2 = Integer.parseInt(arr2[3].trim());
        }
    }

    public APair(String fac1, String fac2, int cnum1, int lsec1, int cnum2, int lsec2, int tsec1, int tsec2){
        this.fac1 = fac1;
        this.fac2 = fac2;
        this.cnum1 = cnum1;
        this.cnum2 = cnum2;
        this.lsec1 = lsec1;
        this.lsec2 = lsec2;
        this.tsec1 = tsec1;
        this.tsec2 = tsec2;
    }
    public String getFaculty1(){ return this.fac1; }
    public String getFaculty2() { return this.fac2; }
    public int getCourseNum1() { return this.cnum1; }
    public int getCourseNum2() { return this.cnum2; }
    public int getLecSec1() { return this.lsec1; }
    public int getLecSec2() { return this.lsec2; }
    public int getTutSec1() { return this.tsec1; }
    public int getTutSec2() { return this.tsec2; }
}
