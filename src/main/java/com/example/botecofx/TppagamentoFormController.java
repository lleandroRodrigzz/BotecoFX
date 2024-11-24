package com.example.botecofx;

import com.example.botecofx.db.dals.TipoPagamentoDAL;
import com.example.botecofx.db.entidades.Garcon;
import com.example.botecofx.db.entidades.TipoPagamento;
import com.example.botecofx.db.util.SingletonDB;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;

import java.awt.image.BufferedImage;
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

    public void setTpPagamento(TipoPagamento tppagt) {
        tfIDTpPagamento.setText(String.valueOf(tppagt.getId()));
        tfNomeTpPagamento.setText(tppagt.getNome());
    }

    public void onConfimarTpPagamento(ActionEvent actionEvent) {
        TipoPagamento tipoPagamento = new TipoPagamento(tfNomeTpPagamento.getText());
        if(!tfIDTpPagamento.getText().isEmpty())    //é edição
        {
            tipoPagamento.setId(Integer.parseInt(tfIDTpPagamento.getText()));
            if(!new TipoPagamentoDAL().alterar(tipoPagamento)){
                throw new RuntimeException("Erro ao atualizar o Tipo de Pagamento");
            }
        }
        else{   //é adição
            if(!new TipoPagamentoDAL().gravar(tipoPagamento)){
                throw new RuntimeException("Erro ao gravar o Tipo de Pagamento");
            }
        }
        btConfirmarTpPagamento.getScene().getWindow().hide();
    }

    public void onCancelarTpPagamento(ActionEvent actionEvent) {
        btCancelarTpPagamento.getScene().getWindow().hide();
    }
}
