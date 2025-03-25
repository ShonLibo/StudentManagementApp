package com.studentmanagement.pdf;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PdfExporter {
    public void exportToPDF(JTable studentTable, JFrame parent) {
        if (studentTable.getRowCount() == 0) {
            JOptionPane.showMessageDialog(parent, "No data to export!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save PDF File");

        String userHome = System.getProperty("user.home");
        fileChooser.setCurrentDirectory(new File(userHome + "/Documents"));
        fileChooser.setSelectedFile(new File("StudentRecord.pdf"));

        if (fileChooser.showSaveDialog(parent) == JFileChooser.APPROVE_OPTION) {
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

                Paragraph title = new Paragraph("Student Record Report")
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
                JOptionPane.showMessageDialog(parent, "PDF exported successfully to: " + filePath);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(parent, "Error exporting PDF: " + ex.getMessage() + "\nTry saving to a different location.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(parent, "Unexpected error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}