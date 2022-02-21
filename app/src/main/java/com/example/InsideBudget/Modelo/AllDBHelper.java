package com.example.InsideBudget.Modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class AllDBHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION=495;
    //nome da base de dados
    private static final String DB_NAME = "projetodb";
    //nomes das tabelas
    private static final String TABLE_NAMEUser = "user";
    private static final String TABLE_NAMEDadospessoais = "dadospessoais";
    private static final String TABLE_NAMEFuncao = "Funcao";
    private static final String TABLE_NAMECliente = "cliente";
    private static final String TABLE_NAMEOrcamento = "orcamento";
    private static final String TABLE_NAMETipologia = "tipologia";
    private static final String TABLE_NAMEProduto = "produto";
    private static final String TABLE_NAMEOrcamentoProduto = "orcamentoproduto";

    //------------------------------Inicio Criação de tabelas-------------------------------
    //-------------------------------------User---------------------------------------------
    private static final String User_Id = "id";
    private static final String User_Username = "username";
    private static final String User_Email = "email";
    private static final String User_Status= "status";

    private static final String createUserTable=
            "CREATE TABLE " + TABLE_NAMEUser+ " ( " +
                    User_Id + " INTEGER PRIMARY KEY, " +
                    User_Username + " TEXT NOT NULL, " +
                    User_Email + " TEXT NOT NULL, " +
                    User_Status + " INTEGER NOT NULL" +
                    " );";

    //-------------------------------------DadosPessoais---------------------------------------------
    private static final String Dadospessoais_Id = "id";
    private static final String Dadospessoais_Nomecompleto = "nomecompleto";
    private static final String Dadospessoais_Empresa = "empresa";
    private static final String Dadospessoais_Pais = "pais";
    private static final String Dadospessoais_Cidade = "cidade";
    private static final String Dadospessoais_Morada = "morada";
    private static final String Dadospessoais_Telefone = "telefone";
    private static final String Dadospessoais_User_Id = "user_id";

    private static final String createDadospessoaisTable=
            "CREATE TABLE " + TABLE_NAMEDadospessoais+ " ( " +
            Dadospessoais_Id + " INTEGER PRIMARY KEY, " +
            Dadospessoais_Nomecompleto + " TEXT NOT NULL, " +
            Dadospessoais_Empresa + " TEXT NOT NULL, " +
            Dadospessoais_Pais + " TEXT NOT NULL, " +
            Dadospessoais_Cidade + " TEXT NOT NULL, " +
            Dadospessoais_Morada + " TEXT NOT NULL, " +
            Dadospessoais_Telefone + " INTEGER NOT NULL, " +
            Dadospessoais_User_Id + " INTEGER NOT NULL" +
            " );";


    //-------------------------------------Função---------------------------------------------
    private static final String Funcao_Item_Name = "item_name";
    private static final String Funcao_User_Id = "user_id";

    private static final String createFuncaoTable=
            "CREATE TABLE " + TABLE_NAMEFuncao + " ( " +
            Funcao_Item_Name + " TEXT NOT NULL, " +
            Funcao_User_Id + " INTEGER NOT NULL " +
            " );";

    //-------------------------------------Cliente---------------------------------------------
    private static final String Cliente_Id = "id";
    private static final String Cliente_Nome = "nome";
    private static final String Cliente_Empresa = "empresa";
    private static final String Cliente_Morada = "morada";
    private static final String Cliente_Nif = "nif";
    private static final String Cliente_Telefone = "telefone";
    private static final String Cliente_Email = "email";
    private static final String Cliente_User_Id = "user_id";

    private static final  String createClienteTable =
            "CREATE TABLE " + TABLE_NAMECliente + " ( " +
             Cliente_Id + " INTEGER PRIMARY KEY, " +
             Cliente_Nome + " TEXT NOT NULL, " +
             Cliente_Empresa + " TEXT NOT NULL, " +
             Cliente_Morada + " TEXT NOT NULL, " +
             Cliente_Nif + " INTEGER NOT NULL, " +
             Cliente_Telefone + " INTEGER NOT NULL, " +
             Cliente_Email + " TEXT NOT NULL, " +
             Cliente_User_Id + " INTEGER NOT NULL" +
             " );";

    //-------------------------------------Orçamento---------------------------------------------
    private static final String Orcamento_Id = "id";
    private static final String Orcamento_Nome = "nome";
    private static final String Orcamento_Descricao = "descricao";
    private static final String Orcamento_Total = "total";
    private static final String Orcamento_Data = "data";
    private static final String Orcamento_Uid = "uid";
    private static final String Orcamento_Cliente_Id = "cliente_id";
    private static final String Orcamento_User_Id= "user_id";

    private static final String createOrcamentoTable=
            "CREATE TABLE " + TABLE_NAMEOrcamento + " ( " +
            Orcamento_Id + " INTEGER PRIMARY KEY, " +
            Orcamento_Nome + " TEXT NOT NULL, " +
            Orcamento_Descricao + " TEXT NOT NULL, " +
            Orcamento_Total + " FLOAT NOT NULL, " +
            Orcamento_Data + " TEXT NOT NULL, " +
            Orcamento_Uid + " TEXT UNIQUE NOT NULL, " +
            Orcamento_Cliente_Id + " INTEGER NOT NULL, " +
            Orcamento_User_Id + " INTEGER NOT NULL " +
            " );";

    //-------------------------------------Tipologia---------------------------------------------
    private static final String Tipologia_Id = "id";
    private static final String Tipologia_Nome = "nome";

    private static final String createTipologiaTable=
            "CREATE TABLE " + TABLE_NAMETipologia + " ( " +
            Tipologia_Id + " INTEGER PRIMARY KEY, " +
            Tipologia_Nome + " TEXT NOT NULL " +
            " );";

    //-------------------------------------Produto---------------------------------------------
    private static final String Produto_Id = "id";
    private static final String Produto_Nome = "nome";
    private static final String Produto_Referenia = "referencia";
    private static final String Produto_Descricao = "descricao";
    private static final String Produto_Preco = "preco";
    private static final String Produto_Fornecedor_Id = "fornecedor_id";
    private static final String Produto_Tipologia_Id = "tipologia_id";

    private static final String createProdutoTable=
            "CREATE TABLE " + TABLE_NAMEProduto + " ( " +
            Produto_Id + " INTEGER PRIMARY KEY, " +
            Produto_Nome + " TEXT NOT NULL, " +
            Produto_Referenia + " TEXT NOT NULL, " +
            Produto_Descricao + " TEXT NOT NULL, " +
            Produto_Preco + " FLOAT NOT NULL, " +
            Produto_Fornecedor_Id + " INTEGER NOT NULL, " +
            Produto_Tipologia_Id + " INTEGER NOT NULL " +
            " );";

    //-------------------------------------Orcamento Produto---------------------------------------------
    private static final String OrcamentoProduto_Id= "id";
    private static final String OrcamentoProduto_Orcamento_Id = "orcamento_id";
    private static final String OrcamentoProduto_Produto_Id = "produto_id";
    private static final String OrcamentoProduto_Quantidade = "quantidade";

    private static final String createProdutoOrcamentoTable=
            "CREATE TABLE " + TABLE_NAMEOrcamentoProduto + " ( " +
                    OrcamentoProduto_Id + " INTEGER PRIMARY KEY, " +
                    OrcamentoProduto_Orcamento_Id + " INTEGER NOT NULL, " +
                    OrcamentoProduto_Produto_Id + " INTEGER NOT NULL, " +
                    OrcamentoProduto_Quantidade + " INTEGER NOT NULL " +
            " );";

    //----------------------------------Fim Criação de tabelas---------------------------------

    private final SQLiteDatabase sqLiteDatabase;


    public AllDBHelper(Context context) {
        super(context,DB_NAME,null, DB_VERSION);
        this.sqLiteDatabase = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(createUserTable);
        sqLiteDatabase.execSQL(createDadospessoaisTable);
        sqLiteDatabase.execSQL(createFuncaoTable);
        sqLiteDatabase.execSQL(createClienteTable);
        sqLiteDatabase.execSQL(createOrcamentoTable);
        sqLiteDatabase.execSQL(createTipologiaTable);
        sqLiteDatabase.execSQL(createProdutoTable);
        sqLiteDatabase.execSQL(createProdutoOrcamentoTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAMEUser);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAMEDadospessoais);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAMEFuncao);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAMECliente);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAMEOrcamento);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAMETipologia);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAMEProduto);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAMEOrcamentoProduto);
        this.onCreate(sqLiteDatabase);
    }


