package vehicleverificationsystem.gui;

import vehicleverificationsystem.services.NumberPlateDetection;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DetectPlateGUI extends JFrame {
    private JLabel imagePathLabel;
    private JTextField imagePathField;
    private JButton browseButton, detectButton, cancelButton;

    public DetectPlateGUI() {
        setTitle("Detect Number Plate");
        setSize(500, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        // Initialize Components
        imagePathLabel = new JLabel("Upload Image:");
        imagePathField = new JTextField();
        browseButton = new JButton("Browse");
        detectButton = new JButton("Detect");
        cancelButton = new JButton("Cancel");

        // Set Bounds
        imagePathLabel.setBounds(30, 30, 100, 25);
        imagePathField.setBounds(130, 30, 200, 25);
        browseButton.setBounds(340, 30, 100, 25);
        detectButton.setBounds(130, 80, 90, 30);
        cancelButton.setBounds(240, 80, 90, 30);

        // Browse Button Action
        browseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(DetectPlateGUI.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    imagePathField.setText(fileChooser.getSelectedFile().getAbsolutePath());
                }
            }
        });

        // Detect Button Action
        detectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String imagePath = imagePathField.getText();
                if (imagePath.isEmpty()) {
                    JOptionPane.showMessageDialog(DetectPlateGUI.this, "Please select an image.");
                    return;
                }

                // Display Progress Dialog
                JDialog progressDialog = new JDialog(DetectPlateGUI.this, "Processing", true);
                progressDialog.setSize(300, 100);
                progressDialog.setLayout(new BoxLayout(progressDialog.getContentPane(), BoxLayout.Y_AXIS));
                progressDialog.add(new JLabel("Detecting number plate, please wait..."));
                progressDialog.setLocationRelativeTo(DetectPlateGUI.this);

                // Run detection in a background thread
                SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                    @Override
                    protected Void doInBackground() {
                        NumberPlateDetection detector = new NumberPlateDetection();
                        detector.detectAndProcessNumberPlate(imagePath);
                        return null;
                    }

                    @Override
                    protected void done() {
                        progressDialog.dispose();
                    }
                };

                // Show progress dialog and start the worker
                worker.execute();
                progressDialog.setVisible(true);
            }
        });

        // Cancel Button Action
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the window
            }
        });

        // Add Components
        add(imagePathLabel);
        add(imagePathField);
        add(browseButton);
        add(detectButton);
        add(cancelButton);
    }
}
