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
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class InvoiceDaoJdbcTemplateImplTest {

    @Autowired
    InvoiceDao dao;

    @Before
    public void setUp() throws Exception {
        List<Invoice> invoices = dao.getAllInvoices();
        invoices.stream().forEach(invoice -> dao.deleteInvoice(invoice.getInvoiceId()));
    }

    @Test
    public void createGetDeleteInvoice() {
        InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setId(1);
        invoiceItem.setInvoiceId(2);
        invoiceItem.setInventory_id(3);
        invoiceItem.setQuantity(4);
        invoiceItem.setUnitPrice(new BigDecimal(19.99));

        List<InvoiceItem> invoiceItemList = new ArrayList<>();

        Invoice invoice = new Invoice();
        invoice.setCustomerId(1);
        invoice.setCustomerId(12345);
        invoice.setPurchaseDate(LocalDate.of(2019, 8 , 20));
        invoice.setInvoiceItems(invoiceItemList);

        dao.createInvoice(invoice);
        assertEquals(invoice, dao.getInvoice(invoice.getInvoiceId()));
        dao.deleteInvoice(invoice.getInvoiceId());
        assertNull(dao.getInvoice(invoice.getInvoiceId()));
    }

    @Test
    public void getAllInvoices() {
        InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setId(1);
        invoiceItem.setInvoiceId(2);
        invoiceItem.setInventory_id(3);
        invoiceItem.setQuantity(4);
        invoiceItem.setUnitPrice(new BigDecimal(19.99));

        List<InvoiceItem> invoiceItemList = new ArrayList<>();

        Invoice invoice = new Invoice();
        invoice.setCustomerId(1);
        invoice.setCustomerId(12345);
        invoice.setPurchaseDate(LocalDate.of(2019, 8 , 20));
        invoice.setInvoiceItems(invoiceItemList);
        dao.createInvoice(invoice);
        Invoice invoice1 = new Invoice();
        invoice1.setCustomerId(2);
        invoice1.setCustomerId(23456);
        invoice1.setPurchaseDate(LocalDate.of(2019, 9 , 20));
        invoice.setInvoiceItems(invoiceItemList);
        dao.createInvoice(invoice1);
        List<Invoice> invoices = dao.getAllInvoices();
        assertEquals(invoices.size(), 2);
    }

    @Test
    public void amendInvoice() {
        InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setId(1);
        invoiceItem.setInvoiceId(2);
        invoiceItem.setInventory_id(3);
        invoiceItem.setQuantity(4);
        invoiceItem.setUnitPrice(new BigDecimal(19.99));

        List<InvoiceItem> invoiceItemList = new ArrayList<>();

        Invoice invoice = new Invoice();
        invoice.setCustomerId(1);
        invoice.setCustomerId(12345);
        invoice.setPurchaseDate(LocalDate.of(2019, 8 , 20));
        invoice.setInvoiceItems(invoiceItemList);
        dao.createInvoice(invoice);
        invoice.setCustomerId(56789);
        dao.amendInvoice(invoice);

        assertEquals(invoice, dao.getInvoice(invoice.getInvoiceId()));



    }
}