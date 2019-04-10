package com.project.flashcards.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface AppuserRepository extends JpaRepository<Appuser,Long> {

    Appuser findAppuserByEmailAndPswd(String email, String pswd);
    boolean existsByEmail(String email);
    boolean existsByEmailAndAndPswd(String email, String pswd);
    boolean existsById(Long id);
    @Query("SELECT new Appuser(a.id, a.name_surname, a.email) FROM Appuser a WHERE a.email = ?1")
    Appuser findAppuserByEmail(String email);
    @Query("SELECT a.pswd FROM Appuser a WHERE a.email = ?1")
    String findPswd(String email);
    @Transactional
    @Modifying
    @Query("UPDATE Appuser a set a.pswd = ?2 WHERE a.email = ?1")
            void changeUserPassword(String email, String pswd);

}
