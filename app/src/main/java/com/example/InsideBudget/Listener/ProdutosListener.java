package com.example.InsideBudget.Listener;

import com.example.InsideBudget.Modelo.Produto;
import java.util.ArrayList;

public interface ProdutosListener {
    void onRefreshListaTodosProdutos(ArrayList<Produto> listaProdutos);
    void onAddOrcamentoProduto();
    void onErroListaTodosProduto(String message);

}
