package com.example.InsideBudget.Listener;

public interface LoginListener {
    void onValidateLogin(String token, String username);
    void onErroLogin(String erro);

}
