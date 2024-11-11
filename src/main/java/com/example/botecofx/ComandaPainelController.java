package com.example.botecofx;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.FlowPane;
import javafx.scene.Parent;

import java.net.URL;
import java.util.ResourceBundle;

public class ComandaPainelController implements Initializable {
    public FlowPane flowPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        carregarComandas();
    }

    private void carregarComandas(){
        //obter comandas em aberto no banco
        //colocar as comandas na tela
            for (int i = 0; i < 10; i++) {  //<--SIMULAÇÃO
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("comanda-view.fxml"));
                    Parent root = (Parent) loader.load();
                    ComandaController ctr = loader.getController();
                    ctr.setNumeroComanda(i);  // executa o método

                    flowPane.getChildren().add(root);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
    }
}
