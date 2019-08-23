package com.trilogyed.invoiceservice.dao;

import com.trilogyed.invoiceservice.model.Invoice;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class InvoiceDaoJdbcTemplateImpl implements InvoiceDao{

    @Override
    public Invoice createInvoice(Invoice invoice) {
        return null;
    }

    @Override
    public Invoice getInvoice(int invoiceId) {
        return null;
    }

    @Override
    public List<Invoice> getAllInvoices() {
        return null;
    }

    @Override
    public void amendInvoice(Invoice invoice) {

    }

    @Override
    public void deleteInvoice(int invoiceId) {

    }
}
