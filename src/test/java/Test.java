import Throwables.TimerAlreadyStartedException;
import Throwables.TimerIncompleteException;
import Throwables.TimerNotStartedException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SuppressWarnings("Duplicates")
public class Test {

    @org.junit.Test
    public void createTimerTest(){
        Timer time = new Timer();
        assertEquals(Double.MIN_VALUE, time.getStartTime());
        assertEquals(Double.MIN_VALUE, time.getEndTime());
        assertFalse(time.isStarted());
    }

    @org.junit.Test
    public void timerStartTest() throws TimerAlreadyStartedException {
        Timer time = new Timer();
        time.setStartTime();
        //Added delta for run time
        assertEquals(System.currentTimeMillis(), time.getStartTime(), 10);
        assertTrue(time.isStarted());
    }

    @org.junit.Test
    public void timerEndTest() throws TimerAlreadyStartedException, TimerNotStartedException {
        Timer time = new Timer();
        time.setStartTime();
        time.setEndTime();
        //Added delta for run time
        assertEquals(System.currentTimeMillis(), time.getEndTime(), 10);
        assertFalse(time.isStarted());
    }

    @org.junit.Test
    public void runTimerTest() throws InterruptedException, TimerAlreadyStartedException, TimerNotStartedException, TimerIncompleteException {
        Timer time = new Timer();
        time.setStartTime();
        double startTime = System.currentTimeMillis();
        Thread.sleep(100);
        time.setEndTime();
        double endTime = System.currentTimeMillis();
        double runTime = endTime - startTime;
        assertEquals(runTime, time.getTimeRun(), 10);
    }

    @org.junit.Test
    public void currentTimeTest() throws TimerAlreadyStartedException, InterruptedException, TimerNotStartedException {
        Timer time = new Timer();
        time.setStartTime();
        double startTime = System.currentTimeMillis();
        Thread.sleep(100);
        double testTime = System.currentTimeMillis() - startTime;
        assertEquals(time.getCurrentTimeRun(), testTime, 10);
    }

    @org.junit.Test
    public void timePairObjTest() {
        TimePair tp = new TimePair();
        String name = "Test";
        double time = 10.00;
        tp.setName(name);
        tp.setTime(time);
        assertEquals(tp.getName(), "Test");
        assertEquals(tp.getTime(), 10.00);
    }

    @org.junit.Test
    public void timePairToStringTest() {
        TimePair tp = new TimePair();
        String name = "Test";
        double time = 1000.00;
        tp.setName(name);
        tp.setTime(time);
        assertEquals(tp.toString(), "Test " + 1.0 + " Seconds");
    }

/*    @org.junit.Test
    public void trackingStopDetectionTest() throws TimerIncompleteException, TimerAlreadyStartedException, TimerNotStartedException {
        Tracking track = new Tracking();
        track.totalScreenTimeStart();
        track.totalScreenTimeStop();
        track.stopDetection();

        assertFalse(track.getDetection());
    }*/

    @org.junit.Test
    public void trackingPrintListTest() {
        Tracking track = new Tracking();
        ArrayList<TimePair> pairs = new ArrayList<TimePair>();
        pairs.add(new TimePair("Test", 1000.00));

        assertEquals(track.runPrintList(1000), "Listed Total Time: " + 1 + " Seconds");
    }

    @org.junit.Test
    public void trackingTotalTimeTest() throws TimerAlreadyStartedException, InterruptedException, TimerNotStartedException, TimerIncompleteException {
        Tracking track = new Tracking();
        String[] arr = {"a", "b", "c", "d", "e"};
        for (String s : arr) {
            track.title = s;
            track.time.setStartTime();
            Thread.sleep(5);
            track.time.setEndTime();
            track.addPair();
            track.resetTimer();
        }
        for (TimePair time : track.pairs) {
            time.setTime(200);
        }
        assertEquals(1, track.totalTime());
    }

    @org.junit.Test
    public void addPairTestFail() throws TimerAlreadyStartedException, TimerNotStartedException, TimerIncompleteException, InterruptedException {
        Tracking track = new Tracking();
        String[] arr = {"Test", "abc"};
        for (String s : arr) {
            track.title = s;
            track.time.setStartTime();
            Thread.sleep(5);
            track.time.setEndTime();
            track.addPair();
            track.resetTimer();
        }
        assertEquals(track.pairs.size(), 2);
    }

    @org.junit.Test
    public void addPairTestTrue() throws TimerAlreadyStartedException, TimerNotStartedException, TimerIncompleteException, InterruptedException {
        Tracking track = new Tracking();
        String[] arr = {"Test", "abc", "Test"};
        for (String s : arr) {
            track.title = s;
            track.time.setStartTime();
            Thread.sleep(5);
            track.time.setEndTime();
            track.addPair();
            track.resetTimer();
        }
        assertEquals(track.pairs.size(), 2);
    }

    @org.junit.Test
    public void initTest() {
        ApplicationGUI app = new ApplicationGUI();
        app.init();
        assertTrue(app.window.isVisible());
    }

    @org.junit.Test (expected = TimerAlreadyStartedException.class)
    public void twoTimerTest() throws TimerAlreadyStartedException {
        Timer time = new Timer();
        time.setStartTime();
        time.setStartTime();
    }

    @org.junit.Test (expected = TimerIncompleteException.class)
    public void noTimeTest() throws TimerAlreadyStartedException, TimerIncompleteException {
        Timer time = new Timer();
        time.setStartTime();
        time.getTimeRun();
    }

    @org.junit.Test (expected = TimerNotStartedException.class)
    public void stopTimerTest() throws TimerNotStartedException {
        Timer time = new Timer();
        time.setEndTime();
    }

    @org.junit.Test
    public void startStopButtonTest() throws InterruptedException {
        ApplicationGUI test = new ApplicationGUI();
        test.init();
        test.startButton.doClick();
        Thread.sleep(5000);
        test.stopButton.doClick();
        assertEquals(5.0, test.totalTime, .5);
    }
}
