package com.example.botecofx;

import com.example.botecofx.db.dals.TipoPagamentoDAL;
import com.example.botecofx.db.entidades.TipoPagamento;
import com.example.botecofx.db.util.SingletonDB;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class TppagamentoFormController implements Initializable {
    public TextField tfIDTpPagamento;
    public TextField tfNomeTpPagamento;
    public Button btConfirmarTpPagamento;
    public Button btCancelarTpPagamento;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                tfNomeTpPagamento.requestFocus();
            }
        });
        // se for uma alteração
        if(TppagamentoConsultaController.tipoPagamento != null){
            TipoPagamento aux = TppagamentoConsultaController.tipoPagamento;
            // preencher os dados
            tfNomeTpPagamento.setText(aux.getNome());
        }
    }

    public void onConfimarTpPagamento(ActionEvent actionEvent) {
        TipoPagamento tipoPagamento = new TipoPagamento(tfNomeTpPagamento.getText());
        if(!new TipoPagamentoDAL().gravar(tipoPagamento))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Erro ao gravar o Tipo de Pagamento \n"+
                    SingletonDB.getConexao().getMensagemErro());
            alert.showAndWait();
        }
        btConfirmarTpPagamento.getScene().getWindow().hide();
    }

    public void onCancelarTpPagamento(ActionEvent actionEvent) {
        btCancelarTpPagamento.getScene().getWindow().hide();
    }
}
