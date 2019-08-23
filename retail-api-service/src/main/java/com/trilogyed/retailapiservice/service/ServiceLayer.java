package com.trilogyed.retailapiservice.service;

import com.trilogyed.retailapiservice.exception.InsufficientInventoryException;
import com.trilogyed.retailapiservice.exception.NotFoundException;
import com.trilogyed.retailapiservice.exception.ServiceFailException;
import com.trilogyed.retailapiservice.model.*;
import com.trilogyed.retailapiservice.util.feign.*;
import com.trilogyed.retailapiservice.viewmodel.CustomerInvoiceLevelupViewmodel;
import com.trilogyed.retailapiservice.viewmodel.InvoiceItemInventoryProductViewmodel;
import com.trilogyed.retailapiservice.viewmodel.ProductsInInventoryViewmodel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@EnableEurekaClient
@EnableFeignClients
@Component
public class ServiceLayer {
   private CustomerService customerService;
   private InventoryService inventoryService;
   private InvoiceService invoiceService;
   private LevelUpService levelUpService;
   private ProductService productService;
   
   @Autowired
   public ServiceLayer(CustomerService customerService, InventoryService inventoryService, InvoiceService invoiceService, LevelUpService levelUpService, ProductService productService) {
      this.customerService = customerService;
      this.inventoryService = inventoryService;
      this.invoiceService = invoiceService;
      this.levelUpService = levelUpService;
      this.productService = productService;
   }
   
   public CustomerInvoiceLevelupViewmodel submitInvoice(CustomerInvoiceLevelupViewmodel order) {
      // Get the customer. Customer may be null, so we use Optional. If customer is null, throw exception
      Optional<Customer> optionalCustomer = Optional.ofNullable(customerService.getCustomer(order.getCustomerId()));
      optionalCustomer.orElseThrow(() -> new NotFoundException(
            "Customer Id: " + order.getCustomerId() + " not found. Please check your Customer Id!"));
      
      Customer customer = optionalCustomer.get();
      
      // prepare invoice
      Invoice invoice = new Invoice();
      invoice.setCustomerId(customer.getCustomerId());
      invoice.setPurchaseDate(LocalDate.now());
      
      // add list of items to the invoice
      invoice.setInvoiceItems(order.getInvoice().getInvoiceItems());
      
      // check if products are in inventory
      List<ProductsInInventoryViewmodel> productsInInv1 = this.getProductsInInventory();
      Map<Integer, ProductsInInventoryViewmodel> productsInInventoryMap = new HashMap<>();
      for (int index=0; index < productsInInv1.size(); index++) {
         productsInInventoryMap.put(productsInInv1.get(index).getInventory().getProductId(), productsInInv1.get(index));
      }
      
      String errorMsg = null;
      Product product = new Product();
      int orderQty = 0;
      int inventoryQty = 0;
      
      for (int index=0; index < invoice.getInvoiceItems().size(); index++) {
         product = productService.getProduct(invoice.getInvoiceItems().get(index).getInventoryId());
         if (productsInInventoryMap.get(invoice.getInvoiceItems().get(index).getInventoryId()) == null) {
            errorMsg += "Product Id: " + product.getProductId()
                  + " " + product.getProductName()
                  + " is not available in inventory,";
   
         } else {
   
            // check if order quantity can be fulfilled
            orderQty = invoice.getInvoiceItems().get(index).getQuantity();
            inventoryQty = productsInInventoryMap.get(invoice.getInvoiceItems().get(index).getInventoryId()).getInventory().getQuantity();
            if (orderQty > inventoryQty) {
               errorMsg += "Product Id: " + product.getProductId()
                     + " " + product.getProductName()
                     + " inventory insufficient - Inventory Qty: " + inventoryQty + " Order Qty: " + orderQty + ",";
            }
         }
      }
      
      // check if errorMsg is not empty which indicates there are error messages
      // related to low inventory to fulfill order
      if (errorMsg != null) {
         throw new InsufficientInventoryException(errorMsg);
      }
   
      // calculate invoice value
      double invoiceValue = calculateInvoiceValue(order.getInvoice().getInvoiceItems());
      
      // calculate level up points
      int points = Math.floorDiv((int)invoiceValue, 50) * 10;
      LevelUp levelUp = null;
      if (points > 0) {
         levelUp = new LevelUp();
         levelUp.setCustomerId(customer.getCustomerId());
         levelUp.setMemberDate(LocalDate.now());
         levelUp.setPoints(points);
      }
   
      // populate CustomerInvoiceLevelupViewModel
      CustomerInvoiceLevelupViewmodel viewModel = new CustomerInvoiceLevelupViewmodel();
   
      viewModel.setCustomerId(customer.getCustomerId());
      viewModel.setCustomer(customer);
      viewModel.setLevelUp(levelUp);
      viewModel.setInvoiceValue(new BigDecimal(invoiceValue).setScale(2, RoundingMode.HALF_EVEN));
      
      // call invoice-service to write the invoice. Service may fail to create the invoice.
      Optional<Invoice> optionalInvoice = Optional.ofNullable(invoiceService.createInvoice(invoice));
      optionalInvoice.orElseThrow(() -> new ServiceFailException("invoice-service error:- Invoice not created"));
      invoice = optionalInvoice.get();
      viewModel.setInvoiceId(invoice.getInvoiceId());
      viewModel.setInvoice(invoice);
      System.out.println("Actual: " + levelUp);
      
      return viewModel;
   }
   
