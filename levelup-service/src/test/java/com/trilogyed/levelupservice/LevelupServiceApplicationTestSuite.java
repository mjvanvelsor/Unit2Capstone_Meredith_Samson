package com.trilogyed.levelupservice;

import com.trilogyed.levelupservice.dao.LevelUpDaoJdbcTemplateImplTest;
import com.trilogyed.levelupservice.service.ServiceLayerTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
      LevelUpDaoJdbcTemplateImplTest.class,
      ServiceLayerTest.class
})
public class LevelupServiceApplicationTestSuite {
}
