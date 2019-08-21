package com.trilogyed.retailapiservice.viewmodel;

import com.trilogyed.retailapiservice.model.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class RetailViewModel {
    private int invoiceId;
    private int customerId;
    private LocalDate purchaseDate;
    private List<InvoiceItem> invoiceItemList;
    private List<Inventory> inventoryList;
    private Customer customer;
    private LevelUp levelUp;
    private Product product;

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

    public List<Inventory> getInventoryList() {
        return inventoryList;
    }

    public void setInventoryList(List<Inventory> inventoryList) {
        this.inventoryList = inventoryList;
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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RetailViewModel that = (RetailViewModel) o;
        return invoiceId == that.invoiceId &&
                customerId == that.customerId &&
                Objects.equals(purchaseDate, that.purchaseDate) &&
                Objects.equals(invoiceItemList, that.invoiceItemList) &&
                Objects.equals(inventoryList, that.inventoryList) &&
                Objects.equals(customer, that.customer) &&
                Objects.equals(levelUp, that.levelUp) &&
                Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(invoiceId, customerId, purchaseDate, invoiceItemList, inventoryList, customer, levelUp, product);
    }

    @Override
    public String toString() {
        return "RetailViewModel{" +
                "invoiceId=" + invoiceId +
                ", customerId=" + customerId +
                ", purchaseDate=" + purchaseDate +
                ", invoiceItemList=" + invoiceItemList +
                ", inventoryList=" + inventoryList +
                ", customer=" + customer +
                ", levelUp=" + levelUp +
                ", product=" + product +
                '}';
    }
}
