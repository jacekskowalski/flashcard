package com.project.flashcards.Controller;

import com.google.gson.Gson;
import com.project.flashcards.Repository.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.transform.Result;
import java.util.List;
import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;

@RestController
@CrossOrigin
public class AchievementController {
    @Autowired
    private StatisticsRepository statisticsRepository;
    @Autowired
    private AppuserRepository appuserRepository;
    @Autowired
    private  AppuserAchievementRepository appuserAchievementRepository;
     @Autowired
    private FavouriteFlashcardsRepository favouriteFlashcardsRepository;
    @Autowired
    private AchievementRepository achievementRepository;
      @Autowired
    private DifficultyRepository difficultyRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    Gson gson = new Gson();

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

    public AppuserAchievementRepository getAppuserAchievementRepository() {
        return appuserAchievementRepository;
    }

    public void setAppuserAchievementRepository(AppuserAchievementRepository appuserAchievementRepository) {
        this.appuserAchievementRepository = appuserAchievementRepository;
    }

    public FavouriteFlashcardsRepository getFavouriteFlashcardsRepository() {
        return favouriteFlashcardsRepository;
    }

    public void setFavouriteFlashcardsRepository(FavouriteFlashcardsRepository favouriteFlashcardsRepository) {
        this.favouriteFlashcardsRepository = favouriteFlashcardsRepository;
    }

    public AchievementRepository getAchievementRepository() {
        return achievementRepository;
    }

    public void setAchievementRepository(AchievementRepository achievementRepository) {
        this.achievementRepository = achievementRepository;
    }

    @GetMapping("/achievement/{id}")
    @ApiOperation(value = "Returns all user achievements")
        public List<String> getAllAchievements(@PathVariable("id") Long id){
        Optional<Appuser> getappuser=  appuserRepository.findById(id);
        Appuser newappuser = getappuser.get();
        int userScore = statisticsRepository.calculateTotalUserPoints(id).intValue();
        if(userScore == 20) {
             createAchievement(id, "Dobre początki");
        }else if( userScore == 100){
            createAchievement(id, "Tauzen");
        }else if(userScore == 1000){
            createAchievement(id, "Ważny krok");
        }
        int jsscore = statisticsRepository.calculateJSUserPoints(id).intValue();
        if(jsscore == 1000) {
            createAchievement(id,"Adept JavaScript") ;
        }else if(jsscore == 5000){
            createAchievement(id,"Mistrz JavaScript");
        }
        int htmlscore = statisticsRepository.calculateHtmlUserPoints(id).intValue();
        if(htmlscore == 1000) {
            createAchievement(id,"Adept HTML5");
                 }else if(htmlscore == 5000){
           createAchievement(id,"Mistrz HTML5");
                  }
        int cssscore = statisticsRepository.calculateCssUserPoints(id).intValue();
        if(cssscore == 1000) {
            createAchievement(id,"Adept CSS3");
                    }else if(cssscore == 5000){
            createAchievement(id,"Mistrz CSS3");
        }

        Iterator it = statisticsRepository.completed(id).iterator();
        while (it.hasNext()) {
            Object[] obj = (Object[]) it.next();

            if(String.valueOf(obj[1]).equalsIgnoreCase("HTML5")){
               if(String.valueOf(obj[2]).equalsIgnoreCase("Początkujący")){
                  createAchievement(id,"Hyperstart");
                       }else if(String.valueOf(obj[2]).equalsIgnoreCase("Średniozaawansowany")){
                   createAchievement(id,"Coraz lepiej");
               }else if(String.valueOf(obj[2]).equalsIgnoreCase("Zaawansowany")){
                   createAchievement(id,"Mistrz znaczników");
               }
            }else if(String.valueOf(obj[1]).equalsIgnoreCase("CSS3")){
                if(String.valueOf(obj[2]).equalsIgnoreCase("Początkujący")){
                    createAchievement(id,"Stylowy start");
                }else if(String.valueOf(obj[2]).equalsIgnoreCase("Średniozaawansowany")){
                    createAchievement(id,"Oby tak dalej");
                }else if(String.valueOf(obj[2]).equalsIgnoreCase("Zaawansowany")){
                    createAchievement(id,"Czarodziej");
                }
            }else if(String.valueOf(obj[1]).equalsIgnoreCase("JavaScript")){
                if(String.valueOf(obj[2]).equalsIgnoreCase("Początkujący")){
                    createAchievement(id,"Skryptowy start");
                }else if(String.valueOf(obj[2]).equalsIgnoreCase("Średniozaawansowany")){
                    createAchievement(id,"Nie przestawaj");
                }else if(String.valueOf(obj[2]).equalsIgnoreCase("Zaawansowany")){
                    createAchievement(id,"Świat jest twój");
                }
            }
        }
         return appuserAchievementRepository.findAllByUser_id(id);
    }
/*
    @PostMapping("/achievement")
    @ApiOperation(value = "")
    public  ResponseEntity<?> result(@RequestBody ResultHelperComponent result) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        if (Objects.isNull(result))
            return new ResponseEntity<>(gson.toJson("Data not found"), responseHeaders, HttpStatus.UNPROCESSABLE_ENTITY);
        else {
            Long getcategoryId = categoryRepository.getCategoryId(result.getCategoryName());
            Long getdifficultyId = difficultyRepository.getDifficultyId(result.getDiificultyName());
            Appuser app= appuserRepository.findById(result.getApuserId()).get();
            Difficulty diff= difficultyRepository.findByName(result.getDiificultyName());
            Category cat= categoryRepository.findByName(result.getCategoryName());
         //   Results newresult = new Results(app, result.getTime(), cat, diff, );



        return new ResponseEntity<>(gson.toJson("result sent"), responseHeaders, HttpStatus.OK);
    }
    }
*/
    public void createAchievement(Long apuserId, String name){

             if(appuserAchievementRepository.findByIds(apuserId,name).isEmpty()){
          Optional<Appuser> getappuser=  appuserRepository.findById(apuserId);
            Appuser newappuser = getappuser.get();
            Achievement achievement = achievementRepository.findAchievementByName(name);
            AppuserAchievement appuserAchievement= new AppuserAchievement(newappuser, achievement);
            appuserAchievementRepository.save(appuserAchievement);
                   }
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
}
