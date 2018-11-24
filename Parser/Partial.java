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
        this.day = arr[1];
        this.time = arr[2];
        String[] arr1 = arr[0].split(" ");
        if(arr1.length == 4){
            if(arr1[2].equals("TUT")){
                this.faculty = arr1[0];
                this.cnum = Integer.parseInt(arr1[1]);
                this.lsec = -1;
                this.tsec = Integer.parseInt(arr1[3]);

            }
            else if(arr1[2].equals("LEC")){
                this.faculty = arr1[0];
                this.cnum = Integer.parseInt(arr1[1]);
                this.lsec = Integer.parseInt(arr1[3]);
                this.tsec = -1;
            }
        }
        else if(arr1.length == 6){
            if(arr1[2].equals("LEC")) {
                this.faculty = arr1[0];
                this.cnum = Integer.parseInt(arr1[1]);
                this.lsec = Integer.parseInt(arr1[3]);
                this.tsec = Integer.parseInt(arr1[5]);
            }
            else if(arr1[4].equals("LEC")){
                this.faculty = arr1[0];
                this.cnum = Integer.parseInt(arr1[1]);
                this.tsec = Integer.parseInt(arr1[3]);
                this.lsec = Integer.parseInt(arr1[5]);
            }
        }
    }
}
