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
    private JFrame dummyFrame;

    @BeforeEach
    void setUp() {
        pdfExporter = new PdfExporter();
        dummyFrame = new JFrame();

        // Mock JTable with sample student data
        String[] columns = {"RegNo", "Name", "Course", "Grade"};
        Object[][] data = {
                {"2023-CS-001", "John Doe", "Computer Science", "A"},
                {"2023-CS-002", "Jane Smith", "Software Engineering", "B+"}
        };
        table = new JTable(new DefaultTableModel(data, columns));
    }

    @Test
    void testExportToPDF() {
        // AssertDoesNotThrow - Ensure exportToPDF does not throw exceptions
        assertDoesNotThrow(() -> pdfExporter.exportToPDF(table, dummyFrame),
                "exportToPDF should execute without exceptions.");

        // Verify if the PDF file was created
        File pdfFile = new File(Constants.PDF_OUTPUT_PATH);
        assertTrue(pdfFile.exists(), "The PDF file should be created after export.");
    }

    @Test
    void testInstanceOfPdfExporter() {
        // AssertInstanceOf - Verify the object type
        assertInstanceOf(PdfExporter.class, pdfExporter, "pdfExporter should be an instance of PdfExporter.");
    }
}
