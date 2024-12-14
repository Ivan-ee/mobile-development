package com.example.seewhatican;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class MenuFragment extends Fragment {

    private MenuViewModel menuViewModel;
    private AppCompatButton apiButton;
    private AppCompatButton roomButton;
    private AppCompatButton profileButton;
    private AppCompatButton aiButton;

    public MenuFragment() {
        // Required empty public constructor
    }

    public static MenuFragment newInstance() {
        return new MenuFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Инициализация ViewModel
        menuViewModel = new ViewModelProvider(requireActivity()).get(MenuViewModel.class);

        // Инициализация кнопок
        apiButton = view.findViewById(R.id.button_api);
        roomButton = view.findViewById(R.id.button_room);
        profileButton = view.findViewById(R.id.button_profile);
        aiButton = view.findViewById(R.id.button_ai);

        // Наблюдение за состоянием кнопки "Работа с ИИ"
        menuViewModel.getIsAddPokemonEnabled().observe(getViewLifecycleOwner(), isEnabled -> {
            aiButton.setEnabled(isEnabled);
        });

        // Логика кнопки "Работа с API"
        apiButton.setOnClickListener(v ->
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container_view, PokemonFragment.newInstance())
                        .addToBackStack(null)
                        .commit()
        );

        // Логика кнопки "Работа с ROOM"
        roomButton.setOnClickListener(v ->
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container_view, FavoritePokemonFragment.newInstance())
                        .addToBackStack(null)
                        .commit()
        );

        // Логика кнопки "Профиль"
        profileButton.setOnClickListener(v ->
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container_view, ProfileFragment.newInstance())
                        .addToBackStack(null)
                        .commit()
        );

        // Логика кнопки "Работа с ИИ"
        aiButton.setOnClickListener(v -> {
            // Пока действие не реализовано
        });
    }
}
