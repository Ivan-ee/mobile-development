package com.example.seewhatican;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MenuViewModel extends ViewModel {
    private final MutableLiveData<Boolean> isAddPokemonEnabled = new MutableLiveData<>(false);

    public LiveData<Boolean> getIsAddPokemonEnabled() {
        return isAddPokemonEnabled;
    }

}
