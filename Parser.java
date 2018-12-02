import java.io.*;
import java.util.*;

public class Parser {
    public static final int[] linelength = {6, 16, 16, 16, 16, 33, 26, 31, 33, 1};
    public String name;
    public Vector<Slot> slots = new Vector();//contains slots for both courses and labs
    public Vector<Course> courses = new Vector();//countains labs and courses
    public Vector<CoursePair> not_compatible = new Vector();//contains not compatible pairs <Course, Course>
    public Vector<CourseTime> unwanted = new Vector();//contains unwanted <Course, int time>
    public Vector<Preference> preferences = new Vector();
    public Vector<CoursePair> pair = new Vector();
    public Vector<CourseTime> partial = new Vector();

    public static void main(String args[]) {
        Parser parse = new Parser("deptinst1.txt");
    }

    public Parser(String filename) {
        String line = null;

        try {
            FileReader filereader = new FileReader(filename);
            BufferedReader read = new BufferedReader(filereader);
            line = read.readLine();
            this.name = read.readLine();
            int counter = 0;
            while(line != null) {
                if((line = read.readLine()) == null){
                    break;
                }
                //line = read.readLine();
				/*for(int i = 0; i < line.getBytes().length; i++) {
					System.out.print(line.getBytes()[i] + " ");
					if(line.getBytes()[i] == 10) {
						System.out.println();
					}
				}*/
				if(line == null){
				    line = "";
                }
                switch(line) {
                    case "Course slots:": counter = 1; break;
                    case "Lab slots:": counter = 2; break;
                    case "Courses:": counter = 3; break;
                    case "Labs:": counter = 4; break;
                    case "Not compatible:": counter = 5; break;
                    case "Unwanted:": counter = 6; break;
                    case "Preferences:": counter = 7; break;
                    case "Pair:": counter = 8; break;
                    case "Partial assignments:": counter = 9; break;
                    default:
                        if(line.length() < linelength[counter] || line.equals("\n") || line.equals(null)) {
                            break;
                        }
                }
                switch(counter) {
                    case 1: parse_course_slots(line); break;
                    case 2: parse_lab_slots(line); break;
                    case 3: parse_courses(line); break;
                    case 4: parse_labs(line); break;
                    case 5: parse_not_compatible(line); break;
                    case 6: parse_unwanted(line); break;
                    case 7: parse_preferences(line); break;
                    case 8: parse_pair(line); break;
                    case 9: parse_partial_assignments(line); break;
                }
            }

            read.close();
            filereader.close();
        }
        catch(Exception e) {
            System.out.println("Failed to open file: " + filename);
            System.out.println(e);
            System.out.println(line);
        }
    }

