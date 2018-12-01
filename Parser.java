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
			name = read.readLine();
			int counter = 0;
			while(line != null) {
				line = read.readLine();
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
			}
			
			read.close();
			filereader.close();
		}
		catch(Exception e) {
			System.out.println("Failed to open file: " + filename);
			System.out.println(e);
		}
	}
	
	public void parse_course_slots(String line) {
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
		slots.add(tmp);
	}
	public void parse_lab_slots(String line) {
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
			slots.add(tmp);
		}
	}
	public void parse_courses(String line) {
		
	}
	public void parse_labs(String line) {
		
	}
	public void parse_not_compatible(String line) {
		
	}
	public void parse_unwanted(String line) {
		
	}
	public void parse_preferences(String line) {
		
	}
	public void parse_pair(String line) {
		
	}
	public void parse_partial_assignments(String line) {
		
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
