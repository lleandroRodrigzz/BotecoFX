package com.example.botecofx;

import com.example.botecofx.db.dals.CategoriaDAL;
import com.example.botecofx.db.dals.UnidadeDAL;
import com.example.botecofx.db.entidades.Categoria;
import com.example.botecofx.db.entidades.TipoPagamento;
import com.example.botecofx.db.entidades.Unidade;
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

public class UnidadeConsultaController implements Initializable {
    public TextField tfFiltroUnidade;
    public TableView <Unidade> tabelaUnidade;
    public TableColumn <Unidade,String> colIDUnid;
    public TableColumn <Unidade,String> colNomeUnid;
    private UnidadeDAL unidadeDAL;
    public static Unidade unidade = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        unidadeDAL = new UnidadeDAL();
        colIDUnid.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNomeUnid.setCellValueFactory(new PropertyValueFactory<>("nome"));
        preencherTabela("");
    }

    private void preencherTabela(String filtro) {
        List<Unidade> unidadeList = unidadeDAL.get(filtro);
        tabelaUnidade.setItems(FXCollections.observableArrayList(unidadeList));
    }

    public void onFiltroUnidade(KeyEvent keyEvent) {
        String filtro = tfFiltroUnidade.getText().toUpperCase();
        preencherTabela("upper(uni_nome) LIKE '%" + filtro + "%'");
    }

    public void onNovoUnidade(ActionEvent actionEvent) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(BotecoFX.class.getResource("unidade-form-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage=new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.showAndWait();
        tfFiltroUnidade.setText("");
        preencherTabela("");
    }

    public void onFecharUnidade(ActionEvent actionEvent) {
        tfFiltroUnidade.getScene().getWindow().hide();
    }

    public void onAlterar(ActionEvent actionEvent) throws Exception{
        if(tabelaUnidade.getSelectionModel().getSelectedIndex() >= 0) {
            Unidade selecionado = tabelaUnidade.getSelectionModel().getSelectedItem();
            FXMLLoader fxmlLoader = new FXMLLoader(BotecoFX.class.getResource("unidade-form-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);

            UnidadeFormController controller = fxmlLoader.getController();
            controller.setUnidade(selecionado); // Passa o objeto selecionado para o formulário
            stage.showAndWait();
            preencherTabela("");
        }
    }

    public void onExcluir(ActionEvent actionEvent) {
        if(tabelaUnidade.getSelectionModel().getSelectedIndex() >= 0) {
            Unidade unid = tabelaUnidade.getSelectionModel().getSelectedItem();
            Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Deseja excluir a Unidade "+unid.getNome());
            if(alert.showAndWait().get()== ButtonType.OK)
            {
                unidadeDAL.apagar(unid);
                onFiltroUnidade(null);
            }
        }
    }
}
