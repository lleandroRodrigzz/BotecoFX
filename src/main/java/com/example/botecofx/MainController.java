package com.example.botecofx;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainController {
    public PasswordField pfSenha;

    public void onAcessar(ActionEvent event) throws Exception{
        String view = "";

        if(pfSenha.getText().equals("adm")){
            //usuario adm
            view = "adm-view.fxml";
        }

        if(pfSenha.getText().equals("123")){
            //usuario basico
            view = "comanda-painel-view.fxml";
        }

        FXMLLoader fxmlLoader = new FXMLLoader(BotecoFX.class.getResource(view));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("BotecoFX");
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }
}
