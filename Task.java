import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Task extends Component{
    protected List<Interval> intervalList=new ArrayList<>();

    public Task(String tagName, Project parentProject){
        super(tagName,parentProject);

    }

    @Override
    void updateFinalDate(LocalDateTime newFinalDate) {
        setDateFinal(newFinalDate);
        parentProject.updateFinalDate(newFinalDate);
    }

    @Override
    void updateDuration(Duration newDuration, Time newTimer) {
        if(this.duration.getSeconds()!=0){
            setDuration(this.duration.plusSeconds(newTimer.getSeconds()));
        }else{
            setDuration(newDuration);}

        Printer pi = new Printer(this.getParentProject());
        pi.visitTask(this);
        parentProject.updateDuration(duration,newTimer);
        }
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
    public void addInterval(Time newTime){
        Interval startInterval= new Interval(newTime, this);
        intervalList.add(startInterval);
    }
    public void startTask(){
        Time newTime = Time.getIntanceTime(2);
        if(this.initialDate==null){
           setInitialDate(LocalDateTime.now());
           this.parentProject.updateInitialDate(LocalDateTime.now());
        }
        addInterval(newTime);
        //System.out.println(this.tagName+"Starts");

    }
    public void stopTask(){
        //System.out.println(this.tagName+" Stops");
        Interval stop=intervalList.get(intervalList.size()-1);
        stop.stopInterval();
    }

}
