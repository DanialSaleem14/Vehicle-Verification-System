package vehicleverificationsystem.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainGUI extends JFrame {
    private JButton registerButton;
    private JButton deleteButton;
    private JButton detectPlateButton;
    private JButton displayVehiclesButton;
    private JButton displayLogsButton; // New button for displaying logs

    public MainGUI() {
        setTitle("Vehicle Verification System");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null); // Using absolute layout

        // Load and Scale Background Image
        ImageIcon originalIcon = new ImageIcon(getClass().getResource("/vehicleverificationsystem/gui/car.jpg"));
        Image scaledImage = originalIcon.getImage().getScaledInstance(1000, 600, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        // Set Background Image
        JLabel background = new JLabel(scaledIcon);
        background.setBounds(0, 0, 1000, 600);
        setContentPane(background); // Set background to the content pane
        background.setLayout(null); // Allow absolute layout over the background

        // Initialize Buttons
        registerButton = new JButton("Register Vehicle");
        deleteButton = new JButton("Delete Vehicle");
        detectPlateButton = new JButton("Detect Number Plate");
        displayVehiclesButton = new JButton("Display Registered Vehicles");
        displayLogsButton = new JButton("Display Vehicle Logs"); // New button

        // Set Bounds for Buttons
        registerButton.setBounds(400, 150, 200, 40);
        deleteButton.setBounds(400, 220, 200, 40);
        detectPlateButton.setBounds(400, 290, 200, 40);
        displayVehiclesButton.setBounds(400, 360, 200, 40);
        displayLogsButton.setBounds(400, 430, 200, 40); // New button placement

        // Add Action Listeners
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RegisterVehicleGUI().setVisible(true); // Open Register Vehicle window
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DeleteVehicleGUI().setVisible(true); // Open Delete Vehicle window
            }
        });

        detectPlateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DetectPlateGUI().setVisible(true); // Open Detect Plate window
            }
        });

        displayVehiclesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DisplayVehiclesGUI().setVisible(true); // Open Display Vehicles window
            }
        });

        displayLogsButton.addActionListener(new ActionListener() { // Action listener for new button
            @Override
            public void actionPerformed(ActionEvent e) {
                new DisplayLogsGUI().setVisible(true); // Open Display Logs window
            }
        });

        // Add Buttons to Background
        background.add(registerButton);
        background.add(deleteButton);
        background.add(detectPlateButton);
        background.add(displayVehiclesButton);
        background.add(displayLogsButton); // Add new button to background
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainGUI mainGUI = new MainGUI();
            mainGUI.setVisible(true);
        });
    }
}
