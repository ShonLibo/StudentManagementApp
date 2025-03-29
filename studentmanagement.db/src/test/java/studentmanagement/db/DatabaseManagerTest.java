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
    void loadData() {
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[]{"RegNo", "Name", "Course", "Grade"});

        DatabaseManager dbManager = new DatabaseManager();
        dbManager.loadData(tableModel, null); // Assuming 'null' is okay for JFrame

        System.out.println("Loaded rows: " + tableModel.getRowCount());
        System.out.println("Table Data Vector: " + tableModel.getDataVector());

        for (int i = 0; i < tableModel.getRowCount(); i++) {
            System.out.println("Row " + i + ": " + tableModel.getValueAt(i, 0) + ", "
                    + tableModel.getValueAt(i, 1) + ", "
                    + tableModel.getValueAt(i, 2) + ", "
                    + tableModel.getValueAt(i, 3));
        }

        assertTrue(tableModel.getRowCount() > 0, "Expected student data was not found in loaded data.");
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
