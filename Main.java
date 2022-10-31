import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;


import static java.lang.System.exit;
import static java.lang.Thread.sleep;

public class Main {
    public static Project root = new Project("root");
    public static void main(String[] args) throws InterruptedException {

        //apendiceA();
        apendiceB();
        crearJSON();
        exit(0);
    }
    public static void apendiceA(){
        Project softwareDesign= new Project("software design",root);
        Project softwareTesting = new Project("sotware testing",root);
        Project dataBase = new Project("database", root);
        Task transportation = new Task("transpotation", root);
        Project Problems = new Project("problems", softwareDesign);
        Project timeTracker = new Project("time tracker", softwareDesign);
        Task firstList = new Task("first list", Problems);
        Task secondList = new Task("second list", Problems);
        Task readHandout = new Task("read handout", timeTracker);
        Task firstMilestone = new Task("first milestone", timeTracker);

        root.addComponent(softwareDesign);
        root.addComponent(softwareTesting);
        root.addComponent(dataBase);
        root.addComponent(transportation);
        softwareDesign.addComponent(Problems);
        softwareDesign.addComponent(timeTracker);
        Problems.addComponent(firstList);
        Problems.addComponent(secondList);
        timeTracker.addComponent(readHandout);
        timeTracker.addComponent(firstMilestone);



    }
    public static void apendiceB() throws InterruptedException {
        Project softwareDesign= new Project("software design",root);
        Project softwareTesting = new Project("sotware testing",root);
        Project dataBase = new Project("database", root);
        Task transportation = new Task("transpotation", root);
        Project Problems = new Project("problems", softwareDesign);
        Project timeTracker = new Project("time tracker", softwareDesign);
        Task firstList = new Task("first list", Problems);
        Task secondList = new Task("second list", Problems);
        Task readHandout = new Task("read handout", timeTracker);
        Task firstMilestone = new Task("first milestone", timeTracker);

        root.addComponent(softwareDesign);
        root.addComponent(softwareTesting);
        root.addComponent(dataBase);
        root.addComponent(transportation);
        softwareDesign.addComponent(Problems);
        softwareDesign.addComponent(timeTracker);
        Problems.addComponent(firstList);
        Problems.addComponent(secondList);
        timeTracker.addComponent(readHandout);
        timeTracker.addComponent(firstMilestone);

        Clock clock = Clock.getInstanceClock(2);
        //Printer printer = new Printer(root);
        //clock.addObserver(printer);

        transportation.startTask();
        System.out.println("Transportation Starts");
        sleep(6000);
        transportation.stopTask();
        sleep(2000);
        System.out.println("Transportation Stops");
        firstList.startTask();
        System.out.println("First list Starts");
        sleep(6000);
        secondList.startTask();
        System.out.println("Second list Starts");
        sleep(4000);
        firstList.stopTask();
        System.out.println("First list Stops");
        sleep(2000);
        secondList.stopTask();
        System.out.println("Second list Stops");
        sleep(2000);
        transportation.startTask();
        System.out.println("Transportation Starts");
        sleep(4000);
        transportation.stopTask();
        System.out.println("Transportation Stops");

        //Printer printer = new Printer(root);
        //clock.addObserver(printer);
        //exit(0);
    }
    //ArrayList<Task> task = new ArrayList<Task>();
    //todo: corregir actualizacion duracion

    public static void crearJSON(){
        JSONObject obj= new JSONObject();
        obj.put("root", writeTreeToJSON(root));
        try {
            FileWriter file = new FileWriter("file.json");
            file.write(obj.toString());
            file.flush();
            file.close();

        } catch (IOException e) {System.out.println(e.toString());}
        System.out.println("fichero creado");

    }

    public static JSONObject writeTreeToJSON(Project root){
        //Map<Component> map = new HashMap<>();
        JSONArray list = new JSONArray();
        DateTimeFormatter DATEFORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        JSONObject jo = new JSONObject();
        jo.put("Name",root.getTagName());
        jo.put("Class", root.getClass());
        jo.put("InitialDate", root.getInitialDate().format(DATEFORMATTER));
        jo.put("FinalDate", root.getDateFinal().format(DATEFORMATTER));
        jo.put("Duration", root.getDuration().toSeconds());

        if(root.getChildrenProject()!=null){

            //System.out.println("mis hijos son "+root.getChildrenProject().size());
            for (Component i: root.getChildrenProject()){
                //System.out.println(i.getTagName());
                list.put(writeChildrenRoot(i));

            }
            jo.put("children",list);
        }
        return jo;
    }

    public static JSONObject writeChildrenRoot(Component childrenComponent){
        DateTimeFormatter DATEFORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        JSONObject itemChildren = new JSONObject();
        JSONArray listInterval = new JSONArray();
        JSONArray listComponent = new JSONArray();
        JSONObject itemsComponent = new JSONObject();

        itemChildren.put("Name",childrenComponent.getTagName());
        itemChildren.put("Class", childrenComponent.getClass());
        itemChildren.put("InitialDate", (childrenComponent.getInitialDate()==null) ? "null" : childrenComponent.getInitialDate().format(DATEFORMATTER));
        itemChildren.put("FinalDate", (childrenComponent.getDateFinal()==null) ? "null" : childrenComponent.getDateFinal().format(DATEFORMATTER));
        itemChildren.put("Duration", childrenComponent.getDuration().toSeconds());

        if(childrenComponent instanceof Task){
            for (Interval i: ((Task) childrenComponent).getIntervalList()){
                listInterval.put(writeIntervalsTask(i));
            }
            itemChildren.put("Intervals",listInterval);
        } else{
            if(((Project)childrenComponent).getChildrenProject()!=null){
                for (Component i: ((Project)childrenComponent).getChildrenProject()){
                    //System.out.println("hijos de hijos "+i.getTagName());
                    itemsComponent= writeChildrenRoot(i);
                    listComponent.put(itemsComponent);
                }
                itemChildren.put("children", listComponent);
            }
        }
        //((Project)childrenComponent).getChildrenProject();

        //listChildren.put(itemChildren);

        return itemChildren;

    }

    private static JSONObject writeIntervalsTask(Interval i) {
        DateTimeFormatter DATEFORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        JSONObject itemIntervals = new JSONObject();
        itemIntervals.put("Initial Date", i.getInitialDate().format(DATEFORMATTER));
        itemIntervals.put("Final Date", i.getFinalDate().format(DATEFORMATTER));
        itemIntervals.put("Duration", i.getDuration().toSeconds());
        itemIntervals.put("Task", i.getTask().tagName);
        return  itemIntervals;

    }



}