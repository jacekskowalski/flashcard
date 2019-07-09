package com.project.flashcards.Controller;
import java.util.*;

import com.google.gson.Gson;
import com.project.flashcards.Repository.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
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
    private DifficultyRepository difficultyRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private AppuserAchievementRepository appuserAchievementRepository;
@Autowired
private TimeStatsRepository timeStatsRepository;
    @Autowired
    private FavouriteFlashcardsRepository favouriteFlashcardsRepository;
    @Autowired
    private AchievementRepository achievementRepository;
    @Autowired
     private ResultRepository resultRepository;

    Gson gson = new Gson();

    public StatisticsRepository getStatisticsRepository() {
        return statisticsRepository;
    }

    public AppuserAchievementRepository getAppuserAchievementRepository() {
        return appuserAchievementRepository;
    }

    public void setAppuserAchievementRepository(AppuserAchievementRepository appuserAchievementRepository) {
        this.appuserAchievementRepository = appuserAchievementRepository;
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

    public TimeStatsRepository getTimeStatsRepository() {
        return timeStatsRepository;
    }

    public void setTimeStatsRepository(TimeStatsRepository timeStatsRepository) {
        this.timeStatsRepository = timeStatsRepository;
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

    public AchievementRepository getAchievementRepository() {
        return achievementRepository;
    }

    public void setAchievementRepository(AchievementRepository achievementRepository) {
        this.achievementRepository = achievementRepository;
    }


    @PutMapping("/account/{id}")
    @ApiOperation(value = "Requires user id")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody Appuser appuser) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        if (id == 0 || id == null) {

            return new ResponseEntity<>("Account not found", responseHeaders, HttpStatus.UNPROCESSABLE_ENTITY);
        } else {
            Optional<Appuser> getappuser = appuserRepository.findById(id);
            Appuser newappuser = getappuser.get();
            newappuser.setName_surname(appuser.getName_surname());
            newappuser.setEmail(appuser.getEmail());
            newappuser.setPswd(bCryptPasswordEncoder.encode(appuser.getPswd()));
            appuserRepository.save(newappuser);
            return new ResponseEntity<>(gson.toJson("Account updated"), responseHeaders, HttpStatus.OK);
        }
    }

    @DeleteMapping("/account/{id}")
    @ApiOperation(value = "Require user id")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        if (id == 0 || id == null) {
            return new ResponseEntity<>(gson.toJson("Account not found"), HttpStatus.UNPROCESSABLE_ENTITY);
        } else {
            favouriteFlashcardsRepository.deleteByUser_id(id);
            resultRepository.deleteResultsBy(id);
            timeStatsRepository.deleteTimeStatsByUserId(id);
            appuserAchievementRepository.deleteByUser_id(id);
            flashcardPointsRepository.deleteFlashcardPointsByUser_id(id);
            statisticsRepository.deleteStatisticsByUser_id(id);
            appuserRepository.deleteById(id);
            return new ResponseEntity<>(gson.toJson("Account deleted"), responseHeaders, HttpStatus.OK);
        }
    }

    @PostMapping("/flashcard")
    @ApiOperation(value = "Requires id, categoryName, difficultyName")
    public ResponseEntity<?> createQuiz(@RequestBody FlashcardInitializer flashcardInitializer) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        if(! Objects.isNull(statisticsRepository.checkIfExists(flashcardInitializer.getId(),
                flashcardInitializer.getCategoryName(),flashcardInitializer.getDifficultyName()))){
            return new ResponseEntity<>(gson.toJson("Data exists"),responseHeaders, HttpStatus.FORBIDDEN);
        }
        if(! appuserRepository.findById(flashcardInitializer.getId()).isPresent() ||
        Objects.isNull(difficultyRepository.findByName(flashcardInitializer.getDifficultyName())) ||
        Objects.isNull(categoryRepository.findByName(flashcardInitializer.getCategoryName()))){
         return new ResponseEntity<>(gson.toJson("Data not found"), responseHeaders, HttpStatus.UNPROCESSABLE_ENTITY);
        }
       else {
           Optional<Appuser> appuser = appuserRepository.findById(flashcardInitializer.getId());
           Appuser user = appuser.get();
           Difficulty difficulty = difficultyRepository.findByName(flashcardInitializer.getDifficultyName());
           Category category = categoryRepository.findByName(flashcardInitializer.getCategoryName());
           Statistics newstatistics = new Statistics(user, 0, category, difficulty);
           List<Flashcards> flashcardPoints = flashcards.findBySelectedOptions(flashcardInitializer.getCategoryName(),
                   flashcardInitializer.getDifficultyName());
          for (Flashcards f : flashcardPoints) {
         Flashcard_points flashcard_points = new Flashcard_points(user, f, 0, "no");
        if (!Objects.isNull(f))
            flashcardPointsRepository.save(flashcard_points);
        }
           statisticsRepository.save(newstatistics);

           return new ResponseEntity<>(flashcardPoints, responseHeaders, HttpStatus.OK);
       }
    }
  @PutMapping("/discovered")
  @ApiOperation(value = "Requires apuserId, flashcardsId, discovered=yes")
  public void updateFlashcardPointField(@RequestBody StatisticsHelperComponent statisticsHelperComponent){
              flashcardPointsRepository
              .updateFlashcardPointsField(statisticsHelperComponent.getApuserId(),
               statisticsHelperComponent.getFlashcardsId());
  }

    @PutMapping("/flashcard")
    @ApiOperation(value = "Requires apuserId, flashcardsId")
    public ResponseEntity<?> updateStat(@RequestBody StatisticsHelperComponent statisticsHelperComponent) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        if (!appuserRepository.existsById(statisticsHelperComponent.getApuserId()) ||
                !flashcards.existsById(statisticsHelperComponent.getFlashcardsId())) {
            return new ResponseEntity<>(gson.toJson("Data not found"), responseHeaders, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        int result;
        flashcardPointsRepository.updateFlashcardPoints(statisticsHelperComponent.getApuserId(),
        statisticsHelperComponent.getFlashcardsId());
              Long difficultyId = flashcards.getDifficultyId(statisticsHelperComponent.getFlashcardsId());
        Long categoryId = flashcards.getCategoryId(statisticsHelperComponent.getFlashcardsId());
        result = flashcardPointsRepository.calculateUserPoints(statisticsHelperComponent.getApuserId(),
                categoryId, difficultyId);
        if ( result ==1 ){
               createAchievement(statisticsHelperComponent.getApuserId(),"Chrzest");
        }
            statisticsRepository.updateStatistics(result, statisticsHelperComponent.getApuserId(), categoryId, difficultyId);
            long userScore = statisticsRepository.calculateTotalUserPoints(statisticsHelperComponent.getApuserId()).longValue();
            Optional<Appuser> getappuser=  appuserRepository.findById(statisticsHelperComponent.getApuserId());
            Appuser newappuser = getappuser.get();
            LinkedHashMap<String, Integer> data = new LinkedHashMap<>();
            data.put("score",result);
            data.put("sum", flashcards.countAllByCategoryAndDiffiulty(categoryId,difficultyId));
               if(userScore == flashcards.count()){
                   Achievement ids= achievementRepository.findAchievementByName("Złap je wszystkie") ;
                   AppuserAchievement appuserAchievement= new AppuserAchievement(newappuser,ids);
                   appuserAchievementRepository.save(appuserAchievement);
               }
               updateAchievement(statisticsHelperComponent.getApuserId());
            return new ResponseEntity<>(gson.toJson(data),responseHeaders,HttpStatus.OK);
        }

    @PostMapping("/favouriteflashcard")
    @ApiOperation(value = "Requires apuserId, flashcardsId")
    public ResponseEntity<?> saveFlashcard(@RequestBody StatisticsHelperComponent statisticsHelperComponent) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        Optional<Appuser> getappuser = appuserRepository.findById(statisticsHelperComponent.getApuserId());
        Appuser newappuser = getappuser.get();
        Optional<Flashcards> flash = flashcards.findById(statisticsHelperComponent.getFlashcardsId());
        Flashcards fl = flash.get();
        FavouriteFlashcards favouriteFlashcards = new FavouriteFlashcards(newappuser, fl);
        if (favouriteFlashcardsRepository
                .existsByAppuserIdAnAndFlashcardsId(statisticsHelperComponent.getApuserId(), statisticsHelperComponent.getFlashcardsId()).size() > 0)
            return new ResponseEntity<>(gson.toJson("Content  exists"), responseHeaders, HttpStatus.UNPROCESSABLE_ENTITY);
        else {
            favouriteFlashcardsRepository.save(favouriteFlashcards);
            int sumFavFlash = favouriteFlashcardsRepository.sumAllFlashcards(statisticsHelperComponent.getApuserId());
            if(sumFavFlash == 50) {
                Achievement ids= achievementRepository.findAchievementByName("Sentyment") ;
                AppuserAchievement appuserAchievement= new AppuserAchievement(newappuser,ids);
                appuserAchievementRepository.save(appuserAchievement);
            }
            return new ResponseEntity<>(gson.toJson("Flashcard added"), responseHeaders, HttpStatus.OK);
        }

    }

    @DeleteMapping("/favouriteflashcard")
    @ApiOperation(value = "Requires apuserId, flashcardsId ")
    public ResponseEntity<?> deleteFlashcard(@RequestBody StatisticsHelperComponent statisticsHelperComponent) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        Optional<Appuser> getappuser = appuserRepository.findById(statisticsHelperComponent.getApuserId());
        Appuser newappuser = getappuser.get();
        Optional<Flashcards> flash = flashcards.findById(statisticsHelperComponent.getFlashcardsId());
        Flashcards fl = flash.get();
        if (favouriteFlashcardsRepository.existsByAppuserIdAnAndFlashcardsId(newappuser.getId(), fl.getId()).isEmpty()) {
            return new ResponseEntity<>(gson.toJson("Content not exist"), responseHeaders, HttpStatus.UNPROCESSABLE_ENTITY);
        } else {
            favouriteFlashcardsRepository.deleteFlashcard(newappuser.getId(), fl.getId());
            return new ResponseEntity<>(gson.toJson("Flashcard deleted"), responseHeaders, HttpStatus.OK);
        }
    }

    @GetMapping("/favouriteflashcard/{id}")
    @ApiOperation(value = "Returns flashcard_id,question, answer, category, difficulty")
    public List<LinkedHashMap<String, String>> getAllFlashcards(@PathVariable("id") Long id) {
        List<LinkedHashMap<String, String>> lista = new ArrayList<>();
        Iterator newit = favouriteFlashcardsRepository.getAllFlashcards(id).iterator();
        while (newit.hasNext()) {
            Object[] obj = (Object[]) newit.next();
            LinkedHashMap<String, String> data = new LinkedHashMap<>();
            data.put("flashcard_id", String.valueOf(obj[0]));
            data.put("question", String.valueOf(obj[1]));
            data.put("answer", String.valueOf(obj[2]));
            data.put("category", String.valueOf(obj[3]));
            data.put("difficulty", String.valueOf(obj[4]));
            lista.add(data);
        }
        return lista;
    }
    public void createAchievement(Long id, String name){
        if(Objects.isNull(appuserAchievementRepository.findByIds(id,name))){
            Optional<Appuser> getappuser=  appuserRepository.findById(id);
            Appuser appuser = getappuser.get();
            Achievement achievement = achievementRepository.findAchievementByName(name);
            AppuserAchievement appuserAchievement= new AppuserAchievement(appuser, achievement);
            appuserAchievementRepository.save(appuserAchievement);
        }
    }

    public void updateAchievement(Long id){
        int userScore = statisticsRepository.calculateTotalUserPoints(id).intValue();

        if(userScore  >= 20) {
            createAchievement(id, "Dobre początki");
        }
        if( userScore >= 100){
            createAchievement(id, "Tauzen");
        }
        if(userScore >= 500){
            createAchievement(id, "Ważny krok");
        }

        int jsscore = statisticsRepository.calculateJSUserPoints(id).intValue();
        if(jsscore >= 50) {
            createAchievement(id,"Adept JavaScript") ;
        }
        if(jsscore >= 150){
            createAchievement(id,"Mistrz JavaScript");
        }
        int htmlscore = statisticsRepository.calculateHtmlUserPoints(id).intValue();
        if(htmlscore >= 50) {
            createAchievement(id,"Adept HTML5");
                 }
         if(htmlscore >= 150){
           createAchievement(id,"Mistrz HTML5");
                  }
        int cssscore = statisticsRepository.calculateCssUserPoints(id).intValue();
        if(cssscore >= 50) {
            createAchievement(id,"Adept CSS3");
                    }
        if(cssscore >= 150){
            createAchievement(id,"Mistrz CSS3");
        }

    }

    public ResultRepository getResultRepository() {
        return resultRepository;
    }

    public void setResultRepository(ResultRepository resultRepository) {
        this.resultRepository = resultRepository;
    }
}