/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package narst_course_utilisation_setting;

/**
 *
 * @author tharu
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class narst_course_utilisation_setting extends JFrame implements ActionListener {
    private JLabel[] labels = new JLabel[8];
    private JTextField[] fields = new JTextField[8];
    private String[] fieldNames = {"ID", "Course ID", "Course Utilization Code", "Unit Number", "Unit Name","Required Contact Hours", "Course Outcome ID", "Reference ID"};
    private JButton insertBtn = new JButton("Insert");
    private JButton updateBtn = new JButton("Update");
    private JButton deleteBtn = new JButton("Delete");
    private JButton retrieveBtn = new JButton("Retrieve");
    private JTable table;
    private DefaultTableModel tableModel;

    public narst_course_utilisation_setting() {
        setTitle("Course Utilisation Setting");
        setSize(1000, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel inputPanel = new JPanel(new GridLayout(8, 2, 5, 5));
        for (int i = 0; i < 8; i++) {
            labels[i] = new JLabel(fieldNames[i]);
            fields[i] = new JTextField();
            inputPanel.add(labels[i]);
            inputPanel.add(fields[i]);
        }

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(insertBtn);
        buttonPanel.add(updateBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(retrieveBtn);

        String[] columnNames = {"ID", "Course ID", "Course Utilization Code", "Unit Number", "Unit Name","Required Contact Hours", "Course Outcome ID", "Reference ID"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane tableScroll = new JScrollPane(table);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(tableScroll, BorderLayout.SOUTH);

        insertBtn.addActionListener(this);
        updateBtn.addActionListener(this);
        deleteBtn.addActionListener(this);
        retrieveBtn.addActionListener(this);
        

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    for (int i = 0; i < fields.length; i++) {
                        fields[i].setText(tableModel.getValueAt(row, i).toString());
                    }
                }
            }
        });
        add(mainPanel);
        SQLDB.connect("C:\\Users\\tharu\\OneDrive\\Desktop\\Apps\\javaapp.db");
        refreshTable();
    }

    private void refreshTable() {
        try {
            tableModel.setRowCount(0);
            ResultSet rs = SQLDB.executeQuery("SELECT * FROM course_utilization");
            while (rs.next()) {
                Object[] row = {
                    rs.getString("ID"),
                    rs.getString("cour_id"),
                    rs.getString("cour_util_code"),
                    rs.getString("unit_no"),
                    rs.getString("unit_name"),
                    rs.getString("require_contact_hr"),
                    rs.getString("cour_out_id"),
                    rs.getString("ref_id")
                };
                tableModel.addRow(row);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error loading data: " + ex.getMessage());
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == insertBtn) {
            narst_CourseUtilizationSetting_create();
        } else if (e.getSource() == updateBtn) {
            narst_CourseUtilizationSetting_update();
        } else if (e.getSource() == deleteBtn) {
            narst_CourseUtilizationSetting_delete();
        } else if (e.getSource() == retrieveBtn) {
            narst_CourseUtilizationSetting_retrieve();
        }
        refreshTable();
    }
    
    private void narst_CourseUtilizationSetting_create(){
        try (PreparedStatement pstmt = SQLDB.conn.prepareStatement(
                "INSERT INTO course_utilization VALUES(?,?,?,?,?,?,?,?)")) {
            for (int i = 0; i < 8; i++) {
                pstmt.setString(i + 1, fields[i].getText());
            }
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Record inserted successfully!");
            clearFields();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Insert error: " + ex.getMessage());
        }
    }

    private void narst_CourseUtilizationSetting_update() {
        String id = fields[0].getText();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter Data to Update!");
            return;
        }
        try (PreparedStatement pstmt = SQLDB.conn.prepareStatement(
                "UPDATE course_utilization SET cour_id=?, cour_util_code=?, unit_no=?, " +
                "unit_name=?, require_contact_hr=?, cour_out_id=?, ref_id=? WHERE ID=?")) {
            for (int i = 0; i < 7; i++) {
                pstmt.setString(i + 1, fields[i+1].getText());
            }
            pstmt.setString(8, fields[0].getText());
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Record updated successfully!");
            clearFields();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Update error: " + ex.getMessage());
        }
    }

    private void narst_CourseUtilizationSetting_delete() {
        String id = fields[0].getText();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a record to delete!");
            return;
        }
        try (PreparedStatement pstmt = SQLDB.conn.prepareStatement(
                "DELETE FROM course_utilization WHERE ID=?")) {
            pstmt.setString(1, id);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Record deleted successfully!");
            clearFields();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Delete error: " + ex.getMessage());
        }
    }

    private void narst_CourseUtilizationSetting_retrieve() {
    try {
        tableModel.setRowCount(0);
        ResultSet rs = SQLDB.executeQuery("SELECT * FROM course_utilization");
        while (rs.next()) {
            Object[] row = {
                rs.getString("ID"),
                rs.getString("cour_id"),
                rs.getString("cour_util_code"),
                rs.getString("unit_no"),
                rs.getString("unit_name"),
                rs.getString("require_contact_hr"),
                rs.getString("cour_out_id"),
                rs.getString("ref_id")
            };
            tableModel.addRow(row);
        }
        JOptionPane.showMessageDialog(this, "Data retrieved successfully!");
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, "Retrieve error: " + ex.getMessage());
        }
    }
    private void clearFields() {
        for (JTextField field : fields) {
            field.setText("");
        }
    }
    public static void main(String[] args) {
      new narst_course_utilisation_setting().setVisible(true);
    }
}