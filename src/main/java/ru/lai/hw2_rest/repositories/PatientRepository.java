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
        int valuesUpdated = 0;

        int id = entity.getId();
        String firstName = entity.getFirstName();
        String lastName = entity.getLastName();
        Date dateOfBirth = entity.getDateOfBirth();
        String gender = entity.getGender();

        if (id == 0) {return 0;}

        if (firstName != null) {
            String firstNameQuery = "UPDATE Patients SET first_name = ? WHERE id = ?";

            try (Connection conn = super.getConnection();
                 PreparedStatement statement = conn.prepareStatement(firstNameQuery)) {
                statement.setString(1, firstName);
                statement.setInt(2, id);
                valuesUpdated += statement.executeUpdate();
            }
        }

        if (lastName != null) {
            String lastNameQuery = "UPDATE Patients SET last_name = ? WHERE id = ?";

            try (Connection conn = super.getConnection();
                 PreparedStatement statement = conn.prepareStatement(lastNameQuery)) {
                statement.setString(1, lastName);
                statement.setInt(2, id);
                valuesUpdated += statement.executeUpdate();
            }
        }

        if (dateOfBirth != null) {
            String dateOfBirthQuery = "UPDATE Patients SET date_of_birth = ? WHERE id = ?";

            try (Connection conn = super.getConnection();
                 PreparedStatement statement = conn.prepareStatement(dateOfBirthQuery)) {
                statement.setDate(1, dateOfBirth);
                statement.setInt(2, id);
                valuesUpdated += statement.executeUpdate();
            }
        }

        if (gender != null) {
            String genderQuery = "UPDATE Patients SET gender = ?::genders WHERE id = ?";

            try (Connection conn = super.getConnection();
                 PreparedStatement statement = conn.prepareStatement(genderQuery)) {
                statement.setString(1, gender);
                statement.setInt(2, id);
                valuesUpdated += statement.executeUpdate();
            }
        }
        return valuesUpdated > 0 ? 1 : 0;
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
