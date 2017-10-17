package ku.cs.calendar.databases;


import java.sql.*;
/**
 * Wiwadh Chinanuphandh
 * 5810400051
 */
public class SQLiteDataSource extends DatabaseDataSource {

    private String url;

    public SQLiteDataSource(String url) {
        this.url = "jdbc:sqlite:" + url;
        this.createDatabase();
    }

    void connect() throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection(this.url);
    }

//    void createDatabase() {
//        try {
//            connect();
//            DatabaseMetaData md = conn.getMetaData();
//            ResultSet rs = md.getTables(null, null, "Appointments", null);
//            boolean appointmentsTableExist = false;
//            while (rs.next()) {
//                if ("Appointments".equals(rs.getString(3))) appointmentsTableExist = true;
//            }
//            if (!appointmentsTableExist) {
//                String query = "CREATE TABLE \"Appointments\" ( `id` INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE, `title` TEXT DEFAULT \"Untitled Appointment\", `description` TEXT DEFAULT \"No description.\", `date` TEXT, `time` TEXT, `REPEATED` INTEGER )";
//                Statement statement = conn.createStatement();
//                statement.execute(query);
//            }
//            close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//
//    }


}
