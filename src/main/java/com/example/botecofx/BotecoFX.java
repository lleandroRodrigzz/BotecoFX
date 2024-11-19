package com.example.botecofx;

import com.example.botecofx.db.util.SingletonDB;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.*;
import java.io.IOException;

public class BotecoFX extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(BotecoFX.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("BotecoFX");
        //stage.setMaximized(true);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        String mydatabase = "botecodb";
        if (SingletonDB.conectar())
            launch(args);
        else {
            JOptionPane.showMessageDialog(null,
                    "Problemas ao conectar: " + SingletonDB.getConexao().getMensagemErro());
            if (JOptionPane.showConfirmDialog(null, "Confirma a tentativa de criação de uma nova base de dados") == JOptionPane.YES_OPTION) {
                if (SingletonDB.criarBD(mydatabase))
                    try {
                        SingletonDB.restaurar("bkpinicial.backup", mydatabase);
                    } catch (Exception e) {
                    }
            }
            Platform.exit();
        }
    }
}