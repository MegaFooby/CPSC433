import java.util.*;

public class Scheduler {
	//time is setup with the first digit as the day and the rest is the start time
	//lecture with lecture collisions
	public static final int lectime_collisions[][] = {
		//monday
		{10800, 10800}, {10900, 10900}, {11000, 11000}, {11100, 11100},
		{11200, 11200}, {11300, 11300}, {11400, 11400}, {11500, 11500},
		{11600, 11600}, {11700, 11700}, {11800, 11800}, {11900, 11900},
		{12000, 12000},
		//tuesday
		{20800, 20800}, {20930, 20930), {21100, 21100}, {21230, 21230},
		{21400, 21400}, {21530, 21530}, {21700, 21700}, {21830, 21830}
	}
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
	}
	//lecture with tutorial collisions. lecutre times are first
	public static final int lectuttime_collisions[][] = {
		//monday
		{10800, 10800}, {10900, 10900}, {11000, 11000}, {11100, 11100},
		{11200, 11200}, {11300, 11300}, {11400, 11400}, {11500, 11500},
		{11600, 11600}, {11700, 11700}, {11800, 11800}, {11900, 11900},
		{12000, 12000},
		//monday friday
		{10800, 50800}, {10900, 50800), {11000, 51000}, {11100, 51000},
		{11200, 51200}, {11300, 51200}, {11400, 51400}, {11500, 51400},
		{11600, 51600}, {11700, 51600}, {11800, 51800}, {11900, 51800},
		//tuesday
		{20800, 20800}, {20800, 20900}, {20930, 20900}, {20930, 21000},
		{21100, 21100}, {21100, 21200}, {21230, 21200}, {21230, 21300},
		{21400, 21400}, {21400, 21500}, {21530, 21500}, {21530, 21600},
		{21700, 21700}, {21700, 21800}, {21830, 21800}, {21830, 21900}
	}
	
	public static void main(String args[]) {
		Slot solution = new Slot[
	}
	
	public static Slot[] and_tree(Slot current[], Vector<Course> unassigned) {
		//return if there is nothing left to add
		if(unassigned.size() == 0) {
			return current;
		}
		//find all possible combinations for adding 1 course
		Vector<Slot[]> possible = new Vector();
		for(int i = 0; i < current.length; i++) {
			//copy so we don't alter the current values
			Slot tmp_cur[] = new Slot[current.length];
			for(int j = 0; j < current.length; j++) {
				tmp_cur[j] = current[j].copy();
			}
			Vector<Course> tmp_unas = unassigned.clone();
			
			//pop off unassigned and call the function again
			tmp_cur[i].addCourse(tmp_unas.remove(0));
			Slot tmp[] = and_tree(tmp_cur, tmp_unas);
			if(tmp != null) {//don't add if null
				possible.add(tmp);
			}
		}
		//evaluate and find the best
		Slot solution[] = null;
		int best = Integer.MIN_VALUE;
		for(int i = 0; i < possible.size(); i++) {
			int cur_eval = evaluate(possible[i]);
			if(cur_eval > best) {
				solution = possible[i];
			}
		}
		return solution;
	}
	
	//evaluate all hard constraint violations to Integer.MIN_VALUE
	public static int evaluate(Slot[] current) {
		
	}
}

class Slot {
	public int time;
	public Vector<Course> course;
	
	public Slot(int time) {
		this.time = time;
		course = new Vector();
	}
	
	public Slot(Vector<Course> courses, int time) {
		this.time = time;
		course = courses;
	}
	
	public boolean addCourse(Course toAdd) {
		course.add(toAdd);
		return true;
	}
	
	public Slot copy() {
		return new Slot(this.course.clone(), time);
	}
}

class Course {
	public String name;
	public int number;
	public int lecture_num;
	public boolean is_lecture;//true if lecture, false if tutorial
	
	public Course(String name, int number, int lecture_num, boolean is_lecture) {
		this.name = name;
		this.number = number;
		this.lecture_num = lecture_num;
		this.is_lecture = is_lecture;
	}
}
