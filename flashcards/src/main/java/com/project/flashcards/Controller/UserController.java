package com.project.flashcards.Controller;
import java.util.*;

import com.google.gson.Gson;
import com.project.flashcards.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class UserController {

    @Autowired
    private FlashcardRepository flashcards;
    @Autowired
    private StatisticsRepository statisticsRepository;
    @Autowired
    private AppuserRepository appuserRepository;
    @Autowired
    private Flashcard_pointsRepository flashcardPointsRepository;
    @Autowired
    private  DifficultyRepository difficultyRepository;
    @Autowired
    private  CategoryRepository categoryRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
@Autowired
private FavouriteFlashcardsRepository favouriteFlashcardsRepository;
    Gson gson =new Gson();

    public StatisticsRepository getStatisticsRepository() {
        return statisticsRepository;
    }

    public void setStatisticsRepository(StatisticsRepository statisticsRepository) {
        this.statisticsRepository = statisticsRepository;
    }

    public AppuserRepository getAppuserRepository() {
        return appuserRepository;
    }

    public void setAppuserRepository(AppuserRepository appuserRepository) {
        this.appuserRepository = appuserRepository;
    }

    public FavouriteFlashcardsRepository getFavouriteFlashcardsRepository() {
        return favouriteFlashcardsRepository;
    }

    public void setFavouriteFlashcardsRepository(FavouriteFlashcardsRepository favouriteFlashcardsRepository) {
        this.favouriteFlashcardsRepository = favouriteFlashcardsRepository;
    }

    @GetMapping("/statistics")
    public ResponseEntity<?> getStatForUser(@RequestParam Long id)
    {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        List<LinkedHashMap<String,String>> lista= new ArrayList<>();
        if(! appuserRepository.existsById(id) ){
            return new ResponseEntity<>(gson.toJson("Data not found"), responseHeaders,HttpStatus.UNPROCESSABLE_ENTITY);}
    else{
                Iterator it = statisticsRepository.findAllByUser(id).iterator();
                while (it.hasNext()) {
                    Object[] obj = (Object[]) it.next();
                    LinkedHashMap<String, String> data = new LinkedHashMap<>();
                    data.put("user", String.valueOf(obj[0]));
                    data.put("category", String.valueOf(obj[1]));
                    data.put("difficulty", String.valueOf(obj[2]));
                    data.put("score", String.valueOf(obj[3]));
                    data.put("total", String.valueOf(obj[4]));
                    lista.add(data);

                }
                return new ResponseEntity<>(lista,HttpStatus.OK);
            }
    }

    @PutMapping("/account/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody Appuser appuser){
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        if(id ==0 || id == null){

            return new ResponseEntity<>("Account not found",responseHeaders, HttpStatus.UNPROCESSABLE_ENTITY);
        }else {
            Optional<Appuser> getappuser=  appuserRepository.findById(id);
            Appuser newappuser = getappuser.get();
            newappuser.setName_surname(appuser.getName_surname());
            newappuser.setEmail(appuser.getEmail());
            newappuser.setPswd(bCryptPasswordEncoder.encode(appuser.getPswd()));
            appuserRepository.save(newappuser);
            return new ResponseEntity<>("Account updated",responseHeaders, HttpStatus.OK);
        }
    }

    @DeleteMapping("/account/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        if(id ==0 || id == null){
            return new ResponseEntity<>("Account not found", HttpStatus.UNPROCESSABLE_ENTITY);
        }else {
            statisticsRepository.deleteFlashcardPointsByUser_id(id);
            statisticsRepository.deleteStatisticsByUser_id(id);
            appuserRepository.deleteById(id);
            return new ResponseEntity<>("Account deleted",responseHeaders, HttpStatus.OK);
        }
    }

    @PostMapping("/flashcard")
    public void createQuiz(@RequestBody FlashcardInitializer flashcardInitializer){

    Optional<Appuser> appuser = appuserRepository.findById(flashcardInitializer.getId());
       Appuser  user =appuser.get();
     Difficulty difficulty = difficultyRepository.findByName(flashcardInitializer.getDifficultyName());
       Category category = categoryRepository.findByName(flashcardInitializer.getCategoryName());

      Statistics newstatistics =new Statistics(user,0,category, difficulty);
     List<Flashcards> flashcardPoints= flashcards.findBySelectedOptions(flashcardInitializer.getCategoryName(),
             flashcardInitializer.getDifficultyName());

     for( Flashcards f : flashcardPoints){

        Flashcard_points flashcard_points = new Flashcard_points(user,f,0);
        if(!Objects.isNull(f))
            System.out.println(f);
       flashcardPointsRepository.save(flashcard_points);
     }
       statisticsRepository.save(newstatistics);
           }

    @PutMapping("/flashcard")
    public ResponseEntity<?> updateStat(@RequestBody StatisticsHelperComponent statisticsHelperComponent){
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        if(! appuserRepository.existsById(statisticsHelperComponent.getApuserId()) ||
                ! flashcards.existsById(statisticsHelperComponent.getFlashcardsId())){
            return new ResponseEntity<>(gson.toJson("Data not found"), responseHeaders,HttpStatus.UNPROCESSABLE_ENTITY);
        }
int result;
     statisticsRepository.updateFlashcardPoints(statisticsHelperComponent.getApuserId(),statisticsHelperComponent.getFlashcardsId());
       Long difficultyId= flashcards.getDifficultyId(statisticsHelperComponent.getFlashcardsId());
       Long categoryId = flashcards.getCategoryId(statisticsHelperComponent.getFlashcardsId());
     if(Objects.isNull(statisticsRepository.calculateUserPoints(statisticsHelperComponent.getApuserId(),
              statisticsHelperComponent.getFlashcardsId(),categoryId,difficultyId)))
     {
         result= 0;
        statisticsRepository.updateStatistics(result,statisticsHelperComponent.getApuserId(),categoryId,difficultyId);
         return new ResponseEntity<>( responseHeaders,HttpStatus.OK);
     }
    else {
         result = statisticsRepository.calculateUserPoints(statisticsHelperComponent.getApuserId(),
                 statisticsHelperComponent.getFlashcardsId(), categoryId, difficultyId);
         statisticsRepository.updateStatistics(result,statisticsHelperComponent.getApuserId(),categoryId,difficultyId);
         return new ResponseEntity<>(HttpStatus.OK);
     }

    }

    public FlashcardRepository getFlashcards() {
        return flashcards;
    }

    public void setFlashcards(FlashcardRepository flashcards) {
        this.flashcards = flashcards;
    }

    public Flashcard_pointsRepository getFlashcardPointsRepository() {
        return flashcardPointsRepository;
    }

    public void setFlashcardPointsRepository(Flashcard_pointsRepository flashcardPointsRepository) {
        this.flashcardPointsRepository = flashcardPointsRepository;
    }


    public DifficultyRepository getDifficultyRepository() {
        return difficultyRepository;
    }

    public void setDifficultyRepository(DifficultyRepository difficultyRepository) {
        this.difficultyRepository = difficultyRepository;
    }

    public CategoryRepository getCategoryRepository() {
        return categoryRepository;
    }

    public void setCategoryRepository(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    @PostMapping("/favouriteflashcard")
    public ResponseEntity<?> saveFlashcard(@RequestBody StatisticsHelperComponent statisticsHelperComponent){
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        Optional<Appuser> getappuser=  appuserRepository.findById(statisticsHelperComponent.getApuserId());
        Appuser newappuser = getappuser.get();
        Optional<Flashcards> flash = flashcards.findById(statisticsHelperComponent.getFlashcardsId());
        Flashcards fl = flash.get();
        FavouriteFlashcards favouriteFlashcards =new FavouriteFlashcards(newappuser, fl);
        if(Objects.nonNull(favouriteFlashcardsRepository.existsByAppuserIdAnAndFlashcardsId(newappuser.getId(), fl.getId())))
            return new ResponseEntity<>("Content  exists",responseHeaders, HttpStatus.UNPROCESSABLE_ENTITY);
         else {
            favouriteFlashcardsRepository.save(favouriteFlashcards);
            return new ResponseEntity<>("Flashcard added",responseHeaders, HttpStatus.OK);
        }

    }
@DeleteMapping("/favouriteflashcard")
    public ResponseEntity<?> deleteFlashcard(@RequestBody StatisticsHelperComponent statisticsHelperComponent){
    HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.setContentType(MediaType.APPLICATION_JSON);
    Optional<Appuser> getappuser=  appuserRepository.findById(statisticsHelperComponent.getApuserId());
    Appuser newappuser = getappuser.get();
    Optional<Flashcards> flash = flashcards.findById(statisticsHelperComponent.getFlashcardsId());
    Flashcards fl = flash.get();
    if(Objects.isNull(favouriteFlashcardsRepository.existsByAppuserIdAnAndFlashcardsId(newappuser.getId(), fl.getId())) ||
    favouriteFlashcardsRepository.existsByAppuserIdAnAndFlashcardsId(newappuser.getId(), fl.getId()).isEmpty()){
        return new ResponseEntity<>("Content not exist",responseHeaders, HttpStatus.UNPROCESSABLE_ENTITY);
    }
    else{ favouriteFlashcardsRepository.deleteFlashcard(newappuser.getId(), fl.getId());
    return new ResponseEntity<>("Flashcard deleted",responseHeaders, HttpStatus.OK);}
}
@GetMapping("/favouriteflashcard/{id}")
    public List<FavouriteFlashcards> getAllFlashcards(@PathVariable("id") Long id){
  return  favouriteFlashcardsRepository.getAllFlashcards(id);
}
}
