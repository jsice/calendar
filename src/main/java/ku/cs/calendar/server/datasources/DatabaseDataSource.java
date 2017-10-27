package ku.cs.calendar.server.datasources;

import ku.cs.calendar.common.models.Appointment;
import ku.cs.calendar.common.utils.AppointmentRepeatStateUtils;
import ku.cs.calendar.common.models.Date;

import java.sql.*;
import java.util.ArrayList;
/**
 * Wiwadh Chinanuphandh
 * 5810400051
 */
public abstract class DatabaseDataSource implements DataSource {

    Connection conn;
    String url;
    abstract void connect() throws SQLException, ClassNotFoundException ;

    void close() throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }

    abstract void createDatabase();

    public ArrayList<Appointment> getAllAppointments() {
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
                String repeated = resultSet.getString(6);
                Appointment ap = new Appointment(new Date(Integer.parseInt(d.substring(0,2)),
                        Integer.parseInt(d.substring(2,4)), Integer.parseInt(d.substring(4))),
                        Integer.parseInt(time.substring(0,2)), Integer.parseInt(time.substring(2)), AppointmentRepeatStateUtils.getInstanceOfState(repeated));
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

    public Appointment insertAppointment(Appointment ap) {
        try {
            connect();
            String title = ap.getTitle();
            String description = ap.getDescription();
            String formattedDate = String.format("%02d%02d%04d", ap.getDate().getDate(), ap.getDate().getMonth(), ap.getDate().getYear());
            String formattedTime = String.format("%02d%02d", ap.getHr(), ap.getMin());
            String repeatState = AppointmentRepeatStateUtils.getNameOfState(ap.getRepeated());
            String query = String.format("INSERT INTO Appointments(title, description, date, time, repeated) values (\"%s\",\"%s\",\"%s\",\"%s\",\"%s\")", title, description, formattedDate, formattedTime, repeatState);
            Statement statement = conn.createStatement();
            statement.execute(query);
            query = "SELECT max(ID) FROM Appointments";
            ResultSet resultSet = statement.executeQuery(query);
            resultSet.next();
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

    public void updateAppointment(Appointment ap) {
        try {
            connect();
            int id = ap.getId();
            String title = ap.getTitle();
            String description = ap.getDescription();
            String formattedDate = String.format("%02d%02d%04d", ap.getDate().getDate(), ap.getDate().getMonth(), ap.getDate().getYear());
            String formattedTime = String.format("%02d%02d", ap.getHr(), ap.getMin());
            String repeatState = AppointmentRepeatStateUtils.getNameOfState(ap.getRepeated());String query = String.format("UPDATE Appointments SET TITLE = \"%s\", DESCRIPTION = \"%s\", DATE = \"%s\", TIME = \"%s\", REPEATED = \"%s\" WHERE ID = %d", title, description, formattedDate, formattedTime, repeatState, id);
            Statement statement = conn.createStatement();
            statement.execute(query);
            close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void deleteAppointment(Appointment ap) {
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
