import javafx.scene.layout.CornerRadii;
import org.apache.commons.cli.*;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class Scheduler {
    //time is setup with the first digit as the day and the rest is the start time
    //lecture with lecture collisions
    public static HashMap<Integer, Integer> lectime_collisions = new HashMap(21);
    public static HashMap<Integer, Integer> tuttime_collisions = new HashMap(31);
    public static HashMap<Integer, Integer> lectuttime_collisions = new HashMap(41);
    public static boolean debug = false;
    public static int best = Integer.MIN_VALUE;
    public static int pen_coursemin = 1;
    public static int pen_labmin = 1;
    public static int pen_notpaired = 1;
    public static int pen_secdiff = 1;
    public static int w_minfilled = 1;
    public static int w_pref = 1;
    public static int w_pair = 1;
    public static int w_secdiff = 1;

    /*public static final int lectime_collisions[][] = {
        //monday
        {10800, 10800}, {10900, 10900}, {11000, 11000}, {11100, 11100},
        {11200, 11200}, {11300, 11300}, {11400, 11400}, {11500, 11500},
        {11600, 11600}, {11700, 11700}, {11800, 11800}, {11900, 11900},
        {12000, 12000},
        //tuesday
        {20800, 20800}, {20930, 20930}, {21100, 21100}, {21230, 21230},
        {21400, 21400}, {21530, 21530}, {21700, 21700}, {21830, 21830}
    };
    //tutorial with tutorial collisions
    public static final int tuttime_collisions[][] = {
        //monday
        {10800, 10800}, {10900, 10900}, {11000, 11000}, {11100, 11100},
        {11200, 11200}, {11300, 11300}, {11400, 11400}, {11500, 11500},
        {11600, 11600}, {11700, 11700}, {11800, 11800}, {11900, 11900},
        {12000, 12000},
        //tuesday
        {20800, 20800}, {20900, 20900}, {21000, 21000}, {21100, 21100},
        {21200, 21200}, {21300, 21300}, {21400, 21400}, {21500, 21500},
        {21600, 21600}, {21700, 11700}, {21800, 21800}, {21900, 21900},
        {22000, 22000},
        //friday
        {50800, 50800}, {51000, 51000}, {51200, 51200}, {51400, 51400},
        {51600, 51600}
    };
    //lecture with tutorial collisions. lecutre times are first
    public static final int lectuttime_collisions[][] = {
        //monday
        {10800, 10800}, {10900, 10900}, {11000, 11000}, {11100, 11100},
        {11200, 11200}, {11300, 11300}, {11400, 11400}, {11500, 11500},
        {11600, 11600}, {11700, 11700}, {11800, 11800}, {11900, 11900},
        {12000, 12000},
        //monday friday
        {10800, 50800}, {10900, 50800}, {11000, 51000}, {11100, 51000},
        {11200, 51200}, {11300, 51200}, {11400, 51400}, {11500, 51400},
        {11600, 51600}, {11700, 51600}, {11800, 51800}, {11900, 51800},
        //tuesday
        {20800, 20800}, {20800, 20900}, {20930, 20900}, {20930, 21000},
        {21100, 21100}, {21100, 21200}, {21230, 21200}, {21230, 21300},
        {21400, 21400}, {21400, 21500}, {21530, 21500}, {21530, 21600},
        {21700, 21700}, {21700, 21800}, {21830, 21800}, {21830, 21900}
    };*/
    public static final int times[] = {
            //monday
            10800, 10900, 11000, 11100, 11200, 11300, 11400, 11500, 11600,
            11700, 11800, 11900, 12000,
            //tuesday lec
            20800, 20930, 21100, 21230, 21400, 21530, 21700, 21830,
            //tuesday tut
            20800, 20900, 21000, 21100, 21200, 21300, 21400, 21500, 21600,
            21700, 21800, 21900, 22000,
            //friday
            50800, 51000, 51200, 51400, 51600, 51800
    };

    public static void main(String args[]) {
        lectime_collisions.put(10800, 10800);lectime_collisions.put(10900, 10900);lectime_collisions.put(11000, 11000);lectime_collisions.put(11100, 11100);
        lectime_collisions.put(11200, 11200);lectime_collisions.put(11300, 11300);lectime_collisions.put(11400, 11400);lectime_collisions.put(11500, 11500);
        lectime_collisions.put(11600, 11600);lectime_collisions.put(11700, 11700);lectime_collisions.put(11800, 11800);lectime_collisions.put(11900, 11900);
        lectime_collisions.put(12000, 12000);
        lectime_collisions.put(20800, 20800);lectime_collisions.put(20930, 20930);lectime_collisions.put(21100, 21100);lectime_collisions.put(21230, 21230);
        lectime_collisions.put(21400, 21400);lectime_collisions.put(21530, 21530);lectime_collisions.put(21700, 21700);lectime_collisions.put(21830, 21830);

        tuttime_collisions.put(10800, 10800);tuttime_collisions.put(10900, 10900);tuttime_collisions.put(11000, 11000);tuttime_collisions.put(11100, 11100);
        tuttime_collisions.put(11200, 11200);tuttime_collisions.put(11300, 11300);tuttime_collisions.put(11400, 11400);tuttime_collisions.put(11500, 11500);
        tuttime_collisions.put(11600, 11600);tuttime_collisions.put(11700, 11700);tuttime_collisions.put(11800, 11800);tuttime_collisions.put(11900, 11900);
        tuttime_collisions.put(12000, 12000);
        tuttime_collisions.put(20800, 20800);tuttime_collisions.put(20900, 20900);tuttime_collisions.put(21000, 21000);tuttime_collisions.put(21100, 21100);
        tuttime_collisions.put(21200, 21200);tuttime_collisions.put(21300, 21300);tuttime_collisions.put(21400, 21400);tuttime_collisions.put(21500, 21500);
        tuttime_collisions.put(21600, 21600);tuttime_collisions.put(21700, 11700);tuttime_collisions.put(21800, 21800);tuttime_collisions.put(21900, 21900);
        tuttime_collisions.put(22000, 22000);
        tuttime_collisions.put(50800, 50800);tuttime_collisions.put(51000, 51000);tuttime_collisions.put(51200, 51200);tuttime_collisions.put(51400, 51400);
        tuttime_collisions.put(51600, 51600);

        lectuttime_collisions.put(10800, 10800);lectuttime_collisions.put(10900, 10900);lectuttime_collisions.put(11000, 11000);lectuttime_collisions.put(11100, 11100);
        lectuttime_collisions.put(11200, 11200);lectuttime_collisions.put(11300, 11300);lectuttime_collisions.put(11400, 11400);lectuttime_collisions.put(11500, 11500);
        lectuttime_collisions.put(11600, 11600);lectuttime_collisions.put(11700, 11700);lectuttime_collisions.put(11800, 11800);lectuttime_collisions.put(11900, 11900);
        lectuttime_collisions.put(12000, 12000);
        lectuttime_collisions.put(10800, 50800);lectuttime_collisions.put(10900, 50800);lectuttime_collisions.put(11000, 51000);lectuttime_collisions.put(11100, 51000);
        lectuttime_collisions.put(11200, 51200);lectuttime_collisions.put(11300, 51200);lectuttime_collisions.put(11400, 51400);lectuttime_collisions.put(11500, 51400);
        lectuttime_collisions.put(11600, 51600);lectuttime_collisions.put(11700, 51600);lectuttime_collisions.put(11800, 51800);lectuttime_collisions.put(11900, 51800);
        lectuttime_collisions.put(20800, 20800);lectuttime_collisions.put(20800, 20900);lectuttime_collisions.put(20930, 20900);lectuttime_collisions.put(20930, 21000);
        lectuttime_collisions.put(21100, 21100);lectuttime_collisions.put(21100, 21200);lectuttime_collisions.put(21230, 21200);lectuttime_collisions.put(21230, 21300);
        lectuttime_collisions.put(21400, 21400);lectuttime_collisions.put(21400, 21500);lectuttime_collisions.put(21530, 21500);lectuttime_collisions.put(21530, 21600);
        lectuttime_collisions.put(21700, 21700);lectuttime_collisions.put(21700, 21800);lectuttime_collisions.put(21830, 21800);lectuttime_collisions.put(21830, 21900);

        Options options = new Options();
        Option pcm = new Option("pcm","coursepen", true, "Course min penalty");
        options.addOption(pcm);
        Option plm = new Option("plm", "labpen", true, "Lab min penalty");
        options.addOption(plm);
        Option pnp = new Option("pnp", "pairpen", true, "Not pair penalty");
        options.addOption(pnp);
        Option wmf = new Option("wmf", "minweight", true, "Weight of min fill");
        options.addOption(wmf);
        Option wpf = new Option("wpf", "prefweight", true, "Weight of preference");
        options.addOption(wpf);
        Option wpa = new Option("wpa", "pairweight", true, "Weight of pairs");
        options.addOption(wpa);
        Option wsd = new Option("wsd", "secweight", true, "Weight of secdiff");
        options.addOption(wsd);
        Option debugo = new Option("d", "debug", false, "DEBUG mode on");
        options.addOption(debugo);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter hf = new HelpFormatter();
        CommandLine cmd = null;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            hf.printHelp("utility-name", options);

            System.exit(1);
        }
        if(cmd.hasOption("pcm"))
            pen_coursemin = Integer.parseInt(cmd.getOptionValue("pcm"));
        if(cmd.hasOption("plm"))
            pen_labmin = Integer.parseInt(cmd.getOptionValue("plm"));
        if(cmd.hasOption("pnp"))
            pen_notpaired = Integer.parseInt(cmd.getOptionValue("pnp"));
        if(cmd.hasOption("d")) debug = true;

        Parser parse = new Parser(args[0]);

        Fact foo = new Fact(parse.courses);
        Slot[] foos = new Slot[parse.slots.size()];
        for(int i = 0; i < foos.length; i++){
            foos[i] = parse.slots.get(i);
        }
        foo.slots = foos;

        for(int i = 0; i < parse.partial.size(); i++) {
            foo.assign(parse.partial.get(i).time, parse.partial.get(i).course);
        }
        //parse input file
        Fact solution = and_tree(foo, parse);
        //print or save solution

        System.out.println("Eval Value: " + solution.score);
        Slot[] sols = solution.slots;
        String[] result = new String[parse.courses.size()];
        int count = 0;
        Integer[] num = {0,1,2,3,4,5,6,7,8,9};
        for(Slot s : sols){
            if(s.course.size() == 0) continue;
            for (Course c : s.course) {
                String tmp = "";
                String g = Integer.toString(c.lecture_num);
                String lecNum = "";
                if(g.length() < 2) lecNum = "0" + g;
                if(c.is_lecture)
                    tmp = tmp + c.name + " " + c.number + " LEC " + lecNum + "             ";
                if(!c.is_lecture && c.lecture_num == 0 && c.tutorial_num > 0) {
                    g = Integer.toString(c.tutorial_num);
                    lecNum = "0" + g;
                    tmp = tmp + c.name + " " + c.number + " TUT " + lecNum + "             ";
                }
                if(c.lecture_num != 0 && c.tutorial_num > 0) {
                    String g2 = Integer.toString(c.tutorial_num);
                    String lecNum2 = "0" + g;
                    tmp = tmp + c.name + " " + c.number + " LEC " + lecNum + " TUT " + lecNum2 + "      ";
                }
                if(!c.is_lecture && c.lecture_num == 0 && c.tutorial_num < 0) {
                    g = Integer.toString(c.tutorial_num);
                    lecNum = "0" + g;
                    tmp = tmp + c.name + " " + c.number + " LAB " + -lecNum + "             ";
                }
                if(c.lecture_num != 0 && c.tutorial_num < 0) {
                    String g2 = Integer.toString(c.tutorial_num);
                    String lecNum2 = "0" + g;
                    tmp = tmp + c.name + " " + c.number + " LEC " + lecNum + " LAB " + -lecNum2 + "      ";
                }
                int time = s.time;
                String str = Integer.toString(s.time);
                String dayOf = str.substring(0, 1);
                String hour = str.substring(1, 3);
                String min = str.substring(3, 5);
                switch (dayOf) {
                    case "1":
                        dayOf = "MO";
                        break;
                    case "2":
                        dayOf = "TU";
                        break;
                    case "5":
                        dayOf = "FR";
                        break;
                }
                tmp = tmp + ": " + dayOf + ", " + hour + ":" + min;

                result[count] = tmp;
                count++;
            }
        }
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(parse.name + "RESULTS.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int nullcount = 0;
        for(String s : result){
            if(s == null){
                nullcount++;
            }
        }
        String[] result2 = new String[parse.courses.size() - nullcount];
        int j = 0;
        for(String s : result){
            if(s == null){
                continue;
            }
            result2[j] = s;
            j++;
        }
        Arrays.sort(result2);
        for(String s : result2){
            System.out.println(s);
            pw.println(s);
        }
        pw.close();
    }

    public static Fact and_tree(Fact current, Parser parse) {
        //return if there is nothing left to add
        if(current.unassigned.size() == 0) {
            return current;
        }
        //find all possible combinations for adding 1 course
        Vector<Fact> possible = new Vector();
        Fact tmp_cur = current.copy();
        for(int i = 0; i < current.slots.length; i++) {
            //copy so we don't alter the current values
            //tmp_cur.assign(i, 0);

            //Fact tmp = and_tree(tmp_cur);
            tmp_cur.evaluate(i, parse);
            if (tmp_cur.score > best) {//don't add if null or worse
                best = tmp_cur.score;
                possible.add(tmp_cur);
            }
        }
        //evaluate and find the best
        //need to change this so it doesn't create and evaluate every single possibility
        Fact solution = null;

        int best = Integer.MIN_VALUE;
        int least_unass = current.unassigned.size();
        for(int i = 0; i < possible.size(); i++) {
            if(possible.get(i).score > best & possible.get(i).unassigned.size() < least_unass) {
                least_unass = possible.get(i).unassigned.size();
                solution = possible.get(i);
                best = possible.get(i).score;
            }
        }
        return solution;
    }

    //evaluate all hard constraint violations to Integer.MIN_VALUE
    //Also just evaluate with respect to the current class in the current slot

}

