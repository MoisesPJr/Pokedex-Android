package com.example.pokecarguero.SQLite;

import com.example.pokecarguero.models.Pokemon;
import com.example.pokecarguero.models.Tipos;

import java.util.List;

public interface ITipoDao {
    boolean salvaTodos(int id, String name,int slot);
    List<Tipos> listaPokemons();

}
