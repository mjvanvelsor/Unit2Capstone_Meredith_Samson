package com.trilogyed.levelupservice.service;

import com.trilogyed.levelupservice.dao.LevelUpDao;
import com.trilogyed.levelupservice.model.LevelUp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ServiceLayer {

    LevelUpDao dao;
    @Autowired
    public ServiceLayer(LevelUpDao levelUpDao) {
        this.dao = levelUpDao;
    }

    public LevelUp createLevelUp(LevelUp levelUp){
        return dao.createLevelUp(levelUp);
    }
    public LevelUp getLevelUp(int levelUpId){
        return dao.getLevelUp(levelUpId);
    }
    public List<LevelUp> getAllLevelUps(){
        return dao.getAllLevelUps();
    }
    public void amendLevelUp(LevelUp levelUp){
        dao.amendLevelUp(levelUp);
    }
    public void deleteLevelUp(int levelUpId){
        dao.deleteLevelUp(levelUpId);
    }
}
