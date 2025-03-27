package studentmanagement.util;

import static org.junit.jupiter.api.Assertions.*;

class ConstantsTest {

    @org.junit.jupiter.api.Test
    void testDBUrl() {
        assertEquals("jdbc:mysql://localhost:3306/student_db", Constants.DB_URL);
    }

    @org.junit.jupiter.api.Test
    void testDBUser() {
        assertEquals("root", Constants.DB_USER);
    }

    @org.junit.jupiter.api.Test
    void testDBPassword() {
        assertEquals("danguru", Constants.DB_PASSWORD);
    }

    @org.junit.jupiter.api.Test
    void testTableColumns() {
        String[] expectedColumns = {"RegNo", "Name", "Course", "Grade"};
        assertArrayEquals(expectedColumns, Constants.TABLE_COLUMNS);
    }

    @org.junit.jupiter.api.Test
    void testPDFOutputPath() {
        assertEquals("student_records.pdf", Constants.PDF_OUTPUT_PATH);
    }
}
