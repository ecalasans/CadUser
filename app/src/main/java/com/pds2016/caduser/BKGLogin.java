package com.pds2016.caduser;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import org.apache.http.protocol.HTTP;
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
 * Created by ericcalasans on 17/10/16.
 */

public class BKGLogin extends AsyncTask<String, Void, String> {
    Context ctx;

    String url;
    String login, senha;

    //Construtor com Context
    public BKGLogin(Context contexto){
        this.ctx = contexto;
    }

    @Override
    protected String doInBackground(String... params) {
        url = params[0];
        login = params[1];
        senha = params[2];

        URL endereco = null;
        try {
            endereco = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        if(login == "convidado"){
            return login;
        } else {
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
                stringDados.appendQueryParameter("login", login);
                stringDados.appendQueryParameter("senha", senha);

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

    }

    @Override
    protected void onPostExecute(String s) {
        if(s == "convidado"){
            Intent abrirCadastro = new Intent(ctx,Cadastro.class);
            ctx.startActivity(abrirCadastro);
        } else {
            try {
                JSONObject json = new JSONObject(s);

                String nome = json.getString("nome");
                String nascProv = json.getString("nasc");
                String nasc = nascProv.replace("\\/","/");
                String email = json.getString("email");
                String foto = json.getString("foto");

                Intent menu = new Intent(ctx, Menu.class);
                Bundle parametros = new Bundle();
                parametros.putString("nome", nome);
                parametros.putString("nasc", nasc);
                parametros.putString("email", email);
                parametros.putString("foto", foto);

                menu.putExtras(parametros);

                ctx.startActivity(menu);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }



    }
}
