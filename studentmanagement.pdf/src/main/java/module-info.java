module studentmanagement.pdf {
    requires java.desktop;
    requires transitive studentmanagement.util;
    requires layout;

    exports studentmanagement.pdf;
}