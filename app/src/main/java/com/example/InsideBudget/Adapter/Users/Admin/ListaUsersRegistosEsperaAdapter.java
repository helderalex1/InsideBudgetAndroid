package com.example.InsideBudget.Adapter.Users.Admin;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.example.InsideBudget.Modelo.DadosPessoais;
import com.example.InsideBudget.Modelo.Funcao;
import com.example.InsideBudget.Modelo.SingletonProjeto;
import com.example.InsideBudget.Modelo.User;
import com.example.InsideBudget.R;

import java.util.ArrayList;

public class ListaUsersRegistosEsperaAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<User> usersRegistosEspera;

    public ListaUsersRegistosEsperaAdapter(Context context, ArrayList<User> usersRegistosEspera){
        this.context = context;
        this.usersRegistosEspera = usersRegistosEspera;
    }

    @Override
    public int getCount() {
        return usersRegistosEspera.size();
    }

    @Override
    public Object getItem(int position) {
        return usersRegistosEspera.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (layoutInflater == null) {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_lista_users_registos_espera, null);
        }
        ListaUsersRegistosEsperaAdapter.ViewHolderLista viewHolderLista = (ListaUsersRegistosEsperaAdapter.ViewHolderLista) convertView.getTag();
        if(viewHolderLista == null){
            viewHolderLista = new ListaUsersRegistosEsperaAdapter.ViewHolderLista(convertView);
            convertView.setTag(viewHolderLista);
        }
        viewHolderLista.update(usersRegistosEspera.get(position));
        return convertView;
    }



   private class ViewHolderLista{
        private final TextView funcao, username, nome, email;
        private Button btnPermitir, btnBanirRecusar;

        public ViewHolderLista(View convertView){
            funcao = convertView.findViewById(R.id.tVFuncaoUsersRegistosEspera);
            username = convertView.findViewById(R.id.tVUsernameUsersRegistosEspera);
            nome = convertView.findViewById(R.id.tVNomeUsersRegistosEspera);
            email = convertView.findViewById(R.id.tVEmailRegistosEspera);
            btnPermitir = convertView.findViewById(R.id.btnRegistosEsperaPermitirAceitar);
            btnBanirRecusar = convertView.findViewById(R.id.btnUsersRegistosEsperaBanirRecusar);
        }
        public void update(User user){
            Funcao valorFuncao = SingletonProjeto.getInstance(context).getFuncaoArray(user.getId());
            DadosPessoais valorDados = SingletonProjeto.getInstance(context).getDadosPessoaisArray(user.getId());
//            System.out.println("-->lala0" + valorFuncao.getItem_Name());
            if(valorFuncao != null) {
                funcao.setText(valorFuncao.getItem_Name());
            }
            username.setText(user.getUsername());
            if(valorDados!= null) {
                nome.setText(valorDados.getNomecompleto());
            }
            email.setText(user.getEmail());
            btnPermitir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    if(SingletonProjeto.isInternetOn(context)) {
                                        SingletonProjeto.getInstance(context).UserBanirPermitirAPI(context, user.getId(), false);
                                        //SingletonProjeto.getInstance(context).getAllUsersAPI(context);

                                        Toast.makeText(context, "Utilizador " + user.getUsername() + " aceite com sucesso", Toast.LENGTH_SHORT).show();
                                    }
                                    break;
                                case DialogInterface.BUTTON_NEGATIVE:
                                    break;
                            }
                        }
                    };
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Deseja aceitar, dar permissão ao utilizado r" + user.getUsername() + "aceder?").setPositiveButton("Sim", dialogClickListener)
                            .setNegativeButton("Não", dialogClickListener).show();
                }
            });
            btnBanirRecusar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    if(SingletonProjeto.isInternetOn(context)) {
                                        SingletonProjeto.getInstance(context).UserBanirPermitirAPI(context, user.getId(), true);
                                        Toast.makeText(context, "Utilizador " + user.getUsername() + " bloqueado com sucesso", Toast.LENGTH_SHORT).show();
                                    }
                                    break;
                                case DialogInterface.BUTTON_NEGATIVE:
                                    break;
                            }
                        }
                    };
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Deseja desbloquear, recusar o registo do utilizado r" + user.getUsername() + "?").setPositiveButton("Sim", dialogClickListener)
                            .setNegativeButton("Não", dialogClickListener).show();
                }
            });
        }}
}
