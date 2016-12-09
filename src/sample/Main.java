package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Trabalho Teoria da Computação: Eduardo, Mayandre, Renata e Rodrigo.
 * Informática Biomédica UFCSPA
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Simulador de Máquina de Turing");
        primaryStage.setScene(new Scene(root, 1000, 800));
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
