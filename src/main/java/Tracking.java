import Throwables.TimerAlreadyStartedException;
import Throwables.TimerIncompleteException;
import Throwables.TimerNotStartedException;

class Tracking {

    private Driver screen = new Driver();
    private String title = screen.getTitle();
    private Timer time = new Timer();

    void totalScreenTimeStart() throws TimerAlreadyStartedException {
        time.setStartTime();
        System.out.println("Start Time: " + time.getStartTime());
    }

    void totalScreenTimeStop() throws TimerNotStartedException, TimerIncompleteException {
        time.setEndTime();
        System.out.println("End Time: " + time.getEndTime());
        System.out.println("Total Time: " + time.getTimeRun() / 1000 + " Seconds");
    }
}
