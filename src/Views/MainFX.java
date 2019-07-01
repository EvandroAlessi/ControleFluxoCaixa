/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import CrossCutting.Enums.Tela;
import DAO.Contexto;
import DAO.CategoriaContaDAO;
import DAO.DespesaDAO;
import DAO.MovimentacaoDAO;
import DAO.SubCategoriaDAO;
import Models.Movimentacao;
import com.sun.media.jfxmedia.logging.Logger;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 *
 * @author evand
 */
public class MainFX extends Application {
    private static BorderPane root;
    private MenuSuperiorFX menuSuperior;
    private MenuLateralFX menuLateral;
    private ResumoFX resumo;
    
    @Override
    public void init(){
        root = new BorderPane();
        menuSuperior = new MenuSuperiorFX(this);
        menuLateral = new MenuLateralFX(this);
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
            
//            BufferedWriter output = new BufferedWriter(new FileWriter("the-file-name.txt", true));
//            output.append("\nThe third line");
//            
//            output.close();
            DespesaDAO sub = new DespesaDAO();
        sub.getAll();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
            Logger.logMsg(Logger.ERROR, "Failed to load cinematic context");
        }
        
    }
     
    public void switchCenter(Tela tela) {
        switch(tela){
            case RESUMO:
                root.setCenter(new ResumoFX());
                break;
            case RELATORIO:
                //root.setCenter(new Resumo());
                break;
            case RECEITA:
                root.setCenter(new ReceitaFX());
                break;
            case DESPESA:
                root.setCenter(new DespesaFX());
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
