package com.example.botecofx;

import com.example.botecofx.db.dals.CategoriaDAL;
import com.example.botecofx.db.entidades.Categoria;
import com.example.botecofx.db.entidades.Unidade;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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

public class CategoriaConsultaController implements Initializable {
    @FXML
    public TextField tfFiltroCategoria;

    @FXML
    public TableView <Categoria> tabelaCategoria;

    @FXML
    public TableColumn <Categoria,String> colIDCateg;

    @FXML
    public TableColumn <Categoria,String> colNomeCategoria;

    private CategoriaDAL categoriaDAL;
    public static Categoria categoria = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        categoriaDAL = new CategoriaDAL();
        colIDCateg.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNomeCategoria.setCellValueFactory(new PropertyValueFactory<>("nome"));
        preencherTabela("");
    }

    private void preencherTabela(String filtro) {
        List<Categoria> categoriaList = categoriaDAL.get(filtro);
        tabelaCategoria.setItems(FXCollections.observableArrayList(categoriaList));
    }

    public void onFiltroCategoria(KeyEvent keyEvent) {
        String filtro = tfFiltroCategoria.getText().toUpperCase();
        preencherTabela("upper(cat_nome) LIKE '%" + filtro + "%'");
    }

    public void onNovoCategoria(ActionEvent actionEvent) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(BotecoFX.class.getResource("categoria-form-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage=new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.showAndWait();
        tfFiltroCategoria.setText("");
        preencherTabela("");
    }

    public void onFecharCategoria(ActionEvent actionEvent) {
        tfFiltroCategoria.getScene().getWindow().hide();
    }

    public void onAlterar(ActionEvent actionEvent) throws Exception{
        if(tabelaCategoria.getSelectionModel().getSelectedIndex() >= 0) {
            Categoria selecionado = tabelaCategoria.getSelectionModel().getSelectedItem();
            FXMLLoader fxmlLoader = new FXMLLoader(BotecoFX.class.getResource("categoria-form-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);

            CategoriaFormController controller = fxmlLoader.getController();
            controller.setCategoria(selecionado); // Passa o objeto selecionado para o formulário
            stage.showAndWait();
            preencherTabela("");
        }
    }

    public void onExcluir(ActionEvent actionEvent) {
        if(tabelaCategoria.getSelectionModel().getSelectedIndex() >= 0) {
            Categoria categ = tabelaCategoria.getSelectionModel().getSelectedItem();
            Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Deseja excluir a Categoria "+categ.getNome());
            if(alert.showAndWait().get()== ButtonType.OK)
            {
                categoriaDAL.apagar(categ);
                onFiltroCategoria(null);
            }
        }
    }
}
