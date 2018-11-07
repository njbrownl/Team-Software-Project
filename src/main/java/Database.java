import java.sql.*;
import java.util.ArrayList;

public class Database {

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
}

