package studentmanagement.pdf;

import studentmanagement.util.Constants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import javax.swing.*;
import javax.swing.table.TableModel;
import java.io.FileOutputStream;

public class PdfExporter {
    public void exportToPDF(JTable table, JFrame parent) {
        try {
            // Initialize PDF writer and document
            PdfWriter writer = new PdfWriter(new FileOutputStream(Constants.PDF_OUTPUT_PATH));
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            // Create table with column count from JTable
            Table pdfTable = new Table(table.getColumnCount());

            // Add headers
            for (int i = 0; i < table.getColumnCount(); i++) {
                pdfTable.addHeaderCell(new Cell().add(new Paragraph(table.getColumnName(i))));
            }

            // Add data
            TableModel model = table.getModel();
            for (int i = 0; i < model.getRowCount(); i++) {
                for (int j = 0; j < model.getColumnCount(); j++) {
                    pdfTable.addCell(new Cell().add(new Paragraph(model.getValueAt(i, j).toString())));
                }
            }

            // Add table to document and close
            document.add(pdfTable);
            document.close();
            JOptionPane.showMessageDialog(parent, "PDF exported successfully!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(parent, "Error exporting PDF: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}