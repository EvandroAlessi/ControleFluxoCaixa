/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import Controllers.ReceitaController;
import CrossCutting.Log;
import CrossCutting.Mensagem;
import Models.Receita;
import static Views.MenuLateralFX.btnSaldo;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
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
import javafx.scene.control.cell.PropertyValueFactory;
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
 * View responsável pela apresentação das Receitas.
 * Apresentação com TableView.
 * Diretamente ligada ao Stage provido pela MainFX.
 * Utiliza o controlador de Receitas.
 * @author Evandro Alessi
 * @author Eric Ueta
 * @see Receita
 * @see ReceitaController
 * @see MainFX
 */
public class ReceitaFX extends GridPane {
    // Declaração de componentes
    Label lbTitulo;
    Button btnCadastrar, btnEditar;
    private TableView<Receita> table;
    private TableColumn<Receita, String> tcData;
    private TableColumn tcDescricao, tcValor, tcPagamento, tcCategoria, tcSubCategoria;
    private TableColumn<Receita, Void> tcApagar;
    private Stage mainStage;
    
    /** @param stage Recebe o stage principal de mainFX
    */
    public ReceitaFX(Stage stage) {
        // Instanciação de componentes
        mainStage = stage;
        ReceitaController control = new ReceitaController();
        lbTitulo = new Label("Receitas");
        table = new TableView();
        tcData = new TableColumn("Ocorrência");
        tcDescricao = new TableColumn("Descrição");
        tcValor = new TableColumn("Valor");
        tcPagamento = new TableColumn("Pagamento");
        btnCadastrar = new Button("Cadastrar");
        btnEditar = new Button("Editar");
        tcCategoria = new TableColumn("Categoria");
        tcSubCategoria = new TableColumn("Subcategoria");
        tcApagar = new TableColumn("Ações");

        tcApagar.prefWidthProperty().bind(table.widthProperty()
                .multiply(0.06));
        tcSubCategoria.prefWidthProperty().bind(table.widthProperty()
                .multiply(0.15));
        tcCategoria.prefWidthProperty().bind(table.widthProperty()
                .multiply(0.15));
        tcData.prefWidthProperty().bind(table.widthProperty()
                .multiply(0.10));
        tcDescricao.prefWidthProperty().bind(table.widthProperty()
                .multiply(0.338));
        tcValor.prefWidthProperty().bind(table.widthProperty()
                .multiply(0.10));
        tcPagamento.prefWidthProperty().bind(table.widthProperty()
                .multiply(0.10));

        tcData.setCellValueFactory(new PropertyValueFactory<>("dateF"));
        tcSubCategoria.setCellValueFactory(new PropertyValueFactory<>("subCategoriaID"));
        tcDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        tcValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
        tcPagamento.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Receita, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Receita, String> param) {
                switch (param.getValue().getFormaPagamento()) {
                    case 1:
                        return new SimpleStringProperty("Crédito");
                    case 2:
                        return new SimpleStringProperty("Dinheiro");
                    case 3:
                        return new SimpleStringProperty("Boleto");
                    case 4:
                        return new SimpleStringProperty("Depósito");
                    case 5:
                        return new SimpleStringProperty("Convênio");
                }
                return new SimpleStringProperty("Indefinido");
            }
        });
        tcCategoria.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Receita, String>, ObservableValue<String>>() {
            @Override
            // obtem a descrição da categoria e converte para Observable
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Receita, String> param) {
                return new SimpleStringProperty(param.getValue().getSubCategoria().getCategoriaConta().getDescricao());
            }
        });
        tcSubCategoria.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Receita, String>, ObservableValue<String>>() {
            @Override
            // obtem a descrição da subcategoria e converte para Observable
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Receita, String> param) {
                return new SimpleStringProperty(param.getValue().getSubCategoria().getDescricao());
            }
        });

        Callback<TableColumn<Receita, Void>, TableCell<Receita, Void>> cellFactory = new Callback<TableColumn<Receita, Void>, TableCell<Receita, Void>>() {
            @Override
            public TableCell<Receita, Void> call(final TableColumn<Receita, Void> param) {
                final TableCell<Receita, Void> cell = new TableCell<Receita, Void>() {

                    private final Button btn = new Button("Remover");

                    {
                        // Evento para confirmação e exclusão
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
                                    Receita data = getTableView().getItems().get(getIndex());
                                    control.delete(data.getMovimentacaoID());
                                    table.getItems().remove(data);
                                    table.refresh();
                                    btnSaldo.fire();
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
        // Menu de Contexto e 2 cliques
        table.setRowFactory(new Callback<TableView<Receita>, TableRow<Receita>>() {
            public TableRow<Receita> call(TableView<Receita> tableView) {
                final TableRow<Receita> row = new TableRow<>();
                final ContextMenu rowMenu = new ContextMenu();
                MenuItem removeItem = new MenuItem("Remover");
                MenuItem editaItem = new MenuItem("Editar");
                // Evento de remoção
                removeItem.setOnAction(e -> {
                    ButtonType btnSim = new ButtonType("Sim");
                    ButtonType btnNao = new ButtonType("Não");
                    Alert dialog = new Alert(Alert.AlertType.WARNING, "", btnSim, btnNao);
                    dialog.setTitle("Confimação de exclusão");
                    dialog.setHeaderText("Deseja realmente excluir?");
                    dialog.setContentText("Tem certeza?");
                    Window window = dialog.getDialogPane().getScene().getWindow();
                    window.setOnCloseRequest(ev -> dialog.hide());
                    Optional<ButtonType> result = dialog.showAndWait();
                    result.ifPresent(res -> {
                        if (res.equals(btnSim)) {
                            Receita data = table.getSelectionModel().getSelectedItem();
                            control.delete(data.getMovimentacaoID());
                            table.getItems().remove(data);
                            table.refresh();
                            table.getItems().remove(row.getItem());
                            btnSaldo.fire();
                        } else if (res.equals(btnNao)) {
                            dialog.close();
                        }
                    });
                    
                });
                // Evento de edição
                editaItem.setOnAction(e -> {
                    CadastroReceitaFX form = new CadastroReceitaFX();
                    try {
                        form.start(mainStage, table.getSelectionModel().getSelectedItem());
                        table.refresh();
                        btnSaldo.fire();
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
                // Evento 2 cliques
                row.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent e) {
                        if (e.getClickCount() == 2 && (!row.isEmpty())) {
                            CadastroReceitaFX form = new CadastroReceitaFX();
                            try {
                                form.start(mainStage, table.getSelectionModel().getSelectedItem());
                                table.refresh();
                                btnSaldo.fire();
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
        table.getColumns().addAll(tcData, tcDescricao, tcPagamento, tcValor, tcCategoria, tcSubCategoria, tcApagar);
        table.setTableMenuButtonVisible(true);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tcCategoria.setVisible(false);
        tcSubCategoria.setVisible(false);

        HBox hBox = new HBox();
        hBox.getChildren().addAll(btnCadastrar, btnEditar);
        hBox.setSpacing(10);
        hBox.setPadding(new Insets(5));

        add(lbTitulo, 0, 0);
        add(hBox, 1, 0);
        add(table, 0, 1);

        List<Receita> receitas = control.getAll();

        this.table.setItems(FXCollections.observableArrayList(receitas));
        this.setPadding(new Insets(5));
        this.setHgap(5);
        this.setVgap(5);

        lbTitulo.setFont(new Font("Arial", 24));
        lbTitulo.setPadding(new Insets(15, 15, 15, 5));
        btnCadastrar.setMinSize(80, 40);
        btnEditar.setMinSize(80, 40);
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
            CadastroReceitaFX form = new CadastroReceitaFX();
            try {
                form.start(mainStage, table.getSelectionModel().getSelectedItem());
                table.refresh();
                btnSaldo.fire();
            } catch (Exception ex) {
                Log.saveLog(ex);
                Mensagem.excecao(ex);
            }
        });

        btnCadastrar.setOnAction(e -> {
            CadastroReceitaFX form = new CadastroReceitaFX();
            try {
                form.start(mainStage, null);
                //&& form.getDespesaCriada().getDataOcorrencia() <= LocalDate.now()
                if (form.getReceitaCriada() != null && (form.getReceitaCriada().getDataOcorrencia().equals(LocalDate.now()) || form.getReceitaCriada().getDataOcorrencia().isBefore(LocalDate.now()))&& form.getReceitaCriada().getDescricao() != null) {
                    table.getItems().add(form.getReceitaCriada());
                    btnSaldo.fire();
                }

            } catch (Exception ex) {
                Log.saveLog(ex);
                Mensagem.excecao(ex);
            }
        });
    }
}
