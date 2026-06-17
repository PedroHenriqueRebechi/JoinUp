package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Publicacao;

import java.util.List;

public class PublicacaoView extends GridPane {

    private Label labelIdSelecionada;
    private TextField campoAutor;
    private TextField campoTitulo;
    private TextField campoConteudo;
    private TextField campoEventoRelacionado;
    private TextField campoDataPublicacao;

    private Button botaoSalvar;
    private Button botaoExcluir;
    private Button botaoLimpar;
    private Button botaoVoltar;

    private TableView<Publicacao> tabelaPublicacoes;
    private ObservableList<Publicacao> tabelaData;

    public PublicacaoView() {
        this.setAlignment(Pos.CENTER);
        this.setHgap(20);
        this.setVgap(10);
        this.setPadding(new Insets(20, 20, 20, 20));

        construirFormularioUnificado();
        construirTabelaConsulta();
    }

    private void construirFormularioUnificado() {
        VBox colunaFormulario = new VBox(10);
        colunaFormulario.setAlignment(Pos.TOP_LEFT);

        Label titulo = new Label("Gestão de publicações");
        labelIdSelecionada = new Label("ID: nenhum selecionado");

        campoAutor = new TextField();
        campoAutor.setPromptText("Digite o autor");

        campoTitulo = new TextField();
        campoTitulo.setPromptText("Digite o título");

        campoConteudo = new TextField();
        campoConteudo.setPromptText("Digite o conteúdo");

        campoEventoRelacionado = new TextField();
        campoEventoRelacionado.setPromptText("Digite o evento relacionado");

        campoDataPublicacao = new TextField();
        campoDataPublicacao.setPromptText("Digite a data da publicação");

        botaoSalvar = new Button("Salvar");

        botaoExcluir = new Button("Excluir");
        botaoExcluir.setStyle("-fx-text-fill: white; -fx-background-color: #d32f2f;");
        botaoExcluir.setDisable(true);

        botaoLimpar = new Button("Limpar");
        botaoVoltar = new Button("Voltar");

        HBox painelBotoes = new HBox(10);
        painelBotoes.setAlignment(Pos.CENTER_LEFT);
        painelBotoes.getChildren().addAll(botaoSalvar, botaoExcluir, botaoLimpar, botaoVoltar);

        colunaFormulario.getChildren().addAll(
                titulo,
                labelIdSelecionada,
                new Label("Autor:"), campoAutor,
                new Label("Título:"), campoTitulo,
                new Label("Conteúdo:"), campoConteudo,
                new Label("Evento relacionado:"), campoEventoRelacionado,
                new Label("Data da publicação:"), campoDataPublicacao,
                painelBotoes);

        this.add(colunaFormulario, 0, 0);
    }

    private void construirTabelaConsulta() {
        VBox colunaTabela = new VBox(10);
        colunaTabela.setAlignment(Pos.TOP_LEFT);

        Label tituloTabela = new Label("Publicações cadastradas");
        tabelaPublicacoes = new TableView<>();
        tabelaData = FXCollections.observableArrayList();

        TableColumn<Publicacao, Integer> colunaId = new TableColumn<>("ID");
        colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaId.setPrefWidth(50);

        TableColumn<Publicacao, String> colunaAutor = new TableColumn<>("Autor");
        colunaAutor.setCellValueFactory(new PropertyValueFactory<>("autor"));
        colunaAutor.setPrefWidth(130);

        TableColumn<Publicacao, String> colunaTitulo = new TableColumn<>("Título");
        colunaTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colunaTitulo.setPrefWidth(160);

        TableColumn<Publicacao, String> colunaConteudo = new TableColumn<>("Conteúdo");
        colunaConteudo.setCellValueFactory(new PropertyValueFactory<>("conteudo"));
        colunaConteudo.setPrefWidth(230);

        TableColumn<Publicacao, String> colunaEventoRelacionado = new TableColumn<>("Evento");
        colunaEventoRelacionado.setCellValueFactory(new PropertyValueFactory<>("eventoRelacionado"));
        colunaEventoRelacionado.setPrefWidth(150);

        TableColumn<Publicacao, String> colunaDataPublicacao = new TableColumn<>("Data");
        colunaDataPublicacao.setCellValueFactory(new PropertyValueFactory<>("dataPublicacao"));
        colunaDataPublicacao.setPrefWidth(110);

        tabelaPublicacoes.getColumns().addAll(
                colunaId,
                colunaAutor,
                colunaTitulo,
                colunaConteudo,
                colunaEventoRelacionado,
                colunaDataPublicacao);

        tabelaPublicacoes.setItems(tabelaData);
        tabelaPublicacoes.setPrefSize(830, 300);

        colunaTabela.getChildren().addAll(tituloTabela, tabelaPublicacoes);
        this.add(colunaTabela, 1, 0);
    }

    public String getAutor() {
        return campoAutor.getText();
    }

    public String getTitulo() {
        return campoTitulo.getText();
    }

    public String getConteudo() {
        return campoConteudo.getText();
    }

    public String getEventoRelacionado() {
        return campoEventoRelacionado.getText();
    }

    public String getDataPublicacao() {
        return campoDataPublicacao.getText();
    }

    public void atualizarTabela(List<Publicacao> publicacoes) {
        try {
            if (publicacoes == null) {
                System.err.println("Erro: Lista de publicações é nula.");
                tabelaData.clear();
                return;
            }
            tabelaData.setAll(publicacoes);
        } catch (Exception e) {
            System.err.println("Erro ao atualizar tabela: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public TableView<Publicacao> getTabelaPublicacoes() {
        return tabelaPublicacoes;
    }

    public Button getBotaoSalvar() {
        return botaoSalvar;
    }

    public Button getBotaoExcluir() {
        return botaoExcluir;
    }

    public Button getBotaoLimpar() {
        return botaoLimpar;
    }

    public Button getBotaoVoltar() {
        return botaoVoltar;
    }

    public void limparCampos() {
        campoAutor.clear();
        campoTitulo.clear();
        campoConteudo.clear();
        campoEventoRelacionado.clear();
        campoDataPublicacao.clear();
    }

    public void preencherCampos(Publicacao publicacao) {
        try {
            if (publicacao == null) {
                labelIdSelecionada.setText("ID: nenhum selecionado");
                limparCampos();
                botaoExcluir.setDisable(true);
                return;
            }

            if (publicacao.getTitulo() == null || publicacao.getId() < 1) {
                System.err.println("Aviso: Publicação inválida.");
                limparCampos();
                return;
            }

            labelIdSelecionada.setText("ID: " + publicacao.getId());
            campoAutor.setText(publicacao.getAutor() != null ? publicacao.getAutor() : "");
            campoTitulo.setText(publicacao.getTitulo() != null ? publicacao.getTitulo() : "");
            campoConteudo.setText(publicacao.getConteudo() != null ? publicacao.getConteudo() : "");
            campoEventoRelacionado.setText(publicacao.getEventoRelacionado() != null ? publicacao.getEventoRelacionado() : "");
            campoDataPublicacao.setText(publicacao.getDataPublicacao() != null ? publicacao.getDataPublicacao() : "");
            botaoExcluir.setDisable(false);

        } catch (Exception e) {
            System.err.println("Erro ao preencher campos: " + e.getMessage());
            e.printStackTrace();
            limparCampos();
        }
    }

    public Publicacao getPublicacaoSelecionada() {
        return tabelaPublicacoes.getSelectionModel().getSelectedItem();
    }

    public void limparSelecao() {
        tabelaPublicacoes.getSelectionModel().clearSelection();
        preencherCampos(null);
    }
}