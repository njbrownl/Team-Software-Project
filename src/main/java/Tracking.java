import Throwables.TimerAlreadyStartedException;
import Throwables.TimerIncompleteException;
import Throwables.TimerNotStartedException;

import java.util.ArrayList;

class Tracking {

    private Driver screen = new Driver();
    private String title;
    private Timer time = new Timer();
    private boolean detection = true;

    private ArrayList<TimePair> pairs = new ArrayList<TimePair>();

    void stopDetection() throws TimerIncompleteException {
        detection = false;
        pairs.add(new TimePair(title, time.getTimeRun()));
        resetTimer();
    }

    boolean getDetection() {
        return detection;
    }

    void detectScreenChange() throws TimerNotStartedException, TimerIncompleteException, TimerAlreadyStartedException {
        title = screen.getTitle();
        while (detection) {
            if ((!title.equals(screen.getTitle())) && (!title.equals(""))) {
                totalScreenTimeStop();
                pairs.add(new TimePair(title, time.getTimeRun()));
                title = screen.getTitle();
                System.out.println(pairs.size());
                resetTimer();
                totalScreenTimeStart();
            }
            if (title.equals("")) {
                title = screen.getTitle();
            }
        }
    }

    private void resetTimer() {
        time = new Timer();
    }

    private void printList() {
        double totalTime = 0;
        for (TimePair pair : pairs) {
            System.out.println(pair.toString());
            totalTime += pair.getTime();
        }
        System.out.println("Listed Total Time: " + (totalTime / 1000) + " Seconds");
    }

    // public method used for easier testing purposes
    String runPrintList(int totalTime) {
        for (TimePair pair : pairs) {
            System.out.println(pair.toString());
            totalTime += pair.getTime();
        }
        return "Listed Total Time: " + (totalTime / 1000) + " Seconds";
    }

    double totalTime(){
        double totalTime = 0;
        for (TimePair pair : pairs) {
            totalTime += pair.getTime();
        }
        return totalTime/1000;
    }

    ArrayList<TimePair> getPairs(){
        return pairs;
    }

    void totalScreenTimeStart() throws TimerAlreadyStartedException {
        time.setStartTime();
    }

    void totalScreenTimeStop() throws TimerNotStartedException {
        time.setEndTime();
    }
}
