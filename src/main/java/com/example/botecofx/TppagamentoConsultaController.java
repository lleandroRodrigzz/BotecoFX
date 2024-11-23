package com.example.botecofx;

import com.example.botecofx.db.dals.TipoPagamentoDAL;
import com.example.botecofx.db.entidades.TipoPagamento;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TppagamentoConsultaController implements Initializable {

    public TextField tfFiltroTpPagamento;
    public TableView <TipoPagamento> tabelaTpPagamento;
    public TableColumn <TipoPagamento, String> colIDTpPagamento;
    public TableColumn <TipoPagamento, String> colNomeTpPagamento;
    private TipoPagamentoDAL tipoPagamentoDAL;
    public static TipoPagamento tipoPagamento = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tipoPagamentoDAL = new TipoPagamentoDAL();
        colIDTpPagamento.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNomeTpPagamento.setCellValueFactory(new PropertyValueFactory<>("nome"));
        preencherTabela("");
    }

    private void preencherTabela(String filtro) {
        List<TipoPagamento> tipoPagList = new ArrayList<>();
        tipoPagList.add(new TipoPagamento(1,"Cartão Crédito"));
        tipoPagList.add(new TipoPagamento(2,"Cartão Débito"));
        tipoPagList.add(new TipoPagamento(3,"Pix"));
        tabelaTpPagamento.setItems(FXCollections.observableArrayList(tipoPagList));
    }

    public void onFiltroTpPagamento(KeyEvent keyEvent) {
        String filtro=tfFiltroTpPagamento.getText().toUpperCase();
        preencherTabela("upper(tpg_nome) LIKE '"+filtro+"'");
    }

    public void onNovoTpPagamento(ActionEvent actionEvent) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(BotecoFX.class.getResource("tppagamento-form-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage=new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.showAndWait();
        tfFiltroTpPagamento.setText("");
        preencherTabela("");
    }

    public void onFecharTpPagamento(ActionEvent actionEvent) {
        tfFiltroTpPagamento.getScene().getWindow().hide();
    }

    public void onAlterar(ActionEvent actionEvent) throws Exception{
        if(tabelaTpPagamento.getSelectionModel().getSelectedIndex() >= 0) {
            tipoPagamento = tabelaTpPagamento.getSelectionModel().getSelectedItem();
            FXMLLoader fxmlLoader = new FXMLLoader(BotecoFX.class.getResource("tppagamento-form-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.showAndWait();
            tipoPagamento = null;
            tfFiltroTpPagamento.setText("");
            preencherTabela("");
        }
    }

    public void onExcluir(ActionEvent actionEvent) {
        if(tabelaTpPagamento.getSelectionModel().getSelectedIndex() >= 0) {
            TipoPagamento tpagt = tabelaTpPagamento.getSelectionModel().getSelectedItem();
            Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Deseja excluir a Categoria "+tpagt.getNome());
            if(alert.showAndWait().get()== ButtonType.OK)
            {
                tipoPagamentoDAL.apagar(tpagt);
                onFiltroTpPagamento(null);
            }
        }
    }
}
