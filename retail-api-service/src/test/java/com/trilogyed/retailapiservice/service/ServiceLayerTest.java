package com.trilogyed.retailapiservice.service;

import com.trilogyed.retailapiservice.exception.InsufficientInventoryException;
import com.trilogyed.retailapiservice.model.*;
import com.trilogyed.retailapiservice.util.feign.*;
import com.trilogyed.retailapiservice.viewmodel.CustomerInvoiceLevelupViewmodel;
import com.trilogyed.retailapiservice.viewmodel.InvoiceItemInventoryProductViewmodel;
import com.trilogyed.retailapiservice.viewmodel.ProductsInInventoryViewmodel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(MockitoJUnitRunner.class)
public class ServiceLayerTest {
   @Mock
   private CustomerService customerService;
   @Mock
   private InventoryService inventoryService;
   @Mock
   private InvoiceService invoiceService;
   @Mock
   private LevelUpService levelUpService;
   @Mock
   private ProductService productService;
   
   @InjectMocks
   ServiceLayer service;
   
   @Before
   public void setUp() throws Exception {
      initMocks(this);
      setupCustomerServiceMock();
      setupInventoryServiceMock();
      setupInvoiceServiceMock();
      setupLevelUpServiceMock();
      setupProductServiceMock();
      service = new ServiceLayer(customerService, inventoryService,
            invoiceService, levelUpService, productService);
      
   }
   
   private void setupCustomerServiceMock() {
      Customer customer = new Customer(100,
            "Firstname","Lastname","Street name",
            "Charlotte","28205","customer@gmail.com","704-987-0986");
      Mockito.doReturn(customer).when(customerService).getCustomer(100);
   
   }
   
   private void setupInventoryServiceMock() {
      // Create inventory
      Inventory inv1 = new Inventory(1, 1, 30);
      Inventory inv2 = new Inventory(2, 2, 25);
      Inventory inv3 = new Inventory(3, 3, 0);
      List<Inventory> inventoryList = new ArrayList<>();
      inventoryList.add(inv1);
      inventoryList.add(inv2);
      inventoryList.add(inv3);
      
      Mockito.doReturn(inv1).when(inventoryService).getInventory(1);
      Mockito.doReturn(inv2).when(inventoryService).getInventory(2);
      Mockito.doReturn(inventoryList).when(inventoryService).getAllInventory();
   }
   
   private void setupProductServiceMock() {
      // Create products
      Product prod1A = new Product();
      prod1A.setProductId(1);
      prod1A.setProductName("Product name 1");
      prod1A.setProductDescription("Product description 1");
      prod1A.setListPrice(new BigDecimal(10.99).setScale(2, RoundingMode.HALF_EVEN));
      prod1A.setUnitCost(new BigDecimal(2.50).setScale(2, RoundingMode.HALF_EVEN));
      
      Product prod1B = new Product();
      prod1B.setProductName("Product name 1");
      prod1B.setProductDescription("Product description 1");
      prod1B.setListPrice(new BigDecimal(10.99).setScale(2, RoundingMode.HALF_EVEN));
      prod1B.setUnitCost(new BigDecimal(2.50).setScale(2, RoundingMode.HALF_EVEN));
      
      Product prod2A = new Product();
      prod2A.setProductId(2);
      prod2A.setProductName("Product name 2");
      prod2A.setProductDescription("Product description 2");
      prod2A.setListPrice(new BigDecimal(15.99).setScale(2, RoundingMode.HALF_EVEN));
      prod2A.setUnitCost(new BigDecimal(4.59).setScale(2, RoundingMode.HALF_EVEN));
   
      Product prod2B = new Product();
      prod2B.setProductName("Product name 2");
      prod2B.setProductDescription("Product description 2");
      prod2B.setListPrice(new BigDecimal(15.99).setScale(2, RoundingMode.HALF_EVEN));
      prod2B.setUnitCost(new BigDecimal(4.59).setScale(2, RoundingMode.HALF_EVEN));
   
      Product prod3A = new Product();
      prod3A.setProductId(3);
      prod3A.setProductName("Product name 3");
      prod3A.setProductDescription("Product description 3");
      prod3A.setListPrice(new BigDecimal(20.99).setScale(2, RoundingMode.HALF_EVEN));
      prod3A.setUnitCost(new BigDecimal(5.59).setScale(2, RoundingMode.HALF_EVEN));
   
      Product prod3B = new Product();
      prod3B.setProductName("Product name 3");
      prod3B.setProductDescription("Product description 3");
      prod3B.setListPrice(new BigDecimal(20.99).setScale(2, RoundingMode.HALF_EVEN));
      prod3B.setUnitCost(new BigDecimal(5.59).setScale(2, RoundingMode.HALF_EVEN));
      
      List<Product> productList = new ArrayList<>();
      productList.add(prod1A);
      productList.add(prod2A);
      productList.add(prod3A);
      
      Mockito.doReturn(prod1A).when(productService).createProduct(prod1B);
      Mockito.doReturn(prod1A).when(productService).getProduct(1);
      Mockito.doReturn(prod2A).when(productService).createProduct(prod2B);
      Mockito.doReturn(prod2A).when(productService).getProduct(2);
      Mockito.doReturn(prod3A).when(productService).createProduct(prod3B);
      Mockito.doReturn(prod3A).when(productService).getProduct(3);
   }
   
