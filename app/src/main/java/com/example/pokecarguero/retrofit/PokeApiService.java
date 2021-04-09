package com.example.pokecarguero.retrofit;

import com.example.pokecarguero.models.PokemonApiResposta;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PokeApiService {

    @GET("pokemon??limit")
    Call<PokemonApiResposta> getPokemonList(@Query("limit") int limit);

    @GET("pokemon/{num}")
    Call<PokemonApiResposta> getPokemon(@Path("num") int id);

}


