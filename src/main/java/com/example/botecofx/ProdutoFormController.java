package com.example.botecofx;

import com.example.botecofx.db.dals.CategoriaDAL;
import com.example.botecofx.db.dals.GarconDAL;
import com.example.botecofx.db.dals.ProdutoDAL;
import com.example.botecofx.db.dals.UnidadeDAL;
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
            // Validar campos obrigatórios
            if (tfNomeProd.getText().isEmpty() || tfPreco.getText().isEmpty()) {
                throw new IllegalArgumentException("Nome e Preço são obrigatórios.");
            }

            double preco = Double.parseDouble(tfPreco.getText());

            // Validar ID da Categoria
            int idCategoria = Integer.parseInt(tfIDCateg.getText());
            CategoriaDAL categoriaDAL = new CategoriaDAL();
            Categoria categoria = categoriaDAL.get(idCategoria);
            if (categoria == null) {
                throw new IllegalArgumentException("Categoria com ID " + idCategoria + " não encontrada.");
            }

            // Validar ID da Unidade
            int idUnidade = Integer.parseInt(tfIDUnid.getText());
            UnidadeDAL unidadeDAL = new UnidadeDAL();
            Unidade unidade = unidadeDAL.get(idUnidade);
            if (unidade == null) {
                throw new IllegalArgumentException("Unidade com ID " + idUnidade + " não encontrada.");
            }

            // Criar o produto
            Produto prod = new Produto(
                    tfNomeProd.getText(),
                    preco,
                    tfDescricaoProd.getText(),
                    categoria,
                    unidade
            );

            // Verificar se é uma alteração ou uma nova inserção
            ProdutoDAL produtoDAL = new ProdutoDAL();
            if (!tfIDProd.getText().isEmpty()) {
                // Alteração
                prod.setId(Integer.parseInt(tfIDProd.getText()));
                if (!produtoDAL.alterar(prod)) {
                    throw new RuntimeException("Erro ao atualizar o produto: " +
                            SingletonDB.getConexao().getMensagemErro());
                }
            } else {
                // Inserção
                if (!produtoDAL.gravar(prod)) {
                    throw new RuntimeException("Erro ao gravar o produto: " +
                            SingletonDB.getConexao().getMensagemErro());
                }
            }

            // Fechar o formulário
            btConfirmarProd.getScene().getWindow().hide();

        } catch (NumberFormatException e) {
            // Tratamento para números inválidos
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("ID inválido ou Preço inválido. Por favor, insira números.");
            alert.showAndWait();
        } catch (IllegalArgumentException e) {
            // Tratamento para argumentos inválidos (ex.: categoria ou unidade não encontrada)
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        } catch (Exception e) {
            // Tratamento de erros inesperados
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Erro inesperado: " + e.getMessage());
            alert.showAndWait();
        }
    }

    public void onCancelarProd(ActionEvent actionEvent) {
        btCancelarProd.getScene().getWindow().hide();
    }

    public void setProduto(Produto selecionado) {
        tfIDProd.setText(String.valueOf(selecionado.getId()));
        tfIDCateg.setText(String.valueOf(selecionado.getCategoria().getId()));
        tfIDUnid.setText(String.valueOf(selecionado.getUnidade().getId()));
        tfNomeProd.setText(selecionado.getNome());
        tfPreco.setText(String.valueOf(selecionado.getPreco()));
        tfDescricaoProd.setText(selecionado.getDescr());
    }
}
