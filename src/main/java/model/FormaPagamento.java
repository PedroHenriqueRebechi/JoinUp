package model;

import java.io.Serializable;

public class FormaPagamento implements Serializable {
    private static final long serialVersionUID = 1L;

    private static int nextId = 1;

    private int id;
    private String metodo; // Débito, Crédito ou Pix
    private int parcelas;
    private String cpfComprador;

    public FormaPagamento(String metodo, int parcelas, String cpfComprador) {
        this.id = nextId++;
        this.metodo = metodo;
        this.parcelas = parcelas;
        this.cpfComprador = cpfComprador;
    }

    public int getId() {
        return id;
    }

    public String getMetodo() {
        return metodo;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }

    public int getParcelas() {
        return parcelas;
    }

    public void setParcelas(int parcelas) {
        this.parcelas = parcelas;
    }

    public String getCpfComprador() {
        return cpfComprador;
    }

    public void setCpfComprador(String cpfComprador) {
        this.cpfComprador = cpfComprador;
    }

    public static void setNextId(int nextId) {
        if (nextId > FormaPagamento.nextId) {
            FormaPagamento.nextId = nextId;
        }
    }

    @Override
    public String toString() {
        return metodo + " - " + cpfComprador;
    }
}