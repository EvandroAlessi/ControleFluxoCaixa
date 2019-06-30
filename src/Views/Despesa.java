/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import javafx.scene.text.Font;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;

/**
 *
 * @author SpaceBR
 */
public class Despesa extends GridPane{
    Label titulo;
    Button cadastrar;
    private TableView table;
    private TableColumn data, descricao, valor, pagamento;
    public Despesa(){
        titulo = new Label("Despesas");
        table = new TableView();
        data = new TableColumn("Ocorrência");
        descricao = new TableColumn("Descrição");
        valor = new TableColumn("Valor");
        pagamento = new TableColumn("Forma de Pagamento");
        cadastrar = new Button("Cadastrar");
        data.prefWidthProperty().bind(table.widthProperty().multiply(0.15));
        descricao.prefWidthProperty().bind(table.widthProperty().multiply(0.40));
        valor.prefWidthProperty().bind(table.widthProperty().multiply(0.15));
        pagamento.prefWidthProperty().bind(table.widthProperty().multiply(0.30));
        table.getColumns().addAll(data,descricao,valor,pagamento);
        this.add(titulo, 0, 0);
        this.add(cadastrar, 1, 0);
        this.add(table,0,1);
        
        titulo.setFont(new Font("Arial", 45));
        cadastrar.setMinSize(100, 50);
        ColumnConstraints c1 =  new ColumnConstraints();
        c1.setHgrow(Priority.ALWAYS);
        
        RowConstraints r1 = new RowConstraints();
        RowConstraints r2 = new RowConstraints();
        r1.setPercentHeight(5);
        r2.setVgrow(Priority.ALWAYS);
        this.getColumnConstraints().add(c1);
        this.getRowConstraints().addAll(r1,r2);
        this.setConstraints(titulo, 0, 0, 1, 1, HPos.CENTER, VPos.BASELINE);
        this.setConstraints(cadastrar, 0, 0, 1, 1, HPos.RIGHT, VPos.CENTER);
        GridPane.setColumnSpan(table, 2);
        
        cadastrar.setOnAction(e ->{
            CadastroDespesa cad = new CadastroDespesa();
        });
    }
}
