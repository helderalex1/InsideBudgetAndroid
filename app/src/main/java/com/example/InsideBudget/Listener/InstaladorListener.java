package com.example.InsideBudget.Listener;

import android.widget.ArrayAdapter;

import com.example.InsideBudget.Modelo.Cliente;
import com.example.InsideBudget.Modelo.Orcamento;
import com.example.InsideBudget.Modelo.OrcamentoProduto;

import java.util.ArrayList;

public interface InstaladorListener {
   void onGetOrcamentos(ArrayList<Orcamento> listaOrcamento);
   void onGetClientes(ArrayList<Cliente> listaClientes);
   void onGetOrcamentoProdutos(int orcamentoProdutosSize, ArrayList<OrcamentoProduto> listaOrcamentoProduto);
}
