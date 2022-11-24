import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.format.DateTimeFormatter;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;

/**
 * The class Printer that implements visitor and observer for printing the components root
 */
public class Printer implements Visitor, Observer {
    //private static final DateTimeFormatter DATEFORMATTER;
    private static final DateTimeFormatter DATEFORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final Logger log = LoggerFactory.getLogger("Print");
    private Project tree;
    public Printer(Project root) {

        assert root!=null: "error, cannot be printed";
        setTree(root);
        assert invariant();
        //assert root!=null: "error, cannot be printed";
    }

    private boolean invariant(){
        return tree!=null;
    }


    public void setTree(Project root){
        this.tree = root;
    }
    /**
     * Print the project
     * @param project project to print
     */
    @Override
    public void visitProject(Project project) {


            String formatString = String.format("Project : %15s %30s %30s %20s", project.getTagName(),(project.getInitialDate()).format(DATEFORMATTER),(project.getDateFinal()).format(DATEFORMATTER),
                    project.getDuration().toSeconds());
            log.info(formatString);

    }
    /**
     * Print the task
     * @param task task to print
     */
    @Override
    public void visitTask(Task task) {


            String formatString = String.format("activity: %15s %30s %30s %20s", task.getTagName(),task.getInitialDate().format(DATEFORMATTER),task.getDateFinal().format(DATEFORMATTER),
                    task.getDuration().toSeconds());
            log.info(formatString);
    }


    /**
     * Print the interval
     * @param interval interval to print
     */
    @Override
    public void visitInterval(Interval interval) {

        String formatString = String.format("Interval:   %44s %30s %20s", interval.getInitialDate().format(DATEFORMATTER),interval.getFinalDate().format(DATEFORMATTER),
                interval.getDuration().toSeconds());



       log.info(formatString);

    }
    /**
     * Implementation of observer to print the tree in every interval
     * @param o object to observable
     * @param arg object that observer to the observable
     */
    @Override
    public void update(Observable o, Object arg) {
        //System.out.println(LocalDateTime.now());
        tree.acceptVisitor(this);
        this.visitProject(tree);

    }
}
