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
   
   @RequestMapping(value = "/customers", method = RequestMethod.POST)
   Customer createCustomer(@Valid @RequestBody Customer customer);
   
   @RequestMapping(value = "/customers/{customerId}", method = RequestMethod.GET)
   Customer findCustomer(@Valid @PathVariable("customerId") int customerId);
   
   @RequestMapping(value = "/customers", method = RequestMethod.GET)
   List<Customer> getAllCustomers();
   
   @RequestMapping(value = "/customers", method = RequestMethod.PUT)
   void amendCustomer(@Valid @RequestBody Customer customer);
   
   @RequestMapping(value = "/customers/{customerId}", method = RequestMethod.DELETE)
   void deleteCustomer(@Valid @PathVariable("customerId") int customerId);
   
}
