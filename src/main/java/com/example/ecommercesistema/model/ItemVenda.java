package com.example.ecommercesistema.model;

public class ItemVenda {
    private int id;
    private Venda novaVenda;
    private Produto produto;
    private int quantidade;
    private double precoUnitario;
    private double precoTotal;

    public ItemVenda () {}

    public ItemVenda (Venda novaVenda, Produto produto, int quantidade, double precoUnitario, double precoTotal) {
        this.novaVenda = novaVenda;
        this.produto = produto;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
        this.precoTotal = precoTotal;
    }

    public ItemVenda (int id, Venda novaVenda, Produto produto, int quantidade, double precoUnitario, double precoTotal) {
        this.id = id;
        this.produto = produto;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
        this.precoTotal = precoTotal;
    }

    //GETTERS
    public int getId () { return id; }
    public Venda getNovaVenda () { return novaVenda; }
    public Produto getProduto () { return produto; }
    public int getQuantidade () { return quantidade; }
    public double getPrecoUnitario () { return precoUnitario; }
    public double getPrecoTotal () { return precoTotal; }

    //SETTERS
    public void setId (int id) { this.id = id; }
    public void setNovaVenda (Venda novaVenda) { this.novaVenda = novaVenda; }
    public void setProduto (Produto produto) { this.produto = produto; }
    public void setQuantidade (int quantidade) { this.quantidade = quantidade; }
    public void setPrecoUnitario (double precoUnitario) { this.precoUnitario = precoUnitario; }
    public void setPrecoTotal (double precoTotal) { this.precoTotal = precoTotal; }

}
