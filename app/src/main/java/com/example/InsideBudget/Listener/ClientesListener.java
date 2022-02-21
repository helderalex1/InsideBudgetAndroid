package com.example.InsideBudget.Listener;

import com.example.InsideBudget.Modelo.Cliente;

import java.util.ArrayList;

public interface ClientesListener {
    void onRefreshListaMeusClientes(ArrayList<Cliente> listaClientes);
    void onAddCliente();
    void onEditCliente();
    void onErroListaMeusClientes(String message);

}
