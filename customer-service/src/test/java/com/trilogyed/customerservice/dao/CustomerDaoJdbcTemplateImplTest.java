package com.trilogyed.customerservice.dao;

import com.trilogyed.customerservice.model.Customer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerDaoJdbcTemplateImplTest {
    @Autowired
    CustomerDao dao;

    @Before
    public void setUp() throws Exception {
        List<Customer> customers = dao.getAllCustomers();
        customers.stream().forEach(customer -> dao.deleteCustomer(customer.getCustomerId()));
    }

    @Test
    public void createCustomer() {
        Customer customer = new Customer();
        customer.setCustomerId(1);
        customer.setFirstName("Mary");
        customer.setLastName("Doe");
        customer.setStreet("123 Sesame Street");
        customer.setCity("Charlotte");
        customer.setZip("23456");
        customer.setEmail("marydoe@gmail.com");
        customer.setPhone("808-435-6774");

        dao.createCustomer(customer);
        assertEquals(customer, dao.getCustomer(customer.getCustomerId()));
        dao.deleteCustomer(customer.getCustomerId());
        assertNull(dao.getCustomer(customer.getCustomerId()));
    }

    @Test
    public void getAllCustomers() {
        Customer customer = new Customer();
        customer.setCustomerId(1);
        customer.setFirstName("Mary");
        customer.setLastName("Doe");
        customer.setStreet("123 Sesame Street");
        customer.setCity("Charlotte");
        customer.setZip("23456");
        customer.setEmail("marydoe@gmail.com");
        customer.setPhone("808-435-6774");
        dao.createCustomer(customer);

        Customer customer1 = new Customer();
        customer1.setCustomerId(2);
        customer1.setFirstName("Christina");
        customer1.setLastName("Smith");
        customer1.setStreet("56 Charleston Street");
        customer1.setCity("Orlando");
        customer1.setZip("12345");
        customer1.setEmail("christinasmith@gmail.com");
        customer1.setPhone("789-123-4567");
        dao.createCustomer(customer1);

        List<Customer> customers = dao.getAllCustomers();
        assertEquals(customers.size(), 2);

    }

    @Test
    public void amendCustomer() {
        Customer customer = new Customer();
        customer.setCustomerId(1);
        customer.setFirstName("Mary");
        customer.setLastName("Doe");
        customer.setStreet("123 Sesame Street");
        customer.setCity("Charlotte");
        customer.setZip("23456");
        customer.setEmail("marydoe@gmail.com");
        customer.setPhone("808-435-6774");
        dao.createCustomer(customer);

        customer.setCity("Orlando");
        dao.amendCustomer(customer);

        assertEquals(customer, dao.getCustomer(customer.getCustomerId()));
    }
}