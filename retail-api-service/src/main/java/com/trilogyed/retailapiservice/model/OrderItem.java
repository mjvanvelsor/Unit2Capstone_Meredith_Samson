package com.trilogyed.retailapiservice.model;

import java.util.Objects;

public class OrderItem {
   private int inventoryId; // gives us product id
   private int quantity;
   
   public OrderItem(int inventoryId, int quantity) {
      this.inventoryId = inventoryId;
      this.quantity = quantity;
   }
   
   public OrderItem() {
   }
   
   public int getInventoryId() {
      return inventoryId;
   }
   
   public void setInventoryId(int inventoryId) {
      this.inventoryId = inventoryId;
   }
   
   public int getQuantity() {
      return quantity;
   }
   
   public void setQuantity(int quantity) {
      this.quantity = quantity;
   }
   
   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      OrderItem orderItem = (OrderItem) o;
      return inventoryId == orderItem.inventoryId &&
            quantity == orderItem.quantity;
   }
   
   @Override
   public int hashCode() {
      return Objects.hash(inventoryId, quantity);
   }
   
   @Override
   public String toString() {
      return "OrderItem{" +
            "inventoryId=" + inventoryId +
            ", quantity=" + quantity +
            '}';
   }
}
