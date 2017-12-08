package com.pds2016.caduser;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Menu extends Activity {

    public Bitmap bmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        TextView btSair = (TextView) findViewById(R.id.btSair);
        TextView btNovo = (TextView) findViewById(R.id.btNovo);
        TextView btListar = (TextView) findViewById(R.id.btListar);
        TextView txtNome = (TextView) findViewById(R.id.txtNome);
        TextView txtNasc = (TextView) findViewById(R.id.txtNasc);
        TextView txtEmail = (TextView) findViewById(R.id.txtEmail);
        ImageView imFoto = (ImageView) findViewById(R.id.imFoto);

        final Intent abrirNovo = new Intent(this, Cadastro.class);
        final Intent abrirLista = new Intent(this, Lista.class);

        Intent recebeDados = getIntent();
        Bundle dados = recebeDados.getExtras();

        txtNome.setText(dados.getString("nome").toString());
        txtNasc.setText(dados.getString("nasc").toString());
        txtEmail.setText(dados.getString("email").toString());

        bmp = constroiFoto(dados.getString("foto").toString());

        imFoto.setImageBitmap(bmp);

        btSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btNovo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(abrirNovo);
            }
        });

        btListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(abrirLista);
            }
        });


    }

    /*public void abrirLista(){
        String endereco = "http://lista2.pds2016.kinghost.net/lista.php";

        BKGLista bkgLista = new BKGLista(this);
        bkgLista.execute(endereco);
    }*/

    public Bitmap constroiFoto(String str){
        Bitmap fotoReconstruida;

        byte[] bytes = Base64.decode(str,0);
        fotoReconstruida = BitmapFactory.decodeByteArray(bytes,0,bytes.length,null);

        return fotoReconstruida;

    }
}
