package Views;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class FormFX extends Application {
    private String title;
    //private T classe;
    
    public FormFX(String title) {
        this.title = title;
    }

//    public FormFX(String title, T control) {
//        this.title = title;
//        this.control = control;
//    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle(title);

        // Create the registration form pane
        GridPane gridPane = createRegistrationFormPane();
        // Create a scene with the registration form gridPane as the root node.
        Scene scene = new Scene(gridPane, 800, 500);
        // Set the scene in primary stage
        primaryStage.setScene(scene);

        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
    private GridPane createRegistrationFormPane() {
        //String[] columns = control.getAllMetaData();
        GridPane gridPane = new GridPane();
        VBox vbLabel = new VBox();
        VBox vbTextField = new VBox();
        Label lbTitle = new Label(title);
        //Label[] lbList = new Label[columns.count()];
        //TextField[] tfList = new TextField[columns.count()];     

        // Position the pane at the center of the screen, both vertically and horizontally
        //gridPane.setAlignment(Pos.CENTER);

        // Set a padding of 20px on each side
        gridPane.setPadding(new Insets(40, 40, 40, 40));

        // Set the horizontal gap between columns
        gridPane.setHgap(10);

        // Set the vertical gap between rows
        gridPane.setVgap(10);

        lbTitle.setFont(Font.font("Arial", 18));
        lbTitle.setStyle("-fx-font-weight: bold");
        // Add Column Constraints
        //Button btn = new Button("btn");
        vbLabel.setAlignment(Pos.TOP_LEFT);
        vbTextField.setAlignment(Pos.TOP_RIGHT);
        
//        for(int i = 0; i < columns.count(); i++){
//            lbList[i].setText(columns[i]);
//            tfList[i] = new TextField();
//        }

        
        //vbLabel.getChildren().addAll(lbList);
        //vbTextField.getChildren().addAll(tfList);
        // columnOneConstraints will be applied to all the nodes placed in column one.
        ColumnConstraints c1 = new ColumnConstraints(40, 100, Double.MAX_VALUE);
        c1.setHalignment(HPos.CENTER);
        // columnTwoConstraints will be applied to all the nodes placed in column two.
        ColumnConstraints c2 = new ColumnConstraints(40,200, Double.MAX_VALUE);
        c2.setHgrow(Priority.ALWAYS);

        RowConstraints r1 = new RowConstraints();
        r1.setPrefHeight(40);
        //r1.setVgrow(Priority.ALWAYS);
        r1.setValignment(VPos.CENTER);
        
        
        gridPane.getColumnConstraints().addAll(c1, c2);
        gridPane.getRowConstraints().addAll(r1);
        
        gridPane.add(vbLabel, 0, 1);
        gridPane.add(vbTextField, 1, 1);
        gridPane.add(lbTitle, 0, 0, 2, 1);
        
        return gridPane;
    }
}