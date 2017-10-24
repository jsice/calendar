package ku.cs.calendar.datasources;


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
                String query = "CREATE TABLE appointments ( `id` INTEGER PRIMARY KEY AUTOINCREMENT, `title` TEXT, `description` TEXT, `date` TEXT, `time` TEXT, `REPEATED` TEXT )";
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
