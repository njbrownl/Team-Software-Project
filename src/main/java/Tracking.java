import Throwables.TimerAlreadyStartedException;
import Throwables.TimerIncompleteException;
import Throwables.TimerNotStartedException;

import java.util.ArrayList;

class Tracking {

    private Driver screen = new Driver();
    String title;
    Timer time = new Timer();
    private boolean detection = true;

    ArrayList<TimePair> pairs = new ArrayList<TimePair>();

    void stopDetection() throws TimerIncompleteException {
        detection = false;
        addPair();
        resetTimer();
    }

    boolean getDetection() {
        return detection;
    }

    void addPair() throws TimerIncompleteException {
        boolean findMatch = false;
        TimePair tempPair = new TimePair(title, time.getTimeRun());
        for (TimePair pair : pairs) {
            if (tempPair.getName().equals(pair.getName())) {
                pair.setTime(tempPair.getTime() + pair.getTime());
                findMatch = true;
            }
        }
        if (!findMatch) {
            pairs.add(tempPair);
        }
    }

    void detectScreenChange() throws TimerNotStartedException, TimerIncompleteException, TimerAlreadyStartedException {
        title = screen.getTitle();
        while (detection) {
            if ((!title.equals(screen.getTitle())) && (!title.equals(""))) {
                totalScreenTimeStop();
                addPair();
                title = screen.getTitle();
                resetTimer();
                totalScreenTimeStart();
            }
            if (title.equals("")) {
                title = screen.getTitle();
            }
        }
    }

    void resetTimer() {
        time = new Timer();
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
