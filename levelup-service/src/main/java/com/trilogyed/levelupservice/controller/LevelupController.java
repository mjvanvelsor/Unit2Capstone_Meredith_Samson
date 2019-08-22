package com.trilogyed.levelupservice.controller;

import com.trilogyed.levelupservice.exception.NotFoundException;
import com.trilogyed.levelupservice.model.LevelUp;
import com.trilogyed.levelupservice.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RefreshScope
@RequestMapping("/levelups")
public class LevelupController {

    @Autowired
    ServiceLayer service;

    @PostMapping
    @ResponseStatus(value = HttpStatus.OK)
    public LevelUp createLevelUp(@RequestBody @Valid LevelUp levelUp){
        return levelUp;
    }

    @GetMapping("/{levelUpId}")
    @ResponseStatus(value = HttpStatus.OK)
    public LevelUp findLevelUp(@PathVariable int levelUpId)
            throws NotFoundException{
        LevelUp levelUp = service.getLevelUp(levelUpId);
        if (levelUp == null){
            throw new NotFoundException("LevelUp could not be retrieved for id: " + levelUpId);
        }
        return levelUp;
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<LevelUp> findAllLevelUps(@PathVariable int levelUpId){
        List<LevelUp> levelUps = service.getAllLevelUps();
        return levelUps;
    }

    @PutMapping("/{levelUpId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void updateLevelUp(@PathVariable int levelUpId, @RequestBody @Valid LevelUp levelUp)
            throws IllegalArgumentException {
                if (levelUp.getLevelUpId() == 0) {
                    levelUp.setLevelUpId(levelUpId);
                } else {
                    throw new IllegalArgumentException("LevelUp id must match the id in the LevelUp object.");
                }
            service.amendLevelUp(levelUp);
    }


    @DeleteMapping("/{levelUpId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteLevelUp(@PathVariable int levelUpId){
        service.deleteLevelUp(levelUpId);
    }
}
