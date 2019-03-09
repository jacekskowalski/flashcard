package com.project.flashcards.Repository;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
public class Appuser implements Serializable {
    private static final long serialVersionUID = -2056422678204150939L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
     private Long id;
    @Size(min = 5,max = 30,message = "minimum 5 and maximum 30")
     private String name_surname;
    @Size(min= 5)
    @Email
    private String email;
    @Size(min = 5,max=20, message = "minimum 5 and maximum 20")
    private String pswd;

    public Appuser() {
    }

    public Appuser(@Size(min = 5) @Email String email) {
        this.email = email;
    }

    public Appuser(@Email String email, @Size(min = 5, max = 20, message = "minimum 5 and maximum 20") String pswd) {
        this.email = email;
        this.pswd = pswd;
    }

    public Appuser(@Size(min = 5, max = 30, message = "minimum 5 and maximum 30") String name_surname, @Email String email, @Size(min = 6, max = 20, message = "minimum 5 and maximum 20") String pswd) {
        this.name_surname = name_surname;
        this.email = email;
        this.pswd = pswd;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName_surname() {
        return name_surname;
    }

    public void setName_surname(String name_surname) {
        this.name_surname = name_surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPswd() {
        return pswd;
    }

    public void setPswd(String pswd) {
        this.pswd = pswd;
    }
}
