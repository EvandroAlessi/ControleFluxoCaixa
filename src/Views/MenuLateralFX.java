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
public class MenuLateralFX extends GridPane {

    GridPane menu;
    VBox vbTop;
    VBox vbBottom;
    VBox vbRight;
    Button btnResumo, btnDespesa, btnReceita, btnCategoria, btnSubCategoria, btnRelatorio, btnSair;
    Image grafico;
    ImageView imageView;

    public MenuLateralFX(MainFX main) {
        vbTop = new VBox();
        vbBottom = new VBox();
        btnResumo = new Button("Resumo");
        btnDespesa = new Button("Despesas");
        btnReceita = new Button("Receitas");
        btnCategoria = new Button("Categorias");
        btnSubCategoria = new Button("SubCategorias");
        btnRelatorio = new Button("Relatórios");
        btnSair = new Button("Sair");
        vbRight = new VBox();

        try {
            grafico = new Image(new FileInputStream("src\\Resources\\grafico.png"));
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

        btnResumo.setOnAction((event) -> {
            main.switchCenter(Tela.RESUMO);
        });
        
        btnRelatorio.setOnAction((event) -> {
            main.switchCenter(Tela.RELATORIO);
        });

        btnDespesa.setOnAction((event) -> {
            main.switchCenter(Tela.DESPESA);
        });

        btnReceita.setOnAction((event) -> {
            main.switchCenter(Tela.RECEITA);
        });

        btnCategoria.setOnAction((event) -> {
            main.switchCenter(Tela.CATEGORIA);
        });
        
        btnSubCategoria.setOnAction((event) -> {
            main.switchCenter(Tela.SUBCATEGORIA);
        });
        
        btnSair.setOnAction((event) -> {
            Platform.exit();
            System.exit(0);
        });

        imageView.setFitHeight(100);
        imageView.setFitWidth(80);
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);

        vbTop.getChildren().addAll(imageView, btnResumo, btnRelatorio, btnDespesa, btnReceita, btnCategoria, btnSubCategoria);
        vbBottom.getChildren().addAll(btnSair);
        btnRelatorio.setMinWidth(150);
        btnRelatorio.setMinHeight(60);
        btnResumo.setMinWidth(150);
        btnResumo.setMinHeight(60);
        btnReceita.setMinWidth(150);
        btnReceita.setMinHeight(60);
        btnDespesa.setMinWidth(150);
        btnDespesa.setMinHeight(60);
        btnCategoria.setMinWidth(150);
        btnCategoria.setMinHeight(60);
        btnSubCategoria.setMinWidth(150);
        btnSubCategoria.setMinHeight(60);
        btnSair.setMinWidth(150);
        btnSair.setMinHeight(60);

        btnResumo.setStyle("-fx-background-insets: 0,0; "
                + "-fx-padding: 1; "
                + "-fx-border: 0;"
                + "-fx-font-weight: bold;");
        btnRelatorio.setStyle("-fx-background-insets: 0,0; "
                + "-fx-padding: 1; "
                + "-fx-border: 0;"
                + "-fx-font-weight: bold");
        btnReceita.setStyle("-fx-background-insets: 0,0; "
                + "-fx-padding: 1; "
                + "-fx-border: 0;"
                + "-fx-font-weight: bold");
        btnCategoria.setStyle("-fx-background-insets: 0,0; "
                + "-fx-padding: 1; "
                + "-fx-border: 0;"
                + "-fx-font-weight: bold");
        btnDespesa.setStyle("-fx-background-insets: 0,0; "
                + "-fx-padding: 1; "
                + "-fx-border: 0;"
                + "-fx-font-weight: bold");
        btnSubCategoria.setStyle("-fx-background-insets: 0,0; "
                + "-fx-padding: 1; "
                + "-fx-border: 0;"
                + "-fx-font-weight: bold");
        btnSair.setStyle("-fx-background-insets: 0,0; "
                + "-fx-padding: 1; "
                + "-fx-border: 0;"
                + "-fx-font-weight: bold");

        add(vbTop, 0, 0);
        add(vbBottom, 0, 1);
        add(vbRight, 1, 0);

        setRowSpan(vbRight, 2);
        vbRight.setStyle("-fx-background-color: #ddd");

        ColumnConstraints c1 = new ColumnConstraints();
        c1.setHgrow(Priority.ALWAYS);
        c1.setPrefWidth(150);
        c1.setHalignment(HPos.CENTER);

        ColumnConstraints c2 = new ColumnConstraints();
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
        r2.setPrefHeight(120);
        r2.setMaxHeight(120);
        r2.setMinHeight(120);
        r2.setValignment(VPos.CENTER);

        getColumnConstraints().add(c1);
        getRowConstraints().add(r1);
        getRowConstraints().add(r2);
        getColumnConstraints().add(c2);
    }
}
