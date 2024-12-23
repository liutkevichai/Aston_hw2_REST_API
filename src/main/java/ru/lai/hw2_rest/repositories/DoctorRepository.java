package ru.lai.hw2_rest.repositories;

import ru.lai.hw2_rest.models.Doctor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DoctorRepository extends Repository<Doctor> {
    @Override
    public Doctor findById(int id) throws SQLException {
        String query = "SELECT * FROM Doctors WHERE id = ?;";

        try (Connection conn = super.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToDoctor(rs);
                } else {
                    return null;
                }
            }
        }
    }

    @Override
    public List<Doctor> findAll() throws SQLException {
        String query = "SELECT * FROM Doctors;";
        List<Doctor> allDoctors = new ArrayList<>();

        try (Connection conn = super.getConnection();
             PreparedStatement statement = conn.prepareStatement(query);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                allDoctors.add(mapResultSetToDoctor(rs));
            }
        }
        return allDoctors;
    }

    @Override
    public int save(Doctor entity) throws SQLException {
        String query = "INSERT INTO Doctors (first_name, last_name, specialization, years_of_experience) " +
                "VALUES(?,?,?,?);";

        try (Connection conn = super.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setString(1, entity.getFirstName());
            statement.setString(2, entity.getLastName());
            statement.setString(3, entity.getSpecialization());
            statement.setInt(4, entity.getYearsOfExperience());
            return statement.executeUpdate();
        }
    }

    @Override
    public int update(Doctor entity) throws SQLException {
        int valuesUpdated = 0;

        int id = entity.getId();
        String firstName = entity.getFirstName();
        String lastName = entity.getLastName();
        String specialization = entity.getSpecialization();
        int yearsOfExperience = entity.getYearsOfExperience();

        if (id == 0) {return 0;}

        if (firstName != null) {
            String firstNameQuery = "UPDATE Doctors SET first_name = ? WHERE id = ?";

            try (Connection conn = super.getConnection();
                 PreparedStatement statement = conn.prepareStatement(firstNameQuery)) {
                statement.setString(1, firstName);
                statement.setInt(2, id);
                valuesUpdated += statement.executeUpdate();
            }
        }

        if (lastName != null) {
            String lastNameQuery = "UPDATE Doctors SET last_name = ? WHERE id = ?";

            try (Connection conn = super.getConnection();
                 PreparedStatement statement = conn.prepareStatement(lastNameQuery)) {
                statement.setString(1, lastName);
                statement.setInt(2, id);
                valuesUpdated += statement.executeUpdate();
            }
        }

        if (specialization != null) {
            String specializationQuery = "UPDATE Doctors SET specialization = ? WHERE id = ?";

            try (Connection conn = super.getConnection();
                 PreparedStatement statement = conn.prepareStatement(specializationQuery)) {
                statement.setString(1, specialization);
                statement.setInt(2, id);
                valuesUpdated += statement.executeUpdate();
            }
        }

        if (yearsOfExperience != 0) {
            String yearsOfExperienceQuery = "UPDATE Doctors SET years_of_experience = ? WHERE id = ?";

            try (Connection conn = super.getConnection();
                 PreparedStatement statement = conn.prepareStatement(yearsOfExperienceQuery)) {
                statement.setInt(1, yearsOfExperience);
                statement.setInt(2, id);
                valuesUpdated += statement.executeUpdate();
            }
        }
        return valuesUpdated > 0 ? 1 : 0;
    }

    @Override
    public int delete(int id) throws SQLException {
        String query = "DELETE FROM Doctors WHERE id = ?;";

        try (Connection conn = super.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setInt(1, id);
            return statement.executeUpdate();
        }
    }

    private Doctor mapResultSetToDoctor(ResultSet rs) throws SQLException {
        Doctor doctor = new Doctor();
        doctor.setId(rs.getInt("id"));
        doctor.setFirstName(rs.getString("first_name"));
        doctor.setLastName(rs.getString("last_name"));
        doctor.setSpecialization(rs.getString("specialization"));
        doctor.setYearsOfExperience(rs.getInt("years_of_experience"));
        return doctor;
    }

}
