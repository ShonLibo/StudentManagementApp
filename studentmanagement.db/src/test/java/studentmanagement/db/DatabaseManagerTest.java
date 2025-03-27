package studentmanagement.db;

import org.junit.jupiter.api.*;
import studentmanagement.util.Constants;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseManagerTest {
    private DatabaseManager dbManager;
    private DefaultTableModel model;

    @BeforeEach
    void setUp() {
        dbManager = new DatabaseManager();
        model = new DefaultTableModel(new String[]{"RegNo", "Name", "Course", "Grade"}, 0);
    }

    @Test
    void saveStudent() {
        JFrame dummyFrame = new JFrame();
        String regNo = "2023-CS-001";
        String name = "John Doe";
        String course = "Computer Science";
        String grade = "A";

        // AssertDoesNotThrow - Ensure saveStudent does not throw an exception
        assertDoesNotThrow(() -> dbManager.saveStudent(regNo, name, course, grade, dummyFrame),
                "saveStudent should execute without exceptions.");

        // Load data to verify insertion
        dbManager.loadData(model, dummyFrame);

        // AssertArrayEquals - Compare saved and loaded data
        Object[] expectedRow = {regNo, name, course, grade};
        Object[] actualRow = model.getDataVector().elementAt(0).toArray();

        assertArrayEquals(expectedRow, actualRow, "Saved student data should match loaded data.");
    }

    @Test
    void loadData() {
        JFrame dummyFrame = new JFrame();
        dbManager.loadData(model, dummyFrame);

        // Extract rows as formatted strings for comparison
        List<String> actualRows = model.getDataVector().stream()
                .map(row -> row.toString().replaceAll("[\\[\\]]", "")) // Remove brackets
                .toList();

        // Debugging - Print actual rows to compare
        System.out.println("Actual Rows: " + actualRows);

        // Ensure at least one expected row is present
        assertTrue(actualRows.stream().anyMatch(row -> row.contains("2023-CS-001, John Doe, Computer Science, A")),
                "Expected student data was not found in loaded data.");
    }


    @Test
    void close() {
        // AssertDoesNotThrow - Ensure close() does not cause exceptions
        assertDoesNotThrow(() -> dbManager.close(), "close() should execute without exceptions.");
    }

    @Test
    void testInstanceOfDatabaseManager() {
        // AssertInstanceOf - Verify dbManager is the correct instance
        assertInstanceOf(DatabaseManager.class, dbManager, "dbManager should be an instance of DatabaseManager.");
    }
}
