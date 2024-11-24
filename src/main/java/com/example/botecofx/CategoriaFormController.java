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

public class CategoriaFormController implements Initializable {

    public TextField tfIDCategoria;
    public TextField tfNomeCategoria;
    public Button btConfirmarCategoria;
    public Button btCancelarCategoria;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                tfNomeCategoria.requestFocus();
            }
        });
        // se for uma alteração
        if(CategoriaConsultaController.categoria != null){
            Categoria aux = CategoriaConsultaController.categoria;
            // preencher os dados
            tfNomeCategoria.setText(aux.getNome());
        }
    }

    public void onConfimarCategoria(ActionEvent actionEvent) {
        Categoria categ = new Categoria(tfNomeCategoria.getText());
        if(!tfIDCategoria.getText().isEmpty())    //é edição
        {
            categ.setId(Integer.parseInt(tfIDCategoria.getText()));
            if(!new CategoriaDAL().alterar(categ)){
                throw new RuntimeException("Erro ao atualizar a Categoria");
            }
        }
        else{   //é adição
            if(!new CategoriaDAL().gravar(categ)){
                throw new RuntimeException("Erro ao gravar a Categoria");
            }
        }
        btConfirmarCategoria.getScene().getWindow().hide();
    }

    public void onCancelarCategoria(ActionEvent actionEvent) {
        btCancelarCategoria.getScene().getWindow().hide();
    }

    public void setCategoria(Categoria selecionado) {
        tfIDCategoria.setText(String.valueOf(selecionado.getId()));
        tfNomeCategoria.setText(selecionado.getNome());
    }
}
