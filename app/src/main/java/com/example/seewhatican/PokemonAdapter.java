package com.example.seewhatican;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.example.data.model.FavoritePokemonEntity;
import com.example.domain.model.Pokemon;
import com.example.domain.model.FavoritePokemon;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder> {
    private List<Pokemon> pokemons;
    private PokemonViewModel pokemonViewModel;
    private static final String TAG = "PokemonAdapter";

    public PokemonAdapter(List<Pokemon> pokemons, PokemonViewModel pokemonViewModel) {
        this.pokemons = pokemons;
        this.pokemonViewModel = pokemonViewModel;
    }

    @NonNull
    @Override
    public PokemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pokemon, parent, false);
        return new PokemonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonViewHolder holder, int position) {
        Pokemon pokemon = pokemons.get(position);
        holder.pokemonName.setText(pokemon.getName());
        holder.pokemonDescription.setText(pokemon.getDescription());
        Picasso.get().load(pokemon.getImage()).into(holder.pokemonImage);

        holder.favoriteButton.setOnClickListener(v -> {
            Log.d(TAG, "Favorite button clicked for pokemon: " + pokemon.getName());

            // Преобразуем Pokemon в FavoritePokemonEntity
            FavoritePokemonEntity favoritePokemonEntity = new FavoritePokemonEntity(
                    pokemon.getName(),
                    pokemon.getDescription(),
                    pokemon.getImage(),
                    pokemon.getCreatedAt()
            );

            // Наблюдаем за состоянием избранного
            pokemonViewModel.isFavorite(pokemon.getId()).observe((LifecycleOwner) holder.itemView.getContext(), isFavorite -> {
                if (isFavorite) {
                    Log.d(TAG, "Removing pokemon from favorites: " + pokemon.getName());
                    pokemonViewModel.deleteFavoritePokemon(pokemon.getId()); // Используем ID из Pokemon
                } else {
                    Log.d(TAG, "Adding pokemon to favorites: " + pokemon.getName());

                    // Преобразуем FavoritePokemonEntity в FavoritePokemon
                    FavoritePokemon favoritePokemon = new FavoritePokemon(
                            favoritePokemonEntity.getId(),
                            favoritePokemonEntity.getName(),
                            favoritePokemonEntity.getDescription(),
                            favoritePokemonEntity.getCreatedAt(),
                            favoritePokemonEntity.getImage()
                    );

                    // Добавляем в избранное
                    pokemonViewModel.addFavoritePokemon(favoritePokemon);
                }
            });
        });
    }

    @Override
    public int getItemCount() {
        return pokemons != null ? pokemons.size() : 0;
    }

    public void setPokemons(List<Pokemon> pokemons) {
        this.pokemons = pokemons;
        notifyDataSetChanged();
    }

    // Обновляем данные покемонов, передавая список Pokemon
    public void updatePokemons(List<Pokemon> newPokemons) {
        this.pokemons = newPokemons;
        notifyDataSetChanged();
    }

    public static class PokemonViewHolder extends RecyclerView.ViewHolder {
        ImageView pokemonImage;
        TextView pokemonName;
        TextView pokemonDescription;
        ImageButton favoriteButton;

        public PokemonViewHolder(@NonNull View itemView) {
            super(itemView);
            pokemonImage = itemView.findViewById(R.id.pokemon_image);
            pokemonName = itemView.findViewById(R.id.pokemon_name);
            pokemonDescription = itemView.findViewById(R.id.pokemon_description);
            favoriteButton = itemView.findViewById(R.id.add_pokemon_button);
        }
    }
}
