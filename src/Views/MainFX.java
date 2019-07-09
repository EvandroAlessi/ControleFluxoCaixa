/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import CrossCutting.Enums.Tela;
import java.util.Optional;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

/**
 *
 * @author evand
 */
public class MainFX extends Application {

    // Declaração de containers e componentes
    private static BorderPane root;
    private MenuSuperiorFX menuSuperior;
    private MenuLateralFX menuLateral;
    //private ResumoFX resumo;
    private Stage stage;

    @Override
    public void init() {
        // Instanciação da tela inicial e menus
        root = new BorderPane();
        menuSuperior = new MenuSuperiorFX(this);
        menuLateral = new MenuLateralFX(this);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        root.setLeft(menuLateral);
        root.setTop(menuSuperior);
        root.setStyle("-fx-focus-color: transparent;"
                + "-fx-faint-focus-color: #d2d4d6;");
        switchCenter(Tela.RESUMO);
        primaryStage.setMaximized(true); // Tela cheia
        Scene scene = new Scene(root, 700, 700);
        stage.setMinHeight(600);
        stage.setMinWidth(800);
        primaryStage.setTitle("Controle de Fluxo de Caixa");
        primaryStage.setScene(scene);
        
        stage.addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, e-> {
            e.consume();
            ButtonType btnSim = new ButtonType("Sim");
            ButtonType btnNao = new ButtonType("Não");
            Alert alert = new Alert(Alert.AlertType.WARNING, "", btnSim, btnNao);
            alert.setHeaderText("Deseja realmente sair?");
            alert.setContentText("Tem certeza?");
            Window window = alert.getDialogPane().getScene().getWindow();
            window.setOnCloseRequest(ev -> alert.hide());
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
        
        primaryStage.show();
    }

    /**
     * Alternar entre as telas exibidas
     * @param tela
     */
    public void switchCenter(Tela tela) {
        switch (tela) {
            case RESUMO:
                root.setCenter(new ResumoFX(this.stage));
                break;
            case ULTIMOS:
                root.setCenter(new RelatorioFX(this.stage, true, "Últimos lançamentos"));
                break;
            case FUTURO:
                root.setCenter(new RelatorioFX(this.stage, false, "Lançamentos Futuros"));
                break;
            case RECEITA:
                root.setCenter(new ReceitaFX(this.stage));
                break;
            case DESPESA:
                root.setCenter(new DespesaFX(this.stage));
                break;
            case CATEGORIA:
                root.setCenter(new CategoriaSubFX(this.stage));
                break;
            case SUBCATEGORIA:
                //root.setCenter(new SubCategoriaFX(this.stage));
                break;
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