   public CustomerInvoiceLevelupViewmodel getInvoice(int id) {

      // Get the invoice submitted
      Optional<Invoice> optionalInvoice = Optional.ofNullable(invoiceService.getInvoice(id));
      optionalInvoice.orElseThrow(() -> new NotFoundException(
            "Invoice Id: " + id + " not found. Please check your Invoice Id!"));
      Invoice invoice = optionalInvoice.get();
   
      return buildCustomerInvoiceLevelupViewmodel(invoice);
      
   }
   
   public List<CustomerInvoiceLevelupViewmodel> getAllInvoices() {
      Optional<List<Invoice>> optionalAllInvoices = Optional.ofNullable(invoiceService.getAllInvoices());
      optionalAllInvoices.orElseThrow(() -> new NotFoundException(
            "No invoices exist in the system. Please ensure invoice-service is running if you know invoices do exist!"));
      List<Invoice> invoiceList = optionalAllInvoices.get();
      Iterator<Invoice> iter = invoiceList.iterator();
      List<CustomerInvoiceLevelupViewmodel> viewmodelList = new ArrayList<>();
      
      while(iter.hasNext()) {
         viewmodelList.add(buildCustomerInvoiceLevelupViewmodel(iter.next()));
      }
      return viewmodelList;
   }
   
   
   public List<CustomerInvoiceLevelupViewmodel> getInvoicesByCustomerId(int id) {
   
      Optional<List<Invoice>> optionalAllInvoices = Optional.ofNullable(invoiceService.getInvoicesByCustomerId(id));
      optionalAllInvoices.orElseThrow(() -> new NotFoundException(
            "No invoices exist in the system for customer id: " + id + ". Please check your customer Id!"));
      List<Invoice> invoiceList = optionalAllInvoices.get();
      Iterator<Invoice> iter = invoiceList.iterator();
      List<CustomerInvoiceLevelupViewmodel> viewmodelList = new ArrayList<>();
   
      while(iter.hasNext()) {
         viewmodelList.add(buildCustomerInvoiceLevelupViewmodel(iter.next()));
      }
      return viewmodelList;
   
   }

