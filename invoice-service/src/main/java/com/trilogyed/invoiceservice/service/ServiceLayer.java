package com.trilogyed.invoiceservice.service;

import com.trilogyed.invoiceservice.dao.InvoiceDao;
import com.trilogyed.invoiceservice.model.Invoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class ServiceLayer {

    InvoiceDao dao;
    @Autowired
    public ServiceLayer(InvoiceDao dao) {
        this.dao = dao;
    }

    public Invoice createInvoice(Invoice invoice){
        return dao.createInvoice(invoice);
    }
    public Invoice getInvoice(int invoiceId){
        return dao.getInvoice(invoiceId);
    }
    public List<Invoice> getAllInvoices(){
        return dao.getAllInvoices();
    }
    public void amendInvoice(Invoice invoice){
        dao.amendInvoice(invoice);
    }
    public void deleteInvoice(int invoiceId){
        dao.deleteInvoice(invoiceId);
    }
}
