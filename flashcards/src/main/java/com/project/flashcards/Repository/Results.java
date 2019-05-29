package com.project.flashcards.Repository;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Entity
public class Results implements Serializable {
    private static final long serialVersionUID = 7991987111632124242L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
  private   Long id;
    @ManyToOne
    @JoinColumn(name = "apuserId")
  private   Appuser appuser;
   private double totaltime;
     @ManyToOne
    @JoinColumn(name = "category_id")
  private   Category category;
    @ManyToOne
    @JoinColumn(name = "difficulty_id")
  private   Difficulty difficulty;
@ManyToOne
@JoinColumn(name = "flashcard_id")
Flashcards flashcards;
    public Results() {
    }

    public Results(Appuser appuser, double totaltime, Category category, Difficulty difficulty, Flashcards flashcards) {
        this.appuser = appuser;
        this.totaltime = totaltime;
        this.category = category;
        this.difficulty = difficulty;
        this.flashcards = flashcards;
    }

    public double getTotaltime() {
        return totaltime;
    }

    public void setTotaltime(double totaltime) {
        this.totaltime = totaltime;
    }

    public Appuser getAppuser() {
        return appuser;
    }

    public void setAppuser(Appuser appuser) {
        this.appuser = appuser;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Flashcards getFlashcards() {
        return flashcards;
    }

    public void setFlashcards(Flashcards flashcards) {
        this.flashcards = flashcards;
    }
}
