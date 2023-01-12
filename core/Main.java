package core;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


import static java.lang.System.exit;
import static java.lang.Thread.sleep;



//TODO: Solucuonar logback configutarion.

public class Main {
    public static Project root = null;
    public static Project rootA = null;
    public static void main(String[] args) throws InterruptedException {
        //apendiceA();

        //rootA.findActivityById(11);
        apendiceB();

        //testTag();
        //crearJSON();
        //readJson();
        //apendiceA();
        exit(0);
    }

    public static Project makeTreeCourses(){
        apendiceA();
        return rootA;

    }

    private static void testTag() {
        rootA = new Project("root");
        Project softwareDesign= new Project("software design",rootA);
        Project softwareTesting = new Project("sotware testing",rootA);
        Project dataBase = new Project("database", rootA);
        Task transportation = new Task("transpotation", rootA);
        Project Problems = new Project("problems", softwareDesign);
        Project timeTracker = new Project("time tracker", softwareDesign);
        Task firstList = new Task("first list", Problems);
        Task secondList = new Task("second list", Problems);
        Task readHandout = new Task("read handout", timeTracker);
        Task firstMilestone = new Task("first milestone", timeTracker);



        rootA.addComponent(softwareDesign);
        rootA.addComponent(softwareTesting);
        rootA.addComponent(dataBase);
        rootA.addComponent(transportation);
        softwareDesign.addComponent(Problems);
        softwareDesign.addComponent(timeTracker);
        Problems.addComponent(firstList);
        Problems.addComponent(secondList);
        timeTracker.addComponent(readHandout);
        timeTracker.addComponent(firstMilestone);


        softwareDesign.setTag("java");
        softwareDesign.setTag("flutter");
        softwareTesting.setTag("c++");
        softwareTesting.setTag("Java");
        softwareTesting.setTag("python");
        dataBase.setTag("SQL");
        dataBase.setTag("python");
        dataBase.setTag("C++");
        firstList.setTag("java");
        secondList.setTag("Dart");
        firstMilestone.setTag("Java");
        firstMilestone.setTag("IntelliJ");

        SearchTag test1 = new SearchTag("java");
        rootA.acceptVisitor(test1);
        SearchTag test2 = new SearchTag("JAVA");
        rootA.acceptVisitor(test2);
        SearchTag test3 = new SearchTag("intellij");
        rootA.acceptVisitor(test3);
        SearchTag test4 = new SearchTag("c++");
        rootA.acceptVisitor(test4);
        SearchTag test5 = new SearchTag("python");
        rootA.acceptVisitor(test5);
        //System.out.println("s");


    }




    public static void apendiceA(){
        rootA = new Project("root");
        Project softwareDesign= new Project("software design",rootA);
        Project softwareTesting = new Project("software testing",rootA);
        Project dataBase = new Project("database", rootA);
        Task transportation = new Task("transportation", rootA);
        Project Problems = new Project("problems", softwareDesign);
        Project timeTracker = new Project("time tracker", softwareDesign);
        Task firstList = new Task("first list", Problems);
        Task secondList = new Task("second list", Problems);
        Task readHandout = new Task("read handout", timeTracker);
        Task firstMilestone = new Task("first milestone", timeTracker);

        rootA.addComponent(softwareDesign);
        rootA.addComponent(softwareTesting);
        rootA.addComponent(dataBase);
        rootA.addComponent(transportation);
        softwareDesign.addComponent(Problems);
        softwareDesign.addComponent(timeTracker);
        Problems.addComponent(firstList);
        Problems.addComponent(secondList);
        timeTracker.addComponent(readHandout);
        timeTracker.addComponent(firstMilestone);


        SearchTag s = new SearchTag("java");


    }
    @SuppressWarnings("checkstyle:Indentation")
    /*
      Es necesario, para solucionar el delay respecto al tiempo de respuesta del reloj.
     * */


