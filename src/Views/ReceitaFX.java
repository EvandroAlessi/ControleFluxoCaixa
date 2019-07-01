/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 *
 * @author SpaceBR
 */
public class ReceitaFX extends GridPane{
    Label titulo;
    Button cadastrar;
    private TableView table;
    private TableColumn data, descricao, valor, pagamento, categoria, subcategoria, apagar;
    
    public ReceitaFX(){
        titulo = new Label("Receitas");
        table = new TableView();
        data = new TableColumn("Ocorrência");
        descricao = new TableColumn("Descrição");
        valor = new TableColumn("Valor");
        pagamento = new TableColumn("Forma de Pagamento");
        cadastrar = new Button("Cadastrar");
        categoria = new TableColumn("Categoria");
        subcategoria = new TableColumn("Subcategoria");
        apagar = new TableColumn();
        apagar.prefWidthProperty().bind(table.widthProperty()
                .multiply(0.03));
        subcategoria.prefWidthProperty().bind(table.widthProperty()
                .multiply(0.15));
        categoria.prefWidthProperty().bind(table.widthProperty()
                .multiply(0.15));
        data.prefWidthProperty().bind(table.widthProperty()
                .multiply(0.10));
        descricao.prefWidthProperty().bind(table.widthProperty()
                .multiply(0.32));
        valor.prefWidthProperty().bind(table.widthProperty()
                .multiply(0.10));
        pagamento.prefWidthProperty().bind(
                table.widthProperty().multiply(0.15));
        table.getColumns().addAll(data,categoria,subcategoria,descricao,valor,pagamento,apagar);
        add(titulo, 0, 0);
        add(cadastrar, 1, 0);
        add(table,0,1);
        
        titulo.setFont(new Font("Arial", 45));
        cadastrar.setMinSize(100, 50);
        ColumnConstraints c1 =  new ColumnConstraints();
        c1.setHgrow(Priority.ALWAYS);
        
        RowConstraints r1 = new RowConstraints();
        RowConstraints r2 = new RowConstraints();
        //r1.setPercentHeight(5);
        r2.setVgrow(Priority.ALWAYS);
        getColumnConstraints().add(c1);
        getRowConstraints().addAll(r1,r2);
        setConstraints(titulo, 0, 0, 1, 1, HPos.CENTER, VPos.BASELINE);
        setConstraints(cadastrar, 0, 0, 1, 1, HPos.RIGHT, VPos.CENTER);
        GridPane.setColumnSpan(table, 2);
        
        cadastrar.setOnAction(e ->{
            CadastroFormFX form = new CadastroFormFX("Cadastro Receita");
            try {
                form.start(new Stage());
            } catch (Exception ex) {
                Logger.getLogger(DespesaFX.class.getName()).log(Level.SEVERE, 
                        null, ex);
            }
        });
    }
}