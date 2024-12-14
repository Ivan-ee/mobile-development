package com.example.domain.repository;

import com.example.domain.model.Pokemon;

import java.util.List;

public interface PokemonRepository {
    void getPokemonsAsync(PokemonCallback callback);

    interface PokemonCallback {
        void onSuccess(List<Pokemon> pokemons);
        void onFailure(Throwable t);
    }
}

