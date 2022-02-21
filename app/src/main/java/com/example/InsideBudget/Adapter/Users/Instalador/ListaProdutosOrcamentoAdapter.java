package com.example.InsideBudget.Adapter.Users.Instalador;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.core.app.ComponentActivity;

import com.example.InsideBudget.LoginActivity;
import com.example.InsideBudget.Modelo.DadosPessoais;
import com.example.InsideBudget.Modelo.Orcamento;
import com.example.InsideBudget.Modelo.OrcamentoProduto;
import com.example.InsideBudget.Modelo.Produto;
import com.example.InsideBudget.Modelo.SingletonProjeto;
import com.example.InsideBudget.Modelo.Tipologia;
import com.example.InsideBudget.R;
import com.example.InsideBudget.Users.Instalador.DetalhesOrcamento;

import java.util.ArrayList;


public class ListaProdutosOrcamentoAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<OrcamentoProduto> orcamentoProdutos;
    private float valorAdd;

    public ListaProdutosOrcamentoAdapter(Context context, ArrayList<OrcamentoProduto> orcamentoProdutos){
        this.context = context;
        this.orcamentoProdutos = orcamentoProdutos;
    }
    @Override
    public int getCount() {
        return orcamentoProdutos.size();
    }

    @Override
    public Object getItem(int position) {
        return orcamentoProdutos.get(position);
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
            convertView = layoutInflater.inflate(R.layout.item_lista_todos_produtos_orcamento_produtos, null);
        }
        ViewHolderLista viewHolderLista = (ViewHolderLista) convertView.getTag();
        if(viewHolderLista == null){
            viewHolderLista = new ViewHolderLista(convertView);
            convertView.setTag(viewHolderLista);
        }
        viewHolderLista.update(orcamentoProdutos.get(position));

        return convertView;
    }

    private class ViewHolderLista{
        private final TextView  nomeProduto,nomeFornecedor, referencia, tipologia,preco;
        private final EditText quantidade;
        private final ImageView imgVRemoverProduto,imgVAddProduto;

        public ViewHolderLista(View convertView){
            nomeProduto = convertView.findViewById(R.id.tVNomeProdutoOrcamento);
            nomeFornecedor = convertView.findViewById(R.id.tVNomeFornecedorProdutoOrcamento);
            referencia = convertView.findViewById(R.id.tVReferenciaProdutoOrcamento);
            tipologia = convertView.findViewById(R.id.tVTipologiaProdutoOrcamento);
            preco = convertView.findViewById(R.id.tVPrecoProdutoOrcamento);
            quantidade = convertView.findViewById(R.id.edtQuantidadeProdutoOrcamento);
            imgVRemoverProduto = convertView.findViewById(R.id.imgVRemoveProduto);
            imgVAddProduto = convertView.findViewById(R.id.imgVAddProduto);
        }
        public void update(OrcamentoProduto orcamentoProduto){

            Produto produto = SingletonProjeto.getInstance(context).getProdutoArray(0,orcamentoProduto.getProduto_Id());
                    DadosPessoais fornecedorNome = SingletonProjeto.getInstance(context).getDadosPessoaisArray(produto.getFornecedor_id());
                    Tipologia tipologia_String = SingletonProjeto.getInstance(context).getTipologiaArray(produto.getTipologia_id());
                    String precoString = String.format("%.2f", produto.getPreco()) + "€";
                    String quantidadeString = Integer.toString(orcamentoProduto.getQuantidade());
                    float valor = orcamentoProduto.getQuantidade() * produto.getPreco();
                    System.out.println("-->op " + produto.getNome() + orcamentoProduto.getProduto_Id() +"|" + produto.getId()+"|"+orcamentoProduto.getQuantidade());
                    nomeProduto.setText(produto.getNome());
                    if(fornecedorNome !=null) {
                        nomeFornecedor.setText(fornecedorNome.getNomecompleto());
                    }
                    referencia.setText(produto.getReferencia());
                    if (tipologia_String !=null) {
                        tipologia.setText(tipologia_String.getNome());
                    }
                    if(valor >= 1) {
                        preco.setText(precoString + "x" + quantidadeString + "=" + String.format("%.2f",valor)+"€");
                    }
                    quantidade.setText(quantidadeString);

            quantidade.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                  //  System.out.println("-->qual:s"+s.toString()+"|"+Integer.toString(start)+"|"+ Integer.toString(after)+"|"+Integer.toString(count));
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String a = quantidade.getText().toString();
                    System.out.println("-->qual:s"+s.toString()+"|"+Integer.toString(start)+"|"+ Integer.toString(before)+"|"+Integer.toString(count));
                    quantidade.setOnKeyListener(new View.OnKeyListener() {
                        @Override
                        public boolean onKey(View v, int keyCode, KeyEvent event) {
                            System.out.println("-->qial"+a+"||"+s.toString());
                            if(keyCode == 66 && !(s.toString()).equals("0")){
                            System.out.println("-->quantidadeadd" + orcamentoProduto.getQuantidade());
                            String addQuantidade = Integer.toString( Integer.parseInt(s.toString()));
                            System.out.println("-->quantidadeadd2" + addQuantidade);

                                quantidade.setText(addQuantidade);


                            Orcamento orcamento = SingletonProjeto.getInstance(context).getMeusOrcamentosArray(DetalhesOrcamento.orcamento_id, null, 0);
                            System.out.println("-->valor"+orcamentoProduto.getQuantidade()  +"|"+ a+"|"+addQuantidade+"|"+orcamento.getTotal());
                            if(orcamentoProduto.getQuantidade() < Integer.parseInt(a)) {

                                System.out.println("-->valormaior: "+orcamentoProduto.getQuantidade()  +"|"+ a+"|"+addQuantidade+"|"+orcamento.getTotal());
                                orcamento.setTotal(orcamento.getTotal() +(produto.getPreco() * (Integer.parseInt(a)-orcamentoProduto.getQuantidade())));
                            }else if (orcamentoProduto.getQuantidade()  > Integer.parseInt(a)){

                                System.out.println("-->valormenor"+orcamentoProduto.getQuantidade()  +"|"+ a+"|"+addQuantidade+"|"+orcamento.getTotal());
                                orcamento.setTotal(orcamento.getTotal() - (produto.getPreco() * (orcamentoProduto.getQuantidade()- Integer.parseInt(a))));
                            }

                                orcamentoProduto.setQuantidade(Integer.parseInt(addQuantidade));
                            DetalhesOrcamento.total.setText(String.format("%.2f", orcamento.getTotal()) + "€");
                            SingletonProjeto.getInstance(context).editOrcamentoProdutoAPI(orcamentoProduto, context);
                            SingletonProjeto.getInstance(context).editOrcamentoAPI(orcamento, context);
                                System.out.println("-->float"+DetalhesOrcamento.total + "|"+String.format("%.2f", orcamento.getTotal()) + "€");

                            return false;
                        }else if((s.toString()).equals("0")){
                                quantidade.setText(Integer.toString(orcamentoProduto.getQuantidade()));
                                Toast.makeText(context,"A quantidade não pode ser inferior a 1",Toast.LENGTH_SHORT).show();
                            }
                        return false;
                        }
                    });
                }

                @Override
                public void afterTextChanged(Editable s) {
                    System.out.println("-->chang3");

                }
            });
                    imgVRemoverProduto.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(Integer.parseInt(quantidade.getText().toString())>1) {
                                System.out.println("-->quantidaderemove" + orcamentoProduto.getQuantidade());
                                String removeQuantidade = Integer.toString(orcamentoProduto.getQuantidade() - 1);
                                orcamentoProduto.setQuantidade(Integer.parseInt(removeQuantidade));
                                System.out.println("-->quantidaderemove" + removeQuantidade);
                                quantidade.setText(removeQuantidade);

                                SingletonProjeto.getInstance(context).editOrcamentoProdutoAPI(orcamentoProduto, context);
                                if (valor >= 1) {
                                    float valorRemove = orcamentoProduto.getQuantidade() * produto.getPreco();
                                    preco.setText(precoString + "x" + removeQuantidade + "=" + String.format("%.2f", valorRemove) + "€");
                                }
                                Orcamento orcamento = SingletonProjeto.getInstance(context).getMeusOrcamentosArray(DetalhesOrcamento.orcamento_id, null, 0);
                                orcamento.setTotal(orcamento.getTotal() - produto.getPreco());
                                DetalhesOrcamento.total.setText(String.format("%.2f", orcamento.getTotal()) + "€");
                                SingletonProjeto.getInstance(context).editOrcamentoProdutoAPI(orcamentoProduto, context);
                                SingletonProjeto.getInstance(context).editOrcamentoAPI(orcamento, context);
                            }else{
                                Toast.makeText(context,"A quantidade não pode ser inferior a 1",Toast.LENGTH_SHORT).show();
                        }
                        }
                    });
                    imgVAddProduto.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                  //  SingletonProjeto.getInstance(context).getMeusOrcamentosAPI(context);

                                    // SingletonProjeto.getInstance(context).getMeusOrcamentoProdutosAPI(context);
                                    System.out.println("-->quantidadeadd" + orcamentoProduto.getQuantidade());
                                    String addQuantidade = Integer.toString(orcamentoProduto.getQuantidade() + 1);
                                    orcamentoProduto.setQuantidade(Integer.parseInt(addQuantidade));
                                    System.out.println("-->quantidadeadd" + addQuantidade);


                                    float valorAdd = orcamentoProduto.getQuantidade() * produto.getPreco();
                                    preco.setText(precoString + "x" + addQuantidade + "=" + String.format("%.2f",valorAdd)+"€");

                                    quantidade.setText(addQuantidade);

                                    Orcamento orcamento = SingletonProjeto.getInstance(context).getMeusOrcamentosArray(DetalhesOrcamento.orcamento_id,null,0);
                           System.out.println("-->quantidadeaddva:"+ orcamento.getTotal() +"|" +produto.getPreco()+"|"+DetalhesOrcamento.total.getText());
                                    orcamento.setTotal(orcamento.getTotal()+produto.getPreco());

                                    DetalhesOrcamento.total.setText(String.format("%.2f",orcamento.getTotal())+"€");
                                    //             System.out.println("-->quantidadeaddva:"+ orcamento.getTotal() +"|" +produto.getPreco()+"|"+DetalhesOrcamento.total.getText());
                                    SingletonProjeto.getInstance(context).editOrcamentoProdutoAPI(orcamentoProduto,context);
                                    SingletonProjeto.getInstance(context).editOrcamentoAPI(orcamento,context);
                                }},500);
                        }
                    });
            }
          /*  OrcamentoProduto valorOrcamentoProduto = SingletonProjeto.getInstance(context).getMeusOrcamentoProdutosArray(orcamentoProduto.getId());
            String quantidadeString = valorOrcamentoProduto.toString();
            nomeProduto.setText(orcamentoProduto.getNome());
            referencia.setText(orcamentoProduto.getReferencia());
            preco.setText(String.valueOf(orcamentoProduto.getPreco()));
            quantidade.setText(quantidadeString);*/

         /*   String a = Integer.toString(valorOrcamentoProduto.getQuantidade());
            nomeProduto.setText(produto.getNome());
            nomeFornecedor.setText(fornecedorNome);
            referencia.setText(produto.getReferencia());
            tipologia.setText(tipologia_IdString);
            preco.setText(precoString);
            System.out.println("-->82" + a.toString());
            if(a != null && valorOrcamentoProduto != null) {
                quantidade.setText(a.toString());
            }*/

    }
}
