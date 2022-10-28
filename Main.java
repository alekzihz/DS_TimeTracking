import static java.lang.System.exit;
import static java.lang.Thread.sleep;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        Project root = new Project("root");
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


        Time clock = Time.getIntanceTime(2);
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

        Printer printer = new Printer(root);
        clock.addObserver(printer);


        exit(0);
    }
    //ArrayList<Task> task = new ArrayList<Task>();
    //todo: corregir actualizacion duracion





}