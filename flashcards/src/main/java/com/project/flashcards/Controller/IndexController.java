package com.project.flashcards.Controller;

import com.google.gson.Gson;
import com.project.flashcards.Repository.*;
import com.sendgrid.*;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

//implements   AuthenticationProvider
@CrossOrigin
@RestController
public class IndexController {
@Value("${spring.sendgrid.api-key}")
private String apiKey;
    Appuser appuser;
    @Autowired
    private AppuserRepository appuserRepository;
    @Autowired
    private FlashcardRepository flashcards;
    @Autowired
    private  BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private TimeStatsRepository timeStatsRepository;

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

    public TimeStatsRepository getTimeStatsRepository() {
        return timeStatsRepository;
    }

    public void setTimeStatsRepository(TimeStatsRepository timeStatsRepository) {
        this.timeStatsRepository = timeStatsRepository;
    }


    @PostMapping("/remember")
    @ApiOperation(value = "Requires email string and returns link to reset password")
    public ResponseEntity<?> remember(@RequestBody Map<String,String> email) throws IOException{
    Appuser getpswd= appuserRepository.findAppuserByEmail(email.get("email"));
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
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException ex) {
            throw ex;
        }
    }
    return new ResponseEntity<>(gson.toJson("Email sent"),responseHeaders, HttpStatus.OK);
}
    @PostMapping("/signup")
    @ApiOperation(value = "Requires name_surname, email, pswd")
    public ResponseEntity<?> signup(@Valid @RequestBody Appuser user){
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
              if(appuserRepository.existsByEmail(user.getEmail()))

            return new ResponseEntity<>(gson.toJson("Account exists"),responseHeaders, HttpStatus.FORBIDDEN);
        else {
            Appuser appuser =new Appuser();
            appuser.setName_surname(user.getName_surname());
            appuser.setEmail(user.getEmail());
            appuser.setPswd(BCrypt.hashpw(user.getPswd(), BCrypt.gensalt(12)));
            appuserRepository.save(appuser);
            TimeStats timeStats=new TimeStats(0,appuser);
            timeStatsRepository.save(timeStats);
            return new ResponseEntity<>(gson.toJson("Account created"),responseHeaders, HttpStatus.OK);
        }
           }


    @PutMapping("/reset")
    @ApiOperation(value = "Requires email, pswd")
    public ResponseEntity<?> changePassword(@RequestBody Appuser appuser_email){
    HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        if(Objects.isNull(appuser_email))
            return new ResponseEntity<>(gson.toJson("No content"),responseHeaders, HttpStatus.NO_CONTENT);

         else if(! appuserRepository.existsByEmail(appuser_email.getEmail())){
            return new ResponseEntity<>( gson.toJson("Account not exist"),responseHeaders, HttpStatus.NOT_FOUND);
        }else{
             String newPassword= bCryptPasswordEncoder.encode(appuser_email.getPswd());
             appuserRepository.changeUserPassword(appuser_email.getEmail(),newPassword);
             return  new ResponseEntity<>( gson.toJson("Password changed"),responseHeaders, HttpStatus.OK);
}
}

    @PostMapping("/login")
    @ApiOperation(value = "Requires email, pswd")
    public ResponseEntity<?> login(@RequestBody Appuser appuser) {

        String getPswd = appuserRepository.findPswd(appuser.getEmail());
        if (!appuserRepository.existsByEmail(appuser.getEmail()) || !bCryptPasswordEncoder.matches(appuser.getPswd(),getPswd))
        {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }else {
         Appuser appu= appuserRepository.findAppuserByEmail(appuser.getEmail());
         timeStatsRepository.loginCounter(appuser.getEmail());
            return new ResponseEntity<>(
                    new UserToken(Jwts.builder().setSubject(appu.getEmail()).claim("roles", "user")
                            .setIssuedAt(new Date()).signWith(SignatureAlgorithm.HS256, "123#&*zcvAWEE999").compact(),
                            appu.getId(),appu.getName_surname(),appu.getEmail()),
                    HttpStatus.OK);
        }
    }
/*
            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException, BadCredentialsException {

              String name= "emalina@hotmail.com";

             String password= "emalina6";
                String getPswd = appuserRepository.findPswd(name).trim();
            if (!appuserRepository.existsByEmail(name) || !bCryptPasswordEncoder.matches(password,getPswd))
                {
                    throw new BadCredentialsException("Not found");
                }
                newappuser(name);
                    timeStatsRepository.loginCounter(name);
                return new UsernamePasswordAuthenticationToken(name, password, new ArrayList<>());

            }

            @Override
            public boolean supports(Class<?> aClass) {
                return UsernamePasswordAuthenticationToken.class.isAssignableFrom(aClass);
                  }
*/
public void newappuser(String email) {
    setAppuser(appuserRepository.findAppuserByEmail(email));
}
    public Appuser getAppuser() {
        return appuser;
    }

    public void setAppuser(Appuser appuser) {
        this.appuser = appuser;
    }
  }
