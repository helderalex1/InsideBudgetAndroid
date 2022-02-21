package com.example.InsideBudget.Modelo;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.provider.Settings;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.InsideBudget.Listener.ClientesListener;
import com.example.InsideBudget.Listener.DadosPessoaisListener;
import com.example.InsideBudget.Listener.FornecedorListener;
import com.example.InsideBudget.Listener.FuncoesListener;
import com.example.InsideBudget.Listener.InstaladorListener;
import com.example.InsideBudget.Listener.OrcamentoProdutosListener;
import com.example.InsideBudget.Listener.OrcamentosListener;
import com.example.InsideBudget.Listener.ProdutosListener;
import com.example.InsideBudget.Listener.RegistarListener;
import com.example.InsideBudget.Listener.TipologiaListener;
import com.example.InsideBudget.Listener.UsersListener;
import com.example.InsideBudget.MenuMainActivity;
import com.example.InsideBudget.Listener.LoginListener;
import com.example.InsideBudget.R;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SingletonProjeto {


    private String loginMessage = null;
    private ArrayList<User> usersArray;
    private ArrayList<DadosPessoais> dadosPessoaisArray;
    private ArrayList<Funcao> funcoesArray;
    private ArrayList<Cliente> meusClientesArray;
    private ArrayList<Orcamento> meusOrcamentosArray;
    private ArrayList<Tipologia> tipologiasArray;
    private ArrayList<Produto> produtosArray;
    private ArrayList<OrcamentoProduto> orcamentoProdutosArray;
    private static SingletonProjeto instance = null;
    private int i = 0;
    private ArrayList<User> utilizadores;
    //declaraçao do volley
    private static RequestQueue volleyQueue = null;

    //declaraçao da SharedPreference
    private SharedPreferences sharedPreferences;

    private LoginListener loginListener;
    private RegistarListener registarListener;
    private UsersListener usersListener;
    private FuncoesListener funcoesListener;
    private DadosPessoaisListener dadosPessoaisListener;
    private ProdutosListener produtosListener;
    private ClientesListener clientesListener;
    private OrcamentosListener orcamentosListener;
    private OrcamentoProdutosListener orcamentoProdutosListener;
    private TipologiaListener tipologiasListener;
    private InstaladorListener instaladorListener;
    private FornecedorListener fornecedorListener;

    private AllDBHelper allDBHelper = null;

    public static String urlMain= "http://192.168.191.135/projeto/backend/web/api/";
    public static final String urlLogin= "user/login";
    public static final String urlRegistar= "user/registar";
    private static final String urlUserToken= "usertoken/user";
    private static final String urlFuncaoUsers= "auth-assignment/funcao";
    private static final String urlFuncoes= "authrule/funcoes";
    private static final String urlDadosPessoais= "dados-pessoais/dados";
    private static final String urlUserTokenBanir= "usertoken/userbanir";
    private static final String urlTodosProdutos= "produto/produtos";
    private static final String urlMeusClientes= "cliente/clientes";
    private static final String urlMeusOrcamentos= "orcamento/orcamentos";
    private static final String urlMeusOrcamentoProduto= "orcamento-produto/orcamentosprodutos";
    private static final String urlTipologia= "tipologia/tipologias";
    private static final String urlAddOrcamento= "orcamento";
    private static final String urlAddCliente= "cliente";
    private static final String urlAddOrcamentoProduto= "orcamento-produto";
    private static final String urlAddProduto= "produto";

    private static int flagOrcamento=0;
    private static int quantidadeOrcamentos=0;

    public static synchronized SingletonProjeto getInstance(Context context) {
        if (instance == null) {
            instance = new SingletonProjeto(context);
            volleyQueue = Volley.newRequestQueue(context);
        }
        return instance;
    }

    private SingletonProjeto(Context context) {
        //inicialização dos array
        usersArray = new ArrayList<User>();
        dadosPessoaisArray = new ArrayList<DadosPessoais>();
        funcoesArray = new ArrayList<Funcao>();
        meusClientesArray = new ArrayList<Cliente>();
        meusOrcamentosArray = new ArrayList<Orcamento>();
        tipologiasArray = new ArrayList<Tipologia>();
        produtosArray = new ArrayList<Produto>();
        orcamentoProdutosArray = new ArrayList<OrcamentoProduto>();
        allDBHelper = new AllDBHelper(context);
    }

    public void setLoginListener(LoginListener loginListener){
        this.loginListener = loginListener;
    }

    public void setUsersListener(UsersListener usersListener){
        this.usersListener = usersListener;
    }

    public void setFuncoesListener(FuncoesListener funcoesListener){
        this.funcoesListener = funcoesListener;
    }

    public void setDadosPessoaisListener(DadosPessoaisListener dadosPessoaisListener){
        this.dadosPessoaisListener = dadosPessoaisListener;
    }

    public void setProdutosListener(ProdutosListener produtosListener){
        this.produtosListener = produtosListener;
    }

    public void setClientesListener(ClientesListener clientesListener){
        this.clientesListener = clientesListener;
    }

    public void setOrcamentosListener(OrcamentosListener orcamentosListener){
        this.orcamentosListener = orcamentosListener;
    }

    public void setOrcamentoProdutosListener(OrcamentoProdutosListener orcamentoProdutosListener){
        this.orcamentoProdutosListener = orcamentoProdutosListener;
    }

    public void setTipologiasListener(TipologiaListener tipologiasListener){
        this.tipologiasListener = tipologiasListener;
    }
    public void setInstaladorListener(InstaladorListener instaladorListener){
        this.instaladorListener = instaladorListener;
    }
    public void setRegistarListener(RegistarListener registarListener){
        this.registarListener = registarListener;
    }
    public void setFornecedorListener(FornecedorListener fornecedorListener){
        this.fornecedorListener = fornecedorListener;
    }

//------------------------------------- Inicio Array-------------------------------
    public ArrayList<User> getUserDB() {
        usersArray = allDBHelper.getAllUsersDB();
        return new ArrayList<>(usersArray);
    }

    public User getUserArray(int user_id){
        for (User user: usersArray) {
            if(user.getId()==user_id)
            {
                return user;
            }
        }
        return null;
    }

    public ArrayList<DadosPessoais> getDadosPessoaisDB() {
        dadosPessoaisArray = allDBHelper.getAllDadosPessoaisDB();
        return new ArrayList<>(dadosPessoaisArray);
    }
    public DadosPessoais getDadosPessoaisArray(int user_id){
        for (DadosPessoais dadosPessoais: dadosPessoaisArray) {
            if(dadosPessoais.getUser_id()==user_id)
            {
                return dadosPessoais;
            }
        }
        return null;
    }

    public ArrayList<Funcao> getFuncoeDB() {
        funcoesArray = allDBHelper.getAllFuncoesDB();
        return new ArrayList<>(funcoesArray);
    }

    public Funcao getFuncaoArray(int user_id){
        for (Funcao funcao: funcoesArray) {
            if(funcao.getUser_id()==user_id)
            {
                return funcao;
            }
        }
        return null;
    }

    public ArrayList<Produto> getProdutosDB() {
        produtosArray = allDBHelper.getAllProdutosDB();
        return new ArrayList<>(produtosArray);
    }

    public Produto getProdutoArray(int user_id,int produto_id){
        for (Produto produto: produtosArray) {
            if(produto.getFornecedor_id()==user_id || produto_id == produto.getId() )
            {
                System.out.println("-->op" + produto.getNome() + "|" + produto_id);
                return produto;
            }
        }
        return null;
    }

    public ArrayList<Cliente> getMeusClientesDB() {
        meusClientesArray = allDBHelper.getAllMeusClientesDB();
        System.out.println("-->informacaoclie"+meusClientesArray.size());
        return new ArrayList<>(meusClientesArray);
    }

    public Cliente getMeusClientesArray(int user_id, int cliente_id){
        for (Cliente cliente: meusClientesArray) {
            if(cliente.getUser_id()==user_id || cliente.getId() == cliente_id)
            {
                return cliente;
            }
        }
        return null;
    }

    public ArrayList<Orcamento> getMeusOrcamentosDB() {
        meusOrcamentosArray = allDBHelper.getAllMeusOrcamentosDB();

        System.out.println("informacaoorcame"+meusOrcamentosArray.size());
        return new ArrayList<>(meusOrcamentosArray);
    }

    public Orcamento getMeusOrcamentosArray(int orcamento_id, String orcamento_uid, int cliente_id){
        System.out.println("ccarra: " + orcamento_id +"|"+meusOrcamentosArray.size());
            for (Orcamento orcamento : meusOrcamentosArray) {
                if (orcamento.getId() == orcamento_id || orcamento.getUid().equals(orcamento_uid) || orcamento.getCliente_id() == cliente_id) {
                    return orcamento;
                }

        }
        return null;
    }

    public ArrayList<OrcamentoProduto> getMeusOrcamentosProdutosDB() {
        orcamentoProdutosArray = allDBHelper.getAllMeusOrcamentoProdutosDB();
        System.out.println("-->informacaoa:"+orcamentoProdutosArray.size());
        return new ArrayList<>(orcamentoProdutosArray);
    }

    public OrcamentoProduto getMeusOrcamentoProdutosArray(int produto_id){
        for (OrcamentoProduto orcamentoProduto: orcamentoProdutosArray) {
            if(orcamentoProduto.getProduto_Id()==produto_id )
            {
                return orcamentoProduto;
            }
        }
        return null;
    }

    public ArrayList<Tipologia> getTipologiasDB() {
        tipologiasArray = allDBHelper.getAllTipologiasDB();
        return new ArrayList<>(tipologiasArray);
    }

    public Tipologia getTipologiaArray(int tipologia_id){
        for (Tipologia tipologia: tipologiasArray) {
            if(tipologia.getId()==tipologia_id)
            {
                return tipologia;
            }
        }
        return null;
    }


//------------------------------------- Add -------------------------------

    public void addUserDB(User user){
        allDBHelper.addUserDB(user);
    }

    public void addUsersDB(ArrayList<User> users) {
        allDBHelper.delAllUsersDB();
        for (User user : users) {
            addUserDB(user);
        }
    }
    public void addDadosPessoaisDB(DadosPessoais dadosPessoais){
        allDBHelper.addDadosPessoaisDB(dadosPessoais);
    }

    public void addDadosPessoaissDB(ArrayList<DadosPessoais> dadosPessoaiss) {
        allDBHelper.delAllDadosPessoaisDB();
        for (DadosPessoais dadosPessoais : dadosPessoaiss) {
            addDadosPessoaisDB(dadosPessoais);
        }
    }

    public void addFuncaoDB(Funcao funcao){
        allDBHelper.addFuncaoDB(funcao);
    }

    public void addFuncoesDB(ArrayList<Funcao> funcoes) {
        allDBHelper.dellAllFuncoesDB();
        for (Funcao funcao : funcoes) {
            addFuncaoDB(funcao);
        }
    }

    public void addProdutoDB(Produto produto){
        allDBHelper.addProdutoDB(produto);
    }

    public void addProdutosDB(ArrayList<Produto> produtos) {
        allDBHelper.dellAllProdutosDB();
        for (Produto produto : produtos) {
            addProdutoDB(produto);
        }
    }

    public void addClienteDB(Cliente cliente){
        allDBHelper.addClienteDB(cliente);
    }

    public void addClientesDB(ArrayList<Cliente> clientes) {
        allDBHelper.dellAllMeusClientesDB();
        for (Cliente cliente : clientes) {
            addClienteDB(cliente);
        }
    }

    public void addOrcamento(Orcamento orcamento){
        allDBHelper.addOrcamentoDB(orcamento);
    }

    public void addOrcamentosDB(ArrayList<Orcamento> orcamentos) {
        allDBHelper.dellAllMeusOrcamentosDB();
        for (Orcamento orcamento : orcamentos) {
            addOrcamento(orcamento);
        }
    }

    public void addOrcamentoProdutos(OrcamentoProduto orcamentoProduto){
        allDBHelper.addProdutoOrcamentoDB(orcamentoProduto);
    }

    public void addOrcamentoProdutosDB(ArrayList<OrcamentoProduto> orcamentoProdutos) {
        allDBHelper.dellAllMeusOrcamentoProdutosDB();
        for (OrcamentoProduto orcamentoProduto : orcamentoProdutos) {
            addOrcamentoProdutos(orcamentoProduto);
        }
    }

    public void addTipologias(Tipologia tipologia){
        allDBHelper.addTipologiasDB(tipologia);
    }

    public void addTipologiasDB(ArrayList<Tipologia> tipologias) {
        allDBHelper.dellAllTipologiasDB();
        for (Tipologia tipologia : tipologias) {
            addTipologias(tipologia);
        }
    }



    public void loginApi(String username, String password, Context context) {
        if (isInternetOn((context))) {
            JSONObject body = new JSONObject();
            try {
                body.put("username",username);
                body.put("password",password);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JsonObjectRequest jsonObjectRequest =
                    new JsonObjectRequest(Request.Method.POST, urlMain + urlLogin ,body, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            boolean login = parserJsonLOGIN(response, password, context);
                            if(login) {
                                sharedPreferences = context.getSharedPreferences(MenuMainActivity.PREF_USER, Context.MODE_PRIVATE);
                                String token = sharedPreferences.getString(MenuMainActivity.TOKEN, null);
                                loginListener.onValidateLogin(token, username);
                                Toast.makeText(context,"Conta logada com sucesso", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(context,loginMessage, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            System.out.println("-->login"+ error.getMessage()+error.toString()+error.getLocalizedMessage()+error.networkResponse);
                            if (error == null || error.networkResponse == null) {
                                return;
                            }
                            String body;
                            final String statusCode = String.valueOf(error.networkResponse.statusCode);
                            try {
                                body = new String(error.networkResponse.data,"UTF-8");
                                System.out.println("-->login"+body);
                            } catch (UnsupportedEncodingException e) {
                                System.out.println("-->login"+e.getMessage());
                            }
                        }
                    });

            volleyQueue.add(jsonObjectRequest);
        }else{
            Toast.makeText(context, "Não é possível efetuar o login enquanto não tiver a internet dispovível.", Toast.LENGTH_SHORT).show();
        }
    }

    public void registarApi(String username, String nomeCompleto,String email,String empresa,String morada,String pais,String cidade,String telefone,String funcao,String password, Context context) {
        if (isInternetOn((context))) {
            JSONObject body = new JSONObject();
            try {
                body.put("username",username);
                body.put("nomecompleto",nomeCompleto);
                body.put("email",email);
                body.put("empresa",empresa);
                body.put("morada",morada);
                body.put("pais",pais);
                body.put("cidade",cidade);
                body.put("telefone",telefone);
                body.put("funcao",funcao);
                body.put("password",password);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JsonObjectRequest jsonObjectRequest =
                    new JsonObjectRequest(Request.Method.POST, urlMain + urlRegistar ,body, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            String registar = parserJsonRegistar(response, context);
                            Toast.makeText(context,registar,Toast.LENGTH_SHORT).show();
                            if(registar.equals("Registo concluido com sucesso")) {
                                if(registarListener !=null) {
                                    registarListener.onRegistarTrue();
                                }
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            System.out.println("-->registar"+ error.getMessage()+error.toString()+error.getLocalizedMessage()+error.networkResponse);
                            if (error == null || error.networkResponse == null) {
                                return;
                            }
                            String body;
                            final String statusCode = String.valueOf(error.networkResponse.statusCode);
                            try {
                                body = new String(error.networkResponse.data,"UTF-8");
                                System.out.println("-->registar"+body);
                            } catch (UnsupportedEncodingException e) {
                                System.out.println("-->registar"+e.getMessage());
                            }
                        }
                    });

            volleyQueue.add(jsonObjectRequest);
        }else{
            Toast.makeText(context, "Não é possível efetuar o login enquanto não tiver a internet dispovível.", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean parserJsonLOGIN(JSONObject response, String password, final Context context){
        SharedPreferences sharedPreferences;
        int id;
        String username=null ,email=null, nome=null, empresa=null, morada=null , pais=null,cidade=null, telefone=null, funcao=null, authkey=null;

        try {
            if (response.getString("login").equals("true")){
                sharedPreferences= context.getSharedPreferences(MenuMainActivity.PREF_USER,Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                id = response.getInt("id");
                username = response.getString("username");
                email= response.getString("email");
                nome= response.getString("nome");
                empresa = response.getString("empresa");
                morada = response.getString("morada");
                pais = response.getString("pais");
                cidade = response.getString("cidade");
                telefone = response.getString("telefone");
                authkey = response.getString("auth_key");
                funcao = response.getString("funcao");
                editor.clear();
                editor.putString(MenuMainActivity.ID,Integer.toString(id));
                editor.putString(MenuMainActivity.USERNAME, username);
                editor.putString(MenuMainActivity.EMAIL,email);
                editor.putString(MenuMainActivity.NOME, nome);
                editor.putString(MenuMainActivity.EMPRESA, empresa);
                editor.putString(MenuMainActivity.MORADA, morada);
                editor.putString(MenuMainActivity.PAIS, pais);
                editor.putString(MenuMainActivity.CIDADE, cidade);
                editor.putString(MenuMainActivity.TELEFONE, telefone);
                editor.putString(MenuMainActivity.TOKEN,authkey);
                editor.putString(MenuMainActivity.FUNCAO,funcao);
                editor.putString(MenuMainActivity.PASSWORD,password);
                editor.apply();
                return true;
            }else{
                loginMessage = response.getString("texto");
                return false ;
            }

        }catch (JSONException e){
            e.printStackTrace();
            System.out.println("--> jsonexception" + e.getMessage());
        }
        return false;
    }
    public String parserJsonRegistar(JSONObject response, final Context context){
        try {
            if (response.getString("registar").equals("true")){
                System.out.println("-->registatrue-->"+response.getString("texto"));

                return response.getString("texto");
            }else{
                System.out.println("-->registarfalse-->"+response.getString("texto"));
                return response.getString("texto") ;
            }

        }catch (JSONException e){
            e.printStackTrace();
            System.out.println("--> jsonexception" + e.getMessage());
        }
        return null;
    }

    public void getAllUsersAPI(final Context context) {
        if (!isInternetOn(context)) {
            Toast.makeText(context, "Sem Internet", Toast.LENGTH_SHORT).show();
            usersArray = allDBHelper.getAllUsersDB();
            if (usersListener != null) {
                usersListener.onRefreshListaUsers(usersArray);
            }
        } else {
            sharedPreferences= context.getSharedPreferences(MenuMainActivity.PREF_USER,Context.MODE_PRIVATE);
            final String token = sharedPreferences.getString(MenuMainActivity.TOKEN,null);
            final String id = sharedPreferences.getString(MenuMainActivity.ID,null);
            System.out.println("-->9999" +urlMain + urlUserToken  + "?token=" + token);
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, urlMain + urlUserToken  + "?auth_key=" + token , null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    // Fazer o Parser da Resposta
                    usersArray = parserJsonUsers(response, Integer.parseInt(id));
                    addUsersDB(usersArray);
                    //Representar os livros nas vistas
                    if (usersListener != null) {
                        usersListener.onRefreshListaUsers(usersArray);
                    }
                    System.out.println("--> Users: " + usersArray);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println("-->aa" + error.toString() + error.getMessage());
                    Toast.makeText(context, "Erro a comunicar com o servidor", Toast.LENGTH_SHORT).show();
                    usersArray = allDBHelper.getAllUsersDB();
                    if (usersListener != null) {
                        usersListener.onRefreshListaUsers(usersArray);
                    }
                }
            });
            volleyQueue.add(jsonArrayRequest);
        }
    }

    public void getAllDadosPessoaisAPI(final Context context) {
        if (!isInternetOn(context)) {
            dadosPessoaisArray = allDBHelper.getAllDadosPessoaisDB();
            if (dadosPessoaisListener != null) {
                dadosPessoaisListener.onRefreshListaDadospessoais(dadosPessoaisArray);
            }

        } else {
            sharedPreferences= context.getSharedPreferences(MenuMainActivity.PREF_USER,Context.MODE_PRIVATE);
            final String token = sharedPreferences.getString(MenuMainActivity.TOKEN,null);
            System.out.println("-->DadosPessoais:" +urlMain + urlDadosPessoais  + "?auth_key=" + token);
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, urlMain + urlDadosPessoais  + "?auth_key=" + token , null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    // Fazer o Parser da Resposta
                    dadosPessoaisArray = parserJsonDadosPessoais(response);
                    addDadosPessoaissDB(dadosPessoaisArray);
                    System.out.println("-->2345" + dadosPessoaisArray.get(0).getNomecompleto());
                    //Representar os livros nas vistas
                    if (usersListener != null) {
                        usersListener.onRefreshListaUsers(usersArray);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println("-->onerrorresponsedadospessoais" + error.toString() + error.getMessage());
                    Toast.makeText(context, "Erro a comunicar com o servidor", Toast.LENGTH_SHORT).show();
                    dadosPessoaisArray = allDBHelper.getAllDadosPessoaisDB();
                    if (dadosPessoaisListener != null) {
                        dadosPessoaisListener.onRefreshListaDadospessoais(dadosPessoaisArray);
                    }
                }
            });
            volleyQueue.add(jsonArrayRequest);
        }
    }

    public void getAllFuncoesAdminAPI(final Context context){
        if(!isInternetOn(context)){
            Toast.makeText(context, "Sem Internet", Toast.LENGTH_SHORT).show();
            funcoesArray=allDBHelper.getAllFuncoesDB();
            if(funcoesListener!=null){
                funcoesListener.onRefreshListaFuncao(funcoesArray);
            }
        }else {

            sharedPreferences= context.getSharedPreferences(MenuMainActivity.PREF_USER,Context.MODE_PRIVATE);
            final String token = sharedPreferences.getString(MenuMainActivity.TOKEN,null);
            final String funcao = sharedPreferences.getString(MenuMainActivity.FUNCAO,null);

            JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, urlMain + urlFuncaoUsers+ "?auth_key=" + token + "&" + "funcao=" + funcao, null, new Response.Listener<JSONArray>() {
           // JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, urlMain + urlFuncoes, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    funcoesArray = parserJsonFuncoes(response);
                    addFuncoesDB(funcoesArray);
                    if(funcoesListener!=null){
                        funcoesListener.onRefreshListaFuncao(funcoesArray);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    if(funcoesListener!=null){
                        funcoesListener.onRefreshListaFuncao(funcoesArray);
                    }
                    System.out.println("-->addclienteerro"+ error.getMessage()+error.toString()+error.getLocalizedMessage()+error.networkResponse);
                    if (error == null || error.networkResponse == null) {
                        return;
                    }
                    String body;
                    final String statusCode = String.valueOf(error.networkResponse.statusCode);
                    try {
                        body = new String(error.networkResponse.data,"UTF-8");
                        System.out.println("-->addclienteerro"+body);
                    } catch (UnsupportedEncodingException e) {
                        System.out.println("-->"+e.getMessage());
                    }
                }
            });
            volleyQueue.add(request);
        }
    }

    public void getAllProdutosAPI(final Context context){
        if(!isInternetOn(context)){
            Toast.makeText(context, "Sem Internet", Toast.LENGTH_SHORT).show();
            produtosArray=allDBHelper.getAllProdutosDB();
            if(produtosListener!=null){
                produtosListener.onRefreshListaTodosProdutos(produtosArray);
            }

            if(fornecedorListener !=null){
                fornecedorListener.onGetProdutos(produtosArray);
            }
        }else {

            sharedPreferences= context.getSharedPreferences(MenuMainActivity.PREF_USER,Context.MODE_PRIVATE);
            final String token = sharedPreferences.getString(MenuMainActivity.TOKEN,null);
            final String funcao = sharedPreferences.getString(MenuMainActivity.FUNCAO,null);
            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, urlMain + urlTodosProdutos+ "?auth_key=" + token, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    produtosArray = parserJsonProdutos(response);
                    addProdutosDB(produtosArray);
                    System.out.println("-->dialog");
                    if(produtosListener!=null){
                        produtosListener.onRefreshListaTodosProdutos(produtosArray);
                    }
                    if(fornecedorListener !=null){
                        fornecedorListener.onGetProdutos(produtosArray);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, "Erro a comunicar com o servidor", Toast.LENGTH_SHORT).show();
                    produtosArray=allDBHelper.getAllProdutosDB();
                    if(produtosListener!=null){
                        produtosListener.onRefreshListaTodosProdutos(produtosArray);
                    }
                    if(fornecedorListener !=null){
                        fornecedorListener.onGetProdutos(produtosArray);
                    }
                }
            });
            volleyQueue.add(request);
        }
    }

    public void getMeusClientesAPI(final Context context){
        if(!isInternetOn(context)){
            Toast.makeText(context, "Sem Internet", Toast.LENGTH_SHORT).show();
            meusClientesArray=allDBHelper.getAllMeusClientesDB();
            if(clientesListener!=null){
                clientesListener.onRefreshListaMeusClientes(meusClientesArray);
            }
            if(instaladorListener !=null) {
                instaladorListener.onGetClientes(meusClientesArray);
                System.out.println("-->informacaoclienteapi:" + meusClientesArray.size());
            }

            if(fornecedorListener !=null){
                fornecedorListener.onGetClientes(meusClientesArray);
            }
        }else {

            sharedPreferences= context.getSharedPreferences(MenuMainActivity.PREF_USER,Context.MODE_PRIVATE);
            final String id = sharedPreferences.getString(MenuMainActivity.ID,null);
            final String token = sharedPreferences.getString(MenuMainActivity.TOKEN,null);
            System.out.println("--> 476" + urlMain + urlMeusClientes+ "?auth_key=" + token + "&user_id=" + id );
            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, urlMain + urlMeusClientes+ "?auth_key=" + token + "&user_id=" + id , null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    meusClientesArray = null;
                    allDBHelper.dellAllMeusClientesDB();
                    meusClientesArray = parserJsonClientes(response);

                    addClientesDB(meusClientesArray);
                    System.out.println("-->239" + meusClientesArray.get(0).getUser_id());
                    if(clientesListener!=null){
                        clientesListener.onRefreshListaMeusClientes(meusClientesArray);
                    }
                    if(instaladorListener !=null) {
                        instaladorListener.onGetClientes(meusClientesArray);
                        System.out.println("-->informacaoclienteapi:" + meusClientesArray.size());
                    }

                    if(fornecedorListener !=null){
                        fornecedorListener.onGetClientes(meusClientesArray);
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, "Erro a comunicar com o servidor", Toast.LENGTH_SHORT).show();
                /*    meusClientesArray=allDBHelper.getAllMeusClientesDB();
                    if(clientesListener!=null){
                        clientesListener.onRefreshListaMeusClientes(meusClientesArray);
                    }*/
                }
            });
            volleyQueue.add(request);
            }
    }

    public void getMeusOrcamentosAPI(final Context context){
        if(!isInternetOn(context)){
//            Toast.makeText(context, "Sem Internet", Toast.LENGTH_SHORT).show();
            meusOrcamentosArray=allDBHelper.getAllMeusOrcamentosDB();
            if(orcamentosListener!=null){
                orcamentosListener.onRefreshListaMeusOrcamentos(meusOrcamentosArray);
            }
            if(instaladorListener != null ){
                instaladorListener.onGetOrcamentos(meusOrcamentosArray);
            }
            if(fornecedorListener != null ){
                fornecedorListener.onGetOrcamentos(meusOrcamentosArray);
            }
        }else {

            sharedPreferences= context.getSharedPreferences(MenuMainActivity.PREF_USER,Context.MODE_PRIVATE);
            final String token = sharedPreferences.getString(MenuMainActivity.TOKEN,null);
            final String id = sharedPreferences.getString(MenuMainActivity.ID,null);

i= 0;
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
       //     for(Cliente cliente: meusClientesArray) {

            //    System.out.println("-->meusorcamento " + urlMain + urlMeusOrcamentos + "?auth_key=" + token + "&cliente_id=" + cliente.getId());
                JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, urlMain + urlMeusOrcamentos + "?auth_key=" + token + "&user_id=" + id, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                      //  ArrayList<Orcamento> meusOrcamentosResponseArray = parserJsonOrcamentos(response);
                        meusOrcamentosArray = null;
                       meusOrcamentosArray = parserJsonOrcamentos(response);
                        //  System.out.println("-->pedido222" + parserJsonOrcamentos(response).toString() + parserJsonOrcamentos(response).size());

                        addOrcamentosDB(meusOrcamentosArray);
                        System.out.println("-->meusorcamentos" + meusClientesArray.size()+"|" + allDBHelper.getAllMeusOrcamentosDB().size());

                                   // System.out.println("-->meusorcamento " + cliente.getId() + "||" + meusClientesArray.size());
                                    if (orcamentosListener != null) {
                                        orcamentosListener.onRefreshListaMeusOrcamentos(allDBHelper.getAllMeusOrcamentosDB());
                                    }
                        System.out.println("-->pedido222" + meusClientesArray.size() +i);
                                    if(instaladorListener != null ){
                                            instaladorListener.onGetOrcamentos(meusOrcamentosArray);
                                    }
                                    if(fornecedorListener != null ){
                                            fornecedorListener.onGetOrcamentos(meusOrcamentosArray);
                                    }
                        }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("-->"+error.getMessage());
                        /*meusOrcamentosArray = allDBHelper.getAllMeusOrcamentosDB();
                        if (orcamentosListener != null) {
                            orcamentosListener.onRefreshListaMeusOrcamentos(meusOrcamentosArray);
                        }if(instaladorListener != null ){
                            instaladorListener.onGetOrcamentos(meusOrcamentosArray);
                        }
                        if(fornecedorListener != null ){
                            fornecedorListener.onGetOrcamentos(meusOrcamentosArray);
                        }*/
                        System.out.println("-->orcamento"+ error.getMessage()+error.toString()+error.getLocalizedMessage()+error.networkResponse);
                        if (error == null || error.networkResponse == null) {
                            return;
                        }
                        String body;
                        final String statusCode = String.valueOf(error.networkResponse.statusCode);
                        try {
                            body = new String(error.networkResponse.data,"UTF-8");
                            System.out.println("-->orcamento"+body);
                        } catch (UnsupportedEncodingException e) {
                            System.out.println("-->orcamento"+e.getMessage());
                        }
                    }
                });
                volleyQueue.add(request);
            //}
        }},500);
        }
    }



    public void getMeusOrcamentoProdutosAPI(final Context context){
        if(!isInternetOn(context)){
//            Toast.makeText(context, "Sem Internet", Toast.LENGTH_SHORT).show();
            orcamentoProdutosArray=allDBHelper.getAllMeusOrcamentoProdutosDB();

            if(orcamentoProdutosListener!=null){
                orcamentoProdutosListener.onRefreshOrcamentoProdutos(orcamentoProdutosArray);
            }
            if (instaladorListener != null) {
                instaladorListener.onGetOrcamentoProdutos(orcamentoProdutosArray.size(), orcamentoProdutosArray);
            }
            if (fornecedorListener != null) {
                fornecedorListener.onGetOrcamentoProdutos(orcamentoProdutosArray.size(), orcamentoProdutosArray);
            }
        }else {
            sharedPreferences= context.getSharedPreferences(MenuMainActivity.PREF_USER,Context.MODE_PRIVATE);
            final String token = sharedPreferences.getString(MenuMainActivity.TOKEN,null);
            final String id = sharedPreferences.getString(MenuMainActivity.ID,null);
            //getMeusOrcamentosAPI(context);
       //     allDBHelper.dellAllMeusOrcamentoProdutosDB();
           /* orcamentoProdutosArray=null;
            meusOrcamentosArray=null;
            meusOrcamentosArray=getMeusOrcamentosDB();*/
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
            System.out.println("-->informacaoorcamentoproapi:antes"+ allDBHelper.getAllMeusOrcamentosDB().size()+"|"+meusOrcamentosArray.size());
       //     for (Orcamento orcamento:meusOrcamentosArray) {
                System.out.println("-->informacaoorcamentoproapi:depois");
                JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, urlMain + urlMeusOrcamentoProduto + "?auth_key=" + token + "&user_id=" + Integer.parseInt(id), null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        System.out.println("-->informacaoorcamentoproapi:depoisdepois");
                        orcamentoProdutosArray = parserJsonOrcamentoProdutos(response);
                        addOrcamentoProdutosDB(orcamentoProdutosArray);
                        System.out.println("-->informacaoorcamentoproapi:" + orcamentoProdutosArray.size());
                        if (orcamentoProdutosListener != null) {
                            orcamentoProdutosListener.onRefreshOrcamentoProdutos(orcamentoProdutosArray);
                        }
                        if (instaladorListener != null) {
                                instaladorListener.onGetOrcamentoProdutos(orcamentoProdutosArray.size(), orcamentoProdutosArray);
                        }
                        if (fornecedorListener != null) {
                            fornecedorListener.onGetOrcamentoProdutos(orcamentoProdutosArray.size(), orcamentoProdutosArray);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("-->552" + error.getMessage());
                        Toast.makeText(context, "Erro a comunicar com o servidorb", Toast.LENGTH_SHORT).show();
                       /* if (orcamentoProdutosListener != null) {
                            orcamentoProdutosListener.onRefreshOrcamentoProdutos(orcamentoProdutosArray);

                        }
                        if (instaladorListener != null) {
                            instaladorListener.onGetOrcamentoProdutos(orcamentoProdutosArray.size(), orcamentoProdutosArray);
                        }
                        if (fornecedorListener != null) {
                            fornecedorListener.onGetOrcamentoProdutos(orcamentoProdutosArray.size(), orcamentoProdutosArray);
                        }*/
                        System.out.println("-->orcamentoproduto"+ error.getMessage()+error.toString()+error.getLocalizedMessage()+error.networkResponse);
                        if (error == null || error.networkResponse == null) {
                            return;
                        }
                        String body;
                        final String statusCode = String.valueOf(error.networkResponse.statusCode);
                        try {
                            body = new String(error.networkResponse.data,"UTF-8");
                            System.out.println("-->orcamentoproduto"+body);
                        } catch (UnsupportedEncodingException e) {
                            System.out.println("-->orcamentoproduto"+e.getMessage());
                        }
                    }
                });
                volleyQueue.add(request);
                 }},500);
         }
    }

    public void getAllTipologiaAPI(final Context context){
        if(!isInternetOn(context)){
            Toast.makeText(context, "Sem Internet", Toast.LENGTH_SHORT).show();
            tipologiasArray=allDBHelper.getAllTipologiasDB();
            if(tipologiasListener!=null){
                tipologiasListener.onRefreshListaTipologias(tipologiasArray);
            }
        }else {
            tipologiasArray =getTipologiasDB();
            sharedPreferences= context.getSharedPreferences(MenuMainActivity.PREF_USER,Context.MODE_PRIVATE);
            final String token = sharedPreferences.getString(MenuMainActivity.TOKEN,null);
            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, urlMain + urlTipologia + "?auth_key=" + token , null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    tipologiasArray = parserJsonTipologia(response);
                    addTipologiasDB(tipologiasArray);
                    if(tipologiasListener!=null){
                        tipologiasListener.onRefreshListaTipologias(tipologiasArray);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, "Erro a comunicar com o servidor", Toast.LENGTH_SHORT).show();
                    tipologiasArray=allDBHelper.getAllTipologiasDB();
                    if(tipologiasListener!=null){
                        tipologiasListener.onRefreshListaTipologias(tipologiasArray);
                    }
                }
            });
            volleyQueue.add(request);
        }
    }

    public static ArrayList<User> parserJsonUsers(JSONArray response, int id) {
        ArrayList<User> listaUsersComAcesso = new ArrayList<>();
        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject user = (JSONObject) response.get(i);
                if(user.getInt("id") != id) {
                    int userId = user.getInt("id");
                    String userUsername = user.getString("username");
                    String userEmail = user.getString("email");
                    int userStatus = user.getInt("status");
                    User addUser = new User(userId, userUsername, userEmail, userStatus);
                    listaUsersComAcesso.add(addUser);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return listaUsersComAcesso;
    }

    public static ArrayList<DadosPessoais> parserJsonDadosPessoais(JSONArray response) {
        ArrayList<DadosPessoais> listaDadosPessoais = new ArrayList<>();

        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject dadospessoais = (JSONObject) response.get(i);
                int dadospessoaisId = dadospessoais.getInt("id");
                String dadospessoaisNomeCompleto = dadospessoais.getString("nomecompleto");
                String dadospessoaisEmpresa = dadospessoais.getString("empresa");
                String dadospessoaisPais = dadospessoais.getString("pais");
                String dadospessoaisCidade = dadospessoais.getString("cidade");
                String dadospessoaisMorada = dadospessoais.getString("morada");
                int dadospessoaisTelefone = dadospessoais.getInt("telefone");
                int dadospessoaisUserId = dadospessoais.getInt("user_id");

                DadosPessoais addDados = new DadosPessoais(dadospessoaisId, dadospessoaisNomeCompleto, dadospessoaisEmpresa, dadospessoaisPais,
                        dadospessoaisCidade,dadospessoaisMorada,dadospessoaisTelefone,dadospessoaisUserId);
                listaDadosPessoais.add(addDados);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return listaDadosPessoais;
    }

    public static ArrayList<Funcao> parserJsonFuncoes(JSONArray response){
        ArrayList<Funcao> listaFuncoes = new ArrayList<>();
        try {
            for (int i = 0; i< response.length(); i++){
                JSONObject funcoes = (JSONObject) response.get(i);
                String funcaoItemName=funcoes.getString("item_name");
                int funcaoUser_id=funcoes.getInt("user_id");
                Funcao addFuncao= new Funcao(funcaoItemName,funcaoUser_id);
                listaFuncoes.add(addFuncao);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return listaFuncoes;
    }

    public static ArrayList<Produto> parserJsonProdutos(JSONArray response){
        ArrayList<Produto> listaProdutos = new ArrayList<>();
        try {
            for (int i = 0; i< response.length(); i++){
                JSONObject produtos = (JSONObject) response.get(i);
                int produtoId=produtos.getInt("id");
                String produtoNome=produtos.getString("nome");
                String produtoReferencia=produtos.getString("referencia");
                String funcaoDescricao=produtos.getString("descricao");
                Double produtoPreco= produtos.getDouble("preco");
                int produtoFornecedor_Id= produtos.getInt("fornecedor_id");
                int produtoTipologia_Id= produtos.getInt("tipologia_id");
                Produto addProduto= new Produto(produtoId,produtoNome,produtoReferencia, funcaoDescricao,produtoPreco.floatValue(),produtoFornecedor_Id,produtoTipologia_Id);
                listaProdutos.add(addProduto);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return listaProdutos;
    }

    public static ArrayList<Cliente> parserJsonClientes(JSONArray response){
        ArrayList<Cliente> listaClientes = new ArrayList<>();
        try {
            for (int i = 0; i< response.length(); i++){
                JSONObject clientes = (JSONObject) response.get(i);
                int clienteId=clientes.getInt("id");
                String clienteNome=clientes.getString("nome");
                String clienteEmpresa=clientes.getString("empresa");
                String clienteMorada=clientes.getString("morada");
                int clienteNif=clientes.getInt("nif");
                int clienteTelefone=clientes.getInt("telefone");
                String clienteEmail=clientes.getString("email");
                int clienteUser_id=clientes.getInt("user_id");

                Cliente addCliente= new Cliente(clienteId,clienteNome,clienteEmpresa,clienteMorada,clienteNif,clienteTelefone,clienteEmail,clienteUser_id);
                listaClientes.add(addCliente);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return listaClientes;
    }

    public static ArrayList<Orcamento> parserJsonOrcamentos(JSONArray response){
        ArrayList<Orcamento> listaOrcamentos = new ArrayList<>();
        try {
            System.out.println("-->pedido12");
            for (int i = 0; i< response.length(); i++){
                JSONObject orcamentos = (JSONObject) response.get(i);
                System.out.println("-->pedido2" + response.getString(i));
                int orcamentoId = orcamentos.getInt("id");
                String orcamentoNome = orcamentos.getString("nome");
                String orcamentoDescricao = orcamentos.getString("descricao");
                double orcamentoTotal = orcamentos.getDouble("total");
                String orcamentoData = orcamentos.getString("data");
                String orcamentoUid = orcamentos.getString("uid");
                int orcamentoCliente_id = orcamentos.getInt("cliente_id");
                int orcamentoUser_id = orcamentos.getInt("user_id");
                Orcamento addOrcamento = new Orcamento(orcamentoId, orcamentoNome, orcamentoDescricao, (float) orcamentoTotal, orcamentoData, orcamentoUid, orcamentoCliente_id, orcamentoUser_id);
                listaOrcamentos.add(addOrcamento);;
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return listaOrcamentos;
    }

    public static ArrayList<OrcamentoProduto> parserJsonOrcamentoProdutos(JSONArray response){
        ArrayList<OrcamentoProduto> listaOrcamentoProdutos = new ArrayList<>();
        try {
            for (int i = 0; i< response.length(); i++) {
                JSONObject orcamentoProdutos = (JSONObject) response.get(i);

                int orcamentoProdutosId = orcamentoProdutos.getInt("id");
                int orcamentoProdutosOrcamento_Id = orcamentoProdutos.getInt("orcamento_id");
                int orcamentoProdutosProduto_id = orcamentoProdutos.getInt("produto_id");
                int orcamentoProdutosQuantidade = orcamentoProdutos.getInt("quantidade");
                System.out.println("-->11" + "|" + orcamentoProdutosOrcamento_Id + "|" + orcamentoProdutosProduto_id + "|" + orcamentoProdutosQuantidade);
                OrcamentoProduto addOrcamentoProduto = new OrcamentoProduto(orcamentoProdutosId, orcamentoProdutosOrcamento_Id, orcamentoProdutosProduto_id, orcamentoProdutosQuantidade);
                listaOrcamentoProdutos.add(addOrcamentoProduto);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        System.out.println("-->ccc" + listaOrcamentoProdutos.size());
        return listaOrcamentoProdutos;
    }

    public static ArrayList<Tipologia> parserJsonTipologia(JSONArray response){
        ArrayList<Tipologia> listaTipologias = new ArrayList<>();
        try {
            for (int i = 0; i< response.length(); i++){
                JSONObject tipologias = (JSONObject) response.get(i);
                int tipologiaId=tipologias.getInt("id");
                String tipologiaNome=tipologias.getString("nome");
                Tipologia tipologia= new Tipologia(tipologiaId,tipologiaNome);
                listaTipologias.add(tipologia);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return listaTipologias;
    }

    public static boolean isInternetOn(Context context) {
        if(context != null) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

            return networkInfo != null && networkInfo.isConnected();
        }
        return false;
    }

    public void UserBanirPermitirAPI(final Context context, int user_id, boolean banir){
        if(!isInternetOn(context)){
            usersArray=allDBHelper.getAllUsersDB();
            if(usersListener!=null){
                usersListener.onRefreshListaUsers(usersArray);
            }

        }else {
            sharedPreferences= context.getSharedPreferences(MenuMainActivity.PREF_USER,Context.MODE_PRIVATE);
            final String token = sharedPreferences.getString(MenuMainActivity.TOKEN,null);
            final String funcao = sharedPreferences.getString(MenuMainActivity.FUNCAO,null);
            //   System.out.println("--> 230" + urlMain + urlFuncao+ "?auth_key=" + token + "&" + "funcao=" + funcao);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, urlMain + urlUserTokenBanir+ "?auth_key=" + token + "&" + "user_id=" + user_id + "&banir=" + banir, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                 //   usersArray = parserJsonUsers(response, user_id);
                    System.out.println("-->2361" + usersArray.get(0).getStatus());
                    if(usersListener!=null){
                        usersListener.onRefreshListaUsers(usersArray);
                    }

                   // Toast.makeText(context, SingletonProjeto."Utilizador " + user.getUsername() + " aceite com sucesso", Toast.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println("-->526" + error.toString());
                }
            });
            volleyQueue.add(jsonObjectRequest);
        }
    }
    public static ArrayList<User> parserJsonUserBanir(JSONArray response1, int id) {
        ArrayList<User> listaUsersComAcesso = new ArrayList<>();
        try {
            for (int i = 0; i < response1.length(); i++) {
                JSONObject response = (JSONObject) response1.get(i);
                //if(response.getInt("status") == 10 && response.getInt("id") != id) {
                    int userId = response.getInt("id");
                    String userUsername = response.getString("username");
                    String userEmail = response.getString("email");
                    int userStatus = response.getInt("status");
                    User addUser = new User(userId, userUsername, userEmail, userStatus);
                    listaUsersComAcesso.add(addUser);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return listaUsersComAcesso;
    }
    public void addOrcamentoAPI(Orcamento orcamento, Context context) {
        String message= null;
        if (!isInternetOn(context)){
            message = context.getString(R.string.sem_ligacao_internet)+". Certifique que está conectado na internet";
            orcamentosListener.onErroListaMeusOrcamentos(message);
            return;
        }

        sharedPreferences= context.getSharedPreferences(MenuMainActivity.PREF_USER,Context.MODE_PRIVATE);
        final String token = sharedPreferences.getString(MenuMainActivity.TOKEN,null);
        final String user_id=sharedPreferences.getString(MenuMainActivity.ID,null);
        System.out.println("-->addorcamento" + orcamento.getCliente_id());
        JSONObject body = new JSONObject();
        try {
            body.put("nome",orcamento.getNome());
            body.put("descricao",orcamento.getDescricao());
            body.put("uid",orcamento.getUid());
            body.put("data",orcamento.getData());
            body.put("cliente_id",orcamento.getCliente_id());
            body.put("user_id",orcamento.getUser_id());
        } catch (JSONException e) {
            e.printStackTrace();
            System.out.println("-->addorcamentoer"+e.getMessage());
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, urlMain + urlAddOrcamento +"?auth_key="+token,body, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                System.out.println("-->detalhesorcamentosi");
                orcamentosListener.onAddOrcamento();
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("-->addorcamentoerro"+ error.getMessage()+error.toString()+error.getLocalizedMessage()+error.networkResponse);
                if (error == null || error.networkResponse == null) {
                    return;
                }
                String body;
                final String statusCode = String.valueOf(error.networkResponse.statusCode);
                try {
                    body = new String(error.networkResponse.data,"UTF-8");
                    System.out.println("-->addorcamentoerro"+body);
                } catch (UnsupportedEncodingException e) {
                    System.out.println("-->"+e.getMessage());
                }
            }
        });
        volleyQueue.add(request);
    }
    public void editOrcamentoAPI(Orcamento orcamento, Context context) {
        String message= null;
        if (!isInternetOn(context)){
//            message = context.getString(R.string.sem_ligacao_internet)+". Certifique que está conectado na internet";
            orcamentosListener.onErroListaMeusOrcamentos(message);
            return;
        }

        sharedPreferences= context.getSharedPreferences(MenuMainActivity.PREF_USER,Context.MODE_PRIVATE);
        final String token = sharedPreferences.getString(MenuMainActivity.TOKEN,null);
        final String user_id=sharedPreferences.getString(MenuMainActivity.ID,null);
        System.out.println("-->addorcamento" + orcamento.getCliente_id());
        JSONObject body = new JSONObject();
        try {
            body.put("nome",orcamento.getNome());
            body.put("descricao",orcamento.getDescricao());
            body.put("uid",orcamento.getUid());
            body.put("data",orcamento.getData());
            body.put("total",orcamento.getTotal());
            body.put("cliente_id",orcamento.getCliente_id());
            body.put("user_id",orcamento.getUser_id());
        } catch (JSONException e) {
            e.printStackTrace();
            System.out.println("-->addorcamentoer"+e.getMessage());
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, urlMain + urlAddOrcamento+"/"+orcamento.getId() +"?auth_key="+token,body, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                System.out.println("-->detalhesorcamentosi");
                orcamentosListener.onEditOrcamento();
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("-->addorcamentoerro"+ error.getMessage()+error.toString()+error.getLocalizedMessage()+error.networkResponse);
                if (error == null || error.networkResponse == null) {
                    return;
                }
                String body;
                final String statusCode = String.valueOf(error.networkResponse.statusCode);
                try {
                    body = new String(error.networkResponse.data,"UTF-8");
                    System.out.println("-->addorcamentoerro"+body);
                } catch (UnsupportedEncodingException e) {
                    System.out.println("-->"+e.getMessage());
                }
            }
        });
        volleyQueue.add(request);
    }

    public void addCLienteAPI(Cliente cliente, Context context){
        String message=null;
        if(!isInternetOn(context)){
            message=context.getString(R.string.sem_ligacao_internet);
            if(orcamentosListener!=null)
            {
                //orcamentosListener.onErroDetalhesUtilizadores(message);
            }
            return;
        }

        sharedPreferences=context.getSharedPreferences(MenuMainActivity.PREF_USER, Context.MODE_PRIVATE);
        final String token = sharedPreferences.getString(MenuMainActivity.TOKEN,null);
        final String id= sharedPreferences.getString(MenuMainActivity.ID,null);
        String nome=cliente.getNome();
        String empresa=cliente.getEmpresa();
        String morada=cliente.getMorada();
        String nif=Integer.toString(cliente.getNif());
        String telefone =Integer.toString(cliente.getTelefone());
        String email = cliente.getEmail();
        JSONObject body = new JSONObject();
        try {
            body.put("nome",nome);
            body.put("empresa",empresa);
            body.put("morada",morada);
            body.put("nif",nif);
            body.put("telefone",telefone);
            body.put("email",email);
            body.put("user_id",id);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println("-->"+urlMain + urlAddCliente+"?auth_key="+token);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, urlMain + "cliente"+"?auth_key="+token, body, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                JSONArray jsonArray = new JSONArray();
                jsonArray.put(response);
                Cliente cliente = parserJsonCliente(jsonArray);
                addClienteDB(cliente);
                System.out.println("-->sizeav" + meusClientesArray.size());
                if(clientesListener != null) {
                    clientesListener.onAddCliente();
                    clientesListener.onRefreshListaMeusClientes(meusClientesArray);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("-->addclienteerro"+ error.getMessage()+error.toString()+error.getLocalizedMessage()+error.networkResponse);
                if (error == null || error.networkResponse == null) {
                    return;
                }
                String body;
                final String statusCode = String.valueOf(error.networkResponse.statusCode);
                try {
                    body = new String(error.networkResponse.data,"UTF-8");
                    System.out.println("-->addclienteerro"+body);
                } catch (UnsupportedEncodingException e) {
                    System.out.println("-->"+e.getMessage());
                }
            }
        });

        volleyQueue.add(request);
    }

    public void editCLienteAPI(Cliente cliente, Context context){
        String message=null;
        if(!isInternetOn(context)){
            message=context.getString(R.string.sem_ligacao_internet);
            if(orcamentosListener!=null)
            {
                //orcamentosListener.onErroDetalhesUtilizadores(message);
            }
            return;
        }

        sharedPreferences=context.getSharedPreferences(MenuMainActivity.PREF_USER, Context.MODE_PRIVATE);
        final String token = sharedPreferences.getString(MenuMainActivity.TOKEN,null);
        final String id= sharedPreferences.getString(MenuMainActivity.ID,null);
        String cliente_id = Integer.toString(cliente.getId());
        String nome=cliente.getNome();
        String empresa=cliente.getEmpresa();
        String morada=cliente.getMorada();
        String nif=Integer.toString(cliente.getNif());
        String telefone =Integer.toString(cliente.getTelefone());
        String email = cliente.getEmail();
        System.out.println("-->addcliente"+cliente.getNome()+"|"+cliente.getEmpresa()+"|"+cliente.getEmpresa()+"|"+cliente.getMorada()+"|"+cliente.getNif()+"|"+cliente.getTelefone()+"|"+cliente.getEmail());
        JSONObject body = new JSONObject();
        try {
            body.put("nome",nome);
            body.put("empresa",empresa);
            body.put("morada",morada);
            body.put("nif",nif);
            body.put("telefone",telefone);
            body.put("email",email);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println("--editarcl>"+urlMain + "cliente/"+cliente_id+"?auth_key="+token);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, urlMain + "cliente/"+cliente_id+"?auth_key="+token, body, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                if(clientesListener !=null){
                    clientesListener.onEditCliente();
                }

                System.out.println("-->detalhesclienteeditapi");
                  //  clientesListener.onRefreshListaMeusClientes(meusClientesArray);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("-->addclienteerro"+ error.getMessage()+error.toString()+error.getLocalizedMessage()+error.networkResponse);
                if (error == null || error.networkResponse == null) {
                    return;
                }
                String body;
                final String statusCode = String.valueOf(error.networkResponse.statusCode);
                try {
                    body = new String(error.networkResponse.data,"UTF-8");
                    System.out.println("-->addclienteerro"+body);
                } catch (UnsupportedEncodingException e) {
                    System.out.println("-->addclienteerro"+e.getMessage());
                }
            }
        });

        volleyQueue.add(request);
    }
    public void addOrcamentoProdutoAPI(OrcamentoProduto orcamentoProduto, Context context){
        String message=null;
        if(!isInternetOn(context)){
            message=context.getString(R.string.sem_ligacao_internet);
            if(produtosListener !=null)
            {
            }
            return;
        }
        JSONObject body = new JSONObject();
        try {
            body.put("orcamento_id",orcamentoProduto.getOrcamento_Id());
            body.put("produto_id",orcamentoProduto.getProduto_Id());
            body.put("quantidade",orcamentoProduto.getQuantidade());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        sharedPreferences=context.getSharedPreferences(MenuMainActivity.PREF_USER, Context.MODE_PRIVATE);
        final String token = sharedPreferences.getString(MenuMainActivity.TOKEN,null);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, urlMain + urlAddOrcamentoProduto+"?auth_key="+token,body, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                allDBHelper.addProdutoOrcamentoDB(orcamentoProduto);
                if(produtosListener!=null){
                    produtosListener.onAddOrcamentoProduto();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(produtosListener!=null)
                {
                  //  produtosListener.onErroDetalhesprodutos(message);
                }
                System.out.println("-->addorcamentoproduto"+ error.getMessage()+error.toString()+error.getLocalizedMessage()+error.networkResponse);
                if (error == null || error.networkResponse == null) {
                    return;
                }
                String body;
                final String statusCode = String.valueOf(error.networkResponse.statusCode);
                try {
                    body = new String(error.networkResponse.data,"UTF-8");
                    System.out.println("-->addorcamentoproduto"+body);
                } catch (UnsupportedEncodingException e) {
                    System.out.println("-->addorcamentoproduto"+e.getMessage());
                }
            }
        });
        volleyQueue.add(request);
    }
    public void editOrcamentoProdutoAPI(OrcamentoProduto orcamentoProduto, Context context){
        String message=null;
        if(!isInternetOn(context)){
            message=context.getString(R.string.sem_ligacao_internet);
            if(produtosListener !=null)
            {
            }
            return;
        }
        JSONObject body = new JSONObject();
        System.out.println("-->addorcamentoproduto"+orcamentoProduto.getId()+"|"+orcamentoProduto.getOrcamento_Id()+"|"+orcamentoProduto.getProduto_Id()+"|"+orcamentoProduto.getQuantidade());
        try {
            body.put("id",orcamentoProduto.getId());
            body.put("orcamento_id",orcamentoProduto.getOrcamento_Id());
            body.put("produto_id",orcamentoProduto.getProduto_Id());
            body.put("quantidade",orcamentoProduto.getQuantidade());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        sharedPreferences=context.getSharedPreferences(MenuMainActivity.PREF_USER, Context.MODE_PRIVATE);
        final String token = sharedPreferences.getString(MenuMainActivity.TOKEN,null);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, urlMain + urlAddOrcamentoProduto+"/"+orcamentoProduto.getId()+"?auth_key="+token,body, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if(produtosListener!=null){
                 //z   produtosListener.onAddOrcamentoProduto();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(produtosListener!=null)
                {
                  //  produtosListener.onErroDetalhesprodutos(message);
                }
                System.out.println("-->addorcamentoproduto"+ error.getMessage()+error.toString()+error.getLocalizedMessage()+error.networkResponse);
                if (error == null || error.networkResponse == null) {
                    return;
                }
                String body;
                final String statusCode = String.valueOf(error.networkResponse.statusCode);
                try {
                    body = new String(error.networkResponse.data,"UTF-8");
                    System.out.println("-->addorcamentoproduto"+body);
                } catch (UnsupportedEncodingException e) {
                    System.out.println("-->addorcamentoproduto"+e.getMessage());
                }
            }
        });
        volleyQueue.add(request);
    }

    public void addProdutoAPI(Produto produto, Context context){
        if(!isInternetOn(context)){
            if(produtosListener !=null)
            {
            }
            return;
        }
        JSONObject body = new JSONObject();
        try {
            body.put("nome",produto.getNome());
            body.put("referencia",produto.getReferencia());
            body.put("descricao",produto.getDescricao());
            body.put("preco",produto.getPreco());
            body.put("tipologia_id",produto.getTipologia_id());
            body.put("fornecedor_id",produto.getFornecedor_id());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        sharedPreferences=context.getSharedPreferences(MenuMainActivity.PREF_USER, Context.MODE_PRIVATE);
        final String token = sharedPreferences.getString(MenuMainActivity.TOKEN,null);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, urlMain + urlAddProduto+"?auth_key="+token,body, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                allDBHelper.addProdutoDB(produto);
                if(produtosListener!=null){
                    produtosListener.onAddOrcamentoProduto();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(produtosListener!=null)
                {
                  //  produtosListener.onErroDetalhesprodutos(message);
                }
                System.out.println("-->addorcamentoproduto"+ error.getMessage()+error.toString()+error.getLocalizedMessage()+error.networkResponse);
                if (error == null || error.networkResponse == null) {
                    return;
                }
                String body;
                final String statusCode = String.valueOf(error.networkResponse.statusCode);
                try {
                    body = new String(error.networkResponse.data,"UTF-8");
                    System.out.println("-->addorcamentoproduto"+body);
                } catch (UnsupportedEncodingException e) {
                    System.out.println("-->addorcamentoproduto"+e.getMessage());
                }
            }
        });
        volleyQueue.add(request);
    }
    public void editProdutoAPI(Produto produto, Context context){
        String message=null;
        if(!isInternetOn(context)){
            message=context.getString(R.string.sem_ligacao_internet);
            if(produtosListener !=null)
            {
            }
            return;
        }
        JSONObject body = new JSONObject();
        try {
            body.put("id",produto.getId());
            body.put("nome",produto.getNome());
            body.put("referencia",produto.getReferencia());
            body.put("descricao",produto.getDescricao());
            body.put("preco",produto.getPreco());
            body.put("tipologia_id",produto.getTipologia_id());
            body.put("fornecedor_id",produto.getFornecedor_id());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        sharedPreferences=context.getSharedPreferences(MenuMainActivity.PREF_USER, Context.MODE_PRIVATE);
        final String token = sharedPreferences.getString(MenuMainActivity.TOKEN,null);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, urlMain + urlAddProduto+"/"+produto.getId()+"?auth_key="+token,body, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if(produtosListener!=null){
                 //z   produtosListener.onAddOrcamentoProduto();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(produtosListener!=null)
                {
                  //  produtosListener.onErroDetalhesprodutos(message);
                }
                System.out.println("-->addorcamentoproduto"+ error.getMessage()+error.toString()+error.getLocalizedMessage()+error.networkResponse);
                if (error == null || error.networkResponse == null) {
                    return;
                }
                String body;
                final String statusCode = String.valueOf(error.networkResponse.statusCode);
                try {
                    body = new String(error.networkResponse.data,"UTF-8");
                    System.out.println("-->addorcamentoproduto"+body);
                } catch (UnsupportedEncodingException e) {
                    System.out.println("-->addorcamentoproduto"+e.getMessage());
                }
            }
        });
        volleyQueue.add(request);
    }

    public static Cliente parserJsonCliente(JSONArray response){
        Cliente addCliente = null;
        try {
            for (int i = 0; i< response.length(); i++){
                JSONObject clientes = (JSONObject) response.get(i);
                int clienteId=clientes.getInt("id");
                String clienteNome=clientes.getString("nome");
                String clienteEmpresa=clientes.getString("empresa");
                String clienteMorada=clientes.getString("morada");
                int clienteNif=clientes.getInt("nif");
                int clienteTelefone=clientes.getInt("telefone");
                String clienteEmail=clientes.getString("email");
                int clienteUser_id=clientes.getInt("user_id");

                addCliente= new Cliente(clienteId,clienteNome,clienteEmpresa,clienteMorada,clienteNif,clienteTelefone,clienteEmail,clienteUser_id);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return addCliente;
    }
    public ArrayList<OrcamentoProduto>getMeusOrcamentoProdutosOrcamentoArray(int id_orcamento){
        System.out.println("-->pdf"+id_orcamento);
        orcamentoProdutosArray=getMeusOrcamentosProdutosDB();
        produtosArray=getProdutosDB();
        ArrayList<OrcamentoProduto> produtosOrcamento;
        produtosOrcamento = new ArrayList<>();
        System.out.println("-->onclick"+orcamentoProdutosArray.size()+"|"+produtosArray.size());

        for(OrcamentoProduto orcamentoProduto: orcamentoProdutosArray){
            for (Produto produto: produtosArray) {
                if(orcamentoProduto.getProduto_Id()==produto.getId()&&orcamentoProduto.getOrcamento_Id()==id_orcamento){
                     produtosOrcamento.add(new OrcamentoProduto(orcamentoProduto.getId(),orcamentoProduto.getOrcamento_Id(),orcamentoProduto.getProduto_Id(),orcamentoProduto.getQuantidade()));
                }
            }
        }
        return produtosOrcamento;
    }
    public ArrayList<Orcamento> getMeusOrcamentosClienteArray(int id_cliente){
        meusOrcamentosArray = getMeusOrcamentosDB();
        ArrayList<Orcamento> orcamentos;
        orcamentos = new ArrayList<>();
        int i=0;
        for(Orcamento orcamento: meusOrcamentosArray){
            if(orcamento.getCliente_id()==id_cliente){
                orcamentos.add(new Orcamento(orcamento.getId(),orcamento.getNome(),orcamento.getDescricao(),orcamento.getTotal(),orcamento.getData(),orcamento.getUid(),orcamento.getCliente_id(),orcamento.getUser_id()));
            }
        }
        return orcamentos;
    }

}