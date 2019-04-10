package com.project.flashcards.Repository;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class FlashcardInitializer {
    private Long id;
    private String categoryName;
    private String difficultyName;

    public FlashcardInitializer() {
    }

    public FlashcardInitializer(Long id, String categoryName, String difficultyName) {
        this.id = id;
        this.categoryName = categoryName;
        this.difficultyName = difficultyName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDifficultyName() {
        return difficultyName;
    }

    public void setDifficultyName(String difficultyName) {
        this.difficultyName = difficultyName;
    }

    @Override
    public String toString() {
        return "FlashcardInitializer{" +
                "id=" + id +
                ", categoryName='" + categoryName + '\'' +
                ", difficultyName='" + difficultyName + '\'' +
                '}';
    }
}
