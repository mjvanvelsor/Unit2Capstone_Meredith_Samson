package com.trilogyed.customerservice.dao;

import com.trilogyed.customerservice.model.Customer;

import java.util.List;

public interface CustomerDao {
    public Customer createCustomer(Customer customer);
    public Customer getCustomer(int customerId);
    public List<Customer> getAllCustomers();
    public void amendCustomer(Customer customer);
    public void deleteCustomer(int customerId);
}
