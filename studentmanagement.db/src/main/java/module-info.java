module studentmanagement.db {
    requires java.sql;
    requires transitive studentmanagement.util;
    requires java.desktop;

    exports studentmanagement.db;
}