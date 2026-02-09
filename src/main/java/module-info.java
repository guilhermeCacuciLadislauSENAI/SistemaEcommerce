module com.example.ecommercesistema {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires jdk.compiler;
    requires java.sql;

    requires mysql.connector.j;


    opens com.example.ecommercesistema to javafx.fxml;
    exports com.example.ecommercesistema;
    exports com.example.ecommercesistema.controller;
    opens com.example.ecommercesistema.controller to javafx.fxml;
}