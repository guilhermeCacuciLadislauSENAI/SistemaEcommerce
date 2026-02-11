package com.example.ecommercesistema.model;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

public class Venda {
    private int id;
    private LocalDate data;
    private Cliente cliente;
    private List<ItemVenda> itens;

    public Venda() {
        this.itens = new ArrayList<>();
    }

    public Venda(LocalDate data, Cliente cliente, List<ItemVenda> itens) {
        this.data = data;
        this.cliente = cliente;
        this.itens = new ArrayList<>();
    }

    public Venda(int id, LocalDate data, Cliente cliente, List<ItemVenda> itens) {
        this.id = id;
        this.data = data;
        this.cliente = cliente;
        this.itens = new ArrayList<>();
    }

    public void adicionarItem (ItemVenda item) {
        this.itens.add(item);
    }

    public void removerItem (ItemVenda item) {
        this.itens.remove(item);
    }

    //GETTERS
    public int getId () { return id; }
    public LocalDate getData () { return data; }
    public Cliente getCliente () { return cliente; }
    public List<ItemVenda> getItens () { return itens; }

    //SETTERS
    public void setId (int id) { this.id = id; }
    public void setData (LocalDate data) { this.data = data; }
    public void setCliente (Cliente cliente) { this.cliente = cliente; }
    public void setItens (List<ItemVenda> itens) { this.itens = itens; }
}
