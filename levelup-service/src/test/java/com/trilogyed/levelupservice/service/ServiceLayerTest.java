package com.trilogyed.levelupservice.service;

import com.trilogyed.levelupservice.dao.LevelUpDao;
import com.trilogyed.levelupservice.dao.LevelUpDaoJdbcTemplateImpl;
import com.trilogyed.levelupservice.model.LevelUp;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceLayerTest {

    LevelUpDao levelUpDao;
    ServiceLayer service;

    @Before
    public void setUp() throws Exception {
        setupLevelUpDaoMock();
        service = new ServiceLayer(levelUpDao);
    }

    private void setupLevelUpDaoMock(){
        levelUpDao = mock(LevelUpDaoJdbcTemplateImpl.class);
        LevelUp levelUp = new LevelUp();
        levelUp.setCustomerId(34567);
        levelUp.setPoints(150);
        levelUp.setMemberDate(LocalDate.of(2019,8,20));

        LevelUp levelUp1 = new LevelUp();
        levelUp1.setLevelUpId(1);
        levelUp1.setCustomerId(34567);
        levelUp1.setPoints(150);
        levelUp1.setMemberDate(LocalDate.of(2019,8,20));

        List<LevelUp> levelUps = new ArrayList<>();
        levelUps.add(levelUp1);

        doReturn(levelUp1).when(levelUpDao).createLevelUp(levelUp);
        doReturn(levelUp1).when(levelUpDao).getLevelUp(1);
        doReturn(levelUp1).when(levelUpDao).getLevelUpByCustomer(34567);
        doReturn(levelUps).when(levelUpDao).getAllLevelUps();
    }

    @Test
    public void createNewGetLevelUp() {
        LevelUp levelUp = new LevelUp();
        levelUp.setCustomerId(34567);
        levelUp.setPoints(150);
        levelUp.setMemberDate(LocalDate.of(2019,8,20));

        LevelUp item = service.createLevelUp(levelUp);
        LevelUp item2 = service.getLevelUp(item.getLevelUpId());
        assertEquals(item, item2);
    }
    
    @Test
    public void createUpdateExistingLevelUp() {
        LevelUp levelUp = new LevelUp();
        levelUp.setCustomerId(34567);
        levelUp.setPoints(150);
        levelUp.setMemberDate(LocalDate.of(2019,8,20));
        
        LevelUp item = service.createLevelUp(levelUp);
        LevelUp item2 = service.getLevelUp(item.getLevelUpId());
        assertEquals(item, item2);
    }

    @Test
    public void getAllLevelUps() {
        List<LevelUp> levelUps = service.getAllLevelUps();
        assertEquals(levelUps.size(), 1);
    }

    @Test
    public void amendLevelUp() {
        LevelUp levelUp = new LevelUp();
        levelUp.setCustomerId(34567);
        levelUp.setPoints(200);
        levelUp.setMemberDate(LocalDate.of(2019,8,20));
        service.amendLevelUp(levelUp);

        ArgumentCaptor<LevelUp> levelUpCaptor = ArgumentCaptor.forClass(LevelUp.class);
        verify(levelUpDao).amendLevelUp(levelUpCaptor.capture());
        assertEquals(levelUp.getPoints(), levelUpCaptor.getValue().getPoints());
    }

    @Test
    public void deleteLevelUp() {
        service.deleteLevelUp(1);
        LevelUp levelUp = service.getLevelUp(1);
    }
}