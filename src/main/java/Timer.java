import Throwables.TimerAlreadyStartedException;
import Throwables.TimerIncompleteException;
import Throwables.TimerNotStartedException;


class Timer {

    private double startTime;
    private double endTime;
    private double timeRun;
    private boolean isStarted;


    Timer() {
        startTime = Double.MIN_VALUE;
        endTime = Double.MIN_VALUE;
        timeRun = Double.MIN_VALUE;
        isStarted = false;
    }

    void setStartTime() throws TimerAlreadyStartedException {
        if (startTime == Double.MIN_VALUE) {
            this.startTime = System.currentTimeMillis();
            this.isStarted = true;
        }else {
            throw new TimerAlreadyStartedException("There is a timer already running");
        }
    }

    double getStartTime() {
        return this.startTime;
    }

    void setEndTime() throws TimerNotStartedException {
        if (this.isStarted) {
            this.endTime = System.currentTimeMillis();
            this.isStarted = false;
            this.timeRun = this.endTime - this.startTime;
        }else {
            throw new TimerNotStartedException("There is no timer currently started");
        }
    }

    double getEndTime() {
        return this.endTime;
    }

    double getCurrentTimeRun() throws TimerNotStartedException {
        if (this.isStarted) {
            return System.currentTimeMillis() - this.startTime;
        }else {
            throw new TimerNotStartedException("There is no timer currently started");
        }
    }

    boolean isStarted() {
        return this.isStarted;
    }

    double getTimeRun() throws TimerIncompleteException {
        if (timeRun > Double.MIN_VALUE) {
            return this.timeRun;
        }else{
            throw new TimerIncompleteException("Timer has not been stopped yet");
        }
    }

    void setTimeRun(double d) {
        this.timeRun = timeRun + d;
    }
}
