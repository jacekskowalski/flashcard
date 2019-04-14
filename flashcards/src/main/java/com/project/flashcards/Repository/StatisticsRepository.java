package com.project.flashcards.Repository;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface StatisticsRepository extends JpaRepository<Statistics, Long> {
    @Query(value="SELECT a.name_surname, c.name as category,d.name as difficulty,(SELECT st.points FROM Statistics st JOIN Category ca ON st.category_id = ca.id JOIN Difficulty di ON st.difficulty_id = di.id AND st.user_id = ?1),(SELECT  count(*) FROM Flashcards WHERE Flashcards.category_id = c.id AND Flashcards.difficulty_id = d.id) FROM Statistics s,Appuser a, Category c, Difficulty d where s.user_id = a.id and s.category_id = c.id and s.difficulty_id = d.id AND s.user_id = ?1",nativeQuery = true)
    List<Object> findAllByUser(Long id);

    @Transactional
    @Modifying
    @Query("DELETE FROM Statistics s WHERE s.user_id.id = ?1")
    void deleteStatisticsByUser_id(Long id);

    @Transactional
    @Modifying
    @Query("DELETE FROM Flashcard_points fp WHERE fp.appuser.id = ?1")
    void deleteFlashcardPointsByUser_id(Long id);

    @Transactional
    @Modifying
    @Query("UPDATE Flashcard_points flash SET flash.point = 1 WHERE " +
            "flash.appuser.id = ?1 AND flash.flashcards.id = ?2")
    void updateFlashcardPoints(Long appuser, Long flashcards);

 @Query ("SELECT SUM(fp.point) FROM Flashcard_points fp WHERE fp.appuser.id = ?1 AND (SELECT f.category_id.id FROM Flashcards f WHERE f.id = ?2) = ?3 AND (SELECT f.difficulty_id.id FROM Flashcards f WHERE f.id = ?2) = ?4")
Integer calculateUserPoints(Long appuser, Long flashcardId, Long catId, Long diffId);

    @Transactional
    @Modifying
    @Query("UPDATE Statistics s SET s.points = :points WHERE s.user_id.id = :id AND s.category.id = :catId AND s.difficulty.id = :diffId")
            void updateStatistics(@Param("points")int points, @Param("id")Long appuser, @Param("catId")Long catId,@Param("diffId") Long diffId);
}
