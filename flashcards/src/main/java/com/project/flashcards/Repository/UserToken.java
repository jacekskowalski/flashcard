package com.project.flashcards.Repository;


public class UserToken {
    private String token;
    private Long id;
    private String email;
    private String name_surname;

    public UserToken() {
    }

    public UserToken(String token, Long id, String name_surname, String email) {
        this.token = token;
        this.id = id;
        this.name_surname=name_surname;
        this.email = email;
        }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName_surname() {
        return name_surname;
    }

    public void setName_surname(String name_surname) {
        this.name_surname = name_surname;
    }
}