    public static void apendiceB() throws InterruptedException {

        root = new Project("root");
        Project softwareDesign= new Project("software design",root);
        Project softwareTesting = new Project("software testing",root);
        Project dataBase = new Project("database", root);
        Task transportation = new Task("transportation", root);
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


        Clock clock = core.Clock.getInstanceClock(2);


        transportation.startTask();
        //System.out.println("Transportation Starts");
        sleep(6000);
        transportation.stopTask();
        sleep(2000);
        //System.out.println("Transportation Stops");
        firstList.startTask();
        //System.out.println("First list Starts");
        sleep(6000);
        secondList.startTask();
        //System.out.println("Second list Starts");
        sleep(4000);
        firstList.stopTask();
        //System.out.println("First list Stops");
        sleep(2000);
        secondList.stopTask();
        //System.out.println("Second list Stops");
        sleep(2000);
        transportation.startTask();
        //System.out.println("Transportation Starts");
        sleep(4000);
        transportation.stopTask();
        //System.out.println("Transportation Stops");
    }
    //todo: corregir actualizacion duracion
    //todo: agregar comentarios a las demas clases.


    /**
     * Test for reading a JSON file and loading into Root Project.
     *
     */
    public static void readJson() {
        String resourceName = "file.json";

        //System.out.println("Reading Json.....");
        InputStream is = Main.class.getResourceAsStream(resourceName);
        if (is == null) {
            throw new NullPointerException("Cannot find resource file " + resourceName);
        }

        JSONTokener tokener = new JSONTokener(is);
        JSONObject object = new JSONObject(tokener);
        creatingRootfromJson(object);

        //System.out.println("Project created");

        //root.setDuration(Duration.ofSeconds(2));


    }


    /**
     * read file json and load in Project root
     * @param object tree_project saved in file json.
     */

    private static void creatingRootfromJson(JSONObject object) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        //System.out.println(object.getJSONObject("root"));
        root = new Project(object.getJSONObject("root").getString("Name"));
        root.setDuration(Duration.ofSeconds(object.getJSONObject("root").getInt("Duration")));
        root.setInitialDate(LocalDateTime.parse(object.getJSONObject("root").getString("InitialDate"), formatter));
        root.setDateFinal((LocalDateTime.parse(object.getJSONObject("root").getString("FinalDate"), formatter)));
        //root.setTagName(object.getJSONObject("root").getString("Name"));
        root.setId(object.getJSONObject("root").getInt("id"));

        JSONArray children = object.getJSONObject("root").getJSONArray("children");
        List<Component> listChildren = new ArrayList<>();


        //loop throught children root.
        for (int i = 0; i < children.length(); i++) {
            JSONObject obj = children.getJSONObject(i);
            Component comp;
            comp = feedChildren(obj, null);
            listChildren.add(comp);
            root.addComponent(comp);
        }

        //set children on project root.
        root.setChildrenProject(listChildren);

    }

    /**
     * Recursive function that will iterate over the JSON object and instantiate and cast all the Projects/Tasks accordingly
     *
     * @param o JSON object of children root.
     * @param comp used to added children-children project/task.
     */


    private static Component feedChildren(JSONObject o, Component comp) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        if(o.getString("Class").equals("class Task")){
            JSONArray intervals = o.getJSONArray("Intervals");

            comp = new Task(o.getString("Name"));

            if(o.getJSONArray("Intervals").length()!=0){

                ((Task) comp).setIntervalList(feedInterval(intervals, ((Task) comp)));

            }
        }
        else{
            comp =new Project(o.getString("Name"));
            JSONArray children = o.getJSONArray("children");

            for (int i = 0; i < children.length(); i++) {
                //firstmilestone.Component auxComponent=null;
                ((Project) comp).addComponent(feedChildren(children.getJSONObject(i),null));



                //(feedChildren());
            }
        }

        comp.setDuration(Duration.ofSeconds(o.getLong("Duration")));

        if(comp.getDuration().toSeconds()!=0){
            comp.setInitialDate(LocalDateTime.parse(o.getString("InitialDate"), formatter));
            comp.setDateFinal(LocalDateTime.parse(o.getString("FinalDate"), formatter));
        }


        return comp;
    }
