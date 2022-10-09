import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
    }
    //ArrayList<Task> task = new ArrayList<Task>();

    Timer timer = new Timer("Timer");
    System.out.println(timer);
    long delay = 1000L;
    timer.schedule(task, delay);
}