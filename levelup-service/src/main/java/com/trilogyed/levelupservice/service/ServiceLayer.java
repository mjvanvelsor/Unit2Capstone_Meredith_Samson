package com.trilogyed.levelupservice.service;

import com.trilogyed.levelupservice.dao.LevelUpDao;
import com.trilogyed.levelupservice.model.LevelUp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ServiceLayer {

    LevelUpDao dao;
    @Autowired
    public ServiceLayer(LevelUpDao levelUpDao) {
        this.dao = levelUpDao;
    }

    public LevelUp createLevelUp(LevelUp levelUp){
        Optional<LevelUp> optionalLevelUp = Optional.ofNullable(
              this.dao.getLevelUpByCustomer(levelUp.getCustomerId()));
        
        if (optionalLevelUp.isPresent()) {
            LevelUp oldLevelUp = optionalLevelUp.get();
            oldLevelUp.setPoints(oldLevelUp.getPoints() + levelUp.getPoints());
            oldLevelUp.setMemberDate(levelUp.getMemberDate());
            dao.amendLevelUpByCustomer(oldLevelUp);
            return oldLevelUp;
        }
        else {
            return dao.createLevelUp(levelUp);
        }
    }
    
    public LevelUp getLevelUp(int levelUpId){
        return dao.getLevelUp(levelUpId);
    }
    
    public LevelUp getLevelUpByCustomer(int customerId){
        return dao.getLevelUpByCustomer(customerId);
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
