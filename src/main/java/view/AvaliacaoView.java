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
import model.Avaliacao;

import java.util.List;

public class AvaliacaoView extends GridPane {

    private Label labelIdSelecionada;
    private TextField campoNomeParticipante;
    private TextField campoEventoAvaliado;
    private TextField campoNota;
    private TextField campoComentario;
    private TextField campoDataAvaliacao;

    private Button botaoSalvar;
    private Button botaoExcluir;
    private Button botaoLimpar;
    private Button botaoVoltar;

    private TableView<Avaliacao> tabelaAvaliacoes;
    private ObservableList<Avaliacao> tabelaData;

    public AvaliacaoView() {
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

        Label titulo = new Label("Gestão de avaliações");
        labelIdSelecionada = new Label("ID: nenhum selecionado");

        campoNomeParticipante = new TextField();
        campoNomeParticipante.setPromptText("Digite o nome do participante");

        campoEventoAvaliado = new TextField();
        campoEventoAvaliado.setPromptText("Digite o evento avaliado");

        campoNota = new TextField();
        campoNota.setPromptText("Digite uma nota de 1 a 5");

        campoComentario = new TextField();
        campoComentario.setPromptText("Digite o comentário");

        campoDataAvaliacao = new TextField();
        campoDataAvaliacao.setPromptText("Digite a data da avaliação");

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
                new Label("Participante:"), campoNomeParticipante,
                new Label("Evento avaliado:"), campoEventoAvaliado,
                new Label("Nota:"), campoNota,
                new Label("Comentário:"), campoComentario,
                new Label("Data da avaliação:"), campoDataAvaliacao,
                painelBotoes);

        this.add(colunaFormulario, 0, 0);
    }

    private void construirTabelaConsulta() {
        VBox colunaTabela = new VBox(10);
        colunaTabela.setAlignment(Pos.TOP_LEFT);

        Label tituloTabela = new Label("Avaliações cadastradas");
        tabelaAvaliacoes = new TableView<>();
        tabelaData = FXCollections.observableArrayList();

        TableColumn<Avaliacao, Integer> colunaId = new TableColumn<>("ID");
        colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaId.setPrefWidth(50);

        TableColumn<Avaliacao, String> colunaNomeParticipante = new TableColumn<>("Participante");
        colunaNomeParticipante.setCellValueFactory(new PropertyValueFactory<>("nomeParticipante"));
        colunaNomeParticipante.setPrefWidth(170);

        TableColumn<Avaliacao, String> colunaEventoAvaliado = new TableColumn<>("Evento");
        colunaEventoAvaliado.setCellValueFactory(new PropertyValueFactory<>("eventoAvaliado"));
        colunaEventoAvaliado.setPrefWidth(180);

        TableColumn<Avaliacao, Integer> colunaNota = new TableColumn<>("Nota");
        colunaNota.setCellValueFactory(new PropertyValueFactory<>("nota"));
        colunaNota.setPrefWidth(70);

        TableColumn<Avaliacao, String> colunaComentario = new TableColumn<>("Comentário");
        colunaComentario.setCellValueFactory(new PropertyValueFactory<>("comentario"));
        colunaComentario.setPrefWidth(260);

        TableColumn<Avaliacao, String> colunaDataAvaliacao = new TableColumn<>("Data");
        colunaDataAvaliacao.setCellValueFactory(new PropertyValueFactory<>("dataAvaliacao"));
        colunaDataAvaliacao.setPrefWidth(110);

        tabelaAvaliacoes.getColumns().addAll(
                colunaId,
                colunaNomeParticipante,
                colunaEventoAvaliado,
                colunaNota,
                colunaComentario,
                colunaDataAvaliacao);

        tabelaAvaliacoes.setItems(tabelaData);
        tabelaAvaliacoes.setPrefSize(840, 300);

        colunaTabela.getChildren().addAll(tituloTabela, tabelaAvaliacoes);
        this.add(colunaTabela, 1, 0);
    }

    public String getNomeParticipante() {
        return campoNomeParticipante.getText();
    }

    public String getEventoAvaliado() {
        return campoEventoAvaliado.getText();
    }

    public String getNota() {
        return campoNota.getText();
    }

    public String getComentario() {
        return campoComentario.getText();
    }

    public String getDataAvaliacao() {
        return campoDataAvaliacao.getText();
    }

    public void atualizarTabela(List<Avaliacao> avaliacoes) {
        try {
            if (avaliacoes == null) {
                System.err.println("Erro: Lista de avaliações é nula.");
                tabelaData.clear();
                return;
            }
            tabelaData.setAll(avaliacoes);
        } catch (Exception e) {
            System.err.println("Erro ao atualizar tabela: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public TableView<Avaliacao> getTabelaAvaliacoes() {
        return tabelaAvaliacoes;
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
        campoNomeParticipante.clear();
        campoEventoAvaliado.clear();
        campoNota.clear();
        campoComentario.clear();
        campoDataAvaliacao.clear();
    }

    public void preencherCampos(Avaliacao avaliacao) {
        try {
            if (avaliacao == null) {
                labelIdSelecionada.setText("ID: nenhum selecionado");
                limparCampos();
                botaoExcluir.setDisable(true);
                return;
            }

            if (avaliacao.getEventoAvaliado() == null || avaliacao.getId() < 1) {
                System.err.println("Aviso: Avaliação inválida.");
                limparCampos();
                return;
            }

            labelIdSelecionada.setText("ID: " + avaliacao.getId());
            campoNomeParticipante.setText(avaliacao.getNomeParticipante() != null ? avaliacao.getNomeParticipante() : "");
            campoEventoAvaliado.setText(avaliacao.getEventoAvaliado() != null ? avaliacao.getEventoAvaliado() : "");
            campoNota.setText(String.valueOf(avaliacao.getNota()));
            campoComentario.setText(avaliacao.getComentario() != null ? avaliacao.getComentario() : "");
            campoDataAvaliacao.setText(avaliacao.getDataAvaliacao() != null ? avaliacao.getDataAvaliacao() : "");
            botaoExcluir.setDisable(false);

        } catch (Exception e) {
            System.err.println("Erro ao preencher campos: " + e.getMessage());
            e.printStackTrace();
            limparCampos();
        }
    }

    public Avaliacao getAvaliacaoSelecionada() {
        return tabelaAvaliacoes.getSelectionModel().getSelectedItem();
    }

    public void limparSelecao() {
        tabelaAvaliacoes.getSelectionModel().clearSelection();
        preencherCampos(null);
    }
}