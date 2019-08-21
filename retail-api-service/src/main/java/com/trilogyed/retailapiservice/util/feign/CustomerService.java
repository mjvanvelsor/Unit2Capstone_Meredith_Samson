package com.trilogyed.retailapiservice.util.feign;

import com.trilogyed.retailapiservice.model.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name = "customer-service")
public interface CustomerService {
   
   @RequestMapping(value = "/customer", method = RequestMethod.POST)
   Customer createCustomer(@Valid @RequestBody Customer customer);
   
   @RequestMapping(value = "/customer/{customerId}", method = RequestMethod.GET)
   Customer getCustomer(@Valid @PathVariable("customerId") int customerId);
   
   @RequestMapping(value = "/customer", method = RequestMethod.GET)
   List<Customer> getAllCustomers();
   
   @RequestMapping(value = "/customer", method = RequestMethod.PUT)
   void amendCustomer(@Valid @RequestBody Customer customer);
   
   @RequestMapping(value = "/customer/{customerId}", method = RequestMethod.DELETE)
   void deleteCustomer(@Valid @PathVariable("customerId") int customerId);
   
}
