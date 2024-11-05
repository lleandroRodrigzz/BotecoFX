package com.example.botecofx;

import com.example.botecofx.db.dals.CategoriaDAL;
import com.example.botecofx.db.dals.UnidadeDAL;
import com.example.botecofx.db.entidades.Categoria;
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
        Unidade unidade = new Unidade(tfNomeUnidade.getText());
        if(!new UnidadeDAL().gravar(unidade))
        {
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Erro ao gravar a Unidade \n"+
                    SingletonDB.getConexao().getMensagemErro());
            alert.showAndWait();
        }
        btConfirmarUnidade.getScene().getWindow().hide();
    }

    public void onCancelarUnidade(ActionEvent actionEvent) {
        btCancelarUnidade.getScene().getWindow().hide();
    }
}
