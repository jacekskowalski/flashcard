package com.project.flashcards.Controller;

import com.project.flashcards.Repository.Appuser;
import com.project.flashcards.Repository.AppuserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
public class IndexController {
    @Autowired
    private AppuserRepository appuserRepository;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Appuser appuser){
        if(! appuserRepository.existsByEmailAndAndPswd(appuser.getEmail(),appuser.getPswd()))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(appuserRepository.findAppuserByEmailAndPswd(appuser.getEmail(),appuser.getPswd()), HttpStatus.OK);
    }
    @PostMapping("/signup")
    public Appuser signup(@RequestBody Appuser user){
        if(appuserRepository.existsByEmail(user.getEmail()))
            return null;
        return appuserRepository.save(user);
    }
    public AppuserRepository getAppuserRepository() {
        return appuserRepository;
    }

    public void setAppuserRepository(AppuserRepository appuserRepository) {
        this.appuserRepository = appuserRepository;
    }
}