   private void setupInvoiceServiceMock() {
      // prepare customer
      Customer customer = new Customer(100,
            "Firstname","Lastname","Street name",
            "Charlotte","28205","customer@gmail.com","704-987-0986");
   
      // prepare 1st invoice
      Invoice invoice3A = new Invoice();
      invoice3A.setInvoiceId(2);
      invoice3A.setCustomerId(100);
      invoice3A.setPurchaseDate(LocalDate.now());
   
      // prepare list of items on the invoice
      List<InvoiceItem> invItemsList3A = new ArrayList<>();
      invItemsList3A.add(new InvoiceItem(1, 2, 1,
            5, new BigDecimal(10.99).setScale(2, RoundingMode.HALF_EVEN)));
      invItemsList3A.add(new InvoiceItem(2, 2, 2,
            4, new BigDecimal(6.99).setScale(2, RoundingMode.HALF_EVEN)));
      // add list of items to the invoice
      invoice3A.setInvoiceItems(invItemsList3A);
   
      // prepare invoice
      Invoice invoice3B = new Invoice();
      invoice3B.setCustomerId(100);
      invoice3B.setPurchaseDate(LocalDate.now());
   
      // prepare list of items on the invoice
      List<InvoiceItem> invItemsList3B = new ArrayList<>();
      invItemsList3B.add(new InvoiceItem(1, 2, 1,
            5, new BigDecimal(10.99).setScale(2, RoundingMode.HALF_EVEN)));
      invItemsList3B.add(new InvoiceItem(2, 2, 2,
            4, new BigDecimal(6.99).setScale(2, RoundingMode.HALF_EVEN)));
      // add list of items to the invoice
      invoice3B.setInvoiceItems(invItemsList3B);
      
      // prepare 1st invoice
      Invoice invoice1A = new Invoice();
      invoice1A.setInvoiceId(1);
      invoice1A.setCustomerId(100);
      invoice1A.setPurchaseDate(LocalDate.now());
   
      // prepare list of items on the invoice
      List<InvoiceItem> invItemsList1A = new ArrayList<>();
      invItemsList1A.add(new InvoiceItem(1, 1, 1,
            5, new BigDecimal(10.99).setScale(2, RoundingMode.HALF_EVEN)));
      invItemsList1A.add(new InvoiceItem(2, 1, 2,
            40, new BigDecimal(6.99).setScale(2, RoundingMode.HALF_EVEN)));
      invItemsList1A.add(new InvoiceItem(3, 1, 3,
            4, new BigDecimal(15.99).setScale(2, RoundingMode.HALF_EVEN)));
      // add list of items to the invoice
      invoice1A.setInvoiceItems(invItemsList1A);
      
      // prepare invoice
      Invoice invoice1B = new Invoice();
      invoice1B.setCustomerId(100);
      invoice1B.setPurchaseDate(LocalDate.now());
   
      // prepare list of items on the invoice
      List<InvoiceItem> invItemsList1B = new ArrayList<>();
      invItemsList1B.add(new InvoiceItem(1, 1, 1,
            5, new BigDecimal(10.99).setScale(2, RoundingMode.HALF_EVEN)));
      invItemsList1B.add(new InvoiceItem(2, 1, 2,
            40, new BigDecimal(6.99).setScale(2, RoundingMode.HALF_EVEN)));
      invItemsList1B.add(new InvoiceItem(3, 1, 3,
            4, new BigDecimal(15.99).setScale(2, RoundingMode.HALF_EVEN)));
      // add list of items to the invoice
      invoice1B.setInvoiceItems(invItemsList1B);
   
      // prepare 2nd invoice
      Invoice invoice2A = new Invoice();
      invoice2A.setInvoiceId(1);
      invoice2A.setCustomerId(100);
      invoice2A.setPurchaseDate(LocalDate.now());
   
      // prepare list of items on the invoice
      List<InvoiceItem> invItemsList2A = new ArrayList<>();
      invItemsList2A.add(new InvoiceItem(1, 1, 1,
            5, new BigDecimal(10.99).setScale(2, RoundingMode.HALF_EVEN)));
      invItemsList2A.add(new InvoiceItem(2, 1, 2,
            40, new BigDecimal(6.99).setScale(2, RoundingMode.HALF_EVEN)));
      invItemsList2A.add(new InvoiceItem(3, 1, 3,
            4, new BigDecimal(15.99).setScale(2, RoundingMode.HALF_EVEN)));
      // add list of items to the invoice
      invoice2A.setInvoiceItems(invItemsList2A);
   
      // prepare invoice
      Invoice invoice2B = new Invoice();
      invoice2B.setCustomerId(100);
      invoice2B.setPurchaseDate(LocalDate.now());
   
      // prepare list of items on the invoice
      List<InvoiceItem> invItemsList2B = new ArrayList<>();
      invItemsList2B.add(new InvoiceItem(1, 1, 1,
            5, new BigDecimal(10.99).setScale(2, RoundingMode.HALF_EVEN)));
      invItemsList2B.add(new InvoiceItem(2, 1, 2,
            40, new BigDecimal(6.99).setScale(2, RoundingMode.HALF_EVEN)));
      invItemsList2B.add(new InvoiceItem(3, 1, 3,
            4, new BigDecimal(15.99).setScale(2, RoundingMode.HALF_EVEN)));
      // add list of items to the invoice
      invoice2B.setInvoiceItems(invItemsList2B);
      List<Invoice> allInvoices = new ArrayList<>();
      allInvoices.add(invoice1A);
      allInvoices.add(invoice2A);
      
      Mockito.doReturn(allInvoices).when(invoiceService).getAllInvoices();
      Mockito.doReturn(allInvoices).when(invoiceService).getInvoicesByCustomerId(100);
      Mockito.doReturn(invoice3A).when(invoiceService).getInvoice(2);
      Mockito.doReturn(invoice3A).when(invoiceService).createInvoice(invoice3B);
      
   }
   
