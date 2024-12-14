package com.example.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.data.model.FavoritePokemonEntity;
import com.example.domain.model.Pokemon;

import java.util.List;

@Dao
public interface FavoritePokemonDao {
    // Вставка нового покемона в базу
    @Insert
    void insert(FavoritePokemonEntity pokemon);

    @Query("SELECT * FROM favorite_pokemons")
    List<Pokemon> getAllFavoritePokemons();

    // Удалить покемона из избранного по его ID
    @Query("DELETE FROM favorite_pokemons WHERE id = :pokemonId")
    void deleteFavoritePokemon(int pokemonId);

    // Получить покемона по его имени
    @Query("SELECT * FROM favorite_pokemons WHERE name = :name LIMIT 1")
    FavoritePokemonEntity getFavoriteByName(String name);

    // Получить покемона по его ID
    @Query("SELECT * FROM favorite_pokemons WHERE id = :id LIMIT 1")
    FavoritePokemonEntity getFavoriteById(int id);
}
