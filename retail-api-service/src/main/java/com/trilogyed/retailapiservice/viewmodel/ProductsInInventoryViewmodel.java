package com.trilogyed.retailapiservice.viewmodel;

import com.trilogyed.retailapiservice.model.Inventory;
import com.trilogyed.retailapiservice.model.Product;

import java.util.Objects;

public class ProductsInInventoryViewmodel {
   private Inventory inventory;
   private Product product;
   
   public ProductsInInventoryViewmodel(Inventory inventory, Product product) {
      this.inventory = inventory;
      this.product = product;
   }
   
   public Inventory getInventory() {
      return inventory;
   }
   
   public void setInventory(Inventory inventory) {
      this.inventory = inventory;
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
      ProductsInInventoryViewmodel that = (ProductsInInventoryViewmodel) o;
      return Objects.equals(inventory, that.inventory) &&
            Objects.equals(product, that.product);
   }
   
   @Override
   public int hashCode() {
      return Objects.hash(inventory, product);
   }
   
   @Override
   public String toString() {
      return "ProductsInInventoryViewmodel{" +
            "inventory=" + inventory +
            ", product=" + product +
            '}';
   }
}
