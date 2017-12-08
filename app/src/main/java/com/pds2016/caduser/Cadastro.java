package com.pds2016.caduser;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class Cadastro extends Activity {

    String nome, strFoto, nasc, rua, numero, cep, bairro, cidade, estado, celular, residencial,
        email, login, senha;

    EditText etNome, etNasc, etRua, etN, etCEP, etBairro, etCidade, etEstado, etCel, etRes,
        etEmail, etLogin, etSenha;

    ImageView btFoto;


    File arq;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        TextView btEnviar = (TextView) findViewById(R.id.btEnviar);
        TextView btLimpar = (TextView) findViewById(R.id.btLimpar);

        etNome = (EditText) findViewById(R.id.etNome);
        etNasc = (EditText) findViewById(R.id.etNasc);
        etRua = (EditText) findViewById(R.id.etRua);
        etN = (EditText) findViewById(R.id.etN);
        etCEP = (EditText) findViewById(R.id.etCEP);
        etBairro = (EditText) findViewById(R.id.etBairro);
        etCidade = (EditText) findViewById(R.id.etCidade);
        etEstado = (EditText) findViewById(R.id.etEstado);
        etCel = (EditText) findViewById(R.id.etCel);
        etRes = (EditText) findViewById(R.id.etRes);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etLogin = (EditText) findViewById(R.id.etLogin);
        etSenha = (EditText) findViewById(R.id.etSenha);

        btFoto = (ImageView) findViewById(R.id.btFoto);
    }

    public void insere(View view){
        nome = etNome.getText().toString();
        nasc = etNasc.getText().toString();
        rua = etRua.getText().toString();
        numero = etN.getText().toString();
        cep = etCEP.getText().toString();
        bairro = etBairro.getText().toString();
        cidade = etCidade.getText().toString();
        estado = etEstado.getText().toString();
        celular = etCel.getText().toString();
        residencial = etRes.getText().toString();
        email = etEmail.getText().toString();
        login = etLogin.getText().toString();
        senha = etSenha.getText().toString();

        String url = "http://lista2.pds2016.kinghost.net/insere.php";

        BKGInsere insercao = new BKGInsere(this);
        insercao.execute(nome, rua, numero, cep, bairro, cidade, estado, nasc, celular,
                    residencial, email, login, senha, url, strFoto);
    }

    public void tirarFoto(View view){
        Intent abrirCamera = new Intent("android.media.action.IMAGE_CAPTURE");

        startActivityForResult(abrirCamera, 10);

    }

    public Uri construirArqFoto(String nomeFoto){
        Uri endFoto;

        arq = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString()
                    + File.separator + nomeFoto + ".jpg");

        endFoto = Uri.fromFile(arq);

        return endFoto;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ((requestCode == 10) && (data !=null)){

            Bundle bits = data.getExtras();

            if (bits !=null){
                Bitmap foto = (Bitmap) bits.get("data");
                btFoto.setImageBitmap(foto);

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                foto.compress(Bitmap.CompressFormat.JPEG, 80, stream);

                byte[] bytesDados = stream.toByteArray();
                strFoto = Base64.encodeToString(bytesDados,0);
                Log.i("decoded", strFoto.toString());
            }


        }
    }


}
