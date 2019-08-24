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
public class InvoiceItemDaoJdbcTemplateImplTest {
    @Autowired
    InvoiceItemDao invoiceItemDao;
    @Autowired
    InvoiceDao invoiceDao;

    @Before
    public void setUp() throws Exception {
        List<InvoiceItem> invoiceItems = invoiceItemDao.getAllInvoiceItems();
        invoiceItems.stream().forEach(invoiceItem -> invoiceItemDao.deleteInvoiceItem(invoiceItem.getInvoiceItemId()));
        List<Invoice> invoices = invoiceDao.getAllInvoices();
        invoices.stream().forEach(invoice -> invoiceDao.deleteInvoice(invoice.getInvoiceId()));
    }

    @Test
    public void createGetDeleteInvoiceItem() {
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

        assertEquals(invoiceItem, invoiceItemDao.getInvoiceItem(invoiceItem.getInvoiceItemId()));
        invoiceItemDao.deleteInvoiceItem(invoiceItem.getInvoiceItemId());
        invoiceDao.deleteInvoice(invoice.getInvoiceId());
        assertNull(invoiceItemDao.getInvoiceItem(invoiceItem.getInvoiceItemId()));
    }

    @Test
    public void getAllInvoiceItems() {
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

        Invoice invoice1 = new Invoice();
        invoice1.setCustomerId(1);
        invoice1.setCustomerId(12345);
        invoice1.setPurchaseDate(LocalDate.of(2019, 8 , 20));
        invoiceDao.createInvoice(invoice);

        InvoiceItem invoiceItem1 = new InvoiceItem();
//        invoiceItem1.setInvoiceItemId(2);
        invoiceItem1.setInvoiceId(invoice1.getInvoiceId());
        invoiceItem1.setInventoryId(4);
        invoiceItem1.setQuantity(5);
        invoiceItem1.setUnitPrice(new BigDecimal(19.99).setScale(2, BigDecimal.ROUND_HALF_UP));
        invoiceItemDao.createInvoiceItem(invoiceItem1);

        List<InvoiceItem> invoiceItemList = invoiceItemDao.getAllInvoiceItems();
        assertEquals(invoiceItemList.size(), 2);
        List<InvoiceItem> invoiceItemListById = invoiceItemDao.getAllInvoiceItemsByInvoiceId(2);
        assertEquals(invoiceItemListById.size(), 1);
    }

    @Test
    public void amendInvoiceItem() {
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

        invoiceItem.setQuantity(10);
        invoiceItemDao.amendInvoiceItem(invoiceItem);

        assertEquals(invoiceItem, invoiceItemDao.getInvoiceItem(invoiceItem.getInvoiceItemId()));
    }

}