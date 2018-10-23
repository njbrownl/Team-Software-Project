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

    void stopDetection() {
        detection = false;
    }

    void detectScreenChange() throws TimerNotStartedException, TimerIncompleteException, TimerAlreadyStartedException {
        title = screen.getTitle();
        double startTime = System.currentTimeMillis();
        while (detection) {
            if ((!title.equals(screen.getTitle())) && (!title.equals(""))) {
                totalScreenTimeStop();
                pairs.add(new TimePair(title, time.getTimeRun()));
                title = screen.getTitle();
                System.out.println(pairs.size());
                resetTimer();
                totalScreenTimeStart();
                if (pairs.size() == 5) {
                    stopDetection();
                }
            }
            if (title.equals("")) {
                title = screen.getTitle();
            }
        }
        double endTime = System.currentTimeMillis();
        printList();
        System.out.println("Actual Total Time: " + ((endTime - startTime) / 1000) + " Seconds");
    }

    private void resetTimer() {
        time = new Timer();
    }

    private void printList() {
        int totalTime = 0;
        for (TimePair pair : pairs) {
            System.out.println(pair.toString());
            totalTime += pair.getTime();
        }
        System.out.println("Listed Total Time: " + (totalTime / 1000) + " Seconds");
    }

    void totalScreenTimeStart() throws TimerAlreadyStartedException {
        time.setStartTime();
    }

    void totalScreenTimeStop() throws TimerNotStartedException {
        time.setEndTime();
    }
}
