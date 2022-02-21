package com.example.InsideBudget.Modelo;

public class DadosPessoais {
    private int id;
    private String nomecompleto;
    private String empresa;
    private String pais;
    private String cidade;
    private String morada;
    private int telefone;
    private int user_id;
    //construtor
    public DadosPessoais(int id, String nomecompleto, String empresa, String pais, String cidade, String morada, int telefone, int user_id) {
        this.id = id;
        this.nomecompleto = nomecompleto;
        this.empresa = empresa;
        this.pais = pais;
        this.cidade = cidade;
        this.morada = morada;
        this.telefone = telefone;
        this.user_id = user_id;
    }

    public int getId() {
        return id;
    }
    public String getNomecompleto() {
        return nomecompleto;
    }
    public String getEmpresa (){
        return empresa;
    }
    public String getPais() {
        return pais;
    }
    public String getCidade() {
        return cidade;
    }
    public String getMorada() {
        return morada;
    }
    public int getTelefone() {
        return telefone;
    }
    public int getUser_id() {
        return user_id;
    }


    public void setId(int id){
        this.id = id;
    }
    public void setNomecompleto(String nomecompleto){
        this.nomecompleto = nomecompleto;
    }
    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }
    public void setPais(String pais) {
        this.pais = pais;
    }
    public void setCidade(String cidade) {
        this.cidade = cidade;
    }
    public void setMorada(String morada) {
        this.morada = morada;
    }
    public void setTelefone(int telefone) {
        this.telefone = telefone;
    }
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

}
