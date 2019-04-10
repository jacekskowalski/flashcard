package com.project.flashcards.Repository;

import org.springframework.web.bind.annotation.CrossOrigin;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@CrossOrigin
public class FavouriteFlashcards implements Serializable {
    private static final long serialVersionUID = -4019906377994482463L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "appuser_id")
    Appuser appuser;
    @ManyToOne
    @JoinColumn(name = "flashcards_id")
    Flashcards flashcards;
    public FavouriteFlashcards() {
    }

    public FavouriteFlashcards(Appuser appuser, Flashcards flashcards) {
        this.appuser = appuser;
        this.flashcards = flashcards;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Appuser getAppuser() {
        return appuser;
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
}
