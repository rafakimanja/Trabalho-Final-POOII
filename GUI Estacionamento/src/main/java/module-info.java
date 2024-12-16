module main.gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.compiler;
    requires com.fasterxml.jackson.databind;
    requires com.google.gson;
    requires java.sql;

    opens main.gui to javafx.fxml;
    opens main.gui.controllers to javafx.fxml;
    opens main.gui.models.Pessoa to com.google.gson;
    opens main.gui.models.Vaga to javafx.base;
    opens main.gui.models.Veiculo to javafx.base;

    exports main.gui;
    exports main.gui.controllers;
    exports main.gui.models.Veiculo;
    opens main.gui.models.Registro to javafx.base;
}