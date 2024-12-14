package com.example.data;

import androidx.lifecycle.LiveData;

import com.example.domain.model.FavoritePokemon;
import com.example.domain.model.Pokemon;
import com.example.domain.repository.FavoritePokemonRepository;
import com.example.data.model.FavoritePokemonEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FavoritePokemonRepositoryImpl implements FavoritePokemonRepository {
    private final FavoritePokemonDao favoritePokemonDao;

    public FavoritePokemonRepositoryImpl(FavoritePokemonDao favoritePokemonDao) {
        this.favoritePokemonDao = favoritePokemonDao;
    }

    @Override
    public void addFavoritePokemon(FavoritePokemon pokemon) {
        FavoritePokemonEntity entity = new FavoritePokemonEntity(
                pokemon.getName(),
                pokemon.getDescription(),
                pokemon.getCreatedAt(),
                pokemon.getImage()
        );
        favoritePokemonDao.insert(entity);
    }

    @Override
    public List<Pokemon> getAllFavoritePokemons() {
        // Получение всех покемонов из избранного
        return favoritePokemonDao.getAllFavoritePokemons();
    }

    @Override
    public void deleteFavoritePokemon(int pokemonId) {
        favoritePokemonDao.deleteFavoritePokemon(pokemonId);
    }

    @Override
    public boolean isFavorite(int pokemonId) {
        FavoritePokemonEntity entity = favoritePokemonDao.getFavoriteById(pokemonId);
        return entity != null;
    }
}
