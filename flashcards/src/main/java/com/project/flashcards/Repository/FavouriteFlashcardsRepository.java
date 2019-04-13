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
   @Query("SELECT f.flashcards FROM FavouriteFlashcards f WHERE f.appuser.id = ?1")
   List<FavouriteFlashcards> getAllFlashcards(Long id);
    @Query("SELECT f.flashcards FROM FavouriteFlashcards f WHERE f.appuser.id =?1 AND f.flashcards.id =?2")
    List<FavouriteFlashcards> existsByAppuserIdAnAndFlashcardsId(Long userId, Long flashcardId);
    @Transactional
    @Modifying
    @Query("DELETE FROM FavouriteFlashcards ff WHERE ff.appuser.id =?1 AND ff.flashcards.id =?2")
   void deleteFlashcard(Long userId, Long flashcardId);
}