package com.trilogyed.invoiceservice.model;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.Objects;

public class InvoiceItem {
    private int id;
    @Positive
    private int invoiceId;
    @Positive
    private int inventory_id;
    @Positive
    private int quantity;
    @NotNull
    @DecimalMin(value = "0.0", inclusive = true)
    @DecimalMax(value = "9999999.99", inclusive = true)
    private BigDecimal unitPrice;

    public InvoiceItem() {
    }

    public InvoiceItem(int id, int invoiceId, int inventory_id, int quantity, BigDecimal unitPrice) {
        this.id = id;
        this.invoiceId = invoiceId;
        this.inventory_id = inventory_id;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public int getInventory_id() {
        return inventory_id;
    }

    public void setInventory_id(int inventory_id) {
        this.inventory_id = inventory_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InvoiceItem that = (InvoiceItem) o;
        return id == that.id &&
                invoiceId == that.invoiceId &&
                inventory_id == that.inventory_id &&
                quantity == that.quantity &&
                unitPrice.equals(that.unitPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, invoiceId, inventory_id, quantity, unitPrice);
    }

    @Override
    public String toString() {
        return "InvoiceItem{" +
                "id=" + id +
                ", invoiceId=" + invoiceId +
                ", inventory_id=" + inventory_id +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                '}';
    }
}


