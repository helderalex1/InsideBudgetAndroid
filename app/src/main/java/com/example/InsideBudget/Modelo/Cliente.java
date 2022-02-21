package com.example.InsideBudget.Modelo;


//Modelo cliente
public class Cliente {
    private int id;
    private String nome;
    private String empresa;
    private String morada;
    private int nif;
    private int telefone;
    private String email;
    private int user_id;

    public Cliente(int id, String nome, String empresa,String morada, int nif, int telefone, String email, int user_id) {
        this.id = id;
        this.nome = nome;
        this.empresa = empresa;
        this.morada = morada;
        this.nif = nif;
        this.telefone = telefone;
        this.email = email;
        this.user_id = user_id;
    }

    public int getId() {
        return id;
    }
    public String getNome() {
        return nome;
    }
    public String getEmpresa() {return empresa;}
    public String getMorada() {return morada;}
    public int getNif() {
        return nif;
    }
    public int getTelefone() {
        return telefone;
    }
    public String getEmail() {
        return email;
    }
    public int getUser_id() {
        return user_id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setEmpresa(String empresa) {this.empresa = empresa;}
    public void setMorada(String morada) {this.morada = morada;}
    public void setNif(int nif) {
        this.nif = nif;
    }
    public void setTelefone(int telefone) {
        this.telefone = telefone;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
