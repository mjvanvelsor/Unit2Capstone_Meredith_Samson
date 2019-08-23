package com.trilogyed.invoiceservice.controller;

import com.trilogyed.invoiceservice.exception.NotFoundException;
import com.trilogyed.invoiceservice.model.Invoice;
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

    @PostMapping
    @ResponseStatus(value = HttpStatus.OK)
    public Invoice createInvoice(@RequestBody @Valid Invoice invoice){
        return invoice;
    }

    @GetMapping("/{invoiceId}")
    @ResponseStatus(value = HttpStatus.OK)
    public Invoice findInvoice(@PathVariable int invoiceId)
            throws NotFoundException {
        Invoice invoice = service.getInvoice(invoiceId);
        if (invoice == null){
            throw new NotFoundException("Invoice could not be retrieved for id: " + invoiceId);
        }
        return invoice;
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<Invoice> findAllInvoices(@PathVariable int invoiceId){
        List<Invoice> invoices = service.getAllInvoices();
        return invoices;
    }

    @PutMapping("/{invoiceId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void updateInvoice(@PathVariable int invoiceId, @RequestBody @Valid Invoice invoice)
            throws IllegalArgumentException {
        if (invoice.getInvoiceId() == 0) {
            invoice.setInvoiceId(invoiceId);
        } else {
            throw new IllegalArgumentException("Invoice id must match the id in the Invoice object.");
        }
        service.amendInvoice(invoice);
    }


    @DeleteMapping("/{invoiceId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteInvoice(@PathVariable int invoiceId){
        service.deleteInvoice(invoiceId);
    }
}

