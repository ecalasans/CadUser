package com.pds2016.caduser;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;

import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ericcalasans on 17/10/16.
 */

public class BKGLista extends AsyncTask<String, Void, ArrayList<ItemLista>>{
    Context context;

    ArrayList<ItemLista> itens = new ArrayList<ItemLista>();

    public BKGLista(Context ctx){
        this.context = ctx;

    }

    String url;

    @Override
    protected ArrayList<ItemLista> doInBackground(String... strings) {
        url  = strings[0];

        URL endereco = null;
        try {
            endereco = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        HttpURLConnection conex = null;
        try {
            conex = (HttpURLConnection) endereco.openConnection();
            conex.setDoOutput(true);
            conex.setDoInput(true);
            conex.setRequestMethod("POST");

            InputStream is = conex.getInputStream();
            BufferedReader leitor = new BufferedReader(new InputStreamReader(is));

            StringBuilder sb = new StringBuilder();
            String l;
            while ((l = leitor.readLine()) != null){
                sb.append(l + "\n");
            }

            String s = sb.toString();

            try {
                JSONArray ja = new JSONArray(s);
                JSONObject jo = null;

                itens.clear();

                for (int i = 0; i < ja.length() ; i++) {
                    jo = ja.getJSONObject(i);

                    ItemLista il = new ItemLista(null,null);
                    String nome = jo.getString("nome");
                    String fotoProv = jo.getString("foto");
                    String foto = fotoProv.replace("\\/", "/");

                    il.setNome(nome);
                    il.setBase64(foto);

                    itens.add(il);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return itens;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }



    }

    @Override
    protected void onPostExecute(ArrayList<ItemLista> itemListas) {
        super.onPostExecute(itemListas);
    }
}
