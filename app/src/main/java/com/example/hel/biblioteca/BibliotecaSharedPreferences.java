package com.example.hel.biblioteca;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class BibliotecaSharedPreferences {


    public  static void  saveBibliotecaList(List<Biblioteca> biblioteca, Context context, String key){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson  = new Gson();
        String json = gson.toJson(biblioteca);
        editor.putString(key, json);
        editor.apply();
    }


    public  static List<Biblioteca> loadBibliotecaList(Context context, String key){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        Gson  gson = new Gson();
        String json = preferences.getString(key, null);
        Type type = new TypeToken<List<Biblioteca>>(){}.getType();

        return gson.fromJson(json, type);

    }
}
