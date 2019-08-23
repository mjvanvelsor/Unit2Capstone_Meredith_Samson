package com.trilogyed.retailapiservice.viewmodel;

import com.trilogyed.retailapiservice.model.Customer;
import com.trilogyed.retailapiservice.model.Invoice;
import com.trilogyed.retailapiservice.model.LevelUp;

import java.math.BigDecimal;
import java.util.Objects;

public class CustomerInvoiceLevelupViewmodel {
   private int customerId;
   private int invoiceId;
   private Invoice invoice;
   private Customer customer;
   private LevelUp levelUp;
   private BigDecimal invoiceValue;
   
   public CustomerInvoiceLevelupViewmodel() {
   }
   
   public CustomerInvoiceLevelupViewmodel(int customerId,
                                          int invoiceId, Invoice invoice,
                                          Customer customer,
                                          LevelUp levelUp,
                                          BigDecimal invoiceValue) {
      this.customerId = customerId;
      this.invoiceId = invoiceId;
      this.invoice = invoice;
      this.customer = customer;
      this.levelUp = levelUp;
      this.invoiceValue = invoiceValue;
   }
   
   public int getCustomerId() {
      return customerId;
   }
   
   public void setCustomerId(int customerId) {
      this.customerId = customerId;
   }
   
   public int getInvoiceId() {
      return invoiceId;
   }
   
   public void setInvoiceId(int invoiceId) {
      this.invoiceId = invoiceId;
   }
   
   public Invoice getInvoice() {
      return invoice;
   }
   
   public void setInvoice(Invoice invoice) {
      this.invoice = invoice;
   }
   
   public Customer getCustomer() {
      return customer;
   }
   
   public void setCustomer(Customer customer) {
      this.customer = customer;
   }
   
   public LevelUp getLevelUp() {
      return levelUp;
   }
   
   public void setLevelUp(LevelUp levelUp) {
      this.levelUp = levelUp;
   }
   
   public BigDecimal getInvoiceValue() {
      return invoiceValue;
   }
   
   public void setInvoiceValue(BigDecimal invoiceValue) {
      this.invoiceValue = invoiceValue;
   }
   
   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      CustomerInvoiceLevelupViewmodel viewmodel = (CustomerInvoiceLevelupViewmodel) o;
      return customerId == viewmodel.customerId &&
            invoiceId == viewmodel.invoiceId &&
            invoice.equals(viewmodel.invoice) &&
            customer.equals(viewmodel.customer) &&
            Objects.equals(levelUp, viewmodel.levelUp) &&
            invoiceValue.equals(viewmodel.invoiceValue);
   }
   
   @Override
   public int hashCode() {
      return Objects.hash(customerId, invoiceId, invoice, customer, levelUp, invoiceValue);
   }
   
   @Override
   public String toString() {
      return "CustomerInvoiceLevelupViewmodel{" +
            "customerId=" + customerId +
            ", invoiceId=" + invoiceId +
            ", invoice=" + invoice +
            ", customer=" + customer +
            ", levelUp=" + levelUp +
            ", invoiceValue=" + invoiceValue +
            '}';
   }
}
