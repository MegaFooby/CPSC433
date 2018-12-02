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
		for(int i = 0; i < parse.slots.size(); i++) {
			System.out.println(parse.slots.get(i).time + " " + parse.slots.get(i).labmax);
		}
	}

	public Parser(String filename) {
		String line = null;

		try {
			FileReader filereader = new FileReader(filename);
			BufferedReader read = new BufferedReader(filereader);
			line = read.readLine();
			name = read.readLine();
			int counter = 0;
			while(line != null) {
				
				/*for(int i = 0; i < line.getBytes().length; i++) {
					System.out.print(line.getBytes()[i] + " ");
					if(line.getBytes()[i] == 10) {
						System.out.println();
					}
				}*/

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
					if(line.length() < linelength[counter] || line.equals("\n")) {
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
				line = read.readLine();
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
	
	public int time_parse(String line) {
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
		if(line.charAt(5) == ':') {
			time += 100* Integer.parseInt(line.substring(4, 5));
			time += 10* Integer.parseInt(line.substring(6, 7));
			time += Integer.parseInt(line.substring(7, 8));
		} else {
			if(line.charAt(4) != ' ') {
				time += 1000* Integer.parseInt(line.substring(4, 5));
			}
			time += 100* Integer.parseInt(line.substring(5, 6));
			time += 10* Integer.parseInt(line.substring(7, 8));
			time += Integer.parseInt(line.substring(8, 9));
		}
		
		return time;
	}

	public void parse_course_slots(String line) {
		if(line.equals("Course slots:") || line.equals("")) return;
		Slot tmp = new Slot(time_parse(line));
		tmp.coursemax = Integer.parseInt(line.substring(11, 12));
		tmp.coursemin = Integer.parseInt(line.substring(14, 15));
		slots.add(tmp);
	}

	public void parse_lab_slots(String line) {
		if(line.equals("Lab slots:") || line.equals("")) return;
		int time = time_parse(line);
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
			tmp.labmax = Integer.parseInt(line.substring(11, 12));
			tmp.labmin = Integer.parseInt(line.substring(14, 15));
			slots.add(tmp);
		}
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
				Course course = new Course(department, courseNum, isLecture, LabNum);
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
			LabNum = Integer.parseInt(info[5]);
				
			Course course = new Course(department, courseNum, LectureNum, isLecture, LabNum);
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
	
	public void parse_courses(String line) {
		if(line.equals("Courses:") || line.equals("")) return;
		courses.add(course_parse(line));
	}
	
	//actually can put parse_lab and parse_course together
	public void parse_labs(String line) {
		if(line.equals("Labs:") || line.equals("")) return;
		courses.add(course_parse(line));
	}

	public void parse_not_compatible(String line) {
		if(line.equals("Not compatible:") || line.equals("")) return;
		String[] pair = line.split(", ");
		
		CoursePair cp = new CoursePair(course_parse(pair[0]), course_parse(pair[1]));
		not_compatible.add(cp);
	}

	public void parse_unwanted(String line) {
		if(line.equals("Unwanted:") || line.equals("")) return;
		String cInfo = null;
		int time = 0;
		
		String[] info = line.split(", ");
		
		cInfo = info[0];
		Course course = course_parse(cInfo);
		
		String timeline = info[1] + ", " + info[2];
		time = time_parse(timeline);
		
		CourseTime ct = new CourseTime(course, time);
		unwanted.add(ct);
	}

	public void parse_preferences(String line) {
		if(line.equals("Preferences:") || line.equals("")) return;
		String[] info = null;
		Course course = null;
		Preference pf = null;
		int time = 0;
		int val = 0;
		
		info = line.split(", ");
		String timeline = info[0] + ", " + info[1];
		time = time_parse(timeline);
		course = course_parse(info[2]);
		val = Integer.parseInt(info[3]);
		
		pf = new Preference(course, time, val);
		preferences.add(pf);
	}

	public void parse_pair(String line) {
		if(line.equals("Pair:") || line.equals("")) return;
		String[] pairs = line.split(", ");
		
		CoursePair cp = new CoursePair(course_parse(pairs[0]), course_parse(pairs[1]));
		pair.add(cp);
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
		partial.add(ct);
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


class Slot {
	public int time;
	public Vector<Course> course;
	public int coursemin;
	public int coursemax;
	public int labmin;
	public int labmax;
	
	public Slot(int time) {
		this.time = time;
		course = new Vector();
		coursemax = 0;
		coursemin = 0;
		labmax = 0;
		labmin = 0;
	}

	public Slot(Vector<Course> courses, int time) {
		this.time = time;
		course = courses;
		coursemax = 0;
		coursemin = 0;
		labmax = 0;
		labmin = 0;
	}

	public Slot(Vector<Course> courses, int time, int cmin, int cmax, int lmin, int lmax) {
		this.time = time;
		course = courses;
		coursemax = cmax;
		coursemin = cmin;
		labmax = lmax;
		labmin = lmin;
	}

	public boolean addCourse(Course toAdd) {
		course.add(toAdd);
		return true;
	}

	public Slot copy() {
		return new Slot((Vector<Course>)this.course.clone(), time, coursemin, coursemax, labmin, labmax);
	}
}

class Course {
	public String name;
	public int number;
	public int lecture_num;
	public boolean is_lecture;//true if lecture, false if tutorial
	public int tut_num;
	
	
	
	public Course(String name, int number, int lecture_num, boolean is_lecture) {
		this.name = name;
		this.number = number;
		this.lecture_num = lecture_num;
		this.is_lecture = is_lecture;
	}
	
	public Course(String name, int number, int lecture_num, boolean is_lecture, int tut_num) {
		this.name = name;
		this.number = number;
		this.lecture_num = lecture_num;
		this.is_lecture = is_lecture;
		this.tut_num = tut_num;
	}
	
	public Course(String name, int number, boolean is_lecture, int tut_num) {
		this.name = name;
		this.number = number;
		this.lecture_num = lecture_num;
		this.is_lecture = is_lecture;
		this.tut_num = tut_num;
	}

	public boolean equals(Course compare) {
		return (this.name.equals(compare.name) && this.number == compare.number &&
		this.lecture_num == compare.lecture_num && this.is_lecture == compare.is_lecture);
	}
}
