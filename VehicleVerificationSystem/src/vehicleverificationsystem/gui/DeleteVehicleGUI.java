package vehicleverificationsystem.gui;

import vehicleverificationsystem.dao.VehicleDAO;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeleteVehicleGUI extends JFrame {
    private JLabel registrationLabel;
    private JTextField registrationField;
    private JButton deleteButton, cancelButton;

    public DeleteVehicleGUI() {
        setTitle("Delete Vehicle");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        // Initialize Components
        registrationLabel = new JLabel("Registration Number:");
        registrationField = new JTextField();
        deleteButton = new JButton("Delete");
        cancelButton = new JButton("Cancel");

        // Set Bounds
        registrationLabel.setBounds(30, 30, 150, 25);
        registrationField.setBounds(180, 30, 180, 25);
        deleteButton.setBounds(90, 100, 90, 30);
        cancelButton.setBounds(200, 100, 90, 30);

        // Add Action Listeners
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String registrationNum = registrationField.getText().trim();
                if (registrationNum.isEmpty()) {
                    JOptionPane.showMessageDialog(DeleteVehicleGUI.this, "Please enter a registration number!");
                    return;
                }

                VehicleDAO dao = new VehicleDAO();
                boolean success = dao.deleteVehicle(registrationNum);

                if (success) {
                    JOptionPane.showMessageDialog(DeleteVehicleGUI.this, "Vehicle deleted successfully!");
                } else {
                    JOptionPane.showMessageDialog(DeleteVehicleGUI.this, "Vehicle not found in the database!");
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the window
            }
        });

        // Add Components
        add(registrationLabel);
        add(registrationField);
        add(deleteButton);
        add(cancelButton);
    }
}
