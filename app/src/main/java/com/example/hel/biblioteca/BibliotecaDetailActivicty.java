package com.example.hel.biblioteca;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class BibliotecaDetailActivicty extends AppCompatActivity {

    ImageView res;
    TextView nome;
    TextView descricao;

        ImageView bibliotecaDetailImage;
        TextView bibliotecaTextName;
        TextView bibliotecaTextDescricao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biblioteca_detail_activicty);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        int res = getIntent().getIntExtra("res",R.drawable.livro1);
        String nome = getIntent().getStringExtra("nome");
        String descricao = getIntent().getStringExtra("descricao");


        bibliotecaTextName = findViewById(R.id.biblioteca_nome);
        bibliotecaTextName.setText(nome);

        bibliotecaTextDescricao = findViewById(R.id.biblioteca_descricao);
        bibliotecaTextDescricao.setText(descricao);

        bibliotecaDetailImage = findViewById(R.id.biblioteca_imagem);
        bibliotecaDetailImage.setImageResource(res);









    }

}