//-------------------------------------------Inicio ADD DB--------------------------------------
    public User addUserDB (User user){
        ContentValues values = new ContentValues();
        values.put(User_Id, user.getId());
        values.put(User_Username, user.getUsername());
        values.put(User_Email, user.getEmail());
        values.put(User_Status, user.getStatus());

        this.sqLiteDatabase.insert(TABLE_NAMEUser, null, values);
        return null;
    }
    public DadosPessoais addDadosPessoaisDB (DadosPessoais dadosPessoais){
        ContentValues values = new ContentValues();
        values.put(Dadospessoais_Id, dadosPessoais.getId());
        values.put(Dadospessoais_Nomecompleto, dadosPessoais.getNomecompleto());
        values.put(Dadospessoais_Empresa, dadosPessoais.getEmpresa());
        values.put(Dadospessoais_Pais, dadosPessoais.getPais());
        values.put(Dadospessoais_Cidade, dadosPessoais.getCidade());
        values.put(Dadospessoais_Morada, dadosPessoais.getMorada());
        values.put(Dadospessoais_Telefone, dadosPessoais.getTelefone());
        values.put(Dadospessoais_User_Id, dadosPessoais.getUser_id());

        this.sqLiteDatabase.insert(TABLE_NAMEDadospessoais, null, values);
        return null;
    }

    public Funcao addFuncaoDB (Funcao funcao){
        ContentValues values = new ContentValues();
        values.put(Funcao_Item_Name, funcao.getItem_Name());
        values.put(Funcao_User_Id, funcao.getUser_id());

        this.sqLiteDatabase.insert(TABLE_NAMEFuncao, null, values);
        return null;
    }

    public Produto addProdutoDB (Produto produto){
        ContentValues values = new ContentValues();
        values.put(Produto_Id, produto.getId());
        values.put(Produto_Nome, produto.getNome());
        values.put(Produto_Referenia, produto.getReferencia());
        values.put(Produto_Descricao, produto.getDescricao());
        values.put(Produto_Preco, produto.getPreco());
        values.put(Produto_Fornecedor_Id, produto.getFornecedor_id());
        values.put(Produto_Tipologia_Id, produto.getTipologia_id());

        this.sqLiteDatabase.insert(TABLE_NAMEProduto, null, values);
        return null;
    }

    public Produto addClienteDB (Cliente cliente){
        ContentValues values = new ContentValues();
        values.put(Cliente_Id, cliente.getId());
        values.put(Cliente_Nome, cliente.getNome());
        values.put(Cliente_Empresa, cliente.getEmpresa());
        values.put(Cliente_Morada, cliente.getMorada());
        values.put(Cliente_Nif, cliente.getNif());
        values.put(Cliente_Telefone, cliente.getTelefone());
        values.put(Cliente_Email, cliente.getEmail());
        values.put(Cliente_User_Id, cliente.getUser_id());

        this.sqLiteDatabase.insert(TABLE_NAMECliente, null, values);
        return null;
    }

    public Produto addOrcamentoDB (Orcamento orcamento){
        ContentValues values = new ContentValues();
        values.put(Orcamento_Id, orcamento.getId());
        values.put(Orcamento_Nome, orcamento.getNome());
        values.put(Orcamento_Descricao, orcamento.getDescricao());
        values.put(Orcamento_Total, orcamento.getTotal());
        values.put(Orcamento_Data, orcamento.getData());
        values.put(Orcamento_Uid, orcamento.getUid());
        values.put(Orcamento_Cliente_Id, orcamento.getCliente_id());
        values.put(Orcamento_User_Id, orcamento.getUser_id());

        this.sqLiteDatabase.insert(TABLE_NAMEOrcamento, null, values);
        return null;
    }

    public Produto addProdutoOrcamentoDB (OrcamentoProduto orcamentoProduto){
        ContentValues values = new ContentValues();
        values.put(OrcamentoProduto_Id, orcamentoProduto.getId());
        values.put(OrcamentoProduto_Orcamento_Id, orcamentoProduto.getOrcamento_Id());
        values.put(OrcamentoProduto_Produto_Id, orcamentoProduto.getProduto_Id());
        values.put(OrcamentoProduto_Quantidade, orcamentoProduto.getQuantidade());

        this.sqLiteDatabase.insert(TABLE_NAMEOrcamentoProduto, null, values);
        return null;
    }

    public Produto addTipologiasDB (Tipologia tipologia){
        ContentValues values = new ContentValues();
        values.put(Tipologia_Id, tipologia.getId());
        values.put(Tipologia_Nome, tipologia.getNome());

        this.sqLiteDatabase.insert(TABLE_NAMETipologia, null, values);
        return null;
    }

