package sample.controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerModalWindow implements Initializable {
    @FXML
    TextArea txtA1;
    @FXML
    TextArea txtA2;
    @FXML
    Controller mainController;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    public void init(Controller main) {
        mainController = main;
        //mainController.text1.setText("Передача значения");
    }
    public void errorLogList1(String row) {
        txtA1.setText(row);

    }

    public void errorLogList2(String row) {


    }


}