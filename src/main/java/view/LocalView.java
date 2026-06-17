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
import model.Local;

import java.util.List;

public class LocalView extends GridPane {

    private Label labelIdSelecionado;
    private TextField campoNome;
    private TextField campoEndereco;
    private TextField campoCapacidade;
    private Button botaoSalvar;
    private Button botaoExcluir;
    private Button botaoLimpar;
    private Button botaoVoltar;
    private TableView<Local> tabelaLocais;
    private ObservableList<Local> tabelaData;

    public LocalView() {
        this.setAlignment(Pos.CENTER);
        this.setHgap(20);
        this.setVgap(10);
        this.setPadding(new Insets(20, 20, 20, 20));

        construirFormulario();
        construirTabela();
    }

    private void construirFormulario() {
        VBox colunaFormulario = new VBox(10);
        colunaFormulario.setAlignment(Pos.TOP_LEFT);

        Label titulo = new Label("Gestão de locais dos eventos");
        labelIdSelecionado = new Label("ID: nenhum selecionado");

        campoNome = new TextField();
        campoNome.setPromptText("Digite o nome do local");

        campoEndereco = new TextField();
        campoEndereco.setPromptText("Digite o endereço completo");

        campoCapacidade = new TextField();
        campoCapacidade.setPromptText("Digite a capacidade máxima");

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
                labelIdSelecionado,
                new Label("Nome do Local:"), campoNome,
                new Label("Endereço:"), campoEndereco,
                new Label("Capacidade Máxima:"), campoCapacidade,
                painelBotoes
        );

        this.add(colunaFormulario, 0, 0);
    }

    private void construirTabela() {
        VBox colunaTabela = new VBox(10);
        colunaTabela.setAlignment(Pos.TOP_LEFT);

        Label tituloTabela = new Label("Locais cadastrados");
        tabelaLocais = new TableView<>();
        tabelaData = FXCollections.observableArrayList();

        TableColumn<Local, Integer> colunaId = new TableColumn<>("ID");
        colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaId.setPrefWidth(50);

        TableColumn<Local, String> colunaNome = new TableColumn<>("Nome");
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaNome.setPrefWidth(160);

        TableColumn<Local, String> colunaEndereco = new TableColumn<>("Endereço");
        colunaEndereco.setCellValueFactory(new PropertyValueFactory<>("endereco"));
        colunaEndereco.setPrefWidth(220);

        TableColumn<Local, Integer> colunaCapacidade = new TableColumn<>("Capacidade");
        colunaCapacidade.setCellValueFactory(new PropertyValueFactory<>("capacidadeMaxima"));
        colunaCapacidade.setPrefWidth(100);

        tabelaLocais.getColumns().addAll(colunaId, colunaNome, colunaEndereco, colunaCapacidade);
        tabelaLocais.setItems(tabelaData);
        tabelaLocais.setPrefSize(560, 260);

        colunaTabela.getChildren().addAll(tituloTabela, tabelaLocais);
        this.add(colunaTabela, 1, 0);
    }

    public String getNome() {
        return campoNome.getText();
    }

    public String getEndereco() {
        return campoEndereco.getText();
    }

    public String getCapacidade() {
        return campoCapacidade.getText();
    }

    public void atualizarTabela(List<Local> locais) {
        tabelaData.setAll(locais);
    }

    public Local getLocalSelecionado() {
        return tabelaLocais.getSelectionModel().getSelectedItem();
    }

    public TableView<Local> getTabelaLocais() {
        return tabelaLocais;
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

    public void limparSelecao() {
        tabelaLocais.getSelectionModel().clearSelection();
        labelIdSelecionado.setText("ID: nenhum selecionado");
        campoNome.clear();
        campoEndereco.clear();
        campoCapacidade.clear();
        botaoExcluir.setDisable(true);
    }

    public void preencherCampos(Local local) {
        if (local == null) {
            limparSelecao();
            return;
        }

        labelIdSelecionado.setText("ID: " + local.getId());
        campoNome.setText(local.getNome());
        campoEndereco.setText(local.getEndereco());
        campoCapacidade.setText(String.valueOf(local.getCapacidadeMaxima()));
        botaoExcluir.setDisable(false);
    }
}