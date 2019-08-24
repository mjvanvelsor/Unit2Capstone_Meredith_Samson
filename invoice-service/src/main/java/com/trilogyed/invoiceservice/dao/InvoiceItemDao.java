package com.trilogyed.invoiceservice.dao;

import com.trilogyed.invoiceservice.model.Invoice;
import com.trilogyed.invoiceservice.model.InvoiceItem;

import java.util.List;

public interface InvoiceItemDao {
    public InvoiceItem createInvoiceItem(InvoiceItem invoiceItem);
    public InvoiceItem getInvoiceItem(int invoiceItemId);
    public List<InvoiceItem> getAllInvoiceItems();
    public List<InvoiceItem> getAllInvoiceItemsByInvoiceId(int invoiceId);
    public void amendInvoiceItem(InvoiceItem invoiceItem);
    public void deleteInvoiceItem(int invoiceItemId);
}
