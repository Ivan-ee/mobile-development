package com.example.seewhatican;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.domain.repository.FavoritePokemonRepository;

public class PokemonViewModelFactory implements ViewModelProvider.Factory {
    private final FavoritePokemonRepository favoritePokemonRepository;

    public PokemonViewModelFactory(FavoritePokemonRepository favoritePokemonRepository) {
        this.favoritePokemonRepository = favoritePokemonRepository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(PokemonViewModel.class)) {
            return (T) new PokemonViewModel(favoritePokemonRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
