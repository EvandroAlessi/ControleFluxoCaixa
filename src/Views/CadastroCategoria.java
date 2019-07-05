/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import Controllers.CategoriaContaController;
import Controllers.SubCategoriaController;
import Models.CategoriaConta;
import Models.Receita;
import Models.SubCategoria;
import java.util.List;
import java.util.ListIterator;
import static javafx.application.Application.launch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author SpaceBR
 */
public class CadastroCategoria {
    //private String title;
    private final HBox l1;
    private final HBox l2;
    private final HBox l3;
    private final RadioButton despesa;
    private final RadioButton receita;
    private final VBox forms;
    private final Label lbTitle;
    private final Label lbDesc;
    private final Label lbTipo;
    private final Label lbCategoria;
    private final ComboBox combo;
    private final TextField txtDesc;
    private final Button cadastrar;
    private final Button novaCategoria;
    private SubCategoriaController controlSub;
    private CategoriaContaController controlCategoria;
    private List<CategoriaConta> categorias;
    private Stage dialog;
    //private T classe;
    private final Button cancelar;
    //private T classe;
    
    public CadastroCategoria() {
        l1 = new HBox();
        l2 = new HBox();
        l3 = new HBox();
        forms = new VBox();
        receita = new RadioButton("Receita");
        despesa = new RadioButton("Despesa");
        lbTitle = new Label("Cadastrar Subcategoria");
        lbCategoria = new Label("Categoria:");
        lbDesc = new Label("Nome:");
        lbTipo = new Label("Tipo:");
        txtDesc = new TextField();
        combo = new ComboBox();
        cadastrar = new Button("Cadastrar");
        cancelar = new Button("Cancelar");
        novaCategoria = new Button("+");
        controlCategoria = new CategoriaContaController();
        categorias = controlCategoria.getAll();
        
        cadastrar.setOnAction(e->{
            SubCategoria nSub = new SubCategoria();
            nSub.setSubCategoriaID(123123);
            nSub.setDescricao(txtDesc.getText());
            for(CategoriaConta conta: categorias){
                if(conta.getDescricao() == combo.getValue()){
                    nSub.setCategoriaConta(conta);
                    break;
                }
                else
                    nSub.setCategoriaConta(null);
            }
            System.out.println(nSub);
            controlSub.create(nSub);
        });
        
        cancelar.setOnAction(e->{
            dialog.close();
        });
    }

//    public FormFX(String title, T control) {
//        this.title = title;
//        this.control = control;
//    }
    

    public void start(Stage mainStage) throws Exception {
        dialog = new Stage();
        GridPane painel = criarFormulario();
        dialog.setTitle("Cadastro Categoria");
        dialog.initOwner(mainStage);
        dialog.initModality(Modality.APPLICATION_MODAL);
        // Create the registration form pane
        
        // Create a scene with the registration form gridPane as the root node.
        Scene scene = new Scene(painel, 400, 400);
        // Set the scene in primary stage
        dialog.setScene(scene);
        dialog.showAndWait();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    

    private GridPane criarFormulario(){
        GridPane pane = new GridPane();
        ListIterator<CategoriaConta> desc = categorias.listIterator();
        pane.setAlignment(Pos.TOP_CENTER);
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setPadding(new Insets(25, 25, 25, 25));

        ToggleGroup grupo = new ToggleGroup();
        receita.setToggleGroup(grupo);
        despesa.setToggleGroup(grupo);
        
        while(desc.hasNext()){
            combo.getItems().addAll(FXCollections.observableArrayList(desc.next().getDescricao()));
        }

        
        l1.getChildren().addAll(receita,despesa);
        l1.setSpacing(10);
        l1.setAlignment(Pos.CENTER_LEFT);
        l2.getChildren().addAll(cadastrar,cancelar);
        l2.setSpacing(10);
        l3.getChildren().addAll(combo,novaCategoria);
        l3.setSpacing(10);
        pane.add(lbTitle, 0, 0, 4, 1);
        pane.add(lbCategoria,0,1);
        pane.add(l3,1,1,2,1);
        pane.add(lbDesc, 0, 2);
        pane.add(txtDesc,1,2);
        pane.add(l2,1,3,2,1);


        lbTitle.setFont(Font.font("Arial",FontWeight.NORMAL,27));
        lbDesc.setFont(Font.font("Arial",FontWeight.NORMAL,17));
        lbTipo.setFont(Font.font("Arial",FontWeight.NORMAL,17));
        lbCategoria.setFont(Font.font("Arial",FontWeight.NORMAL,17));
        receita.setFont(Font.font("Arial",FontWeight.NORMAL,15));
        despesa.setFont(Font.font("Arial",FontWeight.NORMAL,15));
        cadastrar.setFont(Font.font("Arial",FontWeight.NORMAL,15));
        cancelar.setFont(Font.font("Arial",FontWeight.NORMAL,15));
        

        return pane;
    }
}

