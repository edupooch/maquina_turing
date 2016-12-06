package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.util.Arrays;

public class Controller {
    private static final String SEPARADOR = ",";
    public static final int COLUMN_INDEX_ESTADOS = 0;
    public static final int COLUMN_INDEX_INICIAL = 1;
    public static final int ROW_INDEX_SIMBOLOS = 0;
    public static final String CHAR_INICIAL = "*";

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

    public void gerarTabela() {
        String[] entradas = tfAlfabetoEnt.getText().split(SEPARADOR);
        System.out.println(Arrays.toString(entradas));
        String[] estados = tfEstados.getText().split(SEPARADOR);
        String[] estadosFinais = tfEstadosFinais.getText().split(SEPARADOR);
        String[] simbolosEspeciais = tfSimbolosEsp.getText().split(SEPARADOR);

        grid.getChildren().clear();


        //Adicionar colunas no grid
        grid.addColumn(COLUMN_INDEX_ESTADOS);//coluna com os labels dos estados
        grid.addColumn(COLUMN_INDEX_INICIAL);//coluna com as transições do símbolo * de início
        for (int i = 0; i < (entradas.length + simbolosEspeciais.length); i++)
            grid.addColumn(i + 2); // colunas pro alfabeto
        grid.addColumn(entradas.length + simbolosEspeciais.length + 2);// coluna com as transições do simbolo ¬ (branco)

        //Adicionar linhas no grid
        grid.addRow(ROW_INDEX_SIMBOLOS);//linha para as labels do alfabeto
        for (int i = 0; i < estados.length; i++)
            grid.addRow(i + 1); // linhas dos estados

        //Adicionando elementos na tabela
        grid.add(new Label(CHAR_INICIAL), ROW_INDEX_SIMBOLOS, COLUMN_INDEX_INICIAL);

    }
}
