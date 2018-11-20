public class Unwanted {
    private final String faculty;
    private final int cnum;
    private final int lsec;
    private final String day;
    private final String time;

    public Unwanted(String s){
        String[] arr = s.split(",");
        if(arr.length != 3){
            throw new Error("Not enough arguments in Unwanted for: " + s);
        }
        this.day = arr[1];
        this.time = arr[2];
        String[] arr1 = arr[0].split(" ");
        this.faculty = arr1[0];
        this.cnum = Integer.parseInt(arr1[1]);
        this.lsec = Integer.parseInt(arr[3]);
    }

    public String getFaculty() { return faculty; }
    public int getCourseNum() { return cnum; }
    public int getLecSec() { return lsec; }
    public String getDay() { return day; }
    public String getTime() { return time; }
}
