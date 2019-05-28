package com.project.flashcards.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface Flashcard_pointsRepository extends JpaRepository<Flashcard_points, Long> {
    @Query(value = "SELECT COALESCE(SUM(fp.point),0)as  sum FROM Flashcard_points fp, Flashcards f WHERE fp.appuser_id = ?1 AND fp.flashcards_id = f.id AND f.category_id = ?2 AND f.difficulty_id = ?3",nativeQuery = true)
    Integer calculateUserPoints(Long appuser, Long catId, Long diffId);
    @Transactional
    @Modifying
    @Query("UPDATE Flashcard_points flash SET flash.point = 1, flash.discovered = 'yes'  WHERE " +
            "flash.appuser.id = ?1 AND flash.flashcards.id = ?2")
    void updateFlashcardPoints(Long appuser, Long flashcards);
    @Transactional
    @Modifying
    @Query("UPDATE Flashcard_points flash SET flash.discovered = 'yes' WHERE " +
            "flash.appuser.id = ?1 AND flash.flashcards.id = ?2")
    void updateFlashcardPointsField(Long appuser, Long flashcards);
    @Transactional
    @Modifying
    @Query("DELETE FROM Flashcard_points fp WHERE fp.appuser.id = ?1")
    void deleteFlashcardPointsByUser_id(Long id);

    Long countByAppuserAndDiscovered(Long id, String discovered);
    @Query("SELECT count(fp) FROM Flashcard_points fp, fp.category fc WHERE fp.discovered = ?1 AND fp.category.id =fc.id and fc.id =?2")
    Long countByDiscoveredAndCategory(String found, Long catId);

}