   private void setupLevelUpServiceMock() {
      LevelUp levelUp = new LevelUp(0, 100, 10, LocalDate.now());
      Mockito.doReturn(levelUp).when(levelUpService).getLevelUpByCustomer(100);
   }
   
   @Test
   public void submitInvoice() {
      Customer customer = new Customer(100,
            "Firstname","Lastname","Street name",
            "Charlotte","28205","customer@gmail.com","704-987-0986");
      
      // prepare invoice
      Invoice invoice = new Invoice();
      invoice.setCustomerId(100);
      invoice.setPurchaseDate(LocalDate.now());
      
      // prepare list of items on the invoice
      List<InvoiceItem> invItemsList = new ArrayList<>();
      invItemsList.add(new InvoiceItem(1, 2, 1,
            5, new BigDecimal(10.99).setScale(2, RoundingMode.HALF_EVEN)));
      invItemsList.add(new InvoiceItem(2, 2, 2,
            4, new BigDecimal(6.99).setScale(2, RoundingMode.HALF_EVEN)));
      // add list of items to the invoice
      invoice.setInvoiceItems(invItemsList);
      double invoiceValue = service.calculateInvoiceValue(invItemsList);
      int points = Math.floorDiv((int)invoiceValue, 50) * 10;
      
      LevelUp levelUp = null;
      if (points > 0) {
         levelUp = new LevelUp();
         levelUp.setCustomerId(100);
         levelUp.setPoints(points);
         levelUp.setMemberDate(LocalDate.now());
      }
      
      // populate CustomerInvoiceLevelupViewModel
      CustomerInvoiceLevelupViewmodel viewModel = new CustomerInvoiceLevelupViewmodel();
      
      viewModel.setCustomerId(100);
      viewModel.setCustomer(customer);
      viewModel.setInvoice(invoice);
      viewModel.setInvoiceValue(new BigDecimal(invoiceValue).setScale(2, RoundingMode.HALF_EVEN));
      viewModel.setLevelUp(levelUp);
      System.out.println("Expected: " + levelUp);
      viewModel = service.submitInvoice(viewModel);
      
      // Get the invoice submitted
      CustomerInvoiceLevelupViewmodel viewmodel1 = service.getInvoice(viewModel.getInvoiceId());
      assertEquals(viewModel, viewmodel1);
      
   }

