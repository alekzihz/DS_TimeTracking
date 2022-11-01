import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;

//importa org.json.parser;

import java.io.FileReader;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


import static java.lang.System.exit;
import static java.lang.Thread.sleep;

public class Main {
    public static Project root = null;
    public static void main(String[] args) throws InterruptedException, FileNotFoundException {

        //apendiceA();
        //apendiceB();
        //crearJSON();


        readJson();
        exit(0);
    }
    public static void apendiceA(){
        root = new Project("root");
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

        root = new Project("root");
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

    public static void readJson() throws FileNotFoundException {
        String resourceName = "file.json";



        InputStream is = Main.class.getResourceAsStream(resourceName);
        if (is == null) {
            throw new NullPointerException("Cannot find resource file " + resourceName);
        }

        JSONTokener tokener = new JSONTokener(is);
        JSONObject object = new JSONObject(tokener);
        creatingRootfromJson(object);

        //root.setDuration(Duration.ofSeconds(2));


    }

    private static void creatingRootfromJson(JSONObject object) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        root = new Project("root");
        root.setDuration(Duration.ofSeconds(object.getJSONObject("root").getInt("Duration")));
        root.setInitialDate(LocalDateTime.parse(object.getJSONObject("root").getString("InitialDate"), formatter));
        root.setDateFinal((LocalDateTime.parse(object.getJSONObject("root").getString("FinalDate"), formatter)));
        //root.setTagName(object.getJSONObject("root").getString("Name"));


        JSONArray children = object.getJSONObject("root").getJSONArray("children");
        List<Component> listChildren = new ArrayList<>();

        for (int i = 0; i < children.length(); i++) {
            JSONObject obj = children.getJSONObject(i);
            Component comp = null;
            comp = feedChildren(obj, comp);
            listChildren.add(comp);
            root.addComponent(comp);
        }

        root.setChildrenProject(listChildren);
    }

    /*

        root = new Project("root");
        Project softwareDesign= new Project("software design",root);
        Project softwareTesting = new Project("sotware testing",root);
        Project dataBase = new Project("database", root);
        Task transportation = new Task("transpotation",root);

        root.addComponent(softwareDesign);
        root.addComponent(softwareTesting);
        root.addComponent(dataBase);
        root.addComponent(transportation);
*/




    private static Component feedChildren(JSONObject o, Component comp) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        if(o.getString("Class").equals("class Task")){
            JSONArray intervals = o.getJSONArray("Intervals");

            comp= new Task(o.getString("Name"));
            System.out.println(comp.getTagName());

            if(o.getJSONArray("Intervals")!=null){

                ((Task) comp).setIntervalList(feedInterval(intervals, ((Task) comp)));

            }
        }
        else{
            comp =new Project(o.getString("Name"));
            JSONArray children = o.getJSONArray("children");
            System.out.println("tengo hijos"+ children.length());
            for (int i = 0; i < children.length(); i++) {
                Component auxComponent=null;
                ((Project) comp).addComponent(feedChildren(children.getJSONObject(i),auxComponent));
                //(feedChildren());
            }





            //System.out.println(comp.getTagName());

        }

        comp.setDuration(Duration.ofSeconds(o.getLong("Duration")));
        comp.setInitialDate(LocalDateTime.parse(o.getString("InitialDate"), formatter));
        comp.setDateFinal(LocalDateTime.parse(o.getString("FinalDate"), formatter));




        //comp.setTagName(o.getString("Name"));
        //comp.setTagName("algo");



        //o.


        return comp;
    }
//TODO Solucionar SetInterval.
    private static List<Interval> feedInterval(JSONArray addIntervals, Task task) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        List<Interval> intervals = new ArrayList<>();


        task.addInterval();
        Interval auxInterval = new Interval(task);
        for (int i = 0; i < addIntervals.length(); i++) {
            //auxInterval.setFinalDate(addIntervals.get());

            //intervals.add(i, );

            auxInterval.setDuration(Duration.ofSeconds(addIntervals.getLong(Integer.parseInt("Duration"))));
            auxInterval.setInitialDate(LocalDateTime.parse(addIntervals.getString(Integer.parseInt("InitialDate")), formatter));
            auxInterval.setFinalDate(LocalDateTime.parse(addIntervals.getString(Integer.parseInt("FinalDate")), formatter));
            intervals.add(auxInterval);
        }

        return intervals;

    }

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

        //childrenComponent.getChildrenProject();
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