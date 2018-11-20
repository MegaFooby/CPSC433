public class NotComp {


    private String fac1;
    private String fac2;
    private int cnum1;
    private int cnum2;
    private int lsec1;
    private int lsec2;
    private int tsec1;
    private int tsec2;


    public NotComp(String s){
        String[] arr1 = s.split(",");
        String[] arr11 = arr1[0].split(" ");
        String[] arr12 = arr1[1].split(" ");
        if(arr11.length != arr12.length){
            throw new Error("Each line must have same number of arguments. Error occurred from: " + s);
        }
        /*Three options either Lec, Tut, or both */
        else{
            if(arr11.length == 4){
                String f1 = arr11[0];
                String f2 = arr12[0];
                int c1 = Integer.parseInt(arr11[1]);
                int c2 = Integer.parseInt(arr12[1]);
                int l1 = Integer.parseInt(arr11[3]);
                int l2 = Integer.parseInt(arr12[3]);
                int t1 = Integer.parseInt(arr11[3]);
                int t2 = Integer.parseInt(arr12[3]);

                if(arr11[2].trim().equals("TUT") && arr12[2].trim().equals("TUT")){
                    this.fac1 = f1;
                    this.fac2 = f2;
                    this.cnum1 = c1;
                    this.cnum2 = c2;
                    this.lsec1 = -1;
                    this.lsec2 = -1;
                    this.tsec1 = t1;
                    this.tsec2 = t2;
                }
                else if(arr11[2].trim().equals("LEC") && arr12[2].trim().equals("LEC")){
                    this.fac1 = f1;
                    this.fac2 = f2;
                    this.cnum1 = c1;
                    this.cnum2 = c2;
                    this.lsec1 = l1;
                    this.lsec2 = l2;
                    this.tsec1 = -1;
                    this.tsec2 = -1;
                }
                else if(arr11[2].trim().equals("LEC") && arr12[2].trim().equals("TUT")){
                    this.fac1 = f1;
                    this.fac2 = f2;
                    this.cnum1 = c1;
                    this.cnum2 = c2;
                    this.lsec1 = l1;
                    this.lsec2 = -1;
                    this.tsec1 = -1;
                    this.tsec2 = t1;
                }
                else if(arr11[2].trim().equals("TUT") && arr12[2].trim().equals("LEC")){
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
                String f1 = arr11[0];
                String f2 = arr12[0];
                int c1 = Integer.parseInt(arr11[1]);
                int c2 = Integer.parseInt(arr12[1]);
                int l1 = Integer.parseInt(arr11[3]);
                int l2 = Integer.parseInt(arr12[3]);
                int t1 = Integer.parseInt(arr11[5]);
                int t2 = Integer.parseInt(arr12[5]);

                if(arr11[2].trim().equals("LEC") && arr11[4].trim().equals("TUT")){

                    this.fac1 = f1;
                    this.fac2 = f2;
                    this.cnum1 = c1;
                    this.cnum2 = c2;
                    this.lsec1 = l1;
                    this.lsec2 = l2;
                    this.tsec1 = t1;
                    this.tsec2 = t2;
                }
                else if(arr11[2].trim().equals("TUT") && arr11[4].trim().equals("LEC")){
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

    public String getFac1() { return fac1; }
    public String getFac2() { return fac2; }
    public int getCourseNum1() { return cnum1; }
    public int getCourseNum2() { return cnum2; }
    public int getLecSec1() { return lsec1; }
    public int getLecSec2() { return lsec2; }
    public int getTutSec1() { return tsec1; }
    public int getTutSec2(){ return  tsec2; }

}
