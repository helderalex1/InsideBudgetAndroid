package com.example.InsideBudget.Users.Instalador;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.InsideBudget.Adapter.Users.Instalador.ListaDialogProdutosOrcamentoAdapter;
import com.example.InsideBudget.Adapter.Users.Instalador.ListaProdutosOrcamentoAdapter;
import com.example.InsideBudget.Adapter.Users.ListaTodosProdutosAdapter;
import com.example.InsideBudget.Listener.OrcamentoProdutosListener;
import com.example.InsideBudget.Listener.ProdutosListener;
import com.example.InsideBudget.LoginActivity;
import com.example.InsideBudget.Modelo.Orcamento;
import com.example.InsideBudget.Modelo.OrcamentoProduto;
import com.example.InsideBudget.Modelo.Produto;
import com.example.InsideBudget.Modelo.SingletonProjeto;
import com.example.InsideBudget.Modelo.Tipologia;
import com.example.InsideBudget.R;
import com.example.InsideBudget.Users.DetalhesProduto;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class DialogAdicionarProdutoFragment extends DialogFragment implements SwipeRefreshLayout.OnRefreshListener, ProdutosListener {
    private SwipeRefreshLayout swipe;
    private ListView listView;
    private ArrayList<Produto> searchListaTodosProdutos,searchTodosProdutos,spinnerTodosProdutos;
    private ArrayList<Tipologia> tipologias;
    private Spinner spinner;
    private SearchView searchView;
    private ArrayAdapter<Tipologia> spinnerAdapter;
    private int spinnerPosition, i,i1;
    private FloatingActionButton fab;
    private String palavra;
    private Produto produto;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootview = inflater.inflate(R.layout.fragment_adicionar_produto_dialog,container,false);

        swipe= rootview.findViewById(R.id.swiperefrechDialogProdutoOrcamento);
        listView= rootview.findViewById(R.id.lvListaDialogProdutoOrcamento);

        spinner = rootview.findViewById(R.id.spinnerTodosProdutosOrcamentoProduto);
        searchView = rootview.findViewById(R.id.searchViewProdutosOrcamentoProduto);
        fab = rootview.findViewById(R.id.fab_fechar_dialog);
        SingletonProjeto.getInstance(getContext()).setProdutosListener(this);

        SingletonProjeto.getInstance(getContext()).getMeusOrcamentoProdutosAPI(getContext());
        SingletonProjeto.getInstance(getContext()).getAllProdutosAPI(getContext());
        SingletonProjeto.getInstance(getContext()).getAllDadosPessoaisAPI(getContext());
        SingletonProjeto.getInstance(getContext()).getAllTipologiaAPI(getContext());


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                new AlertDialog.Builder(getContext())
                        .setTitle("")
                        .setPositiveButton("Adicionar Produto", new AlertDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(i1 == 0) {
                                    produto = (Produto) parent.getItemAtPosition(position);
                                    ArrayList<OrcamentoProduto> orcamentoProdutos = SingletonProjeto.getInstance(getContext()).getMeusOrcamentoProdutosOrcamentoArray(DetalhesOrcamento.orcamento_id);
                                    OrcamentoProduto orcamentoProdutoss = SingletonProjeto.getInstance(getContext()).getMeusOrcamentoProdutosArray(produto.getId());
                                    Orcamento orcamento = SingletonProjeto.getInstance(getContext()).getMeusOrcamentosArray(DetalhesOrcamento.orcamento_id,null,0);
                                    System.out.println("-->onclicka"+orcamentoProdutos.size());
                                    for (OrcamentoProduto orcamentoProdutoContem : orcamentoProdutos) {
                                        if(orcamentoProdutoss != null){
                                        if (orcamentoProdutoContem.getProduto_Id() == orcamentoProdutoss.getProduto_Id()) {
                                           /* orcamentoProdutoContem.setQuantidade(orcamentoProdutoContem.getQuantidade() + 1);

                                            System.out.println("-->addorcamentoprodutoedit" + orcamentoProduto.getId() + "|" + orcamentoProduto.getOrcamento_Id() + "|" + orcamentoProduto.getProduto_Id() + "|" + orcamentoProduto.getQuantidade());
                                            Orcamento orcamento = SingletonProjeto.getInstance(getContext()).getMeusOrcamentosArray(DetalhesOrcamento.orcamento_id,null,0);
                                            orcamento.setTotal(orcamento.getTotal()+produto.getPreco());
                                            DetalhesOrcamento.total.setText(Float.toString(orcamento.getTotal()));

                                            SingletonProjeto.getInstance(getContext()).editOrcamentoProdutoAPI(orcamentoProdutoContem, getContext());
                                            SingletonProjeto.getInstance(getContext()).editOrcamentoAPI(orcamento, getContext());*/
                                            Toast.makeText(getContext(), "O produto " + produto.getNome() + " com a referência " + produto.getReferencia() + " já se encontra no orcamento " + orcamento.getNome(), Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                        }
                                    }
                                    OrcamentoProduto orcamentoProduto = new OrcamentoProduto(0, DetalhesOrcamento.orcamento_id, produto.getId(), 1);
                                    SingletonProjeto.getInstance(getContext()).addOrcamentoProdutoAPI(orcamentoProduto, getContext());
                                }
                            }
                        })
                        .setNeutralButton("Cancel", null)
                        .setNegativeButton("Visualizar Produto",new AlertDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                              /*  Uri uriUrl = Uri.parse(urlSite);
                                Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                                startActivity(Intent.createChooser(launchBrowser,"as"));*/
                                Bundle bundle = new Bundle();
                                Produto produto = (Produto) parent.getItemAtPosition(position);

                                bundle.putInt("produto_id",produto.getId());
                                bundle.putString("opcao","AddProdutoOrcamento");
                                Intent intent = new Intent(getContext(),DetalhesProduto.class);

                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        })
                        .setMessage( "Deseja adicionar ou visualizar produto?" )
                        .show();
                /* if(i1 == 0) {
                    Produto produto = (Produto) parent.getItemAtPosition(position);
                    ArrayList<OrcamentoProduto> orcamentoProdutos = SingletonProjeto.getInstance(getContext()).getMeusOrcamentoProdutosOrcamentoArray(DetalhesOrcamento.orcamento_id);
                    OrcamentoProduto orcamentoProduto = new OrcamentoProduto(0, DetalhesOrcamento.orcamento_id, produto.getId(), 1);
                    for (OrcamentoProduto orcamentoProdutoContem : orcamentoProdutos) {
                        if (orcamentoProdutoContem.getProduto_Id() == orcamentoProduto.getProduto_Id()) {
                            orcamentoProdutoContem.setQuantidade(orcamentoProdutoContem.getQuantidade() + 1);

                            System.out.println("-->addorcamentoprodutoedit" + orcamentoProduto.getId() + "|" + orcamentoProduto.getOrcamento_Id() + "|" + orcamentoProduto.getProduto_Id() + "|" + orcamentoProduto.getQuantidade());
                            Orcamento orcamento = SingletonProjeto.getInstance(getContext()).getMeusOrcamentosArray(DetalhesOrcamento.orcamento_id,null,0);
                            orcamento.setTotal(orcamento.getTotal()+produto.getPreco());
                            DetalhesOrcamento.total.setText(Float.toString(orcamento.getTotal()));

                            SingletonProjeto.getInstance(getContext()).editOrcamentoProdutoAPI(orcamentoProdutoContem, getContext());
                            SingletonProjeto.getInstance(getContext()).editOrcamentoAPI(orcamento, getContext());
                            return;
                        }
                    }
                    SingletonProjeto.getInstance(getContext()).addOrcamentoProdutoAPI(orcamentoProduto, getContext());
                }*/
                i=1;
            }});
        tipologias=SingletonProjeto.getInstance(getContext()).getTipologiasDB();
        spinnerAdapter = new ArrayAdapter<Tipologia>(getContext(),R.layout.support_simple_spinner_dropdown_item,tipologias);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("-->pesquisa0"+position);
                spinnerPosition = position+1;
                if(spinnerPosition == 1){

                    if(i >0) {
                        ListaTodosProdutosAdapter listaTodosProdutosAdapter = new ListaTodosProdutosAdapter(getContext(), searchListaTodosProdutos);
                        listView.setAdapter(listaTodosProdutosAdapter);
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
                        if(produto.getTipologia_id() ==spinnerPosition){
                            spinnerTodosProdutos.add(produto);
                        }
                    }

                    System.out.println("-->pesquisa2");
                    ListaTodosProdutosAdapter listaTodosProdutosAdapter = new ListaTodosProdutosAdapter(getContext(), spinnerTodosProdutos);
                    listView.setAdapter(listaTodosProdutosAdapter);
                }else{
                    // searchTodosProdutos.clear();
                    if (searchListaTodosProdutos != null) {
                        for (Produto produto : searchListaTodosProdutos) {
                            if (produto.getTipologia_id() ==spinnerPosition && produto.getNome().toLowerCase().contains(palavra.toLowerCase()) ) {
                                spinnerTodosProdutos.add(produto);
                            }
                        }

                        System.out.println("-->pesquisa3");
                        ListaTodosProdutosAdapter listaTodosProdutosAdapter = new ListaTodosProdutosAdapter(getContext(), spinnerTodosProdutos);
                        listView.setAdapter(listaTodosProdutosAdapter);

                    }
                }
                Toast.makeText(getContext(),tipologias.get(position).toString(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        swipe.setOnRefreshListener(this);
        procurarTodosProdudosNome();
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
                    listView.setAdapter(listaTodosProdutosAdapter);
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
                        listView.setAdapter(listaTodosProdutosAdapter);
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
                        listView.setAdapter(listaTodosProdutosAdapter);

                    }
                    return true;
                }
            }
        });
    }
    @Override
    public void onRefresh() {
        SingletonProjeto.getInstance(getContext()).getAllProdutosAPI(getContext());
        spinner.setSelection(0);
        searchView.setQuery("",false);
        searchView.clearFocus();
        swipe.setRefreshing(false);
    }

    @Override
    public void onRefreshListaTodosProdutos(ArrayList<Produto> listaProdutos) {
        System.out.println("-->dialog"+ listaProdutos.size());

        if(listaProdutos!=null ) {
            System.out.println("-->dialog" + listaProdutos.size());
            searchListaTodosProdutos = listaProdutos;
           /* System.out.println("-->1ccc"+listaOrcamentoProdutos.size());
            System.out.println("-->orcamentoid:aa"+ orcamento_id + orcamento.getId());
            searchLista = SingletonProjeto.getInstance(getContext()).getMeusProdutosOrcamentoArray(orcamento.getId());*/
            listView.setAdapter(new ListaDialogProdutosOrcamentoAdapter(getContext(), listaProdutos));
        }
    }

    @Override
    public void onAddOrcamentoProduto() {

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                SingletonProjeto.getInstance(getContext()).getMeusOrcamentosAPI(getContext());
       SingletonProjeto.getInstance(getContext()).getMeusOrcamentoProdutosAPI(getContext());
        System.out.println("-->orcamentovalor: " + DetalhesOrcamento.orcamento_id);
        Orcamento orcamento = SingletonProjeto.getInstance(getContext()).getMeusOrcamentosArray(DetalhesOrcamento.orcamento_id,null,0);
        orcamento.setTotal(orcamento.getTotal() + produto.getPreco());
        SingletonProjeto.getInstance(getContext()).editOrcamentoAPI(orcamento, getContext());
        DetalhesOrcamento.total.setText(String.format("%.2f",orcamento.getTotal())+"€");
    }
},200);
//        dismiss();
    }

    @Override
    public void onErroListaTodosProduto(String message) {
    }

}
