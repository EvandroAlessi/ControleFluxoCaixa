/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import Controllers.CategoriaContaController;
import Models.CategoriaConta;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 *
 * @author SpaceBR
 */
public class CategoriaFX extends GridPane {

    Label lbTitulo;
    Button btnCadastrar, btnEditar;
    private TableView<CategoriaConta> table;
    private TableColumn tcDescricao, tcTipo;
    private TableColumn<CategoriaConta, Void> tcApagar;
    private Stage mainStage;

    public CategoriaFX(Stage stage) {
        mainStage = stage;
        //SubCategoriaController control = new SubCategoriaController();
        CategoriaContaController categoriaController = new CategoriaContaController();
        lbTitulo = new Label("Categorias");
        table = new TableView();
        tcDescricao = new TableColumn("Descricao");
        //subcategoria = new TableColumn("Subcategoria");
        tcTipo = new TableColumn("Tipo");
        tcApagar = new TableColumn("");
        btnCadastrar = new Button("Cadastrar");
        btnEditar = new Button("Editar");

        tcDescricao.prefWidthProperty().bind(table.widthProperty()
                .multiply(0.638));
        //subcategoria.prefWidthProperty().bind(table.widthProperty()
        //.multiply(0.35));
        tcTipo.prefWidthProperty().bind(table.widthProperty()
                .multiply(0.30));
        tcApagar.prefWidthProperty().bind(table.widthProperty()
                .multiply(0.06));

        //subcategoria.setCellValueFactory(new PropertyValueFactory("descricao"));
        tcDescricao.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<CategoriaConta, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<CategoriaConta, String> param) {
                return new SimpleStringProperty(param.getValue().getDescricao());
            }
        });
        tcTipo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<CategoriaConta, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<CategoriaConta, String> param) {
                if (param.getValue().isPositiva()) {
                    return new SimpleStringProperty("Receita");
                } else {
                    return new SimpleStringProperty("Despesa");
                }
            }
        });

        Callback<TableColumn<CategoriaConta, Void>, TableCell<CategoriaConta, Void>> cellFactory = new Callback<TableColumn<CategoriaConta, Void>, TableCell<CategoriaConta, Void>>() {
            @Override
            public TableCell<CategoriaConta, Void> call(final TableColumn<CategoriaConta, Void> param) {
                final TableCell<CategoriaConta, Void> cell = new TableCell<CategoriaConta, Void>() {

                    private final Button btn = new Button("Remover");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            CategoriaConta data = getTableView().getItems().get(getIndex());
                            categoriaController.delete(data.getCategoriaContaID());
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
                cell.setAlignment(Pos.CENTER);
                return cell;
            }
        };
        
        tcApagar.setCellFactory(cellFactory);
        HBox hBox = new HBox();
        hBox.setPadding(new Insets(5));
        hBox.setSpacing(10);
        btnCadastrar.setMinSize(80, 40);
        btnEditar.setMinSize(80, 40);
        hBox.getChildren().addAll(btnCadastrar, btnEditar);

        table.getColumns().addAll(tcTipo, tcDescricao, tcApagar);
        add(lbTitulo, 0, 0);
        add(hBox, 1, 0);
        add(table, 0, 1);

        List<CategoriaConta> categorias = categoriaController.getAll();

        this.table.setItems(FXCollections.observableArrayList(categorias));

        lbTitulo.setFont(new Font("Arial", 32));
        lbTitulo.setPadding(new Insets(5, 5, 5, 10));
        ColumnConstraints c1 = new ColumnConstraints();
        c1.setHgrow(Priority.ALWAYS);

        RowConstraints r1 = new RowConstraints();
        RowConstraints r2 = new RowConstraints();
        //r1.setPercentHeight(5);
        r2.setVgrow(Priority.ALWAYS);
        getColumnConstraints().add(c1);
        getRowConstraints().addAll(r1, r2);
        setConstraints(lbTitulo, 0, 0, 1, 1, HPos.LEFT, VPos.BASELINE);
        setConstraints(btnCadastrar, 0, 0, 1, 1, HPos.RIGHT, VPos.CENTER);
        GridPane.setColumnSpan(table, 2);

        btnEditar.disableProperty().bind(table.getSelectionModel().selectedItemProperty().isNull());

        btnEditar.setOnAction(e -> {
            CadastroCategoriaFX form = new CadastroCategoriaFX();
            try {
                form.start(mainStage, table.getSelectionModel().getSelectedItem());
                table.refresh();
            } catch (Exception ex) {
                Logger.getLogger(DespesaFX.class.getName()).log(Level.SEVERE,
                        null, ex);
            }
        });

        btnCadastrar.setOnAction(e -> {
            CadastroCategoriaFX form = new CadastroCategoriaFX();
            try {
                form.start(mainStage, null);

                if (form.getCategoriaContaCriada() != null) {
                    table.getItems().add(form.getCategoriaContaCriada());
                }

            } catch (Exception ex) {
                Logger.getLogger(DespesaFX.class.getName()).log(Level.SEVERE,
                        null, ex);
            }
        });

        tcDescricao.setCellFactory(TextFieldTableCell.forTableColumn());
    }
}
