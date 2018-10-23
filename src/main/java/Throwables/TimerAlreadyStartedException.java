package Throwables;

public class TimerAlreadyStartedException extends Exception {

    public TimerAlreadyStartedException(){}

    public TimerAlreadyStartedException(String message) {
        super(message);
    }
}
