package com.example.botecofx;

import com.example.botecofx.db.dals.GarconDAL;
import com.example.botecofx.db.dals.ProdutoDAL;
import com.example.botecofx.db.entidades.Categoria;
import com.example.botecofx.db.entidades.Garcon;
import com.example.botecofx.db.entidades.Produto;
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

public class ProdutoConsultaController implements Initializable {
    public TextField tfFiltroProduto;
    public TableView <Produto> tabelaProduto;
    public TableColumn <Produto, String> colIDProduto;
    public TableColumn <Produto, String> colNome;
    public TableColumn <Produto, String> colPreco;
    public TableColumn <Produto, String> colDescricao;
    private ProdutoDAL produtoDAL;
    public static Produto produto = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        produtoDAL = new ProdutoDAL();
        colIDProduto.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
        colDescricao.setCellValueFactory(new PropertyValueFactory<>("descr"));
        preencherTabela("");
    }

    private void preencherTabela(String filtro) {
        List<Produto> prodList = produtoDAL.get(filtro);
        tabelaProduto.setItems(FXCollections.observableArrayList(prodList));
    }

    public void onFiltro(KeyEvent keyEvent) {
        String filtro = tfFiltroProduto.getText().toUpperCase();
        preencherTabela("upper(prod_nome) LIKE '%" + filtro + "%'");
    }

    public void onNovoProduto(ActionEvent actionEvent) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(BotecoFX.class.getResource("produto-form-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage=new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.showAndWait();
        tfFiltroProduto.setText("");
        preencherTabela("");
    }

    public void onFecharProduto(ActionEvent actionEvent) {
        tfFiltroProduto.getScene().getWindow().hide();
    }

    public void onAlterarProduto(ActionEvent actionEvent) throws Exception{
        if(tabelaProduto.getSelectionModel().getSelectedIndex() >= 0) {
            Produto selecionado = tabelaProduto.getSelectionModel().getSelectedItem();
            FXMLLoader fxmlLoader = new FXMLLoader(BotecoFX.class.getResource("produto-form-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);

            ProdutoFormController controller = fxmlLoader.getController();
            controller.setProduto(selecionado); // Passa o objeto selecionado para o formulário
            stage.showAndWait();
            preencherTabela("");
        }
    }

    public void onExcluirProduto(ActionEvent actionEvent) {
        if(tabelaProduto.getSelectionModel().getSelectedIndex()>=0) {
            Produto prod = tabelaProduto.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Deseja excluir o Produto "+prod.getNome());
            if(alert.showAndWait().get()== ButtonType.OK)
            {
                produtoDAL.apagar(prod);
                onFiltro(null);
            }
        }
    }
}
