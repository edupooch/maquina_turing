package sample;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
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
    public static final String CHAR_BRANCO = "¬";

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

        int qtEntradas = entradas.length; // quantidade de símbolos no alfabeto de entrada
        int qtAuxiliar = simbolosEspeciais.length; // quantidade de símbolos do alfabeto auxiliar
        int qtEstados = estados.length; //quantidade de estados

        grid.getChildren().clear(); //limpa a tabela que estava antes

        //Adicionar colunas no grid
        grid.addColumn(COLUMN_INDEX_ESTADOS);//coluna com os labels dos estados
        grid.addColumn(COLUMN_INDEX_INICIAL);//coluna com as transições do símbolo * de início


        for (int i = 0; i < (qtEntradas + qtAuxiliar); i++)
            grid.addColumn(i + 2); // colunas pro alfabeto (+2 pois a primeira coluna é vazia e a segunda é o simbolo *)
        grid.addColumn(qtEntradas + qtAuxiliar + 2);// coluna com as transições do simbolo ¬ (branco)

        //Adicionar linhas no grid
        grid.addRow(ROW_INDEX_SIMBOLOS);//linha para as labels do alfabeto

        for (int i = 0; i < qtEstados; i++)
            grid.addRow(i + 1); // linhas dos estados

        //Adicionando os labels das colunas na tabela (simbolos)
        grid.add(new Label(CHAR_INICIAL), COLUMN_INDEX_INICIAL, ROW_INDEX_SIMBOLOS); //label do char inicial (*)
        for (int i = 0; i < qtEntradas; i++) { // labels dos simbolos de entrada
            Label lbl = new Label(entradas[i]);
            grid.add(lbl, i + 2, ROW_INDEX_SIMBOLOS);
        }
        for (int i = 0; i < qtAuxiliar; i++) // labels dos simbolos do alfabeto auxiliar
            grid.add(new Label(simbolosEspeciais[i]), i + qtEntradas + 2, ROW_INDEX_SIMBOLOS);
        grid.add(new Label(CHAR_BRANCO), qtAuxiliar + qtEntradas + 2, ROW_INDEX_SIMBOLOS);// label do branco(¬)

        //Adicionando os labels das linhas na tabela (estados)
        for (int i = 0; i < qtEstados; i++) // labels dos simbolos de entrada
            grid.add(new Label(estados[i]), COLUMN_INDEX_ESTADOS, i + 1);

        // Adicionar os text fields
        for (int i = 1; i <= qtEstados; i++) {
            for (int j = 1; j < (qtEntradas + qtAuxiliar + 3); j++) {
                TextField tf = new TextField();
                tf.setId("tf" + i + j);
                tf.setMaxSize(80,80);
                grid.add(tf, j, i);
            }
        }

    }
}
