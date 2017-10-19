package ku.cs.calendar.databases;

import ku.cs.calendar.models.Appointment;

import java.io.*;
import java.util.ArrayList;
/**
 * Wiwadh Chinanuphandh
 * 5810400051
 */
public class FileDataSource implements DataSource {

    private String filename;

    public FileDataSource(String filename) {
        this.filename = filename;
    }

    private ObjectInputStream createInputStream() {
        try {
            return new ObjectInputStream(new FileInputStream(filename));
        } catch (Exception e) {
            return null;
        }
    }

    private ObjectOutputStream createOutputStream(boolean append) {
        try {
            return new ObjectOutputStream(new FileOutputStream(filename, append));
        } catch (Exception e) {
            return null;
        }
    }

    public ArrayList<Appointment> getAllAppointments() {
        ArrayList<Appointment> aps = new ArrayList<Appointment>();
        ObjectInputStream in = createInputStream();
        if (in != null) {
            try {
                while (true) {
                    try {
                        Appointment ap = (Appointment) in.readObject();
                        aps.add(ap);
                    } catch (EOFException e) {
                        break;
                    }
                }
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return aps;
    }

    public Appointment insertAppointment(Appointment ap) {
        ArrayList<Appointment> aps = getAllAppointments();
        ObjectOutputStream in = createOutputStream(false);
        if (in != null) {
            try {
                if (aps.size() == 0) {
                    ap.setId(1);
                } else {
                    ap.setId(aps.get(aps.size() -  1).getId() + 1);
                }
                aps.add(ap);
                for (Appointment appointment: aps) {
                    in.writeObject(appointment);
                }
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return ap;
    }

    public void updateAppointment(Appointment ap) {
        ArrayList<Appointment> aps = getAllAppointments();
        ObjectOutputStream in = createOutputStream(false);
        if (in != null) {
            try {
                for (Appointment appointment: aps) {
                    if (appointment.getId() == ap.getId()) {
                        in.writeObject(ap);
                    } else {
                        in.writeObject(appointment);
                    }
                }
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public void deleteAppointment(Appointment ap) {
        ArrayList<Appointment> aps = getAllAppointments();
        ObjectOutputStream in = createOutputStream(false);
        if (in != null) {
            try {
                for (Appointment appointment: aps) {
                    if (appointment.getId() != ap.getId()) {
                        in.writeObject(appointment);
                    }
                }
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
