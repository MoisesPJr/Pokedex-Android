package com.example.pokecarguero.SQLite;

import com.example.pokecarguero.models.Pokemon;

import java.util.List;

public interface IFavoritoDao {

     boolean salva(int id, String name,int status);
     boolean salvaTodos(int id, String name);
     boolean atualiza(int id,int status);
     List<Pokemon> listaPokemonsOrdenadosFav();
     List<Pokemon> listaPokemons();


}
