/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import Controllers.CategoriaContaController;
import Controllers.DespesaController;
import Controllers.SubCategoriaController;
import CrossCutting.Enums.FormaPagamento;
import CrossCutting.Mensagem;
import Models.CategoriaConta;
import Models.Despesa;
import Models.SubCategoria;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * View com formulário de cadastro para Despesas.
 * Diretamente ligada ao Stage provido pela DespesaFX
 * Utiliza o controlador de CategoriaConta, SubCategoria e Despesa
 * @author Evandro Alessi
 * @author Eric Ueta
 * @see CategoriaConta
 * @see SubCategoria
 * @see Despesa
 * @see DespesaController
 * @see CategoriaContaController
 * @see SubCategoriaController
 * @see DespesaFX
 */
public class CadastroDespesaFX {

    private final Label lbData, lbSubCategoria, lbDescricao, lbValor, lbPagamento, lbTitle, lbCategoria;
    private final TextField tfDescricao, tfValor;
    private final DatePicker dpCalendario;
    private final Button btnCadastrar, btnCancelar;
    private final SubCategoriaController subCategoriaController;
    private final CategoriaContaController categoriaController;
    private final List<SubCategoria> subCategorias;
    private final List<CategoriaConta> categoriasConta;
    private ComboBox<FormaPagamento> cbPagamento;
    private ComboBox<SubCategoria> cbSubCategoria;
    private final ComboBox<CategoriaConta> cbCategoria;
    private DespesaController despesaController;
    private Stage dialog;
    private Despesa despesaCriada;

    public CadastroDespesaFX() {
        despesaCriada = null;
        lbDescricao = new Label("Descrição:");
        tfDescricao = new TextField();
        tfValor = new TextField();
        lbTitle = new Label("Nova Despesa");
        lbData = new Label("Ocorrência:");
        lbSubCategoria = new Label("Categoria:");
        lbCategoria = new Label("Tipo Despesa:");
        lbValor = new Label("Valor:");
        lbPagamento = new Label("Pagamento:");
        btnCadastrar = new Button("Cadastrar");
        btnCancelar = new Button("Cancelar");
        dpCalendario = new DatePicker();
        subCategoriaController = new SubCategoriaController();
        categoriaController = new CategoriaContaController();
        despesaController = new DespesaController();
        subCategorias = subCategoriaController.getAll();
        categoriasConta = categoriaController.getAll();
        cbSubCategoria = new ComboBox();
        cbPagamento = new ComboBox();
        cbCategoria = new ComboBox();

        btnCadastrar.setOnAction(e -> {
            boolean ok = false;
            if (tfDescricao.getText().trim().length() > 0 && tfDescricao.getText() != null) {
                if (tfValor.getText().trim().length() > 0 && tfValor.getText() != null) {
                    if (cbSubCategoria.getSelectionModel().getSelectedItem() != null) {
                        ok = true;
                    } else {
                        Mensagem.aviso("A Despesa deve ter um Categoria.");
                    }
                } else {
                    tfValor.setStyle("-fx-text-box-border: red ;"
                            + " -fx-focus-color: red ;");
                    Mensagem.informacao("A Despesa deve ter um valor!");
                }
            } else {
                tfDescricao.setStyle("-fx-text-box-border: red ;"
                        + " -fx-focus-color: red ;");
                Mensagem.informacao("A Despesa deve ter uma Descrição!");
                if (cbSubCategoria.getSelectionModel().getSelectedItem() == null) {
                    Mensagem.aviso("A Despesa deve ter um Categoria.");
                }
                if (tfValor.getText().trim().length() == 0 || tfValor.getText() == null) {
                    tfValor.setStyle("-fx-text-box-border: red ;"
                            + " -fx-focus-color: red ;");
                    Mensagem.informacao("A Despesa deve ter um valor!");
                }
            }
            if (ok) {
                ok = false;
                if (despesaCriada == null) {

                    Despesa nDespesa = new Despesa();

                    try {
                        nDespesa.setDescricao(tfDescricao.getText());
                        nDespesa.setDataOcorrencia(dpCalendario.getValue());
                        nDespesa.setValor(Double.parseDouble(tfValor.getText()));
                        nDespesa.setFormaPagamento(cbPagamento.getSelectionModel().getSelectedItem().getValue());
                        nDespesa.setSubCategoria(cbSubCategoria.getSelectionModel().getSelectedItem());
                        despesaCriada = despesaController.create(nDespesa);
                        if (despesaCriada != null) {
                            ok = true;
                        }
                    } catch (Exception ex) {
                        Mensagem.informacao("O valor deve ser um número!");
                    }
                } else {
                    try {
                        despesaCriada.setDescricao(tfDescricao.getText());
                        despesaCriada.setDataOcorrencia(dpCalendario.getValue());
                        despesaCriada.setValor(Double.parseDouble(tfValor.getText()));
                        despesaCriada.setFormaPagamento(cbPagamento.getSelectionModel().getSelectedItem().getValue());
                        despesaCriada.setSubCategoria(cbSubCategoria.getSelectionModel().getSelectedItem());
                        despesaCriada = despesaController.update(despesaCriada);
                        if (despesaCriada != null) {
                            ok = true;
                        }
                    } catch (Exception ex) {
                        Mensagem.informacao("O valor deve ser um número!");
                    }
                }

                if (ok) {
                    dialog.close();
                }
            }
        });

        btnCancelar.setOnAction(e -> {
            dialog.close();
        });
    }

