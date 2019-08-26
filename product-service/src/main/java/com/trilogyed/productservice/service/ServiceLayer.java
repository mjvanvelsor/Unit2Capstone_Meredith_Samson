package com.trilogyed.productservice.service;

import com.trilogyed.productservice.dao.ProductDao;
import com.trilogyed.productservice.exception.NotFoundException;
import com.trilogyed.productservice.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ServiceLayer {

    ProductDao dao;
    @Autowired
    public ServiceLayer(ProductDao dao) {
        this.dao = dao;
    }

    public Product createProduct(Product product){
        return dao.createProduct(product);
    }
    public Product getProduct(int productId){
        Optional<Product> optionalProduct = Optional.ofNullable(dao.getProduct(productId));
        optionalProduct.orElseThrow(
              () -> new NotFoundException("Product Id: " + productId + " not found in inventory"));
        return optionalProduct.get();
    }
    
    public List<Product> getAllProducts(){
        Optional<List<Product>> optionalAllProducts = Optional.ofNullable(dao.getAllProducts());
        optionalAllProducts.orElseThrow(() -> new NotFoundException(
              "No products exist in the system!"));
        return optionalAllProducts.get();
    }
    
    public void amendProduct(Product product){
        dao.amendProduct(product);
    }
    
    public void deleteProduct(int productId){
        dao.deleteProduct(productId);
    }
}
