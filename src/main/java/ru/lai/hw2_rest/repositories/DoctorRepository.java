package ru.lai.hw2_rest.repositories;

import ru.lai.hw2_rest.models.Doctor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    public void save(Doctor entity) throws SQLException {
        String query = "INSERT INTO Doctors (first_name, last_name, specialization, years_of_experience) " +
                "VALUES(?,?,?,?);";

        try (Connection conn = super.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setString(1, entity.getFirstName());
            statement.setString(2, entity.getLastName());
            statement.setString(3, entity.getSpecialization());
            statement.setInt(4, entity.getYearsOfExperience());
            statement.executeUpdate();
        }
    }

    @Override
    public void update(Doctor entity) throws SQLException {
        String query = "UPDATE Doctors SET first_name = ?, last_name = ?, specialization = ?, years_of_experience = ? " +
                "WHERE id = ?;";

        try (Connection conn = super.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setString(1, entity.getFirstName());
            statement.setString(2, entity.getLastName());
            statement.setString(3, entity.getSpecialization());
            statement.setInt(4, entity.getYearsOfExperience());
            statement.setInt(5, entity.getId());
            statement.executeUpdate();
        }
    }

    @Override
    public void delete(Doctor entity) throws SQLException {
        String query = "DELETE FROM Doctors WHERE id = ?;";

        try (Connection conn = super.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setInt(1, entity.getId());
            statement.executeUpdate();
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
