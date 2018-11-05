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

//        Connection c = null;
//        Statement stmt = null;
//        try {
//            Class.forName("Database");
//
//            c = DriverManager.getConnection("jdbc:sqlite:C:/Users/zmjoh/Desktop/Code/Team Software Project/SQlite/sqlite-tools-win32-x86-3250200/sqlite/tracking.db");
//            stmt = c.createStatement();
//            ResultSet rs = stmt.executeQuery("SELECT * FROM sessionData;");
//            System.out.println("Title    Time");
//            while (rs.next()) {
//                String title = rs.getString("title");
//                double time = rs.getDouble("time");
//                System.out.println(title + " " + time);
//            }
//            rs.close();
//            stmt.close();
//            c.close();
//        } catch (Exception e) {
//            System.err.println(e.getClass().getName() + ": " + e.getMessage());
//            System.exit(0);
//        }
}

