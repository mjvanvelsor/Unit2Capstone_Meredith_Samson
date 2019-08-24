package com.trilogyed.invoiceservice.service;

import com.trilogyed.invoiceservice.dao.InvoiceDao;
import com.trilogyed.invoiceservice.dao.InvoiceItemDao;
import com.trilogyed.invoiceservice.exception.NotFoundException;
import com.trilogyed.invoiceservice.model.Invoice;
import com.trilogyed.invoiceservice.model.InvoiceItem;
import com.trilogyed.invoiceservice.model.InvoiceViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
@Component
public class ServiceLayer {
    InvoiceDao invoiceDao;
    InvoiceItemDao invoiceItemDao;

    @Autowired
    public ServiceLayer(InvoiceDao invoiceDao, InvoiceItemDao invoiceItemDao) {
        this.invoiceDao = invoiceDao;
        this.invoiceItemDao = invoiceItemDao;
    }

    /**
     * INVOICE API
     * @param invoice
     * @return
     */
    @Transactional
    public InvoiceViewModel createInvoice(Invoice invoice){
        invoice = invoiceDao.createInvoice(invoice);
        invoice.setInvoiceId(invoice.getInvoiceId());
        return buildIVMwithInvoice(invoice);
    }

    public InvoiceViewModel getInvoice(int invoiceId){
        Invoice invoice = invoiceDao.getInvoice(invoiceId);
        if (invoice == null)
            return null;
        else
        return buildIVMwithInvoice(invoice);
    }
    public List<InvoiceViewModel> getAllInvoices(){
        List<InvoiceViewModel> invoiceViewModels = new ArrayList<>();
        List<Invoice> invoices = invoiceDao.getAllInvoices();
        invoices.stream().forEach(invoice -> invoiceViewModels.add(buildIVMwithInvoice(invoice)));
        return invoiceViewModels;
    }
    @Transactional
    public void amendInvoice(InvoiceViewModel ivm){
        Invoice invoice = new Invoice();
        invoice.setInvoiceId(ivm.getInvoiceId());
        invoice.setCustomerId(ivm.getCustomerId());
        invoice.setPurchaseDate(ivm.getPurchaseDate());
        invoiceDao.amendInvoice(invoice);
    }
    public void deleteInvoice(int invoiceId){
        invoiceDao.deleteInvoice(invoiceId);
    }

    /**
     * INVOICE ITEM API
     * @param invoiceItem
     * @return
     */
    public InvoiceItem createInvoiceItem(InvoiceItem invoiceItem){
        return invoiceItemDao.createInvoiceItem(invoiceItem);
    }
    public InvoiceItem getInvoiceItem(int invoiceItemId){
        return invoiceItemDao.getInvoiceItem(invoiceItemId);
    }
    public List<InvoiceItem> getAllInvoiceItems(){
        return invoiceItemDao.getAllInvoiceItems();
    }
    public List<InvoiceItem> getAllInvoiceItemsByInvoiceId(int invoiceId){
        return invoiceItemDao.getAllInvoiceItemsByInvoiceId(invoiceId);
    }
    public void amendInvoiceItem(InvoiceItem invoiceItem){
        invoiceItemDao.amendInvoiceItem(invoiceItem);
    }
    public void deleteInvoiceItem(int invoiceItemId){
        invoiceItemDao.deleteInvoiceItem(invoiceItemId);
    }


    // Helper Method
    private InvoiceViewModel buildIVMwithInvoice(Invoice invoice){
        InvoiceViewModel ivm = new InvoiceViewModel();
        ivm.setInvoiceId(invoice.getInvoiceId());
        ivm.setCustomerId(invoice.getCustomerId());
        ivm.setPurchaseDate(invoice.getPurchaseDate());

        try {
            // THIS NEEDS TO BE CHANGED BUT I'M NOT SURE TO WHAT
            List<InvoiceItem> items = new ArrayList<>();
            ivm.setInvoiceItems(items);
        } catch (Exception e){
                        if (e.getCause().getClass().equals(SocketTimeoutException.class)) {
                throw new NotFoundException("Server connection timeout!");
            } else {
                throw e;
            }
        }
        return  ivm;
    }
}
