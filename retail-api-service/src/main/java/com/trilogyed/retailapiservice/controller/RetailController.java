package com.trilogyed.retailapiservice.controller;

import com.trilogyed.retailapiservice.model.Invoice;
import com.trilogyed.retailapiservice.model.Product;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RetailController {
   
   @RequestMapping(value = "/invoices", method = RequestMethod.POST)
   public Invoice submitInvoice(@RequestBody Invoice invoice) {
      return null;
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
