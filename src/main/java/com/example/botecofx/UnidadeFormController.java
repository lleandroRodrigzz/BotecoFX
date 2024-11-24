package com.example.botecofx;

import com.example.botecofx.db.dals.CategoriaDAL;
import com.example.botecofx.db.dals.TipoPagamentoDAL;
import com.example.botecofx.db.dals.UnidadeDAL;
import com.example.botecofx.db.entidades.Categoria;
import com.example.botecofx.db.entidades.TipoPagamento;
import com.example.botecofx.db.entidades.Unidade;
import com.example.botecofx.db.util.SingletonDB;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class UnidadeFormController implements Initializable {
    public TextField tfIDUnidade;
    public TextField tfNomeUnidade;
    public Button btConfirmarUnidade;
    public Button btCancelarUnidade;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                tfNomeUnidade.requestFocus();
            }
        });
        // se for uma alteração
        if(UnidadeConsultaController.unidade != null){
            Categoria aux = CategoriaConsultaController.categoria;
            // preencher os dados
            tfNomeUnidade.setText(aux.getNome());
        }
    }

    public void onConfimarUnidade(ActionEvent actionEvent) {
        Unidade unid = new Unidade(tfNomeUnidade.getText());
        if(!tfIDUnidade.getText().isEmpty())    //é edição
        {
            unid.setId(Integer.parseInt(tfIDUnidade.getText()));
            if(!new UnidadeDAL().alterar(unid)){
                throw new RuntimeException("Erro ao atualizar a Unidade");
            }
        }
        else{   //é adição
            if(!new UnidadeDAL().gravar(unid)){
                throw new RuntimeException("Erro ao gravar a Unidade");
            }
        }
        btConfirmarUnidade.getScene().getWindow().hide();
    }

    public void onCancelarUnidade(ActionEvent actionEvent) {
        btCancelarUnidade.getScene().getWindow().hide();
    }

    public void setUnidade(Unidade selecionado) {
        tfIDUnidade.setText(String.valueOf(selecionado.getId()));
        tfNomeUnidade.setText(selecionado.getNome());
    }
}