   @Test(expected = InsufficientInventoryException.class)
   public void submitInvoice_InsufficientInventory() {
      Customer customer = new Customer(100,
            "Firstname","Lastname","Street name",
            "Charlotte","28205","customer@gmail.com","704-987-0986");

      // prepare invoice
      Invoice invoice = new Invoice();
      invoice.setCustomerId(100);
      invoice.setPurchaseDate(LocalDate.now());

      // prepare list of items on the invoice
      List<InvoiceItem> invItemsList = new ArrayList<>();
      invItemsList.add(new InvoiceItem(1, 1, 1,
            5, new BigDecimal(10.99).setScale(2, RoundingMode.HALF_EVEN)));
      invItemsList.add(new InvoiceItem(2, 1, 2,
            40, new BigDecimal(6.99).setScale(2, RoundingMode.HALF_EVEN)));
      invItemsList.add(new InvoiceItem(3, 1, 3,
            4, new BigDecimal(15.99).setScale(2, RoundingMode.HALF_EVEN)));
      // add list of items to the invoice
      invoice.setInvoiceItems(invItemsList);
      double invoiceValue = service.calculateInvoiceValue(invItemsList);
      int points = Math.floorDiv((int)invoiceValue, 50) * 10;

      LevelUp levelUp = null;
      if (points > 0) {
         levelUp = new LevelUp();
         levelUp.setLevelUpId(0);
         levelUp.setCustomerId(100);
         levelUp.setPoints(points);
         levelUp.setMemberDate(LocalDate.now());
      }

      // populate CustomerInvoiceLevelupViewModel
      CustomerInvoiceLevelupViewmodel viewModel = new CustomerInvoiceLevelupViewmodel();

      viewModel.setCustomerId(100);
      viewModel.setCustomer(customer);
      viewModel.setInvoice(invoice);
      viewModel.setInvoiceValue(new BigDecimal(invoiceValue).setScale(2, RoundingMode.HALF_EVEN));
      viewModel.setLevelUp(levelUp);
      viewModel = service.submitInvoice(viewModel);

      // Get the invoice submitted
      CustomerInvoiceLevelupViewmodel viewmodel1 = service.getInvoice(viewModel.getInvoiceId());
      assertEquals(viewModel, viewmodel1);

   }

