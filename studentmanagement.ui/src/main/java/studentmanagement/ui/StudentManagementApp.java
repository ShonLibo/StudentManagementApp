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
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;

public class StudentManagementApp extends JFrame {
    JTextField regNoField;
    JTextField nameField;
    JTextField courseField;
    JTextField gradeField;
    JTextField searchField;
    private JTable studentTable;
    DefaultTableModel tableModel;
    JButton saveButton;
    private JButton viewButton;
    private JButton loadDataButton;
    JButton exportButton;
    private JButton addNewRecordButton;
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

        JLabel recordTitleLabel = new JLabel("Student Records");
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
        tableModel = new DefaultTableModel(new String[]{"RegNo", "Name", "Course", "Grade"}, 0);
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
            String regNo = regNoField.getText();
            String name = nameField.getText();
            String course = courseField.getText();
            String grade = gradeField.getText();

            if (regNo.isEmpty() || name.isEmpty() || course.isEmpty() || grade.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            dbManager.saveStudent(regNo, name, course, grade, StudentManagementApp.this);
            clearFields();
        });

        viewButton.addActionListener(e -> cardLayout.show(mainPanel, "Record"));

        loadDataButton.addActionListener(e -> {
            tableModel.setRowCount(0); // Clear existing data
            dbManager.loadData(tableModel, StudentManagementApp.this);
        });

        addNewRecordButton.addActionListener(e -> {
            cardLayout.show(mainPanel, "Register");
            clearFields();
        });

        exportButton.addActionListener(e -> {
            if (studentTable.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "No data to export!", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Save PDF File");
            String userHome = System.getProperty("user.home");
            fileChooser.setCurrentDirectory(new File(userHome + "/Documents"));
            fileChooser.setSelectedFile(new File("StudentRecord.pdf"));

            if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                String filePath = selectedFile.getAbsolutePath();

                if (!filePath.toLowerCase().endsWith(".pdf")) {
                    filePath += ".pdf";
                    selectedFile = new File(filePath);
                }

                try {
                    File parentDir = selectedFile.getParentFile();
                    if (!parentDir.exists()) {
                        boolean created = parentDir.mkdirs();
                        if (!created) {
                            throw new IOException("Failed to create directory: " + parentDir.getAbsolutePath());
                        }
                    }
                    if (!parentDir.canWrite()) {
                        throw new IOException("No write permission for directory: " + parentDir.getAbsolutePath());
                    }

                    if (selectedFile.exists() && !selectedFile.canWrite()) {
                        throw new IOException("File exists but is not writable: " + selectedFile.getAbsolutePath());
                    }

                    PdfWriter writer = new PdfWriter(filePath);
                    PdfDocument pdf = new PdfDocument(writer);
                    Document document = new Document(pdf);

                    Paragraph title = new Paragraph("Student Records Report")
                            .setFontSize(20)
                            .setBold()
                            .setTextAlignment(TextAlignment.CENTER);
                    document.add(title);

                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                    String currentDate = dateFormat.format(new Date());
                    Paragraph timestamp = new Paragraph("Generated on: " + currentDate)
                            .setTextAlignment(TextAlignment.CENTER);
                    document.add(timestamp);

                    document.add(new Paragraph("\n"));

                    Table table = new Table(UnitValue.createPercentArray(new float[]{25, 25, 25, 25}));
                    table.setWidth(UnitValue.createPercentValue(100));

                    for (int i = 0; i < studentTable.getColumnCount(); i++) {
                        table.addHeaderCell(studentTable.getColumnName(i)).setBold();
                    }

                    for (int row = 0; row < studentTable.getRowCount(); row++) {
                        for (int col = 0; col < studentTable.getColumnCount(); col++) {
                            table.addCell(studentTable.getValueAt(row, col).toString());
                        }
                    }

                    document.add(table);
                    document.close();
                    JOptionPane.showMessageDialog(this, "PDF exported successfully to: " + filePath);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Error exporting PDF: " + ex.getMessage() + "\nTry saving to a different location.", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Unexpected error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void setupSearchFilter() {
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { filterTable(); }
            @Override public void removeUpdate(DocumentEvent e) { filterTable(); }
            @Override public void changedUpdate(DocumentEvent e) { filterTable(); }
        });
    }

    void filterTable() {
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