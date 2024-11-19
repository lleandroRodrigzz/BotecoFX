module com.example.botecofx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.logging;
    requires org.json;
    requires java.desktop;
    requires javafx.swing;
    requires ij;
    requires jasperreports;
    requires javafx.web;


    opens com.example.botecofx to javafx.fxml;
    opens com.example.botecofx.db.entidades to javafx.fxml;
    exports com.example.botecofx.db.entidades;
    exports com.example.botecofx;
}