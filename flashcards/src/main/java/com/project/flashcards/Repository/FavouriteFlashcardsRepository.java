package com.project.flashcards.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@CrossOrigin
public interface FavouriteFlashcardsRepository extends JpaRepository<FavouriteFlashcards, Long> {
   @Query("SELECT f.flashcards.id,f.flashcards.question, f.flashcards.answer, (SELECT c.name FROM Category c, f.flashcards WHERE c.id = f.flashcards.category_id), (SELECT d.name FROM Difficulty d, f.flashcards WHERE d.id = f.flashcards.difficulty_id) FROM FavouriteFlashcards f WHERE f.appuser.id = ?1")
   List<Object> getAllFlashcards(Long id);
    @Query(value = "SELECT COALESCE(COUNT(*),0) FROM FavouriteFlashcards f WHERE f.appuser_id = ?1",nativeQuery = true)
    Integer sumAllFlashcards(Long id);
    @Query("SELECT f FROM FavouriteFlashcards f WHERE f.appuser.id =?1 AND f.flashcards.id =?2")
    List<FavouriteFlashcards> existsByAppuserIdAnAndFlashcardsId(Long userId, Long flashcardId);
    @Transactional
    @Modifying
    @Query("DELETE FROM FavouriteFlashcards ff WHERE ff.appuser.id =?1 AND ff.flashcards.id =?2")
   void deleteFlashcard(Long userId, Long flashcardId);
    @Query("DELETE FROM FavouriteFlashcards ff WHERE ff.appuser.id =?1")
    void deleteByUser_id(Long id);
}