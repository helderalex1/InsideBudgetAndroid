package com.example.InsideBudget;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PatternMatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.InsideBudget.Listener.RegistarListener;
import com.example.InsideBudget.Modelo.Funcao;
import com.example.InsideBudget.Modelo.SingletonProjeto;
import com.example.InsideBudget.Modelo.Tipologia;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RegisterActivity extends AppCompatActivity implements RegistarListener {

    private TextInputLayout inputLayoutUsername, inputLayoutNomeCompleto,inputLayoutEmail,inputLayoutEmpresa,inputLayoutMorada,inputLayoutPais,inputLayoutCidade,inputLayoutTelefone,inputLayoutFuncao,inputLayoutPassword,inputLayoutConfirmPassword;
    private TextInputEditText inputUsername,inputNomeCompleto,inputEmail,inputEmpresa,inputMorada,inputCidade,inputTelefone ,inputPassword,inputConfirmPassword;
    private AutoCompleteTextView inputPais,inputFuncao;
    private ArrayList<String> funcoesArrayList;
    private ArrayAdapter<String> paisesArrayAdapter;
    private ArrayAdapter<String> funcaoArrayAdapter;
    private ImageButton imgBtnActivityLogin;
    private Button btnRegistar;
    private String[] paisesList = {"Afeganistão","Estados Unidos da América","Albânia","Argélia","Andorra","Angola","Anguilla","Antígua", "Barbuda","Argentina","Arménia","Arménia","Austrália","Áustria","Azerbaijão","Bahamas","Bahrein",
            "Bangladesh","Barbados","Bielorrússia","Bélgica","Belize","Benim", "Bermudas","Bolívia","Bósnia e Herzegovina","Botswana","Brasil","Ilhas Virgens Britânicas","Brunei","Bulgária","Burkina Faso",
            "Burundi","Camboja","Camarões","Cabo Verde","Ilhas Caimão","Chade","Chile","China","Colômbia","Congo","Ilhas Cook","Costa Rica","Costa Do marfim","Croácia","Navio de Cruzeiro","Cuba","Chipre",
            "República Checa","Dinamarca","Djibuti","Dominica","República Dominicana","Equador","Egito","El Salvador","Guiné Equatorial", "Estónia","Etiópia","Ilhas Falkland","Ilhas Faroé","Fiji","Finlândia",
            "França", "Polinésia Francesa", "Índias Ocidentais Francesas", "Gabão","Gâmbia","Geórgia","Alemanha","Gana","Gibraltar","Grécia","Gronelândia","Granada","Guam","Guatemala","Guernsey","Guiné-Bissau",
            "Guiana","Haiti","Honduras","Hong Kong","Hungria","Islândia","Índia","Indonésia","Irão","Iraque","Irlanda", "Ilha de Man","Israel","Itália","Jamaica","Japão","Jersey","Jordan","Cazaquistão","Quénia",
            "Kuwait","República do Quirguistão","Laos","Letónia","Líbano","Lesoto","Libéria","Líbia","Liechtenstein","Lituânia","Luxemburgo","Macau","Macedónia","Madagáscar","Malawi","Malásia", "Maldivas", "Mali",
            "Malta","Mauritânia","Maurícia","México","Moldávia","Mónaco","Mongólia","Montenegro","Montserrat","Marrocos","Moçambique","Namíbia","Nepal","Holanda","Antilhas Holandesas","Nova Caledónia",
            "Nova Zelândia","Nicarágua","Níger","Nigéria","Noruega","Omã","Paquistão","Palestina","Panamá","Papua Nova Guiné","Paraguai","Peru","Filipinas","Polónia","Portugal","Porto Rico","Qatar","Reunião",
            "Roménia","Rússia","Ruanda","Saint Pierre", "Miquelon","Samoa","San Marino","Satélite","Arábia Saudita","Senegal","Sérvia","Seychelles","Serra Leoa","Singapura","Eslováquia","Eslovénia",
            "África do Sul","Coreia do Sul","Espanha","Sri Lanka","St Kitts", "Nevis","St Lucia","St Vincent","St. Lucia","Sudão","Suriname","Suazilândia","Suécia","Suíça","Síria","Taiwan","Tajiquistão",
            "Tanzânia","Tailândia","Timor L'Este","Togo","Tonga","Trinidad", "Tobago","Tunísia","Turquia","Turquemenistão","Turks", "Caicos","Uganda","Ucrânia","Emirados Árabes Unidos","Reino Unido",
            "Uruguai","Uzbequistão","Venezuela","Vietname","Ilhas Virgens (EUA)", "Iémen","Zâmbia","Zimbabué"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        inputLayoutUsername = findViewById(R.id.inputLayoutRegisterUsername);
        inputUsername = findViewById(R.id.inputRegisterUsername);
        inputLayoutNomeCompleto = findViewById(R.id.inputLayoutRegisterNomeCompleto);
        inputNomeCompleto = findViewById(R.id.inputRegisterNomeCompleto);
        inputLayoutEmail = findViewById(R.id.inputLayoutRegisterEmail);
        inputEmail = findViewById(R.id.inputRegisterEmail);
        inputLayoutEmpresa =findViewById(R.id.inputLayoutRegisterEmpresa);
        inputEmpresa = findViewById(R.id.inputRegisterEmpresa);
        inputLayoutMorada = findViewById(R.id.inputLayoutRegisterMorada);
        inputMorada = findViewById(R.id.inputRegisterMorada);
        inputLayoutPais = findViewById(R.id.inputLayoutRegisterPais);
        inputPais = findViewById(R.id.inputRegisterPais);
        inputLayoutCidade = findViewById(R.id.inputLayoutRegisterCidade);
        inputCidade = findViewById(R.id.inputRegisterCidade);
        inputLayoutTelefone = findViewById(R.id.inputLayoutRegisterTelefone);
        inputTelefone = findViewById(R.id.inputRegisterTelefone);
        inputLayoutFuncao = findViewById(R.id.inputLayoutRegisterFuncao);
        inputFuncao = findViewById(R.id.inputRegisterFuncao);
        inputLayoutPassword = findViewById(R.id.inputLayoutRegisterPassword);
        inputPassword = findViewById(R.id.inputRegisterPassword);
        inputLayoutConfirmPassword = findViewById(R.id.inputLayoutRegisterConfirmPassword);
        inputConfirmPassword = findViewById(R.id.inputRegisterConfirmPassword);
        imgBtnActivityLogin = findViewById(R.id.imgBtn);
        btnRegistar = findViewById(R.id.btn_Registar);
        SingletonProjeto.getInstance(getApplicationContext()).setRegistarListener(this);
        SingletonProjeto.getInstance(getApplicationContext()).getAllTipologiaAPI(getApplicationContext());
        /*funcoes= SingletonProjeto.getInstance(getApplicationContext()).getTipologiasDB();
        funcaoArrayAdapter = new ArrayAdapter<Tipologia>(this,R.layout.support_simple_spinner_dropdown_item,funcoes);
        funcaoArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        inputFuncao.setAdapter(funcaoArrayAdapter);*/
        List<String> listaOrdenados = Arrays.asList(paisesList);
        imgBtnActivityLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("-->aaaaa");
                finish();
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        paisesArrayAdapter = new ArrayAdapter<String>(this,R.layout.list_item,listaOrdenados);
        inputPais.setAdapter(paisesArrayAdapter);
        funcoesArrayList = new ArrayList<String>();
        funcoesArrayList.add("Instalador");
        funcoesArrayList.add("Fornecedor");

        funcaoArrayAdapter = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,funcoesArrayList);
       // funcaoArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        inputFuncao.setAdapter(funcaoArrayAdapter);
        btnRegistar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("-->registaractiviy::"+inputUsername.getText().toString()+"|"+inputNomeCompleto.getText().toString()+"|"+inputEmail.getText().toString()+"|"+
                        inputEmpresa.getText().toString()+"|"+inputMorada.getText().toString()+"|"+inputPais.getText().toString()+"|"+inputCidade.getText().toString()+"|"+inputTelefone.getText().toString()+
                        "|"+inputFuncao.getText().toString()+"|"+inputPassword.getText().toString());
                if(inputUsername.getText().toString().trim().isEmpty() ||inputNomeCompleto.getText().toString().trim().isEmpty() || inputEmail.getText().toString().trim().isEmpty() ||
                        !Patterns.EMAIL_ADDRESS.matcher(inputEmail.getText().toString()).matches() || inputEmpresa.getText().toString().trim().isEmpty()|| inputMorada.getText().toString().trim().isEmpty()||
                        inputPais.getText().toString().trim().isEmpty()|| inputCidade.getText().toString().trim().isEmpty()||inputTelefone.getText().toString().trim().isEmpty()||
                        inputFuncao.getText().toString().trim().isEmpty()||(inputFuncao.getText().toString().equals("Instalador") && inputFuncao.getText().toString().equals("Fornecedor"))||
                        inputPassword.getText().toString().trim().isEmpty() || inputPassword.getText().toString().trim().length() < 6 || inputPassword.getText().toString().trim().length() > 50 ||
                        inputConfirmPassword.getText().toString().trim().length() < 6 || inputConfirmPassword.getText().toString().trim().length() > 50) {
                    if (inputUsername.getText().toString().trim().isEmpty()) {
                        inputLayoutUsername.setHelperTextEnabled(true);
                        inputLayoutUsername.setHelperText("Obrigatório*");
                    }else{
                        inputLayoutUsername.setHelperTextEnabled(false);
                    }
                    if (inputNomeCompleto.getText().toString().trim().isEmpty() ) {
                        inputLayoutNomeCompleto.setHelperTextEnabled(true);
                        inputLayoutNomeCompleto.setHelperText("Obrigatório*");
                    }else{
                        inputLayoutNomeCompleto.setHelperTextEnabled(false);
                    }
                    if (inputEmail.getText().toString().trim().isEmpty()) {
                        inputLayoutEmail.setHelperTextEnabled(true);
                        inputLayoutEmail.setHelperText("Obrigatório*");
                    }else{
                        inputLayoutEmail.setHelperTextEnabled(false);
                    }
                    if (inputEmpresa.getText().toString().trim().isEmpty()) {
                        inputLayoutEmail.setHelperTextEnabled(true);
                        inputLayoutEmail.setHelperText("Obrigatório*");
                    }else{
                        inputLayoutEmail.setHelperTextEnabled(false);
                    }
                    if (!Patterns.EMAIL_ADDRESS.matcher(inputEmail.getText().toString()).matches()) {
                        inputLayoutEmail.setHelperTextEnabled(true);
                        inputLayoutEmail.setHelperText("Email inválido*");
                    }else{
                        inputLayoutEmail.setHelperTextEnabled(false);
                    }
                    if (inputMorada.getText().toString().trim().isEmpty()) {
                        inputLayoutMorada.setHelperTextEnabled(true);
                        inputLayoutMorada.setHelperText("Obrigatório*");
                    }else{
                        inputLayoutMorada.setHelperTextEnabled(false);
                    }
                    if (inputPais.getText().toString().trim().isEmpty()) {
                        inputLayoutPais.setHelperTextEnabled(true);
                        inputLayoutPais.setHelperText("Obrigatório*");
                    }else{
                        inputLayoutPais.setHelperTextEnabled(false);
                    }
                    if (inputCidade.getText().toString().trim().isEmpty()) {
                        inputLayoutCidade.setHelperTextEnabled(true);
                        inputLayoutCidade.setHelperText("Obrigatório*");
                    }else{
                        inputLayoutCidade.setHelperTextEnabled(false);
                    }
                    if (inputTelefone.getText().toString().trim().isEmpty()) {
                        inputLayoutTelefone.setHelperTextEnabled(true);
                        inputLayoutTelefone.setHelperText("Obrigatório*");
                    }else{
                        inputLayoutTelefone.setHelperTextEnabled(false);
                    }
                    if (inputFuncao.getText().toString().trim().isEmpty()) {
                        inputLayoutFuncao.setHelperTextEnabled(true);
                        inputLayoutFuncao.setHelperText("Obrigatório*");
                    }else{
                        inputLayoutFuncao.setHelperTextEnabled(false);
                    }
                    if (!inputFuncao.getText().toString().equals("Instalador") &&!inputFuncao.getText().toString().equals("Fornecedor")) {
                        inputLayoutFuncao.setHelperTextEnabled(true);
                        inputLayoutFuncao.setHelperText("Função inválida*");
                    }else{
                        inputLayoutFuncao.setHelperTextEnabled(false);
                    }
                    if (inputPassword.getText().toString().trim().isEmpty()) {
                        inputLayoutPassword.setHelperTextEnabled(true);
                        inputLayoutPassword.setHelperText("Obrigatório*");
                    }else if (inputPassword.getText().toString().trim().length() < 6) {
                        inputLayoutPassword.setHelperTextEnabled(true);
                        inputLayoutPassword.setHelperText("O campo password tem que conter no mínimo 6 caracteres*");
                    }else if (inputPassword.getText().toString().trim().length() > 50) {
                        inputLayoutPassword.setHelperTextEnabled(true);
                        inputLayoutPassword.setHelperText("O campo password tem que conter no máximo 50 caracteres*");
                    }else{
                        inputLayoutPassword.setHelperTextEnabled(false);
                    }
                    if (inputConfirmPassword.getText().toString().trim().isEmpty() ||!inputPassword.getText().toString().equals(inputConfirmPassword.getText().toString())) {
                        inputLayoutConfirmPassword.setHelperTextEnabled(true);
                        inputLayoutConfirmPassword.setHelperText("O campo Confirmar Password têm que ser igual ao campo Password*");
                    }else if (inputConfirmPassword.getText().toString().trim().length() < 6) {
                        inputLayoutConfirmPassword.setHelperTextEnabled(true);
                        inputLayoutConfirmPassword.setHelperText("O campo Confirmar password tem que conter no mínimo 6 caracteres*");
                    }else if (inputConfirmPassword.getText().toString().trim().length() > 50) {
                        inputLayoutConfirmPassword.setHelperTextEnabled(true);
                        inputLayoutConfirmPassword.setHelperText("O campo Confirmar password tem que conter no máximo 50 caracteres*");
                    }else{
                        inputLayoutConfirmPassword.setHelperTextEnabled(false);
                    }
                    return;
                }
                inputLayoutUsername.setHelperTextEnabled(false);
                inputLayoutNomeCompleto.setHelperTextEnabled(false);
                inputLayoutEmail.setHelperTextEnabled(false);
                inputLayoutMorada.setHelperTextEnabled(false);
                inputLayoutPais.setHelperTextEnabled(false);
                inputLayoutCidade.setHelperTextEnabled(false);
                inputLayoutTelefone.setHelperTextEnabled(false);
                inputLayoutFuncao.setHelperTextEnabled(false);
                inputLayoutPassword.setHelperTextEnabled(false);
                inputLayoutConfirmPassword.setHelperTextEnabled(false);
                System.out.println("-->registaractiviy:::::"+inputUsername.getText().toString()+"|"+inputNomeCompleto.getText().toString()+"|"+inputEmail.getText().toString()+"|"+
                        inputEmpresa.getText().toString()+"|"+inputMorada.getText().toString()+"|"+inputPais.getText().toString()+"|"+inputCidade.getText().toString()+"|"+inputTelefone.getText().toString()+
                        "|"+inputFuncao.getText().toString()+"|"+inputPassword.getText().toString()+getApplicationContext());
               SingletonProjeto.getInstance(getApplicationContext()).registarApi(inputUsername.getText().toString(),inputNomeCompleto.getText().toString(),inputEmail.getText().toString(),
                       inputEmpresa.getText().toString(),inputMorada.getText().toString(),inputPais.getText().toString(),inputCidade.getText().toString(),inputTelefone.getText().toString(),
                       inputFuncao.getText().toString(),inputPassword.getText().toString(),getApplicationContext());
            }
        });

    }

    @Override
    public void onRegistarTrue() {
        System.out.println("-->registarstring::"+ "listener");
        finish();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}