   @Test
   public void getInvoice() {
      Customer customer = new Customer(100,
            "Firstname","Lastname","Street name",
            "Charlotte","28205","customer@gmail.com","704-987-0986");
   
      // prepare invoice
      Invoice invoice = new Invoice();
      invoice.setCustomerId(100);
      invoice.setPurchaseDate(LocalDate.now());
   
      // prepare list of items on the invoice
      List<InvoiceItem> invItemsList = new ArrayList<>();
      invItemsList.add(new InvoiceItem(1, 2, 1,
            5, new BigDecimal(10.99).setScale(2, RoundingMode.HALF_EVEN)));
      invItemsList.add(new InvoiceItem(2, 2, 2,
            4, new BigDecimal(6.99).setScale(2, RoundingMode.HALF_EVEN)));
      // add list of items to the invoice
      invoice.setInvoiceItems(invItemsList);
      double invoiceValue = service.calculateInvoiceValue(invItemsList);
      int points = Math.floorDiv((int)invoiceValue, 50) * 10;
   
      LevelUp levelUp = null;
      if (points > 0) {
         levelUp = new LevelUp();
         levelUp.setCustomerId(100);
         levelUp.setPoints(points);
         levelUp.setMemberDate(LocalDate.now());
      }
   
      // populate CustomerInvoiceLevelupViewModel
      CustomerInvoiceLevelupViewmodel viewModel = new CustomerInvoiceLevelupViewmodel();
   
      viewModel.setCustomerId(100);
      viewModel.setCustomer(customer);
      viewModel.setInvoice(invoice);
      viewModel.setInvoiceValue(new BigDecimal(invoiceValue).setScale(2, RoundingMode.HALF_EVEN));
      viewModel.setLevelUp(levelUp);
      System.out.println("Expected: " + levelUp);
      viewModel = service.submitInvoice(viewModel);
   
      // Get the invoice submitted
      CustomerInvoiceLevelupViewmodel viewmodel1 = service.getInvoice(viewModel.getInvoiceId());
      assertEquals(viewModel, viewmodel1);
   
      assertEquals(viewmodel1, viewModel);
   }

   @Test
   public void getAllInvoices() {
      List<CustomerInvoiceLevelupViewmodel> rvmList = service.getAllInvoices();
      assertEquals(2, rvmList.size());
   }

   @Test
   public void getInvoicesByCustomerId() {
      List<CustomerInvoiceLevelupViewmodel> rvmList = service.getInvoicesByCustomerId(100);
      assertEquals(2, rvmList.size());
   }

   @Test
   public void createGetProduct() {
      Product prod1 = new Product();
      prod1.setProductName("Product name 1");
      prod1.setProductDescription("Product description 1");
      prod1.setListPrice(new BigDecimal(10.99).setScale(2, RoundingMode.HALF_EVEN));
      prod1.setUnitCost(new BigDecimal(2.50).setScale(2, RoundingMode.HALF_EVEN));
      prod1 = productService.createProduct(prod1);

      Product prod2 = productService.getProduct(prod1.getProductId());

      assertEquals(prod2, prod1);
   }

