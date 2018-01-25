package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.controllers.Controller;

public class Main extends Application {
// Проверка ГИТ!!!
// Проверка 2 XXX!!!!!!!
// Проверка 3
//    Проверка 4
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(getClass().getResource("fxml/Main.fxml").openStream());
        Controller controller = fxmlLoader.getController();
        controller.setStage(primaryStage);
        primaryStage.setTitle("Главное окно");
        primaryStage.setScene(new Scene(root, 377, 129));
        primaryStage.show();

    }


    public static void main(String[] args) {

        launch(args);
    }

}