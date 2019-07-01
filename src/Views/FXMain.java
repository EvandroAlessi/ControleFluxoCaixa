/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import CrossCutting.Enums.Tela;
import DAO.Contexto;
import DAO.CategoriaContaDAO;
import DAO.MovimentacaoDAO;
import DAO.SubCategoriaDAO;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
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
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        root.setLeft(menuLateral);
        root.setTop(menuSuperior);
        
        switchCenter(Tela.DESPESA);
        primaryStage.setMaximized(true);
        Scene scene = new Scene(root, 700, 700);
        
        primaryStage.setTitle("Controle Fluxo de Caixa");
        primaryStage.setScene(scene);
        
        primaryStage.show();
        //Contexto contexto = new Contexto();
        //contexto.getConnection();
        //System.out.println("OK");
        try {
            SubCategoriaDAO sub = new SubCategoriaDAO();
        sub.getAll();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
        
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
                root.setCenter(new Receita());
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
