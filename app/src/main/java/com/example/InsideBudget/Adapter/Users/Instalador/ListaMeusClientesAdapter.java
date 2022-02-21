package com.example.InsideBudget.Adapter.Users.Instalador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.InsideBudget.Modelo.Cliente;
import com.example.InsideBudget.R;

import java.util.ArrayList;

public class ListaMeusClientesAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<Cliente> meusClientes;

    public ListaMeusClientesAdapter(Context context, ArrayList<Cliente> meusClientes){
        this.context = context;
        this.meusClientes = meusClientes;
    }

    @Override
    public int getCount() {
        return meusClientes.size();
    }

    @Override
    public Object getItem(int position) {
        return meusClientes.get(position);
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
            convertView = layoutInflater.inflate(R.layout.item_lista_meus_clientes, null);
        }
        ListaMeusClientesAdapter.ViewHolderLista viewHolderLista = (ListaMeusClientesAdapter.ViewHolderLista) convertView.getTag();
        if(viewHolderLista == null){
            viewHolderLista = new ListaMeusClientesAdapter.ViewHolderLista(convertView);
            convertView.setTag(viewHolderLista);
        }
        viewHolderLista.update(meusClientes.get(position));
        return convertView;
    }



    private class ViewHolderLista{
        private final TextView  nomeCliente,empresa, nif, telefone,email;

        public ViewHolderLista(View convertView){
            nomeCliente = convertView.findViewById(R.id.tVNomeClienteMeusClientes);
            empresa = convertView.findViewById(R.id.tVEmpresaMeusClientes);
            nif = convertView.findViewById(R.id.tVNifMeusClientes);
            telefone = convertView.findViewById(R.id.tVTelefoneMeusClientes);
            email = convertView.findViewById(R.id.tVEmailMeusClientes);
        }
        public void update(Cliente cliente){
            String nifString = Integer.toString(cliente.getNif());
            String telefoneString = Integer.toString(cliente.getTelefone());
            nomeCliente.setText(cliente.getNome());
            empresa.setText(cliente.getEmpresa());
            nif.setText(nifString);
            telefone.setText(telefoneString);
            email.setText(cliente.getEmail());
        }
    }
}
