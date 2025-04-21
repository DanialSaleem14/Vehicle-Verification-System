package vehicleverificationsystem.gui;

import vehicleverificationsystem.dao.VehicleDAO;
import vehicleverificationsystem.models.Vehicle;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class DisplayVehiclesGUI extends JFrame {
    private JTable vehiclesTable;
    private JButton closeButton;
    private JScrollPane scrollPane;

    public DisplayVehiclesGUI() {
        setTitle("Display All Vehicles");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create Table
        vehiclesTable = new JTable();
        scrollPane = new JScrollPane(vehiclesTable);

        // Fetch and Load Data
        loadVehicleData();

        // Create Close Button
        closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());

        // Add Components to Frame
        add(scrollPane, BorderLayout.CENTER);
        add(closeButton, BorderLayout.SOUTH);
    }

    private void loadVehicleData() {
        // Fetch data using DAO
        VehicleDAO dao = new VehicleDAO();
        ArrayList<Vehicle> vehicles = dao.getAllVehicles();

        // Set up Table Model
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"Registration No", "Owner Name", "Owner Address", "Registration Date"});

        // Populate Table Model
        for (Vehicle vehicle : vehicles) {
            model.addRow(new Object[]{
                    vehicle.getRegistrationNum(),
                    vehicle.getOwnerName(),
                    vehicle.getOwnerAddress(),
                    vehicle.getRegistrationDate()
            });
        }

        // Set Model to Table
        vehiclesTable.setModel(model);
    }
}
