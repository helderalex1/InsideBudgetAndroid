package com.example.InsideBudget.Adapter.Users.Instalador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.InsideBudget.Modelo.Cliente;
import com.example.InsideBudget.Modelo.Orcamento;
import com.example.InsideBudget.Modelo.SingletonProjeto;
import com.example.InsideBudget.R;

import java.util.ArrayList;
import java.util.Locale;


public class ListaClienteOrcamentoAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<Orcamento> orcamentos;

    public ListaClienteOrcamentoAdapter(Context context, ArrayList<Orcamento> orcamentos){
        this.context = context;
        this.orcamentos = orcamentos;
    }
    @Override
    public int getCount() {
        return orcamentos.size();
    }

    @Override
    public Object getItem(int position) {
        return orcamentos.get(position);
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
            convertView = layoutInflater.inflate(R.layout.item_lista_meus_clientes_orcamentos, null);
        }
        ViewHolderLista viewHolderLista = (ViewHolderLista) convertView.getTag();
        if(viewHolderLista == null){
            viewHolderLista = new ViewHolderLista(convertView);
            convertView.setTag(viewHolderLista);
        }
        viewHolderLista.update(orcamentos.get(position));

        return convertView;
    }

    private class ViewHolderLista{
        private final TextView  nome,nomeCliente,data,total;

        public ViewHolderLista(View convertView){
            nome=convertView.findViewById(R.id.tVNomeOrcamentoMeusClientesOrcamentos);
            nomeCliente=convertView.findViewById(R.id.tVNomeClienteMeuClientesOrcamentos);
            data=convertView.findViewById(R.id.tVDataMeusClientesOrcamentos);
            total=convertView.findViewById(R.id.tvTotalMeusClientesOrcamentos);
        }
        public void update(Orcamento orcamento){
            System.out.println("-->aaa"+orcamento.getNome());
            Cliente cliente = SingletonProjeto.getInstance(context).getMeusClientesArray(0,orcamento.getCliente_id());
            String totalString = String.format(Locale.ENGLISH,"%.2f",orcamento.getTotal()) + "€";
            nome.setText(orcamento.getNome());
            if(cliente != null) {
               nomeCliente.setText(cliente.getNome());
            }
            data.setText(orcamento.getData());
            total.setText(totalString);
            }
    }
}
