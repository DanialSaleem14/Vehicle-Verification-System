package vehicleverificationsystem.dao;

import vehicleverificationsystem.database.DatabaseConnection;
import vehicleverificationsystem.models.VehicleLog;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehicleLogDAO {

    // Fetch all vehicle logs
    public List<VehicleLog> getAllLogs() {
        List<VehicleLog> logs = new ArrayList<>();
        String sql = "SELECT * FROM VehicleLogs";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                VehicleLog log = new VehicleLog(
                        rs.getInt("LogID"),
                        rs.getString("VehicleNumber"),
                        rs.getTimestamp("EntryTime"),
                        rs.getTimestamp("ExitTime")
                );
                logs.add(log);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return logs;
    }
    
    public boolean addEntryLog(String vehicleNumber, long entryTime) {
        String sql = "INSERT INTO VehicleLogs (VehicleNumber, EntryTime) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, vehicleNumber);
            stmt.setTimestamp(2, new Timestamp(entryTime));

            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Update the exit time for a vehicle log
    public boolean updateExitLog(String vehicleNumber, long exitTime) {
        String sql = "UPDATE VehicleLogs SET ExitTime = ? WHERE VehicleNumber = ? AND ExitTime IS NULL";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setTimestamp(1, new Timestamp(exitTime));
            stmt.setString(2, vehicleNumber);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ResultSet getLogsByVehicleNumber(String vehicleNumber) {
        String sql = "SELECT * FROM VehicleLogs WHERE VehicleNumber = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, vehicleNumber);
            return stmt.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
