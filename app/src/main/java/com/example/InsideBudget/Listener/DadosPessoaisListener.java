package com.example.InsideBudget.Listener;
import com.example.InsideBudget.Modelo.DadosPessoais;

import java.util.ArrayList;

public interface DadosPessoaisListener {
    void onRefreshListaDadospessoais(ArrayList<DadosPessoais> listaDadospessoais);
    void onErroListaDadosessoais(String message);
}
