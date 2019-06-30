/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;

/**
 *
 * @author SpaceBR
 */
public class Resumo extends GridPane { 
    private TableView table;
    private TableColumn c1;
    private TableColumn c2;
    private BarChart<String, Number> graficoBar;
    private NumberAxis yAxis;
    private NumberAxis xEixo;
    private NumberAxis yEixo;
    private CategoryAxis xAxis;
    private LineChart graficoLinha;
    
    public Resumo() {
        setxAxis(new CategoryAxis());
        setyAxis(new NumberAxis());
        setxEixo(new NumberAxis());
        setyEixo(new NumberAxis());
        getxAxis().setLabel("TESTE");
        getyAxis().setLabel("VALOR");
        setTable(new TableView());
        
        setGraficoBar(new BarChart<>(getxAxis(), getyAxis()));
        setGraficoLinha(new LineChart<>(getxEixo(), getyEixo()));
        
        this.add(getTable(),0,1);
        this.setColumnSpan(getTable(), 2);
        this.add(getGraficoBar(),0,0);
        this.add(getGraficoLinha(),1,0);
        
        ColumnConstraints c1 =  new ColumnConstraints();
        c1.setHgrow(Priority.ALWAYS);
        
        RowConstraints r1 = new RowConstraints();
        r1.setVgrow(Priority.ALWAYS);
        
        this.getColumnConstraints().add(c1);
        this.getRowConstraints().add(r1);
        
    }
    
    public Node exibir(){
        return null;
    }


    /**
     * @return the table
     */
    public TableView getTable() {
        return table;
    }

    /**
     * @param table the table to set
     */
    public void setTable(TableView table) {
        this.table = table;
    }

    /**
     * @return the c1
     */
    public TableColumn getC1() {
        return c1;
    }

    /**
     * @param c1 the c1 to set
     */
    public void setC1(TableColumn c1) {
        this.c1 = c1;
    }

    /**
     * @return the c2
     */
    public TableColumn getC2() {
        return c2;
    }

    /**
     * @param c2 the c2 to set
     */
    public void setC2(TableColumn c2) {
        this.c2 = c2;
    }

    /**
     * @return the graficoBar
     */
    public BarChart<String, Number> getGraficoBar() {
        return graficoBar;
    }

    /**
     * @param graficoBar the graficoBar to set
     */
    public void setGraficoBar(BarChart<String, Number> graficoBar) {
        this.graficoBar = graficoBar;
    }

    /**
     * @return the yAxis
     */
    public NumberAxis getyAxis() {
        return yAxis;
    }

    /**
     * @param yAxis the yAxis to set
     */
    public void setyAxis(NumberAxis yAxis) {
        this.yAxis = yAxis;
    }

    /**
     * @return the xEixo
     */
    public NumberAxis getxEixo() {
        return xEixo;
    }

    /**
     * @param xEixo the xEixo to set
     */
    public void setxEixo(NumberAxis xEixo) {
        this.xEixo = xEixo;
    }

    /**
     * @return the yEixo
     */
    public NumberAxis getyEixo() {
        return yEixo;
    }

    /**
     * @param yEixo the yEixo to set
     */
    public void setyEixo(NumberAxis yEixo) {
        this.yEixo = yEixo;
    }

    /**
     * @return the xAxis
     */
    public CategoryAxis getxAxis() {
        return xAxis;
    }

    /**
     * @param xAxis the xAxis to set
     */
    public void setxAxis(CategoryAxis xAxis) {
        this.xAxis = xAxis;
    }

    /**
     * @return the graficoLinha
     */
    public LineChart getGraficoLinha() {
        return graficoLinha;
    }

    /**
     * @param graficoLinha the graficoLinha to set
     */
    public void setGraficoLinha(LineChart graficoLinha) {
        this.graficoLinha = graficoLinha;
    }
}
