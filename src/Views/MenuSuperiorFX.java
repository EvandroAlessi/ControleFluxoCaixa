/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import CrossCutting.Enums.Tela;
import javafx.application.Platform;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

/**
 *
 * @author SpaceBR
 */
public class MenuSuperiorFX extends MenuBar{
        MenuBar menuSuperior; 
        Menu arquivo, cadastro;
        MenuItem sair, resumo, despesa, receita, categoria;
        
        public MenuSuperiorFX(MainFX main){
            arquivo = new Menu("Arquivo");
            cadastro = new Menu("Geral");
            resumo = new MenuItem("Resumo");
            despesa = new MenuItem("Despesas");
            receita = new MenuItem("Receitas");
            categoria = new MenuItem("Categorias");
            sair = new MenuItem("Sair");
            arquivo.getItems().add(sair);
            cadastro.getItems().addAll(resumo, despesa, receita, categoria);
            
            getMenus().addAll(arquivo, cadastro);
            
            resumo.setOnAction((event) -> {
                main.switchCenter(Tela.RESUMO);
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

            sair.setOnAction((event) -> {
                Platform.exit();
                System.exit(0);
            });
        }    
}
