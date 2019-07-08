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
import java.util.List;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
public class ReceitaFX extends GridPane {

    Label lbTitulo;
    Button btnCadastrar, btnEditar;
    private TableView<Receita> table;
    private TableColumn<Receita, String> tcData;
    private TableColumn tcDescricao, tcValor, tcPagamento, tcCategoria, tcSubCategoria;
    private TableColumn<Receita, Void> tcApagar;
    private Stage mainStage;

    public ReceitaFX(Stage stage) {
        mainStage = stage;
        ReceitaController control = new ReceitaController();
        lbTitulo = new Label("Receitas");
        table = new TableView();
        tcData = new TableColumn("Ocorrência");
        tcDescricao = new TableColumn("Descrição");
        tcValor = new TableColumn("Valor");
        tcPagamento = new TableColumn("Forma de Pagamento");
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
        
        tcData.setCellValueFactory(new PropertyValueFactory<>("dataOcorrencia"));
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
                        btn.setOnAction((ActionEvent event) -> {
                            Receita data = getTableView().getItems().get(getIndex());
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
                if (form.getReceitaCriada() != null) {
                    table.getItems().add(form.getReceitaCriada());
                }

            } catch (Exception ex) {
                Log.saveLog(ex);
                Mensagem.excecao(ex);
            }
        });
    }
}
