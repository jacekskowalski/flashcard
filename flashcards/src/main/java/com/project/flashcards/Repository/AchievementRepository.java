package com.project.flashcards.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@Repository
@CrossOrigin
public interface AchievementRepository extends JpaRepository<Achievement, Long> {
Achievement findAchievementByName(String name);

}
