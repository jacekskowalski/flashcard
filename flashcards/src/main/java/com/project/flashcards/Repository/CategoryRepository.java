package com.project.flashcards.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
   @Query("SELECT c FROM Category  c WHERE c.name = ?1")
    Category existsByName(String example);
    Category findByName(String name);
    @Query("SELECT c.id FROM Category c WHERE c.name = ?1")
    Long getCategoryId(String name);
}
