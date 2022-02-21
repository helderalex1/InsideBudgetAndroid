package com.example.InsideBudget.Listener;

import com.example.InsideBudget.Modelo.Tipologia;

import java.util.ArrayList;

//Listener para a lista de utilizadores no admin
public interface TipologiaListener {

    void onRefreshListaTipologias(ArrayList<Tipologia> listaTipologia);
    void onErroListaTipologia(String message);
}
