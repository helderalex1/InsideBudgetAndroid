package com.example.InsideBudget.Users.Fornecedor;

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

import com.example.InsideBudget.Adapter.Users.Fornecedor.ListaMeusProdutosAdapter;
import com.example.InsideBudget.Adapter.Users.ListaTodosProdutosAdapter;
import com.example.InsideBudget.Listener.ProdutosListener;
import com.example.InsideBudget.MenuMainActivity;
import com.example.InsideBudget.Modelo.Produto;
import com.example.InsideBudget.Modelo.SingletonProjeto;
import com.example.InsideBudget.Modelo.Tipologia;
import com.example.InsideBudget.R;
import com.example.InsideBudget.Users.DetalhesProduto;
import com.example.InsideBudget.Users.ListaTodosProdutosFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ListaMeusProdutosFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, ProdutosListener, BottomNavigationView.OnItemSelectedListener{
    private ListView lvlistMeusProdutos;
    private SwipeRefreshLayout swipeRefreshMeusProdutos;
    private ArrayList<Produto> searchListaMeusProdutos, searchMeusProdutos,spinnerMeusProdutos;
    private SearchView searchView;
    private FloatingActionButton fab;
    private BottomNavigationView bottomNavigationView;
    private SharedPreferences sharedPreferences;
    private int user_id = 0;
    private Spinner spinner;
    private ArrayList<Tipologia> tipologias;
    private ArrayAdapter<Tipologia> spinnerAdapter;
    private String palavra;
    private int i = 0, spinnerPosition;
    private  FragmentManager fragmentManager;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_fornecedor_lista_meus_produtos, container, false);
        setHasOptionsMenu(true);
        swipeRefreshMeusProdutos= rootview.findViewById(R.id.swipeRefreshMeusProdutos);
        //fab= rootview.findViewById(R.id.fabadd);

        bottomNavigationView = rootview.findViewById(R.id.bottom_nav);
        sharedPreferences = getContext().getSharedPreferences(MenuMainActivity.PREF_USER, Context.MODE_PRIVATE);
        String funcao = sharedPreferences.getString(MenuMainActivity.FUNCAO, null);
        user_id = Integer.parseInt(sharedPreferences.getString(MenuMainActivity.ID, null));
        searchView =  rootview.findViewById(R.id.searchViewMeusProdutos);
        spinner = rootview.findViewById(R.id.spinnerMeusProdutos);
        fab = rootview.findViewById(R.id.fab_fonercedorAddProduto);

        bottomNavigationView.setSelectedItemId(R.id.nav_fornecedor_meus_produtos);
            bottomNavigationView.setOnItemSelectedListener(this);
        getActivity().setTitle("Meus Produtos");
            fragmentManager = getParentFragmentManager();

        SingletonProjeto.getInstance(getContext()).setProdutosListener(this);
        SingletonProjeto.getInstance(getContext()).getAllDadosPessoaisAPI(getContext());
        SingletonProjeto.getInstance(getContext()).getAllTipologiaAPI(getContext());
        SingletonProjeto.getInstance(getContext()).getAllProdutosAPI(getContext());

        lvlistMeusProdutos = rootview.findViewById(R.id.lvListaMeusProdutos);
        lvlistMeusProdutos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                Produto produto = (Produto) parent.getItemAtPosition(position);

                bundle.putInt("produto_id",produto.getId());
                bundle.putString("opcao","EditProdutoFornecedor");
                Intent intent = new Intent(getContext(),DetalhesProduto.class);

                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                //bundle.putInt("produto_id",produto.getId());
                bundle.putString("opcao","AddProdutoFornecedor");
                Intent intent = new Intent(getContext(), DetalhesProduto.class);

                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        tipologias=SingletonProjeto.getInstance(getContext()).getTipologiasDB();
        spinnerAdapter = new ArrayAdapter<Tipologia>(getContext(),R.layout.support_simple_spinner_dropdown_item,tipologias);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerPosition = position+1;
                if(position == 0){

                    if(i >0) {
                        ListaTodosProdutosAdapter listaTodosProdutosAdapter = new ListaTodosProdutosAdapter(getContext(), searchListaMeusProdutos);
                        lvlistMeusProdutos.setAdapter(listaTodosProdutosAdapter);
                        System.out.println("-->pesquisa1");
                        i=1;
                        return;
                    }
                    i=1;
                    return;
                }

                spinnerMeusProdutos = new ArrayList<>();
                if (palavra == null){
                    for (Produto produto: searchListaMeusProdutos) {
                        if(produto.getTipologia_id() ==position+1){
                            spinnerMeusProdutos.add(produto);
                        }
                    }

                    System.out.println("-->pesquisa2");
                    ListaMeusProdutosAdapter listaMeusProdutosAdapter = new ListaMeusProdutosAdapter(getContext(), spinnerMeusProdutos);
                    lvlistMeusProdutos.setAdapter(listaMeusProdutosAdapter);
                }else{
                    // searchTodosProdutos.clear();
                    if (searchListaMeusProdutos != null) {
                        for (Produto produto : searchListaMeusProdutos) {
                            if (produto.getTipologia_id() ==position+1 && produto.getNome().toLowerCase().contains(palavra.toLowerCase()) ) {
                                spinnerMeusProdutos.add(produto);
                            }
                        }

                        System.out.println("-->pesquisa3");
                        ListaMeusProdutosAdapter listaMeusProdutosAdapter = new ListaMeusProdutosAdapter(getContext(), spinnerMeusProdutos);
                        lvlistMeusProdutos.setAdapter(listaMeusProdutosAdapter);

                    }
                }
                Toast.makeText(getContext(),tipologias.get(position).toString(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        procurarMeusProdudosNome();
        bottomNavigationView.setSelected(true);
        swipeRefreshMeusProdutos.setOnRefreshListener(this);
        return rootview;
    }

    private void procurarMeusProdudosNome()
    {
        searchMeusProdutos = new ArrayList<>();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                palavra = s;
                searchMeusProdutos.clear();
                if(s == null && searchListaMeusProdutos != null){

                    System.out.println("-->pesquisa4");
                    ListaMeusProdutosAdapter listaMeusProdutosAdapter = new ListaMeusProdutosAdapter(getContext(), searchListaMeusProdutos);
                    lvlistMeusProdutos.setAdapter(listaMeusProdutosAdapter);
                    return true;
                }
                if(spinnerPosition == 1){
                    if (searchListaMeusProdutos != null) {
                        for (Produto produto : searchListaMeusProdutos) {
                            if (produto.getNome().toLowerCase().contains(s.toLowerCase())) {
                                searchMeusProdutos.add(produto);
                            }
                        }

                        System.out.println("-->pesquisa5");
                        ListaMeusProdutosAdapter listaMeusProdutosAdapter = new ListaMeusProdutosAdapter(getContext(), searchMeusProdutos);
                        lvlistMeusProdutos.setAdapter(listaMeusProdutosAdapter);
                    }
                    return true;
                }else{
                    if (searchListaMeusProdutos != null) {
                        for (Produto produto : searchListaMeusProdutos) {
                            if (produto.getNome().toLowerCase().contains(s.toLowerCase()) && produto.getTipologia_id() == spinnerPosition) {
                                searchMeusProdutos.add(produto);
                            }
                        }

                        System.out.println("-->pesquisa6");
                        ListaMeusProdutosAdapter listaMeusProdutosAdapter = new ListaMeusProdutosAdapter(getContext(), searchMeusProdutos);
                        lvlistMeusProdutos.setAdapter(listaMeusProdutosAdapter);

                    }
                    return true;
                }
            }
        });
    }

    @Override
    public void onRefresh() {
        System.out.println("-->RefreshListaUserFragment1");
        SingletonProjeto.getInstance(getContext()).getAllProdutosAPI(getContext());
        searchView.setQuery("",true);
        searchView.clearFocus();
        swipeRefreshMeusProdutos.setRefreshing(false);
    }

    @Override
    public void onRefreshListaTodosProdutos(ArrayList<Produto> listaProdutos) {
     /*   if (listaProdutos != null) {
            lvlistMeusProdutos.setAdapter(new ListaMeusProdutosAdapter(getContext(), listaProdutos));
        }*/
        if (listaProdutos != null) {
            ArrayList<Produto> meusProdutos = new ArrayList<>();
            for (Produto produto : listaProdutos) {
                if (produto.getFornecedor_id() == user_id) {
                    meusProdutos.add(produto);
                }
            }

            searchListaMeusProdutos = meusProdutos;
            lvlistMeusProdutos.setAdapter(new ListaMeusProdutosAdapter(getContext(), meusProdutos));
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

                bottomNavigationView.setSelectedItemId(R.id.nav_fornecedor_meus_produtos);
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