import org.apache.commons.lang.time.StopWatch;

public class Timer {

    private StopWatch watch = new StopWatch();

    public void startWatch() {
        watch.start();
    }

    public void stopWatch() {
        watch.stop();
    }

    public double getStart() {
        return watch.getStartTime();
    }

    public double totalWatchTime() {
        return watch.getTime();
    }


}
