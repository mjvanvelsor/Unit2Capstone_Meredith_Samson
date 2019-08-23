package com.trilogyed.productservice;

import com.trilogyed.productservice.dao.ProductDaoJdbcTemplateImplTest;
import com.trilogyed.productservice.service.ServiceLayerTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
      ProductDaoJdbcTemplateImplTest.class,
      ServiceLayerTest.class
})
public class ProductServiceApplicationTestSuite {
}
