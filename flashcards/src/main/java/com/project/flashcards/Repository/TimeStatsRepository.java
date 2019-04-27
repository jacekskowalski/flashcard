package com.project.flashcards.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.transaction.Transactional;

@Repository
@CrossOrigin
public interface TimeStatsRepository extends JpaRepository<TimeStats, Long> {
    @Transactional
    @Modifying
    @Query("UPDATE TimeStats t SET t.countlogin= t.countlogin+ 1 WHERE t.appuser.id = (SELECT a.id FROM Appuser a WHERE a.email = ?1)")
    void loginCounter(String email);
    @Query("SELECT t.countlogin FROM TimeStats t WHERE t.appuser.id= ?1")
    Integer getCountLogin(Long id);
}
