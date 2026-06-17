package controller;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.Local;
import view.LocalView;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class LocalController {

    private static final String STORAGE_FILE = "locais.ser";

    private LocalView view;
    private List<Local> locais;

    public LocalController(LocalView view, Stage primaryStage, Scene sceneMenu) {
        this.view = view;
        this.locais = carregarLocais();

        this.view.atualizarTabela(locais);

        this.view.getBotaoSalvar().setOnAction(event -> salvarOuAtualizarLocal());
        this.view.getBotaoExcluir().setOnAction(event -> excluirLocal());
        this.view.getBotaoLimpar().setOnAction(event -> view.limparSelecao());
        this.view.getBotaoVoltar().setOnAction(event -> primaryStage.setScene(sceneMenu));

        this.view.getTabelaLocais().getSelectionModel().selectedItemProperty()
                .addListener((obs, antigo, selecionado) -> view.preencherCampos(selecionado));
    }

    private void salvarOuAtualizarLocal() {
        try {
            String nome = view.getNome();
            String endereco = view.getEndereco();
            String capacidadeStr = view.getCapacidade();

            if (nome == null || nome.trim().isEmpty()) {
                mostrarErro("O nome do local é obrigatório.");
                return;
            }

            if (endereco == null || endereco.trim().isEmpty()) {
                mostrarErro("O endereço do local é obrigatório.");
                return;
            }

            if (capacidadeStr == null || capacidadeStr.trim().isEmpty()) {
                mostrarErro("A capacidade máxima é obrigatória.");
                return;
            }

            int capacidade = Integer.parseInt(capacidadeStr.trim());

            if (capacidade <= 0) {
                mostrarErro("A capacidade máxima deve ser maior que zero.");
                return;
            }

            Local selecionado = view.getLocalSelecionado();

            if (selecionado == null) {
                Local novoLocal = new Local(nome.trim(), endereco.trim(), capacidade);
                locais.add(novoLocal);
                System.out.println("Local criado: " + novoLocal);
            } else {
                selecionado.setNome(nome.trim());
                selecionado.setEndereco(endereco.trim());
                selecionado.setCapacidadeMaxima(capacidade);
                System.out.println("Local atualizado: " + selecionado);
            }

            persistirLocais();
            view.atualizarTabela(locais);
            view.limparSelecao();
            mostrarSucesso("Local salvo com sucesso!");

        } catch (NumberFormatException e) {
            mostrarErro("A capacidade deve ser um número inteiro válido.");
        } catch (Exception e) {
            mostrarErro("Erro ao salvar local: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void excluirLocal() {
        Local selecionado = view.getLocalSelecionado();
        if (selecionado == null) {
            mostrarErro("Selecione um local para excluir.");
            return;
        }

        locais.remove(selecionado);
        persistirLocais();
        view.atualizarTabela(locais);
        view.limparSelecao();
        mostrarSucesso("Local excluído com sucesso!");
    }

    private void persistirLocais() {
        try (FileOutputStream fos = new FileOutputStream(STORAGE_FILE);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(locais);
            System.out.println("Locais salvos com sucesso.");
        } catch (IOException e) {
            System.err.println("Erro ao salvar locais: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private List<Local> carregarLocais() {
        try (FileInputStream fis = new FileInputStream(STORAGE_FILE);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            Object objeto = ois.readObject();
            if (!(objeto instanceof List)) {
                System.err.println("Arquivo de locais não contém uma lista válida.");
                return new ArrayList<>();
            }

            @SuppressWarnings("unchecked")
            List<Local> lista = (List<Local>) objeto;
            ajustarNextId(lista);
            System.out.println("Locais carregados: " + lista.size());
            return lista;

        } catch (FileNotFoundException e) {
            System.out.println("Arquivo de locais não existe. Iniciando lista vazia.");
        } catch (EOFException e) {
            System.out.println("Arquivo de locais vazio. Iniciando lista vazia.");
        } catch (ClassNotFoundException e) {
            System.err.println("Classe Local não encontrada: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Erro ao ler locais: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erro inesperado ao carregar locais: " + e.getMessage());
        }

        return new ArrayList<>();
    }

    private void ajustarNextId(List<Local> lista) {
        int maiorId = 0;
        for (Local local : lista) {
            if (local.getId() > maiorId) {
                maiorId = local.getId();
            }
        }
        Local.setNextId(maiorId + 1);
    }

    private void mostrarErro(String mensagem) {
        System.err.println("[ERRO] " + mensagem);
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText("Operação não pode ser concluída");
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    private void mostrarSucesso(String mensagem) {
        System.out.println("[SUCESSO] " + mensagem);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sucesso");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}