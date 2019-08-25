package com.trilogyed.invoiceservice.controller;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;


@RefreshScope
@RequestMapping("/invoiceitems")
public class InvoiceItemController {
//
//    @Autowired
//    ServiceLayer service;
//
//    public InvoiceItemController(ServiceLayer service) {
//        this.service = service;
//    }
//
//    @PostMapping
//    @ResponseStatus(value = HttpStatus.OK)
//    public InvoiceItem createInvoiceItem(@RequestBody @Valid InvoiceItem invoiceItem){
//        return service.createInvoiceItem(invoiceItem);
//    }
//    @GetMapping("/{invoiceItemId}")
//    @ResponseStatus(value = HttpStatus.OK)
//    public InvoiceItem getInvoiceItem(@PathVariable int invoiceItemId)
//            throws NotFoundException {
//        InvoiceItem invoiceItem = service.getInvoiceItem(invoiceItemId);
//        if (invoiceItem == null){
//            throw new NotFoundException("Invoice item could not be retrieved for id: " + invoiceItemId);
//        }
//        return invoiceItem;
//    }
//    @GetMapping("/{invoiceItemId}")
//    @ResponseStatus(value = HttpStatus.OK)
//    public List<InvoiceItem> getAllInvoiceItems()
//            throws NotFoundException {
//        List<InvoiceItem> invoiceItems = service.getAllInvoiceItems();
//        if (invoiceItems == null)
//            throw new NotFoundException("Invoices could not be retrieved.");
//        return invoiceItems;
//    }
//    @GetMapping("/invoiceId/{invoiceId}")
//    @ResponseStatus(value = HttpStatus.OK)
//    public List<InvoiceItem> getAllInvoiceItemsByInvoiceId(@PathVariable int invoiceId)
//         throws NotFoundException {
//            List<InvoiceItem> invoiceItems = service.getAllInvoiceItemsByInvoiceId(invoiceId);
//            if (invoiceItems == null)
//                throw new NotFoundException("Invoices could not be retrieved for invoice id: " + invoiceId);
//            return invoiceItems;
//    }
//    @PutMapping("/{invoiceItemId}")
//    @ResponseStatus(value = HttpStatus.NO_CONTENT)
//    public void amendInvoiceItem(@RequestBody @Valid InvoiceItem invoiceItem)
//            throws IllegalArgumentException {
//        if (invoiceItem.getInvoiceItemId() == 0) {
//            invoiceItem.setInvoiceItemId(invoiceItem.getInvoiceItemId());
//        } else {
//            throw new IllegalArgumentException("Invoice item id must match the id in the Invoice Item object.");
//        }
//        service.amendInvoiceItem(invoiceItem);
//    }
//    @DeleteMapping("/{invoiceItemId}")
//    @ResponseStatus(value = HttpStatus.NO_CONTENT)
//    public void deleteInvoiceItem(@PathVariable int invoiceItemId){
//        service.deleteInvoiceItem(invoiceItemId);
//    }
}
