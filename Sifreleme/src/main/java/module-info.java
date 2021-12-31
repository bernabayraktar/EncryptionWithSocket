module com.sinama.encryption {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires junit;


    opens com.sinama.encryption to javafx.fxml;
    exports com.sinama.encryption;
}