
package com.example.pokecarguero.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.pokecarguero.R;
import com.example.pokecarguero.models.Pokemon;
import com.example.pokecarguero.models.Tipos;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.MyViewHolder> {

    private List<Pokemon> listaNomesPokemons;
    private List<Tipos> listaTipos;
    private Context context;
    public PokemonAdapter(Context context) {
        this.context = context;
        this.listaNomesPokemons = new ArrayList<>();
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemPokemon = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_pokemon,parent,false);
        return new MyViewHolder(itemPokemon);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DecimalFormat df = new DecimalFormat("000");
        Pokemon p = listaNomesPokemons.get(position);




        holder.nome.setText(p.getName()+ " #"+df.format(p.getId() ));

        Glide.with(context).load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"+p.getId()+".png")
                .centerCrop()
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imagemPokemon);

    }





    public Pokemon retornaPokemon(int posicao){
        return listaNomesPokemons.get(posicao);
    }

    @Override
    public int getItemCount() {
        return listaNomesPokemons.size();
    }

    public void addListPokemon(List<Pokemon> listPokemon){
        listaNomesPokemons.addAll(listPokemon);
        notifyDataSetChanged();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView nome;
        ImageView imagemPokemon;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nome = itemView.findViewById(R.id.txtNomePokemon);
            imagemPokemon = itemView.findViewById(R.id.imagemPokemon);

        }
    }

}
