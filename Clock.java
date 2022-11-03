import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;
/**
 * The class Clock inherits from observable. It contains a NotifyTask class that will notify the observer
 */
public class Clock extends Observable {
    private int seconds;
    private static Clock instanceClock;
    private static Timer timer;
    /**
     * The constructor of the clock class
     * @param seconds Every seconds that clock will notify to its observer
     */
    private Clock(int seconds) {
        setSeconds(seconds);
        setTimer(new Timer());
        timer.scheduleAtFixedRate(new NotifyTask(),0, this.seconds *1000);
       }
    /**
     * Implementation of Singleton
     */
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

        /**
         * Inner class to notify the observers
         */

        @Override
        public void run() {
            //System.out.println(LocalDateTime.now());
            instanceClock.setChanged();
            instanceClock.notifyObservers(this);
        }
    }
}
