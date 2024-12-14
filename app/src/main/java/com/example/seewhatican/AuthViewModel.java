package com.example.seewhatican;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.domain.repository.AuthRepository;
import com.example.domain.usecases.LoginUser;
import com.example.domain.usecases.RegisterUser;

public class AuthViewModel extends ViewModel {

    private final LoginUser loginUser;
    private final RegisterUser registerUser;

    private final MutableLiveData<Boolean> loginSuccess = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public AuthViewModel(LoginUser loginUser, RegisterUser registerUser) {
        this.loginUser = loginUser;
        this.registerUser = registerUser;
    }

    public LiveData<Boolean> getLoginSuccess() {
        return loginSuccess;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void login(String email, String password) {
        loginUser.execute(email, password, new AuthRepository.AuthCallback() {
            @Override
            public void onSuccess() {
                loginSuccess.postValue(true);
            }

            @Override
            public void onFailure(String error) {
                errorMessage.postValue("Login failed: " + error); // Обработка ошибки
            }
        });
    }

    public void register(String email, String password) {
        registerUser.execute(email, password, new AuthRepository.AuthCallback() {
            @Override
            public void onSuccess() {
                // Handle registration success if needed
            }

            @Override
            public void onFailure(String error) {
                errorMessage.postValue("Register failed: " + error);
            }
        });
    }
}
