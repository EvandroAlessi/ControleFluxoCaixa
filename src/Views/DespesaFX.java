/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import DAO.MovimentacaoDAO;
import Models.Movimentacao;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.scene.text.Font;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author SpaceBR
 */
public class DespesaFX extends GridPane{
    Label titulo;
    Button cadastrar;
    private TableView table;
    private TableColumn data, pagamento, categoria, subcategoria, apagar;
    private TableColumn<String, DespesaFX> descricao = new TableColumn<>("Descrição");
    private TableColumn<Double, DespesaFX> valor = new TableColumn<>("Valor");
    
    public DespesaFX() {
        titulo = new Label("Despesas");
        table = new TableView();
        data = new TableColumn("Ocorrência");
        descricao = new TableColumn("Descrição");
        valor = new TableColumn("Valor");
        pagamento = new TableColumn("Forma de Pagamento");
        cadastrar = new Button("Cadastrar");
        categoria = new TableColumn("Categoria");
        subcategoria = new TableColumn("Subcategoria");
        apagar = new TableColumn();
        apagar.prefWidthProperty().bind(table.widthProperty().multiply(0.03));
        subcategoria.prefWidthProperty().bind(table.widthProperty().multiply(0.15));
        categoria.prefWidthProperty().bind(table.widthProperty().multiply(0.15));
        data.prefWidthProperty().bind(table.widthProperty().multiply(0.10));
        descricao.prefWidthProperty().bind(table.widthProperty().multiply(0.32));
        valor.prefWidthProperty().bind(table.widthProperty().multiply(0.10));
        pagamento.prefWidthProperty().bind(table.widthProperty().multiply(0.15));
        //table.getColumns().addAll(data,categoria,subcategoria,descricao,valor,pagamento);
        
        descricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        valor.setCellValueFactory(new PropertyValueFactory<>("valor"));
        table.getColumns().addAll(descricao, valor);
        
//        List<Movimentacao> movimentacoes = new MovimentacaoDAO().getAll();
//        List<Movimentacao> despesas = new ArrayList<>();
//        
//        for(Movimentacao movimentacao: movimentacoes){
//            if(!movimentacao.getSubcategoria().getCategoriaConta().isPositiva()){
//                despesas.add(movimentacao);
//            }
//        }
        
        //this.table.setItems(FXCollections.observableArrayList(despesas));
        
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
            FormFX form = new FormFX("Cadrastro Despesa");
            try {
                form.start(new Stage());
            } catch (Exception ex) {
                Logger.getLogger(DespesaFX.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
}
