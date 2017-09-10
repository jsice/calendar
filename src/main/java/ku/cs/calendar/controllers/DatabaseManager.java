package ku.cs.calendar.controllers;

import ku.cs.calendar.models.Appointment;
import ku.cs.calendar.models.Date;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseManager {

    private String url;
    private Connection conn;

    public DatabaseManager(String url) {
        this.url = url;
        this.init();
    }

    private void connect() throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection(this.url);
    }

    private void close() throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }

    private void init() {
        try {
            connect();
            DatabaseMetaData md = conn.getMetaData();
            ResultSet rs = md.getTables(null, null, "Appointments", null);
            boolean appointmentsTableExist = false;
            while (rs.next()) {
                if ("Appointments".equals(rs.getString(3))) appointmentsTableExist = true;
            }
            if (!appointmentsTableExist) {
                String query = "CREATE TABLE \"Appointments\" ( `id` INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE, `title` TEXT DEFAULT \"Untitled Appointment\", `description` TEXT DEFAULT \"No description.\", `date` TEXT, `time` TEXT, `REPEATED` INTEGER )";
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

    protected ArrayList<Appointment> getAppointmentByDate(int date, int month, int year) {
        String formattedDate = String.format("%02d%02d%04d", date, month, year);
        ArrayList<Appointment> appointments = new ArrayList<Appointment>();
        try {
            connect();
            String query = String.format("SELECT * FROM Appointments WHERE DATE = \"%s\"", formattedDate);
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String title = resultSet.getString(2);
                String description = resultSet.getString(3);
                String d = resultSet.getString(4);
                String time = resultSet.getString(5);
                int repeated = resultSet.getInt(6);
                Appointment ap = new Appointment(new Date(Integer.parseInt(d.substring(0,2)),
                        Integer.parseInt(d.substring(2,4)), Integer.parseInt(d.substring(4))),
                        Integer.parseInt(time.substring(0,2)), Integer.parseInt(time.substring(2)), repeated);
                ap.setTitle(title);
                ap.setDescription(description);
                ap.setId(id);
                appointments.add(ap);
            }
            close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return appointments;
    }

    protected ArrayList<Appointment> getAllAppointments() {
        ArrayList<Appointment> appointments = new ArrayList<Appointment>();
        try {
            connect();
            String query = "SELECT * FROM Appointments";
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String title = resultSet.getString(2);
                String description = resultSet.getString(3);
                String d = resultSet.getString(4);
                String time = resultSet.getString(5);
                int repeated = resultSet.getInt(6);
                Appointment ap = new Appointment(new Date(Integer.parseInt(d.substring(0,2)),
                        Integer.parseInt(d.substring(2,4)), Integer.parseInt(d.substring(4))),
                        Integer.parseInt(time.substring(0,2)), Integer.parseInt(time.substring(2)), repeated);
                ap.setTitle(title);
                ap.setDescription(description);
                ap.setId(id);
                appointments.add(ap);
            }
            close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return appointments;
    }

    protected Appointment insertAppointment(Appointment ap) {
        try {
            connect();
            String title = ap.getTitle();
            String description = ap.getDescription();
            String formattedDate = String.format("%02d%02d%04d", ap.getDate().getDate(), ap.getDate().getMonth(), ap.getDate().getYear());
            String formattedTime = String.format("%02d%02d", ap.getHr(), ap.getMin());
            int repeated = ap.getRepeated();
            String query = String.format("INSERT INTO Appointments(title, description, date, time, repeated) values (\"%s\",\"%s\",\"%s\",\"%s\",%d)", title, description, formattedDate, formattedTime, repeated);
            Statement statement = conn.createStatement();
            statement.execute(query);
            query = "SELECT max(ID) FROM Appointments";
            ResultSet resultSet = statement.executeQuery(query);
            int id = resultSet.getInt(1);
            ap.setId(id);
            close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return ap;
    }

    protected void updateAppointment(Appointment ap) {
        try {
            connect();
            int id = ap.getId();
            String title = ap.getTitle();
            String description = ap.getDescription();
            String formattedDate = String.format("%02d%02d%04d", ap.getDate().getDate(), ap.getDate().getMonth(), ap.getDate().getYear());
            String formattedTime = String.format("%02d%02d", ap.getHr(), ap.getMin());
            int repeated = ap.getRepeated();
            String query = String.format("UPDATE Appointments SET TITLE = \"%s\", DESCRIPTION = \"%s\", DATE = \"%s\", TIME = \"%s\", REPEATED = %d WHERE ID = %d", title, description, formattedDate, formattedTime, repeated, id);
            Statement statement = conn.createStatement();
            statement.execute(query);
            close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected void deleteAppointment(Appointment ap) {
        try {
            connect();
            int id = ap.getId();
            String query = String.format("DELETE FROM Appointments WHERE ID = %d", id);
            Statement statement = conn.createStatement();
            statement.execute(query);
            close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
