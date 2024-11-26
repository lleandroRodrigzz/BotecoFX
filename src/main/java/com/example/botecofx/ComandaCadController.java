package com.example.botecofx;

import com.example.botecofx.db.dals.ComandaDAL;
import com.example.botecofx.db.dals.GarconDAL;
import com.example.botecofx.db.entidades.Comanda;
import com.example.botecofx.db.entidades.Garcon;
import com.example.botecofx.db.util.SingletonDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ComandaCadController implements Initializable {

    @FXML
    private ComboBox<Garcon> cbGarcom;

    @FXML
    private TextField tfNumComanda;

    @FXML
    private DatePicker dtCommand;

    @FXML
    private TextArea tfDescr;

    @FXML
    public void onConfirmarCad(ActionEvent event) {

    }

    private boolean buscaComanda(int numComanda) {
        List<Comanda> comandas = new ComandaDAL().get("");
        for(Comanda com:comandas) {
            if(com.getNumero() == numComanda)
                return false;
        }
        return true;
    }

    @FXML
    public void onCancelarCad(ActionEvent event) {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (SingletonDB.getConexao() == null || !SingletonDB.getConexao().getEstadoConexao()) {
            System.out.println("Conexão não está aberta no ComandaCadController.");
        }

        List<Garcon> garcons = new GarconDAL().get("");
        System.out.println("Garçons recuperados: " + garcons.size());
        for (Garcon gar : garcons) {
            System.out.println("Garçon: " + gar.getNome());
        }
        cbGarcom.getItems().addAll(garcons);
    }

}
