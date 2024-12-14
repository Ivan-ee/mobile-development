package com.example.seewhatican;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.data.AppDatabase;
import com.example.data.FavoritePokemonDao;
import com.example.data.FavoritePokemonRepositoryImpl;
import com.example.data.PokemonRepositoryImpl;
import com.example.data.model.FavoritePokemonEntity;
import com.example.domain.model.Pokemon;
import com.example.domain.repository.FavoritePokemonRepository;
import com.example.domain.repository.PokemonRepository;

import java.util.ArrayList;
import java.util.List;

public class PokemonFragment extends Fragment {
    private RecyclerView recyclerView;
    private PokemonAdapter pokemonAdapter;
    private PokemonRepository pokemonRepository;
    private FavoritePokemonRepository favoritePokemonRepository;
    private PokemonViewModel pokemonViewModel;

    public static PokemonFragment newInstance() {
        return new PokemonFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pokemon, container, false);

        recyclerView = view.findViewById(R.id.pokemonRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        AppDatabase appDatabase = Room.databaseBuilder(getContext(), AppDatabase.class, "pokemon-db").build();
        FavoritePokemonDao favoritePokemonDao = appDatabase.favoritePokemonDao();

        // Инициализация репозиториев
        pokemonRepository = new PokemonRepositoryImpl();
        favoritePokemonRepository = new FavoritePokemonRepositoryImpl(favoritePokemonDao);  // Инициализация репозитория избранных покемонов

        // Инициализация ViewModel
        pokemonViewModel = new ViewModelProvider(this, new PokemonViewModelFactory(favoritePokemonRepository)).get(PokemonViewModel.class);

        // Инициализация адаптера
        pokemonAdapter = new PokemonAdapter(new ArrayList<>(), pokemonViewModel);  // Инициализация с пустым списком
        recyclerView.setAdapter(pokemonAdapter);

        // Загружаем покемонов
        loadPokemons();

        return view;
    }

    private void loadPokemons() {
        pokemonRepository.getPokemonsAsync(new PokemonRepository.PokemonCallback() {
            @Override
            public void onSuccess(List<Pokemon> pokemons) {
                if (pokemons != null) {
                    // Логируем количество полученных покемонов
                    Log.d("PokemonFragment", "Loaded " + pokemons.size() + " pokemons");

                    // Обновляем UI с новым списком
                    requireActivity().runOnUiThread(() -> pokemonAdapter.updatePokemons(pokemons));
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("PokemonFragment", "Failed to load pokemons: " + t.getMessage());
            }
        });
    }

}
