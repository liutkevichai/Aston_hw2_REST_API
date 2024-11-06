package ru.lai.hw2_rest.repositories;

import ru.lai.hw2_rest.models.Appointment;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AppointmentRepository extends Repository<Appointment> {
    @Override
    public Appointment findById(int id) throws SQLException {
        String query = "SELECT * FROM Appointments WHERE id = ?;";

        try (Connection conn = super.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToAppointment(rs);
                } else {
                    return null;
                }
            }
        }
    }

    @Override
    public List<Appointment> findAll() throws SQLException {
        String query = "SELECT * FROM Appointments;";
        List<Appointment> allAppointments = new ArrayList<>();

        try (Connection conn = super.getConnection();
             PreparedStatement statement = conn.prepareStatement(query);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                allAppointments.add(mapResultSetToAppointment(rs));
            }
        }
        return allAppointments;
    }

    @Override
    public int save(Appointment entity) throws SQLException {
        String query = "INSERT INTO Appointments (appointment_datetime, patient_id, doctor_id, office_id) " +
                "VALUES(?,?,?,?);";

        try (Connection conn = super.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setTimestamp(1, Timestamp.valueOf(entity.getAppointmentDatetime()));
            statement.setInt(2, entity.getPatientId());
            statement.setInt(3, entity.getDoctorId());
            statement.setInt(4, entity.getOfficeId());
            return statement.executeUpdate();
        }
    }

    @Override
    public int update(Appointment entity) throws SQLException {
        int valuesUpdated = 0;

        int id = entity.getId();
        LocalDateTime appointmentDatetime = entity.getAppointmentDatetime();
        int patientId = entity.getPatientId();
        int doctorId = entity.getDoctorId();
        int officeId = entity.getOfficeId();

        if (id == 0) {return 0;}

        if (appointmentDatetime != null) {
            String dateTimeQuery = "UPDATE Appointments SET appointment_datetime = ? WHERE id = ?";

            try (Connection conn = super.getConnection();
                 PreparedStatement statement = conn.prepareStatement(dateTimeQuery)) {
                statement.setTimestamp(1, Timestamp.valueOf(appointmentDatetime));
                statement.setInt(2, id);
                valuesUpdated += statement.executeUpdate();
            }
        }

        if (patientId != 0) {
            String patientIdQuery = "UPDATE Appointments SET patient_id = ? WHERE id = ?";

            try (Connection conn = super.getConnection();
                 PreparedStatement statement = conn.prepareStatement(patientIdQuery)) {
                statement.setInt(1, patientId);
                statement.setInt(2, id);
                valuesUpdated += statement.executeUpdate();
            }
        }

        if (doctorId != 0) {
            String doctorIdQuery = "UPDATE Appointments SET doctor_id = ? WHERE id = ?";

            try (Connection conn = super.getConnection();
                 PreparedStatement statement = conn.prepareStatement(doctorIdQuery)) {
                statement.setInt(1, doctorId);
                statement.setInt(2, id);
                valuesUpdated += statement.executeUpdate();
            }
        }

        if (officeId != 0) {
            String officeIdQuery = "UPDATE Appointments SET office_id = ? WHERE id = ?";

            try (Connection conn = super.getConnection();
                 PreparedStatement statement = conn.prepareStatement(officeIdQuery)) {
                statement.setInt(1, officeId);
                statement.setInt(2, id);
                valuesUpdated += statement.executeUpdate();
            }
        }
        return valuesUpdated > 0 ? 1 : 0;
    }

    @Override
    public int delete(int id) throws SQLException {
        String query = "DELETE FROM Appointments WHERE id = ?;";

        try (Connection conn = super.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setInt(1, id);
            return statement.executeUpdate();
        }
    }

    private Appointment mapResultSetToAppointment(ResultSet rs) throws SQLException {
        Appointment appointment = new Appointment();
        appointment.setId(rs.getInt("id"));
        appointment.setAppointmentDatetime(rs.getTimestamp("appointment_datetime").toLocalDateTime());
        appointment.setPatientId(rs.getInt("patient_id"));
        appointment.setDoctorId(rs.getInt("doctor_id"));
        appointment.setOfficeId(rs.getInt("office_id"));
        return appointment;
    }

}
