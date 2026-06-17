package model;

import java.io.Serializable;

public class Local implements Serializable {
    private static final long serialVersionUID = 1L;

    private static int nextId = 1;

    private int id;
    private String nome;
    private String endereco;
    private int capacidadeMaxima;

    public Local(String nome, String endereco, int capacidadeMaxima) {
        this.id = nextId++;
        this.nome = nome;
        this.endereco = endereco;
        this.capacidadeMaxima = capacidadeMaxima;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public int getCapacidadeMaxima() {
        return capacidadeMaxima;
    }

    public void setCapacidadeMaxima(int capacidadeMaxima) {
        this.capacidadeMaxima = capacidadeMaxima;
    }

    public static void setNextId(int nextId) {
        if (nextId > Local.nextId) {
            Local.nextId = nextId;
        }
    }

    @Override
    public String toString() {
        return nome;
    }
}