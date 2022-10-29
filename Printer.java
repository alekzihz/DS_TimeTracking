import java.time.format.DateTimeFormatter;
import java.util.Observable;
import java.util.Observer;

public class Printer implements Visitor, Observer {
    //private static final DateTimeFormatter DATEFORMATTER;
    private static final DateTimeFormatter DATEFORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");



    Project tree;
    public Printer(Project root) {
        setTree(root);
    }

    public Printer() {

    }


    public void setTree(Project root){
        this.tree = root;
    }
    @Override
    public void visitProject(Project project) {


            String formatString = String.format("Project : %15s %30s %30s %20s", project.getTagName(),(project.getInitialDate()).format(DATEFORMATTER),(project.getDateFinal()).format(DATEFORMATTER),
                    project.getDuration().toSeconds());
            System.out.println(formatString);



        //System.out.println("Project "+ project.getTagName() + " child of "+project.getParentTagName()+"%15s" +project.getInitialDate()+"%15s"+project.getFinalDate()+ " "
        //            +project.getDurationSeconds());


    }
    @Override
    public void visitTask(Task task) {


            String formatString = String.format("activity: %15s %30s %30s %20s", task.getTagName(),task.getInitialDate().format(DATEFORMATTER),task.getDateFinal().format(DATEFORMATTER),
                    task.getDuration().toSeconds());
            System.out.println(formatString);

        //System.out.println("Task " + task.getTagName() + " child of " + task.getParentProject().getTagName() + " " + task.getInitialDate() + " " + task.getFinalDate() + " "
        //        + task.getDurationSeconds());
    }



    @Override
    public void visitInterval(Interval interval) {

        String formatString = String.format("Interval:   %44s %30s %20s", interval.getInitialDate().format(DATEFORMATTER),interval.getFinalDate().format(DATEFORMATTER),
                interval.getDuration().toSeconds());
        System.out.println(formatString);

    }

    @Override
    public void update(Observable o, Object arg) {
        //System.out.println(LocalDateTime.now());
        tree.acceptVisitor(this);
        this.visitProject(tree);

    }
}
