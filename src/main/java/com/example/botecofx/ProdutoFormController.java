package com.example.botecofx;

import com.example.botecofx.db.dals.GarconDAL;
import com.example.botecofx.db.dals.ProdutoDAL;
import com.example.botecofx.db.entidades.Categoria;
import com.example.botecofx.db.entidades.Garcon;
import com.example.botecofx.db.entidades.Produto;
import com.example.botecofx.db.entidades.Unidade;
import com.example.botecofx.db.util.SingletonDB;
import com.example.botecofx.util.MaskFieldUtil;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ResourceBundle;

public class ProdutoFormController implements Initializable {
    public TextField tfIDProd;
    public TextField tfIDCateg;
    public TextField tfIDUnid;
    public TextField tfNomeProd;
    public TextField tfDescricaoProd;
    public TextField tfPreco;
    public Button btConfirmarProd;
    public Button btCancelarProd;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                tfNomeProd.requestFocus();
            }
        });
        // se for uma alteração
        if(ProdutoConsultaController.produto != null)
        {
            Produto aux= ProdutoConsultaController.produto;
            // preencher os dados do produto
            tfNomeProd.setText(aux.getNome());
        }
    }

    public void onConfimarProd(ActionEvent actionEvent) {
        try {
            if (tfNomeProd.getText().isEmpty() || tfPreco.getText().isEmpty()) {
                throw new IllegalArgumentException("Nome e Preço são obrigatórios.");
            }

            // Converter o preço
            double preco = Double.parseDouble(tfPreco.getText());

            // Criar objetos de Categoria e Unidade (fictícios neste exemplo, use DALs para buscar do banco)
            Categoria categoria = new Categoria();
            categoria.setId(Integer.parseInt(tfIDCateg.getText())); // Usar ID da categoria

            Unidade unidade = new Unidade();
            unidade.setId(Integer.parseInt(tfIDUnid.getText())); // Usar ID da unidade

            // Criar o produto
            Produto prod = new Produto(
                    tfNomeProd.getText(),
                    preco,
                    tfDescricaoProd.getText(),
                    categoria,
                    unidade
            );

            // Gravar no banco
            if (!new ProdutoDAL().gravar(prod)) {
                throw new RuntimeException("Erro ao gravar o produto: " +
                        SingletonDB.getConexao().getMensagemErro());
            }

            // Fechar o formulário
            btConfirmarProd.getScene().getWindow().hide();

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Preço inválido. Por favor, insira um número.");
            alert.showAndWait();
        } catch (IllegalArgumentException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Erro inesperado: " + e.getMessage());
            alert.showAndWait();
        }
    }

    public void onCancelarProd(ActionEvent actionEvent) {
        btCancelarProd.getScene().getWindow().hide();
    }
}
