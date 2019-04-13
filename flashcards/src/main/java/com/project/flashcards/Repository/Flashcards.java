package com.project.flashcards.Repository;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity
public class Flashcards implements Serializable {
    private static final long serialVersionUID = 424213758151231002L;
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;
private String question;
private String answer;
@ManyToOne
@JoinColumn(name="category_id")
private Category category_id;
@ManyToOne
@JoinColumn(name="difficulty_id")
private Difficulty difficulty_id;
@OneToMany(mappedBy = "flashcards")
    List<Flashcard_points> flashcards;
@JsonIgnore
@OneToMany(mappedBy = "flashcards")
List<FavouriteFlashcards> favouriteFlashcards;
     public Flashcards() {
    }

    public String getQuestion() {
        return question;
    }

    public Flashcards(Long id,String question, String answer) {
       this.id = id;
        this.question = question;
        this.answer = answer;
    }

    public Flashcards(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Category getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Category category_id) {
        this.category_id = category_id;
    }

    public Difficulty getDifficulty_id() {
        return difficulty_id;
    }

    public void setDifficulty_id(Difficulty difficulty_id) {
        this.difficulty_id = difficulty_id;
    }

    public List<Flashcard_points> getFlashcards() {
        return flashcards;
    }

    public void setFlashcards(List<Flashcard_points> flashcards) {
        this.flashcards = flashcards;
    }

    public List<FavouriteFlashcards> getFavouriteFlashcards() {
        return favouriteFlashcards;
    }

    public void setFavouriteFlashcards(List<FavouriteFlashcards> favouriteFlashcards) {
        this.favouriteFlashcards = favouriteFlashcards;
    }

    @Override
    public String toString() {
        return "Flashcards{" +
                "id=" + id +
                ", question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                '}';
    }
}
