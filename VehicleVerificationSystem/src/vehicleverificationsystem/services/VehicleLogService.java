package vehicleverificationsystem.services;

import vehicleverificationsystem.dao.VehicleLogDAO;

public class VehicleLogService {
    private VehicleLogDAO vehicleLogDAO;

    public VehicleLogService() {
        vehicleLogDAO = new VehicleLogDAO();
    }

    public boolean logEntryTime(String registrationNum) {
        return vehicleLogDAO.addEntryLog(registrationNum, System.currentTimeMillis());
    }

    public boolean logExitTime(String registrationNum) {
        return vehicleLogDAO.updateExitLog(registrationNum, System.currentTimeMillis());
    }
}
