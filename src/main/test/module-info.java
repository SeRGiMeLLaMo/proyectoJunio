module test {
    requires javafx.controls;
    requires javafx.fxml;

    requires java.xml.bind;
    requires java.sql;
    requires javafx.base;
    requires javafx.graphics;
    requires java.desktop;
    requires org.junit.jupiter.api;
    requires org.junit.platform.commons;
    requires com.example.sexxop;

    opens test to org.junit.platform.commons;
    exports test;
}