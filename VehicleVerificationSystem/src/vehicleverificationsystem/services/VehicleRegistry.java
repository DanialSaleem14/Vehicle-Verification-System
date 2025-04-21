package vehicleverificationsystem.services;

import vehicleverificationsystem.dao.VehicleDAO;
import vehicleverificationsystem.models.Vehicle;

import java.util.ArrayList;

public class VehicleRegistry {
    private VehicleDAO vehicleDAO;

    public VehicleRegistry() {
        vehicleDAO = new VehicleDAO();
    }

    public boolean registerVehicle(Vehicle vehicle) {
        return vehicleDAO.addVehicle(vehicle);
    }

    public boolean deleteVehicle(String registrationNum) {
        return vehicleDAO.deleteVehicle(registrationNum);
    }

    public ArrayList<Vehicle> getAllVehicles() {
        return vehicleDAO.getAllVehicles();
    }

    public boolean isVehicleRegistered(String registrationNum) {
        return vehicleDAO.getVehicleByNumber(registrationNum) != null;
    }
}
