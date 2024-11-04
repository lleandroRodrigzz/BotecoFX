package com.example.botecofx;
import com.example.botecofx.db.dals.GarconDAL;
import com.example.botecofx.db.entidades.Garcon;
import com.example.botecofx.db.util.SingletonDB;
import com.example.botecofx.util.ApisService;
import com.example.botecofx.util.MaskFieldUtil;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.json.JSONObject;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class GarconFormController implements Initializable {
    public ImageView imageView;
    @FXML
    private Button btCancelar;

    @FXML
    private Button btConfirmar;

    @FXML
    private TextField tfCEP;

    @FXML
    private TextField tfCPF;

    @FXML
    private TextField tfCidade;

    @FXML
    private TextField tfEndereco;

    @FXML
    private TextField tfFone;

    @FXML
    private TextField tfID;

    @FXML
    private TextField tfNome;

    @FXML
    private TextField tfNumero;

    @FXML
    private TextField tfUF;

    File file = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                tfNome.requestFocus();
            }
        });
        MaskFieldUtil.cpfField(tfCPF);
        MaskFieldUtil.cepField(tfCEP);
        MaskFieldUtil.foneField(tfFone);
        // se for uma alteração
        if(GarconConsultaController.garcon!=null) {
            Garcon aux=GarconConsultaController.garcon;
            // preencher os dados do garçon
            tfNome.setText(aux.getNome());
            //colocar foto na interface
            BufferedImage bufferedImage;
            bufferedImage = SingletonDB.getConexao().carregarImagem("garcon","gar_foto","gar_id",4); //quatro é provisório
            imageView.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
        }
    }

    @FXML
    void onCancelar(ActionEvent event) {
        btCancelar.getScene().getWindow().hide();
    }

    @FXML
    void onConfimar(ActionEvent event) {
        Garcon garcon=new Garcon(tfNome.getText(),tfCPF.getText(),
                tfCEP.getText(),tfEndereco.getText(),
                tfNumero.getText(),tfCidade.getText(),
                tfUF.getText(),tfFone.getText());
        if(!new GarconDAL().gravar(garcon,file))
        {
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Erro ao gravar o garçon: "+ SingletonDB.getConexao().getMensagemErro());
            alert.showAndWait();
        }
        btConfirmar.getScene().getWindow().hide();
    }


    public void onBuscarCep(KeyEvent keyEvent) {
        if(tfCEP.getText().length()==9){
            String dados=ApisService.consultaCep(tfCEP.getText(),"json");
            JSONObject json = new JSONObject(dados);
            tfCidade.setText(json.getString("localidade"));
            tfEndereco.setText(json.getString("logradouro"));
            tfUF.setText(json.getString("uf"));
        }
    }

    public void onBuscarImagem(MouseEvent mouseEvent) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);
        if(file != null){
            Image image = new Image(file.toURI().toString());
            imageView.setImage(image);
        }
    }
}