//-------------------------------------------Inicio Get DB--------------------------------------
    public ArrayList<User> getAllUsersDB(){
        ArrayList<User> users = new ArrayList<>();
        Cursor cursor = this.sqLiteDatabase.query(TABLE_NAMEUser,new String[]{User_Id,User_Username,User_Email,User_Status},
                null,null,null,null,null,null);

        if(cursor.moveToFirst()){
            do {
                User user= new User(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getInt(3));
                users.add(user);
            }while (cursor.moveToNext());
        }
        return users;
    }

    public ArrayList<DadosPessoais> getAllDadosPessoaisDB(){
        ArrayList<DadosPessoais> dadospessoaiss = new ArrayList<>();
        Cursor cursor = this.sqLiteDatabase.query(TABLE_NAMEDadospessoais,new String[]{Dadospessoais_Id,Dadospessoais_Nomecompleto,Dadospessoais_Empresa,Dadospessoais_Pais,
                        Dadospessoais_Cidade,Dadospessoais_Morada,Dadospessoais_Telefone,Dadospessoais_User_Id},
                null,null,null,null,null,null);

        if(cursor.moveToFirst()){
            do {
                DadosPessoais dadospessoais= new DadosPessoais(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),
                        cursor.getString(5),cursor.getInt(6),cursor.getInt(6));
                dadospessoaiss.add(dadospessoais);
            }while (cursor.moveToNext());
        }
        return dadospessoaiss;
    }

    public ArrayList<Funcao> getAllFuncoesDB(){
        ArrayList<Funcao> funcoes = new ArrayList<>();
        Cursor cursor = this.sqLiteDatabase.query(TABLE_NAMEFuncao,new String[]{Funcao_Item_Name,Funcao_User_Id},
                null,null,null,null,null,null);

        if(cursor.moveToFirst()){
            do {
                Funcao funcao= new Funcao(cursor.getString(0),cursor.getInt(1));
                funcoes.add(funcao);
            }while (cursor.moveToNext());
        }
        return funcoes;
    }

    public ArrayList<Produto> getAllProdutosDB(){
        ArrayList<Produto> produtos = new ArrayList<>();
        Cursor cursor = this.sqLiteDatabase.query(TABLE_NAMEProduto,new String[]{Produto_Id,Produto_Nome,Produto_Referenia,Produto_Descricao,Produto_Preco,Produto_Fornecedor_Id,Produto_Tipologia_Id},
                null,null,null,null,null,null);

        if(cursor.moveToFirst()){
            do {
                Produto produto= new Produto(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getFloat(4),cursor.getInt(5),cursor.getInt(6));
                produtos.add(produto);
            }while (cursor.moveToNext());
        }
        return produtos;
    }

    public ArrayList<Cliente> getAllMeusClientesDB(){
        ArrayList<Cliente> clientes = new ArrayList<>();
        Cursor cursor = this.sqLiteDatabase.query(TABLE_NAMECliente,new String[]{Cliente_Id,Cliente_Nome,Cliente_Empresa,Cliente_Morada,Cliente_Nif,Cliente_Telefone,Cliente_Email,Cliente_User_Id},
                null,null,null,null,null,null);

        if(cursor.moveToFirst()){
            do {
                Cliente cliente= new Cliente(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getInt(4),cursor.getInt(5),cursor.getString(6),cursor.getInt(7));
                clientes.add(cliente);
            }while (cursor.moveToNext());
        }
        return clientes;
    }

    public ArrayList<Orcamento> getAllMeusOrcamentosDB(){
        ArrayList<Orcamento> orcamentos = new ArrayList<>();
        Cursor cursor = this.sqLiteDatabase.query(TABLE_NAMEOrcamento,new String[]{Orcamento_Id,Orcamento_Nome,Orcamento_Descricao,Orcamento_Total,Orcamento_Data,Orcamento_Uid,Orcamento_Cliente_Id,Orcamento_User_Id},
                null,null,null,null,null,null);

        if(cursor.moveToFirst()){
            do {
                Orcamento orcamento= new Orcamento(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getFloat(3),cursor.getString(4), cursor.getString(5),cursor.getInt(6),cursor.getInt(7));
                orcamentos.add(orcamento);
            }while (cursor.moveToNext());
        }
        return orcamentos;
    }

    public ArrayList<OrcamentoProduto> getAllMeusOrcamentoProdutosDB(){
        ArrayList<OrcamentoProduto> orcamentoProdutos = new ArrayList<>();
        Cursor cursor = this.sqLiteDatabase.query(TABLE_NAMEOrcamentoProduto,new String[]{OrcamentoProduto_Id,OrcamentoProduto_Orcamento_Id,OrcamentoProduto_Produto_Id,OrcamentoProduto_Quantidade},
                null,null,null,null,null,null);

        if(cursor.moveToFirst()){
            do {
                OrcamentoProduto orcamentoProduto= new OrcamentoProduto(cursor.getInt(0),cursor.getInt(1),cursor.getInt(2),cursor.getInt(3));
                orcamentoProdutos.add(orcamentoProduto);
            }while (cursor.moveToNext());
        }
        return orcamentoProdutos;
    }

    public ArrayList<Tipologia> getAllTipologiasDB(){
        ArrayList<Tipologia> tipologias = new ArrayList<>();
        Cursor cursor = this.sqLiteDatabase.query(TABLE_NAMETipologia,new String[]{Tipologia_Id,Tipologia_Nome},
                null,null,null,null,null,null);

        if(cursor.moveToFirst()){
            do {
                Tipologia tipologia= new Tipologia(cursor.getInt(0),cursor.getString(1));
                tipologias.add(tipologia);
            }while (cursor.moveToNext());
        }
        return tipologias;
    }

