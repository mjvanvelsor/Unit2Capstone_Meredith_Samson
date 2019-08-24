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
        List<InvoiceItem> invoiceItems = invoiceItemDao.getAllInvoiceItems();
        invoiceItems.stream().forEach(invoiceItem -> invoiceItemDao.deleteInvoiceItem(invoiceItem.getInvoiceItemId()));
        List<Invoice> invoices = invoiceDao.getAllInvoices();
        invoices.stream().forEach(invoice -> invoiceDao.deleteInvoice(invoice.getInvoiceId()));
    }

    @Test
    public void createGetDeleteInvoice() {
        Invoice invoice = new Invoice();
        invoice.setCustomerId(1);
        invoice.setCustomerId(12345);
        invoice.setPurchaseDate(LocalDate.of(2019, 8 , 20));
        invoiceDao.createInvoice(invoice);

        InvoiceItem invoiceItem = new InvoiceItem();
//        invoiceItem.setInvoiceItemId(1);
        invoiceItem.setInvoiceId(invoice.getInvoiceId());
        invoiceItem.setInventoryId(3);
        invoiceItem.setQuantity(4);
        invoiceItem.setUnitPrice(new BigDecimal(19.99).setScale(2, BigDecimal.ROUND_HALF_UP));
        invoiceItemDao.createInvoiceItem(invoiceItem);

        assertEquals(invoice, invoiceDao.getInvoice(invoice.getInvoiceId()));
        invoiceItemDao.deleteInvoiceItem(invoiceItem.getInvoiceItemId());
        invoiceDao.deleteInvoice(invoice.getInvoiceId());
        assertNull(invoiceDao.getInvoice(invoice.getInvoiceId()));
    }

    @Test
    public void getAllInvoices() {
        Invoice invoice = new Invoice();
        invoice.setCustomerId(1);
        invoice.setCustomerId(12345);
        invoice.setPurchaseDate(LocalDate.of(2019, 8 , 20));
        invoiceDao.createInvoice(invoice);
        Invoice invoice1 = new Invoice();
        invoice1.setCustomerId(2);
        invoice1.setCustomerId(23456);
        invoice1.setPurchaseDate(LocalDate.of(2019, 9 , 20));
        invoiceDao.createInvoice(invoice1);
        List<Invoice> invoices = invoiceDao.getAllInvoices();
        assertEquals(invoices.size(), 2);
    }

    @Test
    public void amendInvoice() {
        Invoice invoice = new Invoice();
        invoice.setCustomerId(1);
        invoice.setCustomerId(12345);
        invoice.setPurchaseDate(LocalDate.of(2019, 8 , 20));
        invoiceDao.createInvoice(invoice);

        invoice.setCustomerId(56789);
        invoiceDao.amendInvoice(invoice);

        assertEquals(invoice, invoiceDao.getInvoice(invoice.getInvoiceId()));
    }
}