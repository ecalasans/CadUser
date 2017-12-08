package com.pds2016.caduser;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

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
import java.net.URLEncoder;

/**
 * Created by ericcalasans on 09/10/16.
 */

public class BKGInsere extends AsyncTask<String, Void, String> {
    Context contexto;

    BKGInsere(Context cont){
        this.contexto = cont;
    }

    String resp;


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        String nome = params[0];
        String rua = params[1];
        String numero = params[2];
        String cep = params[3];
        String bairro = params[4];
        String cidade = params[5];
        String estado = params[6];
        String nasc = params[7];
        String celular = params[8];
        String residencial = params[9];
        String email = params[10];
        String login = params[11];
        String senha = params[12];

        String url = params[13];

        String foto = params[14];

        Log.i("Dados", url);

        //Cria a conexao com o script php
        try {
            URL endereco = new URL(url);
            Log.i("Dados", endereco.toString());
            HttpURLConnection conex = (HttpURLConnection) endereco.openConnection();

            conex.setRequestMethod("POST");
            conex.setDoOutput(true);
            conex.setDoInput(true);

            Uri.Builder dados = new Uri.Builder();
            dados.appendQueryParameter("nome", nome);
            dados.appendQueryParameter("foto", foto);
            dados.appendQueryParameter("rua", rua);
            dados.appendQueryParameter("numero", numero);
            dados.appendQueryParameter("cep", cep);
            dados.appendQueryParameter("bairro", bairro);
            dados.appendQueryParameter("cidade", cidade);
            dados.appendQueryParameter("estado", estado);
            dados.appendQueryParameter("nasc", nasc);
            dados.appendQueryParameter("celular", celular);
            dados.appendQueryParameter("residencial", residencial);
            dados.appendQueryParameter("email", email);
            dados.appendQueryParameter("login", login);
            dados.appendQueryParameter("senha", senha);

            String postDados = dados.build().getEncodedQuery().toString();

            Log.i("Dados", postDados);

            OutputStream os = conex.getOutputStream();

            BufferedWriter escritor = new BufferedWriter(new OutputStreamWriter(os));

            escritor.write(postDados);
            escritor.flush();
            escritor.close();
            os.close();
            conex.connect();

            //Recebe resposta
            InputStream is = conex.getInputStream();
            BufferedReader leitor = new BufferedReader(new InputStreamReader(is));

            StringBuilder resposta = new StringBuilder();
            String l;
            while ((l = leitor.readLine()) != null){
                resposta.append(l);
            }

            resp = resposta.toString();
            conex.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return resp;

    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {
        Toast.makeText(contexto, result,Toast.LENGTH_SHORT).show();
    }
}