//TODO Solucionar SetInterval.

    /**
     * Recursive function that will iterate over the JSON object and instantiate and cast all the Projects/Tasks accordingly
     *
     * @param intervalArrayJson JSON array of interval task.
     * @param task used to add its own intervals.
     */


    private static List<Interval> feedInterval(JSONArray intervalArrayJson, Task task) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        List<Interval> intervals = new ArrayList<>();


        //task.addInterval();
        Interval auxInterval = new Interval(task);
        for (int i = 0; i < intervalArrayJson.length(); i++) {
            //auxInterval.setFinalDate(intervalArrayJson.get());
            JSONObject obj = intervalArrayJson.getJSONObject(i);
            //intervals.add(i, );

            auxInterval.setDuration(Duration.ofSeconds(obj.getLong(("Duration"))));
            auxInterval.setInitialDate(LocalDateTime.parse(obj.getString(("Initial Date")), formatter));
            auxInterval.setFinalDate(LocalDateTime.parse(obj.getString(("Final Date")), formatter));
            intervals.add(auxInterval);
        }

        return intervals;

    }


    /**
     * create a JSON string for the project Root and write into a file
     *
     */

    public static void crearJSON(){
        JSONObject obj= new JSONObject();
        obj.put("root", writeTreeToJSON(root));
        try {
            FileWriter file = new FileWriter("file.json");
            file.write(obj.toString());
            file.flush();
            file.close();

        } catch (IOException e) {e.printStackTrace();}
        //System.out.println("file created");

    }


    /**
     * Convert the Root project  to a JSON string
     * @param root Project null to initialize.
     * @return JSONObject that contains the treeRoot structure
     */
    public static JSONObject writeTreeToJSON(Project root){
        //Map<firstmilestone.Component> map = new HashMap<>();
        JSONArray list = new JSONArray();
        DateTimeFormatter DATEFORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        JSONObject jo = new JSONObject();
        jo.put("Name",root.getTagName());
        jo.put("Class", root.getClass());
        jo.put("InitialDate", root.getInitialDate().format(DATEFORMATTER));
        jo.put("FinalDate", root.getDateFinal().format(DATEFORMATTER));
        jo.put("Duration", root.getDuration().toSeconds());
        jo.put("id", root.getId());

        if(root.getChildrenProject()!=null){
            for (Component i: root.getChildrenProject()){
                //System.out.println(i.getTagName());
                list.put(writeChildrenRoot(i));
            }
            jo.put("children",list);
        }
        return jo;
    }

    /**
     * Recursive function that will write children root on file Json.
     * @param childrenComponent receives the children root.
     */
    public static JSONObject writeChildrenRoot(Component childrenComponent){
        DateTimeFormatter DATEFORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        JSONObject itemChildren = new JSONObject();
        JSONArray listInterval = new JSONArray();
        JSONArray listComponent = new JSONArray();
        JSONObject itemsComponent;

        itemChildren.put("Name",childrenComponent.getTagName());
        itemChildren.put("Class", childrenComponent.getClass());
        itemChildren.put("InitialDate", (childrenComponent.getInitialDate()==null) ? "null" : childrenComponent.getInitialDate().format(DATEFORMATTER));
        itemChildren.put("FinalDate", (childrenComponent.getDateFinal()==null) ? "null" : childrenComponent.getDateFinal().format(DATEFORMATTER));
        itemChildren.put("Duration", childrenComponent.getDuration().toSeconds());
        itemChildren.put("id", childrenComponent.getId());


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
        return itemChildren;

    }

    /**
     * function that will write the intervals of a task on a file json.
     * @param i intervals task.
     */

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