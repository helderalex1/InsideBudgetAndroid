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

public class ListaUsersBloqueadosAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<User> usersBanidos;
    public ListaUsersBloqueadosAdapter(Context context, ArrayList<User> usersBanidos){
        this.context = context;
        this.usersBanidos = usersBanidos;
    }
    @Override
    public int getCount() {
        return usersBanidos.size();
    }

    @Override
    public Object getItem(int position) {
        return usersBanidos.get(position);
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
            convertView = layoutInflater.inflate(R.layout.item_lista_users_banidos, null);
        }
        ListaUsersBloqueadosAdapter.ViewHolderLista viewHolderLista = (ListaUsersBloqueadosAdapter.ViewHolderLista) convertView.getTag();
        if(viewHolderLista == null){
            viewHolderLista = new ListaUsersBloqueadosAdapter.ViewHolderLista(convertView);
            convertView.setTag(viewHolderLista);
        }

        viewHolderLista.update(usersBanidos.get(position));
        return convertView;
    }

    private class ViewHolderLista{
        private final TextView funcao, username, nome, email;
        private Button btnBanir;

        public ViewHolderLista(View convertView){
            funcao = convertView.findViewById(R.id.tVFuncaoUsersBanidosDesbanir);
            username = convertView.findViewById(R.id.tVUsernameUsersBanidos);
            nome = convertView.findViewById(R.id.tVNomeUsersBanidos);
            email = convertView.findViewById(R.id.tVEmailUsersBanidos);
            btnBanir = convertView.findViewById(R.id.btnUsersBanidosDesbanir);
        }
        public void update(User user){
            Funcao valorFuncao = SingletonProjeto.getInstance(context).getFuncaoArray(user.getId());
            DadosPessoais valorDados = SingletonProjeto.getInstance(context).getDadosPessoaisArray(user.getId());
            if(valorFuncao != null) {
                funcao.setText(valorFuncao.getItem_Name());

                System.out.println("--->funcaonotnull");
            }else{
                System.out.println("--->funcaonull");
            }
            username.setText(user.getUsername());
            //funcao.setText(getfuncao.getFuncao());
            if(valorDados!= null) {
                System.out.println("-->dadospessoaisnotnull");
                nome.setText(valorDados.getNomecompleto());
            }else{
                System.out.println("-->dadospessoais");
            }
            email.setText(user.getEmail());
            btnBanir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    if(SingletonProjeto.isInternetOn(context)){
                                        SingletonProjeto.getInstance(context).UserBanirPermitirAPI(context,user.getId(), false);
                                        Toast.makeText(context,"Utilizador " + user.getUsername() + " bloqueado com sucesso",Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(context,"Sem internet para puder bloquear." , Toast.LENGTH_SHORT).show();
                                    }
                                    break;
                                case DialogInterface.BUTTON_NEGATIVE:
                                    break;
                            }
                        }
                    };
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Deseja bloquear o utilizador " + user.getUsername()).setPositiveButton("Sim", dialogClickListener)
                            .setNegativeButton("Não", dialogClickListener).show();

                }
            });
        }
    }
}

