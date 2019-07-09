/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import Controllers.DespesaController;
import Controllers.MovimentacaoController;
import Controllers.ReceitaController;
import Models.Despesa;
import Models.Receita;
import java.util.HashMap;
import java.util.List;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * View responsável pela apresentação de um resumo geral das movimentações.
 * Apresentação com TableView e BarChart. Diretamente ligada ao Stage provido
 * pela MainFX. Utiliza o controlador de Movimentações.
 *
 * @author Evandro Alessi
 * @author Eric Ueta
 * @see Movimentacao
 * @see MovimentacaoController
 * @see MainFX
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
    private MovimentacaoController movimentacaoController;

    /**
     * @param stage Recebe o stage principal de mainFX
     */
    public ResumoFX(Stage stage) {
        movimentacaoController = new MovimentacaoController();
        setxAxis(new CategoryAxis());
        setyAxis(new NumberAxis());
        setxEixo(new NumberAxis());
        setyEixo(new NumberAxis());
        getxAxis().setLabel("TESTE");
        getyAxis().setLabel("VALOR");
        setTable(new TableView());

        BarChart<String, Number> bc = new BarChart<String, Number>(new CategoryAxis(), new NumberAxis());
        bc.setTitle("Receitas por Categoria");
        xAxis.setLabel("Categoria");
        yAxis.setLabel("Valor");

        List<Receita> receitas = new ReceitaController().getAll();

        HashMap<String, HashMap<String, Double>> dados = new HashMap();

        for (Receita receita : receitas) {
            if (!(dados.containsKey(receita.getSubCategoria().getCategoriaConta().getDescricao()))) {
                dados.put(receita.getSubCategoria().getCategoriaConta().getDescricao(), new HashMap());
                dados.get(receita.getSubCategoria().getCategoriaConta().getDescricao()).put(receita.getSubCategoria().getDescricao(), receita.getValor());
            } else {
                if (!dados.get(receita.getSubCategoria().getCategoriaConta().getDescricao()).containsKey(receita.getSubCategoria().getDescricao())) {
                    dados.get(receita.getSubCategoria().getCategoriaConta().getDescricao()).put(receita.getSubCategoria().getDescricao(), receita.getValor());
                } else {
                    dados.get(receita.getSubCategoria().getCategoriaConta().getDescricao()).put(receita.getSubCategoria().getDescricao(), dados.get(receita.getSubCategoria().getCategoriaConta().getDescricao()).get(receita.getSubCategoria().getDescricao()) + receita.getValor());
                }
            }
        }

        dados.keySet().forEach((chave) -> {

            XYChart.Series dataSeries = new XYChart.Series();
            dataSeries.setName(chave);

            dados.get(chave).keySet().forEach((chaveI) -> {
                dataSeries.getData().add(new XYChart.Data(chaveI, dados.get(chave).get(chaveI)));
            });

            bc.getData().add(dataSeries);
        });

        BarChart<String, Number> bc2 = new BarChart<String, Number>(new CategoryAxis(), new NumberAxis());
        bc2.setTitle("Despesas por Categoria");
        xAxis.setLabel("Categoria");
        yAxis.setLabel("Valor");

        List<Despesa> despesas = new DespesaController().getAll();

        HashMap<String, HashMap<String, Double>> dados2 = new HashMap();

        for (Despesa despesa : despesas) {
            if (!(dados2.containsKey(despesa.getSubCategoria().getCategoriaConta().getDescricao()))) {
                dados2.put(despesa.getSubCategoria().getCategoriaConta().getDescricao(), new HashMap());
                dados2.get(despesa.getSubCategoria().getCategoriaConta().getDescricao()).put(despesa.getSubCategoria().getDescricao(), despesa.getValor());
            } else {
                if (!dados2.get(despesa.getSubCategoria().getCategoriaConta().getDescricao()).containsKey(despesa.getSubCategoria().getDescricao())) {
                    dados2.get(despesa.getSubCategoria().getCategoriaConta().getDescricao()).put(despesa.getSubCategoria().getDescricao(), despesa.getValor());
                } else {
                    dados2.get(despesa.getSubCategoria().getCategoriaConta().getDescricao()).put(despesa.getSubCategoria().getDescricao(), dados2.get(despesa.getSubCategoria().getCategoriaConta().getDescricao()).get(despesa.getSubCategoria().getDescricao()) + despesa.getValor());
                }
            }
        }

        dados2.keySet().forEach((chave) -> {

            XYChart.Series dataSeries = new XYChart.Series();
            dataSeries.setName(chave);

            dados2.get(chave).keySet().forEach((chaveI) -> {
                dataSeries.getData().add(new XYChart.Data(chaveI, dados2.get(chave).get(chaveI)));
            });

            bc2.getData().add(dataSeries);
        });

        setGraficoBar(new BarChart<>(getxAxis(), getyAxis()));

        double saldo = movimentacaoController.getSaldo();

        Label label = new Label("SALDO: " + saldo);

        label.setAlignment(Pos.CENTER);
        Font font = new Font(20);
        label.setFont(font);
        if (saldo >= 0) {
            label.setTextFill(Color.GREEN);
        } else {
            label.setTextFill(Color.RED);
        }

        add(bc, 0, 0);
        add(bc2, 1, 0);

        Label lbLast = new Label("Últimos Lançamentos");
        Label lbFut = new Label("Lançamentos Futuros");
        lbFut.setFont(new Font("Arial", 18));
        lbLast.setFont(new Font("Arial", 18));

        this.add(lbLast, 0, 1);
        this.add(lbFut, 1, 1);
        this.add(new RelatorioFX(stage).showTable(true), 0, 2);
        this.add(new RelatorioFX(stage).showTable(false), 1, 2);

        this.setPadding(new Insets(5));
        this.setHgap(5);
        this.setVgap(5);
        ColumnConstraints c1 = new ColumnConstraints();
        c1.setHgrow(Priority.ALWAYS);
        c1.setHalignment(HPos.CENTER);
        c1.setPercentWidth(50);

        ColumnConstraints c2 = new ColumnConstraints();
        c2.setHgrow(Priority.ALWAYS);
        c2.setHalignment(HPos.CENTER);
        c2.setPercentWidth(50);

        RowConstraints r1 = new RowConstraints();
        r1.setVgrow(Priority.ALWAYS);
        r1.setValignment(VPos.CENTER);

        RowConstraints r2 = new RowConstraints();
        r2.setVgrow(Priority.ALWAYS);
        r2.setValignment(VPos.CENTER);

        RowConstraints r3 = new RowConstraints();
        r3.setVgrow(Priority.ALWAYS);
        r3.setValignment(VPos.CENTER);

        getColumnConstraints().addAll(c1, c2);
        getRowConstraints().addAll(r1, r2, r3);
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
