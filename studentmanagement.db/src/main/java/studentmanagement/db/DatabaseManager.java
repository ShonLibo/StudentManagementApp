package studentmanagement.db;

import studentmanagement.util.Constants;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class DatabaseManager {
    private Connection connection;

    public DatabaseManager() {
        try {
            connection = DriverManager.getConnection(Constants.DB_URL, Constants.DB_USER, Constants.DB_PASSWORD);
            createTableIfNotExists();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database connection failed: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void createTableIfNotExists() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS students (" +
                "RegNo VARCHAR(20) PRIMARY KEY, " +
                "name VARCHAR(100) NOT NULL, " +
                "course VARCHAR(50) NOT NULL, " +
                "grade VARCHAR(10) NOT NULL)";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        }
    }

    public void saveStudent(String regNo, String name, String course, String grade, JFrame parent) {
        if (regNo.isEmpty() || name.isEmpty() || course.isEmpty() || grade.isEmpty()) {
            JOptionPane.showMessageDialog(parent, "All fields are required!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String sql = "INSERT INTO students (RegNo, name, course, grade) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, regNo);
            pstmt.setString(2, name);
            pstmt.setString(3, course);
            pstmt.setString(4, grade);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(parent, "Student saved successfully!",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(parent, "Error saving student: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void loadData(DefaultTableModel model, JFrame parent) {
        model.setRowCount(0); // Clear existing data

        String sql = "SELECT * FROM students";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String regNo = rs.getString("RegNo");
                String name = rs.getString("name");
                String course = rs.getString("course");
                String grade = rs.getString("grade");
                model.addRow(new Object[]{regNo, name, course, grade});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(parent, "Error loading data: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}