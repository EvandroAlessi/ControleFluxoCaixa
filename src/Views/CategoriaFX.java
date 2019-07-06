/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import Controllers.CategoriaContaController;
import Controllers.SubCategoriaController;
import Models.CategoriaConta;
import Models.Despesa;
import Models.SubCategoria;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 *
 * @author SpaceBR
 */
public class CategoriaFX extends GridPane{
    Label titulo;
    Button cadastrar;
    private TableView table;
    private TableColumn categoria,subcategoria,tipo;
    private TableColumn<SubCategoria, Void> apagar;
    private Stage mainStage;
    
    public CategoriaFX(Stage stage){
        mainStage = stage;
        SubCategoriaController control = new SubCategoriaController();
        CategoriaContaController control2 = new CategoriaContaController();
        titulo = new Label("Categorias");
        table = new TableView();
        categoria = new TableColumn("Categoria");
        subcategoria = new TableColumn("Subcategoria");
        tipo = new TableColumn("Tipo");
        apagar = new TableColumn("x");
        cadastrar = new Button("Cadastrar");
        
        
        
        categoria.prefWidthProperty().bind(table.widthProperty().multiply(0.40));
        subcategoria.prefWidthProperty().bind(table.widthProperty().multiply(0.40));
        tipo.prefWidthProperty().bind(table.widthProperty().multiply(0.17));
        apagar.prefWidthProperty().bind(table.widthProperty().multiply(0.03));
        
        subcategoria.setCellValueFactory(new PropertyValueFactory("descricao"));
        categoria.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SubCategoria, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<SubCategoria, String> param) {
                return new SimpleStringProperty(param.getValue().getCategoriaConta().getDescricao());
            }
        });
        tipo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SubCategoria, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<SubCategoria, String> param) {
                if(param.getValue().getCategoriaConta().isPositiva())
                    return new SimpleStringProperty("Receita");
                else
                    return new SimpleStringProperty("Despesa");
            }
        });
        
        Callback<TableColumn<SubCategoria, Void>, TableCell<SubCategoria, Void>> cellFactory = new Callback<TableColumn<SubCategoria, Void>, TableCell<SubCategoria, Void>>() {
            @Override
            public TableCell<SubCategoria, Void> call(final TableColumn<SubCategoria, Void> param) {
                final TableCell<SubCategoria, Void> cell = new TableCell<SubCategoria, Void>() {

                    private final Button btn = new Button("X");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            SubCategoria data = getTableView().getItems().get(getIndex());
                            control.delete(data.getSubCategoriaID());
                            table.getItems().remove(data);
                            table.refresh();
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        apagar.setCellFactory(cellFactory);
        
        table.getColumns().addAll(tipo,categoria,subcategoria,apagar);
        add(titulo, 0, 0);
        add(cadastrar, 1, 0);
        add(table,0,1);
        
        List<SubCategoria> subcategorias = control.getAll();
        
        this.table.setItems(FXCollections.observableArrayList(subcategorias));
                
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
            CadastroCategoria form = new CadastroCategoria();
            try {
                form.start(mainStage);
                List<SubCategoria> subcategorias2 = control.getAll();
                this.table.setItems(FXCollections.observableArrayList(subcategorias2));
            } catch (Exception ex) {
                Logger.getLogger(DespesaFX.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        categoria.setCellFactory(TextFieldTableCell.forTableColumn());
    }
}