    public void parse_course_slots(String line) {
        if(line.equals("Course slots:")) return;
        if(line.equals("")) return;
        int time = 0;
        if(line.charAt(0) == 'M' && line.charAt(1) == 'O') {
            time += 10000;
        } else if(line.charAt(0) == 'T' && line.charAt(1) == 'U') {
            time += 20000;
        } else if(line.charAt(0) == 'F' && line.charAt(1) == 'R') {
            time += 50000;
        } else {
            System.out.println("Day " + line.charAt(0) + line.charAt(1) + " not recognized");
        }
        if(line.charAt(4) != ' ') {
            time += 1000* Integer.parseInt(line.substring(4, 5));
        }
        time += 100* Integer.parseInt(line.substring(5, 6));
        time += 10* Integer.parseInt(line.substring(7, 8));
        time += Integer.parseInt(line.substring(8, 9));

        Slot tmp = new Slot(time);
        tmp.coursemax = Integer.parseInt(line.substring(11, 12));
        tmp.coursemin = Integer.parseInt(line.substring(14, 15));
        this.slots.add(tmp);
    }
    public void parse_lab_slots(String line) {
        if(line.equals("Lab slots:")) return;
        if(line.equals("")) return;
        int time = 0;
        if(line.charAt(0) == 'M' && line.charAt(1) == 'O') {
            time += 10000;
        } else if(line.charAt(0) == 'T' && line.charAt(1) == 'U') {
            time += 20000;
        } else if(line.charAt(0) == 'F' && line.charAt(1) == 'R') {
            time += 50000;
        } else {
            System.out.println("Day " + line.charAt(0) + line.charAt(1) + " not recognized");
        }
        if(line.charAt(4) != ' ') {
            time += 1000* Integer.parseInt(line.substring(4, 5));
        }
        time += 100* Integer.parseInt(line.substring(5, 6));
        time += 10* Integer.parseInt(line.substring(7, 8));
        time += Integer.parseInt(line.substring(8, 9));
        boolean found = false;
        for(int i = 0; i < slots.size(); i++) {
            if(slots.get(i).time == time) {
                found = true;
                slots.get(i).labmax = Integer.parseInt(line.substring(11, 12));
                slots.get(i).labmin = Integer.parseInt(line.substring(14, 15));
                break;
            }
        }
        if(!found) {
            Slot tmp = new Slot(time);
            tmp.coursemax = Integer.parseInt(line.substring(11, 12));
            tmp.coursemin = Integer.parseInt(line.substring(14, 15));
            this.slots.add(tmp);
        }
    }
    public void parse_courses(String line) {
        if(line.equals("Courses:")) return;
        if(line.equals("")) return;
        String[] data = line.split(" ");
        Course tmp = new Course(data[0], Integer.parseInt(data[1]), Integer.parseInt(data[3]), true);
        this.courses.add(tmp);

    }
    public void parse_labs(String line) {
        if(line.equals("Labs:")) return;
        if(line.equals("")) return;
        String[] data = line.split(" ");
        if(data.length == 4){
            Course tmp = new Course(data[0], Integer.parseInt(data[1]), Integer.parseInt(data[3]), false);
            if(data[2].equals("LAB")){
                tmp.tutorial_num = -1 * tmp.tutorial_num;
            }
            this.courses.add(tmp);
        }
        else if(data.length == 6){
            Course tmp = new Course(data[0],Integer.parseInt(data[1]), Integer.parseInt(data[3]), Integer.parseInt(data[5]), false);
            if(data[4].equals("LAB")){
                tmp.tutorial_num = -1 * tmp.tutorial_num;
            }
            this.courses.add(tmp);
        }
    }
    public void parse_not_compatible(String line) {
        if(line.equals("Not compatible:")) return;
        if(line.equals("")) return;
        String[] data = line.split(",");
        String[] one = data[0].trim().split(" ");
        String[] two = data[1].trim().split(" ");
        Course first;
        Course second;
        if(one.length == 4 && two.length == 4){
            if(one[2].equals("TUT") && two[2].equals("TUT")){
                first = new Course(one[0], Integer.parseInt(one[1]), Integer.parseInt(one[3]), false);
                second = new Course(two[0], Integer.parseInt(two[1]), Integer.parseInt(two[3]), false);
                CoursePair cp = new CoursePair(first,second);
                this.not_compatible.add(cp);
            }
            else if(one[2].equals("LAB") && two.equals("LAB")){
                first = new Course(one[0], Integer.parseInt(one[1]), Integer.parseInt(one[3]), false);
                second = new Course(two[0], Integer.parseInt(two[1]), Integer.parseInt(two[3]), false);
                first.tutorial_num = -1 * first.tutorial_num;
                second.tutorial_num = -1 * second.tutorial_num;
                CoursePair cp = new CoursePair(first,second);
                this.not_compatible.add(cp);
            }
            else{
                first = new Course(one[0], Integer.parseInt(one[1]), Integer.parseInt(one[3]), true);
                second = new Course(two[0], Integer.parseInt(two[1]), Integer.parseInt(two[3]), true);
                CoursePair cp = new CoursePair(first,second);
                this.not_compatible.add(cp);
            }
        }
        else if(one.length == 6 && two.length == 6){
            //Unsure about whether the boolean here should be true or false
            first = new Course(one[0], Integer.parseInt(one[1]), Integer.parseInt(one[3]), Integer.parseInt(one[5]), false);
            second = new Course(two[0], Integer.parseInt(two[1]), Integer.parseInt(two[3]), Integer.parseInt(two[5]), false);
            if(one[4].equals("LAB") && two[4].equals("LAB")){
                first.tutorial_num = -1 * first.tutorial_num;
                second.tutorial_num = -1 * second.tutorial_num;
            }
            CoursePair cp = new CoursePair(first,second);
            this.not_compatible.add(cp);
        }
    }
    public void parse_unwanted(String line) {
        if(line.equals("Unwanted:")) return;
        if(line.equals("")) return;
        String[] data = line.split(",");
        String[] course = data[0].trim().split(" ");
        CourseTime ct = null;
        Course tmp;
        int time = 0;
        String dt = data[1].trim();
        if(dt.equals("MO")) {
            time += 10000;
        } else if(dt.equals("TU")) {
            time += 20000;
        } else if(dt.equals("FR")) {
            time += 50000;
        } else {
            System.out.println("Day " + line.charAt(0) + line.charAt(1) + " not recognized");
        }
        String tm = data[2].trim();
        if(tm.length() > 4) {
            time += 1000* Integer.parseInt(tm.substring(0,1));
            time += 100* Integer.parseInt(tm.substring(1, 2));
            time += 10* Integer.parseInt(tm.substring(3, 4));
            time += Integer.parseInt(tm.substring(4, 5));
        }
        else {
            time += 100 * Integer.parseInt(tm.substring(0, 1));
            time += 10 * Integer.parseInt(tm.substring(2, 3));
            time += Integer.parseInt(tm.substring(3, 4));
        }
        if(course.length == 4){
            if(course[2].equals("LEC")){
                tmp = new Course(course[0], Integer.parseInt(course[1]), Integer.parseInt(course[3]), true);
                ct = new CourseTime(tmp, time);
            }
            if(course[2].equals("TUT")){
                tmp = new Course(course[0], Integer.parseInt(course[1]), Integer.parseInt(course[3]), false);
                ct = new CourseTime(tmp, time);
            }
            if(course[2].equals("LAB")){
                tmp = new Course(course[0], Integer.parseInt(course[1]), Integer.parseInt(course[3]), false);
                tmp.tutorial_num = -1 * tmp.tutorial_num;
                ct = new CourseTime(tmp, time);
            }
        }
        else if(course.length == 6){
            //Again unsure of what the boolean value should be
            tmp = new Course(course[0], Integer.parseInt(course[1]), Integer.parseInt(course[3]),Integer.parseInt(course[5]), false);
            if(course[4].equals("LAB")){
                tmp.tutorial_num = -1 * tmp.tutorial_num;
            }
            ct = new CourseTime(tmp, time);
        }
        this.unwanted.add(ct);
    }
    public void parse_preferences(String line) {
        if(line.equals("Preferences:")) return;
        if(line.equals("")) return;
        Preference pref = null;
        Course tmp = null;
        int time = 0;
        int value = 0;
        String[] data = line.split(",");
        String[] course = data[2].trim().split(" ");
        String dt = data[0].trim();
        String tm = data[1].trim();
        if(dt.equals("MO")) {
            time += 10000;
        } else if(dt.equals("TU")) {
            time += 20000;
        } else if(dt.equals("FR")) {
            time += 50000;
        } else {
            System.out.println("Day " + line.charAt(0) + line.charAt(1) + " not recognized");
        }
        if(tm.length() > 4) {
            time += 1000* Integer.parseInt(tm.substring(0,1));
            time += 100* Integer.parseInt(tm.substring(1, 2));
            time += 10* Integer.parseInt(tm.substring(3, 4));
            time += Integer.parseInt(tm.substring(4, 5));
        }
        else {
            time += 100 * Integer.parseInt(tm.substring(0, 1));
            time += 10 * Integer.parseInt(tm.substring(2, 3));
            time += Integer.parseInt(tm.substring(3, 4));
        }
        value = Integer.parseInt(data[3].trim());

        if(course.length == 4){
            tmp = new Course(course[0], Integer.parseInt(course[1]),Integer.parseInt(course[3]),true);
            if(course[2].equals("LAB")){
                tmp.tutorial_num = -1 * tmp.tutorial_num;
            }
        }
        else if(course.length == 6){
            tmp = new Course(course[0], Integer.parseInt(course[1]),Integer.parseInt(course[3]), Integer.parseInt(course[5]),true);
            if(course[4].equals("LAB")){
                tmp.tutorial_num = -1 * tmp.tutorial_num;
            }
        }
        pref = new Preference(tmp, time, value);
        this.preferences.add(pref);
    }
    public void parse_pair(String line) {
        if(line.equals("Pair:")) return;
        if(line.equals("")) return;
        String[] data = line.split(",");
        String[] one = data[0].trim().split(" ");
        String[] two = data[1].trim().split(" ");
        Course first;
        Course second;
        if(one.length == 4 && two.length == 4){
            if(one[2].equals("TUT") && two[2].equals("TUT")){
                first = new Course(one[0], Integer.parseInt(one[1]), Integer.parseInt(one[3]), false);
                second = new Course(two[0], Integer.parseInt(two[1]), Integer.parseInt(two[3]), false);
                CoursePair cp = new CoursePair(first,second);
                this.pair.add(cp);
            }
            else if(one[2].equals("LAB") && two[2].equals("LAB")){
                first = new Course(one[0], Integer.parseInt(one[1]), Integer.parseInt(one[3]), false);
                second = new Course(two[0], Integer.parseInt(two[1]), Integer.parseInt(two[3]), false);
                first.tutorial_num = -1 * first.tutorial_num;
                second.tutorial_num = -1 * second.tutorial_num;
                CoursePair cp = new CoursePair(first,second);
                this.pair.add(cp);
            }
            else{
                first = new Course(one[0], Integer.parseInt(one[1]), Integer.parseInt(one[3]), true);
                second = new Course(two[0], Integer.parseInt(two[1]), Integer.parseInt(two[3]), true);
                CoursePair cp = new CoursePair(first,second);
                this.pair.add(cp);
            }
        }
        else if(one.length == 6 && two.length == 6){
            //Unsure about whether the boolean here should be true or false
            first = new Course(one[0], Integer.parseInt(one[1]), Integer.parseInt(one[3], Integer.parseInt(one[5])), true);
            second = new Course(two[0], Integer.parseInt(two[1]), Integer.parseInt(two[3], Integer.parseInt(two[5])), true);
            if(one[4].equals("LAB") && two[4].equals("LAB")){
                first.tutorial_num = -1 * first.tutorial_num;
                second.tutorial_num = -1 * second.tutorial_num;
            }
            CoursePair cp = new CoursePair(first,second);
            this.pair.add(cp);
        }
    }
    public void parse_partial_assignments(String line) {
        if(line.equals("Partial assignments:") || line.equals("")) return;
        Course course = null;
        int time = 0;

        String[] info = line.split(", ");
        course = course_parse(info[0]);
        String timeline = info[1] + ", " + info[2];
        time = time_parse(timeline);

        CourseTime ct = new CourseTime(course, time);
        this.partial.add(ct);
    }
    public int time_parse(String line) {
        int time = 0;
        if (line.charAt(0) == 'M' && line.charAt(1) == 'O') {
            time += 10000;
        } else if (line.charAt(0) == 'T' && line.charAt(1) == 'U') {
            time += 20000;
        } else if (line.charAt(0) == 'F' && line.charAt(1) == 'R') {
            time += 50000;
        } else {
            System.out.println("Day " + line.charAt(0) + line.charAt(1) + " not recognized");
        }
        if (line.charAt(5) == ':') {
            time += 100 * Integer.parseInt(line.substring(4, 5));
            time += 10 * Integer.parseInt(line.substring(6, 7));
            time += Integer.parseInt(line.substring(7, 8));
        } else {
            if (line.charAt(4) != ' ') {
                time += 1000 * Integer.parseInt(line.substring(4, 5));
            }
            time += 100 * Integer.parseInt(line.substring(5, 6));
            time += 10 * Integer.parseInt(line.substring(7, 8));
            time += Integer.parseInt(line.substring(8, 9));
        }

        return time;
    }
    public Course course_parse(String CourseInfo) {
        String department = null;
        boolean isLecture = false;
        int courseNum = 0;
        int LectureNum = 0;
        int LabNum = 0;
        Course courseP = null;
        String[] info = CourseInfo.split(" ");
        if (info.length == 4) {
            department = info[0];
            courseNum = Integer.parseInt(info[1]);
            if (info[2] == "LEC") {
                isLecture = true;
                LectureNum = Integer.parseInt(info[3]);
                Course course = new Course(department, courseNum, LectureNum, isLecture);
				/*
				course.name = department;
				course.number = courseNum;
				course.lecture_num = LectureNum;
				course.is_lecture = isLecture;
				*/
                courseP = course;
            }
            else {
                LabNum = Integer.parseInt(info[3]);
                Course course = new Course(department, courseNum, LabNum, isLecture);
				/*
				course.name = department;
				course.number = courseNum;
				course.tut_num = LabNum;
				course.is_lecture = isLecture;
				*/
                courseP = course;
            }
        }
        if (info.length == 6) {
            department = info[0];
            courseNum = Integer.parseInt(info[1]);
            LectureNum = Integer.parseInt(info[3]);
            if (info[4] == "TUT") isLecture = false;
            if (info[4].equals("LAB")) isLecture = false;
            LabNum = Integer.parseInt(info[5]);

            Course course = new Course(department, courseNum, LectureNum, LabNum,isLecture);
			/*
			course.name = department;
			course.number = courseNum;
			course.lecture_num = LectureNum;
			course.is_lecture = isLecture;
			course.tut_num = LabNum;
			*/
            courseP = course;
        }
        return courseP;
    }
}

class CourseTime {
    public Course course;
    public int time;
    public CourseTime() {
        course = null;
        time = 0;
    }
    public CourseTime(Course c, int t) {
        course = c;
        time = t;
    }
}

class CoursePair {
    public Course first;
    public Course second;
    public CoursePair() {
        first = null;
        second = null;
    }
    public CoursePair(Course one, Course two) {
        first = one;
        second = two;
    }
}

class Preference {
    public Course course;
    public int time;
    public int value;
    public Preference() {
        course = null;
        time = 0;
        value = 0;
    }
    public Preference(Course c, int t, int val) {
        course = c;
        time = t;
        value = val;
    }
}