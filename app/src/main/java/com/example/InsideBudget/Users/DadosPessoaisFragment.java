package com.example.InsideBudget.Users;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.InsideBudget.MenuMainActivity;
import com.example.InsideBudget.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DadosPessoaisFragment extends Fragment{
    private ListView lvlistMeusProdutos;
    private SwipeRefreshLayout swipeRefreshMeusProdutos;
    private Button btnPermitir;
    private SearchView searchView;
    private FloatingActionButton fab;
    private BottomNavigationView bottomNavigationView;
    private SharedPreferences sharedPreferences;
    private EditText editNomeCompleto,editEmail,editEmpresa,editPais,editCidade,editMorada,editTelefone;
    FragmentManager fragmentManager;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_dados_pessoais, container, false);
        setHasOptionsMenu(true);

        editNomeCompleto = rootview.findViewById(R.id.editDadosPessoaisNome);
        editEmail = rootview.findViewById(R.id.editDadosPessoaisEmail);
        editEmpresa = rootview.findViewById(R.id.editDadosPessoaisEmpresa);
        editPais = rootview.findViewById(R.id.editDadosPessoaisPais);
        editCidade = rootview.findViewById(R.id.editDadosPessoaisCidade);
        editMorada = rootview.findViewById(R.id.editDadosPessoaisMorada);
        editTelefone = rootview.findViewById(R.id.editDadosPessoaisTelefone);

        sharedPreferences = getContext().getSharedPreferences(MenuMainActivity.PREF_USER, Context.MODE_PRIVATE);
        editNomeCompleto.setText(sharedPreferences.getString(MenuMainActivity.NOME, null));
        editEmail.setText(sharedPreferences.getString(MenuMainActivity.EMAIL, null));
        editEmpresa.setText(sharedPreferences.getString(MenuMainActivity.EMPRESA, null));
        editPais.setText(sharedPreferences.getString(MenuMainActivity.PAIS, null));
        editCidade.setText(sharedPreferences.getString(MenuMainActivity.CIDADE, null));
        editMorada.setText(sharedPreferences.getString(MenuMainActivity.MORADA, null));
        editTelefone.setText(sharedPreferences.getString(MenuMainActivity.TELEFONE, null));

        return rootview;
    }
}