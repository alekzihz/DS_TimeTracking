import java.util.ArrayList;
import java.util.Timer;

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


        Time clock = new Time(2);
        Printer printer = new Printer(root);
        clock.addObserver(printer);

        transportation.startTask(clock);
        sleep(4000);
        transportation.stopTask();
        sleep(2000);
        firstList.startTask(clock);
        sleep(6000);
        secondList.startTask(clock);
        sleep(4000);
        firstList.stopTask();
        sleep(2000);
        secondList.stopTask();
        sleep(2000);
        transportation.startTask(clock);
        sleep(4000);
        transportation.stopTask();
    }
    //ArrayList<Task> task = new ArrayList<Task>();





}