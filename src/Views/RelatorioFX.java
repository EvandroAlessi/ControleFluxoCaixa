/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import Controllers.MovimentacaoController;
import Models.Movimentacao;
import java.time.LocalDate;
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
import javafx.scene.control.DatePicker;
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
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * View responsável pela apresentação de um relátorio das últimas e futuras movimentações.
 * Apresentação com TableView.
 * Diretamente ligada ao Stage provido pela MainFX.
 * Utiliza o controlador de Movimentações.
 * @author Evandro Alessi
 * @author Eric Ueta
 * @see Movimentacao
 * @see MovimentacaoController
 * @see MainFX
 */
public class RelatorioFX extends GridPane {

    private Label lbTitulo;
    //Button btnCadastrar, btnEditar;
    private TableView<Movimentacao> table;
    private TableColumn tcData, tcDescricao, tcValor, tcPagamento, tcCategoria, tcSubCategoria;
    private final MovimentacaoController movimentacaoController;
    private TableColumn tcTipo;
    private final Stage mainStage;
    private DatePicker dpBeginDate;
    private DatePicker dpEndDate;
    private Button btnPesquisar;
    
    /** @param stage Recebe o stage principal de mainFX
    */
    public RelatorioFX(Stage stage) {
        mainStage = stage;
        movimentacaoController = new MovimentacaoController();
    }
    
    public RelatorioFX(Stage stage, boolean untilNow, String title) {
        mainStage = stage;
        movimentacaoController = new MovimentacaoController();
        lbTitulo = new Label(title);
        dpEndDate = new DatePicker();
        btnPesquisar = new Button("Pesquisar");
        showTable(untilNow);
        
        if (!untilNow) {
            dpBeginDate = new DatePicker(LocalDate.now().plusDays(1));
            dpBeginDate.setDisable(true);
            dpBeginDate.disarm();
        }
        else{
            dpBeginDate = new DatePicker();
        }
        
        HBox h = new HBox(new Label("De "), dpBeginDate, new Label(" Até "), dpEndDate, btnPesquisar);
        h.setAlignment(Pos.CENTER);
        h.setSpacing(10);
        
        btnPesquisar.setOnAction((event) -> {
            List<Movimentacao> movimentacoes = null;
            
            if (dpBeginDate.getValue() != null || dpEndDate.getValue() != null) {
                movimentacoes = movimentacaoController.getAll(untilNow, dpBeginDate.getValue(), dpEndDate.getValue());
            }
//            else if (dpBeginDate.getValue() != null){
//                movimentacaoController.getAll(untilNow, dpBeginDate.getValue(), null);
//            }
//            else if (dpEndDate.getValue() != null){
//                movimentacaoController.getAll(untilNow, null, dpEndDate.getValue());
//            }
            else{
                movimentacaoController.getAll(untilNow);
            }
            
            if (movimentacoes != null) {
                this.table.setItems(FXCollections.observableArrayList(movimentacoes));
                this.table.refresh();
            }
        });
        
        
        
        add(lbTitulo, 0, 0);
        add(h, 1, 0);
        add(table, 0, 1);
        
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
        GridPane.setColumnSpan(table, 2);
    }
    
    public TableView<Movimentacao> showTable(boolean untilNow){
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
        
        tcData.setCellValueFactory(new PropertyValueFactory<>("dateF"));
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
                            movimentacaoController.delete(data.getMovimentacaoID());
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
        
        this.setPadding(new Insets(5));
        table.getColumns().addAll(tcTipo, tcData, tcDescricao, tcPagamento, tcValor, tcCategoria, tcSubCategoria);
        table.setTableMenuButtonVisible(true);
        tcCategoria.setVisible(false);
        tcSubCategoria.setVisible(false);
       //table.getVisibleLeafColumns().setAll(tcTipo, tcData, tcDescricao, tcPagamento, tcValor, tcCategoria, tcSubCategoria);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        List<Movimentacao> movimentacoes = movimentacaoController.getAll(untilNow);

        this.table.setItems(FXCollections.observableArrayList(movimentacoes));
        
        return this.table;
    }
    
    public TableView<Movimentacao> showTinyTable(boolean untilNow){
        table = new TableView();
        tcData = new TableColumn("Ocorrência");
        tcDescricao = new TableColumn("Descrição");
        tcValor = new TableColumn("Valor");
        tcPagamento = new TableColumn("Forma de Pagamento");
        tcTipo = new TableColumn("Tipo");

        tcTipo.prefWidthProperty().bind(table.widthProperty()
                .multiply(0.06));
        tcData.prefWidthProperty().bind(table.widthProperty()
                .multiply(0.06));
        tcDescricao.prefWidthProperty().bind(table.widthProperty()
                .multiply(0.3478));
        tcValor.prefWidthProperty().bind(table.widthProperty()
                .multiply(0.15));
        tcPagamento.prefWidthProperty().bind(table.widthProperty()
                .multiply(0.15));

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

        Callback<TableColumn<Movimentacao, Void>, TableCell<Movimentacao, Void>> cellFactory = new Callback<TableColumn<Movimentacao, Void>, TableCell<Movimentacao, Void>>() {
            @Override
            public TableCell<Movimentacao, Void> call(final TableColumn<Movimentacao, Void> param) {
                final TableCell<Movimentacao, Void> cell = new TableCell<Movimentacao, Void>() {

                    private final Button btn = new Button("Remover");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Movimentacao data = getTableView().getItems().get(getIndex());
                            movimentacaoController.delete(data.getMovimentacaoID());
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
        
        this.setPadding(new Insets(5));
        table.getColumns().addAll(tcTipo, tcData, tcDescricao, tcPagamento, tcValor);
        List<Movimentacao> movimentacoes = movimentacaoController.getAll(untilNow);

        this.table.setItems(FXCollections.observableArrayList(movimentacoes));
        
        return this.table;
    }
}
