module main.exemplo_javafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jdk.compiler;

    opens main.exemplo_javafx to javafx.fxml;
    opens main.exemplo_javafx.controller to javafx.fxml;

    opens main.exemplo_javafx.models.Vaga to javafx.base;

    exports main.exemplo_javafx;
    exports main.exemplo_javafx.controller;
}
