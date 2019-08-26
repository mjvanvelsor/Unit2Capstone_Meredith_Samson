package com.trilogyed.invoiceservice.service;

import com.trilogyed.invoiceservice.dao.InvoiceDao;
import com.trilogyed.invoiceservice.dao.InvoiceItemDao;
import com.trilogyed.invoiceservice.exception.NotFoundException;
import com.trilogyed.invoiceservice.model.Invoice;
import com.trilogyed.invoiceservice.model.InvoiceItem;
import com.trilogyed.invoiceservice.ViewModel.InvoiceViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Component
public class ServiceLayer {
    InvoiceDao invoiceDao;
    InvoiceItemDao invoiceItemDao;

    @Autowired
    public ServiceLayer(InvoiceDao invoiceDao, InvoiceItemDao invoiceItemDao) {
        this.invoiceDao = invoiceDao;
        this.invoiceItemDao = invoiceItemDao;
    }
    
    @Transactional
    public InvoiceViewModel createInvoice(InvoiceViewModel ivm){
        System.out.println("Processed: " + ivm);
        // create invoice header
        Invoice inv = new Invoice();
        inv.setCustomerId(ivm.getCustomerId());
        inv.setPurchaseDate(ivm.getPurchaseDate());
        inv = invoiceDao.createInvoice(inv);
        
        // create invoice items
        List<InvoiceItem> invoiceItems = new ArrayList<>();
        InvoiceItem invItm = new InvoiceItem();
        Iterator<InvoiceItem> iter = ivm.getInvoiceItems().iterator();
        while (iter.hasNext()) {
            invItm = iter.next();
            invItm.setInvoiceId(inv.getInvoiceId());
            invItm = invoiceItemDao.createInvoiceItem(invItm);
            invoiceItems.add(invItm);
        }
        
        return buildInvoiceViewModel(inv, invoiceItems);
    }

    public InvoiceViewModel getInvoice(int invoiceId){
        Optional<Invoice> optionalInv = Optional.ofNullable(invoiceDao.getInvoice(invoiceId));
        optionalInv.orElseThrow(()-> new NotFoundException(
              "Invoice id: " + invoiceId + " not found in the Invoice File"));
        
        Optional<List<InvoiceItem>> optionalInvItms = Optional.ofNullable(
              invoiceItemDao.getAllInvoiceItemsByInvoiceId(invoiceId));
        
        InvoiceViewModel ivm = new InvoiceViewModel();
        ivm.setInvoiceId(optionalInv.get().getInvoiceId());
        ivm.setCustomerId(optionalInv.get().getCustomerId());
        ivm.setPurchaseDate(optionalInv.get().getPurchaseDate());
        ivm.setInvoiceItems(optionalInvItms.get().subList(0,optionalInvItms.get().size()));
        
        return ivm;
    }
    
    public List<InvoiceViewModel> getAllInvoices(){
        List<InvoiceViewModel> invoiceViewModels = new ArrayList<>();
        InvoiceViewModel ivm = new InvoiceViewModel();
        
        // get all invoices
        Optional<List<Invoice>> optionalInvoices = Optional.ofNullable(
              invoiceDao.getAllInvoices());
        optionalInvoices.orElseThrow(()-> new NotFoundException(
              "No invoice found in the Invoice File"));
        Invoice inv = new Invoice();

        // get all invoice items for each invoice
        Iterator<Invoice> iter = optionalInvoices.get().iterator();
        while (iter.hasNext()) {
            inv = iter.next();
            
            // get invoice items for a specific invoice
            Optional<List<InvoiceItem>> optionalInvItms = Optional.ofNullable(
                  invoiceItemDao.getAllInvoiceItemsByInvoiceId(inv.getInvoiceId()));
            
            ivm = buildInvoiceViewModel(inv, optionalInvItms.get());
            invoiceViewModels.add(ivm);
        }
        
        return invoiceViewModels;
    }
    
    public List<InvoiceViewModel> getInvoicesByCustomer(int customerId){
        System.out.println("Customer Id: " + customerId);
        List<InvoiceViewModel> invoiceViewModels = new ArrayList<>();
        InvoiceViewModel ivm = new InvoiceViewModel();
        
        // get all invoices
        Optional<List<Invoice>> optionalInvoices = Optional.ofNullable(
              invoiceDao.getInvoicesByCustomer(customerId));
        optionalInvoices.orElseThrow(()-> new NotFoundException(
              "No invoice found in the Invoice File"));
        Invoice inv = new Invoice();
        
        // get all invoice items for each invoice
        Iterator<Invoice> iter = optionalInvoices.get().iterator();
        while (iter.hasNext()) {
            inv = iter.next();
            
            // get invoice items for a specific invoice
            Optional<List<InvoiceItem>> optionalInvItms = Optional.ofNullable(
                  invoiceItemDao.getAllInvoiceItemsByInvoiceId(inv.getInvoiceId()));
            
            ivm = buildInvoiceViewModel(inv, optionalInvItms.get());
            invoiceViewModels.add(ivm);
        }
        
        return invoiceViewModels;
    }
    
    @Transactional
    public void amendInvoice(InvoiceViewModel ivm){
        Invoice invoice = new Invoice();
        invoice.setInvoiceId(ivm.getInvoiceId());
        invoice.setCustomerId(ivm.getCustomerId());
        invoice.setPurchaseDate(ivm.getPurchaseDate());
        invoiceDao.amendInvoice(invoice);
        
        Iterator<InvoiceItem> iter = ivm.getInvoiceItems().iterator();
        while(iter.hasNext()) {
            invoiceItemDao.amendInvoiceItem(iter.next());
        }
    }
    
    @Transactional
    public void deleteInvoice(int invoiceId){
        invoiceItemDao.getAllInvoiceItemsByInvoiceId(invoiceId)
              .stream()
              .forEach(invoiceItem -> invoiceItemDao.deleteInvoiceItem(invoiceItem.getInvoiceItemId()));

        invoiceDao.deleteInvoice(invoiceId);
    }
    
    // Helper Method
    private InvoiceViewModel buildInvoiceViewModel(Invoice inv, List<InvoiceItem> invItms){
        InvoiceViewModel ivm = new InvoiceViewModel();
        ivm.setInvoiceId(inv.getInvoiceId());
        ivm.setCustomerId(inv.getCustomerId());
        ivm.setPurchaseDate(inv.getPurchaseDate());
        ivm.setInvoiceItems(invItms);
        
        return ivm;
        
    }
    
}
