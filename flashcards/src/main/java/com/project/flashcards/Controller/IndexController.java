package com.project.flashcards.Controller;

import com.google.gson.Gson;
import com.project.flashcards.Repository.Appuser;
import com.project.flashcards.Repository.AppuserRepository;
import com.project.flashcards.Repository.FlashcardRepository;
import com.sendgrid.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Collections;
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
Gson gson =new Gson();

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
          HttpHeaders responseHeaders = new HttpHeaders();
          responseHeaders.setContentType(MediaType.APPLICATION_JSON);
          if (!appuserRepository.existsByEmail(appuser.getEmail())
                  || bCryptPasswordEncoder.matches(bCryptPasswordEncoder.encode(appuser.getPswd()),
                  getPswd)) {
              return new ResponseEntity<>(gson.toJson("Wrong data"),responseHeaders,HttpStatus.UNPROCESSABLE_ENTITY);
          } else {
              return new ResponseEntity<>(appuserRepository.findAppuserByEmail(appuser.getEmail()), HttpStatus.OK);
          }
    }


@PostMapping("/remember")
public ResponseEntity<?> remember(@RequestBody Appuser appuser) throws IOException{
Appuser getpswd= appuserRepository.findAppuserByEmail(appuser.getEmail());
    HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.setContentType(MediaType.APPLICATION_JSON);
    if(Objects.isNull(getpswd)){
        return new ResponseEntity<>(gson.toJson("Account not found"), responseHeaders,HttpStatus.NOT_FOUND);
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
    return new ResponseEntity<>(gson.toJson("Email sent"),responseHeaders, HttpStatus.OK);
}
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody Appuser user){
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
              if(appuserRepository.existsByEmail(user.getEmail()))

            return new ResponseEntity<>(gson.toJson("Account exists"),responseHeaders, HttpStatus.FORBIDDEN);
        else {
            Appuser appuser =new Appuser();
            appuser.setName_surname(user.getName_surname());
            appuser.setEmail(user.getEmail());
            appuser.setPswd(bCryptPasswordEncoder.encode(user.getPswd()));
            appuserRepository.save(appuser);
            return new ResponseEntity<>(gson.toJson("Account created"),responseHeaders, HttpStatus.OK);
        }
           }

@PutMapping("/reset")
public ResponseEntity<?> changePassword(@RequestBody Appuser appuser){
    HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        if(Objects.isNull(appuser))
            return new ResponseEntity<>(gson.toJson("No content"),responseHeaders, HttpStatus.NO_CONTENT);

         else if(! appuserRepository.existsByEmail(appuser.getEmail())){
            return new ResponseEntity<>( gson.toJson("Account not exist"),responseHeaders, HttpStatus.NOT_FOUND);
        }else{
             String newPassword= bCryptPasswordEncoder.encode(appuser.getPswd());
             appuserRepository.changeUserPassword(appuser.getEmail(),newPassword);
             return  new ResponseEntity<>( gson.toJson("Password changed"),responseHeaders, HttpStatus.OK);
}
}

}
