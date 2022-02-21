package com.example.InsideBudget.Modelo;

public class User {
    private int id;
    private String username;
    private String email;
    private int status;

    public User(int id, String username, String email, int status) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.status = status;
    }

    public int getId() {
        return id;
    }
    public String getUsername() { return username;}
    public String getEmail (){return email;}
    public int getStatus() {return status;}

    public void setId(int id){
        this.id = id;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public void setStatus(int status) {
        this.status = status;
    }
}
