package com.example.InsideBudget.Users;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.example.InsideBudget.MenuMainActivity;
import com.example.InsideBudget.Modelo.DadosPessoais;
import com.example.InsideBudget.Modelo.Orcamento;
import com.example.InsideBudget.Modelo.OrcamentoProduto;
import com.example.InsideBudget.Modelo.Produto;
import com.example.InsideBudget.Modelo.SingletonProjeto;
import com.example.InsideBudget.Modelo.Tipologia;
import com.example.InsideBudget.R;
import com.example.InsideBudget.Users.Instalador.DetalhesOrcamento;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.sql.SQLOutput;
import java.text.Format;
import java.util.ArrayList;

public class DetalhesProduto extends AppCompatActivity {
    private TextInputLayout inputLayoutNome,inputLayoutTipologia, inputLayoutReferencia,inputLayoutPreco,inputLayoutDescricao,inputLayoutFornecedor;
    private TextInputEditText inputNome,inputReferencia,inputPreco,inputDescricao,inputFornecedor;
    private AutoCompleteTextView inputTipologia;
    private FloatingActionButton fab;
    private Produto produto;
    private Tipologia tipologia;
    private DadosPessoais dados;
    private Button btnCriarProduto;
    private ArrayList<Tipologia> tipologiaArray;
    private ArrayAdapter<Tipologia> tipologiaArrayAdapter;
    private static int textTipologia;
    private SharedPreferences sharedPreferences;
    private int user_id, produto_id;
    private String funcao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_produto);
        inputLayoutNome = findViewById(R.id.inputLayoutProdutoNome);
        inputNome = findViewById(R.id.inputProdutoNome);
        inputLayoutTipologia = findViewById(R.id.inputLayoutProdutoTipologia);
        inputTipologia = findViewById(R.id.inputProdutoTipologia);
        inputLayoutReferencia = findViewById(R.id.inputLayoutProdutoReferencia);
        inputReferencia = findViewById(R.id.inputProdutoRefencia);
        inputLayoutPreco = findViewById(R.id.inputLayoutProdutoPreco);
        inputPreco = findViewById(R.id.inputProdutoPreco);
        inputLayoutDescricao =findViewById(R.id.inputLayoutProdutoDescricao);
        inputDescricao = findViewById(R.id.inputProdutoDescricao);
        inputLayoutFornecedor = findViewById(R.id.inputLayoutProdutoFornecedor);
        inputFornecedor = findViewById(R.id.inputProdutoFornecedor);
        btnCriarProduto = findViewById(R.id.btn_criarProduto);
        fab = findViewById(R.id.fab_produtoAddProduto);

        sharedPreferences= getApplicationContext().getSharedPreferences(MenuMainActivity.PREF_USER, Context.MODE_PRIVATE);
        user_id = Integer.parseInt(sharedPreferences.getString(MenuMainActivity.ID,null));
        funcao = sharedPreferences.getString(MenuMainActivity.FUNCAO,null);
        Bundle bundle = getIntent().getExtras();
        produto_id = bundle.getInt("produto_id");
        String opcao = bundle.getString("opcao");
        SingletonProjeto.getInstance(getApplicationContext()).getAllProdutosAPI(getApplicationContext());
        SingletonProjeto.getInstance(getApplicationContext()).getAllDadosPessoaisAPI(getApplicationContext());
        SingletonProjeto.getInstance(getApplicationContext()).getAllTipologiaAPI(getApplicationContext());
        produto = SingletonProjeto.getInstance(getApplicationContext()).getProdutoArray(0,produto_id);
        if (produto != null) {
            tipologia = SingletonProjeto.getInstance(getApplicationContext()).getTipologiaArray(produto.getTipologia_id());
            dados = SingletonProjeto.getInstance(getApplicationContext()).getDadosPessoaisArray(produto.getFornecedor_id());
        }
        if (opcao.equals("AddProdutoOrcamento")) {
            setTitle("Adicionar produto");
            inputNome.setText(produto.getNome());
            inputTipologia.setText(tipologia.getNome());
            inputReferencia.setText(produto.getReferencia());
            inputPreco.setText(String.format("%.2f", produto.getPreco()));
            inputDescricao.setText(produto.getDescricao());
            inputFornecedor.setText(dados.getNomecompleto());
            //if(funcao.equals("instalador")) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ArrayList<OrcamentoProduto> orcamentoProdutos = SingletonProjeto.getInstance(getApplicationContext()).getMeusOrcamentoProdutosOrcamentoArray(DetalhesOrcamento.orcamento_id);
                    OrcamentoProduto orcamentoProduto = new OrcamentoProduto(0, DetalhesOrcamento.orcamento_id, produto.getId(), 1);
                    Orcamento orcamento = SingletonProjeto.getInstance(getApplicationContext()).getMeusOrcamentosArray(DetalhesOrcamento.orcamento_id, null, 0);
                    for (OrcamentoProduto orcamentoProdutoContem : orcamentoProdutos) {
                        if (orcamentoProdutoContem.getProduto_Id() == orcamentoProduto.getProduto_Id()) {
                            Toast.makeText(getApplicationContext(), "O produto " + produto.getNome() + " com a referência " + produto.getReferencia() + " já se encontra no orcamento " + orcamento.getNome(), Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    SingletonProjeto.getInstance(getApplicationContext()).addOrcamentoProdutoAPI(orcamentoProduto, getApplicationContext());
                }
            });
           /* }else if(funcao.equals("fornecedor") ){
                fab.setVisibility(View.INVISIBLE);
            }*/
        }else if(opcao.equals("VisualizarProduto")) {
            setTitle("Visualizar produto");
            inputNome.setText(produto.getNome());
            inputTipologia.setText(tipologia.getNome());
            inputReferencia.setText(produto.getReferencia());
            inputPreco.setText(String.format("%.2f", produto.getPreco()));
            inputDescricao.setText(produto.getDescricao());
            inputFornecedor.setText(dados.getNomecompleto());
            fab.setVisibility(View.INVISIBLE);
        }else if(opcao.equals("AddProdutoFornecedor") || opcao.equals("EditProdutoFornecedor")){

            inputLayoutNome.setEnabled(true);
            inputTipologia.setEnabled(true);
            inputLayoutTipologia.setEnabled(true);
            inputLayoutDescricao.setEnabled(true);
            inputLayoutFornecedor.setVisibility(View.INVISIBLE);
            inputLayoutReferencia.setEnabled(true);
            inputLayoutPreco.setEnabled(true);
            btnCriarProduto.setEnabled(true);
            btnCriarProduto.setVisibility(View.VISIBLE);
            fab.setVisibility(View.INVISIBLE);
            tipologiaArray = new ArrayList<Tipologia>();
            tipologiaArray = SingletonProjeto.getInstance(getApplicationContext()).getTipologiasDB();

            tipologiaArrayAdapter = new ArrayAdapter<Tipologia>(this,R.layout.support_simple_spinner_dropdown_item,tipologiaArray);
            // funcaoArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            if(opcao.equals("AddProdutoFornecedor")) {
                setTitle("Criar Produto");
            }else if(opcao.equals("EditProdutoFornecedor")){
                setTitle("Editar Produto");
                inputNome.setText(produto.getNome());

                inputTipologia.setText(SingletonProjeto.getInstance(getApplicationContext()).getTipologiaArray(produto.getTipologia_id()).getNome());
                inputPreco.setText(String.format("%.2f",produto.getPreco()));
                inputDescricao.setText(produto.getDescricao());
                inputReferencia.setText(produto.getReferencia());
                btnCriarProduto.setText("Editar Produto");
            }
            inputTipologia.setAdapter(tipologiaArrayAdapter);
            inputTipologia.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    textTipologia = (int) parent.getItemIdAtPosition(position+1);
                }
            });
            btnCriarProduto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(inputNome.getText().toString().trim().isEmpty() ||inputTipologia.getText().toString().trim().isEmpty()|| textTipologia <2 || inputDescricao.getText().toString().trim().isEmpty() || inputReferencia.getText().toString().trim().isEmpty()|| inputPreco.getText().toString().trim().isEmpty()) {
                        if (inputNome.getText().toString().trim().isEmpty()) {
                            inputLayoutNome.setHelperTextEnabled(true);
                            inputLayoutNome.setHelperText("Tipologia inválida");
                        }else{
                            inputLayoutNome.setHelperTextEnabled(false);
                        }
                        if (inputTipologia.getText().toString().trim().isEmpty() || textTipologia < 2) {
                            inputLayoutTipologia.setHelperTextEnabled(true);
                            inputLayoutTipologia.setHelperText("Tipologia inválida");
                        }else{
                            inputLayoutTipologia.setHelperTextEnabled(false);
                        }
                        if (inputReferencia.getText().toString().trim().isEmpty()) {
                            inputLayoutReferencia.setHelperTextEnabled(true);
                            inputLayoutReferencia.setHelperText("Obrigatório*");
                        }else{
                            inputLayoutReferencia.setHelperTextEnabled(false);
                        }
                        if (inputPreco.getText().toString().trim().isEmpty()) {
                            inputLayoutPreco.setHelperTextEnabled(true);
                            inputLayoutPreco.setHelperText("Obrigatório*");
                        }else{
                            inputLayoutPreco.setHelperTextEnabled(false);
                        }
                        if (inputDescricao.getText().toString().trim().isEmpty()) {
                            inputLayoutDescricao.setHelperTextEnabled(true);
                            inputLayoutDescricao.setHelperText("Obrigatório*");
                        }else{
                            inputLayoutDescricao.setHelperTextEnabled(false);
                        }
                        return;
                    }
                    System.out.println("-->criarproduto"+inputNome.getText().toString()+"|"+inputReferencia.getText().toString()+"|"+inputDescricao.getText().toString()+"|"+Float.valueOf(inputPreco.getText().toString())+"|"+user_id+"|"+textTipologia);
                    Produto produtoAdd = new Produto(produto_id,inputNome.getText().toString(),inputReferencia.getText().toString(),inputDescricao.getText().toString(),Float.valueOf(inputPreco.getText().toString()),user_id,textTipologia);
                    if( opcao.equals("EditProdutoFornecedor")){
                        SingletonProjeto.getInstance(getApplicationContext()).editProdutoAPI(produtoAdd, getApplicationContext());
                    }
                    if( opcao.equals("AddProdutoFornecedor")) {
                        SingletonProjeto.getInstance(getApplicationContext()).addProdutoAPI(produtoAdd, getApplicationContext());
                    }
                }
            });
