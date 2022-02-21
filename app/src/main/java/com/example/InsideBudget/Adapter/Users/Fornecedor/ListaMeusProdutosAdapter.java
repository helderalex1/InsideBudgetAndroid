package com.example.InsideBudget.Adapter.Users.Fornecedor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.InsideBudget.Modelo.Produto;
import com.example.InsideBudget.Modelo.SingletonProjeto;
import com.example.InsideBudget.Modelo.Tipologia;
import com.example.InsideBudget.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ListaMeusProdutosAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<Produto> meusProdutos;

    public ListaMeusProdutosAdapter(Context context, ArrayList<Produto> todosProdutos){
        this.context = context;
        this.meusProdutos = todosProdutos;
    }

    @Override
    public int getCount() {
        return meusProdutos.size();
    }

    @Override
    public Object getItem(int position) {
        return meusProdutos.get(position);
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
            convertView = layoutInflater.inflate(R.layout.item_lista_meus_produtos, null);
        }
        ListaMeusProdutosAdapter.ViewHolderLista viewHolderLista = (ListaMeusProdutosAdapter.ViewHolderLista) convertView.getTag();
        if(viewHolderLista == null){
            viewHolderLista = new ListaMeusProdutosAdapter.ViewHolderLista(convertView);
            convertView.setTag(viewHolderLista);
        }
        viewHolderLista.update(meusProdutos.get(position));
        return convertView;
    }



    private class ViewHolderLista{
        private final TextView  nomeProduto, referencia, tipologia,preco;

        public ViewHolderLista(View convertView){
            nomeProduto = convertView.findViewById(R.id.tVNomeProdutoMeusProdutos);
            referencia = convertView.findViewById(R.id.tVReferenciaMeusProdutos);
            tipologia = convertView.findViewById(R.id.tVTipologiaMeusProdutos);
            preco = convertView.findViewById(R.id.tVPrecoMeusProdutos);
        }
        public void update(Produto produto){
            Tipologia valorTipologia = SingletonProjeto.getInstance(context).getTipologiaArray(produto.getTipologia_id());
            String precoString = String.format("%.2f",produto.getPreco()) + "€";
            System.out.println("-->precos"+precoString);
            nomeProduto.setText(produto.getNome());
            referencia.setText(produto.getReferencia());
            if(valorTipologia != null) {
                tipologia.setText(valorTipologia.getNome());
            }
            preco.setText(precoString);
        }
    }
}
