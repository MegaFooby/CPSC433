import javafx.scene.layout.CornerRadii;
import javafx.util.Pair;
import org.apache.commons.cli.*;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;
public class Main {
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
    public static void main(String[] args) {
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
        if(cmd.hasOption("wmf"))
            w_minfilled = Integer.parseInt(cmd.getOptionValue("wmf"));
        if(cmd.hasOption("wpf"))
            w_pref = Integer.parseInt(cmd.getOptionValue("wpf"));
        if(cmd.hasOption("wpa"))
            w_pair = Integer.parseInt(cmd.getOptionValue("wpa"));
        if(cmd.hasOption("wsd"))
            w_secdiff = Integer.parseInt(cmd.getOptionValue("wsd"));
        if(cmd.hasOption("d")) debug = true;
        if(args.length == 0){
            System.out.println("Usage: java Main filename [-arguments] [-options]");
            System.exit(0);
        }
        Parser parse = new Parser(args[0]);
        Fact init = new Fact(parse.courseSlotList, parse.labsSlotList, parse.courses, parse.labs);
        for(Partial p : parse.partials){
            if(p.getTutSec() == -1){
                int min = 0;
                int max = 0;
                for(CourseSlot cs : parse.courseSlotList){
                    if(cs.getDay().equals(p.getDay()) & cs.getStart().equals(p.getTime())){
                        min = cs.getMin();
                        max = cs.getMax();
                    }
                }
                Course c = new Course(p.getFaculty(), p.getCourseNum(), p.getLecSec());
                CourseSlot cs = new CourseSlot(p.getDay(), p.getTime(), max, min, c);
                for(CourseSlot ks : parse.courseSlotList){
                    if(ks.start.equals(cs.start) && ks.min == cs.min && ks.max == cs.max && ks.day.equals(cs.day)){
                        ks.count = ks.count + 1;
                    }
                }
                init.assignments.add(new Assignments(c,cs));
                hasPair(c, cs, parse, init);
                for(Course k : parse.courses){
                    if(k.getFaculty().equals(c.getFaculty()) && k.getCourseNum() == c.getCourseNum() && k.getLecSec() == c.getLecSec()) {
                        init.unassCourse.remove(k);
                        break;
                    }

                }

            }
            else if(p.getLecSec() == -1 || (p.getTutSec() == 1 & p.getLecSec() == 1)){
                int min = 0;
                int max = 0;
                for(LabSlot ls : parse.labsSlotList){
                    if(ls.getDay().equals(p.getDay()) & ls.getStart().equals(p.getTime())){
                        min = ls.getMin();
                        max = ls.getMax();
                    }
                }
                Lab c = new Lab(p.getFaculty(), p.getCourseNum(), p.getLecSec(), p.getTutSec());
                LabSlot ls = new LabSlot(p.getDay(), p.getTime(), max, min, c);
                for(LabSlot ks : parse.labsSlotList){
                    if(ks.start.equals(ls.start) && ks.min == ls.min && ks.max == ls.max && ks.day.equals(ls.day)){
                        ks.count = ks.count + 1;
                    }
                }
                init.assignments.add(new Assignments(c,ls));
                //hasPair for labs
                for(Lab k : parse.labs){
                    if(k.getFaculty().equals(c.getFaculty()) && k.getCourseNum() == c.getCourseNum() && k.getLecSec() == c.getLecSec()) {
                        init.unassCourse.remove(k);
                        break;
                    }

                }

            }
        }

        List<Fact> sols = assign(init, parse);
        List<Fact> res = new ArrayList<>();
        int best = Integer.MAX_VALUE;
        for(Fact b : sols){
            if(constr(b) && !res.contains(b)){
                res.add(b);
            }
        }
        Fact bestf = null;
        for(Fact b : sols){
            int t = eval(b);
            if(t < best){
                best = t;
                bestf = b;
            }
        }
        String[] result = new String[bestf.assignments.size()];
        int count = 0;
        Fact f = sols.get(0);

            for(Assignments as : f.assignments){
                String out = "";
                int g = 0;
                String gg = "";
                int j = 0;
                String jj = "";
                if(as.course != null && as.lab == null) {
                    g = as.course.getLecSec();
                    if(g < 10) {
                        gg = "0"+Integer.toString(g);
                        out = as.course.getFaculty() + " " + as.course.getCourseNum() + " LEC " +
                                gg + "          :          " + as.courseSlot.day + " , " + as.courseSlot.start;
                    }
                    else {
                        out = as.course.getFaculty() + " " + as.course.getCourseNum() + " LEC " +
                                as.course.getLecSec() + "          :          " + as.courseSlot.day + " , " + as.courseSlot.start;
                    }
                }
                else if(as.course == null && as.lab != null) {
                    g = as.lab.getTutSec();
                    if (g < 10) {
                        gg = "0" + Integer.toString(g);
                        out = as.lab.getFaculty() + " " + as.lab.getCourseNum() + " TUT " + gg +
                                "          :          " + as.labSlot.day + " , " + as.labSlot.start;
                    } else {
                        out = as.lab.getFaculty() + " " + as.lab.getCourseNum() + " TUT " + as.lab.getTutSec() +
                                "          :          " + as.labSlot.day + " , " + as.labSlot.start;
                    }
                }
                /*else if(as.course != null && as.lab != null) {
                    g = as.course.getLecSec();
                    j = as.lab.getTutSec();
                    gg = Integer.toString(g);
                    jj = Integer.toString(j);
                    if(g < 10) gg = "0"+Integer.toString(g);
                    if(j < 10) jj = "0"+Integer.toString(j);
                    out = as.course.getFaculty() + " " + as.course.getCourseNum() + " LEC " + gg
                            + " TUT " + jj + "          :          " + as.;
                }*/
                result[count] = out;
                count++;
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
        String[] result2 = new String[result.length - nullcount];
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
    public static boolean constr(Fact f){
        boolean resulty = true;
        for(CourseSlot cs : f.courseSlotList) {
            if (cs.max < cs.count) {
                return false;
            }
        }
        for(LabSlot ls : f.labsSlotList) {
            if (ls.max < ls.count) {
                return false;
            }
        }
        //check all elements of the courses that are assigned then check
        //if name, number, lecnum equal, then the lab\class cannot be assigned to the same slot
        for(CourseSlot cs : f.courseSlotList) {
            for (Course course : cs.courses) {
                if (f.unassCourse.contains(course)) {
                    return false;
                }
            }
        }
        for(LabSlot cs : f.labsSlotList) {
            for (Lab course : cs.labs) {
                if (f.unassLab.contains(course)) {
                    return false;
                }
            }
        }
        //departmental contraints
        for(Course c : f.unassCourse){
            if(c.getFaculty().equals("CPSC")) {
                if (this.unassigned.get(coursenum).is_lecture && this.slots[slotnum].time == 21130) return false;

                if (this.unassigned.get(coursenum).lecture_num == 9) {
                    if (this.slots[slotnum].time < 51800 && this.slots[slotnum].time > 50000) return false;
                    else if (this.slots[slotnum].time < 21800 && this.slots[slotnum].time > 20000) return false;
                    else if (this.slots[slotnum].time < 11800 && this.slots[slotnum].time > 10000) return false;
                }
                if (this.unassigned.get(coursenum).lecture_num == 813 && this.unassigned.get(coursenum).is_lecture && this.slots[slotnum].time != 21800)
                    return false;
                if (this.unassigned.get(coursenum).lecture_num == 813 && this.unassigned.get(coursenum).is_lecture && this.slots[slotnum].time != 21800)
                    return false;
                if (this.unassigned.get(coursenum).lecture_num == 813 && !this.unassigned.get(coursenum).is_lecture) {
                    //traverse not compatible vector to see for any conflicts.
                    for (CoursePair cp : parse.not_compatible) {
                        if (cp.first.name.equals("CPSC") && cp.first.number == 313) {
                            this.conflict.add(cp.second);
                        }
                        if (cp.second.name.equals("CPSC") && cp.second.number == 313) {
                            this.conflict.add(cp.first);
                        }
                    }
                    for (Course c : this.slots[slotnum].course) {
                        if (!this.conflict.isEmpty()) {
                            for (Course con : this.conflict) {
                                if (con.equals(c)) {
                                    return false;
                                }
                            }
                        }
                        if (c.name.equals("CPSC") && c.number == 313) return false;
                    }
                }
            }
            if(this.unassigned.get(coursenum).lecture_num == 913 && !this.unassigned.get(coursenum).is_lecture){
                //traverse not compatible vector to see for any conflicts.
                for(CoursePair cp : parse.not_compatible){
                    if(cp.first.name.equals("CPSC") && cp.first.number == 413) {
                        this.conflict.add(cp.second);
                    }
                    if(cp.second.name.equals("CPSC") && cp.second.number == 413) {
                        this.conflict.add(cp.first);
                    }
                }
                for(Course c : slots[slotnum].course){
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

        if(this.unassigned.get(coursenum).is_lecture) slots[slotnum].asscourse++;
        else slots[slotnum].asslab++;

        return true;
    }
    public static boolean unwanted(Course c, CourseSlot cs, Parser p){
        boolean result = true;
        for(Unwanted u : p.unwanted){
            Course cu = new Course(u.getFaculty(),u.getCourseNum(),u.getLecSec());
            CourseSlot clu = new CourseSlot(u.getDay(),u.getTime(),cs.getMax(),cs.getMin());
            if(!cu.equals(c) && !clu.equals(cs)){
                result = false;
            }
        }
        return result;
    }
    public static boolean isCompat(Course c, CourseSlot cs, Parser p){
        boolean result = true;
        for(Course k : cs.courses){
            NotComp nc = new NotComp(c.getFaculty(),k.getFaculty(),c.getCourseNum(),k.getCourseNum(),c.getLecSec(),k.getLecSec(),0,0);
            if(p.notComps.contains(nc)) result = false;
        }
        return result;
    }

    public static boolean isPref(Course c, CourseSlot cs, Parser p){
        boolean result = false;
        List<Preference> lp = new ArrayList<>();
        for(Preference pref : p.preferences){
            if(pref.getFaculty().equals(c.getFaculty()) && pref.getCourseNum() == c.getCourseNum() &&
                    pref.getLecSec() == c.getLecSec()){
                lp.add(pref);
            }
        }
        int maxWeight = 0;
        int idx = -1;
        for(int i = 0; i < lp.size(); i++){
            if(lp.get(i).getWeight() > maxWeight){
                idx = i;
            }
        }
        if(idx >= 0) result = true;
        return result;
    }

    public static void hasPair(Course c, CourseSlot cl, Parser p, Fact f){
        for(APair pair : p.pairs){
            if(pair.getTutSec1() != -1 && pair.getTutSec2() != -1) continue;
            if(pair.getFaculty1().equals(c.getFaculty()) && pair.getCourseNum1() == c.getCourseNum() &&
            pair.getLecSec1() == c.getLecSec()){
                for(Course k : f.unassCourse){
                    if(pair.getFaculty1().equals(k.getFaculty()) && pair.getCourseNum1() == k.getCourseNum() &&
                    pair.getLecSec1() == k.getLecSec()){
                        Assignments a = new Assignments(k,cl);
                        if(!f.assignments.contains(a)){
                            f.assignments.add(a);
                            f.unassCourse.remove(k);
                            cl.count = cl.count + 1;
                            break;
                        }
                    }
                }

            }
            else if(pair.getFaculty2().equals(c.getFaculty()) && pair.getCourseNum2() == c.getCourseNum() &&
                    pair.getLecSec2() == c.getLecSec()){
                for(Course k : f.unassCourse){
                    if(pair.getFaculty2().equals(k.getFaculty()) && pair.getCourseNum2() == k.getCourseNum() &&
                            pair.getLecSec2() == k.getLecSec()){
                        Assignments a = new Assignments(k,cl);
                        if(!f.assignments.contains(a)){
                            f.assignments.add(a);
                            f.unassCourse.remove(k);
                            cl.count = cl.count + 1;
                            break;
                        }
                    }
                }

            }
        }
    }
    //Could make list of facts as solutions
    public static Fact tree_course(Fact f, Parser p, List<Fact> o){
        if(f.unassCourse.size() == 0){
            o.add(f);
            return f;
        }
        while(f.unassCourse.size() > 0){
            Course c = f.unassCourse.get(new Random().nextInt(f.unassCourse.size()));
            CourseSlot cl = null;
            CourseSlot cs = f.courseSlotList.get(new Random().nextInt(f.courseSlotList.size()));
            if (cs.getCount() < cs.getMax() && !unwanted(c, cs, p) && isCompat(c, cs, p) && isPref(c, cs, p)) {
                cl = cs;
                cs.count = cs.count + 1;
                hasPair(c, cs, p, f);
            }
            else if (cs.getCount() < cs.getMax() && !unwanted(c, cs, p) && isCompat(c, cs, p)) {
                cl = cs;
                cs.count = cs.count + 1;
                hasPair(c, cs, p, f);

            }
            else{
                continue;
            }
            Assignments a = new Assignments(c,cl);
            if(!f.assignments.contains(a)){
                f.assignments.add(a);
                f.unassCourse.remove(c);
            }
            o.add(tree_course(f,p,o));
        }
        return f;
    }

    public static List<Fact> assign(Fact f, Parser p) {
        int unc = f.unassCourse.size();
        int unl = f.unassLab.size();
        List<Fact> test = new ArrayList<>();
        test.add(tree_course(f, p, test));
        return test;
    }
}
