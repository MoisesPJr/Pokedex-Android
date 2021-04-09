package com.example.pokecarguero.models;

import java.util.ArrayList;

public class PokemonApiResposta {

    private ArrayList<Pokemon> results;

    private int weight;

    private ArrayList<Caracteristicas> stats;

    private ArrayList<Tipos> types;

    private ArrayList<Movimentos> moves;

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public ArrayList<Movimentos> getMoves() {
        return moves;
    }

    public void setMoves(ArrayList<Movimentos> moves) {
        this.moves = moves;
    }

    public ArrayList<Tipos> getTypes() {
        return types;
    }

    public void setTypes(ArrayList<Tipos> types) {
        this.types = types;
    }

    public ArrayList<Caracteristicas> getStats() {
        return stats;
    }

    public void setStats(ArrayList<Caracteristicas> stats) {
        this.stats = stats;
    }

    public ArrayList<Pokemon> getResults() {
        return results;
    }

    public void setResults(ArrayList<Pokemon> results) {
        this.results = results;
    }
}
