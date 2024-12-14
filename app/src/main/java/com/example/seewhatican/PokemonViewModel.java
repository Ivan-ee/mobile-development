package com.example.seewhatican;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.data.model.FavoritePokemonEntity;
import com.example.domain.model.FavoritePokemon;
import com.example.domain.model.Pokemon;
import com.example.domain.repository.FavoritePokemonRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PokemonViewModel extends ViewModel {
    private final FavoritePokemonRepository favoritePokemonRepository;
    private static final String TAG = "PokemonViewModel";
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    private final MutableLiveData<List<Pokemon>> favoritePokemons = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isFavoriteLiveData = new MutableLiveData<>();
    private final MutableLiveData<Integer> favoriteCount = new MutableLiveData<>();

    public PokemonViewModel(FavoritePokemonRepository favoritePokemonRepository) {
        this.favoritePokemonRepository = favoritePokemonRepository;
    }

    public LiveData<List<Pokemon>> getFavoritePokemons() {
        executorService.execute(() -> {
            List<Pokemon> pokemons = favoritePokemonRepository.getAllFavoritePokemons();
            // Обновляем LiveData с результатом на главном потоке
            new Handler(Looper.getMainLooper()).post(() -> favoritePokemons.setValue(pokemons));
        });
        return favoritePokemons;
    }


    // Добавление покемона в избранное
    public void addFavoritePokemon(FavoritePokemon favoritePokemon) {
        executorService.execute(() -> {
            favoritePokemonRepository.addFavoritePokemon(favoritePokemon);
        });
    }


    // Получаем количество избранных покемонов
    public LiveData<Integer> getFavoriteCount() {
        return favoriteCount;
    }

    // Удаление покемона из избранного
    public void deleteFavoritePokemon(int pokemonId) {
        executorService.execute(() -> {
            favoritePokemonRepository.deleteFavoritePokemon(pokemonId);
        });
    }

    // Проверка, является ли покемон любимым
    public boolean checkIfFavorite(int pokemonId) {
        return favoritePokemonRepository.isFavorite(pokemonId);
    }

    // Проверка, является ли покемон любимым с использованием LiveData
    public LiveData<Boolean> isFavorite(int pokemonId) {
        executorService.execute(() -> {
            boolean isFavorite = favoritePokemonRepository.isFavorite(pokemonId);
            // Обновляем LiveData с результатом на главном потоке
            new Handler(Looper.getMainLooper()).post(() -> isFavoriteLiveData.setValue(isFavorite));
        });
        return isFavoriteLiveData;
    }
}
