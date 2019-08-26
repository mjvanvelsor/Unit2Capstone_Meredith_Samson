package com.trilogyed.customerservice.service;

import com.trilogyed.customerservice.dao.CustomerDao;
import com.trilogyed.customerservice.exception.NotFoundException;
import com.trilogyed.customerservice.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

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
        Optional<Customer> optionalCustomer = Optional.ofNullable(dao.getCustomer(customerId));
        optionalCustomer.orElseThrow(()-> new NotFoundException(
              "Customer id: " + customerId + " not found in Customer File")
        );
        return optionalCustomer.get();
    }
    
    public List<Customer> getAllCustomers(){
        Optional<List<Customer>> optionalCustomers = Optional.ofNullable(dao.getAllCustomers());
        optionalCustomers.orElseThrow(()-> new NotFoundException(
              "No Customers found in Customer File")
        );
        return optionalCustomers.get();
    }
    
    public void amendCustomer(Customer customer){
        dao.amendCustomer(customer);
    }
    
    public void deleteCustomer(int customerId){
        dao.deleteCustomer(customerId);
    }
}
