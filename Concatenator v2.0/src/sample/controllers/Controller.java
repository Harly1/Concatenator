package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import sample.Main;
import sample.Work;
import sample.controllers.ControllerModalWindow;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    ControllerModalWindow controllerModalWindow; // переменная контроллера модального окна
    public Button b1;
    public Button b4;
    public TextField text1;
    public TextField text2;
    public Label lb3;
    Alert alert = new Alert(Alert.AlertType.WARNING);
    FileChooser fileChooser = new FileChooser();
    FXMLLoader fxmlLoader;  // загрузчик для модального окна
    Parent root; // загрузчик для модального окна
    private Stage primaryStage = null;
    @Override
    @FXML
    public void initialize(URL location, ResourceBundle resources)  {
        text1.setText("C:\\JavaTest\\Тест1.txt");
        text2.setText("C:\\JavaTest\\Тест2.txt");
        //Получение экземпляра контроллера модального окна
        fxmlLoader = new FXMLLoader(getClass().getResource("../fxml/ModalWindow.fxml"));
        try {
             root = (Parent)fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        controllerModalWindow = (ControllerModalWindow) fxmlLoader.getController();
        controllerModalWindow.init(this);
        //
        b4.setVisible(false);

    }


    public void setStage(Stage stage) {
        this.primaryStage = stage;
    }

    public void OnActionb1(ActionEvent actionEvent) throws IOException {
        String s =  System.getProperty("file.separator");

        if (text1.getText().isEmpty() && text2.getText().isEmpty()) {
            alert.setTitle("Некорректный ввод");
            alert.setHeaderText(null);
            alert.setContentText("Не указаны пути к файлам");
            alert.showAndWait();

        } else if (text1.getText().isEmpty()) {
            alert.setTitle("Некорректный ввод пути к файлу");
            alert.setHeaderText(null);
            alert.setContentText("Не указан путь к файлу 1");
            alert.showAndWait();
            text1.requestFocus();

        } else if (text2.getText().isEmpty()) {
            alert.setTitle("Некорректный ввод пути к файлу");
            alert.setHeaderText(null);
            alert.setContentText("Не указан путь к файлу 2");
            alert.showAndWait();
            text2.requestFocus();

        } else if(!text1.getText().contains(s)) {
            alert.setTitle("Некорректный ввод пути к файлу");
            alert.setHeaderText(null);
            alert.setContentText("Некорректный ввод пути к файлу 1");
            alert.showAndWait();
            text1.requestFocus();

        } else if( !text2.getText().contains(s)) {
            alert.setTitle("Некорректный ввод пути к файлу");
            alert.setHeaderText(null);
            alert.setContentText("Некорректный ввод пути к файлу 2");
            alert.showAndWait();
            text2.requestFocus();

        } else {

            Work application = new Work(text1.getText(),text2.getText());
            application.b4 = b4;
            application.lb3 = lb3;
            application.txtA1 = controllerModalWindow.txtA1;
            application.txtA2 = controllerModalWindow.txtA2;
            application.writeOutPutFile(application.getOutPutFileName(),
                    application.getShapka(),application.getResultList(application.getList1(),application.getList2()));
        }
    }
    public void onActionb2(ActionEvent actionEvent) {
        if(!lb3.getText().equals("")){         //Очищаем ярлык "Файл сгенерирован"
            lb3.setText("");
        }

        fileChooser.setTitle("Выберите файл с данными");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if(selectedFile != null){
             text1.setText( selectedFile.getAbsolutePath());

        }
    }

    public void onActionb3(ActionEvent actionEvent) {
        if(!lb3.getText().equals("")){
            lb3.setText("");
        }
        fileChooser.setTitle("Выберите файл с данными");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if(selectedFile != null){
            text2.setText( selectedFile.getAbsolutePath());

        }
    }

    public void onActionb4(ActionEvent actionEvent) throws IOException {
        try {
            Stage stage = new Stage();
            stage.setTitle("Ошибки во входных файлах");
            stage.setMinHeight(300);
            stage.setMinWidth(600);
            stage.setResizable(false);
            stage.setScene(new Scene((root)));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(primaryStage);
            stage.show();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
