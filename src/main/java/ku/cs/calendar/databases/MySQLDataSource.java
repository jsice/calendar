package ku.cs.calendar.databases;

import java.sql.*;

public class MySQLDataSource extends DatabaseDataSource {


    public MySQLDataSource(String host, String port, String dbName) {
        this.url = "//" + host + ":" + port + "/" + dbName;
        this.createDatabase();
    }

    void connect() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection("jdbc:mysql:" + url, "root", "");
    }




}