class Slot {
    public int time;
    public Vector<Course> course;
    public int coursemin;
    public int coursemax;
    public int labmin;
    public int labmax;
    public int asscourse;
    public int asslab;

    public Slot(int time) {
        this.time = time;
        course = new Vector();
        coursemax = 0;
        coursemin = 0;
        labmax = 0;
        labmin = 0;
        asscourse = 0;
        asslab = 0;
    }

    public Slot(Vector<Course> courses, int time) {
        this.time = time;
        course = courses;
        coursemax = 0;
        coursemin = 0;
        labmax = 0;
        labmin = 0;
        asscourse = 0;
        asslab = 0;
    }

    public Slot(Vector<Course> courses, int time, int cmin, int cmax, int lmin, int lmax, int acor, int alab) {
        this.time = time;
        course = courses;
        coursemax = cmax;
        coursemin = cmin;
        labmax = lmax;
        labmin = lmin;
        asscourse = acor;
        asslab = alab;

    }

    public boolean addCourse(Course toAdd) {
        course.add(toAdd);
        return true;
    }
    public Slot copy() {
        return new Slot((Vector<Course>)this.course.clone(), time, coursemin, coursemax, labmin, labmax, asscourse, asslab);
    }
}

class Course {
    public String name;
    public int number;
    public int lecture_num;
    public int tutorial_num;
    public boolean is_lecture;//true if lecture, false if tutorial


