package com.example.pokecarguero.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import com.example.pokecarguero.R;
import com.example.pokecarguero.SQLite.PokemonDao;
import com.example.pokecarguero.SQLite.TipoDao;
import com.example.pokecarguero.adapter.CaracteristicasAdapter;
import com.example.pokecarguero.models.Caracteristicas;
import com.example.pokecarguero.models.CaracteristicasPokemon;
import com.example.pokecarguero.models.Movimentos;
import com.example.pokecarguero.models.Pokemon;
import com.example.pokecarguero.models.PokemonApiResposta;
import com.example.pokecarguero.retrofit.PokeApiService;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class CaracteristicasActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private CaracteristicasAdapter caracteristicasAdapter;

    Retrofit retrofit;
    private TipoDao tipoDao;
    private int id = 1;
    private String nome;
    private int status;
    private TextView txtHp;
    private TextView txtTipo1;
    private TextView txtTipo2;
    private TextView txtWeight;
    private TextView txtNomePokemon;
    private TextView txtAtaque;
    private TextView txtDefesa;
    private TextView txtSpecAtaque;
    private TextView txtSpecDef;
    private TextView txtSpeed;
    private ImageView imgPokemon;
    private Button btnLike;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caracteristicas);
        configurarView();
        configuraRecyclerView();
        retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        tipoDao = new TipoDao(this);
        recuperarDados();
        configurarBotaoLikeEDislike();
        configuraTipoPokemon();
        obterCaracteristicas(id);
        clickFavorito(id);
        Glide.with(this).load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + id + ".png")
                .centerCrop()
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgPokemon);

        obterPeso(id);
    }

    private void configuraRecyclerView() {
        recyclerView = findViewById(R.id.recyclerStats);
        caracteristicasAdapter = new CaracteristicasAdapter(this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(caracteristicasAdapter);
    }

    private void configuraTipoPokemon() {

        ArrayList<String> tipos = new ArrayList<>();
        tipos.addAll(tipoDao.retornaTipo(id));

        txtTipo1.setText(tipos.get(0));
        txtTipo1.setBackgroundResource(setarCor(tipos.get(0)));
        if (tipos.size() > 1) {
            txtTipo2.setText(tipos.get(1));
            txtTipo2.setBackgroundResource(setarCor(tipos.get(1)));
        } else {
            txtTipo2.setVisibility(View.GONE);
        }


    }

    private void recuperarDados() {
        Bundle dados = getIntent().getExtras();
        id = dados.getInt("KEY_CARACTERISTICAS");
        nome = dados.getString("KEY_NOME_CARACTERISTICAS");
        status = dados.getInt("KEY_STATUS");
    }


    private void configurarView() {
        txtHp = findViewById(R.id.txtHp);
        txtAtaque = findViewById(R.id.txtAtk);
        txtDefesa = findViewById(R.id.txtDef);
        txtSpecAtaque = findViewById(R.id.txtSpcAtk);
        txtSpecDef = findViewById(R.id.txtSpcDef);
        txtSpeed = findViewById(R.id.txtSpeed);
        imgPokemon = findViewById(R.id.imgPokemonSel);
        txtNomePokemon = findViewById(R.id.txtNome);
        btnLike = findViewById(R.id.btnLike);
        txtTipo1 = findViewById(R.id.txtTipo1);
        txtTipo2 = findViewById(R.id.txtTipo2);
        txtWeight = findViewById(R.id.txtWeight);
    }

    private void obterPeso(int id){

        PokeApiService pokeApiService = retrofit.create(PokeApiService.class);


        Call<PokemonApiResposta> pokemonRespostaLista = pokeApiService.getPokemon(id);


        pokemonRespostaLista.enqueue(new Callback<PokemonApiResposta>() {
            @Override
            public void onResponse(Call<PokemonApiResposta> call, Response<PokemonApiResposta> response) {
                if (response.isSuccessful()) {
                    PokemonApiResposta pokemonApiResposta = response.body();
                    int peso = pokemonApiResposta.getWeight();
                    txtWeight.setText(String.valueOf(peso));
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

    private void obterCaracteristicas(int id) {
        PokeApiService pokeApiService = retrofit.create(PokeApiService.class);


        Call<PokemonApiResposta> pokemonRespostaLista = pokeApiService.getPokemon(id);


        pokemonRespostaLista.enqueue(new Callback<PokemonApiResposta>() {
            @Override
            public void onResponse(Call<PokemonApiResposta> call, Response<PokemonApiResposta> response) {
                if (response.isSuccessful()) {
                    PokemonApiResposta pokemonApiResposta = response.body();
                    // ArrayList<Pokemon> listaPokemon = pokemonApiResposta.get();
                    ArrayList<Caracteristicas> listaCaracteristicas = pokemonApiResposta.getStats();
                    List<CaracteristicasPokemon> caracteristicasPokemons = new ArrayList<>();
                    for (int i = 0; i < listaCaracteristicas.size(); i++) {
                        Caracteristicas p = listaCaracteristicas.get(i);
                        Log.i("POKEMON", p.getBase_stat() + "");
                        Log.i("POKEMON", p.getStat().getName() + "");
                        carregaView(p);
                    }
                    obterMovimentos(id);
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


    private void obterMovimentos(int id) {
        PokeApiService pokeApiService = retrofit.create(PokeApiService.class);


        Call<PokemonApiResposta> pokemonRespostaLista = pokeApiService.getPokemon(id);


        pokemonRespostaLista.enqueue(new Callback<PokemonApiResposta>() {
            @Override
            public void onResponse(Call<PokemonApiResposta> call, Response<PokemonApiResposta> response) {
                if (response.isSuccessful()) {
                    PokemonApiResposta pokemonApiResposta = response.body();
                    ArrayList<CaracteristicasPokemon> caracteristicasPokemons= new ArrayList<>();
                    ArrayList<Movimentos> listaCaracteristicas = pokemonApiResposta.getMoves();
                    for (int i = 0; i < listaCaracteristicas.size(); i++) {
                        Movimentos p = listaCaracteristicas.get(i);
                        for (int j = 0; j < p.getVersion_group_details().size(); j++) {
                            if (p.getVersion_group_details().get(j).getVersionGroup().getName().equals("red-blue") && p.getVersion_group_details().get(j).getLevel_learned_at() != 0 )  {
                                 CaracteristicasPokemon cp = new CaracteristicasPokemon();
                                 cp.setName(p.getMove().getName());
                                 cp.setValor(String.valueOf(p.getVersion_group_details().get(j).getLevel_learned_at()));
                                 caracteristicasPokemons.add(cp);
                            }
                        }
                    }

                    caracteristicasAdapter.addListPokemon(caracteristicasPokemons);
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


    private void salvarFavorito(int id, String nome) {
        PokemonDao pokemonDao = new PokemonDao(getApplicationContext());
        pokemonDao.salva(id, nome, 1);
    }

    private void salvarUnlike(int id, String nome) {
        PokemonDao pokemonDao = new PokemonDao(getApplicationContext());
        pokemonDao.salva(id, nome, 2);
    }


    private void clickFavorito(int id) {
        btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (status == 1) {
                    salvarUnlike(id, nome);
                    finish();
                } else {
                    salvarFavorito(id, nome);
                    finish();
                }
            }
        });
    }


    private void carregaView(Caracteristicas p) {
        switch (p.getStat().getName()) {
            case "hp":
                txtHp.setText(p.getBase_stat() + "");
                break;
            case "attack":
                txtAtaque.setText(p.getBase_stat() + "");
                break;
            case "defense":
                txtDefesa.setText(p.getBase_stat() + "");
                break;
            case "special-attack":
                txtSpecAtaque.setText(p.getBase_stat() + "");
                break;
            case "special-defense":
                txtSpecDef.setText(p.getBase_stat() + "");
                break;
            case "speed":
                txtSpeed.setText(p.getBase_stat() + "");
                break;
        }

        DecimalFormat df = new DecimalFormat("000");

        txtNomePokemon.setText(nome + " #" + df.format(id));
    }

    private void configurarBotaoLikeEDislike() {
        if (status == 2) {
            btnLike.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24);
        } else {
            btnLike.setBackgroundResource(R.drawable.ic_baseline_favorite_24);
        }
    }

    private int setarCor(String tipo) {
        switch (tipo) {
            case "bug":
                return R.color.colorBugType;

            case "dragon":
                return R.color.colorDragonType;

            case "electric":
                return R.color.colorElectricType;

            case "fairy":
                return R.color.colorFairyType;

            case "fighting":
                return R.color.colorFightingType;

            case "fire":
                return R.color.colorFireType;

            case "flying":
                return R.color.colorFlyingType;

            case "ghost":
                return R.color.colorGhostType;

            case "grass":
                return R.color.colorGrassType;

            case "ground":
                return R.color.colorGroundType;

            case "ice":
                return R.color.colorIceType;

            case "normal":
                return R.color.colorNormalType;

            case "poison":
                return R.color.colorPoisonType;

            case "psychic":
                return R.color.colorPsychicType;

            case "rock":
                return R.color.colorRockType;

            case "steel":
                return R.color.colorSteelType;

            case "water":
                return R.color.colorWaterType;
        }
        return 0;
    }


}