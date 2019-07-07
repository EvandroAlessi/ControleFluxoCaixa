/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import Controllers.DespesaController;
import CrossCutting.Log;
import CrossCutting.Mensagem;
import Models.Despesa;
import java.time.LocalDate;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.text.Font;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import static javafx.scene.layout.GridPane.setConstraints;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 *
 * @author SpaceBR
 */
public class DespesaFX extends GridPane {

    Label lbTitulo;
    Button btnCadastrar, btnEditar;
    private TableView<Despesa> table;
    private TableColumn<Despesa, LocalDate> tcData;
    private TableColumn tcDescricao, tcValor, tcPagamento, tcCategoria, tcSubCategoria;
    private TableColumn<Despesa, Void> tcApagar;
    private Stage mainStage;

    public DespesaFX(Stage stage) {
        mainStage = stage;
        DespesaController control = new DespesaController();
        lbTitulo = new Label("Despesas");
        table = new TableView();
        tcData = new TableColumn("Ocorrência");
        tcDescricao = new TableColumn("Descrição");
        tcValor = new TableColumn("Valor");
        tcPagamento = new TableColumn("Forma de Pagamento");
        btnCadastrar = new Button("Cadastrar");
        btnEditar = new Button("Editar");
        tcCategoria = new TableColumn("Categoria");
        tcSubCategoria = new TableColumn("Subcategoria");
        tcApagar = new TableColumn("");

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

        tcData.setCellValueFactory(new PropertyValueFactory<>("dataOcorrencia"));
        tcSubCategoria.setCellValueFactory(new PropertyValueFactory<>("subCategoriaID"));
        tcDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        tcValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
        tcPagamento.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Despesa, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Despesa, String> param) {
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
        tcCategoria.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Despesa, String>, ObservableValue<String>>() {
            @Override
            // obtem a descrição da categoria e converte para Observable
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Despesa, String> param) {
                return new SimpleStringProperty(param.getValue().getSubCategoria().getCategoriaConta().getDescricao());
            }
        });
        tcSubCategoria.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Despesa, String>, ObservableValue<String>>() {
            @Override
            // obtem a descrição da subcategoria e converte para Observable
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Despesa, String> param) {
                return new SimpleStringProperty(param.getValue().getSubCategoria().getDescricao());
            }
        });

        Callback<TableColumn<Despesa, Void>, TableCell<Despesa, Void>> cellFactory = new Callback<TableColumn<Despesa, Void>, TableCell<Despesa, Void>>() {
            @Override
            public TableCell<Despesa, Void> call(final TableColumn<Despesa, Void> param) {
                final TableCell<Despesa, Void> cell = new TableCell<Despesa, Void>() {

                    private final Button btn = new Button("Remover");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Despesa data = getTableView().getItems().get(getIndex());
                            control.delete(data.getMovimentacaoID());
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
        table.getColumns().addAll(tcData, tcDescricao, tcPagamento, tcValor, tcCategoria, tcSubCategoria, tcApagar);

        HBox hBox = new HBox();
        hBox.getChildren().addAll(btnCadastrar, btnEditar);
        hBox.setSpacing(10);
        hBox.setPadding(new Insets(5));

        add(lbTitulo, 0, 0);
        add(hBox, 1, 0);
        add(table, 0, 1);

        List<Despesa> despesas = control.getAll();

        this.table.setItems(FXCollections.observableArrayList(despesas));

        lbTitulo.setFont(new Font("Arial", 32));
        lbTitulo.setPadding(new Insets(5, 5, 5, 10));
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
            CadastroDespesaFX form = new CadastroDespesaFX();
            try {
                form.start(mainStage, table.getSelectionModel().getSelectedItem());
                table.refresh();
            } catch (Exception ex) {
                Log.saveLog(ex);
                Mensagem.excecao(ex);
            }
        });

        btnCadastrar.setOnAction(e -> {
            CadastroDespesaFX form = new CadastroDespesaFX();
            try {
                form.start(mainStage, null);
                //&& form.getDespesaCriada().getDataOcorrencia() <= LocalDate.now()
                if (form.getDespesaCriada() != null) {
                    table.getItems().add(form.getDespesaCriada());
                }

            } catch (Exception ex) {
                Log.saveLog(ex);
                Mensagem.excecao(ex);
            }
        });
    }
}