    public Course(String name, int number, int lecture_num, boolean is_lecture) {
        this.name = name;
        this.number = number;
        this.lecture_num = lecture_num;
        this.is_lecture = is_lecture;
        if(!is_lecture){
            this.tutorial_num = lecture_num;
            this.lecture_num = 0;
        }
    }
    public Course(String name, int number, int lecture_num, int tutorial_num, boolean is_lecture) {
        this.name = name;
        this.number = number;
        this.lecture_num = lecture_num;
        this.is_lecture = is_lecture;
        this.tutorial_num = tutorial_num;
    }
    public boolean equals(Course compare) {
        return (this.name.equals(compare.name) && this.number == compare.number &&
                this.lecture_num == compare.lecture_num && this.is_lecture == compare.is_lecture);
    }
}

class Fact {
    public Slot[] slots = null;
    public int score = 0;
    public Vector<Course> unassigned;
    public Vector<Course> conflict;

    public Fact(Vector<Course> unassigned) {
        this.unassigned = (Vector<Course>)unassigned.clone();
        slots = new Slot[Scheduler.times.length];
        for(int i = 0; i < Scheduler.times.length; i++) {
            slots[i] = new Slot(Scheduler.times[i]);
        }
    }

    public Fact copy() {
        Fact tmp = new Fact(unassigned);
        tmp.score = this.score;
        tmp.slots = new Slot[this.slots.length];
        for(int i = 0; i < this.slots.length; i++) {
            tmp.slots[i] = this.slots[i].copy();
        }
        return tmp;
    }


