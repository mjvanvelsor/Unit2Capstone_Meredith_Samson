package com.trilogyed.adminapiservice.util.feign;

import com.trilogyed.adminapiservice.model.Invoice;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name="invoice-service")
public interface InvoiceService {
   
   @RequestMapping(value = "/invoices", method = RequestMethod.POST)
   Invoice createInvoice(@Valid @RequestBody Invoice invoice);
   
   @RequestMapping(value = "/invoices/{invoiceId}", method = RequestMethod.GET)
   Invoice getInvoice(@Valid @PathVariable("invoiceId") int invoiceId);
   
   @RequestMapping(value = "/invoices/customer/{customerId}", method = RequestMethod.GET)
   List<Invoice> getInvoicesByCustomer(@Valid @PathVariable("customerId") int customerId);
   
   @RequestMapping(value = "/invoices", method = RequestMethod.GET)
   List<Invoice> getAllInvoices();
   
   @RequestMapping(value = "/invoices", method = RequestMethod.PUT)
   void amendInvoice(@Valid @RequestBody Invoice invoice);
   
   @RequestMapping(value = "/invoices/{invoiceId}", method = RequestMethod.DELETE)
   void deleteInvoice(@Valid @PathVariable("invoiceId") int invoiceId);
   
}