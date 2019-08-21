package com.trilogyed.productservice.dao;

import com.trilogyed.productservice.model.Product;

import java.util.List;

public interface ProductDao {
    public Product createProduct(Product product);
    public Product getProduct(int productId);
    public List<Product> getAllProducts();
    public void amendProduct(Product product);
    public void deleteProduct(int productId);
}
