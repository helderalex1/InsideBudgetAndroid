package com.example.InsideBudget.Users.Instalador;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.InsideBudget.Adapter.Users.Instalador.ListaMeusClientesAdapter;
import com.example.InsideBudget.Listener.ClientesListener;
import com.example.InsideBudget.MenuMainActivity;
import com.example.InsideBudget.Modelo.Cliente;
import com.example.InsideBudget.Modelo.SingletonProjeto;
import com.example.InsideBudget.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class ListaMeusClientesFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, ClientesListener{
    private ListView lvListaMeusClientes;
    private SwipeRefreshLayout swipeRefreshMeusClientes;
    private ArrayList<Cliente> searchListaClientes;
    private SearchView searchView;
    private SharedPreferences sharedPreferences;
    private FloatingActionButton fabAddCliente;
    private FragmentManager fragmentManager;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_lista_meus_clientes, container, false);
        setHasOptionsMenu(true);
        lvListaMeusClientes = rootview.findViewById(R.id.lvListaMeusClientes);
        swipeRefreshMeusClientes= rootview.findViewById(R.id.swipeRefreshMeusClientes);
        searchView = rootview.findViewById(R.id.searchViewMeusClientes);
        fabAddCliente= rootview.findViewById(R.id.fab_addcliente);

        lvListaMeusClientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cliente cliente= (Cliente) parent.getItemAtPosition(position);
                System.out.println("-->cliente " +cliente.getId());
                Intent intent = new Intent(getContext(), DetalhesCliente.class);
                intent.putExtra(DetalhesCliente.DETALHES_Cliente,cliente.getId());
                startActivity(intent);
            }
        });

        fabAddCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("opcao", 1);
                Intent intent = new Intent(getContext(),CriarAtualizarCliente.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        swipeRefreshMeusClientes.setOnRefreshListener(this);
        procurarMeusClientesNome();
        SingletonProjeto.getInstance(getContext()).setClientesListener(this);
        SingletonProjeto.getInstance(getContext()).getMeusClientesAPI(getContext());
        return rootview;
    }
    private void procurarMeusClientesNome()
    {

        ArrayList<Cliente> searchCliente = new ArrayList<>();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                searchCliente.clear();
                if (searchListaClientes != null) {
                    for (Cliente cliente : searchListaClientes) {
                        if (cliente.getNome().toLowerCase().contains(s.toLowerCase())) {
                            searchCliente.add(cliente);
                        }
                    }
                    ListaMeusClientesAdapter listaMeusClientesAdapter = new ListaMeusClientesAdapter(getContext(), searchCliente);
                    lvListaMeusClientes.setAdapter(listaMeusClientesAdapter);

                }
                return true;
            }
        });
    }

    @Override
    public void onRefresh() {
        SingletonProjeto.getInstance(getContext()).getMeusClientesAPI(getContext());

        System.out.println("-->sizeacdb"+SingletonProjeto.getInstance(getContext()).getMeusClientesDB().size());
        searchView.setQuery("",true);
        searchView.clearFocus();
        swipeRefreshMeusClientes.setRefreshing(false);
    }



    @Override
    public void onRefreshListaMeusClientes(ArrayList<Cliente> listaClientes) {

        if(listaClientes !=null){
            searchListaClientes = listaClientes;

            System.out.println("-->sizeac"+listaClientes.size());
            lvListaMeusClientes.setAdapter(new ListaMeusClientesAdapter(getContext(),listaClientes));
        }
    }

    @Override
    public void onAddCliente() {
        swipeRefreshMeusClientes.setOnRefreshListener(this);
    }

    @Override
    public void onEditCliente() {

    }

    @Override
    public void onErroListaMeusClientes(String message) {

    }

 /*   @Override
    public void onResume() {
        ArrayList<Cliente> cliente = SingletonProjeto.getInstance(getContext()).getMeusClientesDB();
        lvListaMeusClientes.setAdapter(new ListaMeusClientesAdapter(getContext(),cliente));
        searchView.setQuery("",true);
        searchView.clearFocus();
        super.onResume();
    }*/
}