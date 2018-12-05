/* Not Compatible
 * A custom class used to store information about courses and labs which should not be together
 * Takes a string as input
 * Contains get methods for all private variables
 */

public class NotComp {


    public String fac1;
    public String fac2;
    public int cnum1;
    public int cnum2;
    public int lsec1;
    public int lsec2;
    public int tsec1;
    public int tsec2;


    public NotComp(String s){
        String[] arr1 = s.split(",");
        String[] arr11 = arr1[0].split(" ");
        if(arr11.length > 4) {
            int count = 0;
            for (String st : arr11) {
                if (st.equals("")) {
                    count += 1;
                }
            }
            String[] ndata = new String[arr11.length - count];
            int i = 0;
            for (String st : arr11){
                if(!st.equals("")){
                    ndata[i] = st;
                    i += 1;
                }
            }
            arr11 = ndata;
        }
        String[] arr12 = arr1[1].split(" ");
        if(arr12.length > 4) {
            int count = 0;
            for (String st : arr12) {
                if (st.equals("")) {
                    count += 1;
                }
            }
            String[] ndata = new String[arr12.length - count];
            int i = 0;
            for (String st : arr12){
                if(!st.equals("")){
                    ndata[i] = st;
                    i += 1;
                }
            }
            arr12 = ndata;
        }
        if(arr11.length != arr12.length){
            throw new Error("Each line must have same number of arguments. Error occurred from: " + s);
        }
        /*Three options either Lec, Tut, or both */
        else{
            if(arr11.length == 4){
                String f1 = arr11[0].trim();
                String f2 = arr12[0].trim();
                int c1 = Integer.parseInt(arr11[1].trim());
                int c2 = Integer.parseInt(arr12[1].trim());
                int l1 = Integer.parseInt(arr11[3].trim());
                int l2 = Integer.parseInt(arr12[3].trim());
                int t1 = Integer.parseInt(arr11[3].trim());
                int t2 = Integer.parseInt(arr12[3].trim());

                if((arr11[2].trim().equals("TUT") | arr11[2].trim().equals("LAB")) &
                        (arr12[2].trim().equals("TUT") | arr12[2].trim().equals("LAB"))) {
                    this.fac1 = f1;
                    this.fac2 = f2;
                    this.cnum1 = c1;
                    this.cnum2 = c2;
                    this.lsec1 = -1;
                    this.lsec2 = -1;
                    this.tsec1 = t1;
                    this.tsec2 = t2;
                }
                else if(arr11[2].trim().equals("LEC") & arr12[2].trim().equals("LEC")){
                    this.fac1 = f1;
                    this.fac2 = f2;
                    this.cnum1 = c1;
                    this.cnum2 = c2;
                    this.lsec1 = l1;
                    this.lsec2 = l2;
                    this.tsec1 = -1;
                    this.tsec2 = -1;
                }
                else if(arr11[2].trim().equals("LEC") & (arr12[2].trim().equals("TUT") | arr12[2].trim().equals("LAB"))){
                    this.fac1 = f1;
                    this.fac2 = f2;
                    this.cnum1 = c1;
                    this.cnum2 = c2;
                    this.lsec1 = l1;
                    this.lsec2 = -1;
                    this.tsec1 = -1;
                    this.tsec2 = t1;
                }
                else if((arr11[2].trim().equals("TUT") | arr11[2].trim().equals("LAB")) & arr12[2].trim().equals("LEC")){
                    this.fac1 = f1;
                    this.fac2 = f2;
                    this.cnum1 = c1;
                    this.cnum2 = c2;
                    this.lsec1 = -1;
                    this.lsec2 = l2;
                    this.tsec1 = t1;
                    this.tsec2 = -1;
                }

            }
            else if(arr11.length == 6){
                String f1 = arr11[0].trim();
                String f2 = arr12[0].trim();
                int c1 = Integer.parseInt(arr11[1].trim());
                int c2 = Integer.parseInt(arr12[1].trim());
                int l1 = Integer.parseInt(arr11[3].trim());
                int l2 = Integer.parseInt(arr12[3].trim());
                int t1 = Integer.parseInt(arr11[5].trim());
                int t2 = Integer.parseInt(arr12[5].trim());

                if(arr11[2].trim().equals("LEC") & (arr11[4].trim().equals("TUT") | arr11[4].trim().equals("LAB"))){
                    this.fac1 = f1;
                    this.fac2 = f2;
                    this.cnum1 = c1;
                    this.cnum2 = c2;
                    this.lsec1 = l1;
                    this.lsec2 = l2;
                    this.tsec1 = t1;
                    this.tsec2 = t2;
                }
                else if((arr11[2].trim().equals("TUT") | arr11[2].trim().equals("LAB")) & arr11[4].trim().equals("LEC")){
                    this.fac1 = f1;
                    this.fac2 = f2;
                    this.cnum1 = c1;
                    this.cnum2 = c2;
                    this.lsec1 = t1;
                    this.lsec2 = t2;
                    this.tsec1 = l1;
                    this.tsec2 = l2;
                }

            }
            else{
                throw new Error("Something went wrong");
            }
        }
    }
    public NotComp(String fac1, String fac2, int cnum1, int cnum2, int lsec1, int lsec2, int tsec1, int tsec2){
        this.fac1 = fac1;
        this.fac2 = fac2;
        this.cnum1 = cnum1;
        this.cnum2 = cnum2;
        this.lsec1 = lsec1;
        this.lsec2 = lsec2;
        this.tsec1 = tsec1;
        this.tsec2 = tsec2;
    }

    public String getFac1() { return fac1; }
    public String getFac2() { return fac2; }
    public int getCourseNum1() { return cnum1; }
    public int getCourseNum2() { return cnum2; }
    public int getLecSec1() { return lsec1; }
    public int getLecSec2() { return lsec2; }
    public int getTutSec1() { return tsec1; }
    public int getTutSec2(){ return  tsec2; }

}
