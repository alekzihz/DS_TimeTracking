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
        parentProject.setDateFinal(newFinalDate);
    }

    @Override
    void updateDuration(Duration newDuration, Time newTimer) {
        setDuration(newDuration);
        parentProject.setDuration(newDuration);
    }

    @Override
    protected void acceptVisitor(Visitor v) {
        v.visitTask(this);
        for(Interval i: intervalList){
            i.acceptVisitor(v);
        }

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
    public void startTask( Time newTime){
        if(this.initialDate==null){
           setInitialDate(LocalDateTime.now());
           this.parentProject.setInitialDate(LocalDateTime.now());
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