   @Test
   public void getProductsInInventory() {

      // Create products
      Product prod1 = new Product();
      prod1.setProductName("Product name 1");
      prod1.setProductDescription("Product description 1");
      prod1.setListPrice(new BigDecimal(10.99).setScale(2, RoundingMode.HALF_EVEN));
      prod1.setUnitCost(new BigDecimal(2.50).setScale(2, RoundingMode.HALF_EVEN));
      prod1 = productService.createProduct(prod1);

      Product prod2 = new Product();
      prod2.setProductName("Product name 2");
      prod2.setProductDescription("Product description 2");
      prod2.setListPrice(new BigDecimal(15.99).setScale(2, RoundingMode.HALF_EVEN));
      prod2.setUnitCost(new BigDecimal(4.59).setScale(2, RoundingMode.HALF_EVEN));
      prod2 = productService.createProduct(prod2);


      Product prod3 = new Product();
      prod3.setProductName("Product name 3");
      prod3.setProductDescription("Product description 3");
      prod3.setListPrice(new BigDecimal(20.99).setScale(2, RoundingMode.HALF_EVEN));
      prod3.setUnitCost(new BigDecimal(5.59).setScale(2, RoundingMode.HALF_EVEN));
      prod3 = productService.createProduct(prod3);

      // Create inventory
      Inventory inv1 = new Inventory(1, prod1.getProductId(), 30);
      Inventory inv2 = new Inventory(2, prod2.getProductId(), 25);
      Inventory inv3 = new Inventory(3, prod3.getProductId(), 0);

      // Populate ProductsInInventoryViewmodel list
      List<ProductsInInventoryViewmodel> productsInInv = new ArrayList<>();
      productsInInv.add(new ProductsInInventoryViewmodel(inv1, prod1));
      productsInInv.add(new ProductsInInventoryViewmodel(inv2, prod2));
      productsInInv.add(new ProductsInInventoryViewmodel(inv3, prod3));

      // I am checking if the method returns two objects in the list because inv3 has zero inventory.
      List<ProductsInInventoryViewmodel> productsInInv1 = service.getProductsInInventory();
      assertEquals(2, productsInInv1.size());

   }

   @Test
   public void getProductByInvoiceId() {

      // 1. create invoice
      Customer customer = new Customer(100,
            "Firstname","Lastname","Street name",
            "Charlotte","28205","customer@gmail.com","704-987-0986");

      // prepare invoice
      Invoice invoice = new Invoice();
      invoice.setCustomerId(100);
      invoice.setPurchaseDate(LocalDate.now());

      // prepare list of items on the invoice
      List<InvoiceItem> invItemsList = new ArrayList<>();
      invItemsList.add(new InvoiceItem(1, 2, 1,
            5, new BigDecimal(10.99).setScale(2, RoundingMode.HALF_EVEN)));
      invItemsList.add(new InvoiceItem(2, 2, 2,
            4, new BigDecimal(6.99).setScale(2, RoundingMode.HALF_EVEN)));
//      invItemsList.add(new InvoiceItem(3, 1, 3,
//            4, new BigDecimal(15.99).setScale(2, RoundingMode.HALF_EVEN)));
      // add list of items to the invoice
      invoice.setInvoiceItems(invItemsList);
      double invoiceValue = service.calculateInvoiceValue(invItemsList);
      int points = Math.floorDiv((int)invoiceValue, 50) * 10;

      LevelUp levelUp = null;
      if (points > 0) {
         levelUp = new LevelUp();
         levelUp.setLevelUpId(0);
         levelUp.setCustomerId(100);
         levelUp.setPoints(points);
         levelUp.setMemberDate(LocalDate.now());
      }

      // populate CustomerInvoiceLevelupViewModel
      CustomerInvoiceLevelupViewmodel viewModel = new CustomerInvoiceLevelupViewmodel();

      viewModel.setCustomerId(100);
      viewModel.setCustomer(customer);
      viewModel.setInvoice(invoice);
      viewModel.setInvoiceValue(new BigDecimal(invoiceValue).setScale(2, RoundingMode.HALF_EVEN));
      viewModel.setLevelUp(levelUp);
      viewModel = service.submitInvoice(viewModel);

      // Get the invoice submitted
      CustomerInvoiceLevelupViewmodel viewmodel1 = service.getInvoice(viewModel.getInvoiceId());

      // 3. get inventory record using the inventory Id on the Invoice Item from inventory-service
      List<InvoiceItemInventoryProductViewmodel> productViewmodelList = new ArrayList<>();
      InvoiceItem invoiceItem = new InvoiceItem();
      Inventory inventory = new Inventory();
      Product product = new Product();
      List<Product> productList = new ArrayList<>();
      Iterator<InvoiceItem> iter = invItemsList.iterator();
      while(iter.hasNext()) {
         invoiceItem = iter.next();
         inventory = inventoryService.getInventory(invoiceItem.getInventoryId());
         product = productService.getProduct(inventory.getProductId());
         productList.add(product);
      }

      List<Product> productList1 = service.getProductByInvoiceId(viewModel.getInvoiceId());
      assertEquals(productList, productList1);

   }

