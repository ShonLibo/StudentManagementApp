module studentmanagement.ui {
    requires java.desktop;
    requires transitive studentmanagement.db;
    requires transitive studentmanagement.pdf;
    requires transitive studentmanagement.util;
    requires JTattoo;
    requires kernel;
    requires layout;

    exports studentmanagement.ui;
}