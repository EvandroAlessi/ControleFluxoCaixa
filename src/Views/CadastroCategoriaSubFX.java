/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import Controllers.CategoriaContaController;
import Controllers.SubCategoriaController;
import CrossCutting.Mensagem;
import Models.CategoriaConta;
import Models.SubCategoria;
import java.util.List;
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
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * View com formulário de cadastro para Categorias.
 * Diretamente ligada ao Stage provido pela CategoriaSubFX
 * Utiliza o controlador de CategoriaConta e SubCategoria
 * @author Evandro Alessi
 * @author Eric Ueta
 * @see CategoriaConta
 * @see SubCategoria
 * @see CategoriaContaController
 * @see SubCategoriaController
 * @see CategoriaSubFX
 */
public class CadastroCategoriaSubFX {

    private final Label lbCategoria, lbDescricao, lbTitle, lbNewCategoria;
    private final TextField tfDescricao, tfNewCategoria;
    private final Button btnCadastrar, btnCancelar, btnAdd, btnSalvarNewCategoria;
    private final SubCategoriaController subCategoriaController;
    private CategoriaContaController categoriaContaController;
    private List<CategoriaConta> categorias;
    private ComboBox<CategoriaConta> cbCategoria;
    private RadioButton rbReceita;
    private final RadioButton rbDespesa;
    final ToggleGroup tGroup;

    private Stage dialog;
    private SubCategoria subCategoriaCriada;

