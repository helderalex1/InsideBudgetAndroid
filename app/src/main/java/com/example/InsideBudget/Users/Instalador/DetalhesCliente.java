package com.example.InsideBudget.Users.Instalador;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.InsideBudget.Adapter.Users.Instalador.ListaClienteOrcamentoAdapter;
import com.example.InsideBudget.Adapter.Users.Instalador.ListaMeusClientesAdapter;
import com.example.InsideBudget.Adapter.Users.Instalador.ListaMeusOrcamentosAdapter;
import com.example.InsideBudget.Adapter.Users.Instalador.ListaProdutosOrcamentoAdapter;
import com.example.InsideBudget.Listener.ClientesListener;
import com.example.InsideBudget.Listener.OrcamentosListener;
import com.example.InsideBudget.Modelo.Cliente;
import com.example.InsideBudget.Modelo.Orcamento;
import com.example.InsideBudget.Modelo.OrcamentoProduto;
import com.example.InsideBudget.Modelo.Produto;
import com.example.InsideBudget.Modelo.SingletonProjeto;
import com.example.InsideBudget.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class DetalhesCliente extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, OrcamentosListener, ClientesListener {
    private TextView nome,empresa,morada,nif,telefone,email;
    private ListView listView;
    private SwipeRefreshLayout swipe;
    private SearchView searchView;
    private ArrayList<Orcamento> searchLista;
    private ArrayList<Cliente> listaCliente;
    private FloatingActionButton fabMenu,fabEditCliente,fabAddOrcamento;
    private FragmentManager fragmentManager;
    private String nomeCliente;
    private View parentLayout;
    private boolean isOpen = false;
    public static final String DETALHES_Cliente ="cliente";
    public static final int ADICIONAR_CLIENTE =1;
    public static final int EDITAR_ORCAMENTO =2;

    private Cliente cliente,cliente1;
    public int cliente_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_cliente);

        setTitleColor(R.color.black);
        nome=findViewById(R.id.tvDetalheNomeCliente);
        empresa=findViewById(R.id.tvDetalheEmpresaCliente);
        morada=findViewById(R.id.tvDetalheMoradaCliente);
        nif=findViewById(R.id.tvDetalheNifCliente);
        telefone=findViewById(R.id.tvDetalheTelefoneCliente);
        email=findViewById(R.id.tvDetalheEmailCliente);
        swipe=findViewById(R.id.swiperefrechDetalheCliente);
        listView=findViewById(R.id.lvListaDetalhesCliente);
        fabMenu = findViewById(R.id.fab_menu_detalhes_cliente);
        fabEditCliente = findViewById(R.id.fab_editCliente_detalhes_cliente);
        fabAddOrcamento= findViewById(R.id.fab_addOrcamento_detalhes_cliente);
        searchView = findViewById(R.id.searchViewClientesOrcamentos);
        parentLayout = findViewById(android.R.id.content);
        cliente_id= getIntent().getIntExtra(DETALHES_Cliente, 0);

        cliente= SingletonProjeto.getInstance(getApplicationContext()).getMeusClientesArray(0,cliente_id);
        SingletonProjeto.getInstance(getApplicationContext()).setOrcamentosListener(this);
        SingletonProjeto.getInstance(getApplicationContext()).setClientesListener(this);

        SingletonProjeto.getInstance(getApplicationContext()).getMeusClientesAPI(getApplicationContext());
        SingletonProjeto.getInstance(getApplicationContext()).getMeusClientesDB();
        SingletonProjeto.getInstance(getApplicationContext()).getMeusClientesAPI(getApplicationContext());
        SingletonProjeto.getInstance(getApplicationContext()).getMeusOrcamentosAPI(getApplicationContext());
