package com.example.InsideBudget.Modelo;

//Modelo Orcamento
public class Orcamento {
    private int id;
    private String nome;
    private String descricao;
    private float total;
    private String data;
    private String uid;
    private int cliente_id;
    private int user_id;

    //Construtor
    public Orcamento(int id, String nome, String descricao, float total, String data,String uid, int cliente_id, int user_id) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.total = total;
        this.data = data;
        this.uid = uid;
        this.cliente_id = cliente_id;
        this.user_id = user_id;
    }

    public int getId() {
        return id;
    }

    public String getNome() {return nome;}

    public String getDescricao() { return descricao; }

    public float getTotal() {
        return total;
    }

    public String getData() {
        return data;
    }

    public String getUid() {
        return uid;
    }

    public int getCliente_id() {
        return cliente_id;
    }

    public int getUser_id() {return user_id;}


    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescricao(String descricao) {this.descricao = descricao;}

    public void setTotal(float total) {
        this.total = total;
    }

    public void setData(String data) { this.data = data; }

    public void setUid(String uid) { this.uid = uid; }

    public void setCliente_id(int cliente_id) {
        this.cliente_id = cliente_id;
    }

    public void setUser_id(int user_id) {this.user_id = user_id;}
}
