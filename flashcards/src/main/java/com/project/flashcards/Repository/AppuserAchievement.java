package com.project.flashcards.Repository;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="appuserachievement")
public class AppuserAchievement implements Serializable {
    private static final long serialVersionUID = -149608656965953635L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Appuser user;
    @ManyToOne
    @JoinColumn(name = "achievement_id")
    private Achievement achievement;

    public AppuserAchievement() {
    }

    public AppuserAchievement(Appuser user, Achievement achievement) {
        this.user = user;
        this.achievement = achievement;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Appuser getUser() {
        return user;
    }

    public void setUser(Appuser user) {
        this.user = user;
    }

    public Achievement getAchievement() {
        return achievement;
    }

    public void setAchievement(Achievement achievement) {
        this.achievement = achievement;
    }
}
