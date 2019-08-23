package com.trilogyed.inventoryservice.service;

import com.trilogyed.inventoryservice.dao.InventoryDao;
import com.trilogyed.inventoryservice.model.Inventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ServiceLayer {

    InventoryDao dao;
    @Autowired
    public ServiceLayer(InventoryDao dao) {
        this.dao = dao;
    }

    public Inventory createInventory(Inventory inventory){
        return dao.createInventory(inventory);
    }
    public Inventory getInventory(int inventoryId){
        return dao.getInventory(inventoryId);
    }
    public List<Inventory> getAllInventories(){
        return dao.getAllInventories();
    }
    public void amendInventory(Inventory inventory){
        dao.amendInventory(inventory);
    }
    public void deleteInventory(int inventoryId){
        dao.deleteInventory(inventoryId);
    }

}
