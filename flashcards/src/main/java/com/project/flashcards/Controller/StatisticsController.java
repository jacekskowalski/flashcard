package com.project.flashcards.Controller;

import com.google.gson.Gson;
import com.project.flashcards.Repository.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

@RestController
public class StatisticsController {
    @Autowired
    private Flashcard_pointsRepository flashcardPointsRepository;
    @Autowired
    private ResultRepository resultRepository;
    @Autowired
    private StatisticsRepository statisticsRepository;
    @Autowired
    private AppuserRepository appuserRepository;
    @Autowired
    private TimeStatsRepository timeStatsRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    Gson gson = new Gson();

    @GetMapping("/statistics")
    @ApiOperation(value = "Returns time of completed courses and nr of discoverd flashcards")
    public ResponseEntity<?> getStatForUser(@RequestParam Long id) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        List<LinkedHashMap<String, String>> lista = new ArrayList<>();
        LinkedHashMap<String, String> temp = new LinkedHashMap<>();
        if (!appuserRepository.existsById(id)) {
            return new ResponseEntity<>(gson.toJson("Data not found"), responseHeaders, HttpStatus.UNPROCESSABLE_ENTITY);
        } else {
            temp.put("user",appuserRepository.namesurname(id));
              lista.add(temp);
            Double timeForAllCourses = resultRepository.getTimeForCompletedCourses(id);
            Double timeForHtmlCouse = resultRepository.getTimeForCompletedCourse(id, 1L);
            Double timeForCssCouse = resultRepository.getTimeForCompletedCourse(id, 2L);
            Double timeForJSCouse = resultRepository.getTimeForCompletedCourse(id, 3L);
            int coursesCompleted = resultRepository.countResultsByAppuserId(id).intValue();
            Long countAllDiscovered = flashcardPointsRepository.countByAppuserAndDiscovered(id, "yes");
            Long countDiscoveredHtml = flashcardPointsRepository.countByDiscoveredAndCategory("yes", 1L);
            Long countDiscoveredCss = flashcardPointsRepository.countByDiscoveredAndCategory("yes", 2L);
            Long countDiscoveredJs = flashcardPointsRepository.countByDiscoveredAndCategory("yes", 3L);
            temp.put("nr of all discovered flashcards", String.valueOf(countAllDiscovered));
            temp.put("nr of html discovered flashcards", String.valueOf(countDiscoveredHtml));
            temp.put("nr of css discovered flashcards", String.valueOf(countDiscoveredCss));
            temp.put("nr of js discovered flashcards", String.valueOf(countDiscoveredJs));
            temp.put("time of all courses",String.valueOf(timeForAllCourses));
            temp.put("time of html course",String.valueOf(timeForHtmlCouse));
            temp.put("time of css course", String.valueOf(timeForCssCouse));
            temp.put("time of js course", String.valueOf(timeForJSCouse));
            temp.put("number of login",String.valueOf(timeStatsRepository.getCountLogin(id)));
            temp.put("courses completed", String.valueOf(coursesCompleted));
            Iterator it = statisticsRepository.findAllByUser(id).iterator();
            while (it.hasNext()) {
                Object[] obj = (Object[]) it.next();
                LinkedHashMap<String, String> data = new LinkedHashMap<>();
                data.put("category", String.valueOf(obj[0]));
                data.put("difficulty", String.valueOf(obj[1]));
                data.put("score", String.valueOf(obj[2]));
                data.put("total", String.valueOf(obj[3]));
                lista.add(data);
            }
            return new ResponseEntity<>(lista, HttpStatus.OK);
        }
    }

    public ResultRepository getResultRepository() {
        return resultRepository;
    }

    public void setResultRepository(ResultRepository resultRepository) {
        this.resultRepository = resultRepository;
    }

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

    public TimeStatsRepository getTimeStatsRepository() {
        return timeStatsRepository;
    }

    public void setTimeStatsRepository(TimeStatsRepository timeStatsRepository) {
        this.timeStatsRepository = timeStatsRepository;
    }

    public Flashcard_pointsRepository getFlashcardPointsRepository() {
        return flashcardPointsRepository;
    }

    public void setFlashcardPointsRepository(Flashcard_pointsRepository flashcardPointsRepository) {
        this.flashcardPointsRepository = flashcardPointsRepository;
    }

    public CategoryRepository getCategoryRepository() {
        return categoryRepository;
    }

    public void setCategoryRepository(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
}
