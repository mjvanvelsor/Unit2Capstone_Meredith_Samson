package com.trilogyed.customerservice.controller;

import com.trilogyed.customerservice.exception.NotFoundException;
import com.trilogyed.customerservice.model.Customer;
import com.trilogyed.customerservice.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RefreshScope
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    ServiceLayer service;

    @PostMapping
    @ResponseStatus(value = HttpStatus.OK)
    public Customer createCustomer(@RequestBody @Valid Customer customer, Principal principal){
        customer = service.createCustomer(customer);
        return customer;
    }

    @GetMapping("/{customerId}")
    @ResponseStatus(value = HttpStatus.OK)
    public Customer findCustomer(@PathVariable int customerId, Principal principal) {
        System.out.println("Got there: " + customerId);
        Customer customer = service.getCustomer(customerId);
        System.out.println(customer);
        return customer;
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<Customer> findAllCustomers(Principal principal){
        List<Customer> customers = service.getAllCustomers();
        return customers;
    }

    @PutMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void updateCustomer(@RequestBody @Valid Customer customer, Principal principal) {
        service.amendCustomer(customer);
    }

    @DeleteMapping("/{customerId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable int customerId, Principal principal){
        service.deleteCustomer(customerId);
    }
}
