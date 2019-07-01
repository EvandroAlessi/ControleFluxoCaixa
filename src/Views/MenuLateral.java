/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import CrossCutting.Enums.Tela;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;

/**
 *
 * @author SpaceBR
 */
public class MenuLateral extends GridPane {
    GridPane menu;
    VBox vbTop;
    VBox vbBottom;
    VBox right;
    Button resumo, relatorio, despesa, receita, categoria, pagamento, sair, logout;
    Image grafico;
    ImageView imageView;
    
    public MenuLateral(FXMain main) {
        vbTop = new VBox();
        vbBottom = new VBox();
        resumo = new Button("Resumos");
        relatorio = new Button("Relatórios");
        despesa = new Button("Despesas");
        receita = new Button("Receitas");
        categoria = new Button("Categorias");
        pagamento = new Button("Formas de Pagamentos");
        sair = new Button("Sair");
        logout = new Button("Logout");
        right = new VBox();
        
        
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
            vbTop.getChildren().add(img);
            alert.showAndWait();
        }
        imageView = new ImageView(grafico);
        
        resumo.setOnAction((event) -> {
            main.switchCenter(Tela.RESUMO);
        });
        
        relatorio.setOnAction((event) -> {
            main.switchCenter(Tela.RELATORIO);
        });
        
        despesa.setOnAction((event) -> {
            main.switchCenter(Tela.DESPESA);
        });
        
        receita.setOnAction((event) -> {
            main.switchCenter(Tela.RECEITA);
        });
        
        categoria.setOnAction((event) -> {
            main.switchCenter(Tela.CATEGORIA);
        });
        
        pagamento.setOnAction((event) -> {
            main.switchCenter(Tela.PAGAMENTO);
        });
        
        logout.setOnAction((event) -> {
            
        });
        
        sair.setOnAction((event) -> {
            Platform.exit();
            System.exit(0);
        });
        
        imageView.setFitHeight(100);
        imageView.setFitWidth(100);
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);
        
        vbTop.getChildren().addAll(imageView,resumo,relatorio,despesa, receita, categoria,pagamento);
        vbBottom.getChildren().addAll(logout, sair);
        resumo.setMinWidth(150);
        resumo.setMinHeight(60);
        receita.setMinWidth(150);
        receita.setMinHeight(60);
        categoria.setMinWidth(150);
        categoria.setMinHeight(60);
        relatorio.setMinWidth(150);
        relatorio.setMinHeight(60);
        despesa.setMinWidth(150);
        despesa.setMinHeight(60);
        pagamento.setMinWidth(150);
        pagamento.setMinHeight(60);
        sair.setMinWidth(150);
        sair.setMinHeight(60);
        logout.setMinWidth(150);
        logout.setMinHeight(60);
        
        resumo.setStyle("-fx-background-insets: 0,0; -fx-padding: 1; -fx-border: 0;");
        receita.setStyle("-fx-background-insets: 0,0; -fx-padding: 1; -fx-border: 0;");
        categoria.setStyle("-fx-background-insets: 0,0; -fx-padding: 1; -fx-border: 0;");
        relatorio.setStyle("-fx-background-insets: 0,0; -fx-padding: 1; -fx-border: 0;");
        despesa.setStyle("-fx-background-insets: 0,0; -fx-padding: 1; -fx-border: 0;");
        pagamento.setStyle("-fx-background-insets: 0,0; -fx-padding: 1; -fx-border: 0;");
        sair.setStyle("-fx-background-insets: 0,0; -fx-padding: 1; -fx-border: 0;");
        logout.setStyle("-fx-background-insets: 0,0; -fx-padding: 1; -fx-border: 0;");
        
        
        add(vbTop, 0, 0);
        add(vbBottom, 0, 1);
        add(right, 1, 0);
        
        setRowSpan(right, 2);
        right.setStyle("-fx-background-color: #ddd");
        
        ColumnConstraints c1 =  new ColumnConstraints();
        c1.setHgrow(Priority.ALWAYS);
        c1.setPrefWidth(150);
        c1.setHalignment(HPos.CENTER);
        
        ColumnConstraints c2 =  new ColumnConstraints();
        c2.setHgrow(Priority.NEVER);
        c2.setPrefWidth(1);
        c2.setHalignment(HPos.CENTER);
        vbTop.setAlignment(Pos.TOP_CENTER);
        vbBottom.setAlignment(Pos.BOTTOM_CENTER);
        
        RowConstraints r1 = new RowConstraints();
        r1.setVgrow(Priority.ALWAYS);
        r1.setValignment(VPos.CENTER);
        RowConstraints r2 = new RowConstraints();
        r2.setVgrow(Priority.ALWAYS);
        r2.setPrefHeight(80);
        r2.setMaxHeight(80);
        r2.setValignment(VPos.CENTER);
        
        this.getColumnConstraints().add(c1);
        this.getRowConstraints().add(r1);
        this.getRowConstraints().add(r2);
        this.getColumnConstraints().add(c2);
        
        
        
        
    }
    
    
//    public Node addMenu(){
//        menu = new GridPane();
//        vbTop = new VBox();
//        vbMidd = new VBox();
//        vbBottom = new VBox();
//        resumo = new Button("Resumos");
//        relatorio = new Button("Relatórios");
//        despesa = new Button("Despesas");
//        pagamento = new Button("Formas de Pagamentos");
//        sair = new Button("Sair");
//        try {
//            grafico = new Image(new FileInputStream("src\\Views\\Imagens\\grafico.png"));
//        } catch (FileNotFoundException ex) {
//            Label img = new Label("Imagem não encontrada");
//            img.setMinWidth(150);
//            img.setMinHeight(150);
//            Alert alert = new Alert(AlertType.WARNING);
//            alert.setTitle("Aviso");
//            alert.setHeaderText("Imagem não encontrada");
//            alert.setContentText("Não foi possível encontrar uma imagem");
//            vbTop.getChildren().add(img);
//            alert.showAndWait();
//        }
//        vbTop.getChildren().addAll(imageView,resumo,relatorio,despesa,pagamento);
//        
//        imageView = new ImageView(grafico);
//        resumo.setMinWidth(150);
//        resumo.setMinHeight(60);
//        relatorio.setMinWidth(150);
//        relatorio.setMinHeight(60);
//        despesa.setMinWidth(150);
//        despesa.setMinHeight(60);
//        pagamento.setMinWidth(150);
//        pagamento.setMinHeight(60);
//        sair.setMinWidth(150);
//        sair.setMinHeight(60);
//        
//        menu.add(vbTop, 0, 0);
//        menu.add(vbMidd, 0, 1);
//        menu.add(vbBottom, 0, 2);
//        
//        menu.setTop.add(c1);
//        menu.setBottom(sair);
//        return menu;
//    }
}
