package ru.lai.hw2_rest.repositories;

import ru.lai.hw2_rest.models.Patient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientRepository extends Repository<Patient> {
    @Override
    public Patient findById(int id) throws SQLException {
        String query = "SELECT * FROM Patients WHERE id = ?;";

        try (Connection conn = super.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToPatient(rs);
                } else {
                    return null;
                }
            }
        }
    }

    @Override
    public List<Patient> findAll() throws SQLException {
        String query = "SELECT * FROM Patients;";
        List<Patient> allPatients = new ArrayList<>();

        try (Connection conn = super.getConnection();
             PreparedStatement statement = conn.prepareStatement(query);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                allPatients.add(mapResultSetToPatient(rs));
            }
        }
        return allPatients;
    }

    @Override
    public int save(Patient entity) throws SQLException {
        String query = "INSERT INTO Patients (first_name, last_name, date_of_birth, gender) VALUES(?,?,?,?::genders);";

        try (Connection conn = super.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setString(1, entity.getFirstName());
            statement.setString(2, entity.getLastName());
            statement.setDate(3, entity.getDateOfBirth());
            statement.setString(4, entity.getGender());
            return statement.executeUpdate();
        }
    }

    @Override
    public int update(Patient entity) throws SQLException {
        String query = "UPDATE Patients SET first_name = ?, last_name = ?, date_of_birth = ?, gender = ?::genders " +
                "WHERE id = ?;";

        try (Connection conn = super.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setString(1, entity.getFirstName());
            statement.setString(2, entity.getLastName());
            statement.setDate(3, entity.getDateOfBirth());
            statement.setString(4, entity.getGender());
            statement.setInt(5, entity.getId());
            return statement.executeUpdate();
        }
    }

    @Override
    public int delete(int id) throws SQLException {
        String query = "DELETE FROM Patients WHERE id = ?;";

        try (Connection conn = super.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setInt(1, id);
            return statement.executeUpdate();
        }
    }

    private Patient mapResultSetToPatient(ResultSet rs) throws SQLException {
        Patient patient = new Patient();
        patient.setId(rs.getInt("id"));
        patient.setFirstName(rs.getString("first_name"));
        patient.setLastName(rs.getString("last_name"));
        patient.setDateOfBirth(rs.getDate("date_of_birth"));
        patient.setGender(rs.getString("gender"));
        return patient;
    }
}
