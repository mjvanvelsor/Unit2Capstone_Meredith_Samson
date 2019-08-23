package com.trilogyed.customerservice.service;

import com.trilogyed.customerservice.dao.CustomerDao;
import com.trilogyed.customerservice.dao.CustomerDaoJdbcTemplateImpl;
import com.trilogyed.customerservice.model.Customer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceLayerTest {

    CustomerDao customerDao;
    ServiceLayer service;

    @Before
    public void setUp() throws Exception {
        setUpCustomerDaoMock();
        service = new ServiceLayer(customerDao);
    }

    private void setUpCustomerDaoMock(){
        customerDao = mock(CustomerDaoJdbcTemplateImpl.class);
        Customer customer = new Customer();
        customer.setFirstName("Mary");
        customer.setLastName("Doe");
        customer.setStreet("123 Sesame Street");
        customer.setCity("Charlotte");
        customer.setZip("23456");
        customer.setEmail("marydoe@gmail.com");
        customer.setPhone("808-435-6774");

        Customer customer1 = new Customer();
        customer1.setCustomerId(1);
        customer1.setFirstName("Mary");
        customer1.setLastName("Doe");
        customer1.setStreet("123 Sesame Street");
        customer1.setCity("Charlotte");
        customer1.setZip("23456");
        customer1.setEmail("marydoe@gmail.com");
        customer1.setPhone("808-435-6774");

        List<Customer> customers = new ArrayList<>();
        customers.add(customer1);

        doReturn(customer1).when(customerDao).createCustomer(customer);
        doReturn(customer1).when(customerDao).getCustomer(1);
        doReturn(customers).when(customerDao).getAllCustomers();

    }

    @Test
    public void createGetCustomer() {
        Customer customer = new Customer();
        customer.setFirstName("Mary");
        customer.setLastName("Doe");
        customer.setStreet("123 Sesame Street");
        customer.setCity("Charlotte");
        customer.setZip("23456");
        customer.setEmail("marydoe@gmail.com");
        customer.setPhone("808-435-6774");

        Customer customer1 = service.createCustomer(customer);
        Customer customer2 = service.getCustomer(customer1.getCustomerId());
        assertEquals(customer1, customer2);
    }

    @Test
    public void getAllCustomers() {
        List<Customer> customers = service.getAllCustomers();
        assertEquals(customers.size(), 1);
    }

    @Test
    public void amendCustomer() {
        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setStreet("123 Sesame Street");
        customer.setCity("Charlotte");
        customer.setZip("23456");
        customer.setEmail("johndoe@gmail.com");
        customer.setPhone("808-435-6774");

        service.amendCustomer(customer);

        ArgumentCaptor<Customer> customerCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(customerDao).amendCustomer(customerCaptor.capture());
        assertEquals(customer.getFirstName(), customerCaptor.getValue().getFirstName());
    }

    @Test
    public void deleteCustomer() {
        service.deleteCustomer(1);
        Customer customer = service.getCustomer(1);

        ArgumentCaptor<Integer> customerCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(customerDao).deleteCustomer(customerCaptor.capture());
        assertEquals(customer.getCustomerId(), customerCaptor.getValue().intValue());
    }
}