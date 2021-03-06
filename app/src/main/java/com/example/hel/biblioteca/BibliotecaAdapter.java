package com.example.hel.biblioteca;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BibliotecaAdapter extends RecyclerView.Adapter<BibliotecaAdapter.BibliotecaViewHolder> {

    List<Biblioteca> biblioteca;
    Context context;


    public BibliotecaAdapter(Context ctx){

        this.context = ctx;
        this.biblioteca = new ArrayList<>();
        List<Biblioteca> savedList = BibliotecaSharedPreferences.loadBibliotecaList(context, "biblioteca");
        if (savedList != null){
            biblioteca.addAll(savedList);
        }

    }

    @NonNull
    @Override
    public BibliotecaAdapter.BibliotecaViewHolder onCreateViewHolder(@NonNull ViewGroup parent , int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false);
        return new BibliotecaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final BibliotecaViewHolder holder, int position) {
        final Biblioteca bibliotec = biblioteca.get(position);

        Picasso.get().load(new File(bibliotec.getPath())).into(holder.image);
        holder.nome.setText(bibliotec.getNome());
        holder.descricao.setText(bibliotec.getDescricao());
       holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.OpenActivity(bibliotec);
            }
        });
    }

    @Override
    public int getItemCount() {
        return biblioteca.size();
    }

    class BibliotecaViewHolder extends RecyclerView.ViewHolder{

        TextView nome;
        TextView descricao;
        ImageView image;

        public BibliotecaViewHolder(@NonNull View itemView) {
            super(itemView);
            nome=itemView.findViewById(R.id.biblioteca_nome);
            descricao=itemView.findViewById(R.id.biblioteca_descricao);
            image=itemView.findViewById(R.id.biblioteca_imagem);
        }

        public void OpenActivity (Biblioteca biblioteca){
            Intent intent = new Intent(context, BibliotecaDetailActivicty.class);
           intent.putExtra("nome",biblioteca.getNome());
           intent.putExtra("descricao",biblioteca.getDescricao());
//           intent.putExtra("res",biblioteca.getPath());
//            context.startActivity(intent);


        }

    }
}
