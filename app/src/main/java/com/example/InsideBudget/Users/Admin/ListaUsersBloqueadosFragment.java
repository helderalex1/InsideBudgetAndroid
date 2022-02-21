package com.example.InsideBudget.Users.Admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.InsideBudget.Adapter.Users.Admin.ListaUsersBloqueadosAdapter;
import com.example.InsideBudget.Listener.DadosPessoaisListener;
import com.example.InsideBudget.Listener.FuncoesListener;
import com.example.InsideBudget.Listener.UsersListener;
import com.example.InsideBudget.Modelo.DadosPessoais;
import com.example.InsideBudget.Modelo.Funcao;
import com.example.InsideBudget.Modelo.SingletonProjeto;
import com.example.InsideBudget.Modelo.User;
import com.example.InsideBudget.R;

import java.util.ArrayList;

public class ListaUsersBloqueadosFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, UsersListener, FuncoesListener, DadosPessoaisListener{
    private ListView lvListaUsersBanidos;
    private SwipeRefreshLayout swipeRefrechUsersBanidos;
    private SearchView searchView;
    private ArrayList<User> searchListaUsersBanidos;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_lista_users_bloqueados, container, false);
        setHasOptionsMenu(true);
        lvListaUsersBanidos = rootview.findViewById(R.id.lvListaUsersBanidos);
        swipeRefrechUsersBanidos= rootview.findViewById(R.id.swipeRefrechUsersBanidos);
        searchView = rootview.findViewById(R.id.searchViewUsersBanidos);

/*        lvListaUsersBanidos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
             /*   User userExist = (User) parent.getItemAtPosition(position);
                Intent intent = new Intent(getContext(), DetalhesLivroActivity.class);
                intent.putExtra(DETALHE_LIVROS, temLivro.getId());
                startActivityForResult(intent,DetalhesLivroActivity.EDITAR);
            }
        });
*/

        swipeRefrechUsersBanidos.setOnRefreshListener(this);
        procurarUsersBanidosUsername();
        SingletonProjeto.getInstance(getContext()).setUsersListener(this);
        SingletonProjeto.getInstance(getContext()).setDadosPessoaisListener(this);
        SingletonProjeto.getInstance(getContext()).getAllFuncoesAdminAPI(getContext());
        SingletonProjeto.getInstance(getContext()).getAllDadosPessoaisAPI(getContext());
        SingletonProjeto.getInstance(getContext()).getAllUsersAPI(getContext());
        return rootview;
    }
    private void procurarUsersBanidosUsername()
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
                if(searchListaUsersBanidos != null) {
                    for (User user : searchListaUsersBanidos) {
                        if (user.getUsername().toLowerCase().contains(s.toLowerCase())) {
                            searchUsers.add(user);
                        }
                    }
                    ListaUsersBloqueadosAdapter listaUsersBloqueadosAdapter = new ListaUsersBloqueadosAdapter(getContext(), searchUsers);
                    lvListaUsersBanidos.setAdapter(listaUsersBloqueadosAdapter);
                }
                return true;
            }
        });
    }
    @Override
    public void onRefresh() {
        SingletonProjeto.getInstance(getContext()).getAllUsersAPI(getContext());
        SingletonProjeto.getInstance(getContext()).getAllProdutosAPI(getContext());
        searchView.setQuery("",true);
        searchView.clearFocus();
        swipeRefrechUsersBanidos.setRefreshing(false);
    }

    @Override
    public void onRefreshListaDadospessoais(ArrayList<DadosPessoais> listaDadospessoais) {
        System.out.println("-->4");
    }

    @Override
    public void onErroListaDadosessoais(String message) {
        System.out.println("-->5");
    }


    @Override
    public void onRefreshListaUsers(ArrayList<User> listaUsers) {
        if (listaUsers != null) {
            ArrayList<User> searchUser = new ArrayList<>();
            for (User user : listaUsers) {
                if (user.getStatus() == 0) {
                    searchUser.add(user);
                }
            }
            searchListaUsersBanidos = searchUser;
            lvListaUsersBanidos.setAdapter(new ListaUsersBloqueadosAdapter(getContext(), searchUser));
        }
            // SingletonProjeto.getInstance(getContext()).getAllUsersAPI(getContext());
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
}