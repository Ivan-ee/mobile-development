package com.example.domain.model;

public class Pokemon {
    private int id;              // Идентификатор покемона (числовой)
    private String name;         // Имя покемона
    private String description;  // Описание покемона
    private String image;        // URL изображения покемона
    private String createdAt;    // Дата и время создания

    // Конструктор с параметрами
    public Pokemon(int id, String name, String description, String image, String createdAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;      // Присваиваем image, а не imageUrl
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
