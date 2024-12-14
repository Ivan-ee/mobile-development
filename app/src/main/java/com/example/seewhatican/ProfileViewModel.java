package com.example.seewhatican;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.domain.model.FavoritePokemon;
import com.example.domain.model.Pokemon;
import com.example.domain.repository.FavoritePokemonRepository;
import com.example.domain.repository.UserRepositoryInterface;

import java.util.List;

public class ProfileViewModel extends ViewModel {

    private final MutableLiveData<String> userEmail = new MutableLiveData<>();
    private final MutableLiveData<Integer> favoriteCount = new MutableLiveData<>();

    private final UserRepositoryInterface userRepository;
    private final FavoritePokemonRepository favoritePokemonRepository;

    public ProfileViewModel(UserRepositoryInterface userRepository, FavoritePokemonRepository favoritePokemonRepository) {
        this.userRepository = userRepository;
        this.favoritePokemonRepository = favoritePokemonRepository;

        // Инициализация данных
        loadUserData();
        loadFavoritePokemonCount();
    }

    // Метод для загрузки данных пользователя
    private void loadUserData() {
        // Пример получения email пользователя
        String email = userRepository.getEmail(); // Замените на вашу реализацию
        userEmail.setValue(email);
    }

    // Метод для загрузки количества избранных покемонов
    private void loadFavoritePokemonCount() {
        new Thread(() -> {
            List<Pokemon> favoritePokemons = favoritePokemonRepository.getAllFavoritePokemons();
            favoriteCount.postValue(favoritePokemons.size());
        }).start();
    }

    // Геттеры
    public LiveData<String> getUserEmail() {
        return userEmail;
    }

    public LiveData<Integer> getFavoriteCount() {
        return favoriteCount;
    }
}