   // build CustomerInvoiceLevelupViewmodel
   private CustomerInvoiceLevelupViewmodel buildCustomerInvoiceLevelupViewmodel(Invoice invoice) {
      // Get the invoice submitted
      Optional<Customer> optionalCustomer = Optional.ofNullable(customerService.getCustomer(invoice.getCustomerId()));
      optionalCustomer.orElseThrow(() -> new NotFoundException(
            "Customer Id: " + invoice.getCustomerId() + " not found. Please check customer-service!"));
      Customer customer = optionalCustomer.get();
   
      // get Level Up
      Optional<LevelUp> optionalLevelUp = Optional.ofNullable(levelUpService.getLevelUpByCustomer(invoice.getCustomerId()));
   
      // populate CustomerInvoiceLevelUpViewmodel
      CustomerInvoiceLevelupViewmodel viewmodel = new CustomerInvoiceLevelupViewmodel();
      viewmodel.setCustomerId(invoice.getCustomerId());
      viewmodel.setInvoiceId(invoice.getInvoiceId());
      viewmodel.setInvoice(invoice);
      viewmodel.setInvoiceValue(new BigDecimal(
            calculateInvoiceValue(invoice.getInvoiceItems()))
            .setScale(2, RoundingMode.HALF_EVEN));
      
      viewmodel.setCustomer(customer);
      if(optionalLevelUp.isPresent()) {
         viewmodel.setLevelUp(optionalLevelUp.get());
      }
   
      return viewmodel;
   }
   
   public Product getProduct(int id) {
      // Get the product submitted
      Optional<Product> optionalProduct = Optional.ofNullable(productService.getProduct(id));
      optionalProduct.orElseThrow(() -> new NotFoundException(
            "Product Id: " + id + " not found. Please check your Product Id!"));
      return optionalProduct.get();
   }
   
   public List<ProductsInInventoryViewmodel> getProductsInInventory() {
      // Get inventory (all items in inventory above 0)
      Optional<List<Inventory>> optionalInventoryList = Optional.ofNullable(inventoryService
            .getAllInventory()
            .stream()
            .filter(inventory -> inventory.getQuantity() > 0)
            .collect(Collectors.toList()));
      
      optionalInventoryList.orElseThrow(() -> new NotFoundException(
            "No products in Inventory Found. Possible that all inventory is finished!"));
   
      List<ProductsInInventoryViewmodel> productsInInv = new ArrayList<>();
      Inventory inventory = new Inventory();
      
      Iterator<Inventory> iter = optionalInventoryList.get().iterator();
      while (iter.hasNext()) {
         inventory = iter.next();
         productsInInv.add(new ProductsInInventoryViewmodel(
               inventory, productService.getProduct(inventory.getProductId())));
      }
      
      return productsInInv;
   }
   
   public List<Product> getProductByInvoiceId(int id) {
      
      Optional<Invoice> optionalInvoice = Optional.ofNullable(invoiceService.getInvoice(id));
      optionalInvoice.orElseThrow(() -> new NotFoundException(
            "Invoice Id: " + id + " not found. Please check your Invoice Id!"));
   
      // 2. get a list of invoice items on the invoice
      List<InvoiceItem> itemList = optionalInvoice.get().getInvoiceItems();
   
      // 3. get inventory record using the inventory Id on the Invoice Item from inventory-service
      List<InvoiceItemInventoryProductViewmodel> productViewmodelList = new ArrayList<>();
      InvoiceItem invoiceItem = new InvoiceItem();
      Inventory inventory = new Inventory();
      Product product = new Product();
      List<Product> productList = new ArrayList<>();
      Iterator<InvoiceItem> iter = itemList.iterator();
      while(iter.hasNext()) {
         invoiceItem = iter.next();
         inventory = inventoryService.getInventory(invoiceItem.getInventoryId());
         product = productService.getProduct(inventory.getProductId());
         productList.add(product);
      }
   
      return productList;
   }
   
   public int getLevelUpPointsByCustomerId(int id) {
      return 0;
   }
   
   public double calculateInvoiceValue(List <InvoiceItem> itemList) {
      double invoiceValue = 0.00;
      InvoiceItem invoiceItem = new InvoiceItem();
      Iterator<InvoiceItem> iter = itemList.iterator();
      while (iter.hasNext()) {
         invoiceItem = iter.next();
         invoiceValue += invoiceItem.getUnitPrice().doubleValue() * invoiceItem.getQuantity();
      }
      
      return invoiceValue;
   }
}

