/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;

/**
 *
 * @author SpaceBR
 */
public class Resumo {
    GridPane grid;
    TableView table;
    TableColumn c1,c2;
    BarChart<String,Number> graficoBar;
    NumberAxis yAxis,xEixo,yEixo;
    CategoryAxis xAxis;
    LineChart graficoLinha;
    
    public Node exibir(){
        xAxis = new CategoryAxis();
        yAxis = new NumberAxis();
        xEixo = new NumberAxis();
        yEixo = new NumberAxis();
        xAxis.setLabel("TESTE");
        yAxis.setLabel("VALOR");
        grid = new GridPane();
        table = new TableView();
        
        graficoBar = new BarChart<>(xAxis,yAxis);
        graficoLinha = new LineChart<>(xEixo,yEixo);
        
        grid.add(table,0,1);
        GridPane.setColumnSpan(table, 2);
        grid.add(graficoBar,0,0);
        grid.add(graficoLinha,1,0);
        return grid;
    }
}
