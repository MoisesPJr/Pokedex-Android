package com.example.pokecarguero.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.pokecarguero.models.Pokemon;
import com.example.pokecarguero.models.Tipos;

import java.util.ArrayList;
import java.util.List;

public class TipoDao implements ITipoDao {
    private SQLiteDatabase escreve;
    private SQLiteDatabase le;

    public TipoDao(Context context) {
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        escreve = dbHelper.getWritableDatabase();
        le = dbHelper.getReadableDatabase();
    }


    @Override
    public boolean salvaTodos(int id, String name, int slot) {
        ContentValues cv = new ContentValues();
        cv.put("nomeTipo", name);
        cv.put("idPokemon", id);
        cv.put("slot", slot);
        try {
            escreve.insertWithOnConflict(DatabaseHelper.TABELA_TIPOS, null, cv, SQLiteDatabase.CONFLICT_IGNORE);
            Log.i("INFO", "Pokemon com o id: " + id + " Salvo");
        } catch (Exception e) {
            Log.e("INFO", "Erro ao salvar pokemon: "+e.getMessage() );
            return false;
        }

        return true;
    }


    @Override
    public List<Tipos> listaPokemons() {

        return null;
    }

    public ArrayList<String> retornaListaTipos(){

        ArrayList<String> nomes = new ArrayList<>();

        String sql = "SELECT nomeTipo FROM tipos GROUP BY nomeTipo ";

        Cursor c = le.rawQuery(sql,null);
        while(c.moveToNext()){

            String nomeTipo = c.getString(c.getColumnIndex("nomeTipo"));

            nomes.add(nomeTipo);

        }
        return nomes;
    }

    public ArrayList<String> retornaTipo(int id){

        ArrayList<String> nomes = new ArrayList<>();

        String sql = "SELECT * FROM tipos WHERE idPokemon = "+id;

        Cursor c = le.rawQuery(sql,null);
        while(c.moveToNext()){

            String nomeTipo = c.getString(c.getColumnIndex("nomeTipo"));

            nomes.add(nomeTipo);

        }
        return nomes;
    }


}
