import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Observable;
import java.util.Observer;

public class Interval implements Observer {
    private Task task;
    private LocalDateTime finalDate;
    private LocalDateTime initalDate;
    private Duration duration;
    private Time timer;


    public Interval(Time newTimer, Task task) {
        setTask(task);
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
        return initalDate;
    }

    public Duration getDuration() {
        return duration;
    }

    public Time getTimer() {
        return timer;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public void setFinalDate(LocalDateTime finalDate) {
        this.finalDate = finalDate;
    }

    public void setInitalDate(LocalDateTime initalDate) {
        this.initalDate = initalDate;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public void setTimer(Time timer) {
        this.timer = timer;
    }

    protected void acceptVisitor(Visitor v){
        v.visitInterval(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        setFinalDate(LocalDateTime.now());
        setDuration(duration.plusSeconds(timer.getSeconds()));

        this.task.updateFinalDate(finalDate);
        this.task.updateDuration(duration);


    }
}
