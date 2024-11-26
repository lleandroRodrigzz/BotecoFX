package com.example.botecofx;

import com.example.botecofx.db.entidades.Comanda;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ComandaController {
    public AnchorPane anchorPane;
    public Label lbNumComanda;
    public Label lbValor;
    public static int id;

    public void onGerenciarComanda(MouseEvent mouseEvent) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(BotecoFX.class.getResource("comanda-form-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage=new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.showAndWait();
    }

    public void setNumeroComanda(Comanda comanda) {
        // Buscar o numero da comanda pelo id recebido
        this.id = comanda.getNumero();
        id = comanda.getId();
        lbNumComanda.setText(""+id);
        // Atualizar o valor
        lbValor.setText("" + (comanda.getValor() - new ComandaFormController().valorPago));
    }

    public void onNovaComanda(MouseEvent mouseEvent) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(BotecoFX.class.getResource("comanda-cad-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage=new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }
}
