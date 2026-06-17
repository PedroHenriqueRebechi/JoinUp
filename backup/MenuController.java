package controller;

import javafx.scene.Scene;
import javafx.stage.Stage;
import view.MenuView;

public class MenuController {

    private MenuView view;
    private Stage primaryStage;
    private Scene sceneCategoria;
    private Scene sceneEvento;
    private Scene sceneParticipante;
    private Scene sceneOrganizador;
    private Scene sceneSuporte;
    private Scene sceneChamadoSuporte;
    private EventoController eventoController;
    private Scene sceneArtista;
    private Scene sceneIngresso;
    private Scene sceneFormaPagamento;
    private Scene sceneLocal;
    public MenuController(MenuView view, Stage primaryStage, Scene sceneCategoria, Scene sceneEvento,
            Scene sceneParticipante, Scene sceneOrganizador, Scene sceneArtista, Scene sceneIngresso,
            Scene sceneSuporte, Scene sceneChamadoSuporte, Scene sceneFormaPagamento, Scene sceneLocal) {
        this.view = view;
        this.primaryStage = primaryStage;
        this.sceneCategoria = sceneCategoria;
        this.sceneEvento = sceneEvento;
        this.sceneParticipante = sceneParticipante;
        this.sceneOrganizador = sceneOrganizador;
        this.sceneArtista = sceneArtista;
        this.sceneIngresso = sceneIngresso;
        this.sceneSuporte = sceneSuporte;
        this.sceneChamadoSuporte = sceneChamadoSuporte;
        this.sceneFormaPagamento = sceneFormaPagamento;
        this.sceneLocal = sceneLocal;
        this.view.getBotaoCategoria().setOnAction(event -> irParaCategoria());
        this.view.getBotaoEvento().setOnAction(event -> irParaEvento());
        this.view.getBotaoParticipante().setOnAction(event -> irParaParticipante());
        this.view.getBotaoOrganizador().setOnAction(event -> irParaOrganizador());
        this.view.getBotaoArtista().setOnAction(event -> irParaArtista());
        this.view.getBotaoIngresso().setOnAction(event -> irParaIngresso());
        this.view.getBotaoSuporte().setOnAction(event -> irParaSuporte());
        this.view.getBotaoChamadoSuporte().setOnAction(event -> irParaChamadoSuporte());
        this.view.getBotaoFormaPagamento().setOnAction(event -> irParaFormaPagamento());
        this.view.getBotaoLocal().setOnAction(event -> irParaLocal());
    }

    public void setEventoController(EventoController eventoController) {
        this.eventoController = eventoController;
    }

    private void irParaCategoria() {
        primaryStage.setScene(sceneCategoria);
    }

    private void irParaEvento() {
        // Recarrega as categorias antes de ir para a tela de eventos
        if (eventoController != null) {
            eventoController.recarregarCategorias();
        }
        primaryStage.setScene(sceneEvento);
    }

    private void irParaParticipante() {
        primaryStage.setScene(sceneParticipante);
    }

    private void irParaOrganizador() {
        primaryStage.setScene(sceneOrganizador);
    }

    private void irParaArtista() {
        primaryStage.setScene(sceneArtista);
    }

    private void irParaIngresso() {
        primaryStage.setScene(sceneIngresso);
    }

    private void irParaSuporte() {
        primaryStage.setScene(sceneSuporte);
    }

    private void irParaChamadoSuporte() {
        primaryStage.setScene(sceneChamadoSuporte);
    }

    private void irParaFormaPagamento() {
        primaryStage.setScene(sceneFormaPagamento);
    }

    private void irParaLocal() {
        primaryStage.setScene(sceneLocal);
    }
}
