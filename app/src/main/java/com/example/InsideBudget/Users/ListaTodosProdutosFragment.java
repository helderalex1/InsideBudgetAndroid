package com.example.InsideBudget.Users;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.InsideBudget.Adapter.Users.ListaTodosProdutosAdapter;
import com.example.InsideBudget.Listener.ProdutosListener;
import com.example.InsideBudget.MenuMainActivity;
import com.example.InsideBudget.Modelo.Produto;
import com.example.InsideBudget.Modelo.SingletonProjeto;
import com.example.InsideBudget.Modelo.Tipologia;
import com.example.InsideBudget.R;
import com.example.InsideBudget.Users.Fornecedor.ListaMeusProdutosFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ListaTodosProdutosFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, ProdutosListener, BottomNavigationView.OnItemSelectedListener {
    private ListView lvlistTodosProdutos;
    private SwipeRefreshLayout swipeRefreshTodosProdutos;
    private ArrayList<Produto> searchListaTodosProdutos,searchTodosProdutos,spinnerTodosProdutos;
    private ArrayList<Tipologia> tipologias;
    private ArrayAdapter<Tipologia> spinnerAdapter;
    private SearchView searchView;
    private FloatingActionButton fab;
    private BottomNavigationView bottomNavigationView;
    private SharedPreferences sharedPreferences;
    private Spinner spinner;
    private FragmentManager fragmentManager;
    private String palavra;
    private int i = 0, spinnerPosition;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_fornecedor_lista_todos_produtos, container, false);
        setHasOptionsMenu(true);
        sharedPreferences = getContext().getSharedPreferences(MenuMainActivity.PREF_USER, Context.MODE_PRIVATE);
        String funcao = sharedPreferences.getString(MenuMainActivity.FUNCAO, null);
        System.out.println("---->99" + funcao);
        if(!funcao.equals("fornecedor")) {
            rootview = inflater.inflate(R.layout.fragment_instalador_lista_todos_produtos, container, false);
            //bottomNavigationView.setEnabled(false);

        }else{
            rootview = inflater.inflate(R.layout.fragment_fornecedor_lista_todos_produtos, container, false);
            fragmentManager = getParentFragmentManager();
            bottomNavigationView = rootview.findViewById(R.id.bottom_nav);
            bottomNavigationView.setOnItemSelectedListener(this);
        }
        lvlistTodosProdutos = rootview.findViewById(R.id.lvListaTodosProdutos);
        swipeRefreshTodosProdutos= rootview.findViewById(R.id.swipeRefreshTodosProdutos);
        searchView = rootview.findViewById(R.id.searchViewTodosProdutos);
        spinner = rootview.findViewById(R.id.spinnerTodosProdutos);
        procurarTodosProdudosNome();
        SingletonProjeto.getInstance(getContext()).setProdutosListener(this);
        SingletonProjeto.getInstance(getContext()).getAllProdutosAPI(getContext());
        SingletonProjeto.getInstance(getContext()).getAllDadosPessoaisAPI(getContext());
        SingletonProjeto.getInstance(getContext()).getAllTipologiaAPI(getContext());

        tipologias=SingletonProjeto.getInstance(getContext()).getTipologiasDB();
        spinnerAdapter = new ArrayAdapter<Tipologia>(getContext(),R.layout.support_simple_spinner_dropdown_item,tipologias);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        getActivity().setTitle("Todos Produtos");
        lvlistTodosProdutos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                Produto produto = (Produto) parent.getItemAtPosition(position);

                bundle.putInt("produto_id",produto.getId());
                bundle.putString("opcao","VisualizarProduto");
                Intent intent = new Intent(getContext(),DetalhesProduto.class);

                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerPosition = position+1;
                if(position == 0){

                    if(i >0) {
                        ListaTodosProdutosAdapter listaTodosProdutosAdapter = new ListaTodosProdutosAdapter(getContext(), searchListaTodosProdutos);
                        lvlistTodosProdutos.setAdapter(listaTodosProdutosAdapter);
                        System.out.println("-->pesquisa1");
                        i=1;
                        return;
                    }
                    i=1;
                    return;
                }

                spinnerTodosProdutos = new ArrayList<>();
                if (palavra == null){
                    for (Produto produto: searchListaTodosProdutos) {
                        if(produto.getTipologia_id() ==position+1){
                            spinnerTodosProdutos.add(produto);
                        }
                    }

                    System.out.println("-->pesquisa2");
                    ListaTodosProdutosAdapter listaTodosProdutosAdapter = new ListaTodosProdutosAdapter(getContext(), spinnerTodosProdutos);
                    lvlistTodosProdutos.setAdapter(listaTodosProdutosAdapter);
                }else{
                   // searchTodosProdutos.clear();
                    if (searchListaTodosProdutos != null) {
                        for (Produto produto : searchListaTodosProdutos) {
                            if (produto.getTipologia_id() ==position+1 && produto.getNome().toLowerCase().contains(palavra.toLowerCase()) ) {
                                spinnerTodosProdutos.add(produto);
                            }
                        }

                        System.out.println("-->pesquisa3");
                        ListaTodosProdutosAdapter listaTodosProdutosAdapter = new ListaTodosProdutosAdapter(getContext(), spinnerTodosProdutos);
                        lvlistTodosProdutos.setAdapter(listaTodosProdutosAdapter);

                    }
                }
                Toast.makeText(getContext(),tipologias.get(position).toString(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        swipeRefreshTodosProdutos.setOnRefreshListener(this);

        return rootview;
    }
    private void procurarTodosProdudosNome()
    {
        searchTodosProdutos = new ArrayList<>();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                palavra = s;
                searchTodosProdutos.clear();
                if(s == null && searchListaTodosProdutos != null){

                    System.out.println("-->pesquisa4");
                    ListaTodosProdutosAdapter listaTodosProdutosAdapter = new ListaTodosProdutosAdapter(getContext(), searchListaTodosProdutos);
                    lvlistTodosProdutos.setAdapter(listaTodosProdutosAdapter);
                    return true;
                }
                if(spinnerPosition == 1){
                    if (searchListaTodosProdutos != null) {
                        for (Produto produto : searchListaTodosProdutos) {
                            if (produto.getNome().toLowerCase().contains(s.toLowerCase())) {
                                searchTodosProdutos.add(produto);
                            }
                        }

                        System.out.println("-->pesquisa5");
                        ListaTodosProdutosAdapter listaTodosProdutosAdapter = new ListaTodosProdutosAdapter(getContext(), searchTodosProdutos);
                        lvlistTodosProdutos.setAdapter(listaTodosProdutosAdapter);
                    }
                    return true;
                }else{
                    if (searchListaTodosProdutos != null) {
                        for (Produto produto : searchListaTodosProdutos) {
                            if (produto.getNome().toLowerCase().contains(s.toLowerCase()) && produto.getTipologia_id() == spinnerPosition) {
                                searchTodosProdutos.add(produto);
                            }
                        }

                        System.out.println("-->pesquisa6");
                        ListaTodosProdutosAdapter listaTodosProdutosAdapter = new ListaTodosProdutosAdapter(getContext(), searchTodosProdutos);
                        lvlistTodosProdutos.setAdapter(listaTodosProdutosAdapter);

                    }
                    return true;
                }
            }
        });
    }
    @Override
    public void onRefresh() {
        System.out.println("-->RefreshListaUserFragment1");
       /* if(as !=null){
            System.out.println("-->12345");
            ArrayList<User> as1 = new ArrayList<>();
            for (User user: as) {
                if(user.getStatus() == 9) {
                    as1.add(user);
                }
            }
            as = as1;
            lvListaUsersRegistosEspera.setAdapter(new ListaUsersRegistosEsperaAdapter(getContext(),as));
        }*/
        SingletonProjeto.getInstance(getContext()).getAllProdutosAPI(getContext());
        spinner.setSelection(0);
        searchView.setQuery("",false);
        searchView.clearFocus();
        swipeRefreshTodosProdutos.setRefreshing(false);
    }


    @Override
    public void onRefreshListaTodosProdutos(ArrayList<Produto> listaProdutos) {
        if (listaProdutos != null) {
            searchListaTodosProdutos = listaProdutos;

            SingletonProjeto.getInstance(getContext()).getAllDadosPessoaisAPI(getContext());

            lvlistTodosProdutos.setAdapter(new ListaTodosProdutosAdapter(getContext(), listaProdutos));
        }
        // SingletonProjeto.getInstance(getContext()).getAllUsersAPI(getContext());
    }

    @Override
    public void onAddOrcamentoProduto() {

    }

    @Override
    public void onErroListaTodosProduto(String message) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch(item.getItemId()){
            case R.id.nav_fornecedor_todos_produtos:
                fragment = new ListaTodosProdutosFragment();
                System.out.println("-->todosprodutos");
                break;
            case R.id.nav_fornecedor_meus_produtos:
                fragment = new ListaMeusProdutosFragment();
                System.out.println("-->meusprodutos");
                break;
        }
        if(fragment != null){
            fragmentManager.beginTransaction().replace(R.id.contentFragment,fragment).commit();
        }
        return true;
    }

}