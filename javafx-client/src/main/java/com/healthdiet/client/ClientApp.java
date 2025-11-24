package com.healthdiet.client;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ClientApp extends Application {
    @Override
    public void start(Stage stage) {
        Label lbl = new Label("Health & Diet Analyzer - JavaFX Client");
        TextField food = new TextField(); food.setPromptText("Food name");
        TextField cal = new TextField(); cal.setPromptText("Calories");
        TextField prot = new TextField(); prot.setPromptText("Protein (g)");
        TextField iron = new TextField(); iron.setPromptText("Iron (mg)");
        Button add = new Button("Add (calls server)"); 
        add.setOnAction(e -> {
            Alert a = new Alert(Alert.AlertType.INFORMATION, "This is a demo. Run the server and use HTTP client to interact.");
            a.showAndWait();
        });
        VBox root = new VBox(8, lbl, food, cal, prot, iron, add);
        Scene scene = new Scene(root, 400, 300);
        stage.setScene(scene);
        stage.setTitle("Health Diet Client - GAURAV BHARDWAJ - 24BSA10187");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
