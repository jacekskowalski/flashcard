package com.project.flashcards.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@Repository
@CrossOrigin
public interface FavouriteFlashcardsRepository extends JpaRepository<FavouriteFlashcards, Long> {
   @Query("SELECT f FROM FavouriteFlashcards f WHERE f.appuser.id = ?1")
   List<FavouriteFlashcards> getAllFlashcards(Long id);
    @Query("DELETE FROM Favouriteflashcards ff WHERE ff.user_id =?1 AND ff.flashcards_id =?2")
   void deleteFlashcard(Long userId, Long flashcardId);
}