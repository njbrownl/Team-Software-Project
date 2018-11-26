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
        double time = 1.0;
        tp.setName(name);
        tp.setTime(time);
        assertEquals(tp.toString(), "Test " + 1.0 + " Seconds");
    }

    @org.junit.Test
    public void testGUI() throws InterruptedException {
        ApplicationGUI sys = new ApplicationGUI();
        sys.init();
        sys.startButton.doClick();
        Thread.sleep(2000);
        sys.stopButton.doClick();
        assertEquals(2.0, sys.totalTime, .3);
    }

    @org.junit.Test
    public void testDebug() {
    }

}
