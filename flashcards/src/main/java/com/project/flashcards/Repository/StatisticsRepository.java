package com.project.flashcards.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface StatisticsRepository extends JpaRepository<Statistics, Long> {
    @Query(value = "SELECT c.name as category,d.name as difficulty, s.points,(SELECT  count(*) FROM Flashcards WHERE Flashcards.category_id = c.id AND Flashcards.difficulty_id = d.id) FROM Statistics s,Appuser a, Category c, Difficulty d where s.user_id = a.id and s.category_id = c.id and s.difficulty_id = d.id AND s.user_id = ?1", nativeQuery = true)
    List<Object> findAllByUser(Long id);
    @Query("SELECT s FROM Statistics  s WHERE s.user_id.id = ?1 AND s.category.id = (SELECT c.id FROM Category c WHERE c.name =?2) AND s.difficulty.id = (SELECT d.id FROM Difficulty d WHERE d.name =?3)")
    Statistics checkIfExists(Long id, String cat, String diff);
    @Transactional
    @Modifying
    @Query("DELETE FROM Statistics s WHERE s.user_id.id = ?1")
    void deleteStatisticsByUser_id(Long id);
    @Transactional
    @Modifying
    @Query("UPDATE Statistics s SET s.points = :points WHERE s.user_id.id = :id AND s.category.id = :catId AND s.difficulty.id = :diffId")
    void updateStatistics(@Param("points") int points, @Param("id") Long appuser, @Param("catId") Long catId, @Param("diffId") Long diffId);
@Query("SELECT COALESCE(SUM(s.points),0) FROM Statistics s WHERE s.user_id.id = ?1")
    Integer calculateTotalUserPoints(Long id);
    @Query("SELECT COALESCE(SUM(s.points),0) FROM Statistics s WHERE s.user_id.id = ?1 AND s.category.id = 3")
    Integer calculateJSUserPoints(Long id);
    @Query("SELECT COALESCE(SUM(s.points),0) FROM Statistics s WHERE s.user_id.id = ?1 AND s.category.id = 1")
    Integer calculateHtmlUserPoints(Long id);
    @Query("SELECT COALESCE(SUM(s.points),0) FROM Statistics s WHERE s.user_id.id = ?1 AND s.category.id = 2")
    Integer calculateCssUserPoints(Long id);
    @Query(value = "SELECT a.name_surname, c.name as category,d.name as difficulty FROM Statistics s,Appuser a,Category c, Difficulty d WHERE s.user_id = a.id and s.category_id = c.id and s.difficulty_id = d.id AND s.user_id = ?1 AND s.points = (SELECT count(*) FROM Flashcards WHERE Flashcards.category_id = c.id AND Flashcards.difficulty_id = d.id)",nativeQuery = true)
    List<Object> completed(Long id);

}