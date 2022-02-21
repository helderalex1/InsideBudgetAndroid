package com.example.InsideBudget.Users.Admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.InsideBudget.Adapter.Users.Admin.ListaUsersComAcessoAdapter;
import com.example.InsideBudget.Listener.DadosPessoaisListener;
import com.example.InsideBudget.Listener.FuncoesListener;
import com.example.InsideBudget.Listener.UsersListener;
import com.example.InsideBudget.Modelo.DadosPessoais;
import com.example.InsideBudget.Modelo.Funcao;
import com.example.InsideBudget.Modelo.SingletonProjeto;
import com.example.InsideBudget.Modelo.User;
import com.example.InsideBudget.R;

import java.util.ArrayList;

public class ListaUsersComAcessoFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, UsersListener, FuncoesListener, DadosPessoaisListener {
    private ListView lvListaUsersComAcesso;
    private SwipeRefreshLayout swipe;
    private SearchView searchView;
    private ArrayList<User> searchListaUsersComAcesso;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_lista_users_com_acesso, container, false);
        setHasOptionsMenu(true);
        lvListaUsersComAcesso = rootview.findViewById(R.id.lvListaUserComAcesso);
        swipe= rootview.findViewById(R.id.swipeRefreshUsersComAcesso);
        searchView = (SearchView) rootview.findViewById(R.id.searchViewUsersComAcesso);
        lvListaUsersComAcesso.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
             /*   User userExist = (User) parent.getItemAtPosition(position);
                Intent intent = new Intent(getContext(), DetalhesLivroActivity.class);
                intent.putExtra(DETALHE_LIVROS, temLivro.getId());
                startActivityForResult(intent,DetalhesLivroActivity.EDITAR);*/
            }
        });

        swipe.setOnRefreshListener(this);
        procurarUsersComAcessoUsername();
        SingletonProjeto.getInstance(getContext()).setUsersListener(this);
        SingletonProjeto.getInstance(getContext()).setDadosPessoaisListener(this);
        SingletonProjeto.getInstance(getContext()).getAllFuncoesAdminAPI(getContext());
        SingletonProjeto.getInstance(getContext()).getAllDadosPessoaisAPI(getContext());
        SingletonProjeto.getInstance(getContext()).getAllUsersAPI(getContext());
        return rootview;
    }
    private void procurarUsersComAcessoUsername()
    {

        ArrayList<User> searchUsers = new ArrayList<>();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                searchUsers.clear();
                if(searchListaUsersComAcesso != null) {
                    for (User user : searchListaUsersComAcesso) {
                        if (user.getUsername().toLowerCase().contains(s.toLowerCase())) {
                            searchUsers.add(user);
                        }
                    }
                    ListaUsersComAcessoAdapter listaUsersComAcessoAdapter = new ListaUsersComAcessoAdapter(getContext(), searchUsers);
                    lvListaUsersComAcesso.setAdapter(listaUsersComAcessoAdapter);
                }
                return true;
            }
        });
    }
    @Override
    public void onRefresh() {
        SingletonProjeto.getInstance(getContext()).getAllProdutosAPI(getContext());
        searchView.setQuery("",true);
        searchView.clearFocus();
        SingletonProjeto.getInstance(getContext()).getAllUsersAPI(getContext());
        swipe.setRefreshing(false);
    }

    @Override
    public void onRefreshListaUsers(ArrayList<User> listaAllUsers) {
            if (listaAllUsers != null) {
                ArrayList<User> searchUsers = new ArrayList<>();
                for (User user : listaAllUsers) {
                    if (user.getStatus() == 10) {
                        searchUsers.add(user);
                    }
                }
                searchListaUsersComAcesso = searchUsers;
                lvListaUsersComAcesso.setAdapter(new ListaUsersComAcessoAdapter(getContext(), searchUsers));
            }

    }

    @Override
    public void onErroListUsers(String message) {

    }

    @Override
    public void onRefreshListaFuncao(ArrayList<Funcao> listafuncoes) {

    }

    @Override
    public void onErroListaFuncao(String message) {

    }

    @Override
    public void onRefreshListaDadospessoais(ArrayList<DadosPessoais> listaDadospessoais) {

    }

    @Override
    public void onErroListaDadosessoais(String message) {

    }
}