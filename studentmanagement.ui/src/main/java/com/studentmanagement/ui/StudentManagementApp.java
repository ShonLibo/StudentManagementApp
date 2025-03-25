package com.studentmanagement.ui;

import com.jtattoo.plaf.acryl.AcrylLookAndFeel;
import com.studentmanagement.db.DatabaseManager;
import com.studentmanagement.pdf.PdfExporter;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentManagementApp extends JFrame {
    private JTextField regNoField, nameField, courseField, gradeField, searchField;
    private JTable studentTable;
    private DefaultTableModel tableModel;
    private JButton saveButton, viewButton, loadDataButton, exportButton, addNewRecordButton;
    private TableRowSorter<DefaultTableModel> rowSorter;
    private final DatabaseManager dbManager;
    private final PdfExporter pdfExporter;

    public StudentManagementApp() {
        // Initialize managers
        dbManager = new DatabaseManager();
        pdfExporter = new PdfExporter();

        // Set up the frame
        setTitle("Student Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        // Main panel with CardLayout to switch between Register and View screens
        CardLayout cardLayout = new CardLayout();
        JPanel mainPanel = new JPanel(cardLayout);
        mainPanel.setBackground(new Color(34, 45, 50)); // Dark teal background

        // Register Student Panel
        JPanel registerPanel = new JPanel();
        registerPanel.setBackground(new Color(34, 45, 50));
        registerPanel.setLayout(null);

        JLabel titleLabel = new JLabel("Register Student");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBounds(220, 20, 200, 30);
        registerPanel.add(titleLabel);

        // Input fields
        JLabel regNoLabel = new JLabel("RegNo");
        regNoLabel.setForeground(Color.WHITE);
        regNoLabel.setBounds(100, 80, 100, 25);
        registerPanel.add(regNoLabel);

        regNoField = new JTextField();
        regNoField.setBounds(200, 80, 250, 25);
        registerPanel.add(regNoField);

        JLabel nameLabel = new JLabel("Name");
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setBounds(100, 120, 100, 25);
        registerPanel.add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(200, 120, 250, 25);
        registerPanel.add(nameField);

        JLabel courseLabel = new JLabel("Course");
        courseLabel.setForeground(Color.WHITE);
        courseLabel.setBounds(100, 160, 100, 25);
        registerPanel.add(courseLabel);

        courseField = new JTextField();
        courseField.setBounds(200, 160, 250, 25);
        registerPanel.add(courseField);

        JLabel gradeLabel = new JLabel("Grade");
        gradeLabel.setForeground(Color.WHITE);
        gradeLabel.setBounds(100, 200, 100, 25);
        registerPanel.add(gradeLabel);

        gradeField = new JTextField();
        gradeField.setBounds(200, 200, 250, 25);
        registerPanel.add(gradeField);

        // Buttons for Register panel
        saveButton = new JButton("Save");
        saveButton.setBackground(new Color(33, 150, 243)); // Blue
        saveButton.setForeground(Color.WHITE);
        saveButton.setBounds(150, 260, 140, 30);
        registerPanel.add(saveButton);

        viewButton = new JButton("View");
        viewButton.setBackground(new Color(0, 188, 212)); // Cyan
        viewButton.setForeground(Color.WHITE);
        viewButton.setBounds(300, 260, 140, 30);
        registerPanel.add(viewButton);

        // Student Record Panel
        JPanel recordPanel = new JPanel();
        recordPanel.setBackground(new Color(34, 45, 50));
        recordPanel.setLayout(null);

        JLabel recordTitleLabel = new JLabel("Student Record");
        recordTitleLabel.setForeground(Color.WHITE);
        recordTitleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        recordTitleLabel.setBounds(220, 20, 200, 30);
        recordPanel.add(recordTitleLabel);

        // Search bar
        JLabel searchLabel = new JLabel("Search:");
        searchLabel.setForeground(Color.WHITE);
        searchLabel.setBounds(50, 60, 50, 25);
        recordPanel.add(searchLabel);

        searchField = new JTextField();
        searchField.setBounds(100, 60, 200, 25);
        recordPanel.add(searchField);

        // Table setup
        String[] columns = {"RegNo", "Name", "Course", "Grade"};
        tableModel = new DefaultTableModel(columns, 0);
        studentTable = new JTable(tableModel);
        rowSorter = new TableRowSorter<>(tableModel);
        studentTable.setRowSorter(rowSorter);
        JScrollPane scrollPane = new JScrollPane(studentTable);
        scrollPane.setBounds(50, 100, 500, 200);
        recordPanel.add(scrollPane);

        // Buttons for Record panel
        loadDataButton = new JButton("Load Data");
        loadDataButton.setBackground(new Color(33, 150, 243)); // Blue
        loadDataButton.setForeground(Color.WHITE);
        loadDataButton.setBounds(50, 320, 130, 30);
        recordPanel.add(loadDataButton);

        addNewRecordButton = new JButton("Add New Record");
        addNewRecordButton.setBackground(new Color(33, 150, 243)); // Blue
        addNewRecordButton.setForeground(Color.WHITE);
        addNewRecordButton.setBounds(200, 320, 180, 30);
        recordPanel.add(addNewRecordButton);

        exportButton = new JButton("Export to PDF");
        exportButton.setBackground(new Color(0, 188, 212)); // Cyan
        exportButton.setForeground(Color.WHITE);
        exportButton.setBounds(400, 320, 140, 30);
        recordPanel.add(exportButton);

        // Add panels to CardLayout
        mainPanel.add(registerPanel, "Register");
        mainPanel.add(recordPanel, "Record");

        // Add main panel to frame
        add(mainPanel);

        // Button actions
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dbManager.saveStudent(regNoField.getText(), nameField.getText(), courseField.getText(), gradeField.getText(), StudentManagementApp.this);
                clearFields();
            }
        });

        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Record");
            }
        });

        loadDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dbManager.loadData(tableModel, StudentManagementApp.this);
            }
        });

        addNewRecordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Register");
            }
        });

        exportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pdfExporter.exportToPDF(studentTable, StudentManagementApp.this);
            }
        });

        // Search bar filtering
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterTable();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filterTable();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filterTable();
            }
        });
    }

    private void filterTable() {
        String searchText = searchField.getText().trim();
        if (searchText.isEmpty()) {
            rowSorter.setRowFilter(null); // Show all rows if search is empty
        } else {
            rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText)); // Case-insensitive search
        }
    }

    private void clearFields() {
        regNoField.setText("");
        nameField.setText("");
        courseField.setText("");
        gradeField.setText("");
    }

    public static void main(String[] args) {
        try {
            // Set JTattoo Look and Feel (Acryl theme as an example)
            UIManager.setLookAndFeel(new AcrylLookAndFeel());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
            // Fallback to default Look and Feel if JTattoo fails
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        SwingUtilities.invokeLater(() -> {
            new StudentManagementApp().setVisible(true);
        });
    }
}