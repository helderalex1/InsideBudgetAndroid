package com.example.InsideBudget.Listener;

import com.example.InsideBudget.Modelo.Cliente;
import com.example.InsideBudget.Modelo.Orcamento;
import com.example.InsideBudget.Modelo.OrcamentoProduto;
import com.example.InsideBudget.Modelo.Produto;

import java.util.ArrayList;

public interface FornecedorListener {
    void onGetProdutos(ArrayList<Produto> listaProduto);
    void onGetOrcamentos(ArrayList<Orcamento> listaOrcamento);
    void onGetClientes(ArrayList<Cliente> listaClientes);
    void onGetOrcamentoProdutos(int orcamentoProdutosSize, ArrayList<OrcamentoProduto> listaOrcamentoProduto);
}
