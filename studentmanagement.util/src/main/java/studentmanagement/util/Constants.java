package studentmanagement.util;

public class Constants {
    public static final String DB_URL = "jdbc:mysql://localhost:3306/studentdb";
    public static final String DB_USER = "root";
    public static final String DB_PASSWORD = "password";
    public static final String[] TABLE_COLUMNS = {"RegNo", "Name", "Course", "Grade"};
    public static final String PDF_EXPORT_TITLE = "Student Records";
    public static final String PDF_EXPORT_FILENAME = "student_records.pdf";
}