import java.sql.*;
import java.util.ArrayList;

@SuppressWarnings("Duplicates")
public class Database {

    //Connect to database
    private Connection connect(){
        Connection conn = null;
        String url = "jdbc:sqlite:src/main/sqlite/tracking.db";
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    //Insert data into the session table
    void insertSessionData(ArrayList<TimePair> timePairs) {

        String data = "INSERT INTO sessionData(title, time) VALUES(?, ?);";
        String title;
        double time;

        for (TimePair pair : timePairs) {
            title = pair.getName();
            time = pair.getTime();

            try {
                Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(data);
                pstmt.setString(1, title);
                pstmt.setDouble(2, time/1000);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    //Select all data from the session table
    ArrayList<TimePair> selectSessionData() {
        ArrayList<TimePair> timePairs = new ArrayList<TimePair>();
        String data = "SELECT title, time FROM sessionData;";
        try {
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(data);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                TimePair tp = new TimePair();
                String title = rs.getString("title");
                tp.setName(title);
                double time = rs.getDouble("time");
                tp.setTime(time);
                timePairs.add(tp);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return timePairs;
    }

    //Clears all data from session table
    void clearSessionData() {
        String data = "DELETE FROM sessionData";
        try {
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(data);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //Insert data into total table
    void insertTotalData(ArrayList<TimePair> timePairs) {
        String data = "INSERT INTO totalData VALUES (?, ?) ON CONFLICT(title) DO UPDATE SET totalTime = totalTime + ?;";
        String title;
        double time;

        for (TimePair pair : timePairs) {
            title = pair.getName();
            time = pair.getTime();

            try {
                Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(data);
                pstmt.setString(1, title);
                pstmt.setDouble(2, time/1000);
                pstmt.setDouble(3, time/1000);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    //Select all data from total table
    ArrayList<TimePair> selectTotalData() {
        ArrayList<TimePair> timePairs = new ArrayList<TimePair>();
        String data = "SELECT title, totalTime FROM totalData;";
        try {
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(data);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                TimePair tp = new TimePair();
                String title = rs.getString("title");
                tp.setName(title);
                double time = rs.getDouble("totalTime");
                tp.setTime(time);
                timePairs.add(tp);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return timePairs;
    }


    //Metrics Functions

    //Most viewed application from session data
    TimePair mostTimeSession() {
        TimePair tp = new TimePair();
        String data = "SELECT title, time FROM sessionData WHERE time =(SELECT max(time) FROM sessionData);";
        try {
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(data);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String title = rs.getString("title");
                tp.setName(title);
                double time = rs.getDouble("time");
                tp.setTime(time);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tp;
    }

    //Most viewed application from total data
    TimePair mostTimeTotal() {
        TimePair tp = new TimePair();
        String data = "SELECT title, totalTime FROM totalData WHERE totalTime =(SELECT max(totalTime) FROM totalData);";
        try {
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(data);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String title = rs.getString("title");
                tp.setName(title);
                double time = rs.getDouble("totalTime");
                tp.setTime(time);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tp;
    }

    //Average time each application was viewed from session data
    double sessionAvg() {
        double avg = 0.0;
        String data = "SELECT AVG(time) FROM sessionData;";
        try {
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(data);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                avg = rs.getDouble("avg(time)");
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return avg;
    }

    //Average time each application was viewed from total data
    double totalAvg() {
        double avg = 0.0;
        String data = "SELECT AVG(totalTime) FROM totalData;";
        try {
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(data);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                avg = rs.getDouble("avg(totalTime)");
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return avg;
    }
}

