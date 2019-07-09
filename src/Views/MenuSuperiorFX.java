/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import CrossCutting.Enums.Tela;
import Models.Despesa;
import java.util.Optional;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Window;

/**
 *
 * @author SpaceBR
 */
public class MenuSuperiorFX extends MenuBar {

    MenuBar menuSuperior;
    Menu arquivo, geral, relatorios;
    MenuItem sair, resumo, despesa, receita, ultimos, futuros, categoria;

    public MenuSuperiorFX(MainFX main) {
        arquivo = new Menu("Arquivo");
        geral = new Menu("Geral");
        relatorios = new Menu("Relatórios");
        resumo = new MenuItem("Resumo");
        despesa = new MenuItem("Despesas");
        receita = new MenuItem("Receitas");
        categoria = new MenuItem("Categorias");
        ultimos = new MenuItem("Últimos lançamentos");
        futuros = new MenuItem("Lançamentos Futuros");
        sair = new MenuItem("Sair      ");
        arquivo.getItems().add(sair);
        
        geral.getItems().addAll(resumo, despesa, receita, categoria);
        relatorios.getItems().addAll(ultimos, futuros);
        
        getMenus().addAll(arquivo, geral, relatorios);

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
           ButtonType btnSim = new ButtonType("Sim");
            ButtonType btnNao = new ButtonType("Não");
            Alert alert = new Alert(Alert.AlertType.WARNING, "", btnSim, btnNao);
            alert.setHeaderText("Deseja realmente sair?");
            alert.setContentText("Tem certeza?");
            Window window = alert.getDialogPane().getScene().getWindow();
            window.setOnCloseRequest(e -> alert.hide());
            Optional<ButtonType> result = alert.showAndWait();
            result.ifPresent(res->{
                if (res.equals(btnSim)) {
                    Platform.exit();
                    System.exit(0);
                } else if (res.equals(btnNao)) {
                    alert.close();
                }
            });
        });
    }
}
