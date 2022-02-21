package com.example.InsideBudget.Adapter.Users.Instalador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.InsideBudget.Modelo.Orcamento;
import com.example.InsideBudget.R;

import java.util.ArrayList;
import java.util.Locale;

public class ListaMeusOrcamentosAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<Orcamento> meusOrcamentos;

    public ListaMeusOrcamentosAdapter(Context context, ArrayList<Orcamento> meusOrcamentos){
        this.context = context;
        this.meusOrcamentos = meusOrcamentos;
    }

    @Override
    public int getCount() {
        return meusOrcamentos.size();
    }

    @Override
    public Object getItem(int position) {
        return meusOrcamentos.get(position);
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
            convertView = layoutInflater.inflate(R.layout.item_lista_meus_orcamentos, null);
        }
        ListaMeusOrcamentosAdapter.ViewHolderLista viewHolderLista = (ListaMeusOrcamentosAdapter.ViewHolderLista) convertView.getTag();
        if(viewHolderLista == null){
            viewHolderLista = new ListaMeusOrcamentosAdapter.ViewHolderLista(convertView);
            convertView.setTag(viewHolderLista);
        }

        viewHolderLista.update(meusOrcamentos.get(position));

        return convertView;
    }



    private class ViewHolderLista{
        private final TextView  nomeOrcamento,nomeCliente,data, total;

        public ViewHolderLista(View convertView){
            nomeOrcamento = convertView.findViewById(R.id.tVNomeOrcamentoMeusOrcamentos);
            nomeCliente = convertView.findViewById(R.id.tVNomeClienteMeusOrcamentos);
            data = convertView.findViewById(R.id.tVDataMeusOrcamentos);
            total = convertView.findViewById(R.id.tvTotalMeusOrcamentos);
        }
        public void update(Orcamento orcamento){
            String totalString = String.format(Locale.US,"%.2f",orcamento.getTotal()) + "€";
            System.out.println("-->float"+orcamento.getTotal()+"|"+totalString);
            nomeOrcamento.setText(orcamento.getNome());
            nomeCliente.setText(orcamento.getNome());
            data.setText(orcamento.getData());
            total.setText(totalString);
        }
    }
}
