/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import Controllers.SubCategoriaController;
import CrossCutting.Log;
import CrossCutting.Mensagem;
import Models.SubCategoria;
import java.util.List;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Callback;

/**
 * View responsável pela apresentação das Categorias e SubCategorias.
 * Apresentação com Table View.
 * Diretamente ligada ao Stage provido pela MainFX
 * Utiliza o controlador de SubCategorias
 * @author Evandro Alessi
 * @author Eric Ueta
 * @see CategoriaConta
 * @see SubCategoria
 * @see SubCategoriaController
 * @see MainFX
 */
public class CategoriaSubFX extends GridPane {

    Label lbTitulo;
    Button btnCadastrar, btnEditar;
    private TableView<SubCategoria> table;
    private TableColumn tcDescricao, tcTipo;
    private TableColumn<SubCategoria, Void> tcApagar;
    private TableColumn<SubCategoria, String> tcTipoCategoria;
    private Stage mainStage;

    /** @param stage Recebe o stage principal de mainFX
    */
    public CategoriaSubFX(Stage stage) {
        mainStage = stage;
        SubCategoriaController subCategoriaController = new SubCategoriaController();
        lbTitulo = new Label("Categorias");
        table = new TableView();
        tcDescricao = new TableColumn("Descricao");
        tcTipo = new TableColumn("Tipo");
        tcApagar = new TableColumn("Ações");
        tcTipoCategoria = new TableColumn("Tipo Receita/Despesa");
        btnCadastrar = new Button("Cadastrar");
        btnEditar = new Button("Editar");

//        tcDescricao.prefWidthProperty().bind(table.widthProperty()
//                .multiply(0.638));
//        tcTipoCategoria.prefWidthProperty().bind(table.widthProperty()
//                .multiply(0.15));
//        tcTipo.prefWidthProperty().bind(table.widthProperty()
//                .multiply(0.15));
//        tcApagar.prefWidthProperty().bind(table.widthProperty()
//                .multiply(0.06));

        tcDescricao.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SubCategoria, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<SubCategoria, String> param) {
                return new SimpleStringProperty(param.getValue().getDescricao());
            }
        });

        tcTipo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SubCategoria, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<SubCategoria, String> param) {
                if (param.getValue().getCategoriaConta().isPositiva()) {
                    return new SimpleStringProperty("Receita");
                } else {
                    return new SimpleStringProperty("Despesa");
                }
            }
        });

        tcTipoCategoria.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SubCategoria, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<SubCategoria, String> param) {
                return new SimpleStringProperty(param.getValue().getCategoriaConta().getDescricao());
            }
        });

        Callback<TableColumn<SubCategoria, Void>, TableCell<SubCategoria, Void>> cellFactory = new Callback<TableColumn<SubCategoria, Void>, TableCell<SubCategoria, Void>>() {
            @Override
            public TableCell<SubCategoria, Void> call(final TableColumn<SubCategoria, Void> param) {
                final TableCell<SubCategoria, Void> cell = new TableCell<SubCategoria, Void>() {

                    private final Button btn = new Button("Remover");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Alert dialog = new Alert(Alert.AlertType.WARNING);
                            ButtonType btnSim = new ButtonType("Sim", ButtonBar.ButtonData.OK_DONE);
                            ButtonType btnNao = new ButtonType("Não", ButtonBar.ButtonData.CANCEL_CLOSE);
                            dialog.setTitle("Confimação de exclusão");
                            dialog.setHeaderText("Deseja realmente excluir?");
                            dialog.setContentText("Tem certeza?");
                            dialog.getButtonTypes().setAll(btnSim, btnNao);
                            Window window = dialog.getDialogPane().getScene().getWindow();
                            window.setOnCloseRequest(e -> dialog.hide());
                            dialog.showAndWait().ifPresent(b -> {
                                if (b == btnSim) {
                                    SubCategoria data = getTableView().getItems().get(getIndex());
                                    if (subCategoriaController.delete(data.getSubCategoriaID())) {
                                        table.getItems().remove(data);
                                        table.refresh();
                                    } else {
                                        dialog.close();
                                        if (data.getCategoriaConta().isPositiva()) {
                                            Mensagem.aviso("Existem receitas cadastradas para está categoria, só é possivel apagar a categoria se ela não estiver vinculada a receitas.");
                                        } else {
                                            Mensagem.aviso("Existem despesas cadastradas para está categoria, só é possivel apagar a categoria se ela não estiver vinculada a despesas.");
                                        }
                                    }
                                } else {
                                    dialog.close();
                                }
                            });
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

        table.setRowFactory(new Callback<TableView<SubCategoria>, TableRow<SubCategoria>>() {
            public TableRow<SubCategoria> call(TableView<SubCategoria> tableView) {
                final TableRow<SubCategoria> row = new TableRow<>();
                final ContextMenu rowMenu = new ContextMenu();
                MenuItem removeItem = new MenuItem("Remover");
                MenuItem editaItem = new MenuItem("Editar");
                removeItem.setOnAction(e -> {
                    Alert dialog = new Alert(Alert.AlertType.WARNING);
                    ButtonType btnSim = new ButtonType("Sim");
                    ButtonType btnNao = new ButtonType("Não");
                    dialog.setTitle("Confimação de exclusão");
                    dialog.setHeaderText("Deseja realmente excluir?");
                    dialog.setContentText("Tem certeza?");
                    dialog.getButtonTypes().setAll(btnSim, btnNao);
                    dialog.showAndWait().ifPresent(b -> {
                        if (b == btnSim) {
                            SubCategoria data = table.getSelectionModel().getSelectedItem();
                            subCategoriaController.delete(data.getSubCategoriaID());
                            table.getItems().remove(data);
                            table.refresh();
                        } else {
                            dialog.close();
                        }
                    });

                });

                editaItem.setOnAction(e -> {
                    CadastroCategoriaSubFX form = new CadastroCategoriaSubFX();
                    try {
                        form.start(mainStage, table.getSelectionModel().getSelectedItem());
                        table.refresh();
                    } catch (Exception ex) {
                        Log.saveLog(ex);
                        Mensagem.excecao(ex);
                    }
                });
                rowMenu.getItems().addAll(editaItem, removeItem);
                row.contextMenuProperty().bind(
                        Bindings.when(Bindings.isNotNull(row.itemProperty()))
                                .then(rowMenu)
                                .otherwise((ContextMenu) null));

                row.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent e) {
                        if (e.getClickCount() == 2 && (!row.isEmpty())) {
                            CadastroCategoriaSubFX form = new CadastroCategoriaSubFX();
                            try {
                                form.start(mainStage, table.getSelectionModel().getSelectedItem());
                                table.refresh();
                            } catch (Exception ex) {
                                Log.saveLog(ex);
                                Mensagem.excecao(ex);
                            }
                        }
                    }
                });

                return row;
            }
        });

        tcApagar.setCellFactory(cellFactory);
        HBox hBox = new HBox();
        hBox.setPadding(new Insets(5));
        hBox.setSpacing(10);
        btnCadastrar.setMinSize(80, 40);
        btnEditar.setMinSize(80, 40);
        hBox.getChildren().addAll(btnCadastrar, btnEditar);

        table.getColumns().addAll(tcTipo, tcDescricao, tcTipoCategoria, tcApagar);
        table.setTableMenuButtonVisible(true);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        add(lbTitulo, 0, 0);
        add(hBox, 1, 0);
        add(table, 0, 1);

        List<SubCategoria> categorias = subCategoriaController.getAll();

        this.table.setItems(FXCollections.observableArrayList(categorias));
        this.setPadding(new Insets(5));
        this.setHgap(5);
        this.setVgap(5);

        lbTitulo.setFont(new Font("Arial", 24));
        lbTitulo.setPadding(new Insets(15, 15, 15, 5));
        ColumnConstraints c1 = new ColumnConstraints();
        c1.setHgrow(Priority.ALWAYS);

        RowConstraints r1 = new RowConstraints();
        RowConstraints r2 = new RowConstraints();
        r2.setVgrow(Priority.ALWAYS);
        getColumnConstraints().add(c1);
        getRowConstraints().addAll(r1, r2);
        setConstraints(lbTitulo, 0, 0, 1, 1, HPos.LEFT, VPos.BASELINE);
        setConstraints(btnCadastrar, 0, 0, 1, 1, HPos.RIGHT, VPos.CENTER);
        GridPane.setColumnSpan(table, 2);

        btnEditar.disableProperty().bind(table.getSelectionModel().selectedItemProperty().isNull());

        btnEditar.setOnAction(e -> {
            CadastroCategoriaSubFX form = new CadastroCategoriaSubFX();
            try {
                form.start(mainStage, table.getSelectionModel().getSelectedItem());
                table.refresh();
            } catch (Exception ex) {
                Log.saveLog(ex);
                Mensagem.excecao(ex);
            }
        });

        btnCadastrar.setOnAction(e -> {
            CadastroCategoriaSubFX form = new CadastroCategoriaSubFX();
            try {
                form.start(mainStage, null);

                if (form.getSubCategoriaCriada() != null) {
                    table.getItems().add(form.getSubCategoriaCriada());
                }

            } catch (Exception ex) {
                Log.saveLog(ex);
                Mensagem.excecao(ex);
            }
        });

        tcDescricao.setCellFactory(TextFieldTableCell.forTableColumn());
    }
}
