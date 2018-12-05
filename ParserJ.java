import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;


/* ParserJ
* This is responsible for most of the logic for reading and storing the data from a text file to be used to design
* An optimal solution to our scheduling problem
* This allows for ease of access to all the data structures and the data within
* Takes a filename as input
 */
public class ParserJ {
    String name;
    List<CourseSlot> courseSlotList= new ArrayList<>();
    List<LabSlot> labsSlotList = new ArrayList<>();
    List<Course> courses = new ArrayList<>();
    List<Lab> labs = new ArrayList<>();
    List<NotComp> notComps = new ArrayList<>();
    List<Unwanted> unwanted = new ArrayList<>();
    List<Preference> preferences = new ArrayList<>();
    List<APair> pairs = new ArrayList<>();
    List<Partial> partials = new ArrayList<>();
    public ParserJ(String fn){
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
        try {
            while (sc.hasNextLine()) {
                line = sc.nextLine();
                if (line.equals(null) | line.equals("")) {
                    continue;
                } else switch (line.trim()) {
                    case "Name:":
                        this.name = sc.nextLine();
                        break;
                    case "Course slots:":
                        String ncs;
                        while (!(ncs = sc.nextLine()).equals("")) {
                            String[] arr = ncs.trim().split(",");
                            String day = arr[0].trim();
                            String start = arr[1].trim();
                            int max = Integer.parseInt(arr[2].trim());
                            int min = Integer.parseInt(arr[3].trim());
                            courseSlotList.add(new CourseSlot(day, start, max, min));
                        }
                        break;
                    case "Lab slots:":
                        String nls;
                        while (!(nls = sc.nextLine()).equals("")) {
                            String[] arr = nls.trim().split(",");
                            String day = arr[0].trim();
                            String start = arr[1].trim();
                            int max = Integer.parseInt(arr[2].trim());
                            int min = Integer.parseInt(arr[3].trim());
                            labsSlotList.add(new LabSlot(day, start, max, min));
                        }
                        break;
                    case "Courses:":
                        String nc;
                        while (!(nc = sc.nextLine()).equals("")) {
                            courses.add(new Course(nc));
                        }
                        break;
                    case "Labs:":
                        String nl;
                        while (!(nl = sc.nextLine()).equals("")) {
                            labs.add(new Lab(nl));
                        }
                        break;
                    case "Not compatible:":
                        String notc;
                        while (!(notc = sc.nextLine()).equals("")) {
                            notComps.add(new NotComp(notc));
                        }
                        break;
                    case "Unwanted:":
                        String uw;
                        while (!(uw = sc.nextLine()).equals("")) {
                            unwanted.add(new Unwanted(uw));
                        }
                        break;
                    case "Preferences:":
                        String pf;
                        while (!(pf = sc.nextLine()).equals("")) {
                            String[] arr = pf.split(",");
                            String day = arr[0].trim();
                            String time = arr[1].trim();
                            String[] lecsec = arr[2].trim().split(" ");
                            String faculty = "";
                            int cnum = -1;
                            int lsec = -1;
                            int tsec = -1;
                            if (lecsec.length == 4) {
                                if (lecsec[2].trim().equals("LEC")) {
                                    faculty = lecsec[0].trim();
                                    cnum = Integer.parseInt(lecsec[1].trim());
                                    lsec = Integer.parseInt(lecsec[3].trim());
                                } else if (lecsec[2].trim().equals("TUT") | lecsec[2].trim().equals("LAB")) {
                                    faculty = lecsec[0];
                                    cnum = Integer.parseInt(lecsec[1].trim());
                                    tsec = Integer.parseInt(lecsec[3].trim());
                                }
                            } else if (lecsec.length == 6) {
                                faculty = lecsec[0];
                                cnum = Integer.parseInt(lecsec[1].trim());
                                lsec = Integer.parseInt(lecsec[3].trim());
                                tsec = Integer.parseInt(lecsec[5].trim());
                            }
                            int weight = Integer.parseInt(arr[3].trim());
                            preferences.add(new Preference(day, time, faculty, cnum, lsec, tsec, weight));
                        }
                        break;
                    case "Pair:":
                        String pr;
                        while (!(pf = sc.nextLine()).equals("")) {
                            pairs.add(new APair(pf));
                        }
                        break;
                    case "Partial assignments:":
                        String pa;
                        while (!(pa = sc.nextLine()).equals("")) {
                            partials.add(new Partial(pa));
                        }
                        break;
                }
            }

        } catch (NoSuchElementException e){
            sc.close();
            return;
        }
    }
}
