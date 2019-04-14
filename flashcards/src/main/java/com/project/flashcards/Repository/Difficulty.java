package com.project.flashcards.Repository;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.List;

@Entity
public class Difficulty implements Serializable {
    private static final long serialVersionUID = 9098926477896019951L;
    @Id
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long id;
    private String name;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
@OneToMany(mappedBy = "difficulty_id")
private List<Flashcards> flashcards;
    public Difficulty() {
    }

    public Difficulty(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Flashcards> getFlashcards() {
        return flashcards;
    }

    public void setFlashcards(List<Flashcards> flashcards) {
        this.flashcards = flashcards;
    }

    @Override
    public String toString() {
        return "Difficulty{" +
                "name='" + name + '\'' +
                '}';
    }
}
