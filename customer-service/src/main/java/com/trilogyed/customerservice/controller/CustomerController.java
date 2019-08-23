package com.trilogyed.customerservice.controller;

import com.trilogyed.customerservice.exception.NotFoundException;
import com.trilogyed.customerservice.model.Customer;
import com.trilogyed.customerservice.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RefreshScope
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    ServiceLayer service;

    @PostMapping
    @ResponseStatus(value = HttpStatus.OK)
    public Customer createCustomer(@RequestBody @Valid Customer customer){
        return customer;
    }

    @GetMapping("/{customerId}")
    @ResponseStatus(value = HttpStatus.OK)
    public Customer findCustomer(@PathVariable int customerId)
            throws NotFoundException {
        Customer customer = service.getCustomer(customerId);
        if (customer == null){
            throw new NotFoundException("Customer could not be retrieved for id: " + customerId);
        }
        return customer;
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<Customer> findAllCustomers(@PathVariable int customerId){
        List<Customer> customers = service.getAllCustomers();
        return customers;
    }

    @PutMapping("/{customerId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void updateCustomer(@PathVariable int customerId, @RequestBody @Valid Customer customer)
            throws IllegalArgumentException {
        if (customer.getCustomerId() == 0) {
            customer.setCustomerId(customerId);
        } else {
            throw new IllegalArgumentException("Customer id must match the id in the Customer object.");
        }
        service.amendCustomer(customer);
    }


    @DeleteMapping("/{customerId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable int customerId){
        service.deleteCustomer(customerId);
    }
}
