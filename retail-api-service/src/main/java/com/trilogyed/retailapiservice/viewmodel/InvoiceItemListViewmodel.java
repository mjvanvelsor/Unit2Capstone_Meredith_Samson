package com.trilogyed.retailapiservice.viewmodel;

import com.trilogyed.retailapiservice.model.InvoiceItem;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class InvoiceItemListViewmodel {
   private int invoiceId;
   private int customerId;
   private LocalDate purchaseDate;
   private List<InvoiceItem> invoiceItemList;
   
   public InvoiceItemListViewmodel() {
   }
   
   public InvoiceItemListViewmodel(int invoiceId,
                                   int customerId, LocalDate purchaseDate,
                                   List<InvoiceItem> invoiceItemList) {
      this.invoiceId = invoiceId;
      this.customerId = customerId;
      this.purchaseDate = purchaseDate;
      this.invoiceItemList = invoiceItemList;
   }
   
   public int getInvoiceId() {
      return invoiceId;
   }
   
   public void setInvoiceId(int invoiceId) {
      this.invoiceId = invoiceId;
   }
   
   public int getCustomerId() {
      return customerId;
   }
   
   public void setCustomerId(int customerId) {
      this.customerId = customerId;
   }
   
   public LocalDate getPurchaseDate() {
      return purchaseDate;
   }
   
   public void setPurchaseDate(LocalDate purchaseDate) {
      this.purchaseDate = purchaseDate;
   }
   
   public List<InvoiceItem> getInvoiceItemList() {
      return invoiceItemList;
   }
   
   public void setInvoiceItemList(List<InvoiceItem> invoiceItemList) {
      this.invoiceItemList = invoiceItemList;
   }
   
   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      InvoiceItemListViewmodel that = (InvoiceItemListViewmodel) o;
      return invoiceId == that.invoiceId &&
            customerId == that.customerId &&
            purchaseDate.equals(that.purchaseDate) &&
            invoiceItemList.equals(that.invoiceItemList);
   }
   
   @Override
   public int hashCode() {
      return Objects.hash(invoiceId, customerId, purchaseDate, invoiceItemList);
   }
   
   @Override
   public String toString() {
      return "InvoiceItemListViewmodel{" +
            "invoiceId=" + invoiceId +
            ", customerId=" + customerId +
            ", purchaseDate=" + purchaseDate +
            ", invoiceItemList=" + invoiceItemList +
            '}';
   }
}
