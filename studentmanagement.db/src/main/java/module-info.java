module studentmanagement.db {
    requires java.sql; // For JDBC
    requires studentmanagement.util; // For Constants
    requires java.desktop; // For JOptionPane

    exports com.studentmanagement.db;
}