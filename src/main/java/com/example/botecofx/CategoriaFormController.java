package com.example.botecofx;

import com.example.botecofx.db.dals.CategoriaDAL;
import com.example.botecofx.db.entidades.Categoria;
import com.example.botecofx.db.entidades.Garcon;
import com.example.botecofx.db.util.SingletonDB;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
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
        Categoria categoria = new Categoria(tfNomeCategoria.getText());
        if(!new CategoriaDAL().gravar(categoria))
        {
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Erro ao gravar a Categoria \n"+
                    SingletonDB.getConexao().getMensagemErro());
            alert.showAndWait();
        }
        btConfirmarCategoria.getScene().getWindow().hide();
    }

    public void onCancelarCategoria(ActionEvent actionEvent) {
        btCancelarCategoria.getScene().getWindow().hide();
    }
}
