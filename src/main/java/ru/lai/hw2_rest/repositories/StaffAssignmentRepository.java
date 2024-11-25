package ru.lai.hw2_rest.repositories;

import ru.lai.hw2_rest.models.StaffAssignment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StaffAssignmentRepository extends Repository<StaffAssignment> {
    @Override
    public StaffAssignment findById(int id) throws SQLException {
        String query = "SELECT * FROM StaffAssignments WHERE id = ?;";

        try (Connection conn = super.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToAssignment(rs);
                } else {
                    return null;
                }
            }
        }
    }

    @Override
    public List<StaffAssignment> findAll() throws SQLException {
        String query = "SELECT * FROM StaffAssignments;";
        List<StaffAssignment> allAssignments = new ArrayList<>();

        try (Connection conn = super.getConnection();
             PreparedStatement statement = conn.prepareStatement(query);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                allAssignments.add(mapResultSetToAssignment(rs));
            }
        }
        return allAssignments;
    }

    @Override
    public int save(StaffAssignment entity) throws SQLException {
        String query = "INSERT INTO StaffAssignments (doctor_id, office_id) VALUES(?, ?);";

        try (Connection conn = super.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setInt(1, entity.getDoctorId());
            statement.setInt(2, entity.getOfficeId());
            return statement.executeUpdate();
        }
    }

    @Override
    public int update(StaffAssignment entity) throws SQLException {
        int valuesUpdated = 0;

        int id = entity.getId();
        int doctorId = entity.getDoctorId();
        int officeId = entity.getOfficeId();

        if (id == 0) {return 0;}

        if (doctorId != 0) {
            String doctorIdQuery = "UPDATE StaffAssignments SET doctor_id = ? WHERE id = ?";

            try (Connection conn = super.getConnection();
                 PreparedStatement statement = conn.prepareStatement(doctorIdQuery)) {
                statement.setInt(1, doctorId);
                statement.setInt(2, id);
                valuesUpdated += statement.executeUpdate();
            }
        }

        if (officeId != 0) {
            String officeIdQuery = "UPDATE StaffAssignments SET office_id = ? WHERE id = ?";

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
        String query = "DELETE FROM StaffAssignments WHERE id = ?;";

        try (Connection conn = super.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setInt(1, id);
            return statement.executeUpdate();
        }
    }

    private StaffAssignment mapResultSetToAssignment(ResultSet rs) throws SQLException {
        StaffAssignment staffAssignment = new StaffAssignment();
        staffAssignment.setId(rs.getInt("id"));
        staffAssignment.setDoctorId(rs.getInt("doctor_id"));
        staffAssignment.setOfficeId(rs.getInt("office_id"));
        return staffAssignment;
    }
}
