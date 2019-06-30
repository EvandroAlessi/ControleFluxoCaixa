/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import javafx.scene.Node;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

/**
 *
 * @author SpaceBR
 */
public class MenuSuperior extends MenuBar{
        MenuBar menuSuperior; 
        Menu arquivo, resumo, relatorio, sobre;
        MenuItem sair;
        
        public MenuSuperior(){
            arquivo = new Menu("Arquivo");
            resumo = new Menu("Resumo");
            relatorio = new Menu("Relatório");
            sobre = new Menu("Sobre");
            sair = new MenuItem("Sair");
            arquivo.getItems().add(sair);
            this.getMenus().addAll(arquivo,resumo,relatorio,sobre);
        }
        
        public Node addMenu(){
            menuSuperior = new MenuBar();
            arquivo = new Menu("Arquivo");
            resumo = new Menu("Resumo");
            relatorio = new Menu("Relatório");
            sobre = new Menu("Sobre");
            sair = new MenuItem("Sair");
            arquivo.getItems().add(sair);
            menuSuperior.getMenus().addAll(arquivo,resumo,relatorio,sobre);
            return menuSuperior;
        }     
}
