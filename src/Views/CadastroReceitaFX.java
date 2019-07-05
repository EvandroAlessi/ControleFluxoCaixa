/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import Controllers.CategoriaContaController;
import Controllers.ReceitaController;
import Controllers.SubCategoriaController;
import Models.CategoriaConta;
import Models.Receita;
import Models.SubCategoria;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javafx.application.Application.launch;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author SpaceBR
 */
public class CadastroReceitaFX {
    private final Label lbData,lbSubCategoria,lbDescricao,lbValor,lbPagamento,lbTitle;
    private final TextField txtDesc,txtValor;
    private final DatePicker calendario;
    private final Button cadastrar,cancelar;
    private SubCategoriaController controlSubCategoria;
    private List<SubCategoria> subCategorias;
    private ComboBox comboSub,comboPgt;
    private ReceitaController controlReceita;
    
    public CadastroReceitaFX() {
        lbDescricao = new Label("Descrição:");
        txtDesc = new TextField();
        txtValor = new TextField();
        lbTitle = new Label("Cadastrar Receita");
        lbData = new Label("Ocorrência:");
        lbSubCategoria = new Label("Subcategoria:");
        lbValor = new Label("Valor:");
        lbPagamento = new Label("Pagamento:");
        cadastrar = new Button("Cadastrar");
        cancelar = new Button("Cancelar");
        calendario = new DatePicker();
        controlSubCategoria = new SubCategoriaController();
        controlReceita = new ReceitaController();
        subCategorias = controlSubCategoria.getAll();
        comboSub = new ComboBox();
        comboPgt = new ComboBox();
        cadastrar.setOnAction(e ->{
            // Date dataOcorrencia, String descricao, double valor, int movimentacaoID, int formaPagamento
            Receita nReceita = new Receita();
            nReceita.setDescricao(txtDesc.getText());
            nReceita.setDataOcorrencia(java.sql.Date.valueOf(calendario.getValue()));
            nReceita.setValor(Double.parseDouble(txtValor.getText()));
            nReceita.setFormaPagamento(switchPag((String) comboPgt.getValue()));
            for (SubCategoria sub: subCategorias) {
                if(sub.getDescricao()==comboSub.getValue()){
                    System.out.println("combo == "+ comboSub.getValue());
                    nReceita.setSubcategoria(sub);
                    break;
                }
                else
                    nReceita.setSubcategoria(null);
            }
            System.out.println(nReceita);
            System.out.println("subcat id: "+nReceita.getSubcategoria().getSubCategoriaID());
            controlReceita.create(nReceita);
        });
    }
    
    public int switchPag(String pgt){
        switch(pgt){
            case "Crédito":
                return 1;
            case "Dinheiro":
                return 2;
            case "Boleto":
                return 3;
            case "Depósito":
                return 4;
            case "Convênio":
                return 5;
        }
        return 0;
    }

    public void start(Stage mainStage) throws Exception {
        Stage dialog = new Stage();
        GridPane painel = criarFormulario();
        dialog.setTitle("Cadastro Receita");
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
        HBox l1 = new HBox();
        List<SubCategoria> receitas = new ArrayList<>();
        for (SubCategoria receita: subCategorias) {
            if(receita.getCategoriaConta().isPositiva())
                receitas.add(receita);
        }
        ListIterator<SubCategoria> subIte = receitas.listIterator();
        pane.setAlignment(Pos.TOP_CENTER);
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setPadding(new Insets(25, 25, 25, 25));

        comboPgt.getItems().addAll("Crédito","Dinheiro","Boleto","Depósito","Convênio");
        while(subIte.hasNext()){
            comboSub.getItems().addAll(FXCollections.observableArrayList(subIte.next().getDescricao()));
        }
        
        l1.getChildren().addAll(cadastrar,cancelar);
        l1.setSpacing(10);
       
        pane.add(lbTitle, 0, 0, 4, 1);
        pane.add(lbSubCategoria,0,1);
        pane.add(comboSub,1,1);
        pane.add(lbDescricao,0,2);
        pane.add(txtDesc,1,2);
        pane.add(lbValor,0,3);
        pane.add(txtValor,1,3);
        pane.add(lbData,0,4);
        pane.add(calendario,1,4);
        pane.add(lbPagamento,0,5);
        pane.add(comboPgt,1,5);
        pane.add(l1,1,6,2,1);


        lbTitle.setFont(Font.font("Arial",FontWeight.NORMAL,27));
        lbDescricao.setFont(Font.font("Arial",FontWeight.NORMAL,17));
        lbData.setFont(Font.font("Arial",FontWeight.NORMAL,17));
        lbPagamento.setFont(Font.font("Arial",FontWeight.NORMAL,17));
        lbSubCategoria.setFont(Font.font("Arial",FontWeight.NORMAL,17));
        lbValor.setFont(Font.font("Arial",FontWeight.NORMAL,17));
        cadastrar.setFont(Font.font("Arial",FontWeight.NORMAL,15));
        cancelar.setFont(Font.font("Arial",FontWeight.NORMAL,15));
        
        return pane;
    }
    
    
}
