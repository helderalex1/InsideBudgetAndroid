package com.example.InsideBudget.Users.Instalador;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.InsideBudget.Listener.ClientesListener;
import com.example.InsideBudget.MenuMainActivity;
import com.example.InsideBudget.Modelo.Cliente;
import com.example.InsideBudget.Modelo.SingletonProjeto;
import com.example.InsideBudget.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class CriarAtualizarCliente extends AppCompatActivity implements ClientesListener {
    private EditText edtNome,edtEmpresa,edtMorada,edtNif,edtTelefone,edtEmail;
    private Button btnAddCliente;
    private SharedPreferences sharedPreferences;
    private String user_id;
    public static final String DETALHE_CLIENTE = "cliente";
    public static final int ADICIONAR = 1;
    public static final int EDITAR = 2;
    private Cliente cliente;
    private int cliente_id,opcao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_atualizar_cliente);
        edtNome = findViewById(R.id.edtAddClienteNome);
        edtEmpresa = findViewById(R.id.edtAddClienteEmpresa);
        edtMorada = findViewById(R.id.edtAddClienteMorada);
        edtNif = findViewById(R.id.edtAddClienteNif);
        edtTelefone =findViewById(R.id.edtAddClienteTelefone);
        edtEmail = findViewById(R.id.edtAddClienteEmail);
        btnAddCliente = findViewById(R.id.btnAddCliente);
        Bundle bundle = getIntent().getExtras();
        cliente_id = bundle.getInt("cliente_id");
        opcao = bundle.getInt("opcao");
        SingletonProjeto.getInstance(getApplicationContext()).getMeusClientesAPI(getApplicationContext());
        cliente = SingletonProjeto.getInstance(getApplicationContext()).getMeusClientesArray(0, cliente_id);
        sharedPreferences= getSharedPreferences(MenuMainActivity.PREF_USER, Context.MODE_PRIVATE);
        user_id = sharedPreferences.getString(MenuMainActivity.ID,null);
        SingletonProjeto.getInstance(getApplicationContext()).setClientesListener(this);
        System.out.println("-->detalhesclientea"+ cliente_id);
        if(cliente!=null){

            System.out.println("-->detalhesclientecriar/atualizar"+ cliente_id +"|"+cliente.getNome());
            setTitle("Editar cliente: " + cliente.getNome());
            edtNome.setText(cliente.getNome());
            edtEmpresa.setText(cliente.getEmpresa());
            edtMorada.setText(cliente.getMorada());
            edtNif.setText(Integer.toString(cliente.getNif()));
            edtTelefone.setText(Integer.toString(cliente.getTelefone()));
            edtEmail.setText(cliente.getEmail());
            btnAddCliente.setText("Atualizar cliente");
        } else {
            setTitle("Adicionar cliente");
            btnAddCliente.setText("Criar cliente");
        }

        btnAddCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtNome.getText().toString().equals("") ||edtEmpresa.getText().toString().equals("")|| edtMorada.getText().toString().equals("") || edtNif.getText().toString().equals("") || edtTelefone.getText().toString().equals("") || edtEmail.getText().toString().equals("")) {
                    Snackbar.make(view, "Obrigatório preencher todos os campos", Snackbar.LENGTH_SHORT).show();
                }else if(cliente != null && opcao == 2) {
                    cliente.setNome(edtNome.getText().toString());
                    cliente.setEmpresa(edtEmpresa.getText().toString());
                    cliente.setMorada(edtMorada.getText().toString());
                    cliente.setNif(Integer.parseInt(edtNif.getText().toString()));
                    cliente.setTelefone(Integer.parseInt(edtTelefone.getText().toString()));
                    cliente.setEmail(edtEmail.getText().toString());
                    SingletonProjeto.getInstance(getApplicationContext()).editCLienteAPI(cliente,getApplicationContext());


                }else{
                    if(opcao==1){
                        Cliente addCliente = new Cliente(0,edtNome.getText().toString(),edtEmpresa.getText().toString(),edtMorada.getText().toString(),Integer.parseInt(edtNif.getText().toString()),Integer.parseInt(edtTelefone.getText().toString()),edtEmail.getText().toString(),Integer.parseInt(user_id));
                        SingletonProjeto.getInstance(getApplicationContext()).addCLienteAPI(addCliente,getApplicationContext());
                    }
                }
            }
        });
    }

    @Override
    public void onRefreshListaMeusClientes(ArrayList<Cliente> listaClientes) {

    }

    @Override
    public void onAddCliente() {
        finish();
    }

    @Override
    public void onEditCliente() {


            SingletonProjeto.getInstance(getApplicationContext()).getMeusOrcamentosClienteArray(cliente_id);
        SingletonProjeto.getInstance(getApplicationContext()).getMeusClientesAPI(getApplicationContext());
        SingletonProjeto.getInstance(getApplicationContext()).getMeusClientesDB();
        System.out.println("-->detalhesclientecriar/atualizareditcl"+ cliente_id +"|"+cliente.getNome());
        setResult(RESULT_OK);
        finish();
/*
        Intent intent = new Intent(getApplicationContext(), DetalhesCliente.class);
        intent.putExtra(DetalhesCliente.DETALHES_Cliente,cliente.getId());
        startActivity(intent);*/
      //  finish();
    }

    @Override
    public void onBackPressed() {

            finish();

        super.onBackPressed();
    }

    @Override
    public void onErroListaMeusClientes(String message) {

    }
  /*  @Override
    public void onResume() {
        SingletonProjeto.getInstance(getApplicationContext()).getMeusClientesAPI(getApplicationContext());
        SingletonProjeto.getInstance(getApplicationContext()).getMeusOrcamentosClienteArray(cliente_id);
        System.out.println("-->detalhesclientecriar/atualizarresume"+ cliente_id +"|"+cliente.getNome());
        super.onResume();
    }

    @Override
    public void onPause() {
        SingletonProjeto.getInstance(getApplicationContext()).getMeusClientesAPI(getApplicationContext());
        SingletonProjeto.getInstance(getApplicationContext()).getMeusOrcamentosClienteArray(cliente_id);
        System.out.println("-->detalhesclientecriar/atualizarpause"+ cliente_id +"|"+cliente.getNome());
        super.onPause();
    }

    @Override
    public void onStop() {
        SingletonProjeto.getInstance(getApplicationContext()).getMeusClientesAPI(getApplicationContext());
        SingletonProjeto.getInstance(getApplicationContext()).getMeusOrcamentosClienteArray(cliente_id);

        System.out.println("-->detalhesclientecriar/atualizarstop"+ cliente_id +"|"+cliente.getNome());
        super.onStop();
    }*/
}