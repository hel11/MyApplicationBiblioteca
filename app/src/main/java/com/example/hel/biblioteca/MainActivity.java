package com.example.hel.biblioteca;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;


public class MainActivity extends AppCompatActivity {

    RecyclerView bibliotecaRecylerView;
    BibliotecaAdapter bibliotecaAdapter;
    Dialog addItemDialog;
    ImageView newImage;
    EditText newTitle;
    EditText newDescription;
    Button saveButton;
    String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bibliotecaRecylerView = findViewById(R.id.biblioteca_recycler_view);
        bibliotecaRecylerView.setLayoutManager(new LinearLayoutManager(this));
        bibliotecaAdapter = new BibliotecaAdapter(this);
        bibliotecaRecylerView.setAdapter(bibliotecaAdapter);

        // mostrar codigo
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        String value = sharedPref.getString("text", "Não tem valor");

        getSupportActionBar().setTitle(value);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showDialog();

//                // codigo para salvar
//                SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
//                SharedPreferences.Editor editor = sharedPref.edit();
//                editor.putString("text", "Lista de livros");
//                editor.commit();

            }
        });




    }
        public  void  showDialog(){
            addItemDialog = new Dialog(MainActivity.this);
            addItemDialog.setContentView(R.layout.add_item_dialog);
            // codigo para pedir permissão para adicionar imagen
            newImage = addItemDialog.findViewById(R.id.new_image);
            newImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (isStoragePermisssionGrantend()){
                        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
                        galleryIntent.setType("image/*"); //expecificar o tipo de ficheiro que quer aceder
                        startActivityForResult(galleryIntent, 12);
                    }else {
                        Toast.makeText(MainActivity.this,"Você não tem permissão", Toast.LENGTH_LONG).show();
                        ActivityCompat.requestPermissions(MainActivity.this, new  String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 12);
                    }
                }
            });

            newTitle = addItemDialog.findViewById(R.id.new_title);
            newDescription = addItemDialog.findViewById(R.id.new_description);
            saveButton = addItemDialog.findViewById(R.id.save_button); // para salvar
            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   // addItemDialog.dismiss(); serve para fechar um tela
                    saveNewItem();
                }
            });
            addItemDialog.setTitle("Adicionar novo item");
            addItemDialog.show();

        }


        public  void saveNewItem(){

            if(newTitle.getText().toString().isEmpty() || newDescription.getText().toString().isEmpty()){
                Toast.makeText(MainActivity.this, "os campos não podem estar nulos", Toast.LENGTH_LONG).show();
            }else {
                String title = newTitle.getText().toString();
                String description = newDescription.getText().toString();
                bibliotecaAdapter.biblioteca.add(new Biblioteca(title, description,path));
                bibliotecaAdapter.notifyDataSetChanged();
                BibliotecaSharedPreferences.saveBibliotecaList(bibliotecaAdapter.biblioteca, MainActivity.this, "biblioteca");
                path = null;
                addItemDialog.dismiss();

            }
        }

        public boolean  isStoragePermisssionGrantend(){

        return ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE )== PackageManager.PERMISSION_GRANTED;

        }


//metodo da classe mãe

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data != null) {
            Uri uri = data.getData();
            Log.i("MainActivity", "Localização do ficheiro: " + uri.getPath());
             path = getRealPathFormURI(uri);
            File file = new File(path);
            //Picasso.get().load(file).into(newImage);
            Picasso.get().load(file).into(newImage);
        }
    }

    // localização real do ficheiro
    public String getRealPathFormURI(Uri contentURI){

        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        String result = cursor.getString(idx);
        cursor.close();
        return result;
    }
}
