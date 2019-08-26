package com.trilogyed.invoiceservice.dao;

import com.trilogyed.invoiceservice.model.Invoice;
import com.trilogyed.invoiceservice.model.InvoiceItem;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class InvoiceDaoJdbcTemplateImplTest {

    @Autowired
    InvoiceDao invoiceDao;
    @Autowired
    InvoiceItemDao invoiceItemDao;

    @Before
    public void setUp() throws Exception {
        invoiceItemDao.getAllInvoiceItems()
              .stream()
              .forEach(invoiceItem -> invoiceItemDao.deleteInvoiceItem(invoiceItem.getInvoiceItemId()));
        invoiceDao.getAllInvoices()
              .stream()
              .forEach(invoice -> invoiceDao.deleteInvoice(invoice.getInvoiceId()));
    }

    @Test
    public void createGetDeleteInvoice() {
        Invoice invoice = new Invoice();
        invoice.setCustomerId(12345);
        invoice.setPurchaseDate(LocalDate.of(2019, 8 , 20));
        invoice = invoiceDao.createInvoice(invoice);
        
        assertEquals(invoice, invoiceDao.getInvoice(invoice.getInvoiceId()));
        
        invoiceDao.deleteInvoice(invoice.getInvoiceId());
        assertNull(invoiceDao.getInvoice(invoice.getInvoiceId()));
    }

    @Test
    public void getAllInvoices() {
        Invoice invoice = new Invoice();
        invoice.setCustomerId(12345);
        invoice.setPurchaseDate(LocalDate.of(2019, 8 , 20));
        invoice = invoiceDao.createInvoice(invoice);
        
        Invoice invoice1 = new Invoice();
        invoice1.setCustomerId(23456);
        invoice1.setPurchaseDate(LocalDate.of(2019, 9 , 20));
        invoice1 = invoiceDao.createInvoice(invoice1);
        
        List<Invoice> invoices = invoiceDao.getAllInvoices();
        assertEquals(2, invoices.size());
    }
    
    @Test
    public void getInvoicesByCustomerId() {
        Invoice invoice = new Invoice();
        invoice.setCustomerId(12345);
        invoice.setPurchaseDate(LocalDate.of(2019, 8 , 20));
        invoice = invoiceDao.createInvoice(invoice);
        
        Invoice invoice1 = new Invoice();
        invoice1.setCustomerId(23456);
        invoice1.setPurchaseDate(LocalDate.of(2019, 9 , 20));
        invoice1 = invoiceDao.createInvoice(invoice1);
        
        List<Invoice> invoices = invoiceDao.getAllInvoices();
        assertEquals(2, invoices.size());
    }

    @Test
    public void amendInvoice() {
        Invoice invoice = new Invoice();
        invoice.setCustomerId(12345);
        invoice.setPurchaseDate(LocalDate.of(2019, 8 , 20));
        invoice = invoiceDao.createInvoice(invoice);

        invoice.setCustomerId(56789);
        invoiceDao.amendInvoice(invoice);

        assertEquals(invoice, invoiceDao.getInvoice(invoice.getInvoiceId()));
    }
}