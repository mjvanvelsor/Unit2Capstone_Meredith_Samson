package com.trilogyed.retailapiservice.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.trilogyed.retailapiservice.model.Product;
import com.trilogyed.retailapiservice.service.ServiceLayer;
import com.trilogyed.retailapiservice.viewmodel.CustomerInvoiceViewModel;
import com.trilogyed.retailapiservice.viewmodel.CustomerOrderViewModel;
import com.trilogyed.retailapiservice.viewmodel.ProductsInInventoryViewModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

@RestController
@RefreshScope
@Service
public class RetailController {
   public static final String EXCHANGE = "levelup-exchange";
   public static final String ROUTING_KEY = "levelup.create.#";
   
   @Autowired
   ServiceLayer service;
   
   @Autowired
   private RabbitTemplate rabbitTemplate;
   public RetailController(RabbitTemplate rabbitTemplate) {
      this.rabbitTemplate = rabbitTemplate;
   }

   @ResponseStatus(HttpStatus.CREATED)
   @RequestMapping(value = "/invoices", method = RequestMethod.POST)
   public CustomerInvoiceViewModel submitInvoice(@RequestBody CustomerOrderViewModel order) {
      CustomerInvoiceViewModel civm = service.submitInvoice(order);
      
      // Send LevelUp Points via the Queue to the levelup-service
      System.out.println("Sending Levelup message via queue ...");
      rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, civm.getLevelUp());
      System.out.println("Message sent ...");
      
      return civm;
   }
   
   @ResponseStatus(HttpStatus.OK)
   @RequestMapping(value = "/invoices/{id}", method = RequestMethod.GET)
   public CustomerInvoiceViewModel getInvoice(@PathVariable int id) {
      return service.getInvoice(id);
   }
   
   @ResponseStatus(HttpStatus.OK)
   @RequestMapping(value = "/invoices", method = RequestMethod.GET)
   public List<CustomerInvoiceViewModel> getAllInvoices() {
      return service.getAllInvoices();
   }
   
   @ResponseStatus(HttpStatus.OK)
   @RequestMapping(value = "/invoices/customer/{id}", method = RequestMethod.GET)
   public List<CustomerInvoiceViewModel> getInvoicesByCustomerId(@PathVariable int id) {
      return service.getInvoicesByCustomerId(id);
   }
   
   @ResponseStatus(HttpStatus.OK)
   @RequestMapping(value = "/products/inventory", method = RequestMethod.GET)
   public List<ProductsInInventoryViewModel> getProductsInInventory() {
      return service.getProductsInInventory();
   }
   
   @ResponseStatus(HttpStatus.OK)
   @RequestMapping(value = "/products/{id}", method = RequestMethod.GET)
   public Product getProduct(@PathVariable int id) {
      return service.getProduct(id);
   }
   
   @ResponseStatus(HttpStatus.OK)
   @RequestMapping(value = "/products/invoice/{id}", method = RequestMethod.GET)
   public List<Product> getProductByInvoiceId(@PathVariable int id) {
      return service.getProductByInvoiceId(id);
   }
   

   @ResponseStatus(HttpStatus.OK)
   @RequestMapping(value = "/levelup/customer/{id}", method = RequestMethod.GET)
   @HystrixCommand(fallbackMethod = "getPointsFallBack")
   public int getLevelUpPointsByCustomerId(int id) {
      return service.getLevelUpPointsByCustomerId(id).getPoints();
   }

    public int getPointsFallBack(){
        return -1;
    }
   
}
