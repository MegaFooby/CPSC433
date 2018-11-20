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
        String[] arr1 = arr[0].split(" ");
        String[] arr2 = arr[1].split(" ");
        if(arr1[2].equals("LEC") && arr2[2].equals("LEC")){
            this.fac1 = arr1[0];
            this.fac2 = arr2[0];
            this.cnum1 = Integer.parseInt(arr1[1]);
            this.cnum2 = Integer.parseInt(arr2[1]);
            this.lsec1 = Integer.parseInt(arr1[3]);
            this.lsec2 = Integer.parseInt(arr2[3]);
            this.tsec1 = -1;
            this.tsec2 = -1;
        }
        else if(arr1[2].equals("LEC") && arr2[2].equals("TUT")){
            this.fac1 = arr1[0];
            this.fac2 = arr2[0];
            this.cnum1 = Integer.parseInt(arr1[1]);
            this.cnum2 = Integer.parseInt(arr2[1]);
            this.lsec1 = Integer.parseInt(arr1[3]);
            this.lsec2 = -1;
            this.tsec1 = -1;
            this.tsec2 = Integer.parseInt(arr2[3]);
        }
        else if(arr1[2].equals("TUT") && arr2[2].equals("TUT")){
            this.fac1 = arr1[0];
            this.fac2 = arr2[0];
            this.cnum1 = Integer.parseInt(arr1[1]);
            this.cnum2 = Integer.parseInt(arr2[1]);
            this.lsec1 = -1;
            this.lsec2 = -1;
            this.tsec1 = Integer.parseInt(arr1[3]);;
            this.tsec2 = Integer.parseInt(arr2[3]);;
        }
    }
}
