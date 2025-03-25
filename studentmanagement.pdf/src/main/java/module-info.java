module studentmanagement.pdf {
    requires itextpdf.kernel;
    requires itextpdf.layout;
    requires java.desktop; // For JTable, JOptionPane, JFileChooser
    requires org.slf4j; // For SLF4J logging

    exports com.studentmanagement.pdf;
}