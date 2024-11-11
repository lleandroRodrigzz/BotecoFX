package com.example.botecofx;

import com.example.botecofx.db.dals.ProdutoDAL;
import com.example.botecofx.db.entidades.Comanda;
import com.example.botecofx.db.entidades.Garcon;
import com.example.botecofx.db.entidades.Produto;
import com.example.botecofx.db.entidades.TipoPagamento;
import com.example.botecofx.util.ModalTable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ComandaFormController {
    @FXML
    public Button btPruduto;

    @FXML
    private ComboBox<Garcon> cbGarcon;

    @FXML
    private ComboBox<TipoPagamento> cbTipoPagamento;

    @FXML
    private TableColumn<Comanda.Item, String> colProduto;

    @FXML
    private TableColumn<Comanda.Item, String> colQuant;

    @FXML
    private TableColumn<Comanda.Item, String> colValor;

    @FXML
    private DatePicker dpData;

    @FXML
    private Label lbValor;

    @FXML
    private Spinner<Integer> spQuant;

    @FXML
    private TextArea taDescr;

    @FXML
    private TableView<Comanda.Item> tableView;

    @FXML
    private TextField tfNome;

    @FXML
    private TextField tfNumero;

    private Produto p = null;

    @FXML
    void onAdd(ActionEvent event) {

    }

    @FXML
    void onCancelar(ActionEvent event) {
        btPruduto.getScene().getWindow().hide();
    }

    @FXML
    void onConfirmarComanda(ActionEvent event) {

    }

    @FXML
    void onFinalizarComanda(ActionEvent event) {

    }

    @FXML
    void onImprimirComanda(ActionEvent event) {

    }

    @FXML
    void onSelProduto(ActionEvent event) {
        ModalTable modalTable = new ModalTable(new ProdutoDAL().get(""),
                                                new String[]{"nome","preco","categoria"},
                                                "nome"
        );
        Stage stage=new Stage();
        stage.setScene(new Scene(modalTable));
        stage.setWidth(600); stage.setHeight(480); stage.initStyle(StageStyle.UNDECORATED);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        p = (Produto) modalTable.getSelecionado();
        btPruduto.setText(p.toString());
    }
}
