package com.project.flashcards.Repository;


public class UserToken {
    private String token;
    private Long id;
    private String email;
    private String password;

    public UserToken() {
    }

    public UserToken(String token, Long id, String email, String password) {
        this.token = token;
        this.id = id;
        this.email = email;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
