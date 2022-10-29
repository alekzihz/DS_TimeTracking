import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

public class Clock extends Observable {
    private int seconds;
    private static Clock instanceClock;
    private static Timer timer;

    private Clock(int seconds) {
        setSeconds(seconds);
        setTimer(new Timer());
        timer.scheduleAtFixedRate(new NotifyTask(),0, this.seconds *1000);
       }

    public static synchronized Clock getInstanceClock(int seconds){
        if(instanceClock ==null){
            instanceClock =new Clock(seconds);
        }
        return instanceClock;
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
            instanceClock.setChanged();
            instanceClock.notifyObservers(this);
        }
    }
}
