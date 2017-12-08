package com.pds2016.caduser;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by ericcalasans on 20/10/16.
 */

public class BKGDetalhe extends AsyncTask<String, Void, String> {

    Context ctx;

    String url;
    String nome;

    //Construtor com Context
    public BKGDetalhe(Context contexto){
        this.ctx = contexto;
    }



    @Override
    protected String doInBackground(String... params) {
        url = params[0];
        nome = params[1];


        URL endereco = null;
        try {
            endereco = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try {
            //Abre conexao com a internet
            HttpURLConnection conex = (HttpURLConnection) endereco.openConnection();

            //Habilita o método POST
            conex.setRequestMethod("POST");

            //Habilita entrada de dados
            conex.setDoInput(true);

            //Habilita saída de dados
            conex.setDoOutput(true);

            //Envia login e senha para o script php
            OutputStream os = conex.getOutputStream();
            BufferedWriter escritor = new BufferedWriter(new OutputStreamWriter(os));

            //Constroi a string de dados a ser passada
            Uri.Builder stringDados = new Uri.Builder();
            stringDados.appendQueryParameter("nome", nome);


            String dadosFormatados = stringDados.build().getEncodedQuery().toString();

            escritor.write(dadosFormatados);
            escritor.flush();
            escritor.close();
            os.close();
            conex.connect();

            //Obtem a resposta do servidor
            InputStream is = conex.getInputStream();
            BufferedReader leitor = new BufferedReader(new InputStreamReader(is));

            //Constroi string da resposta
            StringBuilder sb = new StringBuilder();
            String l;

            while ((l = leitor.readLine()) != null){
                sb.append(l);
            }
            return sb.toString();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(String s) {
        try {
            JSONObject json = new JSONObject(s);

            String nome = json.getString("nome");
            String nascProv = json.getString("nasc");
            String nasc = nascProv.replace("\\/","/");
            String email = json.getString("email");
            String foto = json.getString("foto");
            String rua = json.getString("rua");
            String numero = json.getString("numero");
            String bairro = json.getString("bairro");
            String cep = json.getString("cep");
            String cidade = json.getString("cidade");
            String estado = json.getString("estado");

            Intent menu = new Intent(ctx, Menu.class);
            Bundle parametros = new Bundle();
            parametros.putString("nome", nome);
            parametros.putString("nasc", nasc);
            parametros.putString("email", email);
            parametros.putString("foto", foto);
            parametros.putString("rua", rua);
            parametros.putString("numero", numero);
            parametros.putString("bairro", bairro);
            parametros.putString("cep", cep);
            parametros.putString("cidade", cidade);
            parametros.putString("estado", estado);

            menu.putExtras(parametros);

            ctx.startActivity(menu);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
