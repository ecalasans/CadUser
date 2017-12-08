package com.pds2016.caduser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Lista extends Activity {

    //String[] usuarios = {"Eric", "Amanda", "Nicolas"};

    final static String ENDERECO = "http://lista2.pds2016.kinghost.net/lista.php";

    ArrayList<ItemLista> usuarios;

    ListView lstUsuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        lstUsuarios = (ListView) findViewById(R.id.lstUsuarios);

        BKGLista bkgLista = new BKGLista(this);
        try {
            usuarios = bkgLista.execute(ENDERECO).get();

            ItemAdapter adapter = new ItemAdapter(this, usuarios);
            lstUsuarios.setAdapter(adapter);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }
}
