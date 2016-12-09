package sample;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.Arrays;

public class Controller {
    private static final String SEPARADOR = ",";
    public static final int COLUMN_INDEX_ESTADOS = 0;
    public static final int COLUMN_INDEX_INICIAL = 1;
    public static final int ROW_INDEX_SIMBOLOS = 0;
    public static final String CHAR_INICIAL = "*";
    public static final char CHAR_BRANCO = '¬';

    /**
     * Ìndices do String[] Nó, no estilo (qo,A,D)
     */
    private static final int PROXIMO_ESTADO = 0;
    private static final int SIMBOLO_PARA_COLOCAR = 1;
    private static final int MOVIMENTO_FITA = 2;

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
    @FXML
    private VBox layoutSentenca;
    @FXML
    private Label lbAceita;

    private String[] simbolos;
    private String[] estados;
    private int[] finais;

    public void gerarTabela() {
        //Gera array de strings com os componentes da tabela, removendo os parenteses e separando os índices por vírgula
        String[] entradas;
        if (!tfAlfabetoEnt.getText().isEmpty()) {
            entradas = tfAlfabetoEnt.getText().replace("(", "").replace(")", "").split(SEPARADOR);
        } else {
            entradas = new String[0];
        }
        estados = tfEstados.getText().replace("(", "").replace(")", "").split(SEPARADOR);
        String[] simbolosEspeciais;
        if (!tfSimbolosEsp.getText().isEmpty()) {
            simbolosEspeciais = tfSimbolosEsp.getText().replace("(", "").replace(")", "").split(SEPARADOR);
        } else {
            simbolosEspeciais = new String[0];
        }

        String[] strFinais = tfEstadosFinais.getText().replace("(", "")
                .replace(")", "").replace("q", "").split(SEPARADOR);
        finais = new int[strFinais.length];
        for (int i = 0; i < strFinais.length; i++) {
            finais[i] = Integer.parseInt(strFinais[i]);
        }

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
        simbolos[qtEntradas + qtAuxiliar + 1] = String.valueOf(CHAR_BRANCO);

        /*
         * Ciando a Interface Gráfica
         */
        grid.getChildren().clear(); //limpa a tabela que estava antes
        lbAceita.setText(""); // limpa a mensagem de reconhecimento


        //Adicionar colunas no grid
        grid.addColumn(COLUMN_INDEX_ESTADOS);//coluna com os labels dos estados
        for (int i = 0; i < simbolos.length; i++)
            grid.addColumn(i + 1); // colunas pro alfabeto (+1 pois a primeira coluna é vazia)

        //Adicionar linhas no grid
        grid.addRow(ROW_INDEX_SIMBOLOS); //linha para as labels do alfabeto
        for (int i = 0; i < qtEstados; i++)
            grid.addRow(i + 1); // linhas dos estados (+1 pois a primeira linha é dos labels)


        //Adicionando os labels das colunas na tabela (simbolos)
        for (int i = 0; i < simbolos.length; i++) { // labels dos simbolos de entrada
            grid.add(new Label(simbolos[i]), i + 1, ROW_INDEX_SIMBOLOS);
        }

        //Adicionando os labels das linhas na tabela (estados)
        for (int i = 0; i < qtEstados; i++) { // labels dos simbolos de entrada
            String textoLabel;
            if (isFinal(i)) textoLabel = "✳" + estados[i];
            else textoLabel = estados[i];
            Label label = new Label(textoLabel);
            label.setPadding(new Insets(0, 10, 0, 0));
            grid.add(label, COLUMN_INDEX_ESTADOS, i + 1);
        }

        // Adicionar os text fields
        for (int i = 0; i < qtEstados; i++) {
            for (int j = 0; j < simbolos.length; j++) {
                TextField tf = new TextField();
                tf.setId("tf" + i + "/" + j); //ids dos fields ficarão como "tf0/1" (estado q0 com simbolo[1])
                tf.setMaxSize(80, 80);
                grid.add(tf, j + 1, i + 1);
            }
        }
        layoutSentenca.setDisable(false);//habilita a inserção de sentenças

    }

