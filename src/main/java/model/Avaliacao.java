package model;

import java.io.Serializable;

public class Avaliacao implements Serializable {
    private static final long serialVersionUID = 1L;

    private static int nextId = 1;

    private int id;
    private String nomeParticipante;
    private String eventoAvaliado;
    private int nota;
    private String comentario;
    private String dataAvaliacao;

    public Avaliacao(String nomeParticipante, String eventoAvaliado, int nota, String comentario, String dataAvaliacao) {
        this.id = nextId++;
        this.nomeParticipante = nomeParticipante;
        this.eventoAvaliado = eventoAvaliado;
        this.nota = nota;
        this.comentario = comentario;
        this.dataAvaliacao = dataAvaliacao;
    }

    public int getId() {
        return id;
    }

    public String getNomeParticipante() {
        return nomeParticipante;
    }

    public void setNomeParticipante(String nomeParticipante) {
        this.nomeParticipante = nomeParticipante;
    }

    public String getEventoAvaliado() {
        return eventoAvaliado;
    }

    public void setEventoAvaliado(String eventoAvaliado) {
        this.eventoAvaliado = eventoAvaliado;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getDataAvaliacao() {
        return dataAvaliacao;
    }

    public void setDataAvaliacao(String dataAvaliacao) {
        this.dataAvaliacao = dataAvaliacao;
    }

    public static void setNextId(int nextId) {
        if (nextId > Avaliacao.nextId) {
            Avaliacao.nextId = nextId;
        }
    }

    @Override
    public String toString() {
        return eventoAvaliado + " - Nota " + nota;
    }
}