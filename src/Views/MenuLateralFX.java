/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import Controllers.MovimentacaoController;
import CrossCutting.Enums.Tela;
import java.util.Optional;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Window;

/**
 * View responsável pelo menu lateral. É ligada ao Stage principal gerado em MainFX.
 * @author Evandro Alessi
 * @author Eric Ueta
 * @see MainFX
 */

public class MenuLateralFX extends GridPane {
    // Declaração de componentes
    GridPane menu;
    VBox vbTop;
    VBox vbBottom;
    VBox vbRight;
    Button btnResumo, btnDespesa, btnReceita, btnCategoria, btnRelatorio, btnFuturo, btnSair;
    Image grafico;
    ImageView imageView;
    public static Button btnSaldo;
    double saldo;
    
    /** @param main Recebe MainFX
    */
    public MenuLateralFX(MainFX main) {
        //Instanciação de componentes
        vbTop = new VBox();
        vbBottom = new VBox();
        btnResumo = new Button("Resumo");
        btnSaldo = new Button();
        btnDespesa = new Button("Despesas");
        btnReceita = new Button("Receitas");
        btnCategoria = new Button("Categorias");
        btnRelatorio = new Button("Últimos lançamentos");
        btnFuturo = new Button("Lançamentos Futuros");
        btnSair = new Button("Sair");
        vbRight = new VBox();
        saldo = new MovimentacaoController().getSaldo();
        
        grafico = new Image(MenuLateralFX.class.getResourceAsStream("/Resources/Icon-05.png"));
        imageView = new ImageView(grafico);

        btnResumo.setOnAction((event) -> {
            main.switchCenter(Tela.RESUMO);
        });
        
        btnRelatorio.setOnAction((event) -> {
            main.switchCenter(Tela.ULTIMOS);
        });
        
        btnFuturo.setOnAction((event) -> {
            main.switchCenter(Tela.FUTURO);
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
        
        // Evento para confirmação de saída
        btnSair.setOnAction((event) -> {
            ButtonType btnSim = new ButtonType("Sim", ButtonBar.ButtonData.OK_DONE);
            ButtonType btnNao = new ButtonType("Não", ButtonBar.ButtonData.CANCEL_CLOSE);
            Alert alert = new Alert(Alert.AlertType.WARNING, "", btnSim, btnNao);
            alert.setHeaderText("Deseja realmente sair?");
            alert.setContentText("Tem certeza?");
            Window window = alert.getDialogPane().getScene().getWindow();
            window.setOnCloseRequest(e -> alert.hide());
            Optional<ButtonType> result = alert.showAndWait();
            result.ifPresent(res->{
                if (res.equals(btnSim)) {
                    Platform.exit();
                    System.exit(0);
                } else if (res.equals(btnNao)) {
                    alert.close();
                }
            });
        });
        
        //Definindo tamanhos
        imageView.setFitHeight(120);
        imageView.setFitWidth(120);
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);
        btnResumo.setPadding(new Insets(25));
        vbTop.getChildren().addAll(imageView, btnSaldo, btnResumo, btnDespesa, btnReceita, btnCategoria, btnRelatorio, btnFuturo);
        vbBottom.getChildren().addAll(btnSair);
        btnResumo.setMinWidth(150);
        btnResumo.setMinHeight(60);
        btnSaldo.setMinWidth(150);
        btnSaldo.setMinHeight(60);
        btnReceita.setMinWidth(150);
        btnReceita.setMinHeight(60);
        btnDespesa.setMinWidth(150);
        btnDespesa.setMinHeight(60);
        btnCategoria.setMinWidth(150);
        btnCategoria.setMinHeight(60);
        btnRelatorio.setMinWidth(150);
        btnRelatorio.setMinHeight(60);
        btnFuturo.setMinWidth(150);
        btnFuturo.setMinHeight(60);
        btnSair.setMinWidth(150);
        btnSair.setMinHeight(60);
        
        if (saldo >= 0) { // Saldo positivo/Neutro
            btnSaldo.setStyle("-fx-background-insets: 0,0; "
                + "-fx-padding: 1; "
                + "-fx-border: 0;"
                + "-fx-font-weight: bold;"
                + "-fx-background-color: green;"
                + "-fx-color: white;"
                + "-fx-alignment: center;" 
                + "-fx-font-size: 17;");
        }
        else{ // Saldo negativo
            btnSaldo.setStyle("-fx-background-insets: 0,0; "
                + "-fx-padding: 1; "
                + "-fx-border: 0;"
                + "-fx-font-weight: bold;"
                + "-fx-background-color: red;"
                + "-fx-color: white;"
                + "-fx-alignment: center;" 
                + "-fx-font-size: 17;");
        }
        
        btnResumo.setStyle("-fx-background-insets: 0,0; "
                + "-fx-padding: 1; "
                + "-fx-border: 0;"
                + "-fx-font-weight: bold;");
        
        btnRelatorio.setStyle("-fx-background-insets: 0,0; "
                + "-fx-padding: 1; "
                + "-fx-border: 0;"
                + "-fx-font-weight: bold");
        
        btnFuturo.setStyle("-fx-background-insets: 0,0; "
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
        
        btnSair.setStyle("-fx-background-insets: 0,0; "
                + "-fx-padding: 1; "
                + "-fx-border: 0;"
                + "-fx-font-weight: bold;"
                + "-fx-background-color: #d64646;"
                + "-fx-color: white;");
        
        
        btnSaldo.setOnAction(new EventHandler<ActionEvent>() { // Atualizar exibição do saldo
            @Override
            public void handle(ActionEvent actionEvent) {
                saldo = new MovimentacaoController().getSaldo();
                btnSaldo.setText("Saldo Atual\nR$ " + saldo);
                btnSaldo.setTextAlignment(TextAlignment.CENTER);
                if (saldo >= 0) {
                    btnSaldo.setStyle("-fx-background-insets: 0,0; "
                            + "-fx-padding: 1; "
                            + "-fx-border: 0;"
                            + "-fx-font-weight: bold;"
                            + "-fx-background-color: green;"
                            + "-fx-color: white;"
                            + "-fx-alignment: center;"
                            + "-fx-font-size: 17;");
                } else {
                    btnSaldo.setStyle("-fx-background-insets: 0,0; "
                            + "-fx-padding: 1; "
                            + "-fx-border: 0;"
                            + "-fx-font-weight: bold;"
                            + "-fx-background-color: red;"
                            + "-fx-color: white;"
                            + "-fx-alignment: center;"
                            + "-fx-font-size: 17;");
                }
            }
        });
        
        btnSair.setTextFill(Color.WHITE);
        btnSaldo.setTextFill(Color.WHITE);
        btnSaldo.setTextAlignment(TextAlignment.CENTER);
        btnSaldo.setText("Saldo Atual\nR$ " + saldo);
        
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
