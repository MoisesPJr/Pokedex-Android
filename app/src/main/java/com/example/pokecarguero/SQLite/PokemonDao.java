package com.example.pokecarguero.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.pokecarguero.models.Pokemon;

import java.util.ArrayList;
import java.util.List;

public class PokemonDao implements IFavoritoDao {


    private SQLiteDatabase escreve;
    private SQLiteDatabase le;

    public PokemonDao(Context context) {
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        escreve = dbHelper.getWritableDatabase();
        le = dbHelper.getReadableDatabase();
    }

    @Override
    public boolean salva(int id, String name,int status) {
        ContentValues cv = new ContentValues();
        cv.put("status", status);
        cv.put("nome",name);
        cv.put("id",id);
        try {
            escreve.insertWithOnConflict(DatabaseHelper.TABELA_POKEMONS, null, cv, SQLiteDatabase.CONFLICT_REPLACE);
            Log.i("INFO", "Pokemon com o id: " + id + " Salvo");
        } catch (Exception e) {
            Log.e("INFO", "Erro ao salvar pokemon");
            return false;
        }

        return true;
    }

    @Override
    public boolean salvaTodos(int id, String name) {
            ContentValues cv = new ContentValues();
            cv.put("nome",name);
            cv.put("id",id);
            try {
                escreve.insertWithOnConflict(DatabaseHelper.TABELA_POKEMONS, null, cv, SQLiteDatabase.CONFLICT_IGNORE);
                Log.i("INFO", "Pokemon com o id: " + id + " Salvo");
            } catch (Exception e) {
                Log.e("INFO", "Erro ao salvar pokemon");
                return false;
            }

            return true;
    }

    @Override
    public boolean atualiza(int id,int status) {
        String sql = "UPDATE "+DatabaseHelper.TABELA_POKEMONS+ " SET status = "+status+" WHERE id = " + id+";";
        ContentValues cv = new ContentValues();
       try {
           escreve.rawQuery(sql, null);
           Log.i("INFO", "Pokemon com o id: " + id + " atualizado para status = "+status);
       }catch (Exception e){
           Log.e("INFO", "Erro ao atualizar pokemon");

       }
        return false;
    }

    @Override
    public List<Pokemon> listaPokemonsOrdenadosFav() {

        List<Pokemon> listaIdPokemon = new ArrayList<>();


        String sql = "SELECT * FROM " + DatabaseHelper.TABELA_POKEMONS + " ORDER BY STATUS ASC;";
        Cursor c = le.rawQuery(sql, null);
        while (c.moveToNext()) {
            Pokemon p;
            int id = c.getInt(c.getColumnIndex("id"));
            String nome = c.getString(c.getColumnIndex("nome"));
            int status = c.getInt(c.getColumnIndex("status"));

            p = new Pokemon(id,nome,status);
            listaIdPokemon.add(p);
        }
        return listaIdPokemon;
    }




    @Override
    public List<Pokemon> listaPokemons() {

        List<Pokemon> listaIdPokemon = new ArrayList<>();


        String sql = "SELECT * FROM " + DatabaseHelper.TABELA_POKEMONS +" ORDER BY id;";
        Cursor c = le.rawQuery(sql, null);
        while (c.moveToNext()) {
            int id = c.getInt(c.getColumnIndex("id"));
            int status = c.getInt(c.getColumnIndex("status"));
            String nome = c.getString(c.getColumnIndex("nome"));
            Pokemon p = new Pokemon(id,nome,status);
            listaIdPokemon.add(p);
        }
        return listaIdPokemon;
    }


    public List<Pokemon> getPokemonsPorTipo(String tipo){

        List<Pokemon> listaIdPokemon = new ArrayList<>();


        String sql = "select * from pokemons, tipos where idPokemon = id and  nomeTipo = '"+tipo+"' order by idPokemon;";
        Cursor c = le.rawQuery(sql, null);
        while (c.moveToNext()) {
            int id = c.getInt(c.getColumnIndex("id"));
            int status = c.getInt(c.getColumnIndex("status"));
            String nome = c.getString(c.getColumnIndex("nome"));
            Pokemon p = new Pokemon(id,nome,status);
            listaIdPokemon.add(p);
        }
        return listaIdPokemon;

        //select * from pokemons, tipos where idPokemon = id and  nomeTipo =
    }
}
