package com.trilogyed.adminapiservice.controller;

import com.trilogyed.adminapiservice.model.Product;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AdminController {
   
   // Admin Endpoints
   
   @RequestMapping(value = "/products", method = RequestMethod.POST)
   public Product createProduct(@RequestBody Product product) {
      return null;
   }
   
   // getProductById is above in the Retail endpoints
   
   @RequestMapping(value = "/products", method = RequestMethod.GET)
   public List<Product> getAllProducts() {
      return null;
   }
   
   @RequestMapping(value = "/products/{id}", method = RequestMethod.PUT)
   public void updateProduct(@PathVariable int id, @RequestBody Product product) {
   
   }
   
   @RequestMapping(value = "/products/{id}", method = RequestMethod.DELETE)
   public void deleteProduct(@PathVariable int id) {
   
   }
}

