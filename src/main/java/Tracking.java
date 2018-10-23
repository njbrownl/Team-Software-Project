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
        while (detection) {
            if (!title.equals(screen.getTitle())) {
                totalScreenTimeStop();
                pairs.add(new TimePair(title, time.getTimeRun()));
                title = screen.getTitle();
                resetTimer();
                totalScreenTimeStart();
                if (pairs.size() == 5) {
                    stopDetection();
                }
            }
        }
        printList();
    }

    private void resetTimer() {
        time = new Timer();
    }

    private void printList() {
        for (TimePair pair : pairs) {
            System.out.println(pair.toString());
        }
    }

    void totalScreenTimeStart() throws TimerAlreadyStartedException {
        time.setStartTime();
    }

    void totalScreenTimeStop() throws TimerNotStartedException {
        time.setEndTime();
    }
}
