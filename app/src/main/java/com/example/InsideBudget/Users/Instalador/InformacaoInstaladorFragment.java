package com.example.InsideBudget.Users.Instalador;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.strictmode.CleartextNetworkViolation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.InsideBudget.Listener.InstaladorListener;
import com.example.InsideBudget.MenuMainActivity;
import com.example.InsideBudget.Modelo.Cliente;
import com.example.InsideBudget.Modelo.Orcamento;
import com.example.InsideBudget.Modelo.OrcamentoProduto;
import com.example.InsideBudget.Modelo.Produto;
import com.example.InsideBudget.Modelo.SingletonProjeto;
import com.example.InsideBudget.Modelo.Tipologia;
import com.example.InsideBudget.Modelo.User;
import com.example.InsideBudget.R;

import java.util.ArrayList;
import java.util.Locale;

public class InformacaoInstaladorFragment extends Fragment implements InstaladorListener, SwipeRefreshLayout.OnRefreshListener{
    private ArrayList<Orcamento> listaOrcamentosMain;
    private ArrayList<Cliente> listaClientesMain;
    private ArrayList<Produto> listaProdutosMain;
    private ArrayList<OrcamentoProduto> listaOrcamentoProdutosMain;
    private SwipeRefreshLayout swipe;
    private SharedPreferences sharedPreferences;FragmentManager fragmentManager;
    private TextView tvMeusTotalClientes,tvMeusTotalOrcamentos ,tvMeusTotalProdutos,tvMeusTotalProdutosQuantidade, tvMeuProdutoMaisQuantidades,totalUsersInstaladores,totalProdutos ;
    private int id,iTotalMeusClientes=0,iTotalMeusOrcamentos=0,iTotalProdutosQuantidade=0,iUserAdmins=1,iUserFornecedores=0,iUsersInstaladores = 0,iProdutosTotal = 0;
    int aa = 0;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_informacao_instalador, container, false);
        setHasOptionsMenu(true);
        tvMeusTotalClientes = rootview.findViewById(R.id.tVClienteTotalClientes);
        tvMeusTotalOrcamentos = rootview.findViewById(R.id.tVClienteTotalOrcamentos);
        tvMeusTotalProdutos = rootview.findViewById(R.id.tVClienteTotalProdutos);
        tvMeusTotalProdutosQuantidade = rootview.findViewById(R.id.tVClienteTotalProdutosQuantidade);
        tvMeuProdutoMaisQuantidades = rootview.findViewById(R.id.tvClienteProdutoMaisQuantidades);
        swipe = rootview.findViewById(R.id.swiperefreshInformacaoInstalador);
        sharedPreferences = getContext().getSharedPreferences(MenuMainActivity.PREF_USER, Context.MODE_PRIVATE);
        id = Integer.parseInt(sharedPreferences.getString(MenuMainActivity.ID, null));


        SingletonProjeto.getInstance(getContext()).setInstaladorListener(this);

        SingletonProjeto.getInstance(getContext()).getMeusClientesAPI(getContext());
        SingletonProjeto.getInstance(getContext()).getMeusOrcamentosAPI(getContext());
        SingletonProjeto.getInstance(getContext()).getMeusOrcamentoProdutosAPI(getContext());
                System.out.println("-->informcaoaaaaadfbfd");

            /*    SingletonProjeto.getInstance(getContext()).getMeusClientesAPI(getContext());
                SingletonProjeto.getInstance(getContext()).getMeusOrcamentosAPI(getContext());
                SingletonProjeto.getInstance(getContext()).getMeusOrcamentoProdutosAPI(getContext());*/
       /*     Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                listaCliente = SingletonProjeto.getInstance(getContext()).getMeusClientesDB();
                System.out.println("-->infor" + listaCliente.size());
                for (Cliente cliente : listaCliente) {
                    if (cliente.getUser_id() == id) {
                        iTotalMeusClientes++;
                    }
                }
                listaOrcamentos = SingletonProjeto.getInstance(getContext()).getMeusOrcamentosDB();

                System.out.println("-->infor" + listaOrcamentos.size());
                for (Orcamento orcamento : listaOrcamentos) {
                    if (orcamento.getUser_id() == id) {
                        iTotalMeusOrcamentos++;

                        listaOrcamentoProduto = SingletonProjeto.getInstance(getContext()).getMeusOrcamentoProdutosOrcamentoArray(orcamento.getId());

              System.out.println("-->informacaoaaa:"+listaOrcamentos.size()+"|"+iTotalMeusOrcamentos+"|"+listaOrcamentoProduto.size());
                        for (OrcamentoProduto orcamentoProduto : listaOrcamentoProduto) {
                            System.out.println("-->informacaoccccc:" + orcamentoProduto.getOrcamento_Id() + "|" + orcamento.getId());
                            if (orcamentoProduto.getOrcamento_Id() == orcamento.getId()) {
                                iTotalMeusProdutosQuantidade++;

                                System.out.println("-->informacaobbb:" + listaOrcamentoProduto.size() + "|" + iTotalMeusProdutosQuantidade);
                            }
                        }
                    }
                }
                if (iTotalMeusClientes != 0) {
                    tvMeusTotalClientes.setText(Integer.toString(iTotalMeusClientes));
                }

                if (iTotalMeusOrcamentos != 0) {
                    tvMeusTotalOrcamentos.setText(Integer.toString(iTotalMeusOrcamentos));
                }

                if (iTotalMeusProdutosQuantidade != 0) {
                    tvMeusTotalProdutosQuantidade.setText(Integer.toString(iTotalMeusProdutosQuantidade));
                }
//
//        SingletonProjeto.getInstance(getContext()).getMeusOrcamentoProdutosAPI(getContext());
                //   SingletonProjeto.getInstance(getContext()).getAllUsersAPI(getContext());

            }},3000);*/


        swipe.setOnRefreshListener(this);
        return rootview;
    }

    @Override
    public void onGetOrcamentos(ArrayList<Orcamento> listaOrcamentos) {
        listaOrcamentosMain = listaOrcamentos;
        iTotalMeusOrcamentos=0;
        System.out.println("-->informacaoinstalador:"+iTotalMeusOrcamentos);
        if(listaOrcamentos != null) {
            for (Orcamento orcamento : listaOrcamentos) {
                if (orcamento.getUser_id() == id) {
                    iTotalMeusOrcamentos++;
                }
            }
            tvMeusTotalOrcamentos.setText(Integer.toString(iTotalMeusOrcamentos));
            System.out.println("-->informacaoinstalador:::" + iTotalMeusOrcamentos);
        }
    }

    @Override
    public void onGetClientes(ArrayList<Cliente> listaClientes) {
        listaClientesMain = listaClientes;
        System.out.println("-->informacaocliente:"+listaClientes.size());
        tvMeusTotalClientes.setText(Integer.toString(listaClientes.size()));
    }

    @Override
    public void onGetOrcamentoProdutos(int orcamentoProdutosSize, ArrayList<OrcamentoProduto> listaOrcamentoProduto) {
        listaOrcamentoProdutosMain = listaOrcamentoProduto;
        iTotalProdutosQuantidade =0;
        for (OrcamentoProduto orcamentoProduto: listaOrcamentoProduto) {
            iTotalProdutosQuantidade = iTotalProdutosQuantidade + orcamentoProduto.getQuantidade();
        }

        tvMeusTotalProdutos.setText(Integer.toString(orcamentoProdutosSize));
        tvMeusTotalProdutosQuantidade.setText(Integer.toString(iTotalProdutosQuantidade));
        onProdutoMaisQuantidade();
    }


    public void onProdutoMaisQuantidade() {
        listaProdutosMain = SingletonProjeto.getInstance(getContext()).getProdutosDB();
        int quantpro1 = 0;
        int quantpro2 = 0;
        String produtonome = null;
        for (Produto produto : listaProdutosMain){
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

        System.out.println("-->abcde:"+produtonome +" | "+ quantpro2);
        tvMeuProdutoMaisQuantidades.setText(produtonome +" | "+ quantpro2);
    }

    @Override
    public void onRefresh() {
        SingletonProjeto.getInstance(getContext()).getMeusClientesAPI(getContext());
        SingletonProjeto.getInstance(getContext()).getMeusOrcamentosAPI(getContext());
        SingletonProjeto.getInstance(getContext()).getMeusOrcamentoProdutosAPI(getContext());
        SingletonProjeto.getInstance(getContext()).getAllProdutosAPI(getContext());


        swipe.setRefreshing(false);
    }
}