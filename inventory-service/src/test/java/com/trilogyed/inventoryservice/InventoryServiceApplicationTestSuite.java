package com.trilogyed.inventoryservice;

import com.trilogyed.inventoryservice.dao.InventoryDaoJdbcTemplateImplTest;
import com.trilogyed.inventoryservice.service.ServiceLayerTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
      InventoryDaoJdbcTemplateImplTest.class,
      ServiceLayerTest.class
})
public class InventoryServiceApplicationTestSuite {
}
