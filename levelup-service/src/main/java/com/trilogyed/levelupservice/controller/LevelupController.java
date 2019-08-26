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
        levelUp = service.createLevelUp(levelUp);
        return levelUp;
    }

    @GetMapping("/{levelUpId}")
    @ResponseStatus(value = HttpStatus.OK)
    public LevelUp getLevelUp(@PathVariable int levelUpId)
            throws NotFoundException{
        LevelUp levelUp = service.getLevelUp(levelUpId);
        if (levelUp == null){
            throw new NotFoundException("LevelUp could not be retrieved for id: " + levelUpId);
        }
        return levelUp;
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<LevelUp> getAllLevelUp(){
        List<LevelUp> levelUps = service.getAllLevelUps();
        return levelUps;
    }
    
    @GetMapping("/customer/{customerId}")
    @ResponseStatus(value = HttpStatus.OK)
    public LevelUp getLevelUpByCustomer(@PathVariable int customerId) {
        return service.getLevelUpByCustomer(customerId);
    }

    @PutMapping
    @ResponseStatus(value = HttpStatus.OK)
    public void amendLevelUp(@RequestBody @Valid LevelUp levelUp) {
            service.amendLevelUp(levelUp);
    }


    @DeleteMapping("/{levelUpId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteLevelUp(@PathVariable int levelUpId){
        service.deleteLevelUp(levelUpId);
    }
}
