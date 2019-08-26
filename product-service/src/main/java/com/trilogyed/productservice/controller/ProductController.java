package com.trilogyed.productservice.controller;

import com.trilogyed.productservice.exception.NotFoundException;
import com.trilogyed.productservice.model.Product;
import com.trilogyed.productservice.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RefreshScope
@RequestMapping("/products")
public class ProductController {
   
   @Autowired
   ServiceLayer service;
   
   @PostMapping
   @ResponseStatus(value = HttpStatus.OK)
   public Product createProduct(@RequestBody @Valid Product product, Principal principal) {
      product = service.createProduct(product);
      return product;
   }
   
   @GetMapping("/{productId}")
   @ResponseStatus(value = HttpStatus.OK)
   public Product getProduct(@PathVariable int productId, Principal principal)
         throws NotFoundException {
      Product product = service.getProduct(productId);
      if (product == null) {
         throw new NotFoundException("Product could not be retrieved for id: " + productId);
      }
      return product;
   }
   
   @GetMapping
   @ResponseStatus(value = HttpStatus.OK)
   public List<Product> findAllProducts(Principal principal) {
      List<Product> products = service.getAllProducts();
      return products;
   }
   
   @PutMapping
   @ResponseStatus(value = HttpStatus.NO_CONTENT)
   public void updateProduct(@RequestBody @Valid Product product, Principal principal) {
      service.amendProduct(product);
   }
   
   @DeleteMapping("/{productId}")
   @ResponseStatus(value = HttpStatus.NO_CONTENT)
   public void deleteProduct(@PathVariable int productId, Principal principal) {
      service.deleteProduct(productId);
   }
}
