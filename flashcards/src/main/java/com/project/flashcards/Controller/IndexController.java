package com.project.flashcards.Controller;

import com.project.flashcards.Repository.Appuser;
import com.project.flashcards.Repository.AppuserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
public class IndexController {
    @Autowired
    private AppuserRepository appuserRepository;

    public AppuserRepository getAppuserRepository() {
        return appuserRepository;
    }

    public void setAppuserRepository(AppuserRepository appuserRepository) {
        this.appuserRepository = appuserRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Appuser appuser) {
        if (!appuserRepository.existsByEmailAndAndPswd(appuser.getEmail(), appuser.getPswd())) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(appuserRepository.findAppuserByEmailAndPswd(appuser.getEmail(), appuser.getPswd()), HttpStatus.OK);
        }
    }
    @DeleteMapping("/account/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){

if(id ==0 || id == null){
    return new ResponseEntity<>("Account not found", HttpStatus.NOT_FOUND);
}else {
    appuserRepository.deleteById(id);
    return new ResponseEntity<>("Account deleted", HttpStatus.OK);
}
    }


    @PostMapping("/signup")
    public Appuser signup(@RequestBody Appuser user){
        if(appuserRepository.existsByEmail(user.getEmail()))
            return null;
        return appuserRepository.save(user);
    }

@PutMapping("/account")
    public Appuser update(@RequestBody Appuser appuser){
        return appuserRepository.save(appuser);
}


}
