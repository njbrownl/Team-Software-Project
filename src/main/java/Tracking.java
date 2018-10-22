public class Tracking {

    Driver screen = new Driver();

    String title = screen.getTitle();

    Timer time = new Timer();

    public void test(){
        time.startWatch();
        System.out.println("Start Time: " + time.getStart());


        time.stopWatch();

        System.out.println("Total Time:" + time.totalWatchTime());
    }

    public void totalScreenTimeStart(){
        time.startWatch();
        System.out.println("Start Time: " + time.getStart());
    }

    public void totalScreenTimeStop(){
        time.stopWatch();
        System.out.println("Total Time:" + time.totalWatchTime());
    }
}
