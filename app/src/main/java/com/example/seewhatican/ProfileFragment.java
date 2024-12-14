package com.example.seewhatican;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.data.AppDatabase;
import com.example.data.FavoritePokemonDao;
import com.example.data.FavoritePokemonRepositoryImpl;
import com.example.data.UserRepository;
import com.example.domain.repository.UserRepositoryInterface;
import com.example.domain.repository.FavoritePokemonRepository;

import java.text.BreakIterator;

public class ProfileFragment extends Fragment {

    private TextView emailTextView;
    private ImageButton menuButton;
    private ProfileViewModel profileViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true); // Сохранение состояния при повороте экрана
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Инфлейтинг разметки для этого фрагмента
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Инициализация репозитория пользователя
        UserRepositoryInterface userRepository = new UserRepository(getContext());

        // Получаем экземпляр базы данных через синглтон
        AppDatabase db = AppDatabase.getInstance(getContext());

        // Получаем DAO
        FavoritePokemonDao favoritePokemonDao = db.favoritePokemonDao();

        // Инициализация репозитория с DAO
        FavoritePokemonRepository favoritePokemonRepository = new FavoritePokemonRepositoryImpl(favoritePokemonDao);

        // Инициализация ViewModel с обоими репозиториями
        profileViewModel = new ViewModelProvider(this, new ProfileViewModelFactory(userRepository, favoritePokemonRepository))
                .get(ProfileViewModel.class);

        // Отображение email
        emailTextView = view.findViewById(R.id.email_text);

        // Наблюдение за изменениями email
        profileViewModel.getUserEmail().observe(getViewLifecycleOwner(), email -> emailTextView.setText(email));

        // Отображение количества избранных покемонов
        TextView favoriteCountTextView = view.findViewById(R.id.favorite_count_text);
        profileViewModel.getFavoriteCount().observe(getViewLifecycleOwner(), count ->
                favoriteCountTextView.setText(String.valueOf(count)));
    }

    // Метод для создания нового экземпляра фрагмента
    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }
}

