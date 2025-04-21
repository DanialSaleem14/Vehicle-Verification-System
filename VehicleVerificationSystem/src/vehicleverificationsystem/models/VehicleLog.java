package vehicleverificationsystem.models;

import java.sql.Timestamp;

public class VehicleLog {
    private int logId;
    private String vehicleNumber;
    private Timestamp entryTime;
    private Timestamp exitTime;

    public VehicleLog(int logId, String vehicleNumber, Timestamp entryTime, Timestamp exitTime) {
        this.logId = logId;
        this.vehicleNumber = vehicleNumber;
        this.entryTime = entryTime;
        this.exitTime = exitTime;
    }

    // Getters and Setters
    public int getLogId() {
        return logId;
    }

    public void setLogId(int logId) {
        this.logId = logId;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public Timestamp getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(Timestamp entryTime) {
        this.entryTime = entryTime;
    }

    public Timestamp getExitTime() {
        return exitTime;
    }

    public void setExitTime(Timestamp exitTime) {
        this.exitTime = exitTime;
    }
}
