/* Preference
 * A custom class used to store information about preferred times for courses and labs
 * Takes a string as input of form: Day Time Faculty Course# LEC Lec# TUT Tut# Weight
 * Contains get methods for all private variables
 */

public class Preference {
    private final String day;
    private final String time;
    private final String faculty;
    private final int cnum;
    private final int lsec;
    private final int tsec;
    private final int weight;

    public Preference(String day, String time, String faculty, int cnum, int lsec, int tsec, int weight){
        this.day = day;
        this.time = time;
        this.faculty = faculty;
        this.cnum = cnum;
        this.lsec = lsec;
        this.tsec = tsec;
        this.weight = weight;
    }

    public String getDay() { return day; }
    public String getTime() { return time; }
    public String getFaculty() { return faculty; }
    public int getCourseNum() { return cnum; }
    public int getLecSec() { return lsec; }
    public int getTutSec() { return  tsec; }
    public int getWeight() { return weight; }
}
