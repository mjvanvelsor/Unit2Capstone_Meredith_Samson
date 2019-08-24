package com.trilogyed.invoiceservice.controller;

import com.trilogyed.invoiceservice.exception.NotFoundException;
import com.trilogyed.invoiceservice.model.Invoice;
import com.trilogyed.invoiceservice.model.InvoiceViewModel;
import com.trilogyed.invoiceservice.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public InvoiceViewModel createInvoice(@RequestBody @Valid Invoice invoice){
        return service.createInvoice(invoice);
    }

    @GetMapping("/{invoiceId}")
    @ResponseStatus(value = HttpStatus.OK)
    public InvoiceViewModel findInvoice(@PathVariable int invoiceId)
            throws NotFoundException {
        InvoiceViewModel ivm = service.getInvoice(invoiceId);
        if (ivm == null){
            throw new NotFoundException("Invoice could not be retrieved for id: " + invoiceId);
        }
        return ivm;
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<InvoiceViewModel> findAllInvoices(@PathVariable int invoiceId)
    throws NotFoundException{
        List<InvoiceViewModel> invoices = service.getAllInvoices();
        if (invoices == null)
            throw new NotFoundException("Invoices could not be retrieved.");
        return invoices;
    }

    @PutMapping("/{invoiceId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void updateInvoice(@PathVariable int invoiceId, @RequestBody @Valid InvoiceViewModel ivm)
            throws IllegalArgumentException {
        if (ivm.getInvoiceId() == 0) {
            ivm.setInvoiceId(invoiceId);
        } else {
            throw new IllegalArgumentException("Invoice id must match the id in the Invoice object.");
        }
        service.amendInvoice(ivm);
    }

    @DeleteMapping("/{invoiceId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteInvoice(@PathVariable int invoiceId){
        service.deleteInvoice(invoiceId);
    }
}

