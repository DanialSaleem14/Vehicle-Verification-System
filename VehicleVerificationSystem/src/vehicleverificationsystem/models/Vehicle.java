package vehicleverificationsystem.models;

/**
 *
 * @author muham
 */
public class Vehicle {
    private String registrationNum;
    private String ownerName;
    private String ownerAddress;
    private String registrationDate;

    // Constructor
    public Vehicle(String registrationNum, String ownerName, String ownerAddress, String registrationDate) {
        this.registrationNum = registrationNum;
        this.ownerName = ownerName;
        this.ownerAddress = ownerAddress;
        this.registrationDate = registrationDate;
    }

    // Getters and Setters
    public String getRegistrationNum() {
        return registrationNum;
    }

    public void setRegistrationNum(String registrationNum) {
        this.registrationNum = registrationNum;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerAddress() {
        return ownerAddress;
    }

    public void setOwnerAddress(String ownerAddress) {
        this.ownerAddress = ownerAddress;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

}
