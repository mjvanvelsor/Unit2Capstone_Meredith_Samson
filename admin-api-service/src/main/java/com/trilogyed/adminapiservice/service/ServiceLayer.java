package com.trilogyed.adminapiservice.service;

import com.trilogyed.adminapiservice.model.Customer;
import com.trilogyed.adminapiservice.model.Inventory;
import com.trilogyed.adminapiservice.model.LevelUp;
import com.trilogyed.adminapiservice.model.Product;
import com.trilogyed.adminapiservice.util.feign.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.stereotype.Component;

import java.util.List;

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
   
   public Product createProduct(Product product) {
      return productService.createProduct(product);
   }
   
   public List<Product> getAllProducts() {
      return productService.getAllProducts();
   }
   
   public void updateProduct(Product product) {
      productService.amendProduct(product);
   }
   
   public void deleteProduct(int productId) {
      productService.deleteProduct(productId);
   }
   
   public Customer createCustomer(Customer customer) {
      return customerService.createCustomer(customer);
   }
   
   public Customer getCustomer(int customerId) {
      return customerService.findCustomer(customerId);
   }
   
   public List<Customer> getAllCustomers() {
      return customerService.getAllCustomers();
   }
   
   public void amendCustomer(Customer customer) {
      customerService.amendCustomer(customer);
   }
   
   public void deleteCustomer(int customerId) {
      customerService.deleteCustomer(customerId);
   }
   
   public Inventory createInventory(Inventory inventory) {
      return inventoryService.createInventory(inventory);
   }
   
   public Inventory getInventory(int inventoryId) {
      return inventoryService.getInventory(inventoryId);
   }
   
   public List<Inventory> getAllInventory() {
      return inventoryService.getAllInventory();
   }
   
   public void amendInventory(Inventory inventory) {
      inventoryService.amendInventory(inventory);
   }
   
   public void deleteInventory(int inventoryId) {
      inventoryService.deleteInventory(inventoryId);
   }
   
   public LevelUp createLevelUp(LevelUp levelUp) {
      return levelUpService.createLevelUp(levelUp);
   }
   
   public LevelUp getLevelUp(int levelUpId) {
      return levelUpService.getLevelUp(levelUpId);
   }
   
   public List<LevelUp> getAllLevelUps() {
      return levelUpService.getAllLevelUp();
   }
   
   public void amendLevelUp(LevelUp levelUp) {
      levelUpService.amendLevelUp(levelUp);
   }
   
   public void deleteLevelUp(int levelUpId) {
      levelUpService.deleteLevelUp(levelUpId);
   }
   
}
