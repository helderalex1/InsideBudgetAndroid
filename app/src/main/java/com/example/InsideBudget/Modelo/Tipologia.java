package com.example.InsideBudget.Modelo;


//Modelo categoria
public class Tipologia {
    private int id;
    private String nome;

    //Construtor
    public Tipologia(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }
    public int getId() {
        return id;
    }
    public String getNome() {
        return nome;
    }
    public void setId(int id) {this.id = id;}
    public void setNome(String nome) { this.nome = nome;}
    @Override
    public String toString() {
        return nome;
    }
}
