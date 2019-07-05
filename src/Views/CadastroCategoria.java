/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import static javafx.application.Application.launch;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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
    private String title;
    private HBox l1,l2,l3;
    private RadioButton despesa,receita;
    private VBox forms;
    private Label lbTitle,lbDesc, lbTipo;
    private ComboBox combo;
    private TextField txtDesc;
    private Button cadastrar,cancelar;
    //private T classe;
    
    public CadastroCategoria() {
        l1 = new HBox();
        l2 = new HBox();
        l3 = new HBox();
        forms = new VBox();
        receita = new RadioButton("Receita");
        despesa = new RadioButton("Despesa");
        lbTitle = new Label("Cadastrar Categoria");
        lbDesc = new Label("Nome:");
        lbTipo = new Label("Tipo:");
        txtDesc = new TextField();
        combo = new ComboBox();
        cadastrar = new Button("Cadastrar");
        cancelar = new Button("Cancelar");
    }

//    public FormFX(String title, T control) {
//        this.title = title;
//        this.control = control;
//    }
    

    public void start(Stage mainStage) throws Exception {
        Stage dialog = new Stage();
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
        pane.setAlignment(Pos.TOP_CENTER);
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setPadding(new Insets(25, 25, 25, 25));
        ToggleGroup grupo = new ToggleGroup();
        receita.setToggleGroup(grupo);
        despesa.setToggleGroup(grupo);
        l1.getChildren().addAll(receita,despesa);
        l1.setSpacing(10);
        l1.setAlignment(Pos.CENTER_LEFT);
        pane.add(lbTitle, 0, 0, 4, 1);
        pane.add(lbDesc, 0, 1);
        pane.add(txtDesc, 1, 1);
        pane.add(lbTipo,0,2);
        pane.add(l1,1,2,2,1);
        pane.add(cadastrar,0,3);
        pane.add(cancelar,1,3);
        
        lbTitle.setFont(Font.font("Arial",FontWeight.NORMAL,35));
        lbDesc.setFont(Font.font("Arial",FontWeight.NORMAL,25));
        lbTipo.setFont(Font.font("Arial",FontWeight.NORMAL,25));
        receita.setFont(Font.font("Arial",FontWeight.NORMAL,15));
        despesa.setFont(Font.font("Arial",FontWeight.NORMAL,15));
        cadastrar.setFont(Font.font("Arial",FontWeight.NORMAL,15));
        cancelar.setFont(Font.font("Arial",FontWeight.NORMAL,15));
        

        return pane;
    }
}

