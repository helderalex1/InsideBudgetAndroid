package com.example.InsideBudget.Users.Instalador;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.InsideBudget.Adapter.Users.Instalador.ListaProdutosOrcamentoAdapter;
import com.example.InsideBudget.BuildConfig;
import com.example.InsideBudget.Listener.OrcamentoProdutosListener;
import com.example.InsideBudget.Listener.ProdutosListener;
import com.example.InsideBudget.MenuMainActivity;
import com.example.InsideBudget.Modelo.Cliente;
import com.example.InsideBudget.Modelo.Orcamento;
import com.example.InsideBudget.Modelo.OrcamentoProduto;
import com.example.InsideBudget.Modelo.Produto;
import com.example.InsideBudget.Modelo.SingletonProjeto;
import com.example.InsideBudget.R;
import com.example.InsideBudget.Users.DetalhesProduto;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.itextpdf.barcodes.BarcodeQRCode;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.color.DeviceCmyk;
import com.itextpdf.kernel.color.DeviceRgb;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Tab;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Locale;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


//classe de detalhes do orcamento
public class  DetalhesOrcamento extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, OrcamentoProdutosListener, ProdutosListener {

    private TextView data,descricao;
    public static TextView total;
    private ListView listView;
    private SwipeRefreshLayout swipe;
    private ArrayList<OrcamentoProduto> searchLista;
    private ArrayList<Produto> produtosArray;
    private ArrayList<OrcamentoProduto> listaOrcamentoProduto;
    private SharedPreferences sharedPreferences;
    private SearchView searchView;
    private FloatingActionButton fabPdf,fabAddProduto,fabEditOrcamento,fabMenu;
    public static final String DETALHES_Orcamento ="orcamento";
    public static final String UID="uid";
    public static final int ADICIONAR=1;
    public static final int EDITAR=2;
    private Orcamento orcamento;
    public static int orcamento_id = 0;
    private boolean isOpen = false;
    private String orcamento_uid, meuEmpresa,meuMorada,meuEmail,meuTelefone, clienteNome,clienteEmpresa,clienteMorada,clienteNif,clienteTelefone,clienteEmail;
    private ImageView imageView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setTitleColor(R.color.black);
        setContentView(R.layout.activity_detalhes_orcamento);
        total=findViewById(R.id.tvTotalOrcamento);
        data=findViewById(R.id.tvDetalheDataOrcamento);
        descricao=findViewById(R.id.tvDetalheMoradaCliente);
        swipe=findViewById(R.id.swiperefrechDetalheOrcamento);
        listView=findViewById(R.id.lvListaDetalhesOrcamento);
        orcamento_id= getIntent().getIntExtra(DETALHES_Orcamento, 0);
        orcamento_uid = getIntent().getStringExtra(UID);
        searchView = findViewById(R.id.searchViewOrcamentoProdutoProdutos);
        fabMenu = findViewById(R.id.fab_menu_orcamento);
        fabPdf = findViewById(R.id.fab_pdfcreate);
        fabAddProduto = findViewById(R.id.fab_addproduto);
        fabEditOrcamento = findViewById(R.id.fab_editorcamento);

        SingletonProjeto.getInstance(getApplicationContext()).setOrcamentoProdutosListener(this);
        SingletonProjeto.getInstance(getApplicationContext()).getMeusOrcamentosAPI(getApplicationContext());
        SingletonProjeto.getInstance(getApplicationContext()).getMeusOrcamentoProdutosAPI(getApplicationContext());

