/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import javafx.application.Application;
import javafx.scene.Scene;
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
        MenuSuperior menuSuperior = new MenuSuperior();
        MenuLateral menuLateral = new MenuLateral();
        Resumo resumo = new Resumo();
        root.setCenter(resumo);
        root.setLeft(menuLateral);
        root.setTop(menuSuperior.addMenu());
        
        Scene scene = new Scene(root, 700, 700);

        primaryStage.setTitle("Controle Fluxo de Caixa");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
     
    private void alternarTela(){
        
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