    public boolean constr(int slotnum, int coursenum, Parser parse){
        this.conflict.clear();
        /*if(this.slots[slotnum].coursemax < this.slots[slotnum].asscourse + 1) {
            return;
        }
        if(this.slots[slotnum].labmax < this.slots[slotnum].asslab + 1 ) {
            return;
        }*/
        //check all elements of the courses that are assigned then check
        //if name, number, lecnum equal, then the lab\class cannot be assigned to the same slot
        for (Course course : this.slots[slotnum].course) {
            if(this.unassigned.get(coursenum).name.equals(course.name) &&
                    this.unassigned.get(coursenum).number == course.number &&
                    this.unassigned.get(coursenum).lecture_num == course.lecture_num &&
                    this.unassigned.get(coursenum).is_lecture != course.is_lecture) {
                return false;
            }
        }
        //check if any of the course pairs is equal to the course in question
        //then check the assigned slots to see if they equal the opposite pair

        for(CoursePair p : parse.pair) {
            if(p.first.equals(this.unassigned.get(coursenum))) {
                for(Course c : this.slots[slotnum].course) {
                    if(this.slots[slotnum].course.equals(p.second)){
                        return false;
                    }
                }
            }
            else if(p.second.equals(this.unassigned.get(coursenum))){
                for(Course c : this.slots[slotnum].course) {
                    if(this.slots[slotnum].course.equals(p.first)) {
                        return false;
                    }
                }
            }

        }
        //traverses through the unwatned vector and compares the slot time to each CourseTime time and then compares the course to see if they are equal
        for(CourseTime c : parse.unwanted){
            if(c.time == slots[slotnum].time && c.course.equals(this.unassigned.get(coursenum))) return false;

        }
        if(this.unassigned.get(coursenum).number >= 500){
            for(Course c : this.slots[slotnum].course){
                if(c.number >= 500 && c.number != this.unassigned.get(coursenum).number){
                    return false;
                }
            }
        }
        int fri_slotnum = 0;

        if(this.unassigned.get(coursenum).is_lecture){
            if(this.slots[slotnum].time <= 12000){
                fri_slotnum = this.slots[slotnum].time + 40000;
                //TODO: add into friday time slot at end
                //friday time slot is 0 if none of the above conditions are true
            }
        }
        //departmental contraints
        if(this.unassigned.get(coursenum).name.equals("CPSC")){
            if(this.unassigned.get(coursenum).is_lecture && this.slots[slotnum].time == 21130) return false;

            if(this.unassigned.get(coursenum).lecture_num == 9){
                if(this.slots[slotnum].time < 51800 && this.slots[slotnum].time > 50000) return false;
                else if(this.slots[slotnum].time < 21800 && this.slots[slotnum].time > 20000) return false;
                else if(this.slots[slotnum].time < 11800 && this.slots[slotnum].time > 10000) return false;
            }
    if(this.unassigned.get(coursenum).lecture_num == 813 &&this.unassigned.get(coursenum).is_lecture && this.slots[slotnum].time != 21800) return false;
    if(this.unassigned.get(coursenum).lecture_num == 813 && this.unassigned.get(coursenum).is_lecture && this.slots[slotnum].time != 21800) return false;
    if(this.unassigned.get(coursenum).lecture_num == 813 && !this.unassigned.get(coursenum).is_lecture){
      //traverse not compatible vector to see for any conflicts.
      for(CousePair cp : parse.not_compatible){
        if(cp.first.name.equals("CPSC") && cp.first.number == 313) {
          this.conflict.add(cp.second);
        }
        if(cp.second.name.equals("CPSC") && cp.second.number == 313) {
          this.conlflict.add(cp.first);
        }
      }
      for(Couse c : this.slots[slotnum].course){
        if(!this.conflict.isEmpty()){
          for(Course con : this.conflict){
            if(con.equals(c)){
              return false;
            }
          }
        }
        if(c.name.equals("CPSC") && c.number == 313) return;
      }
    }
    if(this.unassigned.get(coursenum).lecture_num == 913 && !this.unassigned.get(coursenum).is_lecture){
      //traverse not compatible vector to see for any conflicts.
      for(CousePair cp : parse.not_compatible){
        if(cp.first.name.equals("CPSC") && cp.first.number == 413) {
          this.conflict.add(cp.second);
        }
        if(cp.second.name.equals("CPSC") && cp.second.number == 413) {
          this.conlflict.add(cp.first);
        }
      }
      for(Couse c : slots[slotnum].course){
        if(!this.conflict.isEmpty()){
          for(Course con : this.conflict){
            if(con.equals(c)){
              return false;
            }
          }
        }
        if(c.name.equals("CPSC") && c.number == 413) return false;
      }
    }
    }
        }
        this.slots[slotnum].course.add(this.unassigned.get(coursenum));
        this.unassigned.remove(this.unassigned.get(coursenum));
        return true;
    }

    public int eval_minfilled(int slot){
        int cnum = 0, lnum = 0;
        for(int i = 0; i < this.slots[slot].course.size(); i++) {
            if(this.slots[slot].course.get(i).is_lecture) {
                cnum++;
            } else {
                lnum++;
            }
        }
        boolean lecFlag = false;
        boolean labFlag = false;
        if(this.slots[slot].coursemax != 0 & this.slots[slot].coursemin != 0) lecFlag = true;
        if(this.slots[slot].labmax != 0 & this.slots[slot].labmin != 0) labFlag = true;
        if(this.slots[slot].coursemax < cnum & lecFlag) {
            return Integer.MIN_VALUE;

        }
        if(this.slots[slot].labmax < lnum & labFlag) {
            return Integer.MIN_VALUE;
        }
        for(Slot s : this.slots){
            if(s.course.size() == 0) break;
            int min = s.coursemin;
            int leccount = cnum;
            int labcount = lnum;
            if(s.coursemin > leccount) score += Scheduler.pen_coursemin;
            if(s.labmin > labcount) score += Scheduler.pen_labmin;

        }
        return score;
    }
    public int eval_pref(Parser parse){
        int nonpref = 0;
        for(Slot s : this.slots){
            for(Preference p : parse.preferences){
                for(Course c : s.course){
                    if (c.equals(p.course) && s.time != p.time){
                        nonpref += p.value;
                    }
                }
            }
        }
        return nonpref;
    }
    public int eval_pair(Parser parse){
        int score = 0;
        for(CoursePair cp : parse.pair){
            for(Slot s : this.slots){
                if(s.course.contains(cp.first) && !(s.course.contains(cp.second))){
                    score += Scheduler.pen_notpaired;
                }
                if(s.course.contains(cp.second) && !(s.course.contains(cp.first))){
                    score += Scheduler.pen_notpaired;
                }
            }
        }
        return score;
    }
    public int eval_secdiff(Parser parse){
        int score = 0;
        int[] checked = new int[parse.courses.size()];
        int i = 0;
        for(Course c : parse.courses){
            for(Course k : parse.courses){
                boolean notin = false;
                for(int j : checked){
                    if(c.number == j){
                        notin = true;
                        break;
                    }
                }
                if(c.number == k.number & c.lecture_num != k.lecture_num && notin){
                    score += Scheduler.pen_secdiff;
                    checked[i] = c.number;
                }
            }
            i++;
        }
        return score;
    }

    public void evaluate(int slot, Parser parse) {
        this.constr(slot, this.unassigned.size()-1, parse);
        int score = 0;
        score = (this.eval_minfilled(slot) * Scheduler.w_minfilled) + (this.eval_pref(parse) * Scheduler.w_pref) + (this.eval_pair(parse) *
                Scheduler.w_pair) + (this.eval_secdiff(parse) * Scheduler.w_secdiff);
        //if there are too many courses in the slot

    }

    //try to use the other one because this is slow
    public boolean assign(int time, Course course) {
        int slotnum, coursenum;
        for(slotnum = 0; slotnum < this.slots.length; slotnum++) {
            if(time == this.slots[slotnum].time) {
                break;
            }
        }
        if(slotnum == this.slots.length) return false;
        for(coursenum = 0; coursenum < this.unassigned.size(); coursenum++) {
            if(course == this.unassigned.get(coursenum) || course.equals(this.unassigned.get(coursenum))) {
                break;
            }
        }
        if(coursenum == this.unassigned.size()) return false;
        this.slots[slotnum].course.add(this.unassigned.remove(coursenum));
        return true;
    }
}
