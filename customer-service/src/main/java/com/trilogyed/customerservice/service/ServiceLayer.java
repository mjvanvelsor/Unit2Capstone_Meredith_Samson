package com.trilogyed.customerservice.service;

import com.trilogyed.customerservice.dao.CustomerDao;
import com.trilogyed.customerservice.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class ServiceLayer {

    CustomerDao dao;
    @Autowired
    public ServiceLayer(CustomerDao dao) {
        this.dao = dao;
    }

    public Customer createCustomer(Customer customer){
        return dao.createCustomer(customer);
    }
    public Customer getCustomer(int customerId){
        return dao.getCustomer(customerId);
    }
    public List<Customer> getAllCustomers(){
        return dao.getAllCustomers();
    }
    public void amendCustomer(Customer customer){
        dao.amendCustomer(customer);
    }
    public void deleteCustomer(int customerId){
        dao.deleteCustomer(customerId);
    }
}
