import java.util.Observable;
import java.util.Observer;

public class Printer implements Visitor, Observer {

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

        String formatString = String.format("Project %4s       child of %4s %40s %40s %20s", project.getTagName(),project.getParentTagName(),project.getInitialDate(),project.getDateFinal(),
                project.getDuration());
        System.out.println(formatString);


        //System.out.println("Project "+ project.getTagName() + " child of "+project.getParentTagName()+"%15s" +project.getInitialDate()+"%15s"+project.getFinalDate()+ " "
        //            +project.getDurationSeconds());


    }
    @Override
    public void visitTask(Task task) {
        String formatString = String.format("Task %7s       child of %4s %40s %40s %20s", task.getTagName(),task.getParentTagName(),task.getInitialDate(),task.getDateFinal(),
                task.getDuration());
        System.out.println(formatString);



        //System.out.println("Task " + task.getTagName() + " child of " + task.getParentProject().getTagName() + " " + task.getInitialDate() + " " + task.getFinalDate() + " "
        //        + task.getDurationSeconds());
    }



    @Override
    public void visitInterval(Interval interval) {

        String formatString = String.format("Interval %3s       child of %4s %40s %40s %20s", interval.getTask().getTagName(),interval.getTask().getParentTagName(),interval.getInitialDate(),interval.getFinalDate(),
                interval.getDuration().toSeconds());
        System.out.println(formatString);

    }

    @Override
    public void update(Observable o, Object arg) {
        //System.out.println(LocalDateTime.now());
        tree.acceptVisitor(this);
        //this.visitProject(tree);

    }
}
