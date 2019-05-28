package com.project.flashcards.Repository;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Flashcard_points implements Serializable {

    private static final long serialVersionUID = 5088550529100354823L;
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private  Long id;
    @ManyToOne
    @JoinColumn(name="appuser_id")
     Appuser appuser;
    @ManyToOne
    @JoinColumn(name="flashcards_id")
     Flashcards flashcards;
    private int point;
    private String discovered;

    public Flashcard_points() {
    }

    public Flashcard_points(Appuser appuser, Flashcards flashcards, int point) {
        this.appuser = appuser;
        this.flashcards = flashcards;
        this.point = point;
    }


    public Flashcard_points(Appuser appuser, Flashcards flashcards, int point, String discovered) {
        this.appuser = appuser;
        this.flashcards = flashcards;
        this.point = point;
        this.discovered = discovered;
    }

    public Appuser getAppuser() {
        return appuser;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAppuser(Appuser appuser) {
        this.appuser = appuser;
    }

    public Flashcards getFlashcards() {
        return flashcards;
    }

    public void setFlashcards(Flashcards flashcards) {
        this.flashcards = flashcards;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public String getDiscovered() {
        return discovered;
    }

    public void setDiscovered(String discovered) {
        this.discovered = discovered;
    }

    @Override
    public String toString() {
        return "Flashcard_points{" +
                "id=" + id +
                ", appuser=" + appuser +
                ", flashcards=" + flashcards +
                ", point=" + point +
                '}';
    }
}
