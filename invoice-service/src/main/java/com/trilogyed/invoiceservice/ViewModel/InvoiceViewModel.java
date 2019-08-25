package com.trilogyed.invoiceservice.ViewModel;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.trilogyed.invoiceservice.model.InvoiceItem;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class InvoiceViewModel {
    private int invoiceId;
    @Positive
    private int customerId;
    @NotNull
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate purchaseDate;
    @NotEmpty(message = "Invoice must have invoice items.")
    private List<InvoiceItem> invoiceItems;

    public InvoiceViewModel() {
    }

    public InvoiceViewModel(int invoiceId, int customerId, LocalDate purchaseDate, List<InvoiceItem> invoiceItems) {
        this.invoiceId = invoiceId;
        this.customerId = customerId;
        this.purchaseDate = purchaseDate;
        this.invoiceItems = invoiceItems;
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

    public List<InvoiceItem> getInvoiceItems() {
        return invoiceItems;
    }

    public void setInvoiceItems(List<InvoiceItem> invoiceItems) {
        this.invoiceItems = invoiceItems;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InvoiceViewModel that = (InvoiceViewModel) o;
        return invoiceId == that.invoiceId &&
                customerId == that.customerId &&
                purchaseDate.equals(that.purchaseDate) &&
                invoiceItems.equals(that.invoiceItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(invoiceId, customerId, purchaseDate, invoiceItems);
    }
    
    @Override
    public String toString() {
        return "InvoiceViewModel{" +
              "invoiceId=" + invoiceId +
              ", customerId=" + customerId +
              ", purchaseDate=" + purchaseDate +
              ", invoiceItems=" + invoiceItems +
              '}';
    }
}
