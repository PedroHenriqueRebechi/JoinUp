package controller;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.FormaPagamento;
import view.FormaPagamentoView;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class FormaPagamentoController {

    private static final String STORAGE_FILE = "formas_pagamento.ser";

    private FormaPagamentoView view;
    private List<FormaPagamento> formasPagamento;

    public FormaPagamentoController(FormaPagamentoView view, Stage primaryStage, Scene sceneMenu) {
        this.view = view;
        this.formasPagamento = carregarFormasPagamento();

        this.view.atualizarTabela(formasPagamento);

        this.view.getBotaoSalvar().setOnAction(event -> salvarOuAtualizarFormaPagamento());
        this.view.getBotaoExcluir().setOnAction(event -> excluirFormaPagamento());
        this.view.getBotaoLimpar().setOnAction(event -> view.limparSelecao());
        this.view.getBotaoVoltar().setOnAction(event -> primaryStage.setScene(sceneMenu));

        this.view.getTabelaFormasPagamento().getSelectionModel().selectedItemProperty()
                .addListener((obs, antigo, selecionado) -> view.preencherCampos(selecionado));
    }

    private void salvarOuAtualizarFormaPagamento() {
        try {
            String metodo = view.getMetodo();
            String parcelasStr = view.getParcelas();
            String cpf = view.getCpf();

            if (metodo == null || metodo.isEmpty()) {
                mostrarErro("Por favor, selecione um método de pagamento.");
                return;
            }

            if (parcelasStr == null || parcelasStr.trim().isEmpty()) {
                mostrarErro("A quantidade de parcelas é obrigatória.");
                return;
            }

            int parcelas = Integer.parseInt(parcelasStr.trim());

            if (parcelas <= 0) {
                mostrarErro("A quantidade de parcelas deve ser maior que zero.");
                return;
            }

            if (cpf == null || cpf.trim().isEmpty()) {
                mostrarErro("O CPF do comprador é obrigatório.");
                return;
            }

            FormaPagamento selecionada = view.getFormaPagamentoSelecionada();

            if (selecionada == null) {
                FormaPagamento novaFormaPagamento = new FormaPagamento(metodo, parcelas, cpf.trim());
                formasPagamento.add(novaFormaPagamento);
                System.out.println("Forma de pagamento criada: " + novaFormaPagamento);
            } else {
                selecionada.setMetodo(metodo);
                selecionada.setParcelas(parcelas);
                selecionada.setCpfComprador(cpf.trim());
                System.out.println("Forma de pagamento atualizada: " + selecionada);
            }

            persistirFormasPagamento();
            view.atualizarTabela(formasPagamento);
            view.limparSelecao();
            mostrarSucesso("Forma de pagamento salva com sucesso!");

        } catch (NumberFormatException e) {
            mostrarErro("A quantidade de parcelas deve ser um número inteiro válido.");
        } catch (Exception e) {
            mostrarErro("Erro ao salvar forma de pagamento: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void excluirFormaPagamento() {
        FormaPagamento selecionada = view.getFormaPagamentoSelecionada();
        if (selecionada == null) {
            mostrarErro("Selecione uma forma de pagamento para excluir.");
            return;
        }

        formasPagamento.remove(selecionada);
        persistirFormasPagamento();
        view.atualizarTabela(formasPagamento);
        view.limparSelecao();
        mostrarSucesso("Forma de pagamento excluída com sucesso!");
    }

    private void persistirFormasPagamento() {
        try (FileOutputStream fos = new FileOutputStream(STORAGE_FILE);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(formasPagamento);
            System.out.println("Formas de pagamento salvas com sucesso.");
        } catch (IOException e) {
            System.err.println("Erro ao salvar formas de pagamento: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private List<FormaPagamento> carregarFormasPagamento() {
        try (FileInputStream fis = new FileInputStream(STORAGE_FILE);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            Object objeto = ois.readObject();
            if (!(objeto instanceof List)) {
                System.err.println("Arquivo de formas de pagamento não contém uma lista válida.");
                return new ArrayList<>();
            }

            @SuppressWarnings("unchecked")
            List<FormaPagamento> lista = (List<FormaPagamento>) objeto;
            ajustarNextId(lista);
            System.out.println("Formas de pagamento carregadas: " + lista.size());
            return lista;

        } catch (FileNotFoundException e) {
            System.out.println("Arquivo de formas de pagamento não existe. Iniciando lista vazia.");
        } catch (EOFException e) {
            System.out.println("Arquivo de formas de pagamento vazio. Iniciando lista vazia.");
        } catch (ClassNotFoundException e) {
            System.err.println("Classe FormaPagamento não encontrada: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Erro ao ler formas de pagamento: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erro inesperado ao carregar formas de pagamento: " + e.getMessage());
        }

        return new ArrayList<>();
    }

    private void ajustarNextId(List<FormaPagamento> lista) {
        int maiorId = 0;
        for (FormaPagamento formaPagamento : lista) {
            if (formaPagamento.getId() > maiorId) {
                maiorId = formaPagamento.getId();
            }
        }
        FormaPagamento.setNextId(maiorId + 1);
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