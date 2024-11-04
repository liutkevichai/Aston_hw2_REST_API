package ru.lai.hw2_rest.repositories;

import ru.lai.hw2_rest.models.Office;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OfficeRepository extends Repository<Office> {
    @Override
    public Office findById(int id) throws SQLException {
        String query = "SELECT * FROM Offices WHERE id = ?;";

        try (Connection conn = super.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToOffice(rs);
                } else {
                    return null;
                }
            }
        }
    }

    @Override
    public List<Office> findAll() throws SQLException {
        String query = "SELECT * FROM Offices;";
        List<Office> allOffices = new ArrayList<>();

        try (Connection conn = super.getConnection();
             PreparedStatement statement = conn.prepareStatement(query);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                allOffices.add(mapResultSetToOffice(rs));
            }
        }
        return allOffices;
    }

    @Override
    public int save(Office entity) throws SQLException {
        String query = "INSERT INTO Offices (address) VALUES(?);";

        try (Connection conn = super.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setString(1, entity.getAddress());
            return statement.executeUpdate();
        }
    }

    @Override
    public int update(Office entity) throws SQLException {
        String query = "UPDATE Offices SET address = ? WHERE id = ?;";

        try (Connection conn = super.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setString(1, entity.getAddress());
            statement.setInt(2, entity.getId());
            return statement.executeUpdate();
        }
    }

    @Override
    public int delete(int id) throws SQLException {
        String query = "DELETE FROM Offices WHERE id = ?;";

        try (Connection conn = super.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setInt(1, id);
            return statement.executeUpdate();
        }
    }

    private Office mapResultSetToOffice(ResultSet rs) throws SQLException {
        Office office = new Office();
        office.setId(rs.getInt("id"));
        office.setAddress(rs.getString("address"));
        return office;
    }
}
