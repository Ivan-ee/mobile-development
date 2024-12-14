package com.example.seewhatican;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); // Включение работы с Edge-to-Edge отображением
        setContentView(R.layout.activity_main);

        // Обеспечение корректного отображения элементов с учетом системных отступов
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.rootLayout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findViewById(R.id.btn_login).setOnClickListener(view -> {
            navigateToLogin();
        });

        findViewById(R.id.btn_limited_access).setOnClickListener(view -> {
            navigateToHome();
        });
    }

    private void navigateToLogin() {
        startActivity(new Intent(this, AuthActivity.class));
    }

    private void navigateToHome() {
        startActivity(new Intent(this, ApiActivity.class));
    }

}
