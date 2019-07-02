/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import CrossCutting.Enums.Tela;
import DAO.CategoriaContaDAO;
import Models.CategoriaConta;
import com.sun.media.jfxmedia.logging.Logger;
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
    // Declaração de containers e componentes
    private static BorderPane root;
    private MenuSuperiorFX menuSuperior;
    private MenuLateralFX menuLateral;
    private ResumoFX resumo;
    
    @Override
    public void init(){
        // Instanciação da tela inicial e menus
        root = new BorderPane();
        menuSuperior = new MenuSuperiorFX(this);
        menuLateral = new MenuLateralFX(this);
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        root.setLeft(menuLateral);
        root.setTop(menuSuperior);
        
        switchCenter(Tela.DESPESA);
        primaryStage.setMaximized(true); // Tela cheia
        Scene scene = new Scene(root, 700, 700);
        
        primaryStage.setTitle("Controle Fluxo de Caixa");
        primaryStage.setScene(scene);
        
        primaryStage.show();
       
        // teste
        try {
            CategoriaContaDAO sub = new CategoriaContaDAO();
                sub.create(new CategoriaConta("descricao43", false));
        } catch (SQLException e) {
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
                root.setCenter(new RelatorioFX());
                break;
            case RECEITA:
                root.setCenter(new ReceitaFX());
                break;
            case DESPESA:
                root.setCenter(new DespesaFX());
                break;
            case CATEGORIA:
                root.setCenter(new CategoriaFX());
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