   @Test
   public void calculateInvoiceValueAndPoints() {

      // prepare list of items on invoice
      List<InvoiceItem> invItemsList = new ArrayList<>();
      invItemsList.add(new InvoiceItem(1, 1, 1,
            5, new BigDecimal(10.99).setScale(2, RoundingMode.HALF_EVEN)));
      invItemsList.add(new InvoiceItem(2, 1, 2,
            4, new BigDecimal(6.99).setScale(2, RoundingMode.HALF_EVEN)));
      invItemsList.add(new InvoiceItem(3, 1, 3,
            4, new BigDecimal(15.99).setScale(2, RoundingMode.HALF_EVEN)));

      double invoiceValue = service.calculateInvoiceValue(invItemsList);
      int points = Math.floorDiv((int)invoiceValue, 50) * 10;

      assertEquals(146.87, invoiceValue, 0.001);
      assertEquals(20, points);

      // When invoice is less than 50.00
      invItemsList = new ArrayList<>();
      invItemsList.add(new InvoiceItem(2, 1, 2,
            4, new BigDecimal(6.99).setScale(2, RoundingMode.HALF_EVEN)));
      invoiceValue = service.calculateInvoiceValue(invItemsList);
      points = Math.floorDiv((int)invoiceValue, 50) * 10;

      assertEquals(27.96, invoiceValue, 0.001);
      assertEquals(0, points);

      // When invoice is 99.9
      invItemsList = new ArrayList<>();
      invItemsList.add(new InvoiceItem(2, 1, 2,
            4, new BigDecimal(16.65).setScale(2, RoundingMode.HALF_EVEN)));
      invoiceValue = service.calculateInvoiceValue(invItemsList);
      points = Math.floorDiv((int)invoiceValue, 50) * 10;

      assertEquals(66.6, invoiceValue, 0.001);
      assertEquals(10, points);

      // When invoice is 100.50
      invItemsList = new ArrayList<>();
      invItemsList.add(new InvoiceItem(2, 1, 2,
            6, new BigDecimal(16.75).setScale(2, RoundingMode.HALF_EVEN)));
      invoiceValue = service.calculateInvoiceValue(invItemsList);
      points = Math.floorDiv((int)invoiceValue, 50) * 10;

      assertEquals(100.50, invoiceValue, 0.001);
      assertEquals(20, points);

      // When invoice is 333.00
      invItemsList = new ArrayList<>();
      invItemsList.add(new InvoiceItem(2, 1, 2,
            20, new BigDecimal(16.65).setScale(2, RoundingMode.HALF_EVEN)));
      invoiceValue = service.calculateInvoiceValue(invItemsList);
      points = Math.floorDiv((int)invoiceValue, 50) * 10;

      assertEquals(333.00, invoiceValue, 0.001);
      assertEquals(60, points);

      // When there is cancellation of a purchase is 100.50
      invItemsList = new ArrayList<>();
      invItemsList.add(new InvoiceItem(2, 1, 2,
            -6, new BigDecimal(16.75).setScale(2, RoundingMode.HALF_EVEN)));
      invoiceValue = service.calculateInvoiceValue(invItemsList);
      points = Math.floorDiv((int)invoiceValue, 50) * 10;

      assertEquals(-100.50, invoiceValue, 0.001);
      assertEquals(-20, points);
   }
}