    public void reconhecer() {
        boolean aceita = false;
        String[][] q = gerarMatriz();

        char[] fita = (CHAR_INICIAL + tfFrase.getText() + CHAR_BRANCO + CHAR_BRANCO + CHAR_BRANCO + CHAR_BRANCO).toCharArray();

        int loops = 0;

        int estadoAtual = 0; //pointer do Estado (começa em qo)
        int pointFita = 0; //pointer da Fita (começa em *)
        while (!aceita) {
            System.out.println("Símbolo atual: " + fita[pointFita]);

            // pega o char da vez e acha o índice desse char na tabela
            int iSimbolo = achaIndiceSimbolo(fita[pointFita]);


            if (iSimbolo == -1) { //Caso o char lido não esteja nos símbolos aceitos, o loop é quebrado
                aceita = false;
                break;
            }

            if (fita[pointFita] == CHAR_BRANCO && isFinal(estadoAtual)) {
                aceita = true;
                break;
            }

            String[] no = q[estadoAtual][iSimbolo].replace("(", "").replace(")", "").split(",");
            System.out.println(Arrays.toString(no));

            if (no.length != 3) {
                aceita = isFinal(estadoAtual);
                break;
            }

            estadoAtual = Integer.parseInt(no[PROXIMO_ESTADO].replace("q", ""));
            fita[pointFita] = no[SIMBOLO_PARA_COLOCAR].charAt(0);

            if (no[MOVIMENTO_FITA].toUpperCase().equals("D") || no[MOVIMENTO_FITA].toUpperCase().equals("R")) {
                pointFita++;
                if (pointFita >= fita.length) pointFita = (fita.length - 1);
            } else if (no[MOVIMENTO_FITA].toUpperCase().equals("E") || no[MOVIMENTO_FITA].toUpperCase().equals("L")) {
                pointFita--;
                if (pointFita < 0) pointFita = 0;
            }
            if (++loops > 1000000) {
                System.out.println("Muitos loops");
                aceita = false;
                break;
            }
        }
        if (aceita) {
            System.out.println("A máquina reconhece essa sentença");
            lbAceita.setText("A máquina reconhece essa sentença");
        } else {
            System.out.println("A máquina não reconhece essa sentença");
            lbAceita.setText("A máquina não reconhece essa sentença");
        }
    }

    private boolean isFinal(int estadoAtual) {
        for (int estadoFinal : finais) {
            if (estadoAtual == estadoFinal) {
                System.out.println("O estado q" + estadoAtual + " é final");
                return true;
            }
        }
        return false;
    }

    private int achaIndiceSimbolo(char charLido) {
        for (int i = 0; i < simbolos.length; i++) {
            String simbolo = simbolos[i];
            if (String.valueOf(charLido).equals(simbolo)) {
                return i;
            }
        }
        System.out.println("O símbolo '" + charLido + "' não é reconhecido");
        return -1;
    }

    private String[][] gerarMatriz() {
        String[][] q = new String[estados.length][simbolos.length];
        ObservableList<Node> children = grid.getChildren();
        for (Node node : children) {
            try {
                if (node.getId().contains("tf")) {
                    // Como o ID é no estilo "tf0/1", precisamos remover o tf, e pegar o valor antes e depois da barra
                    String[] tfs = node.getId().replace("tf", "").split("/");
                    int iEstado = Integer.parseInt(tfs[0]); // o valor antes da barra é o indice do estado (q0,q1,q2)
                    int iSimbolo = Integer.parseInt(tfs[1]); // o valor depois da barra é o indice do simbolo (a,b,¬)

                    TextField textField = (TextField) node;
                    q[iEstado][iSimbolo] = textField.getText();
                }
            } catch (NullPointerException ignored) {
            }
        }
        for (String[] str : q
                ) {
            System.out.println(Arrays.toString(str));
        }
        return q;
    }

}

