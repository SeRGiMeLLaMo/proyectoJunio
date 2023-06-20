module com.example.sexxop {
    requires javafx.controls;
    requires javafx.fxml;

    requires java.xml.bind;
    requires java.sql;
    requires javafx.base;
    requires javafx.graphics;
    requires java.desktop;
    //requires org.junit.jupiter.api;
    //requires org.junit.platform.commons;

    opens com.example.sexxop.model.connection to java.xml.bind;
    opens com.example.sexxop.model.domain to javafx.base;

    opens com.example.sexxop to javafx.fxml;
    exports com.example.sexxop;
    exports com.example.sexxop.model.domain to test;
}