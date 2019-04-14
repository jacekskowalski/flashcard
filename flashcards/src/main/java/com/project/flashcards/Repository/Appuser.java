package com.project.flashcards.Repository;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

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
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Size(min = 5, message = "minimum 5")
    private String pswd;
    @JsonIgnore
@OneToMany(mappedBy = "user_id")
private  List<Statistics> statisticsList;
    @JsonIgnore
 @OneToMany(mappedBy = "appuser")
private Set<Flashcard_points> flashcardPoints;
    @JsonIgnore
    @OneToMany(mappedBy = "appuser")
    private List<FavouriteFlashcards> favouriteFlashcards;
    public Appuser() {
    }

    public Appuser(Long id){
        this.id = id;
    }

    public Appuser(@Size(min = 5) @Email String email) {
        this.email = email;
    }

    public Appuser(@Email String email, @Size(min = 5, message = "minimum 5 and maximum 20") String pswd) {
        this.email = email;
        this.pswd = pswd;
    }

    public Appuser(Long id, @Size(min = 5, max = 30, message = "minimum 5 and maximum 30") String name_surname, @Email String email) {
       this.id = id;
        this.name_surname = name_surname;
        this.email = email;

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

    public List<Statistics> getStatisticsList() {
        return statisticsList;
    }

    public void setStatisticsList(List<Statistics> statisticsList) {
        this.statisticsList = statisticsList;
    }

    public Set<Flashcard_points> getFlashcardPoints() {
        return flashcardPoints;
    }

    public void setFlashcardPoints(Set<Flashcard_points> flashcardPoints) {
        this.flashcardPoints = flashcardPoints;
    }

    public List<FavouriteFlashcards> getFavouriteFlashcards() {
        return favouriteFlashcards;
    }

    public void setFavouriteFlashcards(List<FavouriteFlashcards> favouriteFlashcards) {
        this.favouriteFlashcards = favouriteFlashcards;
    }

    @Override
    public String toString() {
        return "Appuser{" +
                "name_surname='" + name_surname + '\'' +
                '}';
    }
}
