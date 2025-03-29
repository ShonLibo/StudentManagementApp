package studentmanagement.ui;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.table.DefaultTableModel;

import static org.junit.jupiter.api.Assertions.*;

class StudentManagementAppTest {
    private StudentManagementApp app;

    @BeforeEach
    void setUp() {
        app = new StudentManagementApp();
        app.tableModel.setRowCount(0); // Clear table before each test
        app.tableModel.addRow(new Object[]{"123", "Alice", "Math", "A"});
        app.tableModel.addRow(new Object[]{"124", "Bob", "Science", "B"});
        app.tableModel.addRow(new Object[]{"125", "Charlie", "Math", "A"});
    }

    @Test
    void filterTable() {
        app.searchField.setText("Alice");
        app.filterTable();
        assertEquals(1, app.studentTable.getRowCount(), "Search should return exactly 1 result");
    }

    @Test
    void filterTable_multipleMatches() {
        app.searchField.setText("Math");
        app.filterTable();
        assertEquals(2, app.studentTable.getRowCount(), "Search should return exactly 2 results");
    }

    @Test
    void filterTable_noMatches() {
        app.searchField.setText("History");
        app.filterTable();
        assertEquals(0, app.studentTable.getRowCount(), "Search should return 0 results");
    }

    @Test
    void main() {
        assertDoesNotThrow(() -> StudentManagementApp.main(new String[]{}), "Application should start without exceptions");
    }
}
