module studentmanagement.ui {
    requires java.desktop; // For Swing
    requires com.jtattoo; // For JTattoo Look and Feel
    requires studentmanagement.db; // For DatabaseManager
    requires studentmanagement.pdf; // For PdfExporter

    exports com.studentmanagement.ui;
}