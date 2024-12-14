package com.example.data;

import android.util.Log;
import com.example.domain.model.Pokemon;
import com.example.domain.repository.PokemonRepository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class PokemonRepositoryImpl implements PokemonRepository {

    private static final String TAG = "PokemonRepositoryImpl"; // Тег для логирования

    @Override
    public void getPokemonsAsync(PokemonCallback callback) {
        // Асинхронный вызов через Retrofit
        NetworkApi.PokemonService service = NetworkApi.getClient().create(NetworkApi.PokemonService.class);
        Call<List<Pokemon>> call = service.getPokemons(); // Мы ожидаем список покемонов, не PokemonResponse

        Log.d(TAG, "Sending request to get Pokemons"); // Логируем начало запроса

        call.enqueue(new Callback<List<Pokemon>>() {
            @Override
            public void onResponse(Call<List<Pokemon>> call, Response<List<Pokemon>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Данные успешно получены, передаем их в callback
                    Log.d(TAG, "Pokemons successfully retrieved, count: " + response.body().size()); // Логируем успешный ответ
                    callback.onSuccess(response.body());
                } else {
                    // Ответ не успешен
                    Log.e(TAG, "Response unsuccessful, code: " + response.code()); // Логируем код ошибки
                    callback.onFailure(new Exception("Response unsuccessful"));
                }
            }

            @Override
            public void onFailure(Call<List<Pokemon>> call, Throwable t) {
                // Ошибка при вызове API
                Log.e(TAG, "API call failed", t); // Логируем ошибку
                callback.onFailure(t);
            }
        });
    }
}
