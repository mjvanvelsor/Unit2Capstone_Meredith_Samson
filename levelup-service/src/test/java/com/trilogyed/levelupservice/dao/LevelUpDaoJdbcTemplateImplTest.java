package com.trilogyed.levelupservice.dao;

import com.trilogyed.levelupservice.model.LevelUp;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LevelUpDaoJdbcTemplateImplTest {

    @Autowired
    LevelUpDao dao;

    @Before
    public void setUp() throws Exception {
        List<LevelUp> levelUps = dao.getAllLevelUps();
        levelUps.stream().forEach(levelUp -> dao.deleteLevelUp(levelUp.getLevelUpId()));
    }

    @Test
    public void createGetDeleteLevelUp() {
        LevelUp levelUp = new LevelUp();
        levelUp.setLevelUpId(1);
        levelUp.setCustomerId(34567);
        levelUp.setPoints(150);
        levelUp.setMemberDate(LocalDate.of(2019,8,20));

        dao.createLevelUp(levelUp);
        assertEquals(levelUp, dao.getLevelUp(levelUp.getLevelUpId()));
        dao.deleteLevelUp(levelUp.getLevelUpId());
        assertNull(dao.getLevelUp(levelUp.getLevelUpId()));
    }

    @Test
    public void getAllLevelUps() {
        LevelUp levelUp = new LevelUp();
        levelUp.setLevelUpId(1);
        levelUp.setCustomerId(34567);
        levelUp.setPoints(150);
        levelUp.setMemberDate(LocalDate.of(2019,8,20));
        dao.createLevelUp(levelUp);
        LevelUp levelUp1 = new LevelUp();
        levelUp1.setLevelUpId(1);
        levelUp1.setCustomerId(34567);
        levelUp1.setPoints(150);
        levelUp1.setMemberDate(LocalDate.of(2019,8,20));
        dao.createLevelUp(levelUp1);

        List<LevelUp> levelUps = dao.getAllLevelUps();
        assertEquals(levelUps.size(), 2);
    }

    @Test
    public void amendLevelUp() {
        LevelUp levelUp = new LevelUp();
        levelUp.setLevelUpId(1);
        levelUp.setCustomerId(34567);
        levelUp.setPoints(150);
        levelUp.setMemberDate(LocalDate.of(2019,8,20));
        dao.createLevelUp(levelUp);

        levelUp.setPoints(200);
        dao.amendLevelUp(levelUp);

        assertEquals(levelUp, dao.getLevelUp(levelUp.getLevelUpId()));
    }
}