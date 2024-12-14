package com.example.seewhatican;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.domain.repository.FavoritePokemonRepository;
import com.example.domain.repository.UserRepositoryInterface;

public class ProfileViewModelFactory implements ViewModelProvider.Factory {

    private final UserRepositoryInterface userRepository;
    private final FavoritePokemonRepository favoritePokemonRepository;

    public ProfileViewModelFactory(UserRepositoryInterface userRepository, FavoritePokemonRepository favoritePokemonRepository) {
        this.userRepository = userRepository;
        this.favoritePokemonRepository = favoritePokemonRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ProfileViewModel(userRepository, favoritePokemonRepository);
    }
}
