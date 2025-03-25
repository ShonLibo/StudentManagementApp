package com.studentmanagement.db;

import com.studentmanagement.util.Constants;

import javax.swing.*;
import java.sql.*;

public class DatabaseManager {
    public void saveStudent(String regNo, String name, String course, String grade, JFrame parent) {
        if (regNo.isEmpty() || name.isEmpty() || course.isEmpty() || grade.isEmpty()) {
            JOptionPane.showMessageDialog(parent, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = DriverManager.getConnection(Constants.DB_URL, Constants.DB_USER, Constants.DB_PASSWORD)) {
            String sql = "INSERT INTO students (regNo, name, course, grade) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, regNo);
            stmt.setString(2, name);
            stmt.setString(3, course);
            stmt.setString(4, grade);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(parent, "Student saved successfully!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(parent, "Error saving student: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void loadData(DefaultTableModel tableModel, JFrame parent) {
        tableModel.setRowCount(0); // Clear existing data
        try (Connection conn = DriverManager.getConnection(Constants.DB_URL, Constants.DB_USER, Constants.DB_PASSWORD)) {
            String sql = "SELECT * FROM students";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String regNo = rs.getString("regNo");
                String name = rs.getString("name");
                String course = rs.getString("course");
                String grade = rs.getString("grade");
                tableModel.addRow(new Object[]{regNo, name, course, grade});
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(parent, "Error loading data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}