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

}
