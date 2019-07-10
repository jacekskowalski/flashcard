package com.project.flashcards.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface AppuserAchievementRepository extends JpaRepository<AppuserAchievement, Long> {
    @Query("SELECT aa.achievement.name FROM AppuserAchievement aa WHERE aa.user.id = ?1")
    List<String> findAllByUser_id(Long id);
    @Query("SELECT new AppuserAchievement(aa.user, aa.achievement) FROM AppuserAchievement aa WHERE aa.user.id = ?1 AND aa.achievement.name = ?2")
    AppuserAchievement findByIds(Long userId, String achievementName);
    @Transactional
    @Modifying
    @Query("DELETE FROM AppuserAchievement a WHERE a.user.id = ?1")
    void deleteByUser_id(Long id);

}
