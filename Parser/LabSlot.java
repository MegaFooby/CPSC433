public class LabSlot<Day, Start, Max, Min> {

    private final Day day;
    private final Start start;
    private final Max max;
    private final Min min;

    public LabSlot(Day day, Start start, Max max, Min min) {
        this.day = day;
        this.start = start;
        this.max = max;
        this.min = min;
    }

    public Day getDay() { return day; }
    public Start getStart() { return start; }
    public Max getMax() { return max; }
    public Min getMin() { return min;}
}