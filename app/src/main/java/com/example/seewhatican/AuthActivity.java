package com.example.seewhatican;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.data.FirebaseAuthRepository;
import com.example.domain.repository.AuthRepository;
import com.example.domain.usecases.LoginUser;
import com.example.domain.usecases.RegisterUser;

public class AuthActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private Button loginButton, registerButton;
    private AuthViewModel authViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        // Инициализация элементов интерфейса
        emailEditText = findViewById(R.id.et_email);
        passwordEditText = findViewById(R.id.et_password);
        loginButton = findViewById(R.id.btn_login);
        registerButton = findViewById(R.id.btn_register);

        // Инициализация репозитория
        AuthRepository authRepository = new FirebaseAuthRepository();  // Поменяйте на ваш репозиторий

        // Инициализация UseCases
        LoginUser loginUser = new LoginUser(authRepository);
        RegisterUser registerUser = new RegisterUser(authRepository);

        // Инициализация ViewModel
        authViewModel = new ViewModelProvider(this, new AuthViewModelFactory(loginUser, registerUser)).get(AuthViewModel.class);

        // Наблюдение за состоянием успешного логина
        authViewModel.getLoginSuccess().observe(this, isSuccess -> {
            if (isSuccess) {
                String email = emailEditText.getText().toString();
                saveUserData(email);  // Сохранение данных пользователя
                Toast.makeText(AuthActivity.this, "Login successful", Toast.LENGTH_SHORT).show();

                // Перенаправление в MainActivity
                startActivity(new Intent(AuthActivity.this, ApiActivity.class));
                finish();  // Закрытие текущей активности
            }
        });

        // Наблюдение за сообщением об ошибке
        authViewModel.getErrorMessage().observe(this, errorMessage -> {
            if (errorMessage != null) {
                Toast.makeText(AuthActivity.this, "Login failed: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });

        // Логика для авторизации
        loginButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            // Проверка на пустые поля
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(AuthActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Выполнение логина через ViewModel
            authViewModel.login(email, password);
        });

        // Логика для перехода на страницу регистрации
        registerButton.setOnClickListener(v -> {
            // Открытие страницы регистрации
            Intent intent = new Intent(AuthActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    private void saveUserData(String email) {
        // Сохраняем информацию о пользователе в SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email", email);  // Сохраняем email
        editor.putBoolean("isLoggedIn", true);  // Устанавливаем флаг, что пользователь авторизован
        editor.apply();
    }
}
