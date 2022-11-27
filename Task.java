import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


/**
 * class Task inherits from Component. It saves its intervals in a array.
 *  and has functions to start/stop them. It updates all of its parentsComponents with new information from
 *  the observable Clock.
 * **/

public class Task extends Component{
    protected List<Interval> intervalList=new ArrayList<>();
    private final Logger log = LoggerFactory.getLogger("Task");

    public Task(String tagName, Project parentProject){
        super(tagName,parentProject);
        assert invariant();
        log.info("adding task "+tagName);
        log.debug("Adding Task "+ tagName + "to project " + this.parentProject.getTagName());

    }
    public Task (String tagName){
        super(tagName);
        assert invariant();
        log.info("adding task "+tagName);
    }


    private boolean invariant(){
        if(this.getTagName().equals("") || this.getTagName().equals(null) || this ==null){
            log.error("error, Task must have a name");
            return false;
        }
        return true;
    }


    /**
     * updates Duration  of the task actual.
     * @param newDuration Duration new from observable.
     * @param clock clock of the interval task used for update its parentProject.
     * @param newFinalDate Data Final new from observable.
     * and printing the task with the attributed updated.
     * */


    @Override
    void updateDurationAndFinalDate(Duration newDuration, Clock clock, LocalDateTime newFinalDate) {
        assert clock!=null || newDuration!=null || newFinalDate!=null: "error updating datas";
        assert invariant();
        setDateFinal(newFinalDate);
        if(this.duration.getSeconds()!=0){
            assert clock!=null:"clock is not started";
            setDuration(this.duration.plusSeconds(clock.getSeconds()));
        }else{
            setDuration(newDuration);}

        Printer pi = new Printer(this.getParentProject());
        pi.visitTask(this);
        parentProject.updateDurationAndFinalDate(duration,clock, newFinalDate);

        assert this!=null:"error, updating task";
        log.warn("it could not update this task");
        assert invariant();

    }



    /**
     * @param v type visitor for visiting the task
     */

    @Override
    protected void acceptVisitor(Visitor v) {
        assert v !=null: "error, visit task cannot be null";
        assert invariant();


        v.visitTask(this);

        assert this !=null: "error visiting task";
        assert invariant();
    }
    public List<Interval> getIntervalList() {
        return intervalList;
    }
    public void setIntervalList(List<Interval> intervalList) {
        this.intervalList = intervalList;
    }

    /**
    * initialize a new interval and addes it to the arraylist "intervalList"
    * */
    public void addInterval(){

        assert this !=null: "error task " + this.getTagName() + "could not been started";
        assert invariant();
        Interval startInterval= new Interval(this);
        intervalList.add(startInterval);

        assert intervalList.size()>0 : "error adding interval";
        assert invariant();
    }

    /**
     * initialize the initial Date of the task and its parentProject.
     * */
    public void startTask(){
        assert invariant();
        if(this.initialDate==null){
           setInitialDate(LocalDateTime.now());
           this.parentProject.updateInitialDate(LocalDateTime.now());
        }
        addInterval();
        log.info(this.tagName+" Starts");
        assert invariant();

    }

    /**
     * Remove the actual interval from the arraylist "intervalList" and stops it
     */
    public void stopTask(){
        assert intervalList.size()>0: "error, "+ "Task: "+this.getTagName()+ " has not been started";
        assert invariant();

        Interval stop=intervalList.get(intervalList.size()-1);
        stop.stopInterval();
        log.info(this.tagName+ " Stops");

        assert this !=null: "error stopping task "+ this.getTagName();
        assert invariant();
    }

}
