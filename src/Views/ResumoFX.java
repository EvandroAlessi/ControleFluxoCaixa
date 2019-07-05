/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import Controllers.DespesaController;
import Controllers.MovimentacaoController;
import DAO.DespesaDAO;
import DAO.ReceitaDAO;
import Models.Despesa;
import Models.Receita;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

/**
 *
 * @author SpaceBR
 */
public class ResumoFX extends GridPane { 
    private TableView table;
    private TableColumn c1;
    private TableColumn c2;
    private BarChart<String, Number> graficoBar;
    private NumberAxis yAxis;
    private NumberAxis xEixo;
    private NumberAxis yEixo;
    private CategoryAxis xAxis;
    private LineChart<String, Number> graficoLinha;
    
    public ResumoFX(Stage stage) {
        try {
            MovimentacaoController control = new MovimentacaoController();
            setxAxis(new CategoryAxis());
            setyAxis(new NumberAxis());
            setxEixo(new NumberAxis());
            setyEixo(new NumberAxis());
            getxAxis().setLabel("TESTE");
            getyAxis().setLabel("VALOR");
            setTable(new TableView());

            this.graficoLinha = new LineChart<>(new CategoryAxis(), new NumberAxis());
            XYChart.Series dataSeries1 = new XYChart.Series();
            dataSeries1.setName("Receitas");

            List<Receita> receitas = new ReceitaDAO().getAll();

            HashMap<String, Double> dados = new HashMap();

            receitas.forEach((receita) -> {
                if(!dados.containsKey((receita.getDataOcorrencia().getMonth() + 1) + "/" + (receita.getDataOcorrencia().getYear() + 1900))){
                    dados.put((receita.getDataOcorrencia().getMonth() + 1) + "/" + (receita.getDataOcorrencia().getYear() + 1900), receita.getValor());
                }else{
                    dados.put((receita.getDataOcorrencia().getMonth() + 1) + "/" + (receita.getDataOcorrencia().getYear() + 1900), receita.getValor() + dados.get((receita.getDataOcorrencia().getMonth() + 1) + "/" + (receita.getDataOcorrencia().getYear() + 1900)));
                }
            });
            
            DespesaController dao = new DespesaController();
            dao.create(new Despesa(new Date(1997, 10, 05), "asdasss",120 , 1));

            for(String chave: dados.keySet()){
                dataSeries1.getData().add(new XYChart.Data(chave, dados.get(chave)));
            }

            XYChart.Series dataSeries2 = new XYChart.Series();
            dataSeries2.setName("Despesas");

            List<Despesa> despesas = new DespesaDAO().getAll();

            HashMap<String, Double> dados2 = new HashMap();

            for(Despesa despesa: despesas){
                System.out.println((despesa.getDataOcorrencia().getMonth() + 1) + "/" + (despesa.getDataOcorrencia().getYear() + 1900));
                if(!dados2.containsKey((despesa.getDataOcorrencia().getMonth() + 1) + "/" + (despesa.getDataOcorrencia().getYear() + 1900))){
                    dados2.put((despesa.getDataOcorrencia().getMonth() + 1) + "/" + (despesa.getDataOcorrencia().getYear() + 1900), despesa.getValor());
                }else{
                    dados2.put((despesa.getDataOcorrencia().getMonth() + 1) + "/" + (despesa.getDataOcorrencia().getYear() + 1900), despesa.getValor() + dados2.get((despesa.getDataOcorrencia().getMonth() + 1) + "/" + (despesa.getDataOcorrencia().getYear() + 1900)));
                }
            }

            for(String chave:  dados2.keySet() ){
                
                dataSeries2.getData().add(new XYChart.Data(chave, dados2.get(chave)));
            }

           this.graficoLinha.setAxisSortingPolicy(LineChart.SortingPolicy.X_AXIS);
            
            this.graficoLinha.getData().add(dataSeries1);
            this.graficoLinha.getData().add(dataSeries2);


            setGraficoBar(new BarChart<>(getxAxis(), getyAxis()));

            add(getTable(),0,1);
            setColumnSpan(getTable(), 2);
            add(getGraficoBar(),0,0);
            add(this.graficoLinha,1,0);

            ColumnConstraints c1 =  new ColumnConstraints();
            c1.setHgrow(Priority.ALWAYS);

            RowConstraints r1 = new RowConstraints();
            r1.setVgrow(Priority.ALWAYS);

            getColumnConstraints().add(c1);
            getRowConstraints().add(r1);
        }catch(ClassNotFoundException | SQLException e){
            //
        }
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
