package controller;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.Publicacao;
import view.PublicacaoView;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PublicacaoController {

    private static final String STORAGE_FILE = "publicacoes.ser";

    private PublicacaoView view;
    private List<Publicacao> publicacoes;

    public PublicacaoController(PublicacaoView view, Stage primaryStage, Scene sceneMenu) {
        this.view = view;
        this.publicacoes = carregarPublicacoes();

        this.view.atualizarTabela(publicacoes);

        this.view.getBotaoSalvar().setOnAction(event -> salvarOuAtualizarPublicacao());
        this.view.getBotaoExcluir().setOnAction(event -> excluirPublicacao());
        this.view.getBotaoLimpar().setOnAction(event -> view.limparSelecao());
        this.view.getBotaoVoltar().setOnAction(event -> primaryStage.setScene(sceneMenu));

        this.view.getTabelaPublicacoes().getSelectionModel().selectedItemProperty()
                .addListener((obs, antigo, selecionado) -> view.preencherCampos(selecionado));
    }

    private void salvarOuAtualizarPublicacao() {
        try {
            String autor = view.getAutor();
            String titulo = view.getTitulo();
            String conteudo = view.getConteudo();
            String eventoRelacionado = view.getEventoRelacionado();
            String dataPublicacao = view.getDataPublicacao();

            if (autor == null || autor.trim().isEmpty()) {
                mostrarErro("Autor da publicação é obrigatório.");
                return;
            }

            if (titulo == null || titulo.trim().isEmpty()) {
                mostrarErro("Título da publicação é obrigatório.");
                return;
            }

            if (conteudo == null || conteudo.trim().isEmpty()) {
                mostrarErro("Conteúdo da publicação é obrigatório.");
                return;
            }

            Publicacao selecionada = view.getPublicacaoSelecionada();

            if (selecionada == null) {
                Publicacao novaPublicacao = new Publicacao(
                        autor,
                        titulo,
                        conteudo,
                        eventoRelacionado,
                        dataPublicacao);

                publicacoes.add(novaPublicacao);
                System.out.println("Publicação criada: " + novaPublicacao);
            } else {
                selecionada.setAutor(autor);
                selecionada.setTitulo(titulo);
                selecionada.setConteudo(conteudo);
                selecionada.setEventoRelacionado(eventoRelacionado);
                selecionada.setDataPublicacao(dataPublicacao);
                System.out.println("Publicação atualizada: " + selecionada);
            }

            persistirPublicacoes();
            view.atualizarTabela(publicacoes);
            view.limparSelecao();
            mostrarSucesso("Publicação salva com sucesso!");

        } catch (Exception e) {
            mostrarErro("Erro ao salvar publicação: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void excluirPublicacao() {
        Publicacao selecionada = view.getPublicacaoSelecionada();
        if (selecionada == null) {
            System.out.println("Selecione uma publicação para excluir.");
            return;
        }

        publicacoes.remove(selecionada);
        persistirPublicacoes();
        view.atualizarTabela(publicacoes);
        view.limparSelecao();
        System.out.println("Publicação excluída: " + selecionada);
    }

    private void persistirPublicacoes() {
        try (FileOutputStream fos = new FileOutputStream(STORAGE_FILE);
                ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            if (publicacoes == null) {
                System.err.println("Erro: Lista de publicações é nula.");
                return;
            }

            oos.writeObject(publicacoes);
            System.out.println("Publicações salvas com sucesso.");

        } catch (FileNotFoundException e) {
            System.err.println("Erro: Arquivo de publicações não encontrado - " + STORAGE_FILE);
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Erro ao salvar publicações: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao salvar publicações: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private List<Publicacao> carregarPublicacoes() {
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
            List<Publicacao> lista = (List<Publicacao>) objeto;

            if (lista.isEmpty()) {
                System.out.println("Nenhuma publicação carregada.");
                return lista;
            }

            ajustarNextId(lista);
            System.out.println("Publicações carregadas: " + lista.size());
            return lista;

        } catch (FileNotFoundException e) {
            System.out.println("Arquivo de publicações não existe. Iniciando lista vazia.");
        } catch (EOFException e) {
            System.out.println("Arquivo de publicações está corrompido ou vazio. Iniciando lista vazia.");
        } catch (ClassNotFoundException e) {
            System.err.println("Erro: Classe de publicação não encontrada - " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Erro ao ler arquivo de publicações: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erro inesperado ao carregar publicações: " + e.getMessage());
        }

        return new ArrayList<>();
    }

    private void ajustarNextId(List<Publicacao> lista) {
        int maiorId = 0;
        for (Publicacao publicacao : lista) {
            if (publicacao.getId() > maiorId) {
                maiorId = publicacao.getId();
            }
        }
        Publicacao.setNextId(maiorId + 1);
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