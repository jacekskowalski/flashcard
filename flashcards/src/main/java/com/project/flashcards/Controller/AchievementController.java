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
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@CrossOrigin
public class AchievementController {
    Gson gson = new Gson();
    @Autowired
    private StatisticsRepository statisticsRepository;
    @Autowired
    private AppuserRepository appuserRepository;
    @Autowired
    private AppuserAchievementRepository appuserAchievementRepository;
     @Autowired
    private FavouriteFlashcardsRepository favouriteFlashcardsRepository;
    @Autowired
    private AchievementRepository achievementRepository;
      @Autowired
    private DifficultyRepository difficultyRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private FlashcardRepository flashcardRepository;
    @Autowired
    private ResultRepository resultRepository;

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

               return appuserAchievementRepository.findAllByUser_id(id);
    }

    @PostMapping("/achievement")
    @ApiOperation(value = "Called after the last answer in a quiz")
    public  ResponseEntity<?> result(@RequestBody ResultHelperComponent result) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);

        if (Objects.isNull(result))
            return new ResponseEntity<>(gson.toJson("Data not found"), responseHeaders, HttpStatus.UNPROCESSABLE_ENTITY);
        else {
            Appuser app= appuserRepository.findById(result.getApuserId()).get();
            Difficulty diff= difficultyRepository.findByName(result.getDiificultyName());
            Category cat= categoryRepository.findByName(result.getCategoryName());
            Flashcards flashcards = flashcardRepository.findById(result.getFlashcardId()).get();
            Results newresult = new Results(app, result.getTime(), cat, diff,flashcards);
            resultRepository.save(newresult);

                if(String.valueOf(result.getCategoryName()).equalsIgnoreCase("HTML5")){
                    if(String.valueOf(result.getDiificultyName()).equalsIgnoreCase("Początkujący")){
                        createAchievement(result.getApuserId(),"Hyperstart");
                    }else if(String.valueOf(result.getDiificultyName()).equalsIgnoreCase("Średniozaawansowany")){
                        createAchievement(result.getApuserId(),"Coraz lepiej");
                    }else if(String.valueOf(result.getDiificultyName()).equalsIgnoreCase("Zaawansowany")){
                        createAchievement(result.getApuserId(),"Mistrz znaczników");
                    }
                }else if(String.valueOf(result.getCategoryName()).equalsIgnoreCase("CSS3")){
                    if(String.valueOf(result.getDiificultyName()).equalsIgnoreCase("Początkujący")){
                        createAchievement(result.getApuserId(),"Stylowy start");
                    }else if(String.valueOf(result.getDiificultyName()).equalsIgnoreCase("Średniozaawansowany")){
                        createAchievement(result.getApuserId(),"Oby tak dalej");
                    }else if(String.valueOf(result.getDiificultyName()).equalsIgnoreCase("Zaawansowany")){
                        createAchievement(result.getApuserId(),"Czarodziej");
                    }
                }else if(String.valueOf(result.getCategoryName()).equalsIgnoreCase("JavaScript")){
                    if(String.valueOf(result.getDiificultyName()).equalsIgnoreCase("Początkujący")){
                        createAchievement(result.getApuserId(),"Skryptowy start");
                    }else if(String.valueOf(result.getDiificultyName()).equalsIgnoreCase("Średniozaawansowany")){
                        createAchievement(result.getApuserId(),"Nie przestawaj");
                    }else if(String.valueOf(result.getDiificultyName()).equalsIgnoreCase("Zaawansowany")){
                        createAchievement(result.getApuserId(),"Świat jest twój");
                    }
                }
            }
       return new ResponseEntity<>(gson.toJson("result sent"), responseHeaders, HttpStatus.OK);
       }

    public void createAchievement(Long apuserId, String name){
        if(Objects.isNull(appuserAchievementRepository.findByIds(apuserId,name))){
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

    public FlashcardRepository getFlashcardRepository() {
        return flashcardRepository;
    }

    public void setFlashcardRepository(FlashcardRepository flashcardRepository) {
        this.flashcardRepository = flashcardRepository;
    }

    public ResultRepository getResultRepository() {
        return resultRepository;
    }

    public void setResultRepository(ResultRepository resultRepository) {
        this.resultRepository = resultRepository;
    }
}
