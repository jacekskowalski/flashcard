package com.project.flashcards.Repository;

import org.springframework.stereotype.Component;

@Component
public class StatisticsHelperComponent {
    private Long apuserId;
    private Long flashcardsId;
    private String discovered;

    public StatisticsHelperComponent() {
    }

    public StatisticsHelperComponent(Long apuserId) {
        this.apuserId = apuserId;
    }

    public StatisticsHelperComponent(Long apuserId, Long flashcardsId) {
        this.apuserId = apuserId;
        this.flashcardsId = flashcardsId;
    }

    public StatisticsHelperComponent(Long apuserId, Long flashcardsId, String discovered) {
        this.apuserId = apuserId;
        this.flashcardsId = flashcardsId;
        this.discovered = discovered;
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

    public String getDiscovered() {
        return discovered;
    }

    public void setDiscovered(String discovered) {
        this.discovered = discovered;
    }
}
