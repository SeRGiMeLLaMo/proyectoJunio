module com.example.sexxop {
    requires javafx.controls;
    requires javafx.fxml;

    requires java.xml.bind;
    requires java.sql;
    requires javafx.base;
    requires javafx.graphics;
    requires java.desktop;


    opens com.example.sexxop.model.connection to java.xml.bind;
    opens com.example.sexxop to javafx.fxml;
    exports com.example.sexxop;
}