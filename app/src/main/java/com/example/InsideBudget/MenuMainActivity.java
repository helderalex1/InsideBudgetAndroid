package com.example.InsideBudget;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.InsideBudget.Users.Admin.InformacaoAdminFragment;
import com.example.InsideBudget.Users.DadosPessoaisFragment;
import com.example.InsideBudget.Users.Admin.ListaUsersBloqueadosFragment;
import com.example.InsideBudget.Users.Admin.ListaUsersComAcessoFragment;
import com.example.InsideBudget.Users.Admin.ListaUsersRegistosEsperaFragment;
import com.example.InsideBudget.Modelo.SingletonProjeto;
import com.example.InsideBudget.Users.Fornecedor.InformacaoFornecedorFragment;
import com.example.InsideBudget.Users.Instalador.InformacaoInstaladorFragment;
import com.example.InsideBudget.Users.Instalador.ListaMeusClientesFragment;
import com.example.InsideBudget.Users.Instalador.ListaMeusOrcamentosFragment;
import com.example.InsideBudget.Users.ListaTodosProdutosFragment;
import com.google.android.material.navigation.NavigationView;

public class MenuMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //campos dentro do ficheiro User_Save;
    public static final String PREF_USER = "USER_SAVE";
    public static final String ID = "ID";
    public static final String USERNAME = "USERNAME";
    public static final String EMAIL= "EMAIL";
    public static final String NOME = "NOME";
    public static final String EMPRESA = "EMPRESA";
    public static final String MORADA = "MORADA";
    public static final String PAIS = "PAIS";
    public static final String CIDADE = "CIDADE";
    public static final String TELEFONE = "TELEFONE";
    public static final String FUNCAO = "FUNCAO";
    public static final String TOKEN = "TOKEN";
    public static final String PASSWORD ="PASSWORD";
    private SharedPreferences sharedPreferences;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private FragmentManager fragmentManager;
    //private ActionBarDrawerToggle toggle = null;
    private  String funcao = null;
    private  String username = null;
    private  String nome = null;
    private  String empresa = null;
    private  String email = null;
    private  String token = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActionBarDrawerToggle toggle = null;
        super.onCreate(savedInstanceState);
        sharedPreferences= getApplicationContext().getSharedPreferences(MenuMainActivity.PREF_USER, Context.MODE_PRIVATE);
        funcao = sharedPreferences.getString(FUNCAO,null);
        token = sharedPreferences.getString(TOKEN,null);
        System.out.println("-->12" + funcao + token );
        drawerLayout=null;
        switch (funcao){
            case "admin" :
                setContentView(R.layout.activity_menu_admin);
                drawerLayout = findViewById(R.id.drawer_layout_administrador);
                if(SingletonProjeto.getInstance(getApplicationContext()).isInternetOn(getApplicationContext())){
                    SingletonProjeto.getInstance(getApplicationContext()).getAllUsersAPI(getApplicationContext());
                    SingletonProjeto.getInstance(getApplicationContext()).getAllFuncoesAdminAPI(getApplicationContext());
                   // SingletonProjeto.getInstance(getApplicationContext()).getMeusOrcamentosAPI(getApplicationContext());
                    SingletonProjeto.getInstance(getApplicationContext()).getAllProdutosAPI(getApplicationContext());
                }
                break;
            case "fornecedor":
                setContentView(R.layout.activity_menu_fornecedor);
                drawerLayout = findViewById(R.id.drawer_layout_fornecedor);
                break;
            case "instalador":

                setContentView(R.layout.activity_menu_instalador);
                drawerLayout= findViewById(R.id.drawer_layout_instalador);

                break;
            default:
                onDestroy();
                break;
        }

        Toolbar toolbar = findViewById(R.id.toolbar_menu);
        setSupportActionBar(toolbar);
        navigationView = findViewById(R.id.nav_view);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer);
        toggle.syncState();
        drawerLayout.addDrawerListener(toggle);

        navigationView.setNavigationItemSelectedListener(this);
        fragmentManager = getSupportFragmentManager();
        Fragment fragment;
        switch (funcao){
            case "admin" :
                fragment = new InformacaoAdminFragment();
                fragmentManager.beginTransaction().replace(R.id.contentFragment, fragment).commit();
                setTitle("Informação de Admin");
                break;
            case "fornecedor":
                fragment = new InformacaoFornecedorFragment();
                fragmentManager.beginTransaction().replace(R.id.contentFragment, fragment).commit();
                setTitle("Minhas Informações");
                break;
            case "instalador":
                fragment = new InformacaoInstaladorFragment();
                fragmentManager.beginTransaction().replace(R.id.contentFragment, fragment).commit();
                setTitle("Minhas Informações");

                break;
            default:
                onDestroy();
                break;
        }
       // carregarFragmentoInicial(tipo_conta);
        carregarcabecalho();

    }

    private void carregarcabecalho() {
        sharedPreferences= getSharedPreferences(MenuMainActivity.PREF_USER, Context.MODE_PRIVATE);
        funcao=sharedPreferences.getString(FUNCAO,null);
        username=sharedPreferences.getString(USERNAME,null);
        nome=sharedPreferences.getString(NOME,null);
        empresa=sharedPreferences.getString(EMPRESA,null);
        email=sharedPreferences.getString(EMAIL, null);

        View hView = navigationView.getHeaderView(0);
        TextView nav_funcao = hView.findViewById(R.id.nav_header_tvFuncao);
        nav_funcao.setText(funcao);
        TextView nav_username = hView.findViewById(R.id.nav_header_tvUsername);
        nav_username.setText(username);
        TextView nav_nome = hView.findViewById(R.id.nav_header_tVNome);
        nav_nome.setText(nome);
        TextView nav_empresa = hView.findViewById(R.id.nav_header_tVEmpresa);
        nav_empresa.setText(empresa);
        TextView nav_email=hView.findViewById(R.id.nav_header_tVEmail);
        nav_email.setText(email);

    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()) {
            //--------------------Admin--------------------
            case R.id.nav_admin_informacao:
                fragment = new InformacaoAdminFragment();
                setTitle(item.getTitle());
                break;
            case R.id.nav_admin_users_com_acesso:
                fragment = new ListaUsersComAcessoFragment();
                setTitle(item.getTitle());
                break;

            case R.id.nav_admin_users_registos_espera:
                fragment = new ListaUsersRegistosEsperaFragment();
                setTitle(item.getTitle());
                break;
            case R.id.nav_admin_users_bloqueados:
                fragment = new ListaUsersBloqueadosFragment();
                setTitle(item.getTitle());
                break;
            case R.id.nav_fornecedor_informacao:
                fragment = new InformacaoFornecedorFragment();
                setTitle(item.getTitle());
                break;
            case R.id.nav_user_todos_produtos:
                fragment = new ListaTodosProdutosFragment();
                setTitle(item.getTitle());
                break;
            //--------------------Instalador-------------------
            case R.id.nav_instalador_informacao:
                fragment = new InformacaoInstaladorFragment();
                setTitle(item.getTitle());
                break;
            case R.id.nav_instalador_clientes:
                fragment = new ListaMeusClientesFragment();
                setTitle(item.getTitle());
                break;
            case R.id.nav_instalador_orcamentos:
                fragment = new ListaMeusOrcamentosFragment();
                setTitle(item.getTitle());
                break;
            //--------------------Todos users--------------------
            case R.id.nav_dadospessoais:
                fragment = new DadosPessoaisFragment();
                setTitle(item.getTitle());
                break;
            case R.id.nav_sair:
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
                sharedPreferences= getBaseContext().getSharedPreferences(MenuMainActivity.PREF_USER, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                break;
        }
        if(fragment != null){
                fragmentManager.beginTransaction().replace(R.id.contentFragment,fragment).commit();
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    public void onBackPressed() {
        return;
    }
}