package com.example.pokecarguero.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokecarguero.R;
import com.example.pokecarguero.models.Caracteristicas;
import com.example.pokecarguero.models.CaracteristicasPokemon;
import com.example.pokecarguero.models.Pokemon;

import java.util.ArrayList;
import java.util.List;

public class CaracteristicasAdapter extends RecyclerView.Adapter<CaracteristicasAdapter.MyViewHolder> {

    private List<CaracteristicasPokemon> listaStats = new ArrayList<>();
    private Context context;

    public  CaracteristicasAdapter(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemPokemon = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_caracteristicas,parent,false);
        return new CaracteristicasAdapter.MyViewHolder(itemPokemon);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            holder.txtOpc.setText(listaStats.get(position).getName());
            holder.txtResult.setText(String.valueOf(listaStats.get(position).getValor()));
    }

    public void addListPokemon(List<CaracteristicasPokemon> listPokemon){
        CaracteristicasPokemon cp = new CaracteristicasPokemon();
        cp.setName("Move");
        cp.setValor("Level");
        listaStats.add(cp);
        listaStats.addAll(listPokemon);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listaStats.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView txtOpc;
        TextView txtResult;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtOpc = itemView.findViewById(R.id.txtOpc);
            txtResult = itemView.findViewById(R.id.txtResult);

        }
    }
}
