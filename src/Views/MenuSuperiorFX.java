/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import CrossCutting.Enums.Tela;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

/**
 *
 * @author SpaceBR
 */
public class MenuSuperiorFX extends MenuBar {

    MenuBar menuSuperior;
    Menu arquivo, cadastro, relatorios;
    MenuItem sair, resumo, despesa, receita, ultimos, futuros, categoria;

    public MenuSuperiorFX(MainFX main) {
        arquivo = new Menu("Arquivo");
        cadastro = new Menu("Geral");
        relatorios = new Menu("Relatórios");
        resumo = new MenuItem("Resumo");
        despesa = new MenuItem("Despesas");
        receita = new MenuItem("Receitas");
        categoria = new MenuItem("Categorias");
        ultimos = new MenuItem("Ultimos lançamentos");
        futuros = new MenuItem("Lançamentos Futuros");
        sair = new MenuItem("Sair");
        arquivo.getItems().add(sair);
        
        cadastro.getItems().addAll(resumo, despesa, receita, categoria);
        relatorios.getItems().addAll(ultimos, futuros);
        
        getMenus().addAll(arquivo, cadastro, relatorios);

        resumo.setOnAction((event) -> {
            main.switchCenter(Tela.RESUMO);
        });

        despesa.setOnAction((event) -> {
            main.switchCenter(Tela.DESPESA);
        });

        receita.setOnAction((event) -> {
            main.switchCenter(Tela.RECEITA);
        });

        ultimos.setOnAction((event) -> {
            main.switchCenter(Tela.ULTIMOS);
        });

        futuros.setOnAction((event) -> {
            main.switchCenter(Tela.FUTURO);
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
