package com.project.flashcards.Repository;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Category implements Serializable {
    private static final long serialVersionUID = 3612211937273401253L;
    @Id
    private int id;
    private String name;

    public Category() {
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
