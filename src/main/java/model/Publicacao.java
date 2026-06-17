package model;

import java.io.Serializable;

public class Publicacao implements Serializable {
    private static final long serialVersionUID = 1L;

    private static int nextId = 1;

    private int id;
    private String autor;
    private String titulo;
    private String conteudo;
    private String eventoRelacionado;
    private String dataPublicacao;

    public Publicacao(String autor, String titulo, String conteudo, String eventoRelacionado, String dataPublicacao) {
        this.id = nextId++;
        this.autor = autor;
        this.titulo = titulo;
        this.conteudo = conteudo;
        this.eventoRelacionado = eventoRelacionado;
        this.dataPublicacao = dataPublicacao;
    }

    public int getId() {
        return id;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public String getEventoRelacionado() {
        return eventoRelacionado;
    }

    public void setEventoRelacionado(String eventoRelacionado) {
        this.eventoRelacionado = eventoRelacionado;
    }

    public String getDataPublicacao() {
        return dataPublicacao;
    }

    public void setDataPublicacao(String dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
    }

    public static void setNextId(int nextId) {
        if (nextId > Publicacao.nextId) {
            Publicacao.nextId = nextId;
        }
    }

    @Override
    public String toString() {
        return titulo;
    }
}