package ku.cs.calendar.server.datasources;

import java.sql.*;
/**
 * Wiwadh Chinanuphandh
 * 5810400051
 */
public class MySQLDataSource extends DatabaseDataSource {


    public MySQLDataSource(String host, String port, String dbName) {
        this.url = "//" + host + ":" + port + "/" + dbName;
        this.createDatabase();
    }

    void connect() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection("jdbc:mysql:" + url, "root", "");
    }

    void createDatabase() {
        try {
            connect();
            DatabaseMetaData md = conn.getMetaData();
            ResultSet rs = md.getTables(null, null, "appointments", null);
            boolean appointmentsTableExist = false;
            while (rs.next()) {
                if ("appointments".equals(rs.getString(3).toLowerCase())) appointmentsTableExist = true;
            }
            if (!appointmentsTableExist) {
                String query = "CREATE TABLE appointments ( `id` INTEGER PRIMARY KEY AUTO_INCREMENT, `title` TEXT, `description` TEXT, `date` TEXT, `time` TEXT, `REPEATED` TEXT )";
                Statement statement = conn.createStatement();
                statement.execute(query);
            }
            close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


}
