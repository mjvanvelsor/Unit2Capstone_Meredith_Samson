package com.trilogyed.invoiceservice.controller;

import com.trilogyed.invoiceservice.ViewModel.InvoiceViewModel;
import com.trilogyed.invoiceservice.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;


@RestController
@RefreshScope
@RequestMapping("/invoices")
public class InvoiceController {

    @Autowired
    ServiceLayer service;

    public InvoiceController(ServiceLayer service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.OK)
    public InvoiceViewModel createInvoice(@RequestBody @Valid InvoiceViewModel ivm, Principal principal){
        return service.createInvoice(ivm);
    }

    @GetMapping("/{invoiceId}")
    @ResponseStatus(value = HttpStatus.OK)
    public InvoiceViewModel findInvoice(@PathVariable int invoiceId, Principal principal) {
        return service.getInvoice(invoiceId);
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<InvoiceViewModel> findAllInvoices(Principal principal) {
        return service.getAllInvoices();
    }
    
    @GetMapping("/customer/{customerId}")
    @ResponseStatus(value = HttpStatus.OK)
    public List<InvoiceViewModel> getInvoicesByCustomer(Integer customerId, Principal principal) {
        return service.getInvoicesByCustomer(customerId);
    }
    
    @PutMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void updateInvoice(@RequestBody @Valid InvoiceViewModel ivm, Principal principal) {
        service.amendInvoice(ivm);
    }

    @DeleteMapping("/{invoiceId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteInvoice(@PathVariable int invoiceId, Principal principal){
        service.deleteInvoice(invoiceId);
    }
}

