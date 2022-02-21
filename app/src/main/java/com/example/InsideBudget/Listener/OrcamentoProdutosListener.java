package com.example.InsideBudget.Listener;

import com.example.InsideBudget.Modelo.OrcamentoProduto;

import java.util.ArrayList;

public interface OrcamentoProdutosListener {
    void onRefreshOrcamentoProdutos(ArrayList<OrcamentoProduto> listaOrcamentoProdutos);
}
