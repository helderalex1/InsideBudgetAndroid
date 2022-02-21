package com.example.InsideBudget.Users.Instalador;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.InsideBudget.Listener.OrcamentosListener;
import com.example.InsideBudget.MenuMainActivity;
import com.example.InsideBudget.Modelo.Cliente;
import com.example.InsideBudget.Modelo.Orcamento;
import com.example.InsideBudget.Modelo.SingletonProjeto;
import com.example.InsideBudget.R;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class CriarAtualizarOrcamento extends AppCompatActivity implements OrcamentosListener {
    private EditText edtNome,edtDescricao;
    private String nome,descricao;
    private Button btnAdd;
    private Orcamento orcamento;
    private FragmentManager fragmentManager;
    private SharedPreferences sharedPreferences;
    private String user_id;
    private int cliente_id;
    public static int opcao;
    public static final int ADICIONAR = 1;
    public static final int EDITAR = 2;
    public static final String CLIENTE_ID = "";
    private SimpleDateFormat sdf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_atualizar_orcamento);
        //  setHasOptionsMenu(true);
        edtNome = findViewById(R.id.edtAddOrcamentoNome);
        edtDescricao = findViewById(R.id.edtAddOrcamentoDescricao);
        btnAdd = findViewById(R.id.btnAddOrcamento);
        sharedPreferences = getSharedPreferences(MenuMainActivity.PREF_USER, Context.MODE_PRIVATE);
        user_id = sharedPreferences.getString(MenuMainActivity.ID, null);
        //opcao = getIntent().getIntExtra(ADICIONAREDITARREMOVER, -1);
        //cliente_id = getIntent().getIntExtra(CLIENTE_ID, 0);
        Bundle bundle = getIntent().getExtras();
        cliente_id = bundle.getInt("cliente_id");
        opcao = bundle.getInt("opcao");
        Orcamento orcamento = SingletonProjeto.getInstance(getApplicationContext()).getMeusOrcamentosArray(0, null, cliente_id);
        sdf = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());
        SingletonProjeto.getInstance(getApplicationContext()).setOrcamentosListener(this);
        System.out.println("-->addd"+ ADICIONAR +"|"+EDITAR+"|"+opcao+"|"+cliente_id+ "|"+currentDateandTime);
        if(orcamento != null && opcao == 2 ) {
            setTitle("Editar orcamento: " + orcamento.getNome());
            btnAdd.setText("Editar orcamento");
            edtNome.setText(orcamento.getNome());
            edtDescricao.setText(orcamento.getDescricao());
        }
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtNome.getText().toString().equals("") ||edtDescricao.getText().toString().equals("")){
                    Snackbar.make(view, "Obrigatório preencher todos os campos", Snackbar.LENGTH_SHORT).show();
                    return;
                }else if (orcamento != null && opcao== 2){

                    System.out.println("-->detalhesorcamentoedit");
                    setTitle("Editar orcamento: " + orcamento.getNome());
                    orcamento.setNome(edtNome.getText().toString());
                    orcamento.setDescricao(edtDescricao.getText().toString());
                    orcamento.setCliente_id(cliente_id);
                    SingletonProjeto.getInstance(getApplicationContext()).editOrcamentoAPI(orcamento, getApplicationContext());
                } else if(opcao== 1){

                    System.out.println("-->detalhesorcamentoadd");
                    String uid = edtNome.getText().toString() + currentDateandTime;
                    Orcamento addOrcamento = new Orcamento(0, edtNome.getText().toString(), edtDescricao.getText().toString(), 0, currentDateandTime, uid, cliente_id, Integer.parseInt(user_id));
                    SingletonProjeto.getInstance(getApplicationContext()).addOrcamentoAPI(addOrcamento, getApplicationContext());
                }
            }
        });
    }

    @Override
    public void onRefreshListaMeusOrcamentos(ArrayList<Orcamento> listaOrcamentos) {

    }

    @Override
    public void onAddOrcamento() {
        SingletonProjeto.getInstance(getApplicationContext()).getMeusOrcamentosAPI(getApplicationContext());
        finish();
        Intent intent = new Intent(getApplicationContext(),DetalhesCliente.class);
        intent.putExtra(DetalhesCliente.DETALHES_Cliente,cliente_id);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {

        SingletonProjeto.getInstance(getApplicationContext()).getMeusOrcamentosAPI(getApplicationContext());
        finish();
        if(opcao ==2){
            Intent intent = new Intent(getApplicationContext(),DetalhesOrcamento.class);
            //intent.putExtra(DetalhesOrcamento.DETALHES_Cliente,cliente_id);
            startActivity(intent);
        }
        super.onBackPressed();
    }

    @Override
    public void onEditOrcamento() {
        System.out.println("-->detalhesorcamento");
        setResult(RESULT_OK);
        finish();

    }

    @Override
    public void onErroListaMeusOrcamentos(String message) {

    }
}