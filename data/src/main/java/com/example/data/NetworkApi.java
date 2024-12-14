package com.example.data;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import com.example.domain.model.Pokemon;
import java.util.List;

public class NetworkApi {
    private static final String BASE_URL = "https://675423bc36bcd1eec85051f6.mockapi.io/api/";

    private static Retrofit retrofit;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public interface PokemonService {
        @GET("pokemons/pokemon")
        Call<List<Pokemon>> getPokemons();
    }
}