        SingletonProjeto.getInstance(getApplicationContext()).getAllProdutosAPI(getApplicationContext());
        SingletonProjeto.getInstance(getApplicationContext()).getAllDadosPessoaisAPI(getApplicationContext());
        SingletonProjeto.getInstance(getApplicationContext()).getAllTipologiaAPI(getApplicationContext());
        orcamento= SingletonProjeto.getInstance(getApplicationContext()).getMeusOrcamentosArray(orcamento_id,null,0);
        System.out.println("-->ccccc"+ orcamento_id+ "|"+orcamento );
        if(orcamento!=null){
            System.out.println("-->cc"+orcamento.getId());
            setTitle("Orcamento "+orcamento.getNome());
            total.setText(String.format("%.2f",orcamento.getTotal()) + "€");
            data.setText(String.valueOf(orcamento.getData()));
            descricao.setText(String.valueOf(orcamento.getDescricao()));
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                finish();
                System.out.println("-->aaaaaaaaaaaaaaa");
                OrcamentoProduto orcamentoProduto= (OrcamentoProduto) parent.getItemAtPosition(position);
               /* for(OrcamentoProduto orcamentoProdutos:listaOrcamentoProduto){
                    if(orcamentoProdutos.getId() == orcamentoProduto.getOrcamento_Id()){
                       // listaOrcamentoProduto = orcamentoProduto.getId();
                    }
                }
                Intent intent = new Intent(getApplicationContext(), DetalhesOrcamento.class);
                intent.putExtra(DetalhesOrcamento.DETALHES_Orcamento,orcamento.getId());
                startActivity(intent);*/
                Bundle bundle = new Bundle();


                bundle.putInt("produto_id",orcamentoProduto.getProduto_Id());
                bundle.putString("opcao","AddProdutoOrcamento");
                Intent intent = new Intent(getApplicationContext(), DetalhesProduto.class);

                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        fabMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("-->menu"+isOpen);
                openFab();
            }
        });
        fabEditOrcamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFab();
                Bundle bundle = new Bundle();
                bundle.putInt("cliente_id", orcamento.getCliente_id());
                bundle.putInt("opcao", 2);
                Intent intent = new Intent(getApplicationContext(),CriarAtualizarOrcamento.class);
                intent.putExtras(bundle);
                startActivityForResult(intent,CriarAtualizarOrcamento.EDITAR);
            }
        });
        fabAddProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFab();
                DialogAdicionarProdutoFragment dialogAdicionarProdutoFragment = new DialogAdicionarProdutoFragment();
                dialogAdicionarProdutoFragment.setCancelable(false);
                dialogAdicionarProdutoFragment.show(getSupportFragmentManager(),"MyFragment");
            }
        });
        fabPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFab();
                String name = "orcamento_" + orcamento.getNome();
                createPdf(name);
            }
        });

        procurarMeusProdutosNome();
        swipe.setOnRefreshListener(this);
    }
    private void openFab(){
        if(isOpen){
            fabPdf.setVisibility(View.INVISIBLE);
            fabEditOrcamento.setVisibility(View.INVISIBLE);
            fabAddProduto.setVisibility(View.INVISIBLE);

            System.out.println("-->menu1"+fabPdf.getVisibility());
            isOpen = false;
        }else{
            fabPdf.setVisibility(View.VISIBLE);
            fabEditOrcamento.setVisibility(View.VISIBLE);
            fabAddProduto.setVisibility(View.VISIBLE);
            System.out.println("-->menu2"+fabPdf.getVisibility());
            isOpen = true;
        }
    }
    @Override
    public void onActivityResult(int requestCode,int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    SingletonProjeto.getInstance(getApplicationContext()).getMeusOrcamentosAPI(getApplicationContext());
                    Orcamento orcamento1= SingletonProjeto.getInstance(getApplicationContext()).getMeusOrcamentosArray(orcamento_id,orcamento_uid,-1);


                    if (requestCode == CriarAtualizarOrcamento.EDITAR) {
                        setTitle("Orcamento " + orcamento1.getNome());
                        total.setText(String.format(Locale.US,"%.2f", orcamento.getTotal()) + "€");
                        descricao.setText(String.valueOf(orcamento.getDescricao()));
                        // Snackbar.make(getView(), getString(R.string.cliente_editado_com_sucesso), Snackbar.LENGTH_LONG).show();
                    }
                }
            }, 500);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void procurarMeusProdutosNome()
    {

        ArrayList<OrcamentoProduto> searchOrcamentoProduto = new ArrayList<>();

        produtosArray = SingletonProjeto.getInstance(getApplicationContext()).getProdutosDB();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                searchOrcamentoProduto.clear();
                if (searchLista != null) {
                    for (OrcamentoProduto orcamentoProduto : searchLista) {

                        System.out.println("--->aa");
                        for(Produto produto:produtosArray)
                        {
                            if (produto.getNome().toLowerCase().contains(s.toLowerCase()) && orcamentoProduto.getProduto_Id() == produto.getId()) {
                                searchOrcamentoProduto.add(orcamentoProduto);
                                System.out.println("--->ac");
                            }
                        }
                    }
                    ListaProdutosOrcamentoAdapter listaProdutosOrcamentoAdapter = new ListaProdutosOrcamentoAdapter(getApplicationContext(), searchOrcamentoProduto);
                    listView.setAdapter(listaProdutosOrcamentoAdapter);

                }
                return true;
            }
        });
    }
    @Override
    public void onRefresh() {


                SingletonProjeto.getInstance(getApplicationContext()).getAllProdutosAPI(getApplicationContext());
                SingletonProjeto.getInstance(getApplicationContext()).getMeusOrcamentosAPI(getApplicationContext());
             //   SingletonProjeto.getInstance(getApplicationContext()).getMeusOrcamentoProdutosAPI(getApplicationContext());
                Orcamento orcamento1= SingletonProjeto.getInstance(getApplicationContext()).getMeusOrcamentosArray(orcamento_id,orcamento_uid,0);

                    total.setText(String.format("%.2f", orcamento1.getTotal()) + "€");


        searchView.setQuery("",true);
        searchView.clearFocus();
        swipe.setRefreshing(false);

    }


    @Override
    public void onRefreshOrcamentoProdutos(ArrayList<OrcamentoProduto> listaOrcamentoProdutos) {
        SingletonProjeto.getInstance(getApplicationContext()).getAllProdutosAPI(getApplicationContext());
        if(listaOrcamentoProdutos!=null) {
            System.out.println("-->ccc"+listaOrcamentoProdutos.size() + "|"+orcamento_id+"|");
//            System.out.println("-->orcamentoid:aa"+ orcamento_id + orcamento.getId());
            searchLista = SingletonProjeto.getInstance(getApplicationContext()).getMeusOrcamentoProdutosOrcamentoArray(orcamento_id);
            listView.setAdapter(new ListaProdutosOrcamentoAdapter(getApplicationContext(), searchLista));
        }
    }

    private void createPdf (String name){
        String pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
     //   String pdfPath = Environment.getExternalStorageDirectory().getAbsolutePath().toString();
        File file= new File(pdfPath,name+".pdf");
        try {
            OutputStream outputStream = new FileOutputStream(file);
            PdfWriter writer = new PdfWriter(String.valueOf(file));
            PdfDocument pdfDocument = new PdfDocument(writer);
            Document document = new Document(pdfDocument);
            pdfDocument.setDefaultPageSize(PageSize.A4);
            document.setMargins(0,10,0,10);
            BarcodeQRCode barcodeQRCode = new BarcodeQRCode(orcamento.getUid());
            PdfFormXObject barcordeObject = barcodeQRCode.createFormXObject((new DeviceRgb(0, 0, 0)),pdfDocument);
            Image barcodeImage = new Image(barcordeObject).setWidth(100).setFixedPosition(490,730);
            Paragraph paragraphMeusDados= new Paragraph(meuEmpresa+"\n"+meuMorada+"\n"+meuEmail+"/"+meuTelefone).setBold().setFontSize(16).setTextAlignment(TextAlignment.LEFT).setMarginLeft(10);
            Paragraph paragraphTitulo= new Paragraph("Orcamento "+ orcamento.getNome()).setBold().setFontSize(18).setTextAlignment(TextAlignment.CENTER).setMarginLeft(10);
            Paragraph paragraphclienteDados= new Paragraph("Cliente: "+clienteNome+"\n"+"Empresa: "+clienteEmpresa+"\n"+"Morada: "+clienteMorada+"\n"+
                    "NIF: "+clienteNif+"\n"+"Telefone: "+clienteTelefone+"\n"+"Email: "+clienteEmail).setBold().setFontSize(16).setTextAlignment(TextAlignment.LEFT).setMarginLeft(10);
            float[] width = {62,160,112,112,112};
            Table table = new Table(width);
            table.setHorizontalAlignment(HorizontalAlignment.CENTER);
            table.addCell(new Cell().add(new Paragraph("Nome").setFontSize(12).setBorder(Border.NO_BORDER)).setBackgroundColor(new DeviceRgb(149, 153, 156)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell().add(new Paragraph("Referência").setFontSize(12).setBorder(Border.NO_BORDER)).setBackgroundColor(new DeviceRgb(149, 153, 156)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell().add(new Paragraph("Quantidade").setFontSize(12).setBorder(Border.NO_BORDER)).setBackgroundColor(new DeviceRgb(149, 153, 156)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell().add(new Paragraph("Preço Unitário").setFontSize(12).setBorder(Border.NO_BORDER)).setBackgroundColor(new DeviceRgb(149, 153, 156)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell().add(new Paragraph("Preço Total").setFontSize(12).setBorder(Border.NO_BORDER)).setBackgroundColor(new DeviceRgb(149, 153, 156)).setTextAlignment(TextAlignment.CENTER));

            for (OrcamentoProduto orcamentoProduto : searchLista) {
                for (Produto produto : produtosArray) {
                    if(orcamentoProduto.getProduto_Id() == produto.getId()) {
                        table.addCell(new Cell().add(new Paragraph(produto.getNome()).setFontSize(12).setBorder(Border.NO_BORDER)).setTextAlignment(TextAlignment.CENTER));
                        table.addCell(new Cell().add(new Paragraph(produto.getReferencia()).setFontSize(12).setBorder(Border.NO_BORDER)).setTextAlignment(TextAlignment.CENTER));
                        table.addCell(new Cell().add(new Paragraph(Integer.toString(orcamentoProduto.getQuantidade())).setFontSize(12).setBorder(Border.NO_BORDER)).setTextAlignment(TextAlignment.CENTER));
                        table.addCell(new Cell().add(new Paragraph(Float.toString(produto.getPreco())+"€").setFontSize(12).setBorder(Border.NO_BORDER)).setTextAlignment(TextAlignment.CENTER));
                        table.addCell(new Cell().add(new Paragraph(Float.toString(produto.getPreco() * orcamentoProduto.getQuantidade())+"€").setFontSize(12).setBorder(Border.NO_BORDER)).setTextAlignment(TextAlignment.CENTER));
                    }
                }
            }
            table.addCell(new Cell(1,4).add(new Paragraph("Total do orcamento: ")).setTextAlignment(TextAlignment.RIGHT));
            table.addCell(new Cell().add(new Paragraph(orcamento.getTotal()+"€")).setTextAlignment(TextAlignment.CENTER));


            document.add(barcodeImage);
            document.add(paragraphMeusDados);
            document.add(paragraphTitulo);
            document.add(paragraphclienteDados);
            document.add(table);
            document.close();
            Toast.makeText(getApplicationContext(),"Pdf criado com sucesso", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @Override
    public void onRefreshListaTodosProdutos(ArrayList<Produto> listaProdutos) {

    }

    @Override
    public void onAddOrcamentoProduto() {

    }

    @Override
    public void onErroListaTodosProduto(String message) {

    }

    @Override
    protected void onResume() {

    //    SingletonProjeto.getInstance(getApplicationContext()).getAllProdutosAPI(getApplicationContext());
  //      SingletonProjeto.getInstance(getApplicationContext()).getMeusOrcamentoProdutosAPI(getApplicationContext());
//        SingletonProjeto.getInstance(getApplicationContext()).getMeusOrcamentoProdutosOrcamentoArray(orcamento.getId());
//        onRefresh();
        searchView.setQuery("",true);
        searchView.clearFocus();
        super.onResume();
    }
}