package com.trilogyed.invoiceservice.service;

import com.trilogyed.invoiceservice.dao.InvoiceDao;
import com.trilogyed.invoiceservice.dao.InvoiceDaoJdbcTemplateImpl;
import com.trilogyed.invoiceservice.model.Invoice;
import com.trilogyed.invoiceservice.model.InvoiceItem;
import com.trilogyed.invoiceservice.model.InvoiceViewModel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.mockito.Mockito.*;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceLayerTest {

//    InvoiceDao invoiceDao;
//    ServiceLayer service;
//
//    @Before
//    public void setUp() throws Exception {
//        setUpInvoiceDaoMock();
//        service = new ServiceLayer(invoiceDao);
//    }
//
//    private void setUpInvoiceDaoMock(){
//        invoiceDao = mock(InvoiceDaoJdbcTemplateImpl.class);
//        InvoiceItem invoiceItem = new InvoiceItem();
//        invoiceItem.setInvoiceItemId(1);
//        invoiceItem.setInvoiceId(2);
//        invoiceItem.setInventoryId(3);
//        invoiceItem.setQuantity(4);
//        invoiceItem.setUnitPrice(new BigDecimal(19.99));
//
//        List<InvoiceItem> invoiceItemList = new ArrayList<>();
//
//        Invoice invoice = new Invoice();
//        invoice.setCustomerId(12345);
//        invoice.setPurchaseDate(LocalDate.of(2019, 8 , 20));
////        invoice.setInvoiceItems(invoiceItemList);
//
//        Invoice invoice1 = new Invoice();
//        invoice1.setCustomerId(1);
//        invoice1.setCustomerId(12345);
//        invoice1.setPurchaseDate(LocalDate.of(2019, 8 , 20));
////        invoice1.setInvoiceItems(invoiceItemList);
//
//        List<Invoice> invoices = new ArrayList<>();
//        invoices.add(invoice1);
//
//        doReturn(invoice1).when(invoiceDao).createInvoice(invoice);
//        doReturn(invoice1).when(invoiceDao).getInvoice(1);
//        doReturn(invoices).when(invoiceDao).getAllInvoices();
//    }
//
//    @Test
//    public void createInvoice() {
////        InvoiceItem invoiceItem = new InvoiceItem();
////        invoiceItem.setInvoiceItemId(1);
////        invoiceItem.setInvoiceId(2);
////        invoiceItem.setInventoryId(3);
////        invoiceItem.setQuantity(4);
////        invoiceItem.setUnitPrice(new BigDecimal(19.99));
////
////        List<InvoiceItem> invoiceItemList = new ArrayList<>();
//
//        Invoice invoice = new Invoice();
//        invoice.setCustomerId(12345);
//        invoice.setPurchaseDate(LocalDate.of(2019, 8 , 20));
//        InvoiceViewModel ivm = service.createInvoice(invoice);
////        invoice.setInvoiceItems(invoiceItemList);
//
//        Invoice invoice1 = service.createInvoice(invoice);
//        Invoice invoice2 = service.getInvoice(invoice1.getInvoiceId());
//
//        assertEquals(invoice1, invoice2);
//    }

//    @Test
//    public void getAllInvoices() {
//        List<Invoice> invoices = service.getAllInvoices();
//        assertEquals(invoices.size(), 1);
//    }
//
//    @Test
//    public void amendInvoice() {
//        InvoiceItem invoiceItem = new InvoiceItem();
//        invoiceItem.setInvoiceItemId(1);
//        invoiceItem.setInvoiceId(2);
//        invoiceItem.setInventoryId(3);
//        invoiceItem.setQuantity(4);
//        invoiceItem.setUnitPrice(new BigDecimal(19.99));
//
//        List<InvoiceItem> invoiceItemList = new ArrayList<>();
//
//        Invoice invoice = new Invoice();
//        invoice.setCustomerId(54321);
//        invoice.setPurchaseDate(LocalDate.of(2019, 8 , 20));
//        invoice.setInvoiceItems(invoiceItemList);
//
//        service.amendInvoice(invoice);
//
//        ArgumentCaptor<Invoice> invoiceCaptor = ArgumentCaptor.forClass(Invoice.class);
//        verify(invoiceDao).amendInvoice(invoiceCaptor.capture());
//        assertEquals(invoice.getCustomerId(), invoiceCaptor.getValue().getCustomerId());
//    }
//
//    @Test
//    public void deleteInvoice() {
//        service.deleteInvoice(1);
//        Invoice invoice = service.getInvoice(1);
//
//        ArgumentCaptor<Integer> invoiceCaptor = ArgumentCaptor.forClass(Integer.class);
//        verify(invoiceDao).deleteInvoice(invoiceCaptor.capture());
//        assertEquals(invoice.getCustomerId(), invoiceCaptor.getValue().intValue());
//    }
}