package com.project.flashcards.Repository;

import org.springframework.stereotype.Component;

@Component
public class ResultHelperComponent {

   private Long apuserId;
   private   double time;
   private String categoryName;
   private String diificultyName;
   private Long flashcardId;

    public ResultHelperComponent() {
    }

    public ResultHelperComponent(double time, String categoryName, String diificultyName, Long flashcardId) {
        this.time = time;
        this.categoryName = categoryName;
        this.diificultyName = diificultyName;
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

    public String getDiificultyName() {
        return diificultyName;
    }

    public void setDiificultyName(String diificultyName) {
        this.diificultyName = diificultyName;
    }
}