//-------------------------------------------Inicio Editar DB--------------------------------------

    public boolean editarProdutoDB(Produto produto){
        ContentValues values = new ContentValues();
        values.put(Produto_Nome, produto.getNome());
        values.put(Produto_Referenia, produto.getReferencia());
        values.put(Produto_Descricao, produto.getDescricao());
        values.put(Produto_Preco, produto.getPreco());

        return this.sqLiteDatabase.update(TABLE_NAMEProduto,values,"id = ?", new String[]{String.valueOf(produto.getId())}) == 1 ;
    }
    public boolean editarClienteDB(Cliente cliente){
        ContentValues values = new ContentValues();
        values.put(Cliente_Email, cliente.getEmail());
        values.put(Cliente_Empresa, cliente.getEmpresa());
        values.put(Cliente_Morada, cliente.getMorada());
        values.put(Cliente_Nif, cliente.getNif());
        values.put(Cliente_Nome, cliente.getNome());
        values.put(Cliente_Telefone, cliente.getTelefone());

        return this.sqLiteDatabase.update(TABLE_NAMEProduto,values,"id = ?", new String[]{String.valueOf(cliente.getId())}) == 1 ;
    }

//-------------------------------------------Inicio Delete DB--------------------------------------
    public void delAllUsersDB(){
        this.sqLiteDatabase.delete(TABLE_NAMEUser,null,null);
    }

    public void delAllDadosPessoaisDB(){this.sqLiteDatabase.delete(TABLE_NAMEDadospessoais,null,null);}

    public void dellAllFuncoesDB(){
        this.sqLiteDatabase.delete(TABLE_NAMEFuncao,null,null);
    }

    public void dellAllProdutosDB(){this.sqLiteDatabase.delete(TABLE_NAMEProduto,null,null);}

    public void dellAllMeusClientesDB(){this.sqLiteDatabase.delete(TABLE_NAMECliente,null,null);}

    public void dellAllMeusOrcamentosDB(){this.sqLiteDatabase.delete(TABLE_NAMEOrcamento,null,null);}

    public void dellAllMeusOrcamentoProdutosDB(){this.sqLiteDatabase.delete(TABLE_NAMEOrcamentoProduto,null,null);}

    public void dellAllTipologiasDB(){this.sqLiteDatabase.delete(TABLE_NAMETipologia,null,null);}
}
