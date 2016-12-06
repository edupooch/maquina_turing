package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class Controller {
    private static final String SEPARADOR = ",";

    @FXML
    private TextField tfAlfabetoEnt;
    @FXML
    private TextField tfEstados;
    @FXML
    private TextField tfEstadosFinais;
    @FXML
    private TextField tfSimbolosEsp;
    @FXML
    private GridPane grid;

    public void gerarTabela(){
        String[] entradas = tfAlfabetoEnt.getText().split(SEPARADOR);
        String[] estados = tfEstados.getText().split(SEPARADOR);
        String[] estadosFinais = tfEstadosFinais.getText().split(SEPARADOR);
        String[] simbolosEspeciais = tfSimbolosEsp.getText().split(SEPARADOR);



    }
}
