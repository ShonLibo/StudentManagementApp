package studentmanagement.pdf;

import com.itextpdf.layout.element.Paragraph;
import studentmanagement.util.Constants;
import javax.swing.*;
import javax.swing.table.JTableHeader;
import javax.swing.text.Document;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.awt.*;
import java.io.FileOutputStream;

public class PdfExporter {
    public void exportToPDF(JTable table, JFrame parent) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(Constants.PDF_EXPORT_FILENAME));
            document.open();

            // Add title
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Paragraph title = new Paragraph(Constants.PDF_EXPORT_TITLE, titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20f);
            document.add(title);

            // Create table
            PdfPTable pdfTable = new PdfPTable(table.getColumnCount());
            pdfTable.setWidthPercentage(100);

            // Add table headers
            JTableHeader header = table.getTableHeader();
            for (int i = 0; i < table.getColumnCount(); i++) {
                PdfPCell cell = new PdfPCell(new Phrase(header.getColumnModel().getColumn(i).getHeaderValue().toString()));
                cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                pdfTable.addCell(cell);
            }

            // Add table data
            for (int i = 0; i < table.getRowCount(); i++) {
                for (int j = 0; j < table.getColumnCount(); j++) {
                    pdfTable.addCell(table.getValueAt(i, j).toString());
                }
            }

            document.add(pdfTable);
            document.close();

            JOptionPane.showMessageDialog(parent, "PDF exported successfully to " + Constants.PDF_EXPORT_FILENAME,
                    "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(parent, "Error exporting PDF: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}