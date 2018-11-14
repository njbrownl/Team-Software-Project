public class TimePair {

    private String name;
    private double time;

    TimePair() {
        name = null;
        time = Double.MIN_VALUE;
    }

    TimePair(String s, double d) {
        name = s;
        time = d;
    }

    void setName(String s) {
        this.name = s;
    }

    String getName() {
        return this.name;
    }

    void setTime(double d) {
        this.time = d;
    }

    double getTime() {
        return this.time;
    }

    public String toString() { return (this.getName() + " " + this.getTime() + " Seconds"); }
}
