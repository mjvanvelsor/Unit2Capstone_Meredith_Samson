package com.trilogyed.retailapiservice.viewmodel;

import com.trilogyed.retailapiservice.model.Inventory;
import com.trilogyed.retailapiservice.model.InvoiceItem;
import com.trilogyed.retailapiservice.model.Product;

import java.util.Objects;

public class InvoiceItemInventoryProductViewmodel {
   private int invoiceItemId;
   private InvoiceItem invoiceItem;
   private int inventoryId;
   private Inventory inventory;
   private int productId;
   private Product product;
   
   public InvoiceItemInventoryProductViewmodel() {
   }
   
   public InvoiceItemInventoryProductViewmodel(int invoiceItemId,
                                               InvoiceItem invoiceItem,
                                               int inventoryId,
                                               Inventory inventory,
                                               int productId, Product product) {
      this.invoiceItemId = invoiceItemId;
      this.invoiceItem = invoiceItem;
      this.inventoryId = inventoryId;
      this.inventory = inventory;
      this.productId = productId;
      this.product = product;
   }
   
   public int getInvoiceItemId() {
      return invoiceItemId;
   }
   
   public void setInvoiceItemId(int invoiceItemId) {
      this.invoiceItemId = invoiceItemId;
   }
   
   public InvoiceItem getInvoiceItem() {
      return invoiceItem;
   }
   
   public void setInvoiceItem(InvoiceItem invoiceItem) {
      this.invoiceItem = invoiceItem;
   }
   
   public int getInventoryId() {
      return inventoryId;
   }
   
   public void setInventoryId(int inventoryId) {
      this.inventoryId = inventoryId;
   }
   
   public Inventory getInventory() {
      return inventory;
   }
   
   public void setInventory(Inventory inventory) {
      this.inventory = inventory;
   }
   
   public int getProductId() {
      return productId;
   }
   
   public void setProductId(int productId) {
      this.productId = productId;
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
      InvoiceItemInventoryProductViewmodel that = (InvoiceItemInventoryProductViewmodel) o;
      return invoiceItemId == that.invoiceItemId &&
            inventoryId == that.inventoryId &&
            productId == that.productId &&
            invoiceItem.equals(that.invoiceItem) &&
            inventory.equals(that.inventory) &&
            product.equals(that.product);
   }
   
   @Override
   public int hashCode() {
      return Objects.hash(invoiceItemId, invoiceItem, inventoryId, inventory, productId, product);
   }
   
   @Override
   public String toString() {
      return "InvoiceItemInventoryProductViewmodel{" +
            "invoiceItemId=" + invoiceItemId +
            ", invoiceItem=" + invoiceItem +
            ", inventoryId=" + inventoryId +
            ", inventory=" + inventory +
            ", productId=" + productId +
            ", product=" + product +
            '}';
   }
}
