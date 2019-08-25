package com.trilogyed.invoiceservice.controller;

import com.trilogyed.invoiceservice.exception.NotFoundException;
import com.trilogyed.invoiceservice.model.Invoice;
import com.trilogyed.invoiceservice.ViewModel.InvoiceViewModel;
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
    public InvoiceViewModel createInvoice(@RequestBody @Valid InvoiceViewModel ivm){
        return service.createInvoice(ivm);
    }

    @GetMapping("/{invoiceId}")
    @ResponseStatus(value = HttpStatus.OK)
    public InvoiceViewModel findInvoice(@PathVariable int invoiceId) {
        return service.getInvoice(invoiceId);
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<InvoiceViewModel> findAllInvoices() {
        return service.getAllInvoices();
    }

    @PutMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void updateInvoice(@RequestBody @Valid InvoiceViewModel ivm) {
        service.amendInvoice(ivm);
    }

    @DeleteMapping("/{invoiceId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteInvoice(@PathVariable int invoiceId){
        service.deleteInvoice(invoiceId);
    }
}

