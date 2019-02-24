package com.project.flashcards.Repository;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Difficulty implements Serializable {
    private static final long serialVersionUID = 9098926477896019951L;
    @Id
    private int id;
    private String name;

    public Difficulty() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
