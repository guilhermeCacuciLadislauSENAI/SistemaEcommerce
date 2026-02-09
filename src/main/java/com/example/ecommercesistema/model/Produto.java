package com.example.ecommercesistema.model;

public class Produto {

    private int id;
    private String nome;
    private double preco;
    private int estoque;

    // Construtor vazio (obrigatório)
    public Produto() {}

    // Construtor com dados
    public Produto(String nome, double preco, int estoque) {
        this.nome = nome;
        this.preco = preco;
        this.estoque = estoque;
    }

    // GETTERS
    public int getId() { return id; }
    public String getNome() { return nome; }
    public double getPreco() {
        return preco;
    }
    public int getEstoque() {
        return estoque;
    }

    //Setters
    public void setId(int id) { this.id = id; }
    public void setNome(String nome) { this.nome = nome; }
    public void setPreco(double preco) {
        this.preco = preco;
    }
    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }
}
