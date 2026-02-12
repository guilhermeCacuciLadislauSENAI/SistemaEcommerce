package com.example.ecommercesistema.model;

public class Cliente {
    private int id;
    private String nome;
    private String cpf;
    private String telefone;
    private String email;
    private String endereco;

    public Cliente () {}

    public Cliente (String nome, String cpf, String telefone, String email, String endereco) {
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
        this.endereco = endereco;
    }

    public Cliente (int id, String nome, String cpf, String telefone) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
        this.endereco = endereco;
    }

    // GETTERS
    public int getId () { return id; }
    public String getNome () { return nome; }
    public String getCpf () { return cpf; }
    public String getTelefone () { return telefone; }
    public String getEmail () { return email; }
    public String getEndereco () { return endereco; }

    //SETTERS
    public void setId (int id){ this.id = id;}
    public void setNome (String nome) { this.nome = nome; }
    public void setCpf (String cpf) { this.cpf = cpf; }
    public void setTelefone (String telefone) { this.telefone = telefone; }
    public void setEmail (String email) { this.email = email; }
    public void setEndereco (String endereco) { this.endereco = endereco; }

    @Override
    public String toString() {
        return nome;
    }
}
