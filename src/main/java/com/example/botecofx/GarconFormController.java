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
import org.json.JSONObject;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
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

    File file=null;

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
        if(GarconConsultaController.garcon != null)
        {
            Garcon aux=GarconConsultaController.garcon;
            // preencher os dados do garçon
            tfNome.setText(aux.getNome());
            //colocar foto na interface
            BufferedImage bufferedImage;
            bufferedImage=SingletonDB.getConexao().carregarImagem("garcon","gar_foto","gar_id",4);
            imageView.setImage(SwingFXUtils.toFXImage(bufferedImage,null));
        }
    }

    public void setGarcon(Garcon garcon) {
        tfID.setText(String.valueOf(garcon.getId()));
        tfNome.setText(garcon.getNome());
        tfCPF.setText(garcon.getCpf());
        tfCEP.setText(garcon.getCep());
        tfFone.setText(garcon.getFone());
        tfNumero.setText(garcon.getNumero());
        tfCidade.setText(garcon.getCidade());
        tfEndereco.setText(garcon.getEndereco());
        tfUF.setText(garcon.getUf());
        BufferedImage bufferedImage = SingletonDB.getConexao().carregarImagem("garcon", "gar_foto", "gar_id", garcon.getId());
        if (bufferedImage != null) {
            imageView.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
        } else {
            // Use uma imagem padrão caso não haja foto do garçom
            imageView.setImage(new Image(getClass().getResourceAsStream("/com/example/botecofx/semFoto.png")));
        }
    }

    @FXML
    void onCancelar(ActionEvent event) {
        btCancelar.getScene().getWindow().hide();
    }

    @FXML
    void onConfimar(ActionEvent event) {
        try {
            if (tfNome.getText().isEmpty() || tfCPF.getText().isEmpty()) {
                throw new IllegalArgumentException("Nome e CPF são obrigatórios.");
            }

            // Criar o objeto Garcon com os dados do formulário
            Garcon garcon = new Garcon();
            garcon.setNome(tfNome.getText());
            garcon.setCpf(tfCPF.getText());
            garcon.setCep(tfCEP.getText());
            garcon.setEndereco(tfEndereco.getText());
            garcon.setNumero(tfNumero.getText());
            garcon.setCidade(tfCidade.getText());
            garcon.setUf(tfUF.getText());
            garcon.setFone(tfFone.getText());


            if (!tfID.getText().isEmpty())// é uma edição
            {
                garcon.setId(Integer.parseInt(tfID.getText()));
                if (!new GarconDAL().alterar(garcon))
                {
                    throw new RuntimeException("Erro ao atualizar o garçom.");
                }
            }
            else
            {
                if (!new GarconDAL().gravar(garcon,file)) // é uma inserção
                {
                    throw new RuntimeException("Erro ao gravar o novo garçom.");
                }
            }
            btConfirmar.getScene().getWindow().hide();

        } catch (IllegalArgumentException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Erro: " + e.getMessage());
            alert.showAndWait();
        }
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
        FileChooser fileChooser=new FileChooser();
        file=fileChooser.showOpenDialog(null);
        if (file!=null){
            Image image=new Image(file.toURI().toString());
            imageView.setImage(image);
        }
    }
}