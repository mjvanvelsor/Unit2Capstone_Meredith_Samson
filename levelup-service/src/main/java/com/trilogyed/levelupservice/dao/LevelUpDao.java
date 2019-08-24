package com.trilogyed.levelupservice.dao;

import com.trilogyed.levelupservice.model.LevelUp;
import java.util.List;

public interface LevelUpDao {
    public LevelUp createLevelUp(LevelUp levelUp);
    public LevelUp getLevelUp(int levelUpId);
    public LevelUp getLevelUpByCustomer(int customerId);
    public void amendLevelUpByCustomer(LevelUp levelUp);
    public List<LevelUp> getAllLevelUps();
    public void amendLevelUp(LevelUp levelUp);
    public void deleteLevelUp(int levelUpId);
}
