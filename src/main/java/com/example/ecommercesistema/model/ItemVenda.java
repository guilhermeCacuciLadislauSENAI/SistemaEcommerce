package com.example.ecommercesistema.model;

public class ItemVenda {
    private int id;
    private Produto produto;
    private int quantidade;
    private double precoUnitario;

    public ItemVenda () {}

    public ItemVenda (Produto produto, int quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public ItemVenda (Produto produto, int quantidade, double precoUnitario) {
        this.produto = produto;
        this.quantidade = quantidade;
        this.precoUnitario = produto.getPreco();
    }

    public ItemVenda (int id, Produto produto, int quantidade, double precoUnitario) {
        this.id = id;
        this.produto = produto;
        this.quantidade = quantidade;
        this.precoUnitario = produto.getPreco();
    }

    //GETTERS
    public int getId () { return id; }
    public Produto getProduto () { return produto; }
    public int getQuantidade () { return quantidade; }
    public double getPrecoUnitario () { return precoUnitario; }

    //SETTERS
    public void setId (int id) { this.id = id; }
    public void setProduto (Produto produto) { this.produto = produto; }
    public void setQuantidade (int quantidade) { this.quantidade = quantidade; }
    public void setPrecoUnitario (double precoUnitario) { this.precoUnitario = precoUnitario; }

    public double getSubTotal () { return quantidade * precoUnitario; }

}
