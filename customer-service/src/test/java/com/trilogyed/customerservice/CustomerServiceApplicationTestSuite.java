package com.trilogyed.customerservice;

import com.trilogyed.customerservice.dao.CustomerDaoJdbcTemplateImplTest;
import com.trilogyed.customerservice.service.ServiceLayerTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
      CustomerDaoJdbcTemplateImplTest.class,
      ServiceLayerTest.class
})
public class CustomerServiceApplicationTestSuite {
}
