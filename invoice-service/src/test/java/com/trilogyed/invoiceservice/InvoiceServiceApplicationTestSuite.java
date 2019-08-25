package com.trilogyed.invoiceservice;

import com.trilogyed.invoiceservice.dao.InvoiceDaoJdbcTemplateImplTest;
import com.trilogyed.invoiceservice.dao.InvoiceItemDaoJdbcTemplateImplTest;
import com.trilogyed.invoiceservice.service.ServiceLayerTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
      InvoiceDaoJdbcTemplateImplTest.class,
      InvoiceItemDaoJdbcTemplateImplTest.class,
      ServiceLayerTest.class
})
public class InvoiceServiceApplicationTestSuite {
}
