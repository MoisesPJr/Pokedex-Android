package com.example.pokecarguero.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;


public class DatabaseHelper extends SQLiteOpenHelper {

    public static int VERSION = 1;
    public static String NOME_DB = "DB_POKEDEX";
    public static String TABELA_POKEMONS = "pokemons";
    public static String TABELA_TIPOS = "tipos";


    public DatabaseHelper(@Nullable Context context) {
        super(context, NOME_DB, null, VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = " CREATE TABLE IF NOT EXISTS "+TABELA_POKEMONS+" " +
                "(id INT(3) PRIMARY KEY, status INT(1) DEFAULT 2, nome VARCHAR)";

        String sqlTipo = " CREATE TABLE IF NOT EXISTS "+TABELA_TIPOS+"" +
                "( idPokemon INTEGER , nomeTipo VARCHAR, slot INTEGER, PRIMARY KEY(idPokemon,slot))";
        try {
            db.execSQL(sql);
            db.execSQL(sqlTipo);
            Log.i("INFO_DB","Sucesso ao criar a tabela");
        }catch (Exception e){
            Log.i("INFO_DB","Erro ao criar a tabela"+e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
