package com.project.flashcards.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface ResultRepository extends JpaRepository<Results, Long> {
    @Query("SELECT COALESCE(SUM(r.totaltime),0) FROM Results r WHERE r.appuser.id = ?1 AND r.category.id =?2")
    Double getTimeForCompletedCourse(Long userID, Long categoryId);
    @Query("SELECT COALESCE(SUM(r.totaltime),0) FROM Results r WHERE r.appuser.id = ?1")
    Double getTimeForCompletedCourses(Long userID);
    @Transactional
    @Modifying
    @Query("DELETE FROM Results t WHERE t.appuser.id = ?1")
    void deleteResultsBy(Long id);
@Query(value = "SELECT count(*) from Results r  WHERE r.apuser_id = ?1",nativeQuery = true)
    Integer countResultsByAppuserId(Long userId);
}