    public void start(Stage mainStage, Despesa despesaCriada) throws Exception {
        dialog = new Stage();
        GridPane painel = criarFormulario(despesaCriada);
        dialog.initOwner(mainStage);
        dialog.initModality(Modality.APPLICATION_MODAL);

        Scene scene = new Scene(painel, 400, 420);

        dialog.setResizable(false);
        dialog.setMaxHeight(420);
        dialog.setMaxWidth(400);
        dialog.setMinHeight(420);
        dialog.setMinWidth(400);

        dialog.setScene(scene);
        dialog.showAndWait();
    }

    private GridPane criarFormulario(Despesa despesaCriada) {
        GridPane pane = new GridPane();
        HBox l1 = new HBox();
        List<SubCategoria> subCatDespesa = new ArrayList<>();
        for (SubCategoria despesa : subCategorias) {
            if (!despesa.getCategoriaConta().isPositiva()) {
                subCatDespesa.add(despesa);
            }
        }
        List<CategoriaConta> catDespesa = new ArrayList<>();

        for (CategoriaConta despesa : categoriasConta) {
            if (!despesa.isPositiva()) {
                catDespesa.add(despesa);
            }
        }

        ListIterator<SubCategoria> subIte = subCatDespesa.listIterator();
        pane.setAlignment(Pos.TOP_CENTER);
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setPadding(new Insets(25, 10, 25, 25));

        cbPagamento.getItems().addAll(FormaPagamento.values());

        cbCategoria.setItems(FXCollections.observableArrayList(catDespesa));

        cbSubCategoria.setDisable(false);

        cbCategoria.getSelectionModel().selectedItemProperty().addListener((opcoes, valorAntigo, valorNovo) -> {
            List<SubCategoria> subCatDespesaSelected = new ArrayList<>();
            for (SubCategoria despesa : subCatDespesa) {
                if (despesa.getCategoriaConta().getCategoriaContaID() == valorNovo.getCategoriaContaID()) {
                    subCatDespesaSelected.add(despesa);
                }
            }
            cbSubCategoria.setItems(FXCollections.observableArrayList(subCatDespesaSelected));
            if (subCatDespesaSelected.size() > 0) {
                cbSubCategoria.setDisable(false);
                cbSubCategoria.getSelectionModel().selectFirst();
            } else {
                cbSubCategoria.setDisable(true);
            }
        });

        ColumnConstraints c1 = new ColumnConstraints();
        //c1.setHgrow(Priority.ALWAYS);
        c1.setHalignment(HPos.CENTER);

        RowConstraints r1 = new RowConstraints();
        //r1.setVgrow(Priority.ALWAYS);
        r1.setValignment(VPos.CENTER);

        pane.getColumnConstraints().add(c1);
        pane.getRowConstraints().add(r1);

        cbCategoria.getSelectionModel().selectFirst();

        l1.getChildren().addAll(btnCadastrar, btnCancelar);
        l1.setSpacing(10);
        l1.setPadding(new Insets(35, 0, -10, 0));
        l1.setAlignment(Pos.CENTER);

        lbTitle.setPadding(new Insets(0, 0, 25, 0));
        lbTitle.setAlignment(Pos.CENTER);

        cbPagamento.getSelectionModel().selectFirst();
        dpCalendario.setValue(LocalDate.now());

        cbCategoria.setPrefWidth(200);
        cbSubCategoria.setPrefWidth(200);
        cbPagamento.setPrefWidth(200);
        tfDescricao.setPrefWidth(200);
        tfValor.setPrefWidth(200);
        dpCalendario.setPrefWidth(200);

        tfValor.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {

                try {
                    Double.parseDouble(tfValor.getText());
                } catch (Exception e) {
                    tfValor.setText(tfValor.getText().substring(0, tfValor.getText().length() - 1));
                }
                finally{
                    if (tfValor.getText().length() > 7) {
                        String s = tfValor.getText().substring(0, 7);
                        tfValor.setText(s);
                    }
                }   
            }
        });
        
        pane.add(lbTitle, 0, 0, 4, 1);
        pane.add(lbCategoria, 0, 1);
        pane.add(cbCategoria, 1, 1);
        pane.add(lbSubCategoria, 0, 2);
        pane.add(cbSubCategoria, 1, 2);
        pane.add(lbDescricao, 0, 3);
        pane.add(tfDescricao, 1, 3);
        pane.add(lbValor, 0, 4);
        pane.add(tfValor, 1, 4);
        pane.add(lbData, 0, 5);
        pane.add(dpCalendario, 1, 5);
        pane.add(lbPagamento, 0, 6);
        pane.add(cbPagamento, 1, 6);
        pane.add(l1, 0, 7, 2, 1);

        lbTitle.setFont(Font.font("Arial", FontWeight.NORMAL, 23));
        lbDescricao.setFont(Font.font("Arial", FontWeight.NORMAL, 17));
        lbCategoria.setFont(Font.font("Arial", FontWeight.NORMAL, 17));
        lbData.setFont(Font.font("Arial", FontWeight.NORMAL, 17));
        lbPagamento.setFont(Font.font("Arial", FontWeight.NORMAL, 17));
        lbSubCategoria.setFont(Font.font("Arial", FontWeight.NORMAL, 17));
        lbValor.setFont(Font.font("Arial", FontWeight.NORMAL, 17));
        btnCadastrar.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
        btnCancelar.setFont(Font.font("Arial", FontWeight.NORMAL, 15));

        if (despesaCriada != null) {
            this.despesaCriada = despesaCriada;
            this.lbTitle.setText("Editar Despesa");
            btnCadastrar.setText("Salvar");
            this.tfDescricao.setText(despesaCriada.getDescricao());
            this.dpCalendario.setValue(despesaCriada.getDataOcorrencia());
            this.tfValor.setText(String.valueOf(despesaCriada.getValor()));
            this.cbPagamento.getSelectionModel().select(despesaCriada.getFormaPagamento() - 1);

            for (CategoriaConta categoria : this.cbCategoria.getItems()) {
                if (categoria.getCategoriaContaID() == despesaCriada.getSubCategoria().getCategoriaConta().getCategoriaContaID()) {
                    this.cbCategoria.getSelectionModel().select(categoria);
                }
            }

            for (SubCategoria subcategoria : this.cbSubCategoria.getItems()) {
                if (subcategoria.getSubCategoriaID() == despesaCriada.getSubCategoria().getSubCategoriaID()) {
                    this.cbSubCategoria.getSelectionModel().select(subcategoria);
                }
            }

            //this.calendario.setValue(LocalDate.parse( new SimpleDateFormat("yyyy-MM-dd").format(despesaCriada.getDataOcorrencia()) ));
        }

        return pane;
    }

    public Despesa getDespesaCriada() {
        return despesaCriada;
    }
}
