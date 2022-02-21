package com.example.InsideBudget.Listener;

import com.example.InsideBudget.Modelo.Funcao;

import java.util.ArrayList;

public interface FuncoesListener {
    void onRefreshListaFuncao(ArrayList<Funcao> listafuncoes);
    void onErroListaFuncao(String message);
}
