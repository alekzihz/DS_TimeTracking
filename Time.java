import java.time.LocalDateTime;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

public class Time extends Observable {
    private int seconds;
    private Timer timer;
    public Time(int second) {
        setSeconds(second);
        setTimer(new Timer());
        timer.scheduleAtFixedRate(new NotifyTask(),0,seconds*1000);
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }
    public class NotifyTask extends TimerTask{

        @Override
        public void run() {
            //System.out.println(LocalDateTime.now());
            setChanged();
            notifyObservers(this);
        }
    }
}
