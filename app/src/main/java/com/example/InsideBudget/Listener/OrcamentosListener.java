package com.example.InsideBudget.Listener;

import com.example.InsideBudget.Modelo.Orcamento;

import java.util.ArrayList;

public interface OrcamentosListener {
    void onRefreshListaMeusOrcamentos(ArrayList<Orcamento> listaOrcamentos);
    void onAddOrcamento();
    void onEditOrcamento();
    void onErroListaMeusOrcamentos(String message);

}
