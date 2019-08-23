package com.trilogyed.invoiceservice.dao;

import com.trilogyed.invoiceservice.model.Invoice;

import java.util.List;

public interface InvoiceDao {
    public Invoice createInvoice(Invoice invoice);
    public Invoice getInvoice(int invoiceId);
    public List<Invoice> getAllInvoices();
    public void amendInvoice(Invoice invoice);
    public void deleteInvoice(int invoiceId);
}
