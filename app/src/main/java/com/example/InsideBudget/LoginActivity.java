package com.example.InsideBudget;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import com.android.volley.RequestQueue;
import com.example.InsideBudget.Modelo.SingletonProjeto;
import com.example.InsideBudget.Listener.LoginListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;


import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements LoginListener {


    public RequestQueue volleyQueue;
    public static String urlSite= "http://192.168.68.135/projeto/frontend/web";
    private String username = null;
    private String password = null;
    private String funcao;
    private Button btnLogin, btnSite,btnRegister;
    EditText editUsername, editPassword;
    private TextInputLayout inputLayoutUsername,inputLayoutPassword;
    private TextInputEditText inputUsername, inputPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        /*Toolbar toolbar = findViewById(R.id.toolbar_menu);
        setSupportActionBar(toolbar);*/

        inputLayoutUsername =(TextInputLayout) findViewById(R.id.inputLayoutUsername);
        inputUsername =(TextInputEditText) findViewById(R.id.inputUsername);
        inputLayoutPassword =(TextInputLayout) findViewById(R.id.inputLayoutPassword);
        inputPassword =(TextInputEditText) findViewById(R.id.inputPassword);
        btnLogin = findViewById(R.id.btn_Login);
        btnSite = findViewById(R.id.btn_Site);
        btnRegister = findViewById(R.id.btn_Register);
        SingletonProjeto.getInstance(getApplicationContext()).setLoginListener(this);

        inputLayoutUsername.setHelperTextEnabled(false);
        inputLayoutPassword.setHelperTextEnabled(false);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(inputUsername.getText().toString().trim().isEmpty() || inputPassword.getText().toString().trim().isEmpty()) {
                    if (inputUsername.getText().toString().trim().isEmpty()) {
                        inputLayoutUsername.setHelperTextEnabled(true);
                        inputLayoutUsername.setHelperText("Required");
                    }else{
                        inputLayoutUsername.setHelperTextEnabled(false);
                    }
                    if (inputPassword.getText().toString().trim().isEmpty()) {
                        inputLayoutPassword.setHelperTextEnabled(true);
                        inputLayoutPassword.setHelperText("Required");
                    }else{
                        inputLayoutPassword.setHelperTextEnabled(false);
                    }
                    return;
                }
                username = inputUsername.getText().toString();
                password = inputPassword.getText().toString();
                SingletonProjeto.getInstance(getApplicationContext()).loginApi(username,password,getApplicationContext());

            }
        });
        btnSite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(LoginActivity.this)
                        .setTitle("")
                        .setPositiveButton("ok", new AlertDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                              /*  Uri uriUrl = Uri.parse(urlSite);
                                Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                                startActivity(Intent.createChooser(launchBrowser,"as"));*/
                                Uri uri = Uri.parse(urlSite);
                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("cancel",null)
                        .setMessage( "Deseja aceder ao website?" )
                        .show();

         /*   Uri uriUrl = Uri.parse(urlSite);
            Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
            startActivity(launchBrowser);*/
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
        SharedPreferences sharedPreferenceuser = getSharedPreferences(MenuMainActivity.PREF_USER, MODE_PRIVATE);
        username = sharedPreferenceuser.getString(MenuMainActivity.USERNAME, null);
        password = sharedPreferenceuser.getString(MenuMainActivity.PASSWORD, null);
        String funcao = sharedPreferenceuser.getString(MenuMainActivity.FUNCAO, null);
        try {
            if (funcao != null && username != null && password != null) {
                if(!SingletonProjeto.isInternetOn(getApplicationContext())) {
                    Toast.makeText(this, "Sem internet. Conseguirá visualizar os ultimos carregamentos salvos enquanto esteve com internet", Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(this, MenuMainActivity.class);
                intent.putExtra(MenuMainActivity.FUNCAO, funcao);
                startActivity(intent);
                finish();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Erro a fazer o login. Contacte a administração", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onValidateLogin(String token, String username) {
        if(token != null) {
            SharedPreferences sharedPreferenceslogin = getSharedPreferences(MenuMainActivity.PREF_USER, Context.MODE_PRIVATE);
            funcao = sharedPreferenceslogin.getString(MenuMainActivity.FUNCAO, null);
            SharedPreferences.Editor editor = sharedPreferenceslogin.edit();
            editor.putString(MenuMainActivity.USERNAME, username);
            editor.putString(MenuMainActivity.TOKEN, token);
            editor.apply();

            Intent intent = new Intent(this, MenuMainActivity.class);
            startActivity(intent);
            finish();
        }else{
            Toast.makeText(this, "Username ou Password Inválidos", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onErroLogin(String erro) {
        Toast.makeText(this, erro, Toast.LENGTH_SHORT).show();
    }

}