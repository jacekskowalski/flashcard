package com.project.flashcards.Controller;

import com.project.flashcards.Repository.Appuser;
import com.project.flashcards.Repository.AppuserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.sendgrid.*;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
public class IndexController {

@Value("${spring.sendgrid.api-key}")
private String apiKey;
    @Autowired
    private AppuserRepository appuserRepository;

    public AppuserRepository getAppuserRepository() {
        return appuserRepository;
    }

    public void setAppuserRepository(AppuserRepository appuserRepository) {
        this.appuserRepository = appuserRepository;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
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

@PostMapping("/remember")
public void remember(@RequestBody Appuser appuser) throws IOException{
Appuser getpswd= appuserRepository.findAppuserByEmail(appuser.getEmail());
   Email from = new Email("flashcards@no-reply.com");
    String subject = "Flashcards";
    Email to = new Email(getpswd.getEmail());
    Content content = new Content("text/plain", "Your forgotten password " +getpswd.getPswd());
    Mail mail = new Mail(from, subject, to, content);
    SendGrid sg = new SendGrid(apiKey);
    Request request = new Request();
    try {
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());
        Response response = sg.api(request);

    } catch (IOException ex) {
        throw ex;
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
