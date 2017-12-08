package com.pds2016.caduser;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

/**
 * Created by ericcalasans on 17/10/16.
 */

public class ItemLista {
    private String nome;
    private String base64;

    public ItemLista(String base64, String nome) {
        this.base64 = base64;
        this.nome = nome;
    }

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Bitmap fazFoto(String base64){
        Bitmap foto;

        byte[] bytes = Base64.decode(base64,0);
        foto = BitmapFactory.decodeByteArray(bytes,0,bytes.length,null);

        return foto;
    }
}
