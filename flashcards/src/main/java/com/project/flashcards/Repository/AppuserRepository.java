package com.project.flashcards.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppuserRepository extends JpaRepository<Appuser,Long> {
    Appuser findAppuserByEmailAndPswd(String email, String pswd);
    boolean existsByEmail(String email);
    boolean existsByEmailAndAndPswd(String email, String pswd);
}
