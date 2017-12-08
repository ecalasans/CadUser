package com.pds2016.caduser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Main extends Activity {

    EditText etLogin, etSenha;

    final static String URL = "http://lista2.pds2016.kinghost.net/login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView btLogin = (TextView) findViewById(R.id.btLogin);
        TextView btLimpar = (TextView) findViewById(R.id.btLimpar);
        etLogin = (EditText) findViewById(R.id.etLogin);
        etSenha = (EditText) findViewById(R.id.etSenha);



        btLimpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                limparCampos((ViewGroup) findViewById(R.id.activity_main));
            }
        });

    }

    public void efetuaLogin(View view){
        BKGLogin login = new BKGLogin(this);

        String strLogin = etLogin.getText().toString();
        String strSenha = etSenha.getText().toString();

        login.execute(URL,strLogin,strSenha);

    }

    public void limparCampos(ViewGroup grupo){

        for(int i = 0; i < grupo.getChildCount(); ++i){
            View v = grupo.getChildAt(i);
            if(v instanceof EditText){
                ((EditText) v).setText("");
            }
        }

    }
}

