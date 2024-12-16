package main.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {

    private static Stage stage;

    @Override
    public void start(Stage primaryStage) throws IOException {
        stage = primaryStage;
        loadScene("main-view");
        primaryStage.show();
    }

    public static void loadScene(String view, Object dados){
        view = "views/"+view+".fxml";
        try{
            BorderPane root = (BorderPane)FXMLLoader.load(MainApplication.class.getResource(view));
            Scene scene = new Scene(root, 1280 , 920);

            if(dados != null){
                root.setUserData(dados);
            }

            stage.setScene(scene);
            stage.show();

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void loadScene(String view){
        loadScene(view, null);
    }

    public static void main(String[] args) {
        launch();
    }
}