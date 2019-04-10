package com.project.flashcards.Repository;

import org.springframework.stereotype.Component;

@Component
public class StatisticsHelperComponent {
    private Long apuserId;
    private Long flashcardsId;

    public StatisticsHelperComponent() {
    }

    public StatisticsHelperComponent(Long apuserId) {
        this.apuserId = apuserId;
    }

    public StatisticsHelperComponent(Long apuserId, Long flashcardsId) {
        this.apuserId = apuserId;
        this.flashcardsId = flashcardsId;
    }
    public Long getApuserId() {
        return apuserId;
    }

    public void setApuserId(Long apuserId) {
        this.apuserId = apuserId;
    }

    public Long getFlashcardsId() {
        return flashcardsId;
    }

    public void setFlashcardsId(Long flashcardsId) {
        this.flashcardsId = flashcardsId;
    }


}
