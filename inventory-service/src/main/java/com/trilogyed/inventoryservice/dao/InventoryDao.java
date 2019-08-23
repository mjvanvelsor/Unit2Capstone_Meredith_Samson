package com.trilogyed.inventoryservice.dao;

import com.trilogyed.inventoryservice.model.Inventory;

import java.util.List;

public interface InventoryDao {
    public Inventory createInventory(Inventory inventory);
    public Inventory getInventory(int inventoryId);
    public List<Inventory> getAllInventories();
    public void amendInventory(Inventory inventory);
    public void deleteInventory(int inventoryId);
}
