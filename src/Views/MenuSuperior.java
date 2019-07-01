/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import CrossCutting.Enums.Tela;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

/**
 *
 * @author SpaceBR
 */
public class MenuSuperior extends MenuBar{
        MenuBar menuSuperior; 
        Menu arquivo, resumo, relatorio, sobre;
        MenuItem sair;
        
        public MenuSuperior(FXMain main){
            arquivo = new Menu("Arquivo");
            resumo = new Menu("Resumo");
            relatorio = new Menu("Relatório");
            sobre = new Menu("Sobre");
            sair = new MenuItem("Sair");
            arquivo.getItems().add(sair);
            
            getMenus().addAll(arquivo,resumo,relatorio,sobre);
            
            resumo.setOnAction((event) -> {
                main.switchCenter(Tela.RESUMO);
            });

            relatorio.setOnAction((event) -> {
                main.switchCenter(Tela.RELATORIO);
            });
        }    
}
