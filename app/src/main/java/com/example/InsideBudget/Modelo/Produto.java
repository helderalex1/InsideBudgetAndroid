package com.example.InsideBudget.Modelo;


//Modelo Produto
public class Produto {
    private int id;
    private String nome;
    private String referencia;
    private String descricao;
    private float preco;
    private int fornecedor_id;
    private int tipologia_id;
   //Construtor


    public Produto(int id, String nome, String referencia, String descricao, float preco, int fornecedor_id, int tipologia_id) {
        this.id = id;
        this.nome = nome;
        this.referencia = referencia;
        this.descricao = descricao;
        this.preco = preco;
        this.fornecedor_id = fornecedor_id;
        this.tipologia_id = tipologia_id;
    }

    //Funções GET


    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getReferencia() {
        return referencia;
    }

    public String getDescricao() {
        return descricao;
    }

    public float getPreco() {
        return preco;
    }

    public int getFornecedor_id() {
        return fornecedor_id;
    }

    public int getTipologia_id() {return tipologia_id;}

    //Funções SET


    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }
}
