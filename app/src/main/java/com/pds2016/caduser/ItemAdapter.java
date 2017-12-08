package com.pds2016.caduser;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ericcalasans on 18/10/16.
 */

public class ItemAdapter extends BaseAdapter {
    Context context;
    ArrayList<ItemLista> lista;

    LayoutInflater inflater;
    //construtor

    public Bitmap bmp;

    public ItemAdapter(Context context, ArrayList<ItemLista> lista) {
        this.context = context;
        this.lista = lista;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int i) {
        return lista.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ItemLista il = lista.get(i);

        //Transforma xml em objeto Java
        View linhaLista = inflater.inflate(R.layout.lista_usuarios, null);

        TextView nome = (TextView) linhaLista.findViewById(R.id.lsNome);
        ImageView foto = (ImageView) linhaLista.findViewById(R.id.lsImagem);

        nome.setText(il.getNome());

        String s = il.getBase64();

        bmp = il.fazFoto(s);

        foto.setImageBitmap(bmp);

        return linhaLista;

    }
}
