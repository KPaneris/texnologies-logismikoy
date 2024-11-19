module org.example.demo1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires com.google.api.client;
    requires com.google.api.services.youtube;
    requires java.desktop;

    requires com.google.api.client.json.jackson2;
    requires org.json;

    requires google.api.client;
    requires jlayer;

    opens org.example.demo1 to javafx.fxml;
    exports org.example.demo1;
}