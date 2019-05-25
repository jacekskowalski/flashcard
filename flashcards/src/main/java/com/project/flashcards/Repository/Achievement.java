package com.project.flashcards.Repository;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@CrossOrigin
public class Achievement implements Serializable {
    private static final long serialVersionUID = 1700023747685784741L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @JsonIgnore
    @OneToMany(mappedBy = "achievement")
    private List<AppuserAchievement> appuserAchievements;
    public Achievement() {
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

    public List<AppuserAchievement> getAppuserAchievements() {
        return appuserAchievements;
    }

    public void setAppuserAchievements(List<AppuserAchievement> appuserAchievements) {
        this.appuserAchievements = appuserAchievements;
    }
}
