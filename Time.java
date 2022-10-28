import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

public class Time extends Observable {
    private int seconds;
    private static Time intanceTime;
    private static Timer timer;

    private Time(int seconds) {
        setSeconds(seconds);
        setTimer(new Timer());
        timer.scheduleAtFixedRate(new NotifyTask(),0, this.seconds *1000);
       }

    public static synchronized Time getIntanceTime(int seconds){
        if(intanceTime ==null){
            intanceTime =new Time(seconds);
        }
        return intanceTime;
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
            intanceTime.setChanged();
            intanceTime.notifyObservers(this);
        }
    }
}
