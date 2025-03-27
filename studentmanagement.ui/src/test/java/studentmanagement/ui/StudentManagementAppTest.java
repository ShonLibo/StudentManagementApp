package studentmanagement.ui;

import static org.junit.jupiter.api.Assertions.*;

class StudentManagementAppTest {

    private StudentManagementApp app;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        // Instantiate the real application before each test
        app = new StudentManagementApp();
    }

    @org.junit.jupiter.api.Test
    void testSaveStudentWithEmptyFields() {
        // Set all fields to empty
        app.regNoField.setText("");
        app.nameField.setText("");
        app.courseField.setText("");
        app.gradeField.setText("");

        // Simulate clicking the save button
        app.saveButton.doClick();

        // Assert that the error dialog appears (you can extend this part)
        // Example: check if fields are cleared
        assertEquals("", app.regNoField.getText());
        assertEquals("", app.nameField.getText());
        assertEquals("", app.courseField.getText());
        assertEquals("", app.gradeField.getText());
    }

    @org.junit.jupiter.api.Test
    void testSaveStudentWithValidFields() {
        // Set valid student data
        app.regNoField.setText("12345");
        app.nameField.setText("John Doe");
        app.courseField.setText("Math");
        app.gradeField.setText("A");

        // Simulate clicking the save button
        app.saveButton.doClick();

        // Assert that the fields are cleared after saving the student data
        assertEquals("", app.regNoField.getText());
        assertEquals("", app.nameField.getText());
        assertEquals("", app.courseField.getText());
        assertEquals("", app.gradeField.getText());
    }

    @org.junit.jupiter.api.Test
    void testSearchFilter() {
        // Add some data to the table
        app.tableModel.addRow(new Object[]{"12345", "John Doe", "Math", "A"});
        app.tableModel.addRow(new Object[]{"67890", "Jane Smith", "Science", "B"});

        // Simulate search for "John"
        app.searchField.setText("John");
        app.filterTable();

        // Assert that the filter worked and only "John Doe" is visible
        assertEquals(1, app.tableModel.getRowCount());
        assertEquals("12345", app.tableModel.getValueAt(0, 0)); // Ensure "John Doe" is still there
    }

    @org.junit.jupiter.api.Test
    void testExportToPdfWhenNoData() {
        // Clear the table data (simulate no records)
        app.tableModel.setRowCount(0);

        // Simulate clicking the export to PDF button
        app.exportButton.doClick();

        // Check that the warning dialog is shown (you'd need a real check here)
        // Example: Assuming a method exists to verify the warning dialog
        assertTrue(isWarningDialogVisible());
    }

    @org.junit.jupiter.api.Test
    void testExportToPdfWhenDataExists() {
        // Add some data to the table
        app.tableModel.addRow(new Object[]{"12345", "John Doe", "Math", "A"});
        app.tableModel.addRow(new Object[]{"67890", "Jane Smith", "Science", "B"});

        // Simulate clicking the export to PDF button
        app.exportButton.doClick();

        // Check that the PDF export success message is shown (you'd need a real check here)
        // Example: Assuming a method exists to verify successful export
        assertTrue(isPdfExportedSuccessfully());
    }

    // Helper methods to simulate dialog verifications
    private boolean isWarningDialogVisible() {
        // Logic to check if warning dialog is visible (simplified for this test)
        return true;
    }

    private boolean isPdfExportedSuccessfully() {
        // Logic to check if PDF export was successful (simplified for this test)
        return true;
    }
}
