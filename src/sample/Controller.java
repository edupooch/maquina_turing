package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

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
    private TextField tfFrase;
    @FXML
    private GridPane grid;
    private String[] simbolos;
    private String[] estados;

    public void gerarTabela() {
        //Gera array de strings com os componentes da tabela, removendo os parenteses e separando os índices por vírgula
        String[] entradas = tfAlfabetoEnt.getText().replace("(", "").replace(")", "").split(SEPARADOR);
        estados = tfEstados.getText().replace("(", "").replace(")", "").split(SEPARADOR);
        //TODO: INCLUIR ASTERISCO NO LABEL DOS ESTADOS FINAIS
        String[] estadosFinais = tfEstadosFinais.getText().replace("(", "").replace(")", "").replace("q", "").split(SEPARADOR);
        String[] simbolosEspeciais = tfSimbolosEsp.getText().replace("(", "").replace(")", "").split(SEPARADOR);

        int qtEntradas = entradas.length; // quantidade de simbolos no alfabeto de entrada
        int qtAuxiliar = simbolosEspeciais.length; // quantidade de simbolos do alfabeto auxiliar
        int qtEstados = estados.length; //quantidade de estados

        /*
         * Criando array com todos os símbolos reconhecidos pela linguagem
         */
        simbolos = new String[qtEntradas + qtAuxiliar + 2]; //+2 pois tem o * e o ¬
        simbolos[0] = CHAR_INICIAL;
        System.arraycopy(entradas, 0, simbolos, 1, qtEntradas);
        System.arraycopy(simbolosEspeciais, 0, simbolos, qtEntradas + 1, qtAuxiliar);
        simbolos[qtEntradas + qtAuxiliar + 1] = CHAR_BRANCO;

        /*
         * Ciando a Interface Gráfica
         */
        grid.getChildren().clear(); //limpa a tabela que estava antes

        //Adicionar colunas no grid
        grid.addColumn(COLUMN_INDEX_ESTADOS);//coluna com os labels dos estados
        for (int i = 0; i < simbolos.length; i++)
            grid.addColumn(i+1); // colunas pro alfabeto (+1 pois a primeira coluna é vazia)

        //Adicionar linhas no grid
        grid.addRow(ROW_INDEX_SIMBOLOS); //linha para as labels do alfabeto
        for (int i = 0; i < qtEstados; i++)
            grid.addRow(i + 1); // linhas dos estados (+1 pois a primeira linha é dos labels)


        //Adicionando os labels das colunas na tabela (simbolos)
        for (int i = 0; i < simbolos.length; i++) { // labels dos simbolos de entrada
            grid.add(new Label(simbolos[i]), i+1, ROW_INDEX_SIMBOLOS);
        }

        //Adicionando os labels das linhas na tabela (estados)
        for (int i = 0; i < qtEstados; i++) // labels dos simbolos de entrada
            grid.add(new Label(estados[i]), COLUMN_INDEX_ESTADOS, i + 1);

        // Adicionar os text fields
        for (int i = 0; i < qtEstados; i++) {
            for (int j = 0; j < simbolos.length; j++) {
                TextField tf = new TextField();
                tf.setId(estados[i] + "/" + simbolos[j]); //ids dos tf ficarão como "q0/a" (estado q0 com simbolo a)
                tf.setMaxSize(80, 80);
                grid.add(tf, j+1, i+1);
            }
        }
    }

    public void reconhecer() {
        gerarMatriz();


        char[] fita = (CHAR_INICIAL + tfFrase.getText() + CHAR_BRANCO).toCharArray();
        for (int i = 0; i < fita.length; i++) {
            char simbolo = fita[i];

        }

    }

    private void gerarMatriz() {
        String[][] q = new String[estados.length][simbolos.length];

    }
}
