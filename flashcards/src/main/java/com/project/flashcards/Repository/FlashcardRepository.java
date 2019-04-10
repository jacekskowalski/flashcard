package com.project.flashcards.Repository;

import org.hibernate.annotations.NamedNativeQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.NamedQuery;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface FlashcardRepository extends JpaRepository<Flashcards, Long> {
    @Query(value = "select new Flashcards (f.id ,f.question,f.answer) from Flashcards f where f.category_id.name = ?1 and f.difficulty_id.name = ?2")
    List<Flashcards> findBySelectedOptions(String category, String difficulty);
@Query("SELECT f.category_id.id FROM Flashcards f WHERE f.id = ?1")
Long getCategoryId(Long id);
    @Query("SELECT f.difficulty_id.id FROM Flashcards  f WHERE f.id = ?1")
    Long getDifficultyId(Long id);

}
