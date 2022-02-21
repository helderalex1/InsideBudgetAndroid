package com.example.InsideBudget.Modelo;

public class Funcao {
    private String item_name;
    private int user_id;

    public Funcao(String item_name, int user_id) {
        this.item_name = item_name;
        this.user_id = user_id;
    }

    public String getItem_Name() {
        return item_name;
    }
    public int getUser_id() {
        return user_id;
    }
    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }


}
