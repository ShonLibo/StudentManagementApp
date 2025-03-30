package studentmanagement.pdf;

import org.junit.jupiter.api.*;
import studentmanagement.util.Constants;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.File;
import static org.junit.jupiter.api.Assertions.*;

class PdfExporterTest {
    private PdfExporter pdfExporter;
    private JTable table;
    private JFrame parent;
    private static final String TEST_PDF_PATH = "test_output.pdf";

    @BeforeEach
    void setUp() {
        pdfExporter = new PdfExporter();
        parent = new JFrame();

        // Create a JTable with sample data
        String[] columns = {"ID", "Name", "Grade"};
        Object[][] data = {
                {"1", "Alice", "A"},
                {"2", "Bob", "B"}
        };
        table = new JTable(new DefaultTableModel(data, columns));
    }

    @Test
    void exportToPDF_createsFileSuccessfully() {
        File file = new File(TEST_PDF_PATH);

        // Ensure file doesn't exist before test
        if (file.exists()) {
            file.delete();
        }

        // Set a temporary output path
        Constants.PDF_OUTPUT_PATH = TEST_PDF_PATH;

        assertDoesNotThrow(() -> pdfExporter.exportToPDF(table, parent),
                "Exporting PDF should not throw an exception.");

        // Verify file was created
        assertTrue(file.exists(), "PDF file should be created successfully.");

        // Cleanup
        file.delete();
    }
}
