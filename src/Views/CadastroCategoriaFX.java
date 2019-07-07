/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import Controllers.CategoriaContaController;
import Controllers.SubCategoriaController;
import Models.CategoriaConta;
import Models.SubCategoria;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
 *
 * @author SpaceBR
 */
public class CadastroCategoriaFX {

    private final Label lbSubCategoria, lbDescricao, lbTitle;
    private final TextField tfDescricao;
    private final Button btnCadastrar, btnCancelar;
    private final SubCategoriaController subCategoriaController;
    private final List<SubCategoria> subCategorias;
    private ComboBox<SubCategoria> cbSubCategoria;
    private CategoriaContaController categoriaContaController;
    private Stage dialog;
    private CategoriaConta categoriaContaCriada;

    public CadastroCategoriaFX() {
        categoriaContaCriada = null;
        lbDescricao = new Label("Descrição:");
        tfDescricao = new TextField();
        lbTitle = new Label("Nova Categoria");
        lbSubCategoria = new Label("Subcategoria:");
        btnCadastrar = new Button("Cadastrar");
        btnCancelar = new Button("Cancelar");
        subCategoriaController = new SubCategoriaController();
        categoriaContaController = new CategoriaContaController();
        subCategorias = subCategoriaController.getAll();
        cbSubCategoria = new ComboBox();

        btnCadastrar.setOnAction(e -> {
            if (categoriaContaCriada == null) {
                // Date dataOcorrencia, String descricao, double valor, int movimentacaoID, int formaPagamento
                CategoriaConta nCategoriaConta = new CategoriaConta();

                nCategoriaConta.setDescricao(tfDescricao.getText());
                categoriaContaController.create(nCategoriaConta);
                categoriaContaCriada = nCategoriaConta;
            } else {
                categoriaContaCriada.setDescricao(tfDescricao.getText());
                categoriaContaController.update(categoriaContaCriada);
            }

            dialog.close();
        });
        btnCancelar.setOnAction(e -> {
            dialog.close();
        });
    }

    public void start(Stage mainStage, CategoriaConta categoriaContaCriada) throws Exception {
        dialog = new Stage();
        GridPane painel = criarFormulario(categoriaContaCriada);
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

    private GridPane criarFormulario(CategoriaConta categoriaContaCriada) {
        GridPane pane = new GridPane();
        HBox l1 = new HBox();
        List<SubCategoria> subCatCategoriaConta = new ArrayList<>();
        for (SubCategoria categoriaConta : subCategorias) {
            if (categoriaConta.getCategoriaConta().isPositiva()) {
                subCatCategoriaConta.add(categoriaConta);
            }
        }

        pane.setAlignment(Pos.TOP_CENTER);
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setPadding(new Insets(25, 10, 25, 25));

        ColumnConstraints c1 = new ColumnConstraints();
        //c1.setHgrow(Priority.ALWAYS);
        c1.setHalignment(HPos.CENTER);

        RowConstraints r1 = new RowConstraints();
        //r1.setVgrow(Priority.ALWAYS);
        r1.setValignment(VPos.CENTER);

        pane.getColumnConstraints().add(c1);
        pane.getRowConstraints().add(r1);

        l1.getChildren().addAll(btnCadastrar, btnCancelar);
        l1.setSpacing(10);
        l1.setPadding(new Insets(35, 0, -10, 0));
        l1.setAlignment(Pos.CENTER);

        lbTitle.setPadding(new Insets(0, 0, 25, 0));
        lbTitle.setAlignment(Pos.CENTER);

        cbSubCategoria.setPrefWidth(200);
        tfDescricao.setPrefWidth(200);

        pane.add(lbTitle, 0, 0, 4, 1);
        pane.add(lbSubCategoria, 0, 2);
        pane.add(cbSubCategoria, 1, 2);
        pane.add(lbDescricao, 0, 3);
        pane.add(tfDescricao, 1, 3);
        pane.add(l1, 0, 7, 2, 1);

        lbTitle.setFont(Font.font("Arial", FontWeight.NORMAL, 23));
        lbDescricao.setFont(Font.font("Arial", FontWeight.NORMAL, 17));
        lbSubCategoria.setFont(Font.font("Arial", FontWeight.NORMAL, 17));
        btnCadastrar.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
        btnCancelar.setFont(Font.font("Arial", FontWeight.NORMAL, 15));

        if (categoriaContaCriada != null) {
            this.categoriaContaCriada = categoriaContaCriada;
            this.lbTitle.setText("Editar Categoria");
            btnCadastrar.setText("Salvar");
            this.tfDescricao.setText(categoriaContaCriada.getDescricao());

            for (SubCategoria subcategoria : this.cbSubCategoria.getItems()) {
                //if (subcategoria.getSubCategoriaID() == categoriaContaCriada.getSubcategoria().getSubCategoriaID()) {
                this.cbSubCategoria.getSelectionModel().select(subcategoria);
                //}
            }

            //this.calendario.setValue(LocalDate.parse( new SimpleDateFormat("yyyy-MM-dd").format(categoriaContaCriada.getDataOcorrencia()) ));
        }

        return pane;
    }

    public CategoriaConta getCategoriaContaCriada() {
        return categoriaContaCriada;
    }
}
