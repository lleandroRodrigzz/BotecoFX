package com.example.botecofx;

import com.example.botecofx.db.dals.ComandaDAL;
import com.example.botecofx.db.entidades.Comanda;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.FlowPane;
import javafx.scene.Parent;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ComandaPainelController implements Initializable {
    public FlowPane flowPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        carregarComandas();
    }

    private void carregarComandas() {
        try {
            ComandaDAL dao = new ComandaDAL();
            List<Comanda> comandasAbertas = dao.buscarComandasEmAberto();

            // Adicionar botão "Nova Comanda" (simbolizando a criação de uma nova comanda)
            FXMLLoader loaderNovaComanda = new FXMLLoader(getClass().getResource("novaComanda-view.fxml"));
            Parent rootNovaComanda = loaderNovaComanda.load();
            flowPane.getChildren().add(rootNovaComanda);

            // Carregar as comandas reais do banco
            for (Comanda comanda : comandasAbertas) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("comanda-view.fxml"));
                    Parent root = loader.load();

                    ComandaController controller = loader.getController();
                    controller.setNumeroComanda(comanda);
                    controller.lbValor.setText(String.format("R$ %.2f", comanda.getValor()));

                    flowPane.getChildren().add(root);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onComandaFechada(ActionEvent actionEvent) {
    }

    public void onRecarregar(ActionEvent actionEvent) {

    }
}
