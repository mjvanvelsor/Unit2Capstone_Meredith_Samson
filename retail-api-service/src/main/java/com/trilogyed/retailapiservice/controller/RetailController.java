package com.trilogyed.retailapiservice.controller;

import com.trilogyed.retailapiservice.model.Invoice;
import com.trilogyed.retailapiservice.model.Product;
import com.trilogyed.retailapiservice.service.ServiceLayer;
import com.trilogyed.retailapiservice.viewmodel.CustomerInvoiceViewModel;
import com.trilogyed.retailapiservice.viewmodel.CustomerOrderViewModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RefreshScope
public class RetailController {
   public static final String EXCHANGE = "levelup-exchange";
   public static final String ROUTING_KEY = "levelup.create.#";
   
   @Autowired
   ServiceLayer service;
   
   @Autowired
   private RabbitTemplate rabbitTemplate;
   
   @RequestMapping(value = "/invoices", method = RequestMethod.POST)
   //public CustomerInvoiceLevelupViewmodel submitInvoice(CustomerInvoiceLevelupViewmodel order)
   public CustomerInvoiceViewModel submitInvoice(@RequestBody CustomerOrderViewModel order) {
      CustomerInvoiceViewModel invoice = service.submitInvoice(order);
      return invoice;
   }
   
   @RequestMapping(value = "/invoices/{id}", method = RequestMethod.GET)
   public Invoice getInvoice(@PathVariable int id) {
      return null;
   }
   
   @RequestMapping(value = "/invoices", method = RequestMethod.GET)
   public List<Invoice> getAllInvoices() {
      return null;
   }
   
   @RequestMapping(value = "/invoices/customer/{id}", method = RequestMethod.GET)
   public List<Invoice> getInvoicesByCustomerId(@PathVariable int id) {
      return null;
   }
   
   @RequestMapping(value = "/products/inventory", method = RequestMethod.GET)
   public List<Product> getProductsInInventory() {
      return null;
   }
   
   @RequestMapping(value = "/products/{id}", method = RequestMethod.GET)
   public Product getProduct(@PathVariable int id) {
      return null;
   }
   
   @RequestMapping(value = "/products/invoice/{id}", method = RequestMethod.GET)
   public List<Product> getProductByInvoiceId(@PathVariable int id) {
      return null;
   }
   
   @RequestMapping(value = "/levelup/customer/{id}", method = RequestMethod.GET)
   public int getLevelUpPointsByCustomerId(int id) {
      return 0;
   }
   
}
