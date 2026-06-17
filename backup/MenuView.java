package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class MenuView extends GridPane {

    private Button botaoCategoria;
    private Button botaoEvento;
    private Button botaoParticipante;
    private Button botaoOrganizador;
    private Button botaoArtista;
    private Button botaoIngresso;
    private Button botaoSuporte;
    private Button botaoChamadoSuporte;
    private Button botaoFormaPagamento;
    private Button botaoLocal;

    public MenuView() {
        configurarTela();

        Label titulo = criarTitulo();
        Label subtitulo = criarSubtitulo();

        VBox cabecalho = new VBox(6);
        cabecalho.setAlignment(Pos.CENTER_LEFT);
        cabecalho.getChildren().addAll(titulo, subtitulo);

        botaoEvento = criarBotaoMenu("Gestão de eventos", "Cadastrar, editar e visualizar eventos");
        botaoParticipante = criarBotaoMenu("Participantes", "Gerenciar participantes cadastrados");
        botaoOrganizador = criarBotaoMenu("Organizadores", "Controlar organizadores de eventos");
        botaoArtista = criarBotaoMenu("Artistas", "Cadastrar artistas e atrações");

        botaoIngresso = criarBotaoMenu("Ingressos", "Gerenciar ingressos dos eventos");
        botaoCategoria = criarBotaoMenu("Categorias", "Organizar tipos de eventos");
        botaoLocal = criarBotaoMenu("Locais", "Cadastrar locais dos eventos");
        botaoFormaPagamento = criarBotaoMenu("Formas de pagamento", "Gerenciar métodos de pagamento");

        botaoSuporte = criarBotaoMenu("Suporte", "Cadastrar canais de atendimento");
        botaoChamadoSuporte = criarBotaoMenu("Chamados de suporte", "Acompanhar solicitações abertas");

        GridPane gradeBotoes = criarGradeBotoes();

        adicionarBotaoNaGrade(gradeBotoes, botaoEvento, 0, 0);
        adicionarBotaoNaGrade(gradeBotoes, botaoParticipante, 1, 0);

        adicionarBotaoNaGrade(gradeBotoes, botaoOrganizador, 0, 1);
        adicionarBotaoNaGrade(gradeBotoes, botaoArtista, 1, 1);

        adicionarBotaoNaGrade(gradeBotoes, botaoIngresso, 0, 2);
        adicionarBotaoNaGrade(gradeBotoes, botaoCategoria, 1, 2);

        adicionarBotaoNaGrade(gradeBotoes, botaoLocal, 0, 3);
        adicionarBotaoNaGrade(gradeBotoes, botaoFormaPagamento, 1, 3);

        adicionarBotaoNaGrade(gradeBotoes, botaoSuporte, 0, 4);
        adicionarBotaoNaGrade(gradeBotoes, botaoChamadoSuporte, 1, 4);

        VBox container = new VBox(28);
        container.setAlignment(Pos.TOP_CENTER);
        container.setPadding(new Insets(32));
        container.setMaxWidth(720);
        container.setStyle(
                "-fx-background-color: white;" +
                "-fx-background-radius: 18;" +
                "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.12), 18, 0, 0, 4);"
        );

        container.getChildren().addAll(cabecalho, gradeBotoes);

        this.add(container, 0, 0);
    }

    private void configurarTela() {
        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(32));
        this.setHgap(0);
        this.setVgap(0);
        this.setStyle("-fx-background-color: linear-gradient(to bottom right, #f4f6fb, #e9edf5);");
    }

    private Label criarTitulo() {
        Label titulo = new Label("JoinUp");
        titulo.setStyle(
                "-fx-font-size: 32px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: #1f2937;"
        );
        return titulo;
    }

    private Label criarSubtitulo() {
        Label subtitulo = new Label("Painel principal de gerenciamento");
        subtitulo.setStyle(
                "-fx-font-size: 14px;" +
                "-fx-text-fill: #6b7280;"
        );
        return subtitulo;
    }

    private GridPane criarGradeBotoes() {
        GridPane grade = new GridPane();
        grade.setHgap(16);
        grade.setVgap(16);
        grade.setAlignment(Pos.CENTER);

        ColumnConstraints coluna1 = new ColumnConstraints();
        coluna1.setPercentWidth(50);

        ColumnConstraints coluna2 = new ColumnConstraints();
        coluna2.setPercentWidth(50);

        grade.getColumnConstraints().addAll(coluna1, coluna2);

        return grade;
    }

    private Button criarBotaoMenu(String titulo, String descricao) {
        Button botao = new Button(titulo + "\n" + descricao);

        botao.setPrefSize(320, 78);
        botao.setMaxWidth(Double.MAX_VALUE);
        botao.setAlignment(Pos.CENTER_LEFT);
        botao.setPadding(new Insets(14, 18, 14, 18));

        aplicarEstiloNormal(botao);

        botao.setOnMouseEntered(event -> aplicarEstiloHover(botao));
        botao.setOnMouseExited(event -> aplicarEstiloNormal(botao));

        return botao;
    }

    private void aplicarEstiloNormal(Button botao) {
        botao.setStyle(
                "-fx-background-color: #f9fafb;" +
                "-fx-background-radius: 14;" +
                "-fx-border-color: #e5e7eb;" +
                "-fx-border-radius: 14;" +
                "-fx-border-width: 1;" +
                "-fx-text-fill: #111827;" +
                "-fx-font-size: 13px;" +
                "-fx-font-weight: bold;" +
                "-fx-cursor: hand;"
        );
    }

    private void aplicarEstiloHover(Button botao) {
        botao.setStyle(
                "-fx-background-color: #eef2ff;" +
                "-fx-background-radius: 14;" +
                "-fx-border-color: #6366f1;" +
                "-fx-border-radius: 14;" +
                "-fx-border-width: 1;" +
                "-fx-text-fill: #111827;" +
                "-fx-font-size: 13px;" +
                "-fx-font-weight: bold;" +
                "-fx-cursor: hand;"
        );
    }

    private void adicionarBotaoNaGrade(GridPane grade, Button botao, int coluna, int linha) {
        grade.add(botao, coluna, linha);
    }

    public Button getBotaoCategoria() {
        return botaoCategoria;
    }

    public Button getBotaoEvento() {
        return botaoEvento;
    }

    public Button getBotaoParticipante() {
        return botaoParticipante;
    }

    public Button getBotaoOrganizador() {
        return botaoOrganizador;
    }

    public Button getBotaoArtista() {
        return botaoArtista;
    }

    public Button getBotaoIngresso() {
        return botaoIngresso;
    }

    public Button getBotaoSuporte() {
        return botaoSuporte;
    }

    public Button getBotaoChamadoSuporte() {
        return botaoChamadoSuporte;
    }

    public Button getBotaoFormaPagamento() {
        return botaoFormaPagamento;
    }

    public Button getBotaoLocal() {
        return botaoLocal;
    }
}