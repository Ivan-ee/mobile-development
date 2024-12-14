package com.example.seewhatican;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.data.AppDatabase;
import com.example.data.FavoritePokemonDao;
import com.example.data.FavoritePokemonRepositoryImpl;
import com.example.data.model.FavoritePokemonEntity;
import com.example.domain.model.Pokemon;
import com.example.domain.repository.FavoritePokemonRepository;

import java.util.ArrayList;
import java.util.List;

public class FavoritePokemonFragment extends Fragment {
    private RecyclerView favoritePokemonRecyclerView;
    private PokemonAdapter pokemonAdapter;
    private FavoritePokemonDao favoritePokemonDao;
    private FavoritePokemonRepository favoritePokemonRepository;
    private PokemonViewModel pokemonViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Создаем разметку для фрагмента
        View view = inflater.inflate(R.layout.fragment_favorite_pokemon, container, false);

        // Инициализация DAO
        favoritePokemonDao = AppDatabase.getInstance(requireContext()).favoritePokemonDao();

        // Инициализация репозитория
        favoritePokemonRepository = new FavoritePokemonRepositoryImpl(favoritePokemonDao);

        // Инициализация ViewModel
        pokemonViewModel = new ViewModelProvider(this, new PokemonViewModelFactory(favoritePokemonRepository)).get(PokemonViewModel.class);

        // Инициализация RecyclerView
        favoritePokemonRecyclerView = view.findViewById(R.id.favoritePokemonRecyclerView);
        favoritePokemonRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Инициализация адаптера с пустым списком Pokemon
        pokemonAdapter = new PokemonAdapter(new ArrayList<>(), pokemonViewModel);
        favoritePokemonRecyclerView.setAdapter(pokemonAdapter);

        // Загрузка избранных покемонов через репозиторий
        loadFavoritePokemons();

        // Подписка на LiveData для обновления данных
        pokemonViewModel.getFavoritePokemons().observe(getViewLifecycleOwner(), pokemons -> {
            if (pokemons != null && !pokemons.isEmpty()) {
                // Обновляем данные в адаптере
                pokemonAdapter.updatePokemons(pokemons);
            } else {
                Log.d("FavoritePokemonFragment", "No favorite pokemons found");
            }
        });

        return view;
    }

    private void loadFavoritePokemons() {
        pokemonViewModel.getFavoritePokemons().observe(getViewLifecycleOwner(), pokemons -> {
            if (pokemons != null && !pokemons.isEmpty()) {
                // Обновляем данные в адаптере
                pokemonAdapter.updatePokemons(pokemons);
            } else {
                Log.d("FavoritePokemonFragment", "No favorite pokemons found");
            }
        });
    }

    public static FavoritePokemonFragment newInstance() {
        return new FavoritePokemonFragment();
    }
}
