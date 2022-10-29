import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Observable;
import java.util.Observer;

public class Interval implements Observer {
    private Task task;
    private LocalDateTime finalDate;
    private LocalDateTime initialDate;
    private Duration duration;
    private Clock timer;


    public Interval(Task task) {
        Clock newTimer = Clock.getInstanceClock(2);
        setTask(task);
        setInitialDate(LocalDateTime.now());
        setFinalDate(LocalDateTime.now());
        setDuration(Duration.ofSeconds(0));
        newTimer.addObserver(this);
        setTimer(newTimer);
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

    protected void acceptVisitor(Visitor v){
        v.visitInterval(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        setFinalDate(LocalDateTime.now());
        setDuration(duration.plusSeconds(timer.getSeconds()));
        Printer pi = new Printer(this.getTask().getParentProject());
        pi.visitInterval(this);
        this.task.updateFinalDate(finalDate);
        this.task.updateDuration(duration,timer);
    }
    public void stopInterval() {
        timer.deleteObserver(this);
    }
}
