package ku.cs.calendar.controllers;

import ku.cs.calendar.models.Appointment;
import ku.cs.calendar.models.Date;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseManager {

    private String url = "jdbc:sqlite:calendar_appointments.db";
    private Connection conn;

    private void connect() throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection(this.url);

    }

    private void close() throws SQLException {
        if (conn != null) {
            conn.close();
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
                Appointment ap = new Appointment(new Date(Integer.parseInt(d.substring(0,2)),
                        Integer.parseInt(d.substring(2,4)), Integer.parseInt(d.substring(4))),
                        Integer.parseInt(time.substring(0,2)), Integer.parseInt(time.substring(2)));
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

    protected void insertAppointment(Appointment ap) {
        try {
            connect();
            String title = ap.getTitle();
            String description = ap.getDescription();
            String formattedDate = String.format("%02d%02d%04d", ap.getDate().getDate(), ap.getDate().getMonth(), ap.getDate().getYear());
            String formattedTime = String.format("%02d%02d", ap.getHr(), ap.getMin());
            String query = String.format("INSERT INTO Appointments(title, description, date, time) values (\"%s\",\"%s\",\"%s\",\"%s\")", title, description, formattedDate, formattedTime);
            Statement statement = conn.createStatement();
            statement.execute(query);
            close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected void updateAppointment(Appointment ap) {
        try {
            connect();
            String title = ap.getTitle();
            String description = ap.getDescription();
            String formattedDate = String.format("%02d%02d%04d", ap.getDate().getDate(), ap.getDate().getMonth(), ap.getDate().getYear());
            String formattedTime = String.format("%02d%02d", ap.getHr(), ap.getMin());
            int id = ap.getId();
            String query = String.format("UPDATE Appointments SET TITLE = \"%s\", DESCRIPTION = \"%s\", DATE = \"%s\", TIME = \"%s\" WHERE ID = %d", title, description, formattedDate, formattedTime, id);
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
