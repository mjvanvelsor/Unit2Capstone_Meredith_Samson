package com.trilogyed.retailapiservice.service;

import com.trilogyed.retailapiservice.exception.InsufficientInventoryException;
import com.trilogyed.retailapiservice.exception.NotFoundException;
import com.trilogyed.retailapiservice.model.*;
import com.trilogyed.retailapiservice.util.feign.*;
import com.trilogyed.retailapiservice.viewmodel.CustomerInvoiceViewModel;
import com.trilogyed.retailapiservice.viewmodel.CustomerOrderViewModel;
import com.trilogyed.retailapiservice.viewmodel.ProductsInInventoryViewModel;
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
            "Firstname", "Lastname", "Street name",
            "Charlotte", "28205", "customer@gmail.com", "704-987-0986");
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
   }
   
   private void setupInvoiceServiceMock() {
      // This process happens in the RestController,
      // before the data is sent to the ServiceLayer
      // prepare CustomerOrderViewModel (that is, the customer order)
      CustomerOrderViewModel covm1A = new CustomerOrderViewModel();
      covm1A.setCustomerId(100);
      covm1A.setPurchaseDate(LocalDate.now());
      // enter list of items on the order
      List<OrderItem> orderItems1A = new ArrayList<>();
      orderItems1A.add(new OrderItem(1, 5));
      orderItems1A.add(new OrderItem(2, 4));
      // add list of items to the order
      covm1A.setOrderItemList(orderItems1A);
      
      // At this stage, the controller sends the order to the serviceLayer
      // which builts it into an invoice before sending the invoice to
      // the invoice-service for saving to the database.
      Invoice invoice1A = new Invoice();
      // Customer record is retrieved, invalid customer raises exception
      Customer customer = new Customer(100,
            "Firstname", "Lastname", "Street name",
            "Charlotte", "28205", "customer@gmail.com", "704-987-0986");
      
      invoice1A.setCustomerId(covm1A.getCustomerId());
      invoice1A.setPurchaseDate(covm1A.getPurchaseDate());
      
      // prepare list of items on the invoice. The service layer should carryout some
      // validations on whether the item exists and has inventory to fulfill order
      // and should throw exceptions if those rules are not met.
      List<InvoiceItem> invItemsList1A = new ArrayList<>();
      invItemsList1A.add(new InvoiceItem(0, 0, 1,
            5, new BigDecimal(10.99).setScale(2, RoundingMode.HALF_EVEN)));
      invItemsList1A.add(new InvoiceItem(0, 0, 2,
            4, new BigDecimal(15.99).setScale(2, RoundingMode.HALF_EVEN)));
      // add list of items to the invoice
      invoice1A.setInvoiceItems(invItemsList1A);
      
      // At this stage, the pre-built invoice is sent to the invoice-service.
      // to be written into the database and returned back to the service layer
      // to finish building the CustomerInvoiceModel.
      Invoice invoice1B = new Invoice();
      invoice1B.setInvoiceId(1); // invoice Id comes from the invoice-service.
      invoice1B.setCustomerId(invoice1A.getCustomerId());
      invoice1B.setPurchaseDate(invoice1A.getPurchaseDate());
      List<InvoiceItem> invItemsList1B = new ArrayList<>();
      invItemsList1B.add(new InvoiceItem(1, 1, 1,
            5, new BigDecimal(10.99).setScale(2, RoundingMode.HALF_EVEN)));
      invItemsList1B.add(new InvoiceItem(2, 1, 2,
            4, new BigDecimal(15.99).setScale(2, RoundingMode.HALF_EVEN)));
      // add list of items to the invoice which now has invoiceItemId and invoiceId
      invoice1B.setInvoiceItems(invItemsList1B);
      
      // Finish build of the CustomerInvoiceView model
      CustomerInvoiceViewModel civm1A = new CustomerInvoiceViewModel();
      civm1A.setCustomerId(covm1A.getCustomerId());
      civm1A.setInvoiceId(invoice1B.getInvoiceId());
      civm1A.setCustomer(customer);
      civm1A.setLevelUp(new LevelUp(0, 100, 20, LocalDate.now()));
      civm1A.setInvoiceValue(new BigDecimal(118.91).setScale(2, RoundingMode.HALF_EVEN));
      
      Mockito.doReturn(invoice1B).when(invoiceService).getInvoice(1);
      Mockito.doReturn(invoice1B).when(invoiceService).createInvoice(invoice1A);
      
      // Mock for insufficient stock exception
      CustomerOrderViewModel covm2A = new CustomerOrderViewModel();
      covm2A.setCustomerId(100);
      covm2A.setPurchaseDate(LocalDate.now());
      // prepare list of items on the order
      List<OrderItem> orderItems2A = new ArrayList<>();
      orderItems2A.add(new OrderItem(1, 6));
      orderItems2A.add(new OrderItem(2, 500));
      // add list of items to the order
      covm2A.setOrderItemList(orderItems2A);
      
      Invoice invoice2A = new Invoice();
      // Customer is retrieved, invalid customer raises exception
      invoice2A.setCustomerId(covm2A.getCustomerId());
      invoice2A.setPurchaseDate(covm2A.getPurchaseDate());
      
      List<InvoiceItem> invItemsList2A = new ArrayList<>();
      invItemsList2A.add(new InvoiceItem(0, 0, 1,
            6, new BigDecimal(10.99).setScale(2, RoundingMode.HALF_EVEN)));
      invItemsList2A.add(new InvoiceItem(0, 0, 2,
            500, new BigDecimal(15.99).setScale(2, RoundingMode.HALF_EVEN)));
      // add list of items to the invoice
      invoice2A.setInvoiceItems(invItemsList2A);
   
      // Mock non-existent inventory exception
      CustomerOrderViewModel covm3A = new CustomerOrderViewModel();
      covm3A.setCustomerId(100);
      covm3A.setPurchaseDate(LocalDate.now());
      // prepare list of items on the order
      List<OrderItem> orderItems3A = new ArrayList<>();
      orderItems3A.add(new OrderItem(300, 7));
      orderItems3A.add(new OrderItem(200, 5));
      // add list of items to the order
      covm3A.setOrderItemList(orderItems3A);
      
      Invoice invoice3A = new Invoice();
      // Customer is retrieved, invalid customer raises exception
      invoice3A.setCustomerId(covm3A.getCustomerId());
      invoice3A.setPurchaseDate(covm3A.getPurchaseDate());
      
      List<InvoiceItem> invItemsList3A = new ArrayList<>();
      invItemsList3A.add(new InvoiceItem(0, 0, 300,
            7, new BigDecimal(10.99).setScale(2, RoundingMode.HALF_EVEN)));
      invItemsList3A.add(new InvoiceItem(0, 0, 200,
            5, new BigDecimal(15.99).setScale(2, RoundingMode.HALF_EVEN)));
      // add list of items to the invoice
      invoice3A.setInvoiceItems(invItemsList3A);
      
      // GetAll scenarios
      // 1st invoice
      Invoice invoice4A = new Invoice();
      invoice4A.setInvoiceId(3); // invoice Id comes from the invoice-service layer.
      invoice4A.setCustomerId(100);
      invoice4A.setPurchaseDate(invoice3A.getPurchaseDate());
      List<InvoiceItem> invItemsList4A = new ArrayList<>();
      invItemsList4A.add(new InvoiceItem(1, 3, 300,
            7, new BigDecimal(10.99).setScale(2, RoundingMode.HALF_EVEN)));
      invItemsList4A.add(new InvoiceItem(2, 3, 200,
            5, new BigDecimal(15.99).setScale(2, RoundingMode.HALF_EVEN)));
      // add list of items to the invoice which now has invoiceItemId and invoiceId
      invoice4A.setInvoiceItems(invItemsList4A);
      
      //2nd invoice
      Invoice invoice4B = new Invoice();
      invoice4B.setInvoiceId(3); // invoice Id comes from the invoice-service layer.
      invoice4B.setCustomerId(100);
      invoice4B.setPurchaseDate(invoice3A.getPurchaseDate());
      List<InvoiceItem> invItemsList4B = new ArrayList<>();
      invItemsList4B.add(new InvoiceItem(1, 3, 300,
            7, new BigDecimal(10.99).setScale(2, RoundingMode.HALF_EVEN)));
      invItemsList4B.add(new InvoiceItem(2, 3, 200,
            5, new BigDecimal(15.99).setScale(2, RoundingMode.HALF_EVEN)));
      // add list of items to the invoice which now has invoiceItemId and invoiceId
      invoice4B.setInvoiceItems(invItemsList4B);
      
      List<Invoice> allInvoices = new ArrayList<>();
      allInvoices.add(invoice4A);
      allInvoices.add(invoice4B);
      
      Mockito.doReturn(allInvoices).when(invoiceService).getAllInvoices();
      Mockito.doReturn(allInvoices).when(invoiceService).getInvoicesByCustomerId(100);
      
   }
   
   private void setupLevelUpServiceMock() {
      LevelUp levelUp = new LevelUp(0, 100, 20, LocalDate.now());
      Mockito.doReturn(levelUp).when(levelUpService).getLevelUpByCustomer(100);
   }

   @Test
   public void submitGetInvoice() {
      // prepare CustomerOrderViewModel (that is, the customer order)
      CustomerOrderViewModel covm1A = new CustomerOrderViewModel();
      covm1A.setCustomerId(100);
      covm1A.setPurchaseDate(LocalDate.now());
      // prepare list of items on the order
      List<OrderItem> orderItems1A = new ArrayList<>();
      orderItems1A.add(new OrderItem(1, 5));
      orderItems1A.add(new OrderItem(2, 4));
      // add list of items to the order
      covm1A.setOrderItemList(orderItems1A);
      CustomerInvoiceViewModel civm = service.submitInvoice(covm1A);
      
      // Get the created invoice and compare it with the retrieved invoice
      CustomerInvoiceViewModel civm1 = service.getInvoice(civm.getInvoiceId());
      assertEquals(civm, civm1);
      
   }
   
   @Test(expected = InsufficientInventoryException.class)
   public void submitInvoice_InsufficientInventoryException() {
      // prepare CustomerOrderViewModel (that is, the customer order)
      CustomerOrderViewModel covm1A = new CustomerOrderViewModel();
      covm1A.setCustomerId(100);
      covm1A.setPurchaseDate(LocalDate.now());
      // prepare list of items on the order
      List<OrderItem> orderItems1A = new ArrayList<>();
      orderItems1A.add(new OrderItem(1, 5));
      orderItems1A.add(new OrderItem(2, 500));
      // add list of items to the order
      covm1A.setOrderItemList(orderItems1A);
      CustomerInvoiceViewModel civm = service.submitInvoice(covm1A);
      
      // Get the created invoice and compare it with the retrieved invoice
      CustomerInvoiceViewModel civm1 = service.getInvoice(civm.getInvoiceId());
      assertEquals(civm, civm1);
      
   }
   
   @Test(expected = NotFoundException.class)
   public void submitInvoice_InventoryNotFoundException() {
      // prepare CustomerOrderViewModel (that is, the customer order)
      CustomerOrderViewModel covm1A = new CustomerOrderViewModel();
      covm1A.setCustomerId(100);
      covm1A.setPurchaseDate(LocalDate.now());
      // prepare list of items on the order
      List<OrderItem> orderItems1A = new ArrayList<>();
      orderItems1A.add(new OrderItem(300, 7));
      orderItems1A.add(new OrderItem(200, 5));
      // add list of items to the order
      covm1A.setOrderItemList(orderItems1A);
      CustomerInvoiceViewModel civm = service.submitInvoice(covm1A);
      
      // Get the created invoice and compare it with the retrieved invoice
      CustomerInvoiceViewModel civm1 = service.getInvoice(civm.getInvoiceId());
      assertEquals(civm, civm1);
      
   }
   
   @Test(expected = NotFoundException.class)
   public void submitInvoice_InvalidCustomerException() {
      // prepare CustomerOrderViewModel (that is, the customer order)
      CustomerOrderViewModel covm1A = new CustomerOrderViewModel();
      covm1A.setCustomerId(999999999);
      covm1A.setPurchaseDate(LocalDate.now());
      // prepare list of items on the order
      List<OrderItem> orderItems1A = new ArrayList<>();
      orderItems1A.add(new OrderItem(1, 7));
      orderItems1A.add(new OrderItem(2, 5));
      // add list of items to the order
      covm1A.setOrderItemList(orderItems1A);
      CustomerInvoiceViewModel civm = service.submitInvoice(covm1A);
      
      // Get the created invoice and compare it with the retrieved invoice
      CustomerInvoiceViewModel civm1 = service.getInvoice(civm.getInvoiceId());
      assertEquals(civm, civm1);
      
   }
   
   @Test
   public void getAllInvoices() {
      List<CustomerInvoiceViewModel> rvmList = service.getAllInvoices();
      assertEquals(2, rvmList.size());
   }
   
   @Test
   public void getInvoicesByCustomerId() {
      List<CustomerInvoiceViewModel> rvmList = service.getInvoicesByCustomerId(100);
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
      List<ProductsInInventoryViewModel> productsInInv = new ArrayList<>();
      productsInInv.add(new ProductsInInventoryViewModel(inv1, prod1));
      productsInInv.add(new ProductsInInventoryViewModel(inv2, prod2));
      productsInInv.add(new ProductsInInventoryViewModel(inv3, prod3));
      
      // I am checking if the method returns two objects in the list because inv3 has zero inventory.
      List<ProductsInInventoryViewModel> productsInInv1 = service.getProductsInInventory();
      assertEquals(2, productsInInv1.size());
      
   }
   
   @Test
   public void getProductByInvoiceId() {
      // prepare CustomerOrderViewModel (that is, the customer order)
      CustomerOrderViewModel covm1A = new CustomerOrderViewModel();
      covm1A.setCustomerId(100);
      covm1A.setPurchaseDate(LocalDate.now());
      // prepare list of items on the order
      List<OrderItem> orderItems1A = new ArrayList<>();
      orderItems1A.add(new OrderItem(1, 5));
      orderItems1A.add(new OrderItem(2, 4));
      // add list of items to the order
      covm1A.setOrderItemList(orderItems1A);
      CustomerInvoiceViewModel civm = service.submitInvoice(covm1A);
      
      List<Product> productList1 = service.getProductByInvoiceId(civm.getInvoiceId());
      assertEquals(2, productList1.size());
   }
   
   @Test(expected = NotFoundException.class)
   public void getProductByInvoiceId_InvalidInvoiceId() {
      // prepare CustomerOrderViewModel (that is, the customer order)
      CustomerOrderViewModel covm1A = new CustomerOrderViewModel();
      covm1A.setCustomerId(100);
      covm1A.setPurchaseDate(LocalDate.now());
      // prepare list of items on the order
      List<OrderItem> orderItems1A = new ArrayList<>();
      orderItems1A.add(new OrderItem(91, 5));
      orderItems1A.add(new OrderItem(129999992, 4));
      // add list of items to the order
      covm1A.setOrderItemList(orderItems1A);
      CustomerInvoiceViewModel civm = service.submitInvoice(covm1A);
      
      List<Product> productList1 = service.getProductByInvoiceId(civm.getInvoiceId());
      assertEquals(2, productList1.size());
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
