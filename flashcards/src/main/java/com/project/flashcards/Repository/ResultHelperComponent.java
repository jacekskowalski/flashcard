package com.project.flashcards.Repository;

import org.springframework.stereotype.Component;

@Component
public class ResultHelperComponent {

   private Long apuserId;
   private double time;
   private String categoryName;
   private String difficultyName;
   private Long flashcardId;

    public ResultHelperComponent() {
    }

    public ResultHelperComponent(Long apuserId, double time, String categoryName, String difficultyName, Long flashcardId) {
        this.apuserId = apuserId;
        this.time = time;
        this.categoryName = categoryName;
        this.difficultyName = difficultyName;
        this.flashcardId = flashcardId;
    }

    public Long getApuserId() {
        return apuserId;
    }

    public void setApuserId(Long apuserId) {
        this.apuserId = apuserId;
    }

    public Long getFlashcardId() {
        return flashcardId;
    }

    public void setFlashcardId(Long flashcardId) {
        this.flashcardId = flashcardId;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
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
}
