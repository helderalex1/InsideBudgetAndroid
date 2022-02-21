package com.example.InsideBudget.Users.Instalador;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.InsideBudget.Modelo.Orcamento;
import com.example.InsideBudget.Modelo.SingletonProjeto;
import com.example.InsideBudget.Users.Instalador.DetalhesOrcamento;
import com.example.InsideBudget.Users.Instalador.ListaMeusOrcamentosFragment;
import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler{
    private ZXingScannerView scannerView;
    private ArrayList<Orcamento> orcamentos;
    private boolean flagOrcamento = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);
        Dexter.withContext(getApplicationContext()).withPermission(Manifest.permission.CAMERA).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                scannerView.startCamera();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                onBackPressed();
                Toast.makeText(getApplicationContext(),"Permição da câmera recusada", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                Toast.makeText(getApplicationContext(),"Permissão necessária", Toast.LENGTH_SHORT).show();
                permissionToken.continuePermissionRequest();
            }
        }).check();

    }

    @Override
    public void handleResult(Result rawResult) {
        orcamentos =SingletonProjeto.getInstance(getApplicationContext()).getMeusOrcamentosDB();
        for(Orcamento orcamento: orcamentos) {
            if (orcamento.getUid().equals(rawResult.toString())){
                flagOrcamento = true;
                ListaMeusOrcamentosFragment.UID = rawResult.toString();
                Intent intent = new Intent(getApplicationContext(), DetalhesOrcamento.class);
                intent.putExtra(DetalhesOrcamento.UID, rawResult.toString());
                startActivity(intent);
        }
        }if(!flagOrcamento){
            Toast.makeText( getApplicationContext(),"Não foi possível identificar o orçamento",Toast.LENGTH_SHORT).show();
        }
        onBackPressed();
    }

    @Override
    public void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }

    @Override
    public void onResume() {
        super.onResume();
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }
}