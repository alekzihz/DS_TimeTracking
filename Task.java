import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyEditor;
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
    static final Logger log = LoggerFactory.getLogger(Printer.class);

    public Task(String tagName, Project parentProject){
        super(tagName,parentProject);
        invariant();

    }
    public Task (String tagName){
        super(tagName);
        invariant();
    }


    private void invariant(){
        assert this.getTagName() != "": "error, task not created";
        assert this != null: "error, task not created";


        assert this.getTagName() != null: "error, task must have a name";
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
        setDateFinal(newFinalDate);
        if(this.duration.getSeconds()!=0){
            setDuration(this.duration.plusSeconds(clock.getSeconds()));
        }else{
            setDuration(newDuration);}

        Printer pi = new Printer(this.getParentProject());
        pi.visitTask(this);
        parentProject.updateDurationAndFinalDate(duration,clock, newFinalDate);
        }


    /**
     * @param v type visitor for visiting the task
     */

    @Override
    protected void acceptVisitor(Visitor v) {
        v.visitTask(this);
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
        Interval startInterval= new Interval(this);
        intervalList.add(startInterval);
    }

    /**
     * initialize the initial Date of the task and its parentProject.
     * */
    public void startTask(){

        if(this.initialDate==null){
           setInitialDate(LocalDateTime.now());
           this.parentProject.updateInitialDate(LocalDateTime.now());
        }
        addInterval();
        log.info(this.tagName+" Starts");

    }

    /**
     * Remove the actual interval from the arraylist "intervalList" and stops it
     */
    public void stopTask(){
        //System.out.println(this.tagName+" Stops");
        Interval stop=intervalList.get(intervalList.size()-1);
        stop.stopInterval();
        log.info(this.tagName+ " Stops");
    }

}
