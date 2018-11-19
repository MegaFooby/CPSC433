import java.util.*;

public class Scheduler {
	public static void main(String args[]) {
		
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
