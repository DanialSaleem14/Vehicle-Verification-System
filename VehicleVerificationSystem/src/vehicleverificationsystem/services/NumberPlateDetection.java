package vehicleverificationsystem.services;

import vehicleverificationsystem.models.Vehicle;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class NumberPlateDetection {
    private VehicleRegistry vehicleRegistry;
    private VehicleLogService vehicleLogService;

    public NumberPlateDetection() {
        vehicleRegistry = new VehicleRegistry();
        vehicleLogService = new VehicleLogService();
    }

    public void detectAndProcessNumberPlate(String imagePath) {
        String detectedPlate = detectNumberPlateFromImage(imagePath);
        if (detectedPlate.equals("No plate detected")) {
            JOptionPane.showMessageDialog(null, "No number plate detected.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Extract the last three words from the detected plate text
        String[] words = detectedPlate.split("\\s+"); // Split by whitespace
        String lastThreeWords = detectedPlate; // Default to the full text if less than three words
        if (words.length >= 3) {
            lastThreeWords = String.join(" ", words[words.length - 3], words[words.length - 2], words[words.length - 1]);
        }

        // Log or process the extracted last three words
        System.out.println("Detected Plate: " + detectedPlate);
        System.out.println("Last Three Words: " + lastThreeWords);

        if (vehicleRegistry.isVehicleRegistered(lastThreeWords)) {
            // Vehicle is registered
            boolean loggedEntry = vehicleLogService.logEntryTime(lastThreeWords);
            if (loggedEntry) {
                JOptionPane.showMessageDialog(null, "Vehicle entry time logged: " + lastThreeWords, "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Failed to log vehicle entry time.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            // Vehicle is not registered
            int response = JOptionPane.showConfirmDialog(
                null,
                "Vehicle not registered: " + lastThreeWords + ". Do you want to register this vehicle?",
                "Alert",
                JOptionPane.YES_NO_OPTION
            );

            if (response == JOptionPane.YES_OPTION) {
                // Prompt for vehicle registration details
                String ownerName = JOptionPane.showInputDialog("Enter Owner Name:");
                if (ownerName != null && !ownerName.trim().isEmpty()) {
                    String ownerAddress = JOptionPane.showInputDialog("Enter Owner Address:");
                    if (ownerAddress != null && !ownerAddress.trim().isEmpty()) {
                        String registrationDate = JOptionPane.showInputDialog("Enter Registration Date (YYYY-MM-DD):");
                        if (registrationDate != null && !registrationDate.trim().isEmpty()) {
                            // Create a new Vehicle object with all details
                            Vehicle newVehicle = new Vehicle(lastThreeWords, ownerName, ownerAddress, registrationDate);
                            boolean registered = vehicleRegistry.registerVehicle(newVehicle);

                            if (registered) {
                                JOptionPane.showMessageDialog(null, "Vehicle registered successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(null, "Failed to register vehicle.", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Registration Date is required!", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Owner Address is required!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Owner Name is required!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    public String getDetectedNumberPlate(String image) {
        return detectNumberPlateFromImage(image);
    }

    private String detectNumberPlateFromImage(String imagePath) {
        String detectedPlate = null;
        try {
            String pythonPath = "python"; // Adjust for your environment
            String scriptPath = "src/vehicleverificationsystem/services/script/number_plate_detection.py";

            // Create a process to run the Python script with the image path as an argument
            ProcessBuilder processBuilder = new ProcessBuilder(pythonPath, scriptPath, imagePath);
            processBuilder.redirectErrorStream(true);

            // Start the process
            Process process = processBuilder.start();

            // Capture the output from the Python script
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            int exitCode = process.waitFor();
            if (exitCode == 0) {
                detectedPlate = output.toString().trim(); // Extract detected plate text
            } else {
                System.err.println("Python script exited with error code: " + exitCode);
                System.err.println("Error output: " + output);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detectedPlate != null ? detectedPlate : "No plate detected";
    }
}
