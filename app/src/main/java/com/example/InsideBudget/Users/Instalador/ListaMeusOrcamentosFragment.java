package com.example.InsideBudget.Users.Instalador;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.InsideBudget.Adapter.Users.Instalador.ListaMeusOrcamentosAdapter;
import com.example.InsideBudget.Listener.OrcamentosListener;
import com.example.InsideBudget.Modelo.Cliente;
import com.example.InsideBudget.Modelo.Orcamento;
import com.example.InsideBudget.Modelo.SingletonProjeto;
import com.example.InsideBudget.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ListaMeusOrcamentosFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, OrcamentosListener {
    private ListView lvListaMeusOrcamentos;
    private SwipeRefreshLayout swipeRefreshMeusOrcamentos;
    private ArrayList<Orcamento> searchListaOrcamentos;
    private ArrayList<Cliente> listaCliente;
    private FragmentManager fragmentManager ;
    private String nomeCliente;
    private SearchView searchView;
    private FloatingActionButton fabMenu,fabQr,fabAdd;
    public static String UID = "UID";
    boolean isOpen;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_lista_meus_orcamentos, container, false);
        setHasOptionsMenu(true);
        lvListaMeusOrcamentos = rootview.findViewById(R.id.lvListaMeusOrcamentos);
        swipeRefreshMeusOrcamentos= rootview.findViewById(R.id.swipeRefreshMeusOrcamentos);
        searchView = rootview.findViewById(R.id.searchViewMeusOrcamentos);
        listaCliente = SingletonProjeto.getInstance(getContext()).getMeusClientesDB();
        fabQr = rootview.findViewById(R.id.fab_qrcode);

        SingletonProjeto.getInstance(getContext()).setOrcamentosListener(this);
        SingletonProjeto.getInstance(getContext()).getMeusOrcamentosAPI(getContext());
        lvListaMeusOrcamentos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Orcamento orcamento= (Orcamento) parent.getItemAtPosition(position);

                Intent intent = new Intent(getContext(), DetalhesOrcamento.class);
                intent.putExtra(DetalhesOrcamento.DETALHES_Orcamento,orcamento.getId());
                startActivity(intent);
            }
        });


        fabQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ScanActivity.class);
                startActivity(intent);
            }
        });

        procurarMeusOrcamentosNome();
        swipeRefreshMeusOrcamentos.setOnRefreshListener(this);
        return rootview;
    }

    private void procurarMeusOrcamentosNome()
    {

        ArrayList<Orcamento> searchOrcamentos = new ArrayList<>();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                searchOrcamentos.clear();

                if (searchListaOrcamentos != null) {
                    for (Orcamento orcamento : searchListaOrcamentos) {
                        if (orcamento.getNome().toLowerCase().contains(s.toLowerCase())) {
                            searchOrcamentos.add(orcamento);
                        }
                    }
                    ListaMeusOrcamentosAdapter listaMeusOrcamentosAdapter = new ListaMeusOrcamentosAdapter(getContext(), searchOrcamentos);
                    lvListaMeusOrcamentos.setAdapter(listaMeusOrcamentosAdapter);
                }
                return true;
            }
        });
    }

    @Override
    public void onRefresh() {
        SingletonProjeto.getInstance(getContext()).getMeusOrcamentosAPI(getContext());
        searchView.setQuery("",false);
        searchView.clearFocus();
        swipeRefreshMeusOrcamentos.setRefreshing(false);
    }



    @Override
    public void onRefreshListaMeusOrcamentos(ArrayList<Orcamento> listaOrcamentos) {
        if(listaOrcamentos !=null){
            System.out.println("-->sizeab:"+listaOrcamentos.size());
            searchListaOrcamentos = listaOrcamentos;
            lvListaMeusOrcamentos.setAdapter(new ListaMeusOrcamentosAdapter(getContext(),listaOrcamentos));

        }
    }

    @Override
    public void onAddOrcamento() {

    }

    @Override
    public void onEditOrcamento() {

    }


    @Override
    public void onErroListaMeusOrcamentos(String message) {

    }
}