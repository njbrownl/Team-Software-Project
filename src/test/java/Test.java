import Throwables.TimerAlreadyStartedException;
import Throwables.TimerIncompleteException;
import Throwables.TimerNotStartedException;
import java.util.ArrayList;
import java.sql.*;

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
        Thread.sleep(100);
        sys.startButton.doClick();
        Thread.sleep(1000);
        sys.stopButton.doClick();
        assertEquals(1.0, sys.totalTime, .75);
        assertEquals(1000, sys.tempList.get(0).getTime(), 750);
    }

    @org.junit.Test
    public void insertSessionDataTest() {
        ArrayList<TimePair> tp = new ArrayList<TimePair>();
        TimePair timepair = new TimePair();
        double actual = 0.0;
        timepair.setName("Test.test");
        timepair.setTime(2.50);
        tp.add(timepair);

        Database db = new Database();
        db.insertSessionData(tp);
        String data = "SELECT time FROM sessionData WHERE title = 'Test.test';";
        try {
            Connection conn = db.connect();
            PreparedStatement pstmt = conn.prepareStatement(data);
            ResultSet rs = pstmt.executeQuery();
            actual = rs.getDouble("time");
            assertEquals(2.50, actual * 1000);
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @org.junit.Test
    public void selectSessionDataTest() {
        ArrayList<TimePair> tp = new ArrayList<TimePair>();
        TimePair timepair = new TimePair();
        TimePair timepair2 = new TimePair();
        timepair.setName("Test.test");
        timepair.setTime(2.50);
        tp.add(timepair);

        Database db = new Database();
        db.clearSessionData();
        db.insertSessionData(tp);
        timepair2.setName(db.selectSessionData().toString());
        assertEquals("[Test.test 0.0025 Seconds]", timepair2.getName());
    }

    @org.junit.Test
    public void clearSessionData() {
        ArrayList<TimePair> tp = new ArrayList<TimePair>();
        TimePair timepair = new TimePair();
        timepair.setName("Test.test");
        timepair.setTime(2.50);
        tp.add(timepair);
        double actualTime = 0.0;

        Database db = new Database();
        db.insertSessionData(tp);
        db.clearSessionData();
        String data = "SELECT time FROM sessionData WHERE title = 'Test.test';";
        try {
            Connection conn = db.connect();
            PreparedStatement pstmt = conn.prepareStatement(data);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                actualTime = rs.getDouble("time");
                assertEquals("", actualTime);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
