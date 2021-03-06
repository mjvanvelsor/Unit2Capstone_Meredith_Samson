package com.trilogyed.retailapiservice.util.feign;

import com.trilogyed.retailapiservice.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name = "product-service")
public interface ProductService {
   
   @RequestMapping(value = "/products", method = RequestMethod.POST)
   Product createProduct(@Valid @RequestBody Product product);
   
   @RequestMapping(value = "/products/{productId}", method = RequestMethod.GET)
   Product getProduct(@Valid @PathVariable("productId") int productId);
   
   @RequestMapping(value = "/products", method = RequestMethod.GET)
   List<Product> getAllProducts();
   
   @RequestMapping(value = "/products", method = RequestMethod.PUT)
   void amendProduct(@Valid @RequestBody Product product);
   
   @RequestMapping(value = "/products/{productId}", method = RequestMethod.DELETE)
   void deleteProduct(@Valid @PathVariable("productId") int productId);
   
}