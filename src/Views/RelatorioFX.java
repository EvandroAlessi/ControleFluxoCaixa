/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import Controllers.MovimentacaoController;
import Models.Movimentacao;
import java.util.List;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import static javafx.scene.layout.GridPane.setConstraints;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 *
 * @author evand
 */
public class RelatorioFX extends GridPane {

    Label lbTitulo;
    //Button btnCadastrar, btnEditar;
    private TableView<Movimentacao> table;
    private TableColumn tcData, tcDescricao, tcValor, tcPagamento, tcCategoria, tcSubCategoria;
    //private TableColumn<Movimentacao, Void> apagar;
    private TableColumn tcTipo;
    private Stage mainStage;

    public RelatorioFX(Stage stage, boolean untilNow) {
        mainStage = stage;
        MovimentacaoController control = new MovimentacaoController();
        lbTitulo = new Label("Últimos lançamentos");
        table = new TableView();
        tcData = new TableColumn("Ocorrência");
        tcDescricao = new TableColumn("Descrição");
        tcValor = new TableColumn("Valor");
        tcPagamento = new TableColumn("Forma de Pagamento");
        tcCategoria = new TableColumn("Categoria");
        tcSubCategoria = new TableColumn("Subcategoria");
        tcTipo = new TableColumn("Tipo");

        tcTipo.prefWidthProperty().bind(table.widthProperty()
                .multiply(0.06));
        tcSubCategoria.prefWidthProperty().bind(table.widthProperty()
                .multiply(0.15));
        tcCategoria.prefWidthProperty().bind(table.widthProperty()
                .multiply(0.15));
        tcData.prefWidthProperty().bind(table.widthProperty()
                .multiply(0.06));
        tcDescricao.prefWidthProperty().bind(table.widthProperty()
                .multiply(0.378));
        tcValor.prefWidthProperty().bind(table.widthProperty()
                .multiply(0.10));
        tcPagamento.prefWidthProperty().bind(table.widthProperty()
                .multiply(0.10));

        tcTipo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Movimentacao, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Movimentacao, String> param) {
                if (param.getValue().getSubCategoria().getCategoriaConta().isPositiva()) {
                    return new SimpleStringProperty("Receita");
                } else {
                    return new SimpleStringProperty("Despesa");
                }
            }
        });
        tcData.setCellValueFactory(new PropertyValueFactory<>("dataOcorrencia"));
        tcSubCategoria.setCellValueFactory(new PropertyValueFactory<>("subCategoriaID"));
        tcDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        tcValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
        tcPagamento.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Movimentacao, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Movimentacao, String> param) {
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
        tcCategoria.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Movimentacao, String>, ObservableValue<String>>() {
            @Override
            // obtem a descrição da categoria e converte para Observable
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Movimentacao, String> param) {
                return new SimpleStringProperty(param.getValue().getSubCategoria().getCategoriaConta().getDescricao());
            }
        });
        tcSubCategoria.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Movimentacao, String>, ObservableValue<String>>() {
            @Override
            // obtem a descrição da subcategoria e converte para Observable
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Movimentacao, String> param) {
                return new SimpleStringProperty(param.getValue().getSubCategoria().getDescricao());
            }
        });

        Callback<TableColumn<Movimentacao, Void>, TableCell<Movimentacao, Void>> cellFactory = new Callback<TableColumn<Movimentacao, Void>, TableCell<Movimentacao, Void>>() {
            @Override
            public TableCell<Movimentacao, Void> call(final TableColumn<Movimentacao, Void> param) {
                final TableCell<Movimentacao, Void> cell = new TableCell<Movimentacao, Void>() {

                    private final Button btn = new Button("Remover");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Movimentacao data = getTableView().getItems().get(getIndex());
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

        table.getColumns().addAll(tcTipo, tcData, tcDescricao, tcPagamento, tcValor, tcCategoria, tcSubCategoria);

        add(lbTitulo, 0, 0);
        add(table, 0, 1);

        List<Movimentacao> movimentacoes = control.getAll(untilNow);

        this.table.setItems(FXCollections.observableArrayList(movimentacoes));

        lbTitulo.setFont(new Font("Arial", 32));
        lbTitulo.setPadding(new Insets(5, 5, 5, 10));
        ColumnConstraints c1 = new ColumnConstraints();
        c1.setHgrow(Priority.ALWAYS);

        RowConstraints r1 = new RowConstraints();
        RowConstraints r2 = new RowConstraints();
        r2.setVgrow(Priority.ALWAYS);
        getColumnConstraints().add(c1);
        getRowConstraints().addAll(r1, r2);
        setConstraints(lbTitulo, 0, 0, 1, 1, HPos.LEFT, VPos.BASELINE);
        GridPane.setColumnSpan(table, 2);
    }
}
