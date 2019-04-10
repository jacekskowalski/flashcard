package com.project.flashcards.Controller;

import com.project.flashcards.Repository.Appuser;
import com.project.flashcards.Repository.AppuserRepository;
import com.project.flashcards.Repository.FlashcardRepository;
import com.sendgrid.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Objects;

@CrossOrigin
@RestController
public class IndexController {

@Value("${spring.sendgrid.api-key}")
private String apiKey;
    @Autowired
    private AppuserRepository appuserRepository;
    @Autowired
    private FlashcardRepository flashcards;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    public AppuserRepository getAppuserRepository() {
        return appuserRepository;
    }

    public void setAppuserRepository(AppuserRepository appuserRepository) {
        this.appuserRepository = appuserRepository;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public FlashcardRepository getFlashcards() {
        return flashcards;
    }

    public void setFlashcards(FlashcardRepository flashcards) {
        this.flashcards = flashcards;
    }

@GetMapping("/appusers")
public  ResponseEntity<?> badrequest(){
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
}

      @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Appuser appuser) {
          String getPswd = appuserRepository.findPswd(appuser.getEmail());

          if (!appuserRepository.existsByEmail(appuser.getEmail())
                  || bCryptPasswordEncoder.matches(bCryptPasswordEncoder.encode(appuser.getPswd()),
                  getPswd)) {
              return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
          } else {
              return new ResponseEntity<>(appuserRepository.findAppuserByEmail(appuser.getEmail()), HttpStatus.OK);
          }
    }


@PostMapping("/remember")
public ResponseEntity<?> remember(@RequestBody Appuser appuser) throws IOException{
Appuser getpswd= appuserRepository.findAppuserByEmail(appuser.getEmail());
    if(Objects.isNull(getpswd)){
        return new ResponseEntity<>("Account not found", HttpStatus.NOT_FOUND);
    }else {
        Email from = new Email("flashcards@no-reply.com");
        String subject = "Flashcards";
        Email to = new Email(getpswd.getEmail());
        Content content = new Content("text/html", "<p>Kliknij w link, aby zresetować hasło</p>" +
                "<a href='http://localhost:3000/reset'>Reset</a>" );
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
    return new ResponseEntity<>("Email sent", HttpStatus.OK);
}
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody Appuser user){
        if(appuserRepository.existsByEmail(user.getEmail()))

            return new ResponseEntity<>( "Account exist", HttpStatus.FORBIDDEN);
        else {
            Appuser appuser =new Appuser();
            appuser.setName_surname(user.getName_surname());
            appuser.setEmail(user.getEmail());
            appuser.setPswd(bCryptPasswordEncoder.encode(user.getPswd()));
            appuserRepository.save(appuser);
            return new ResponseEntity<>("Account created", HttpStatus.OK);
        }
           }

@PutMapping("/reset")
public ResponseEntity<?> changePassword(@RequestBody Appuser appuser){
        if(Objects.isNull(appuser))
            return new ResponseEntity<>("No content", HttpStatus.NO_CONTENT);

         else if(! appuserRepository.existsByEmail(appuser.getEmail())){
            return new ResponseEntity<>( "Account not exist", HttpStatus.NOT_FOUND);
        }else{
             String newPassword= bCryptPasswordEncoder.encode(appuser.getPswd());
             appuserRepository.changeUserPassword(appuser.getEmail(),newPassword);
             return  new ResponseEntity<>( "Password changed", HttpStatus.OK);
}
}

}