    public CadastroCategoriaSubFX() {
        subCategoriaCriada = null;
        lbDescricao = new Label("Descrição:");
        tfDescricao = new TextField();
        tfNewCategoria = new TextField();
        lbTitle = new Label("Nova Categoria");
        lbCategoria = new Label("Tipo Receita:");
        lbNewCategoria = new Label("Novo Tipo:");
        btnCadastrar = new Button("Cadastrar");
        btnSalvarNewCategoria = new Button("Salvar");
        btnAdd = new Button("+");
        btnCancelar = new Button("Cancelar");
        subCategoriaController = new SubCategoriaController();
        categoriaContaController = new CategoriaContaController();
        categorias = categoriaContaController.getAll(true);

        cbCategoria = new ComboBox();
        rbReceita = new RadioButton("Receita");
        rbDespesa = new RadioButton("Despesa");
        tGroup = new ToggleGroup();

        rbReceita.setToggleGroup(tGroup);
        rbDespesa.setToggleGroup(tGroup);

        tGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> ov,
                    Toggle old_toggle, Toggle new_toggle) {
                if (tGroup.getSelectedToggle().equals(rbReceita)) {
                    lbCategoria.setText("Tipo Receita:");
                    categorias = categoriaContaController.getAll(true);
                } else {
                    lbCategoria.setText("Tipo Despesa:");
                    categorias = categoriaContaController.getAll(false);
                }
                cbCategoria.setItems(FXCollections.observableArrayList(categorias));
                cbCategoria.getSelectionModel().selectFirst();
            }
        });

        btnCadastrar.setOnAction(e -> {
            if (tfDescricao.getText().trim().length() != 0) {
                if (subCategoriaCriada == null) {
                    SubCategoria nSubCategoria = new SubCategoria();
                    
                    nSubCategoria.setDescricao(tfDescricao.getText());
                    nSubCategoria.setCategoriaConta(cbCategoria.getSelectionModel().getSelectedItem());
                    subCategoriaCriada = subCategoriaController.create(nSubCategoria);
                } else {
                    subCategoriaCriada.setDescricao(tfDescricao.getText());
                    subCategoriaCriada.setCategoriaConta(cbCategoria.getSelectionModel().getSelectedItem());
                    subCategoriaController.update(subCategoriaCriada);
                }
                if (subCategoriaCriada != null) {
                    dialog.close();
                } else {
                    Mensagem.aviso("Não foi possivel criar a categoria.");
                }
            } else {
                tfDescricao.setStyle("-fx-text-box-border: red ;"
                        + " -fx-focus-color: red ;");
                Mensagem.informacao("Você deve dar uma descricao para a categoria.");
            }
        });

        btnSalvarNewCategoria.setOnAction((event) -> {
            if (tfNewCategoria.getText().trim().length() != 0) {
                CategoriaConta cat = null;
                if (tGroup.getSelectedToggle().equals(rbReceita)) {
                    cat = categoriaContaController.create(new CategoriaConta(tfNewCategoria.getText(), true));
                } else {
                    cat = categoriaContaController.create(new CategoriaConta(tfNewCategoria.getText(), false));
                }

                if (cat != null) {
                    cbCategoria.getItems().add(cat);
                    cbCategoria.getSelectionModel().select(cat);
                    tfNewCategoria.clear();
                    lbNewCategoria.setVisible(false);
                    tfNewCategoria.setVisible(false);
                    btnSalvarNewCategoria.setVisible(false);
                    btnAdd.setText("+");
                    tfNewCategoria.setStyle("-fx-text-box-border: #ccc ;"
                        + " -fx-focus-color: #ccc;");
                }
            } else {
                tfNewCategoria.setStyle("-fx-text-box-border: red ;"
                        + " -fx-focus-color: red ;");
                Mensagem.informacao("Você deve dar um nome para o novo tipo.");
            }
        });

        btnCancelar.setOnAction(e -> {
            dialog.close();
        });
    }

    /**
     *
     * @param mainStage
     * @param categoriaContaCriada
     * @throws Exception
     */
    public void start(Stage mainStage, SubCategoria subCategoria) throws Exception {
        dialog = new Stage();
        GridPane painel = criarFormulario(subCategoria);
        dialog.initOwner(mainStage);
        dialog.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(painel, 430, 380);

        btnAdd.setOnAction((event) -> {
            boolean aux = !lbNewCategoria.isVisible();
            btnAdd.setText(aux ? "-" : "+");
            lbNewCategoria.setVisible(aux);
            tfNewCategoria.setVisible(aux);
            btnSalvarNewCategoria.setVisible(aux);
        });

        dialog.setResizable(false);
        dialog.setMaxHeight(380);
        dialog.setMaxWidth(430);
        dialog.setMinHeight(380);
        dialog.setMinWidth(430);

        dialog.setScene(scene);
        dialog.showAndWait();
    }

    private GridPane criarFormulario(SubCategoria subCategoria) {
        GridPane pane = new GridPane();
        HBox hbButtons = new HBox();
        HBox hbRadios = new HBox(30, rbReceita, rbDespesa);
        
        hbRadios.setAlignment(Pos.CENTER);
        
        cbCategoria.setItems(FXCollections.observableArrayList(categorias));
        cbCategoria.getSelectionModel().selectFirst();

        pane.setAlignment(Pos.TOP_CENTER);
        pane.setHgap(10);
        pane.setVgap(20);
        pane.setPadding(new Insets(25, 10, 25, 25));

        ColumnConstraints c1 = new ColumnConstraints();
        c1.setHalignment(HPos.CENTER);

        RowConstraints r1 = new RowConstraints();
        r1.setValignment(VPos.CENTER);

        pane.getColumnConstraints().add(c1);
        pane.getRowConstraints().add(r1);

        hbButtons.getChildren().addAll(btnCadastrar, btnCancelar);
        hbButtons.setSpacing(10);
        hbButtons.setPadding(new Insets(20, 0, -10, 0));
        hbButtons.setAlignment(Pos.CENTER);

        lbTitle.setPadding(new Insets(0, 0, 25, 0));
        lbTitle.setAlignment(Pos.CENTER);

        cbCategoria.setPrefWidth(200);
        tfDescricao.setPrefWidth(200);
        tfNewCategoria.setPrefWidth(200);

        pane.add(lbTitle, 0, 0, 4, 1);
        
        pane.add(hbRadios, 0, 1, 3, 1);
        
        pane.add(tfNewCategoria, 1, 3, 2, 1);
        pane.add(btnSalvarNewCategoria, 2, 3);
        pane.add(lbNewCategoria, 0, 3);
        
        pane.add(lbCategoria, 0, 2);
        pane.add(cbCategoria, 1, 2);
        pane.add(btnAdd, 2, 2);
        
        pane.add(lbDescricao, 0, 4);
        pane.add(tfDescricao, 1, 4, 2, 1);
        
        pane.add(hbButtons, 0, 5, 4, 1);

        lbNewCategoria.setVisible(false);
        tfNewCategoria.setVisible(false);
        btnSalvarNewCategoria.setVisible(false);

        lbTitle.setFont(Font.font("Arial", FontWeight.NORMAL, 23));
        lbDescricao.setFont(Font.font("Arial", FontWeight.NORMAL, 17));
        lbCategoria.setFont(Font.font("Arial", FontWeight.NORMAL, 17));
        lbNewCategoria.setFont(Font.font("Arial", FontWeight.NORMAL, 17));
        btnCadastrar.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
        btnCancelar.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
        rbDespesa.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        rbReceita.setFont(Font.font("Arial", FontWeight.NORMAL, 14));

        if (subCategoria != null) {
            this.subCategoriaCriada = subCategoria;
            this.lbTitle.setText("Editar Categoria");
            btnCadastrar.setText("Salvar");
            this.tfDescricao.setText(subCategoriaCriada.getDescricao());
            if (subCategoriaCriada != null) {
                if (subCategoriaCriada.getCategoriaConta().isPositiva()) {
                    rbReceita.setSelected(true);
                } else {
                    rbDespesa.setSelected(true);
                }
            } else {
                rbReceita.setSelected(true);
            }
            this.cbCategoria.getSelectionModel().select(subCategoriaCriada.getCategoriaConta());
        } else {
            rbReceita.setSelected(true);
        }
        return pane;
    }

    public SubCategoria getSubCategoriaCriada() {
        return subCategoriaCriada;
    }
}
