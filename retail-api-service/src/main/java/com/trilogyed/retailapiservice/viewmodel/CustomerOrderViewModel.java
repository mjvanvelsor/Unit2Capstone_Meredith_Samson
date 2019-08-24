package com.trilogyed.retailapiservice.viewmodel;

import com.trilogyed.retailapiservice.model.OrderItem;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class CustomerOrderViewModel {
   private int customerId;
   private LocalDate purchaseDate;
   private List<OrderItem> orderItemList;
   
   public CustomerOrderViewModel() {
   }
   
   public CustomerOrderViewModel(int customerId, LocalDate purchaseDate, List<OrderItem> orderItemList) {
      this.customerId = customerId;
      this.purchaseDate = purchaseDate;
      this.orderItemList = orderItemList;
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
   
   public List<OrderItem> getOrderItemList() {
      return orderItemList;
   }
   
   public void setOrderItemList(List<OrderItem> orderItemList) {
      this.orderItemList = orderItemList;
   }
   
   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      CustomerOrderViewModel that = (CustomerOrderViewModel) o;
      return customerId == that.customerId &&
            purchaseDate.equals(that.purchaseDate) &&
            orderItemList.equals(that.orderItemList);
   }
   
   @Override
   public int hashCode() {
      return Objects.hash(customerId, purchaseDate, orderItemList);
   }
   
   @Override
   public String toString() {
      return "CustomerOrderViewmodel{" +
            "customerId=" + customerId +
            ", purchaseDate=" + purchaseDate +
            ", orderItemList=" + orderItemList +
            '}';
   }
}
