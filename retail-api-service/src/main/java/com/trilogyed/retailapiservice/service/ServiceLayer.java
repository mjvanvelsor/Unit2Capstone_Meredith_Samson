package com.trilogyed.retailapiservice.service;

import com.trilogyed.retailapiservice.exception.InsufficientInventoryException;
import com.trilogyed.retailapiservice.exception.NotFoundException;
import com.trilogyed.retailapiservice.exception.ServiceFailException;
import com.trilogyed.retailapiservice.model.*;
import com.trilogyed.retailapiservice.util.feign.*;
import com.trilogyed.retailapiservice.viewmodel.CustomerInvoiceViewModel;
import com.trilogyed.retailapiservice.viewmodel.CustomerOrderViewModel;
import com.trilogyed.retailapiservice.viewmodel.InvoiceitemInventoryProductViewModel;
import com.trilogyed.retailapiservice.viewmodel.ProductsInInventoryViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@EnableEurekaClient
@EnableFeignClients
@EnableDiscoveryClient
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
   
   @Transactional
   public CustomerInvoiceViewModel submitInvoice(CustomerOrderViewModel order) {
      Optional<Customer> optionalCustomer = Optional.ofNullable(customerService.findCustomer(order.getCustomerId()));
      optionalCustomer.orElseThrow(() -> new NotFoundException(
            "Customer Id: " + order.getCustomerId() + " not found. Please check your Customer Id!"));

      Customer customer = optionalCustomer.get();

      // prepare invoice
      Invoice invoice = new Invoice();
      invoice.setCustomerId(order.getCustomerId());
      invoice.setPurchaseDate(order.getPurchaseDate());
      
      // prepare invoice items - that is put a list price on each item,
      // check if item exists and if order can be fulfilled,
      // throw exception if it fails the check
      OrderItem orderItem = new OrderItem();
      Optional<Product> optionalProduct;
      List<InvoiceItem> invoiceItems = new ArrayList<>();
      BigDecimal listPrice = new BigDecimal(0.00).setScale(2, RoundingMode.HALF_EVEN);
      int inventoryQty = 0;
      Iterator<OrderItem> iter = order.getOrderItemList().iterator();
      while(iter.hasNext()) {
         orderItem = iter.next();
         optionalProduct = Optional.ofNullable(productService.getProduct(orderItem.getInventoryId()));
         String errorMsg = "Product Id: " + orderItem.getInventoryId() + " not found in inventory";
         optionalProduct.orElseThrow(
               () -> new NotFoundException(errorMsg));
         
         OrderItem finalOrderItem = orderItem;
   
         // check if order quantity can be fulfilled from available inventory
         inventoryQty = inventoryService.getInventory(orderItem.getInventoryId()).getQuantity();
         if (inventoryQty < orderItem.getQuantity()) {
            throw new InsufficientInventoryException(
                  "Product Id: " + orderItem.getInventoryId() +
                        " " + optionalProduct.get().getProductName() +
                        " has insufficient inventory of: " + inventoryQty +
                        " against order quantity: " + orderItem.getQuantity() +
                        ". Please reduce order quantity");
         }
         
         listPrice = optionalProduct.get().getListPrice();
         
         invoiceItems.add(
               new InvoiceItem(orderItem.getInventoryId(), orderItem.getQuantity(), listPrice));
      }
      
      // add invoiceItems to invoice
      invoice.setInvoiceItems(invoiceItems);
   
      // call invoice-service to write the invoice. Service may fail to create the invoice.
      // so check for null and throw exception
      Optional<Invoice> optionalInvoice = Optional.ofNullable(invoiceService.createInvoice(invoice));
      optionalInvoice.orElseThrow(() -> new ServiceFailException("invoice-service error:- Invoice not created"));
      
      // calculate invoice value, so we can determine LevelUp points.
      double invoiceValue = calculateInvoiceValue(invoiceItems);
      
      // create LevelUp object if points "Math.floorDiv((int)invoiceValue, 50) * 10" greater than 0
      LevelUp levelUp = null;
      if (Math.floorDiv((int)invoiceValue, 50) * 10 > 0) {
         levelUp = new LevelUp();
         levelUp.setCustomerId(customer.getCustomerId());
         levelUp.setMemberDate(LocalDate.now());
         levelUp.setPoints(Math.floorDiv((int)invoiceValue, 50) * 10);
      }
   
      // populate CustomerInvoiceLevelupViewModel
      CustomerInvoiceViewModel viewModel = new CustomerInvoiceViewModel();
      viewModel.setCustomerId(order.getCustomerId());
      viewModel.setCustomer(customer);
      viewModel.setLevelUp(levelUp);
      viewModel.setInvoiceValue(new BigDecimal(invoiceValue).setScale(2, RoundingMode.HALF_EVEN));
      viewModel.setInvoiceId(optionalInvoice.get().getInvoiceId());
      viewModel.setInvoice(optionalInvoice.get());
      return viewModel;
   }
   
   public CustomerInvoiceViewModel getInvoice(int id) {
      // Get the invoice submitted
      Optional<Invoice> optionalInvoice = Optional.ofNullable(invoiceService.getInvoice(id));
      optionalInvoice.orElseThrow(() -> new NotFoundException(
            "Invoice Id: " + id + " not found. Please check your Invoice Id!"));
      Invoice invoice = optionalInvoice.get();
   
      return buildCustomerInvoiceViewModel(invoice);
      
   }
   
   public List<CustomerInvoiceViewModel> getInvoicesByCustomer(int id) {
      Optional<List<Invoice>> optionalAllInvoices = Optional.ofNullable(invoiceService.getInvoicesByCustomer(id));
      optionalAllInvoices.orElseThrow(() -> new NotFoundException(
            "No invoices exist in the system for customer id: " + id + ". Please check your customer Id!"));
      List<Invoice> invoiceList = optionalAllInvoices.get();
      Iterator<Invoice> iter = invoiceList.iterator();
      List<CustomerInvoiceViewModel> viewmodelList = new ArrayList<>();
      
      while(iter.hasNext()) {
         viewmodelList.add(buildCustomerInvoiceViewModel(iter.next()));
      }
      return viewmodelList;
      
   }
   
   public List<CustomerInvoiceViewModel> getAllInvoices() {
      Optional<List<Invoice>> optionalAllInvoices = Optional.ofNullable(invoiceService.getAllInvoices());
      optionalAllInvoices.orElseThrow(() -> new NotFoundException(
            "No invoices exist in the system. Please ensure invoice-service is running if you know invoices do exist!"));
      List<Invoice> invoiceList = optionalAllInvoices.get();
      Iterator<Invoice> iter = invoiceList.iterator();
      List<CustomerInvoiceViewModel> viewmodelList = new ArrayList<>();
      
      while(iter.hasNext()) {
         viewmodelList.add(buildCustomerInvoiceViewModel(iter.next()));
      }
      return viewmodelList;
   }

   // build CustomerInvoiceLevelupViewmodel
   private CustomerInvoiceViewModel buildCustomerInvoiceViewModel(Invoice invoice) {
      // Get the invoice submitted
      Optional<Customer> optionalCustomer = Optional.ofNullable(customerService.findCustomer(invoice.getCustomerId()));
      optionalCustomer.orElseThrow(() -> new NotFoundException(
            "Customer Id: " + invoice.getCustomerId() + " not found. Please check customer-service!"));
      Customer customer = optionalCustomer.get();
   
      // get Level Up
      Optional<LevelUp> optionalLevelUp = Optional.ofNullable(levelUpService.getLevelUpByCustomer(invoice.getCustomerId()));
   
      // populate CustomerInvoiceLevelUpViewmodel
      CustomerInvoiceViewModel viewmodel = new CustomerInvoiceViewModel();
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
   
   public List<ProductsInInventoryViewModel> getProductsInInventory() {
      // Get inventory (all items in inventory above 0)
      Optional<List<Inventory>> optionalInventoryList = Optional.ofNullable(inventoryService
            .getAllInventory()
            .stream()
            .filter(inventory -> inventory.getQuantity() > 0)
            .collect(Collectors.toList()));
      
      optionalInventoryList.orElseThrow(() -> new NotFoundException(
            "No products in Inventory Found. Possible that all inventory is finished!"));
   
      List<ProductsInInventoryViewModel> productsInInv = new ArrayList<>();
      Inventory inventory = new Inventory();
      
      Iterator<Inventory> iter = optionalInventoryList.get().iterator();
      while (iter.hasNext()) {
         inventory = iter.next();
         productsInInv.add(new ProductsInInventoryViewModel(
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
      List<InvoiceitemInventoryProductViewModel> productViewmodelList = new ArrayList<>();
      InvoiceItem invoiceItem = new InvoiceItem();
      Inventory inventory = new Inventory();
      Product product = new Product();
      List<Product> productList = new ArrayList<>();
      Iterator<InvoiceItem> iter = itemList.iterator();
      while(iter.hasNext()) {
         invoiceItem = iter.next();
         Optional<Inventory> optionalInventory = Optional.ofNullable(inventoryService.getInventory(invoiceItem.getInventoryId()));
         String errorMsg = "Inventory Item: " + invoiceItem.getInventoryId() + " on invoice id: " + id + " Not Found in inventory";
         optionalInventory.orElseThrow(()-> new NotFoundException(errorMsg));
         Optional<Product> optionalProduct = Optional.ofNullable(productService.getProduct(optionalInventory.get().getProductId()));
         optionalProduct.orElseThrow(()-> new NotFoundException(errorMsg));
         productList.add(optionalProduct.get());
      }
   
      return productList;
   }
   
   public LevelUp getLevelUpPointsByCustomerId(int id) {
      return levelUpService.getLevelUpByCustomer(id);
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

