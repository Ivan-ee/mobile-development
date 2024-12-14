package com.example.domain.repository;

import com.example.domain.model.FavoritePokemon;
import com.example.domain.model.Pokemon;

import java.util.List;

public interface FavoritePokemonRepository {
    void addFavoritePokemon(FavoritePokemon pokemon);  // Добавление покемона в избранное
    List<Pokemon> getAllFavoritePokemons();    // Получение всех покемонов из избранного
    void deleteFavoritePokemon(int pokemonId); // Удаление покемона из избранного по ID
    boolean isFavorite(int pokemonId);          // Проверка, является ли покемон избранным
}
