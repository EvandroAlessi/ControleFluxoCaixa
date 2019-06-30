/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 *
 * @author evand
 */
public class FXMain extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        HBox centro = new HBox();
        MenuSuperior menuSuperior = new MenuSuperior();
        MenuLateral menuLateral = new MenuLateral();
        Resumo resumo = new Resumo();
        centro.getChildren().addAll(menuLateral.addMenu(),resumo.exibir());
        root.setTop(menuSuperior.addMenu());
        root.setCenter(centro);
        
        Scene scene = new Scene(root, 700, 700);

        primaryStage.setTitle("Controle Fluxo de Caixa");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
