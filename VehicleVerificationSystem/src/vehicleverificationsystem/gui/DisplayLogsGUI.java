package vehicleverificationsystem.gui;

import vehicleverificationsystem.dao.VehicleLogDAO;
import vehicleverificationsystem.models.VehicleLog;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class DisplayLogsGUI extends JFrame {
    private JTable logsTable;

    public DisplayLogsGUI() {
        setTitle("Vehicle Logs");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Table Model
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Log ID");
        tableModel.addColumn("Vehicle Number");
        tableModel.addColumn("Entry Time");
        tableModel.addColumn("Exit Time");

        logsTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(logsTable);

        add(scrollPane, BorderLayout.CENTER);

        // Fetch data using DAO
        VehicleLogDAO logDAO = new VehicleLogDAO();
        List<VehicleLog> logs = logDAO.getAllLogs();

        // Populate table
        for (VehicleLog log : logs) {
            tableModel.addRow(new Object[]{
                    log.getLogId(),
                    log.getVehicleNumber(),
                    log.getEntryTime(),
                    log.getExitTime()
            });
        }
    }
}
