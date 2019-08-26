package com.trilogyed.invoiceservice.service;

import com.trilogyed.invoiceservice.ViewModel.InvoiceViewModel;
import com.trilogyed.invoiceservice.dao.InvoiceDao;
import com.trilogyed.invoiceservice.dao.InvoiceItemDao;
import com.trilogyed.invoiceservice.model.Invoice;
import com.trilogyed.invoiceservice.model.InvoiceItem;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(MockitoJUnitRunner.class)
public class ServiceLayerTest {
   
   @Mock
   private InvoiceDao invoiceDao;
   @Mock
   private InvoiceItemDao invoiceItemDao;
   
   @InjectMocks
   private ServiceLayer service;
   
   @Before
   public void setUp() throws Exception {
      initMocks(this);
      setUpInvoiceDaoMock();
      setUpInvoiceItemDaoMock();
      service = new ServiceLayer(invoiceDao, invoiceItemDao);
   }
   
   private void setUpInvoiceDaoMock() {
      Invoice inv1A = new Invoice();
      inv1A.setCustomerId(45);
      inv1A.setPurchaseDate(LocalDate.of(2019, 8 , 20));
      
      Invoice inv1B = new Invoice();
      inv1B.setInvoiceId(1);
      inv1B.setCustomerId(45);
      inv1B.setPurchaseDate(LocalDate.of(2019, 8 , 20));
      List<Invoice> allInvoices = new ArrayList<>();
      allInvoices.add(inv1B);
            
      Mockito.doReturn(inv1B).when(invoiceDao).createInvoice(inv1A);
      Mockito.doReturn(inv1B).when(invoiceDao).getInvoice(1);
      Mockito.doReturn(allInvoices).when(invoiceDao).getAllInvoices();
      Mockito.doReturn(allInvoices).when(invoiceDao).getInvoicesByCustomer(45);
      
   }
   
   private void setUpInvoiceItemDaoMock() {
      
      InvoiceItem invItm1A = new InvoiceItem();
      invItm1A.setInvoiceId(1);
      invItm1A.setInventoryId(1);
      invItm1A.setQuantity(4);
      invItm1A.setUnitPrice(new BigDecimal(19.99).setScale(2, RoundingMode.HALF_EVEN));
   
      InvoiceItem invItm1B = new InvoiceItem();
      invItm1B.setInvoiceItemId(1);
      invItm1B.setInvoiceId(1);
      invItm1B.setInventoryId(1);
      invItm1B.setQuantity(4);
      invItm1B.setUnitPrice(new BigDecimal(19.99).setScale(2, RoundingMode.HALF_EVEN));
   
      InvoiceItem invItm1C = new InvoiceItem();
      invItm1C.setInvoiceId(1);
      invItm1C.setInventoryId(2);
      invItm1C.setQuantity(20);
      invItm1C.setUnitPrice(new BigDecimal(50.99).setScale(2, RoundingMode.HALF_EVEN));
   
      InvoiceItem invItm1D = new InvoiceItem();
      invItm1D.setInvoiceItemId(2);
      invItm1D.setInvoiceId(1);
      invItm1D.setInventoryId(2);
      invItm1D.setQuantity(20);
      invItm1D.setUnitPrice(new BigDecimal(50.99).setScale(2, RoundingMode.HALF_EVEN));
      
      Mockito.doReturn(invItm1B).when(invoiceItemDao).createInvoiceItem(invItm1A);
//      Mockito.doReturn(invItm1B).when(invoiceItemDao).getInvoiceItem(1);
      Mockito.doReturn(invItm1D).when(invoiceItemDao).createInvoiceItem(invItm1C);
//      Mockito.doReturn(invItm1D).when(invoiceItemDao).getInvoiceItem(2);
      
      List<InvoiceItem> invItmList = new ArrayList<>();
      invItmList.add(invItm1B);
      invItmList.add(invItm1D);
//      Mockito.doReturn(invItmList).when(invoiceItemDao).getAllInvoiceItems();
      Mockito.doReturn(invItmList).when(invoiceItemDao).getAllInvoiceItemsByInvoiceId(1);

   }
   
   @Test
   public void createGetInvoice() {
      // create invoice header
      Invoice inv1A = new Invoice();
      inv1A.setCustomerId(45);
      inv1A.setPurchaseDate(LocalDate.of(2019, 8 , 20));
   
      // create invoice items
      InvoiceItem invItm1A = new InvoiceItem();
      invItm1A.setInventoryId(1);
      invItm1A.setQuantity(4);
      invItm1A.setUnitPrice(new BigDecimal(19.99).setScale(2, RoundingMode.HALF_EVEN));
   
      InvoiceItem invItm1C = new InvoiceItem();
      invItm1C.setInventoryId(2);
      invItm1C.setQuantity(20);
      invItm1C.setUnitPrice(new BigDecimal(50.99).setScale(2, RoundingMode.HALF_EVEN));
   
      List<InvoiceItem> invItmList = new ArrayList<>();
      invItmList.add(invItm1A);
      invItmList.add(invItm1C);
      
      // Create invoice with header and items
      InvoiceViewModel ivm = new InvoiceViewModel();
      ivm.setCustomerId(inv1A.getCustomerId());
      ivm.setPurchaseDate(inv1A.getPurchaseDate());
      ivm.setInvoiceItems(invItmList);
      
      // send invoice to service
      ivm = service.createInvoice(ivm);
      
      // get invoice
      InvoiceViewModel ivm1 = service.getInvoice(ivm.getInvoiceId());
      
      // Assert
      assertEquals(ivm, ivm1);
   }
   
