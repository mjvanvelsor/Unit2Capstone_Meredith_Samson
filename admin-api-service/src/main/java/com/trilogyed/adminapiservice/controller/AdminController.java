package com.trilogyed.adminapiservice.controller;

import com.trilogyed.adminapiservice.model.Customer;
import com.trilogyed.adminapiservice.model.Inventory;
import com.trilogyed.adminapiservice.model.LevelUp;
import com.trilogyed.adminapiservice.model.Product;
import com.trilogyed.adminapiservice.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RefreshScope
public class AdminController {
   
   @Autowired
   ServiceLayer service;
   
   @ResponseStatus(HttpStatus.CREATED)
   @RequestMapping(value = "/products", method = RequestMethod.POST)
   public Product createProduct(@Valid @RequestBody Product product) {
      return service.createProduct(product);
   }
   
   @ResponseStatus(HttpStatus.OK)
   @RequestMapping(value = "/products", method = RequestMethod.GET)
   public List<Product> getAllProducts() {
      return service.getAllProducts();
   }
   
   @ResponseStatus(HttpStatus.OK)
   @RequestMapping(value = "/products", method = RequestMethod.PUT)
   public void updateProduct(@Valid @RequestBody Product product) {
   service.updateProduct(product);
   }
   
   @ResponseStatus(HttpStatus.NO_CONTENT)
   @RequestMapping(value = "/products/{id}", method = RequestMethod.DELETE)
   public void deleteProduct(@Valid @PathVariable int id) {
   service.deleteProduct(id);
   }
   
   @ResponseStatus(HttpStatus.CREATED)
   @RequestMapping(value = "/customers", method = RequestMethod.POST)
   public Customer createCustomer(@Valid @RequestBody Customer customer) {
      return service.createCustomer(customer);
   }
   
   @ResponseStatus(HttpStatus.OK)
   @RequestMapping(value = "/customers/{customerId}", method = RequestMethod.GET)
   public Customer getCustomer(@Valid @PathVariable int customerId) {
      return service.getCustomer(customerId);
   }
   
   @ResponseStatus(HttpStatus.OK)
   @RequestMapping(value = "/customers", method = RequestMethod.GET)
   public List<Customer> getAllCustomers() {
      return service.getAllCustomers();
   }
   
   @ResponseStatus(HttpStatus.OK)
   @RequestMapping(value = "/customers", method = RequestMethod.PUT)
   public void amendCustomer(@Valid @RequestBody Customer customer) {
      service.amendCustomer(customer);
   }
   
   @ResponseStatus(HttpStatus.NO_CONTENT)
   @RequestMapping(value = "/customers/{customerId}", method = RequestMethod.DELETE)
   public void deleteCustomer(@Valid @PathVariable int customerId) {
      service.deleteCustomer(customerId);
   }
   
   @ResponseStatus(HttpStatus.CREATED)
   @RequestMapping(value = "/inventory", method = RequestMethod.POST)
   public Inventory createInventory(@Valid @RequestBody Inventory inventory) {
      return service.createInventory(inventory);
   }
   
   @ResponseStatus(HttpStatus.OK)
   @RequestMapping(value = "/inventory/{inventoryId}", method = RequestMethod.PUT)
   public Inventory getInventory(@Valid @PathVariable int inventoryId) {
      return service.getInventory(inventoryId);
   }
   
   @ResponseStatus(HttpStatus.OK)
   @RequestMapping(value = "/inventory", method = RequestMethod.GET)
   public List<Inventory> getAllInventory() {
      return service.getAllInventory();
   }
   
   @ResponseStatus(HttpStatus.OK)
   @RequestMapping(value = "/inventory", method = RequestMethod.PUT)
   public void amendInventory(@Valid @RequestBody Inventory inventory) {
      service.amendInventory(inventory);
   }
   
   @ResponseStatus(HttpStatus.NO_CONTENT)
   @RequestMapping(value = "/inventory/{inventoryId}", method = RequestMethod.DELETE)
   public void deleteInventory(@Valid @PathVariable int inventoryId) {
      service.deleteInventory(inventoryId);
   }
   
   @ResponseStatus(HttpStatus.CREATED)
   @RequestMapping(value = "/levelups", method = RequestMethod.POST)
   public LevelUp createLevelUp(@Valid @RequestBody LevelUp levelUp) {
      return service.createLevelUp(levelUp);
   }
   
   @ResponseStatus(HttpStatus.OK)
   @RequestMapping(value = "/levelups/{levelUpId}", method = RequestMethod.GET)
   public LevelUp getLevelUp(@Valid @PathVariable int levelUpId) {
      return service.getLevelUp(levelUpId);
   }
   
   @ResponseStatus(HttpStatus.OK)
   @RequestMapping(value = "/levelups", method = RequestMethod.GET)
   public List<LevelUp> getAllLevelUps() {
      return service.getAllLevelUps();
   }
   
   @ResponseStatus(HttpStatus.OK)
   @RequestMapping(value = "/levelups", method = RequestMethod.PUT)
   public void amendLevelUp(@Valid @RequestBody LevelUp levelUp) {
      service.amendLevelUp(levelUp);
   }
   
   @ResponseStatus(HttpStatus.NO_CONTENT)
   @RequestMapping(value = "/levelups/{levelUpId}", method = RequestMethod.DELETE)
   public void deleteLevelUp(@Valid @PathVariable int levelUpId) {
      service.deleteLevelUp(levelUpId);
   }
   
}

