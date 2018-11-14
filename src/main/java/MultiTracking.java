import Throwables.*;
import java.util.ArrayList;

class MultiTracking {

    private Timer time = new Timer();
    private Boolean detection = null;

    private ArrayList<WindowType> originalWindows = new ArrayList<WindowType>();
    private ArrayList<WindowType> currentWindows = new ArrayList<WindowType>();
    private ArrayList<TimePair> masterList = new ArrayList<TimePair>();

    //Populates the passes ArrayList with the current visible windows
    private ArrayList<WindowType> grabWindows() {
        Driver drive = new Driver();
        return drive.newGetTitle();
    }

    //Original call to grab visible windows
    private void getOriginalWindows() {
        originalWindows = grabWindows();
    }

    //Updates the list of currently visible windows
    private void getCurrentWindows() {
        currentWindows = grabWindows();
    }

    //Method to be called when the start button is pressed
    void startDetection() throws TimerAlreadyStartedException, TimerNotStartedException, TimerIncompleteException {
        detection = true;
        startTotalTimeRun();
        detect();
    }

    //Method to be called when the stop button is pressed
    void stopDetection() throws TimerNotStartedException, TimerIncompleteException {
        detection = false;
        stopTotalTimeRun();
        addAllPairs();
    }

    //Main detection driver, detects all changes in windows that are visible
    private void detect() throws TimerAlreadyStartedException, TimerNotStartedException, TimerIncompleteException {

        getOriginalWindows();

        while (detection) {

            ArrayList<String> originalNames = new ArrayList<String>();
            ArrayList<String> newNames = new ArrayList<String>();
            ArrayList<WindowType> toBeRemoved = new ArrayList<WindowType>();

            getCurrentWindows();

            for (WindowType windowType : originalWindows) {
                originalNames.add(windowType.getTitle());
            }

            for (WindowType windowType : currentWindows) {
                newNames.add(windowType.getTitle());
            }

            for (WindowType windowType : originalWindows) {
                //Case where the window is no longer on the screen
                if (!newNames.contains(windowType.getTitle())) {
                    //System.out.println("WINDOW NO LONGER ON SCREEN");
                    stopTimer(windowType);
                    addPair(windowType);
                    toBeRemoved.add(windowType);
                }
            }

            //Case to avoid ConcurrentModificationException
            for (WindowType windowType : toBeRemoved) {
                for (int i = 0; i < originalWindows.size(); i++) {
                    if (originalWindows.get(i).getTitle().equals(windowType.getTitle())) {
                        originalWindows.remove(i);
                    }
                }
            }

            //Case where the window is a new window
            for (WindowType windowType : currentWindows) {
                if (!originalNames.contains(windowType.getTitle())) {
                    startTimer(windowType);
                    System.out.println(windowType.getTimer().isStarted());
                    originalWindows.add(windowType);
                }
            }

            originalNames.clear();
            newNames.clear();
            toBeRemoved.clear();
        }
    }

    private void startAllTimers(ArrayList<WindowType> windows) throws TimerAlreadyStartedException {
        for (WindowType windowType : windows) {
            windowType.getTimer().setStartTime();
        }
    }

    //Stops all timers in the passed ArrayList. ONLY CALLED WHEN PROGRAM STOPS
    private void stopAllTimers(ArrayList<WindowType> windows) throws TimerNotStartedException, TimerIncompleteException {
        for (WindowType windowType : windows) {
            stopTimer(windowType);
        }
    }

    //Method to stop a single window timer
    private void stopTimer(WindowType window) throws TimerNotStartedException, TimerIncompleteException {
        window.getTimer().setEndTime();
        window.getTimer().setTimeRun(475);
        System.out.println(window.getTitle() + " " + window.getTimer().getTimeRun());
    }

    //Method to start a single window timer
    private void startTimer(WindowType window) throws TimerAlreadyStartedException {
        window.getTimer().setStartTime();
    }

    //Starts current session
    private void startTotalTimeRun() throws TimerAlreadyStartedException {
        time.setStartTime();
    }

    //Stops current session timer
    private void stopTotalTimeRun() throws TimerNotStartedException {
        time.setEndTime();
        time.setTimeRun(300);
    }

    //Responsible for adding Title/Time pairs and checking to see if the Title is already in the list
    private void addPair(WindowType window) throws TimerIncompleteException, TimerNotStartedException {
        boolean foundPair = false;
        if (window.getTimer().isStarted()) {
            window.getTimer().setEndTime();
        }
        TimePair tempPair = new TimePair(window.getTitle(), window.getTimer().getTimeRun());

        for (TimePair timePair : masterList) {
            if (timePair.getName().equals(tempPair.getName())) {
                foundPair = true;
                timePair.setTime(timePair.getTime() + tempPair.getTime());
            }
        }
        if (!foundPair) {
            masterList.add(tempPair);
        }
    }

    //Adds all currently visible windows to the master list. ONLY CALLED WHEN PROGRAM STOPS
    private void addAllPairs() throws TimerNotStartedException, TimerIncompleteException {
        stopAllTimers(originalWindows);
        for (WindowType windowType : originalWindows) {
            addPair(windowType);
        }
    }

    //Helper method to return total session time to other methods
    double getTotalTimeRunSession() throws TimerIncompleteException {
        return (time.getTimeRun() / 1000);
    }

    //Helper method to return the list of pairs to other methods
    ArrayList<TimePair> getMasterList() {
        return masterList;
    }

    //Helper method to return the status of detection
    boolean getDetection() throws NullDetectionException {
        if (detection == null) {
            throw new NullDetectionException("Detection has not been run yet");
        }else {
            return detection;
        }
    }

}