//        System.out.println("-->detalhesclientea"+ cliente_id + cliente.getNome());

        if(cliente!=null){

            System.out.println("-->detalhesclienteb"+ cliente_id +"|"+cliente.getNome());
            setTitle("Cliente  "+cliente.getNome());
            nome.setText(cliente.getNome());
            empresa.setText(cliente.getEmpresa());
            morada.setText(cliente.getMorada());
            nif.setText(Integer.toString(cliente.getNif()));
            telefone.setText(Integer.toString(cliente.getTelefone()));
            email.setText(cliente.getEmail());
        }

        listaCliente = SingletonProjeto.getInstance(getApplicationContext()).getMeusClientesDB();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Orcamento orcamento= (Orcamento) parent.getItemAtPosition(position);

               // DetalhesOrcamento.orcamento_id = orcamento.getId();
                Intent intent = new Intent(getApplicationContext(), DetalhesOrcamento.class);
                intent.putExtra(DetalhesOrcamento.DETALHES_Orcamento,orcamento.getId());
               // DetalhesOrcamento.orcamento_id = orcamento.getId();
                startActivity(intent);

            }
        });
        fabMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFab();
            }
        });
        fabEditCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                bundle.putInt("cliente_id", cliente.getId());
                bundle.putInt("opcao", 2);
                Intent intent = new Intent(getApplicationContext(),CriarAtualizarCliente.class);
                intent.putExtras(bundle);
                startActivityForResult(intent,CriarAtualizarCliente.EDITAR);
              //  finish();
            }
        });
        fabAddOrcamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Bundle bundle = new Bundle();
                bundle.putInt("cliente_id", cliente.getId());
                bundle.putInt("opcao", 1);
                Intent intent = new Intent(getApplicationContext(),CriarAtualizarOrcamento.class);
                intent.putExtras(bundle);
                startActivity(intent);


            }
        });

       // procurarMeusClientesNome();
        swipe.setOnRefreshListener(this);
    }

    private void openFab(){
        if(isOpen){
            fabEditCliente.setVisibility(View.INVISIBLE);
            fabAddOrcamento.setVisibility(View.INVISIBLE);
            isOpen = false;
        }else{
            fabEditCliente.setVisibility(View.VISIBLE);
            fabAddOrcamento.setVisibility(View.VISIBLE);
            isOpen = true;
        }
    }

    private void procurarMeusClientesNome()
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
                if (searchLista != null) {
                    for (Orcamento orcamento : searchLista) {
                        if (orcamento.getNome().toLowerCase().contains(s.toLowerCase())) {
                            searchOrcamentos.add(orcamento);
                        }
                    }
                    }
                    ListaClienteOrcamentoAdapter listaClienteOrcamentoAdapter = new ListaClienteOrcamentoAdapter(getApplicationContext(), searchOrcamentos);
                    listView.setAdapter(listaClienteOrcamentoAdapter);
                    return true;
                }

        });
    }

    @Override
    public void onActivityResult(int requestCode,int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    SingletonProjeto.getInstance(getApplicationContext()).getMeusClientesAPI(getApplicationContext());
                    cliente1= SingletonProjeto.getInstance(getApplicationContext()).getMeusClientesArray(0,cliente_id);
                    System.out.println("-->detalhesclienteaditaaaaa"+cliente1.getNome());

            switch (requestCode) {
                case CriarAtualizarOrcamento.ADICIONAR:
                    System.out.println("-->detalhesclienteadd");

                    SingletonProjeto.getInstance(getApplicationContext()).getMeusClientesAPI(getApplicationContext());
                    cliente1= SingletonProjeto.getInstance(getApplicationContext()).getMeusClientesArray(0,cliente_id);
                 //   Snackbar.make(getView(), , Snackbar.LENGTH_LONG).show();
                    break;

                case CriarAtualizarOrcamento.EDITAR:


                    setTitle("Cliente  "+cliente1.getNome());
                    nome.setText(cliente1.getNome());
                    empresa.setText(cliente1.getEmpresa());
                    morada.setText(cliente1.getMorada());
                    nif.setText(Integer.toString(cliente1.getNif()));
                    telefone.setText(Integer.toString(cliente1.getTelefone()));
                    email.setText(cliente1.getEmail());
                   // Snackbar.make(getView(), getString(R.string.cliente_editado_com_sucesso), Snackbar.LENGTH_LONG).show();
                    break;
            }}
            }, 500);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRefresh() {
        SingletonProjeto.getInstance(getApplicationContext()).getMeusOrcamentosAPI(getApplicationContext());

        searchView.setQuery("",true);
        searchView.clearFocus();
        swipe.setRefreshing(false);
    }


    @Override
    public void onBackPressed() {
      // finish();

        SingletonProjeto.getInstance(getApplicationContext()).getMeusClientesAPI(getApplicationContext());
        SingletonProjeto.getInstance(getApplicationContext()).getMeusOrcamentosClienteArray(cliente_id);
        searchView.setQuery("",true);
        searchView.clearFocus();
        finish();
        super.onBackPressed();
    }

    @Override
    public void onRefreshListaMeusOrcamentos(ArrayList<Orcamento> listaOrcamentos) {

        if(listaOrcamentos!=null &&cliente!=null) {
            System.out.println("-->cliente " + cliente.getId() + cliente_id);
            listView.setAdapter(new ListaClienteOrcamentoAdapter(getApplicationContext(), SingletonProjeto.getInstance(getApplicationContext()).getMeusOrcamentosClienteArray(cliente_id)));
        }
    }

    @Override
    public void onAddOrcamento() {

       /* SingletonProjeto.getInstance(getApplicationContext()).getMeusClientesAPI(getApplicationContext());
        SingletonProjeto.getInstance(getApplicationContext()).getMeusOrcamentosClienteArray(cliente_id);
        searchView.setQuery("",true);
        searchView.clearFocus();*/
    }

    @Override
    public void onEditOrcamento() {

        /*Toast.makeText(this,"Orcamento adicionado com sucesso", Toast.LENGTH_SHORT).show();
        SingletonProjeto.getInstance(getApplicationContext()).getMeusClientesAPI(getApplicationContext());
        SingletonProjeto.getInstance(getApplicationContext()).getMeusOrcamentosClienteArray(cliente_id);
        searchView.setQuery("",true);
        searchView.clearFocus();*/
    }

    @Override
    public void onErroListaMeusOrcamentos(String message) {

    }

    @Override
    public void onRefreshListaMeusClientes(ArrayList<Cliente> listaClientes) {

    }

    @Override
    public void onAddCliente() {

    }

    @Override
    public void onEditCliente() {


    }

    @Override
    public void onErroListaMeusClientes(String message) {

    }

}