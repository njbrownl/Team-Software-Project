import Throwables.TimerAlreadyStartedException;
import Throwables.TimerIncompleteException;
import Throwables.TimerNotStartedException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
}
