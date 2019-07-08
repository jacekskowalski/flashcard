package com.project.flashcards.Repository;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Statistics implements Serializable {
    private static final long serialVersionUID = -1702167354792282349L;
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private Long id;
@ManyToOne
@JoinColumn(name = "user_id")
private Appuser user_id;
//@JsonProperty(access = JsonProperty.Access.READ_ONLY)
private int points;
@ManyToOne
@JoinColumn(name="category_id")
private Category category;
@ManyToOne
@JoinColumn(name = "difficulty_id")
private Difficulty difficulty;


    public Statistics() {
    }


    public Statistics(Appuser user_id, Category category, Difficulty difficulty) {
        this.user_id = user_id;
        this.category = category;
        this.difficulty = difficulty;
    }

    public Statistics(Appuser user_id, int points, Category category, Difficulty difficulty) {
        this.user_id = user_id;
        this.points = points;
        this.category = category;
        this.difficulty = difficulty;
    }

    public Appuser getUser_id() {
        return user_id;
    }

    public void setUser_id(Appuser user_id) {
        this.user_id = user_id;
    }

    public int getPoints() {
        return points;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Statistics{" +
                "id=" + id +
                ", user_id=" + user_id +
               ", points=" + points +
                ", category=" + category +
                ", difficulty=" + difficulty +
                '}';
    }
}
