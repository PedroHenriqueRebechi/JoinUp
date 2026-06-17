package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.FormaPagamento;

import java.util.List;

public class FormaPagamentoView extends GridPane {

    private Label labelIdSelecionado;
    private ComboBox<String> comboMetodo;
    private TextField campoParcelas;
    private TextField campoCpf;
    private Button botaoSalvar;
    private Button botaoExcluir;
    private Button botaoLimpar;
    private Button botaoVoltar;
    private TableView<FormaPagamento> tabelaFormasPagamento;
    private ObservableList<FormaPagamento> tabelaData;

    public FormaPagamentoView() {
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

        Label titulo = new Label("Gestão de formas de pagamento");
        labelIdSelecionado = new Label("ID: nenhum selecionado");

        comboMetodo = new ComboBox<>();
        comboMetodo.getItems().addAll("Débito", "Crédito", "Pix");
        comboMetodo.setPromptText("Selecione o método");
        comboMetodo.setPrefWidth(200);

        campoParcelas = new TextField();
        campoParcelas.setPromptText("Digite a qtd de parcelas (ex: 1)");

        campoCpf = new TextField();
        campoCpf.setPromptText("Digite o CPF do comprador");

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
                new Label("Método de Pagamento:"), comboMetodo,
                new Label("Quantidade de Parcelas:"), campoParcelas,
                new Label("CPF do Comprador:"), campoCpf,
                painelBotoes
        );

        this.add(colunaFormulario, 0, 0);
    }

    private void construirTabela() {
        VBox colunaTabela = new VBox(10);
        colunaTabela.setAlignment(Pos.TOP_LEFT);

        Label tituloTabela = new Label("Formas de pagamento cadastradas");
        tabelaFormasPagamento = new TableView<>();
        tabelaData = FXCollections.observableArrayList();

        TableColumn<FormaPagamento, Integer> colunaId = new TableColumn<>("ID");
        colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaId.setPrefWidth(50);

        TableColumn<FormaPagamento, String> colunaMetodo = new TableColumn<>("Método");
        colunaMetodo.setCellValueFactory(new PropertyValueFactory<>("metodo"));
        colunaMetodo.setPrefWidth(140);

        TableColumn<FormaPagamento, Integer> colunaParcelas = new TableColumn<>("Parcelas");
        colunaParcelas.setCellValueFactory(new PropertyValueFactory<>("parcelas"));
        colunaParcelas.setPrefWidth(100);

        TableColumn<FormaPagamento, String> colunaCpf = new TableColumn<>("CPF Comprador");
        colunaCpf.setCellValueFactory(new PropertyValueFactory<>("cpfComprador"));
        colunaCpf.setPrefWidth(240);

        tabelaFormasPagamento.getColumns().addAll(colunaId, colunaMetodo, colunaParcelas, colunaCpf);
        tabelaFormasPagamento.setItems(tabelaData);
        tabelaFormasPagamento.setPrefSize(560, 260);

        colunaTabela.getChildren().addAll(tituloTabela, tabelaFormasPagamento);
        this.add(colunaTabela, 1, 0);
    }

    public String getMetodo() {
        return comboMetodo.getValue();
    }

    public String getParcelas() {
        return campoParcelas.getText();
    }

    public String getCpf() {
        return campoCpf.getText();
    }

    public void atualizarTabela(List<FormaPagamento> formasPagamento) {
        tabelaData.setAll(formasPagamento);
    }

    public FormaPagamento getFormaPagamentoSelecionada() {
        return tabelaFormasPagamento.getSelectionModel().getSelectedItem();
    }

    public TableView<FormaPagamento> getTabelaFormasPagamento() {
        return tabelaFormasPagamento;
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
        tabelaFormasPagamento.getSelectionModel().clearSelection();
        labelIdSelecionado.setText("ID: nenhum selecionado");
        comboMetodo.setValue(null);
        campoParcelas.clear();
        campoCpf.clear();
        botaoExcluir.setDisable(true);
    }

    public void preencherCampos(FormaPagamento formaPagamento) {
        if (formaPagamento == null) {
            limparSelecao();
            return;
        }

        labelIdSelecionado.setText("ID: " + formaPagamento.getId());
        comboMetodo.setValue(formaPagamento.getMetodo());
        campoParcelas.setText(String.valueOf(formaPagamento.getParcelas()));
        campoCpf.setText(formaPagamento.getCpfComprador());
        botaoExcluir.setDisable(false);
    }
}