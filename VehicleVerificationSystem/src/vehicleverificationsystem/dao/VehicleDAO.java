package vehicleverificationsystem.dao;

import vehicleverificationsystem.models.Vehicle;
import vehicleverificationsystem.database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;

public class VehicleDAO {

    // Add a new vehicle to the database
    public boolean addVehicle(Vehicle vehicle) {
        String sql = "INSERT INTO vehicles (registrationNum, ownerName, ownerAddress, registrationDate) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, vehicle.getRegistrationNum());
            stmt.setString(2, vehicle.getOwnerName());
            stmt.setString(3, vehicle.getOwnerAddress());
            stmt.setDate(4, Date.valueOf(vehicle.getRegistrationDate()));

            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete a vehicle from the database
    public boolean deleteVehicle(String registrationNum) {
        String sql = "DELETE FROM vehicles WHERE registrationNum = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, registrationNum);
            int rowsAffected = stmt.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Retrieve all vehicles from the database
    public ArrayList<Vehicle> getAllVehicles() {
        ArrayList<Vehicle> vehicles = new ArrayList<>();
        String sql = "SELECT * FROM vehicles";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Vehicle vehicle = new Vehicle(
                        rs.getString("registrationNum"),
                        rs.getString("ownerName"),
                        rs.getString("ownerAddress"),
                        rs.getDate("registrationDate").toString()
                );
                vehicles.add(vehicle);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vehicles;
    }

    // Find a vehicle by its registration number
    public Vehicle findVehicleByRegistrationNum(String registrationNum) {
        String sql = "SELECT * FROM vehicles WHERE registrationNum = ?";
        Vehicle vehicle = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, registrationNum);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                vehicle = new Vehicle(
                        rs.getString("registrationNum"),
                        rs.getString("ownerName"),
                        rs.getString("ownerAddress"),
                        rs.getDate("registrationDate").toString()
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vehicle;
    }

    // Fetch a vehicle from the database by its registration number
    public Vehicle getVehicleByNumber(String registrationNum) {
        String sql = "SELECT * FROM vehicles WHERE registrationNum = ?";
        Vehicle vehicle = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, registrationNum);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                vehicle = new Vehicle(
                        rs.getString("registrationNum"),
                        rs.getString("ownerName"),
                        rs.getString("ownerAddress"),
                        rs.getDate("registrationDate").toString()
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vehicle;
    }

    // Update the entry time of a vehicle
    public boolean updateEntryTime(String registrationNum, long entryTime) {
        String sql = "UPDATE vehicles SET entryTime = ? WHERE registrationNum = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setTimestamp(1, new Timestamp(entryTime));
            stmt.setString(2, registrationNum);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Update the exit time of a vehicle
    public boolean updateExitTime(String registrationNum, long exitTime) {
        String sql = "UPDATE vehicles SET exitTime = ? WHERE registrationNum = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setTimestamp(1, new Timestamp(exitTime));
            stmt.setString(2, registrationNum);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
