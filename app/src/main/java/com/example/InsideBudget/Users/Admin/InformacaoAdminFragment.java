package com.example.InsideBudget.Users.Admin;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.InsideBudget.Modelo.Funcao;
import com.example.InsideBudget.Modelo.Produto;
import com.example.InsideBudget.Modelo.SingletonProjeto;
import com.example.InsideBudget.Modelo.User;
import com.example.InsideBudget.R;

import java.util.ArrayList;

public class InformacaoAdminFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private ArrayList<User> listaUsers;
    private ArrayList<Funcao> listaFuncoes;
    private ArrayList<Produto> listaProdutos;
    private SwipeRefreshLayout swipe;
    private TextView totalUsersComAcesso,totalUsersRegistosEspera ,totalUsersBanidos,totalUsersAdmins, totalUsersFornecedores,totalUsersInstaladores,totalProdutos ;
    private int iUserComAcesso=0,iUserRegistosEspera=0,iUsersBanidos=0,iUserAdmins=1,iUserFornecedores=0,iUsersInstaladores = 0 ;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_informacao_admin, container, false);
        setHasOptionsMenu(true);
        totalUsersComAcesso = rootview.findViewById(R.id.tVAdminTotalClientes);
        totalUsersRegistosEspera = rootview.findViewById(R.id.tVAdminTotalOrcamentos);
        totalUsersBanidos = rootview.findViewById(R.id.tVAdminTotalUsersBanidos);
        totalUsersAdmins = rootview.findViewById(R.id.tVAdminTotalUsersAdmins);
        totalUsersFornecedores = rootview.findViewById(R.id.tVAdminTotalUsersFornecedores);
        totalUsersInstaladores = rootview.findViewById(R.id.tVAdminTotalUsersInstaldores);
        totalProdutos = rootview.findViewById(R.id.tVAdminClienteTotalProdutos);
        swipe = rootview.findViewById(R.id.swiperefreshInformacaoAdmin);
        SingletonProjeto.getInstance(getContext()).getAllUsersAPI(getContext());
        SingletonProjeto.getInstance(getContext()).getAllDadosPessoaisAPI(getContext());
        SingletonProjeto.getInstance(getContext()).getAllProdutosAPI(getContext());
        SingletonProjeto.getInstance(getContext()).getAllTipologiaAPI(getContext());
        SingletonProjeto.getInstance(getContext()).getAllFuncoesAdminAPI(getContext());
        listaUsers= SingletonProjeto.getInstance(getContext()).getUserDB();
        listaFuncoes= SingletonProjeto.getInstance(getContext()).getFuncoeDB();
        listaProdutos= SingletonProjeto.getInstance(getContext()).getProdutosDB();
        for (User user: listaUsers){
            if (user.getStatus()==10){
                iUserComAcesso++;
            }else if ( user.getStatus()==9){
                iUserRegistosEspera++;
            }else if ( user.getStatus()==0){
                iUsersBanidos++;
            }
        }

        for (Funcao funcao: listaFuncoes){
            if (funcao.getItem_Name().equals("admin") ){
                for (User user: listaUsers) {
                    if(funcao.getUser_id() == user.getId() && user.getStatus()==10)  {
                        iUserAdmins++;
                    }
                }
            }else if (funcao.getItem_Name().equals("fornecedor")){
                for (User user: listaUsers) {
                    if(funcao.getUser_id() == user.getId() && user.getStatus()==10)  {
                        iUserFornecedores++;
                    }
                }
            }else if (funcao.getItem_Name().equals("instalador")){
                for (User user: listaUsers) {
                    if(funcao.getUser_id() == user.getId() && user.getStatus()==10)  {
                        iUsersInstaladores++;
                    }
                }
            }
        }
        totalUsersComAcesso.setText(Integer.toString(iUserComAcesso));
        totalUsersRegistosEspera.setText(Integer.toString(iUserRegistosEspera));
        totalUsersBanidos.setText(Integer.toString(iUsersBanidos));
        totalUsersAdmins.setText(Integer.toString(iUserAdmins));
        totalUsersFornecedores.setText(Integer.toString(iUserFornecedores));
        totalUsersInstaladores.setText(Integer.toString(iUsersInstaladores));
        totalProdutos.setText(Integer.toString(listaProdutos.size()));

        swipe.setOnRefreshListener(this);
        return rootview;
    }

    @Override
    public void onRefresh() {
        iUserComAcesso=0;
        iUserRegistosEspera=0;
        iUsersBanidos=0;
        iUserAdmins=1;
        iUserFornecedores=0;
        iUsersInstaladores = 0;
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
        SingletonProjeto.getInstance(getContext()).getAllUsersAPI(getContext());
        SingletonProjeto.getInstance(getContext()).getAllDadosPessoaisAPI(getContext());
        SingletonProjeto.getInstance(getContext()).getAllProdutosAPI(getContext());
        SingletonProjeto.getInstance(getContext()).getAllTipologiaAPI(getContext());
        SingletonProjeto.getInstance(getContext()).getAllFuncoesAdminAPI(getContext());
        listaUsers= SingletonProjeto.getInstance(getContext()).getUserDB();
        listaFuncoes= SingletonProjeto.getInstance(getContext()).getFuncoeDB();
        listaProdutos= SingletonProjeto.getInstance(getContext()).getProdutosDB();
            }},1000);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
        for (User user: listaUsers){
            if (user.getStatus()==10){
                iUserComAcesso++;
            }else if ( user.getStatus()==9){
                iUserRegistosEspera++;
            }else if ( user.getStatus()==0){
                iUsersBanidos++;
            }
        }

        for (Funcao funcao: listaFuncoes){
            if (funcao.getItem_Name().equals("admin") ){
                for (User user: listaUsers) {
                    if(funcao.getUser_id() == user.getId() && user.getStatus()==10)  {
                        iUserAdmins++;
                    }
                }
            }else if (funcao.getItem_Name().equals("fornecedor")){
                for (User user: listaUsers) {
                    if(funcao.getUser_id() == user.getId() && user.getStatus()==10)  {
                        iUserFornecedores++;
                    }
                }
            }else if (funcao.getItem_Name().equals("instalador")){
                for (User user: listaUsers) {
                    if(funcao.getUser_id() == user.getId() && user.getStatus()==10)  {
                        iUsersInstaladores++;
                    }
                }
            }
        }
        totalUsersComAcesso.setText(Integer.toString(iUserComAcesso));
        totalUsersRegistosEspera.setText(Integer.toString(iUserRegistosEspera));
        totalUsersBanidos.setText(Integer.toString(iUsersBanidos));
        totalUsersAdmins.setText(Integer.toString(iUserAdmins));
        totalUsersFornecedores.setText(Integer.toString(iUserFornecedores));
        totalUsersInstaladores.setText(Integer.toString(iUsersInstaladores));
        totalProdutos.setText(Integer.toString(listaProdutos.size()));

        swipe.setRefreshing(false);
            }},1000);
    }
}