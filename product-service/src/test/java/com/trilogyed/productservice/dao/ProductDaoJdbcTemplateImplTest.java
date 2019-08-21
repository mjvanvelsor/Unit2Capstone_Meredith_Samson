package com.trilogyed.productservice.dao;

import com.trilogyed.productservice.model.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductDaoJdbcTemplateImplTest {

    @Autowired
    ProductDao dao;

    @Before
    public void setUp() throws Exception {
        List<Product> products = dao.getAllProducts();
        products.stream().forEach(product -> dao.deleteProduct(product.getProductId()));
    }

    @Test
    public void createGetDeleteProduct() {
        Product product = new Product();
        product.setProductId(1);
        product.setProductName("pants");
        product.setProductDescription("clothing");
        product.setListPrice(new BigDecimal(39.99).setScale(2, BigDecimal.ROUND_HALF_UP));
        product.setUnitCost(new BigDecimal(9.99).setScale(2, BigDecimal.ROUND_HALF_UP));

        dao.createProduct(product);
        assertEquals(product, dao.getProduct(product.getProductId()));
        dao.deleteProduct(product.getProductId());
        assertNull(dao.getProduct(product.getProductId()));
    }

    @Test
    public void getAllProducts() {
        Product product = new Product();
        product.setProductId(1);
        product.setProductName("pants");
        product.setProductDescription("clothing");
        product.setListPrice(new BigDecimal(39.99).setScale(2, BigDecimal.ROUND_HALF_UP));
        product.setUnitCost(new BigDecimal(9.99).setScale(2, BigDecimal.ROUND_HALF_UP));
        dao.createProduct(product);

        Product product1 = new Product();
        product1.setProductId(2);
        product1.setProductName("tennis shoes");
        product1.setProductDescription("shoes");
        product1.setListPrice(new BigDecimal(99.99).setScale(2, BigDecimal.ROUND_HALF_UP));
        product1.setUnitCost(new BigDecimal(19.99).setScale(2, BigDecimal.ROUND_HALF_UP));
        dao.createProduct(product1);

        List<Product> products = dao.getAllProducts();
        assertEquals(products.size(), 2);
    }

    @Test
    public void amendProduct() {
        Product product = new Product();
        product.setProductId(1);
        product.setProductName("pants");
        product.setProductDescription("clothing");
        product.setListPrice(new BigDecimal(39.99).setScale(2, BigDecimal.ROUND_HALF_UP));
        product.setUnitCost(new BigDecimal(9.99).setScale(2, BigDecimal.ROUND_HALF_UP));
        dao.createProduct(product);

        product.setProductName("dress pants");
        dao.amendProduct(product);

        assertEquals(product, dao.getProduct(product.getProductId()));
    }

}