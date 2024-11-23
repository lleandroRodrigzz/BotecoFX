package com.example.botecofx;

import com.example.botecofx.db.util.SingletonDB;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

import javax.swing.*;
import java.io.File;
import java.net.URL;
import java.sql.ResultSet;
import java.util.Map;
import java.util.ResourceBundle;

public class AdmController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void onCadGarcon(ActionEvent actionEvent) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(BotecoFX.class.getResource("garcon-consulta-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage=new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.showAndWait();
    }

    public void onCadCategoria(ActionEvent actionEvent) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(BotecoFX.class.getResource("categoria-consulta-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage=new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.showAndWait();
    }

    public void onCadUnidade(ActionEvent actionEvent) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(BotecoFX.class.getResource("unidade-consulta-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage=new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.showAndWait();
    }

    public void onCadTpPagamento(ActionEvent actionEvent) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(BotecoFX.class.getResource("tppagamento-consulta-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage=new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.showAndWait();
    }

    public void onCadProduto(ActionEvent event) throws Exception{   //  NAO IMPLEMENTADO
        FXMLLoader fxmlLoader = new FXMLLoader(BotecoFX.class.getResource("produto-consulta-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage=new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.showAndWait();
    }

    public void onRelListaProdutos(ActionEvent event) {
        String sql = "SELECT * FROM produto, categoria WHERE produto.cat_id = categoria.cat_id ORDER BY prod_nome";
        gerarRelatorio(sql,"reports/rel_produtos1.jasper","Relação simples de produtos");
    }

    public void onRelListaPreco(ActionEvent event) {
        String sql = "SELECT * FROM produto, categoria WHERE produto.cat_id = categoria.cat_id ORDER BY cat_nome,prod_nome";
        gerarRelatorio(sql,"reports/lista_preco1.jasper","Lista de preço");
    }

    private void gerarRelatorio(String sql,String relat, String titulo)
    {
        try
        {
            //sql para obter os dados para o relatorio
            ResultSet rs = SingletonDB.getConexao().consultar(sql);
            //implementação da interface JRDataSource para DataSource ResultSet
            JRResultSetDataSource jrRS = new JRResultSetDataSource(rs);
            //chamando o relatório
            String jasperPrint =
                    JasperFillManager.fillReportToFile(relat,null, jrRS);
            JasperViewer viewer = new JasperViewer(jasperPrint, false, false);
            viewer.setExtendedState(JasperViewer.MAXIMIZED_BOTH);//maximizado
            viewer.setTitle(titulo);//titulo do relatório
            viewer.setVisible(true);
        }
        catch (JRException erro){
            erro.printStackTrace();
        }

    }

    public void onBackup(ActionEvent event) {
        try{
            SingletonDB.backup("botecodb123.backup","botecodb");
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null,"ERRO: " + e);
        }
    }

    public void onRestore(ActionEvent event) {
        try {
            SingletonDB.restaurar("botecobd123.backup", "botecodb");
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null,"Erro: "+e);
        }
    }

    public void onAjuda(ActionEvent event) {
        WebView webView = new WebView();
        webView.setPrefSize(1300,700);
        //webView.getEngine().load("http://unoeste.br");    //Na web
        File file = new File("help/ajuda.html");            //Local
        webView.getEngine().load(file.toURI().toString());
        Stage stage = new Stage();
        stage.setScene(new Scene(webView));
        stage.showAndWait();
    }

    public void onSobre(ActionEvent event) {
    }
}
