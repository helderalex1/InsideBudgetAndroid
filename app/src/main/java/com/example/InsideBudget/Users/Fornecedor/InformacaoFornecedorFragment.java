package com.example.InsideBudget.Users.Fornecedor;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.InsideBudget.Listener.FornecedorListener;
import com.example.InsideBudget.MenuMainActivity;
import com.example.InsideBudget.Modelo.AllDBHelper;
import com.example.InsideBudget.Modelo.Cliente;
import com.example.InsideBudget.Modelo.Orcamento;
import com.example.InsideBudget.Modelo.OrcamentoProduto;
import com.example.InsideBudget.Modelo.Produto;
import com.example.InsideBudget.Modelo.SingletonProjeto;
import com.example.InsideBudget.Modelo.User;
import com.example.InsideBudget.R;

import java.util.ArrayList;

public class InformacaoFornecedorFragment extends Fragment implements FornecedorListener,SwipeRefreshLayout.OnRefreshListener {
    private ArrayList<Produto> listaProdutoMain = null;
    private ArrayList<OrcamentoProduto> listaOrcamentoProdutosMain = null;
    private ArrayList<Cliente> listaClientesMain = null;
    private ArrayList<Orcamento> listaOrcamentosMain = null;
    private SharedPreferences sharedPreferences;
    private TextView totalMeusProdutos,totalClientes ,totalOrcamentos,produtoMaisQuantidade;
    private int iTotalMeusProdutos=0,iTotalMeuProdutoMaisUtilizado=0,iUsersBanidos=0,iUserAdmins=1,iUserFornecedores=0,iUsersInstaladores = 0,iProdutosTotal = 0;
    private SwipeRefreshLayout swipe;
    private int id;
    private int quantpro1=0,quantpro2=0,produtoid=0;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_informacao_fornecedor, container, false);
        setHasOptionsMenu(true);

        sharedPreferences = getContext().getSharedPreferences(MenuMainActivity.PREF_USER, Context.MODE_PRIVATE);
        id = Integer.parseInt(sharedPreferences.getString(MenuMainActivity.ID, null));
        totalMeusProdutos = rootview.findViewById(R.id.tVFornecedorTotalProdutos);
        totalClientes = rootview.findViewById(R.id.tVFornecedorTotalClientes);
        totalOrcamentos = rootview.findViewById(R.id.tVFornecedorTotalOrcamentos);
        produtoMaisQuantidade = rootview.findViewById(R.id.tVFornecedorProdutoMaisQuantidade);
        swipe = rootview.findViewById(R.id.swiperefreshInformacaoFornecedor);

        SingletonProjeto.getInstance(getContext()).setFornecedorListener(this);
        SingletonProjeto.getInstance(getContext()).getMeusOrcamentosAPI(getContext());
        SingletonProjeto.getInstance(getContext()).getAllProdutosAPI(getContext());
        SingletonProjeto.getInstance(getContext()).getMeusClientesAPI(getContext());
        SingletonProjeto.getInstance(getContext()).getMeusOrcamentoProdutosAPI(getContext());
        listaOrcamentoProdutosMain = SingletonProjeto.getInstance(getContext()).getMeusOrcamentosProdutosDB();
        listaClientesMain = SingletonProjeto.getInstance(getContext()).getMeusClientesDB();
        listaOrcamentosMain = SingletonProjeto.getInstance(getContext()).getMeusOrcamentosDB();
        listaProdutoMain = SingletonProjeto.getInstance(getContext()).getProdutosDB();



        swipe.setOnRefreshListener(this);
        return rootview;
    }

    @Override
    public void onRefresh() {
        SingletonProjeto.getInstance(getContext()).getMeusOrcamentosAPI(getContext());
        SingletonProjeto.getInstance(getContext()).getAllProdutosAPI(getContext());
        SingletonProjeto.getInstance(getContext()).getMeusClientesAPI(getContext());
        SingletonProjeto.getInstance(getContext()).getMeusOrcamentoProdutosAPI(getContext());
        listaOrcamentoProdutosMain = SingletonProjeto.getInstance(getContext()).getMeusOrcamentosProdutosDB();
        listaClientesMain = SingletonProjeto.getInstance(getContext()).getMeusClientesDB();
        listaOrcamentosMain = SingletonProjeto.getInstance(getContext()).getMeusOrcamentosDB();
        listaProdutoMain = SingletonProjeto.getInstance(getContext()).getProdutosDB();
        swipe.setRefreshing(false);
    }

    @Override
    public void onGetProdutos(ArrayList<Produto> listaProduto) {
        listaProdutoMain = listaProduto;
        iTotalMeusProdutos=0;
        for (Produto produto:listaProduto){
            if(produto.getFornecedor_id() == id){
                iTotalMeusProdutos++;
            }
        }
        totalMeusProdutos.setText(Integer.toString(iTotalMeusProdutos));
    }

    @Override
    public void onGetOrcamentos(ArrayList<Orcamento> listaOrcamento) {
        listaOrcamentosMain = listaOrcamento;
        int orcamentoFornecedor = 0;
        int flagOrcamento = 0;
        for (Orcamento orcamento : listaOrcamento){
            flagOrcamento = 1;
            for (OrcamentoProduto orcamentoProduto : listaOrcamentoProdutosMain) {
                System.out.println("-->informacaofornecedor:");
                if (orcamento.getId() == orcamentoProduto.getOrcamento_Id()) {
                    System.out.println("-->informacaofornecedor:1");
                    for (Produto produto : listaProdutoMain) {
                        System.out.println("-->informacaofornecedor:2");
                        if(flagOrcamento == 1){
                            System.out.println("-->informacaofornecedor:3");
                            if(orcamentoProduto.getProduto_Id() == produto.getId()){
                                orcamentoFornecedor++;
                                flagOrcamento = 0;

                                System.out.println("-->informacaofornecedor:4||"+ orcamentoFornecedor);
                            }
                        }
                    }
                }
            }
        }
        totalOrcamentos.setText(Integer.toString(orcamentoFornecedor));
    }

    @Override
    public void onGetClientes(ArrayList<Cliente> listaClientes) {
        listaOrcamentosMain = SingletonProjeto.getInstance(getContext()).getMeusOrcamentosDB();
        int valor = 0;
        int flagCliente =0;
        int clienteFornecedor = 0;
        System.out.println("-->informacaofornecedorcliaaa:"+listaProdutoMain.size());
        System.out.println("-->informacaofornecedorcli:a");
        for (Cliente cliente: listaClientesMain){
            flagCliente = 1;
            System.out.println("-->informacaofornecedorcli:b");
            for (Orcamento orcamento : listaOrcamentosMain){
                System.out.println("-->informacaofornecedorcli:c");
                if(cliente.getId() == orcamento.getCliente_id()){
                    System.out.println("-->informacaofornecedorcli1:");
                    for(OrcamentoProduto orcamentoProduto : listaOrcamentoProdutosMain){
                        System.out.println("-->informacaofornecedorcli:2");
                        if(orcamento.getId() == orcamentoProduto.getOrcamento_Id() && valor != orcamento.getId()){
                            System.out.println("-->informacaofornecedorcli:3");
                            valor = orcamento.getId();
                            for(Produto produto : listaProdutoMain){
                                System.out.println("-->informacaofornecedorcli4:");
                                if(orcamentoProduto.getProduto_Id() == produto.getId()){
                                    System.out.println("-->informacaofornecedorcli5:");
                                    if(flagCliente == 1){
                                        System.out.println("-->informacaofornecedorcli:6");
                                        clienteFornecedor++;
                                        flagCliente = 0;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        totalClientes.setText(Integer.toString(clienteFornecedor));
    }

    @Override
    public void onGetOrcamentoProdutos(int orcamentoProdutosSize, ArrayList<OrcamentoProduto> listaOrcamentoProduto) {
        int quantpro1 = 0;
        int quantpro2 = 0;
        String produtonome = null;
        for (Produto produto:listaProdutoMain){
            quantpro1 = 0;
            for (OrcamentoProduto orcamentoProduto : listaOrcamentoProdutosMain){
                if(produto.getId() == orcamentoProduto.getProduto_Id()){
                    quantpro1 = quantpro1 + orcamentoProduto.getQuantidade();
                    if(quantpro1 >= quantpro2){
                        produtonome = produto.getNome();
                        quantpro2 = quantpro1;
                    }
                }
            }
        }
        produtoMaisQuantidade.setText(produtonome + " | "+ Integer.toString(quantpro2));
    }
}