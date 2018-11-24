import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/* Parser
* This is responsible for most of the logic for reading and storing the data from a text file to be used to design
* An optimal solution to our scheduling problem
* This allows for ease of access to all the data structures and the data within
* Takes a filename as input
 */
public class Parser {
    String name;
    List<CourseSlot<String, String, String, String>> courseSlotList= new ArrayList<>();
    List<LabSlot<String, String, String, String>> labsSlotList = new ArrayList<>();
    List<Course> courses = new ArrayList<>();
    List<Lab> labs = new ArrayList<>();
    List<NotComp> notComps = new ArrayList<>();
    List<Unwanted> unwanted = new ArrayList<>();
    List<Preference<String,Integer>> preferences = new ArrayList<>();
    List<APair> pairs = new ArrayList<>();
    List<Partial> partials = new ArrayList<>();
    public Parser(String fn){
        File file = new File(fn);

        try {
            Scanner scan = new Scanner(file);
            readFile(scan);
        }
        catch (FileNotFoundException e) {
            throw new Error("An error occurred: " + e.getMessage());
        }

    }

    public void readFile(Scanner sc){
        String line;
        while(sc.hasNextLine()){
            line = sc.nextLine();
            if(line.equals(null) || line.equals("")){
                continue;
            }
            else switch (line.trim()) {
                case "Name:":
                    this.name = sc.nextLine();
                case "Course slots:":
                    String ncs;
                    while (!(ncs = sc.nextLine()).equals("")) {
                        String[] arr = ncs.trim().split(",");
                        String day = arr[0];
                        String start = arr[1];
                        String max = arr[2];
                        String min = arr[3];
                        courseSlotList.add(new CourseSlot<>(day, start, max, min));
                    }
                case "Lab slots:":
                    String nls;
                    while (!(nls = sc.nextLine()).equals("")) {
                        String[] arr = nls.trim().split(",");
                        String day = arr[0];
                        String start = arr[1];
                        String max = arr[2];
                        String min = arr[3];
                        labsSlotList.add(new LabSlot<>(day, start, max, min));
                    }
                case "Courses:":
                    String nc;
                    while(!(nc = sc.nextLine()).equals("")){
                        courses.add(new Course(nc));
                    }
                case "Labs:":
                    String nl;
                    while (!(nl = sc.nextLine()).equals("")){
                        labs.add(new Lab(nl));
                    }
                case "Not compatible:":
                    String notc;
                    while(!(notc = sc.nextLine()).equals("")){
                        notComps.add(new NotComp(notc));
                    }
                case "Unwanted:":
                    String uw;
                    while(!(uw = sc.nextLine()).equals("")){
                        unwanted.add(new Unwanted(uw));
                    }
                case "Preferences:":
                    String pf;
                    while (!(pf = sc.nextLine()).equals("")){
                        String[] arr = pf.split(",");
                        String day = arr[0];
                        String time = arr[1];
                        String[] lecsec = arr[2].split(" ");
                        String faculty = "";
                        int cnum = -1;
                        int lsec = -1;
                        int tsec = -1;
                        if(lecsec.length == 4){
                            if(lecsec[2].trim().equals("LEC")){
                                faculty = lecsec[0];
                                cnum = Integer.parseInt(lecsec[1]);
                                lsec = Integer.parseInt(lecsec[3]);
                            }
                            else if(lecsec[2].trim().equals("TUT")){
                                faculty = lecsec[0];
                                cnum = Integer.parseInt(lecsec[1]);
                                tsec = Integer.parseInt(lecsec[3]);
                            }
                        }
                        else if(lecsec.length == 6){
                            faculty = lecsec[0];
                            cnum = Integer.parseInt(lecsec[1]);
                            lsec = Integer.parseInt(lecsec[3]);
                            tsec = Integer.parseInt(lecsec[5]);
                        }
                        int weight = Integer.parseInt(arr[3]);
                        preferences.add(new Preference<String, Integer>(day, time, faculty, cnum, lsec, tsec, weight));
                    }
                case "Pair:":
                    String pr;
                    while (!(pf = sc.nextLine()).equals("")){
                        pairs.add(new APair(pf));
                    }
                case "Partial assignments:":
                    String pa;
                    while(!(pa = sc.nextLine()).equals("")){
                        partials.add(new Partial(pa));
                    }
            }
        }
    }
}
