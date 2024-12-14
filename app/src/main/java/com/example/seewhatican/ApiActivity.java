package com.example.seewhatican;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class ApiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api);

        // Инициализация кнопки меню
        Button menuButton = findViewById(R.id.btn_menu);

        // Загрузка фрагмента с покемонами при старте активности
        loadPokemonFragment();

        // Обработчик нажатия на кнопку меню
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Загрузка фрагмента меню
                loadMenuFragment();
            }
        });
    }

    // Метод для загрузки фрагмента с покемонами
    private void loadPokemonFragment() {
        PokemonFragment pokemonFragment = new PokemonFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container_view, pokemonFragment); // Заменяем содержимое контейнера
        transaction.commit();
    }

    // Метод для загрузки фрагмента меню
    private void loadMenuFragment() {
        MenuFragment menuFragment = new MenuFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container_view, menuFragment); // Заменяем содержимое контейнера
        transaction.commit();
    }
}
