package com.example.ecommercesistema.model;

public class Produto {

    private int id;
    private String nome;
    private double preco;
    private int estoque;

    // Construtor vazio (OBRIGATÓRIO pro JavaFX)
    public Produto() {}

    // Construtor SEM ID (para INSERT)
    public Produto(String nome, double preco, int estoque) {
        this.nome = nome;
        this.preco = preco;
        this.estoque = estoque;
    }

    // Construtor COM ID (para SELECT)
    public Produto(int id, String nome, double preco, int estoque) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.estoque = estoque;
    }

    // GETTERS
    public int getId() { return id; }
    public int getEstoque() { return estoque; }
    public double getPreco() { return preco; }
    public String getNome() { return nome; }

    // SETTERS
    public void setId(int id) { this.id = id; }
    public void setNome(String nome) { this.nome = nome; }
    public void setPreco(double preco) { this.preco = preco; }
    public void setEstoque(int estoque) { this.estoque = estoque; }

    @Override
    public String toString() {
        return nome + " - R$ " + String.format("%.2f", preco);
    }
}
