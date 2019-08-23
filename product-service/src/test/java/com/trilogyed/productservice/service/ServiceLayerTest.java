package com.trilogyed.productservice.service;

import com.trilogyed.productservice.dao.ProductDao;
import com.trilogyed.productservice.dao.ProductDaoJdbcTemplateImpl;
import com.trilogyed.productservice.model.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceLayerTest {

    ProductDao productDao;
    ServiceLayer service;

    @Before
    public void setUp() throws Exception {
        setUpProductDaoMock();

        service = new ServiceLayer(productDao);
    }

    private void setUpProductDaoMock(){
        productDao = mock(ProductDaoJdbcTemplateImpl.class);
        Product product = new Product();
        product.setProductName("pants");
        product.setProductDescription("clothing");
        product.setListPrice(new BigDecimal(39.99).setScale(2, BigDecimal.ROUND_HALF_UP));
        product.setUnitCost(new BigDecimal(9.99).setScale(2, BigDecimal.ROUND_HALF_UP));

        Product product1 = new Product();
        product1.setProductId(1);
        product1.setProductName("pants");
        product1.setProductDescription("clothing");
        product1.setListPrice(new BigDecimal(39.99).setScale(2, BigDecimal.ROUND_HALF_UP));
        product1.setUnitCost(new BigDecimal(9.99).setScale(2, BigDecimal.ROUND_HALF_UP));

        List<Product> products = new ArrayList<>();
        products.add(product1);

        doReturn(product1).when(productDao).createProduct(product);
        doReturn(product1).when(productDao).getProduct(1);
        doReturn(products).when(productDao).getAllProducts();
    }

    @Test
    public void createGetProduct() {
        Product product = new Product();
        product.setProductName("pants");
        product.setProductDescription("clothing");
        product.setListPrice(new BigDecimal(39.99).setScale(2, BigDecimal.ROUND_HALF_UP));
        product.setUnitCost(new BigDecimal(9.99).setScale(2, BigDecimal.ROUND_HALF_UP));

        Product item = service.createProduct(product);
        Product item1 = service.getProduct(item.getProductId());
        assertEquals(item1, item);
    }

    @Test
    public void getAllProducts() {
       List<Product> products = service.getAllProducts();
       assertEquals(products.size(), 1);
    }

    @Test
    public void amendProduct() {
        Product product = new Product();
        product.setProductName("jeans");
        product.setProductDescription("clothing");
        product.setListPrice(new BigDecimal(39.99).setScale(2, BigDecimal.ROUND_HALF_UP));
        product.setUnitCost(new BigDecimal(9.99).setScale(2, BigDecimal.ROUND_HALF_UP));

        service.amendProduct(product);

        ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);
        verify(productDao).amendProduct(productCaptor.capture());
        assertEquals(product.getProductName(), productCaptor.getValue().getProductName());
    }

    @Test
    public void deleteProduct() {
        service.deleteProduct(1);
        Product product = service.getProduct(1);

        ArgumentCaptor<Integer> productCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(productDao).deleteProduct(productCaptor.capture());
        assertEquals(product.getProductId(), productCaptor.getValue().intValue());
    }
}