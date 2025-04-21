package vehicleverificationsystem.gui;

import java.awt.Image;
import vehicleverificationsystem.dao.VehicleDAO;
import vehicleverificationsystem.models.Vehicle;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterVehicleGUI extends JFrame {
    private JLabel registrationLabel, ownerNameLabel, ownerAddressLabel, registrationDateLabel;
    private JTextField registrationField, ownerNameField, ownerAddressField, registrationDateField;
    private JButton registerButton, cancelButton;

    public RegisterVehicleGUI() {
        setTitle("Register Vehicle");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);
        
        ImageIcon originalIcon = new ImageIcon(getClass().getResource("/vehicleverificationsystem/gui/car.jpg"));
        Image scaledImage = originalIcon.getImage().getScaledInstance(800, 600, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        
        JLabel background = new JLabel(scaledIcon);
        background.setBounds(0, 0, 1000, 600);
        setContentPane(background);
        background.setLayout(null);

        
        registrationLabel = new JLabel("Registration Number:");
        ownerNameLabel = new JLabel("Owner Name:");
        ownerAddressLabel = new JLabel("Owner Address:");
        registrationDateLabel = new JLabel("Registration Date (YYYY-MM-DD):");

        registrationField = new JTextField();
        ownerNameField = new JTextField();
        ownerAddressField = new JTextField();
        registrationDateField = new JTextField();

        registerButton = new JButton("Register");
        cancelButton = new JButton("Cancel");

        // Set Bounds
        registrationLabel.setBounds(30, 30, 200, 25);
        registrationField.setBounds(230, 30, 200, 25);

        ownerNameLabel.setBounds(30, 70, 200, 25);
        ownerNameField.setBounds(230, 70, 200, 25);

        ownerAddressLabel.setBounds(30, 110, 200, 25);
        ownerAddressField.setBounds(230, 110, 200, 25);

        registrationDateLabel.setBounds(30, 150, 200, 25);
        registrationDateField.setBounds(230, 150, 200, 25);

        registerButton.setBounds(120, 250, 100, 30);
        cancelButton.setBounds(250, 250, 100, 30);

        // Add Action Listeners
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String registrationNum = registrationField.getText().trim();
                String ownerName = ownerNameField.getText().trim();
                String ownerAddress = ownerAddressField.getText().trim();
                String registrationDate = registrationDateField.getText().trim();

                // Validation
                if (registrationNum.isEmpty() || ownerName.isEmpty() || ownerAddress.isEmpty() || registrationDate.isEmpty()) {
                    JOptionPane.showMessageDialog(RegisterVehicleGUI.this, "All fields are required!");
                    return;
                }

                try {
                    // Create Vehicle Object
                    Vehicle vehicle = new Vehicle(registrationNum, ownerName, ownerAddress, registrationDate);
                    VehicleDAO dao = new VehicleDAO();
                    boolean success = dao.addVehicle(vehicle);

                    if (success) {
                        JOptionPane.showMessageDialog(RegisterVehicleGUI.this, "Vehicle registered successfully!");
                        clearFields(); // Clear fields after successful registration
                    } else {
                        JOptionPane.showMessageDialog(RegisterVehicleGUI.this, "Failed to register vehicle. It may already exist!");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(RegisterVehicleGUI.this, "Error: " + ex.getMessage());
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the window
            }
        });

        // Add Components to Frame
        add(registrationLabel);
        add(registrationField);
        add(ownerNameLabel);
        add(ownerNameField);
        add(ownerAddressLabel);
        add(ownerAddressField);
        add(registrationDateLabel);
        add(registrationDateField);
        add(registerButton);
        add(cancelButton);
    }

    // Method to clear fields
    private void clearFields() {
        registrationField.setText("");
        ownerNameField.setText("");
        ownerAddressField.setText("");
        registrationDateField.setText("");
    }
}
