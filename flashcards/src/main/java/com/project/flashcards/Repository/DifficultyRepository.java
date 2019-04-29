package com.project.flashcards.Repository;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DifficultyRepository extends JpaRepository<Difficulty, Long> {
@Query("SELECT d FROM Difficulty d WHERE d.name =?1")
 Difficulty existsByName(String example);
  Difficulty findByName(String name);
}
