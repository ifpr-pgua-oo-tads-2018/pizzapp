package pizzapp.model;

import java.io.Serializable;

public class Pizza implements Serializable {

    private String nome;
    private double valor;

    public Pizza(String nome, double valor) {
        this.nome = nome;
        this.valor = valor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return  nome;
    }
}