   @Test
   public void getAllInvoices() {
      List<InvoiceViewModel> invoices = service.getAllInvoices();
      assertEquals(1, invoices.size());
   }
   
   @Test
   public void getInvoicesByCustomer() {
      List<InvoiceViewModel> invoices = service.getInvoicesByCustomer(45);
      assertEquals(1, invoices.size());
   }
   
   @Test
   public void amendInvoice() {
   
      // create invoice items
      InvoiceItem invItm1A = new InvoiceItem();
      invItm1A.setInventoryId(1);
      invItm1A.setQuantity(4);
      invItm1A.setUnitPrice(new BigDecimal(19.99).setScale(2, RoundingMode.HALF_EVEN));
   
      InvoiceItem invItm1C = new InvoiceItem();
      invItm1C.setInventoryId(2);
      invItm1C.setQuantity(20);
      invItm1C.setUnitPrice(new BigDecimal(50.99).setScale(2, RoundingMode.HALF_EVEN));
   
      List<InvoiceItem> invItmList = new ArrayList<>();
      invItmList.add(invItm1A);
      invItmList.add(invItm1C);
   
      // Create invoice with header and items
      InvoiceViewModel ivm = new InvoiceViewModel();
      ivm.setCustomerId(45);
      ivm.setPurchaseDate(LocalDate.of(2019, 8 , 20));
      ivm.setInvoiceItems(invItmList);
   
      // send invoice to service
      ivm = service.createInvoice(ivm);
      
      // Amend the invoice
      ivm.setCustomerId(45);
      ivm.setPurchaseDate(LocalDate.of(2019, 8 , 29));
   
      List<InvoiceItem> invItmList2 = ivm.getInvoiceItems();
   
      invItmList2.get(0).setInventoryId(6);
      invItmList2.get(0).setQuantity(50);
      invItmList2.get(0).setUnitPrice(new BigDecimal(80.99).setScale(2, RoundingMode.HALF_EVEN));
   
      invItmList2.get(1).setInventoryId(4);
      invItmList2.get(1).setQuantity(60);
      invItmList2.get(1).setUnitPrice(new BigDecimal(90.99).setScale(2, RoundingMode.HALF_EVEN));
      
     // ivm.getInvoiceItems().clear();
      
      List<InvoiceItem> invoiceItems = new ArrayList<>();
      invoiceItems.add(invItmList2.get(0));
      invoiceItems.add(invItmList2.get(1));
      
      ivm.setInvoiceItems(invoiceItems);
   
      service.amendInvoice(ivm);

      ArgumentCaptor<Invoice> invoiceCaptor = ArgumentCaptor.forClass(Invoice.class);
      verify(invoiceDao).amendInvoice(invoiceCaptor.capture());
      assertEquals(ivm.getCustomerId(), invoiceCaptor.getValue().getCustomerId());
      
   }
   
   @Test
   public void deleteInvoice() {
      // create invoice header
      Invoice inv1A = new Invoice();
      inv1A.setCustomerId(45);
      inv1A.setPurchaseDate(LocalDate.of(2019, 8 , 20));

      // create invoice items
      InvoiceItem invItm1A = new InvoiceItem();
      invItm1A.setInventoryId(1);
      invItm1A.setQuantity(4);
      invItm1A.setUnitPrice(new BigDecimal(19.99).setScale(2, RoundingMode.HALF_EVEN));

      InvoiceItem invItm1C = new InvoiceItem();
      invItm1C.setInventoryId(2);
      invItm1C.setQuantity(20);
      invItm1C.setUnitPrice(new BigDecimal(50.99).setScale(2, RoundingMode.HALF_EVEN));

      List<InvoiceItem> invItmList = new ArrayList<>();
      invItmList.add(invItm1A);
      invItmList.add(invItm1C);

      // Create invoice with header and items
      InvoiceViewModel ivm = new InvoiceViewModel();
      ivm.setCustomerId(inv1A.getCustomerId());
      ivm.setPurchaseDate(inv1A.getPurchaseDate());
      ivm.setInvoiceItems(invItmList);

      // send invoice to service
      ivm = service.createInvoice(ivm);

      // Delete invoice
      service.deleteInvoice(ivm.getInvoiceId());
   
      ArgumentCaptor<Integer> invoiceCaptor = ArgumentCaptor.forClass(Integer.class);
      verify(invoiceDao).deleteInvoice(invoiceCaptor.capture());
      assertEquals(ivm.getInvoiceId(), invoiceCaptor.getValue().intValue());
   
   
   }
//

//
//   @Test
//   public void deleteInvoice() {
//      service.deleteInvoice(1);
//      Invoice invoice = service.getInvoice(1);
//
//      ArgumentCaptor<Integer> invoiceCaptor = ArgumentCaptor.forClass(Integer.class);
//      verify(invoiceDao).deleteInvoice(invoiceCaptor.capture());
//      assertEquals(invoice.getCustomerId(), invoiceCaptor.getValue().intValue());
//   }
}