/*            inputTipologia.setText(tipologia.getNome());
            inputReferencia.setText(produto.getReferencia());
            inputPreco.setText(String.format("%.2f",produto.getPreco()));
            inputDescricao.setText(produto.getDescricao());
            inputFornecedor.setText(dados.getNomecompleto());
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ArrayList<OrcamentoProduto> orcamentoProdutos = SingletonProjeto.getInstance(getApplicationContext()).getMeusOrcamentoProdutosOrcamentoArray(DetalhesOrcamento.orcamento_id);
                    OrcamentoProduto orcamentoProduto = new OrcamentoProduto(0, DetalhesOrcamento.orcamento_id, produto.getId(), 1);
                    Orcamento orcamento = SingletonProjeto.getInstance(getApplicationContext()).getMeusOrcamentosArray(DetalhesOrcamento.orcamento_id,null,0);
                    for (OrcamentoProduto orcamentoProdutoContem : orcamentoProdutos) {
                        if (orcamentoProdutoContem.getProduto_Id() == orcamentoProduto.getProduto_Id()) {
                            Toast.makeText(getApplicationContext(),"O produto " + produto.getNome() + " com a referência " + produto.getReferencia() + " já se encontra no orcamento " + orcamento.getNome(), Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    SingletonProjeto.getInstance(getApplicationContext()).addOrcamentoProdutoAPI(orcamentoProduto, getApplicationContext());
                }
        });}*/
    }
}}