package com.example.botecofx;

import com.example.botecofx.db.dals.ComandaDAL;
import com.example.botecofx.db.entidades.Comanda;
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
            // Adicionar o botão "Nova Comanda" (simbolizando a criação de uma nova comanda)
            FXMLLoader loaderNovaComanda = new FXMLLoader(getClass().getResource("novaComanda-view.fxml"));
            Parent rootNovaComanda = loaderNovaComanda.load();
            flowPane.getChildren().add(rootNovaComanda);

            // Adicionar comandas fictícias
            for (int i = 1; i <= 10; i++) { // Simulação: 10 comandas fictícias
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("comanda-view.fxml"));
                    Parent root = loader.load();

                    // Configurar controlador com dados fictícios
                    ComandaController controller = loader.getController();
                    controller.setNumeroComanda(i); // Configurar número da comanda fictícia
                    controller.lbValor.setText(String.format("R$ %.2f", Math.random() * 100)); // Valor fictício

                    // Adicionar ao FlowPane
                    flowPane.getChildren().add(root);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
