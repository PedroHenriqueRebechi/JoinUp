package controller;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.Avaliacao;
import view.AvaliacaoView;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AvaliacaoController {

    private static final String STORAGE_FILE = "avaliacoes.ser";

    private AvaliacaoView view;
    private List<Avaliacao> avaliacoes;

    public AvaliacaoController(AvaliacaoView view, Stage primaryStage, Scene sceneMenu) {
        this.view = view;
        this.avaliacoes = carregarAvaliacoes();

        this.view.atualizarTabela(avaliacoes);

        this.view.getBotaoSalvar().setOnAction(event -> salvarOuAtualizarAvaliacao());
        this.view.getBotaoExcluir().setOnAction(event -> excluirAvaliacao());
        this.view.getBotaoLimpar().setOnAction(event -> view.limparSelecao());
        this.view.getBotaoVoltar().setOnAction(event -> primaryStage.setScene(sceneMenu));

        this.view.getTabelaAvaliacoes().getSelectionModel().selectedItemProperty()
                .addListener((obs, antigo, selecionado) -> view.preencherCampos(selecionado));
    }

    private void salvarOuAtualizarAvaliacao() {
        try {
            String nomeParticipante = view.getNomeParticipante();
            String eventoAvaliado = view.getEventoAvaliado();
            String notaTexto = view.getNota();
            String comentario = view.getComentario();
            String dataAvaliacao = view.getDataAvaliacao();

            if (nomeParticipante == null || nomeParticipante.trim().isEmpty()) {
                mostrarErro("Nome do participante é obrigatório.");
                return;
            }

            if (eventoAvaliado == null || eventoAvaliado.trim().isEmpty()) {
                mostrarErro("Evento avaliado é obrigatório.");
                return;
            }

            if (notaTexto == null || notaTexto.trim().isEmpty()) {
                mostrarErro("Nota da avaliação é obrigatória.");
                return;
            }

            int nota;

            try {
                nota = Integer.parseInt(notaTexto.trim());
            } catch (NumberFormatException e) {
                mostrarErro("Nota deve ser um número inteiro de 1 a 5.");
                return;
            }

            if (nota < 1 || nota > 5) {
                mostrarErro("Nota deve estar entre 1 e 5.");
                return;
            }

            Avaliacao selecionada = view.getAvaliacaoSelecionada();

            if (selecionada == null) {
                Avaliacao novaAvaliacao = new Avaliacao(
                        nomeParticipante,
                        eventoAvaliado,
                        nota,
                        comentario,
                        dataAvaliacao);

                avaliacoes.add(novaAvaliacao);
                System.out.println("Avaliação criada: " + novaAvaliacao);
            } else {
                selecionada.setNomeParticipante(nomeParticipante);
                selecionada.setEventoAvaliado(eventoAvaliado);
                selecionada.setNota(nota);
                selecionada.setComentario(comentario);
                selecionada.setDataAvaliacao(dataAvaliacao);
                System.out.println("Avaliação atualizada: " + selecionada);
            }

            persistirAvaliacoes();
            view.atualizarTabela(avaliacoes);
            view.limparSelecao();
            mostrarSucesso("Avaliação salva com sucesso!");

        } catch (Exception e) {
            mostrarErro("Erro ao salvar avaliação: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void excluirAvaliacao() {
        Avaliacao selecionada = view.getAvaliacaoSelecionada();
        if (selecionada == null) {
            System.out.println("Selecione uma avaliação para excluir.");
            return;
        }

        avaliacoes.remove(selecionada);
        persistirAvaliacoes();
        view.atualizarTabela(avaliacoes);
        view.limparSelecao();
        System.out.println("Avaliação excluída: " + selecionada);
    }

    private void persistirAvaliacoes() {
        try (FileOutputStream fos = new FileOutputStream(STORAGE_FILE);
                ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            if (avaliacoes == null) {
                System.err.println("Erro: Lista de avaliações é nula.");
                return;
            }

            oos.writeObject(avaliacoes);
            System.out.println("Avaliações salvas com sucesso.");

        } catch (FileNotFoundException e) {
            System.err.println("Erro: Arquivo de avaliações não encontrado - " + STORAGE_FILE);
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Erro ao salvar avaliações: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao salvar avaliações: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private List<Avaliacao> carregarAvaliacoes() {
        try (FileInputStream fis = new FileInputStream(STORAGE_FILE);
                ObjectInputStream ois = new ObjectInputStream(fis)) {

            Object objeto = ois.readObject();

            if (objeto == null) {
                System.out.println("Arquivo existe mas está vazio. Iniciando lista vazia.");
                return new ArrayList<>();
            }

            if (!(objeto instanceof List)) {
                System.err.println("Erro: Arquivo não contém uma lista válida.");
                return new ArrayList<>();
            }

            @SuppressWarnings("unchecked")
            List<Avaliacao> lista = (List<Avaliacao>) objeto;

            if (lista.isEmpty()) {
                System.out.println("Nenhuma avaliação carregada.");
                return lista;
            }

            ajustarNextId(lista);
            System.out.println("Avaliações carregadas: " + lista.size());
            return lista;

        } catch (FileNotFoundException e) {
            System.out.println("Arquivo de avaliações não existe. Iniciando lista vazia.");
        } catch (EOFException e) {
            System.out.println("Arquivo de avaliações está corrompido ou vazio. Iniciando lista vazia.");
        } catch (ClassNotFoundException e) {
            System.err.println("Erro: Classe de avaliação não encontrada - " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Erro ao ler arquivo de avaliações: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erro inesperado ao carregar avaliações: " + e.getMessage());
        }

        return new ArrayList<>();
    }

    private void ajustarNextId(List<Avaliacao> lista) {
        int maiorId = 0;
        for (Avaliacao avaliacao : lista) {
            if (avaliacao.getId() > maiorId) {
                maiorId = avaliacao.getId();
            }
        }
        Avaliacao.setNextId(maiorId + 1);
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