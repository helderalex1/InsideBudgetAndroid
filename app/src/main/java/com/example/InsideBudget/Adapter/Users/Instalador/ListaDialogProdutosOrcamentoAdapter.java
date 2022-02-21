
package com.example.InsideBudget.Adapter.Users.Instalador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.InsideBudget.Adapter.Users.ListaTodosProdutosAdapter;
import com.example.InsideBudget.Modelo.DadosPessoais;
import com.example.InsideBudget.Modelo.OrcamentoProduto;
import com.example.InsideBudget.Modelo.Produto;
import com.example.InsideBudget.Modelo.SingletonProjeto;
import com.example.InsideBudget.Modelo.Tipologia;
import com.example.InsideBudget.R;

import java.util.ArrayList;


public class ListaDialogProdutosOrcamentoAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<Produto> todosProdutos;

    public ListaDialogProdutosOrcamentoAdapter(Context context, ArrayList<Produto> todosProdutos){
        this.context = context;
        this.todosProdutos = todosProdutos;
    }

    @Override
    public int getCount() {
        return todosProdutos.size();
    }

    @Override
    public Object getItem(int position) {
        return todosProdutos.get(position);
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
            convertView = layoutInflater.inflate(R.layout.item_lista_todos_produtos, null);
        }
        ListaDialogProdutosOrcamentoAdapter.ViewHolderLista viewHolderLista = (ListaDialogProdutosOrcamentoAdapter.ViewHolderLista) convertView.getTag();
        if(viewHolderLista == null){
            viewHolderLista = new ListaDialogProdutosOrcamentoAdapter.ViewHolderLista(convertView);
            convertView.setTag(viewHolderLista);
        }
        viewHolderLista.update(todosProdutos.get(position));
        return convertView;
    }



    private class ViewHolderLista{
        private final TextView  nomeProduto,nomeFornecedor, referencia, tipologia,preco;

        public ViewHolderLista(View convertView){
            nomeProduto = convertView.findViewById(R.id.tVNomeProdutoTodosProdutos);
            nomeFornecedor = convertView.findViewById(R.id.tVNomeFornecedorTodosProdutos);
            referencia = convertView.findViewById(R.id.tVReferenciaTodosProdutos);
            tipologia = convertView.findViewById(R.id.tVTipologiaTodosProdutos);
            preco = convertView.findViewById(R.id.tVPrecoTodosProdutos);
        }
        public void update(Produto produto){
            Tipologia valorTipologia = SingletonProjeto.getInstance(context).getTipologiaArray(produto.getTipologia_id());
            DadosPessoais valorDados = SingletonProjeto.getInstance(context).getDadosPessoaisArray(produto.getFornecedor_id());
            String precoString = String.format("%.2f",produto.getPreco()) + "€";
            if(valorTipologia != null) {
                tipologia.setText(valorTipologia.getNome());
            }
            if(valorDados != null){
                nomeFornecedor.setText(valorDados.getNomecompleto());
            }
            nomeProduto.setText(produto.getNome());
            referencia.setText(produto.getReferencia());
            preco.setText(precoString);
        }
    }
}
