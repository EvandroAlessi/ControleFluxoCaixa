/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author SpaceBR
 */
public class MenuLateral{
    BorderPane menu;
    VBox c1;
    Button resumo, relatorio, despesa, pagamento, sair;
    Image grafico;
    ImageView imageView;
    
    public Node addMenu(){
        menu = new BorderPane();
        c1 = new VBox();
        resumo = new Button("Resumos");
        relatorio = new Button("Relatórios");
        despesa = new Button("Despesas");
        pagamento = new Button("Formas de Pagamentos");
        sair = new Button("Sair");
        try {
            grafico = new Image(new FileInputStream("src\\Views\\Imagens\\grafico.png"));
        } catch (FileNotFoundException ex) {
            Label img = new Label("Imagem não encontrada");
            img.setMinWidth(150);
            img.setMinHeight(150);
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Aviso");
            alert.setHeaderText("Imagem não encontrada");
            alert.setContentText("Não foi possível encontrar uma imagem");
            c1.getChildren().add(img);
            alert.showAndWait();
        }
        
        imageView = new ImageView(grafico);
        resumo.setMinWidth(150);
        resumo.setMinHeight(60);
        relatorio.setMinWidth(150);
        relatorio.setMinHeight(60);
        despesa.setMinWidth(150);
        despesa.setMinHeight(60);
        pagamento.setMinWidth(150);
        pagamento.setMinHeight(60);
        sair.setMinWidth(150);
        sair.setMinHeight(60);
        c1.getChildren().addAll(imageView,resumo,relatorio,despesa,pagamento);
        menu.setTop(c1);
        menu.setBottom(sair);
        return menu;
    }
}
