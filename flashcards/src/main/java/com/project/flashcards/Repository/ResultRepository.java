package com.project.flashcards.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface ResultRepository extends JpaRepository<Results, Long> {
    @Query("SELECT SUM(r.totaltime) FROM Results r WHERE r.appuser.id = ?1 AND r.category =?2")
    Double getTimeForCompletedCourse(Long userID, Long categoryId);
    @Query("SELECT SUM(r.totaltime) FROM Results r WHERE r.appuser.id = ?1")
    Double getTimeForCompletedCourses(Long userID);
    @Transactional
    @Modifying
    @Query("DELETE FROM Results t WHERE t.appuser.id = ?1")
    void deleteResultsBy(Long id);
}
