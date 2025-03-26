package studentmanagement.ui;

import com.jtattoo.plaf.acryl.AcrylLookAndFeel;
import studentmanagement.db.DatabaseManager;
import studentmanagement.pdf.PdfExporter;
import studentmanagement.util.Constants;

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

        // Main panel with CardLayout
        CardLayout cardLayout = new CardLayout();
        JPanel mainPanel = new JPanel(cardLayout);
        mainPanel.setBackground(new Color(34, 45, 50));

        // Register Student Panel
        JPanel registerPanel = createRegisterPanel();

        // Student Record Panel
        JPanel recordPanel = createRecordPanel();

        // Add panels to CardLayout
        mainPanel.add(registerPanel, "Register");
        mainPanel.add(recordPanel, "Record");

        // Add main panel to frame
        add(mainPanel);

        // Set up button actions
        setupButtonActions(cardLayout, mainPanel);

        // Search bar filtering
        setupSearchFilter();
    }

    private JPanel createRegisterPanel() {
        JPanel registerPanel = new JPanel();
        registerPanel.setBackground(new Color(34, 45, 50));
        registerPanel.setLayout(null);

        JLabel titleLabel = new JLabel("Register Student");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBounds(220, 20, 200, 30);
        registerPanel.add(titleLabel);

        // Input fields
        addLabelAndField(registerPanel, "RegNo", 80, regNoField = new JTextField());
        addLabelAndField(registerPanel, "Name", 120, nameField = new JTextField());
        addLabelAndField(registerPanel, "Course", 160, courseField = new JTextField());
        addLabelAndField(registerPanel, "Grade", 200, gradeField = new JTextField());

        // Buttons
        saveButton = createButton("Save", new Color(33, 150, 243), 150, 260, 140, 30);
        viewButton = createButton("View", new Color(0, 188, 212), 300, 260, 140, 30);

        registerPanel.add(saveButton);
        registerPanel.add(viewButton);

        return registerPanel;
    }

    private JPanel createRecordPanel() {
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
        tableModel = new DefaultTableModel(Constants.TABLE_COLUMNS, 0);
        studentTable = new JTable(tableModel);
        rowSorter = new TableRowSorter<>(tableModel);
        studentTable.setRowSorter(rowSorter);
        JScrollPane scrollPane = new JScrollPane(studentTable);
        scrollPane.setBounds(50, 100, 500, 200);
        recordPanel.add(scrollPane);

        // Buttons
        loadDataButton = createButton("Load Data", new Color(33, 150, 243), 50, 320, 130, 30);
        addNewRecordButton = createButton("Add New Record", new Color(33, 150, 243), 200, 320, 180, 30);
        exportButton = createButton("Export to PDF", new Color(0, 188, 212), 400, 320, 140, 30);

        recordPanel.add(loadDataButton);
        recordPanel.add(addNewRecordButton);
        recordPanel.add(exportButton);

        return recordPanel;
    }

    private void addLabelAndField(JPanel panel, String labelText, int yPos, JTextField textField) {
        JLabel label = new JLabel(labelText);
        label.setForeground(Color.WHITE);
        label.setBounds(100, yPos, 100, 25);
        panel.add(label);

        textField.setBounds(200, yPos, 250, 25);
        panel.add(textField);
    }

    private JButton createButton(String text, Color bgColor, int x, int y, int width, int height) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setBounds(x, y, width, height);
        return button;
    }

    private void setupButtonActions(CardLayout cardLayout, JPanel mainPanel) {
        saveButton.addActionListener(e -> {
            dbManager.saveStudent(
                    regNoField.getText(),
                    nameField.getText(),
                    courseField.getText(),
                    gradeField.getText(),
                    StudentManagementApp.this
            );
            clearFields();
        });

        viewButton.addActionListener(e -> cardLayout.show(mainPanel, "Record"));
        loadDataButton.addActionListener(e -> dbManager.loadData(tableModel, StudentManagementApp.this));
        addNewRecordButton.addActionListener(e -> {
            cardLayout.show(mainPanel, "Register");
            clearFields();
        });
        exportButton.addActionListener(e -> pdfExporter.exportToPDF(studentTable, StudentManagementApp.this));
    }

    private void setupSearchFilter() {
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { filterTable(); }
            @Override public void removeUpdate(DocumentEvent e) { filterTable(); }
            @Override public void changedUpdate(DocumentEvent e) { filterTable(); }
        });
    }

    private void filterTable() {
        String searchText = searchField.getText().trim();
        if (searchText.isEmpty()) {
            rowSorter.setRowFilter(null);
        } else {
            rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText));
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
            UIManager.setLookAndFeel(new AcrylLookAndFeel());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        SwingUtilities.invokeLater(() -> {
            StudentManagementApp app = new StudentManagementApp();
            app.setVisible(true);
        });
    }
}