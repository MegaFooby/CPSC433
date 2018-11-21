import java.util.*;

public class Scheduler {
	//time is setup with the first digit as the day and the rest is the start time
	//lecture with lecture collisions
	public static HashMap<Integer, Integer> lectime_collisions = new HashMap(21);
	public static HashMap<Integer, Integer> tuttime_collisions = new HashMap(31);
	public static HashMap<Integer, Integer> lectuttime_collisions = new HashMap(41);
	
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
		10800, 11000, 11200, 11400, 11600, 11800
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
		
		
		Vector<Course> to_assign = new Vector();
		Fact foo = new Fact(to_assign);
		//parse input file
		Fact solution = and_tree(foo);
		//print or save solution
	}
	
	public static Fact and_tree(Fact current) {
		//return if there is nothing left to add
		if(current.unassigned.size() == 0) {
			return current;
		}
		//find all possible combinations for adding 1 course
		Vector<Fact> possible = new Vector();
		for(int i = 0; i < current.slots.length; i++) {
			//copy so we don't alter the current values
			Fact tmp_cur = current.copy();
			tmp_cur.assign(i, 0);
			Fact tmp = and_tree(tmp_cur);
			if(tmp != null) {//don't add if null
				possible.add(tmp);
			}
		}
		//evaluate and find the best
		Fact solution = null;
		for(int i = 0; i < possible.size(); i++) {
			evaluate(possible.get(i));
			if(possible.get(i).score > solution.score) {
				solution = possible.get(i);
			}
		}
		return solution;
	}
	
	//evaluate all hard constraint violations to Integer.MIN_VALUE
	public static void evaluate(Fact current) {
		
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
		return new Slot((Vector<Course>)this.course.clone(), time);
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

class Fact {
	public Slot[] slots = null;
	public int score = Integer.MIN_VALUE;
	public Vector<Course> unassigned;
	
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
	
	public void assign(int slotnum, int coursenum) {
		this.slots[slotnum].course.add(this.unassigned.remove(coursenum));
	}
}
