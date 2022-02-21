package com.example.InsideBudget.Modelo;

//Modelo ProdutoOrcamento
public class OrcamentoProduto {
    private int id;
    private int orcamento_id;
    private int produto_id;
    private int quantidade;

    //Construtor

    public OrcamentoProduto(int id, int orcamento_id, int produto_id, int quantidade) {
        this.id = id;
        this.orcamento_id = orcamento_id;
        this.produto_id = produto_id;
        this.quantidade = quantidade;
    }

    public int getId() {
        return id;
    }

    public int getOrcamento_Id() {
        return orcamento_id;
    }

    public int getProduto_Id() { return produto_id;}

    public int getQuantidade() {return quantidade;}

    public void setId(int id) {
        this.id = id;
    }

    public void setOrcamento_Id(int orcamento_id) {
        this.orcamento_id = orcamento_id;
    }

    public void setProduto_Id(int produto_id) {
        this.produto_id = produto_id;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
