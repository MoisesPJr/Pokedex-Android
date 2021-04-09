package com.example.pokecarguero.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokecarguero.R;
import com.example.pokecarguero.SQLite.PokemonDao;
import com.example.pokecarguero.SQLite.TipoDao;
import com.example.pokecarguero.Util.DialogUtil;
import com.example.pokecarguero.Util.RecyclerItemClickListener;
import com.example.pokecarguero.adapter.PokemonAdapter;
import com.example.pokecarguero.models.Pokemon;
import com.example.pokecarguero.models.PokemonApiResposta;
import com.example.pokecarguero.models.Tipos;
import com.example.pokecarguero.retrofit.PokeApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PokemonAdapter pokemonAdapter;
    private ArrayList<Pokemon> ids = new ArrayList<>();
    private PokemonDao pokemonDao;
    private int count = 0;
    private TipoDao tipoDao;
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pokemonDao = new PokemonDao(this);
        tipoDao = new TipoDao(this);
        retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        configuraRecyclerView();
        obterPokemon();
        Toolbar toolbar = findViewById(R.id.toolbarPrincipal);
        toolbar.setTitle("Pokédex");
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onResume() {
        configuraRecyclerView();
        pokemonAdapter.addListPokemon(pokemonDao.listaPokemons());
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menuFiltroFavorito:
                    listarFavorito();
                break;
            case R.id.menuFiltroTipo:
                listarTipo();
                break;
            case R.id.menuIdentificador:
                listarIdentificador();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void listarTipo() {

        ArrayList<Pokemon> listaPokemonTipo = new ArrayList<>();
        ArrayList<String> listaTipos = new ArrayList<>();
        listaTipos.addAll(tipoDao.retornaListaTipos());
        DialogUtil.escolherDeUmaLista(this, "Tipos de Pókemons", listaTipos, new DialogUtil.OnRetornoEscolha() {
            @Override
            public void onSucesso(int posicaoDoItem) {
                listaPokemonTipo.addAll(pokemonDao.getPokemonsPorTipo(listaTipos.get(posicaoDoItem)));
                configuraRecyclerView();
                pokemonAdapter.addListPokemon(listaPokemonTipo);
            }

            @Override
            public void onCancelado() {
                return;
            }
        });




    }

    private void listarFavorito() {
        PokemonDao pokemonDao = new PokemonDao(getApplicationContext());
        ArrayList<Pokemon> pokemonsList = new ArrayList<>();
        pokemonsList.addAll(pokemonDao.listaPokemonsOrdenadosFav());
        configuraRecyclerView();
        pokemonAdapter.addListPokemon(pokemonsList);

    }

    private void listarIdentificador(){
        PokemonDao pokemonDao = new PokemonDao(getApplicationContext());
        configuraRecyclerView();
        pokemonAdapter.addListPokemon(pokemonDao.listaPokemons());
    }


    private void configuraRecyclerView() {
        recyclerView = findViewById(R.id.recyclerPokemon);
        pokemonAdapter = new PokemonAdapter(this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(pokemonAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Pokemon p;
                p = pokemonAdapter.retornaPokemon(position);
                abrirCaracteristicas(p.getId(), p.getName(),p.getStatus());
            }

            @Override
            public void onLongItemClick(View view, int position) {
            }

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        }));
    }

    private void abrirCaracteristicas(int id, String nome,int status) {
        Intent i = new Intent(MainActivity.this, CaracteristicasActivity.class);
        i.putExtra("KEY_CARACTERISTICAS", id);
        i.putExtra("KEY_NOME_CARACTERISTICAS", nome);
        i.putExtra("KEY_STATUS",status);
        startActivity(i);
    }

    private void obterPokemon() {

        PokeApiService pokeApiService = retrofit.create(PokeApiService.class);
        Call<PokemonApiResposta> pokemonRespostaLista = pokeApiService.getPokemonList(151);
        pokemonRespostaLista.enqueue(new Callback<PokemonApiResposta>() {
            @Override
            public void onResponse(Call<PokemonApiResposta> call, Response<PokemonApiResposta> response) {
                if (response.isSuccessful()) {
                    PokemonApiResposta pokemonApiResposta = response.body();
                    ArrayList<Pokemon> listaPokemon = pokemonApiResposta.getResults();
                    for (int i = 0; i < listaPokemon.size(); i++) {
                        Pokemon p = new Pokemon();
                        p.setId(i + 1);
                        p.setName(listaPokemon.get(i).getName());
                        pokemonDao.salvaTodos(p.getId(), p.getName());
                    }
                    obterTipos(pokemonDao.listaPokemons());
                    pokemonAdapter.addListPokemon(pokemonDao.listaPokemons());
                } else {
                    Log.i("POKEMON", "Erro");
                }
            }

            @Override
            public void onFailure(Call<PokemonApiResposta> call, Throwable t) {
                Log.i("POKEMON", t.getMessage());

            }
        });

    }

    private void obterTipos(List<Pokemon> id) {
        PokeApiService pokeApiService = retrofit.create(PokeApiService.class);
        Call<PokemonApiResposta> pokemonRespostaLista = null;
        for (int j = 0; j < id.size(); j++) {
            pokemonRespostaLista = pokeApiService.getPokemon(id.get(j).getId());
            int finalJ = j;
            pokemonRespostaLista.enqueue(new Callback<PokemonApiResposta>() {
                @Override
                public void onResponse(Call<PokemonApiResposta> call, Response<PokemonApiResposta> response) {
                    if (response.isSuccessful()) {
                        PokemonApiResposta pokemonApiResposta = response.body();
                        ArrayList<Tipos> listaCaracteristicas = pokemonApiResposta.getTypes();
                        Tipos p;
                        for (int i = 0; i < listaCaracteristicas.size(); i++) {
                            p = listaCaracteristicas.get(i);
                            Log.i("TIPO_POKEMON", id.get(finalJ).getId() + p.getType().getName());
                            Log.i("TIPO_POKEMON", p.getSlot() + "");
                            tipoDao.salvaTodos(id.get(finalJ).getId(), p.getType().getName(), p.getSlot());
                        }
                    } else {
                        Log.i("POKEMON", "Erro");
                    }
                }
                @Override
                public void onFailure(Call<PokemonApiResposta> call, Throwable t) {
                    Log.i("POKEMON", t.getMessage());
                }
            });
        }

    }
}


