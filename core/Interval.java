package core;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Observable;
import java.util.Observer;
/**
 * The class Interval implements observer and updates interval of actual task
 *
 */
public class Interval implements Observer {
    private Task task;
    private LocalDateTime finalDate;
    private LocalDateTime initialDate;
    private Duration duration;
    private Clock timer;

    private Boolean active=false;
    private final Logger log = LoggerFactory.getLogger("Interval");


    public Interval(Task task) {

        assert task!=null:"error task is null";
        Clock newTimer = core.Clock.getInstanceClock(2);
        //System.out.println(newTimer);
        setTask(task);
        setInitialDate(LocalDateTime.now());
        setFinalDate(LocalDateTime.now());
        setDuration(Duration.ofSeconds(0));
        newTimer.addObserver(this);

        setActive(true);
        setTimer(newTimer);

        assert invariant();
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    private boolean invariant(){
        if(task ==null){
            log.error("error, Task does not exist");
            return false;
        }
        return true;
    }

    public Task getTask() {
        return task;
    }

    public LocalDateTime getFinalDate() {
        return finalDate;
    }

    public LocalDateTime getInitialDate() {
        return initialDate;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public void setFinalDate(LocalDateTime finalDate) {
        this.finalDate = finalDate;
    }

    public void setInitialDate(LocalDateTime initialDate) {
        this.initialDate = initialDate;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public void setTimer(Clock timer) {
        this.timer = timer;
    }

    /**
     * Implementation of observer to update duration and final date of actual task
     * @param o object to observable
     * @param arg object that observer to the observable
     */
    @Override
    public void update(Observable o, Object arg) {
        setFinalDate(LocalDateTime.now());
        setDuration(duration.plusSeconds(timer.getSeconds()));
        Printer pi = new Printer(task.getParentProject());
        pi.visitInterval(this);
        //this.task.updateFinalDate(finalDate);
        this.task.updateDurationAndFinalDate(duration,timer,finalDate);
    }
    public void stopInterval() {
        timer.deleteObserver(this);
        setActive(false);
    }

    public JSONObject toJson() {

        JSONObject json = new JSONObject();
        json.put("class", "interval");
        //json.put("id", id);
        json.put("initialDate", initialDate==null
            ? JSONObject.NULL : task.formatter.format(initialDate));
        json.put("finalDate", finalDate==null
            ? JSONObject.NULL : task.formatter.format(finalDate));
        json.put("duration", duration.toSeconds());
        json.put("active", active);
        return json;
    }
}
