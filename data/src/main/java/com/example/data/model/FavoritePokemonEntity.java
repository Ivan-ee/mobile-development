package com.example.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorite_pokemons") // Имя таблицы "favorite_pokemons"
public class FavoritePokemonEntity {
    @PrimaryKey(autoGenerate = true) // ID будет генерироваться Room автоматически
    private int id;
    private String name;
    private String description;
    private String image;
    private String createdAt;

    // Пустой конструктор, необходим для Room
    public FavoritePokemonEntity() {
    }

    // Конструктор без id (для создания новых записей)
    public FavoritePokemonEntity(String name, String description, String image, String createdAt) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.createdAt = createdAt;
    }

    // Геттеры и сеттеры
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
