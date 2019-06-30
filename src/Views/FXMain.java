/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import CrossCutting.Enums.Tela;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 *
 * @author evand
 */
public class FXMain extends Application {
    private static BorderPane root;
    private MenuSuperior menuSuperior;
    private MenuLateral menuLateral;
    private Resumo resumo;
    
    @Override
    public void init(){
        root = new BorderPane();
        menuSuperior = new MenuSuperior(this);
        menuLateral = new MenuLateral(this);
        //resumo = new Resumo();
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        root.setLeft(menuLateral);
        root.setTop(menuSuperior);
        
        //switchCenter(Tela.RECEITA);
        primaryStage.setMaximized(true);
        Scene scene = new Scene(root, 700, 700);
        
        
       
        
        primaryStage.setTitle("Controle Fluxo de Caixa");
        primaryStage.setScene(scene);
        
        
        primaryStage.show();
    }
     
    public void switchCenter(Tela tela){
        switch(tela){
            case RESUMO:
                root.setCenter(new Resumo());
                break;
            case RELATORIO:
                //root.setCenter(new Resumo());
                break;
            case RECEITA:
                //root.setCenter(new Resumo());
                break;
            case DESPESA:
                root.setCenter(new Despesa());
                break;
            case CATEGORIA:
                //root.setCenter(new Resumo());
                break;
            case PAGAMENTO:
                //root.setCenter(new Resumo());
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
