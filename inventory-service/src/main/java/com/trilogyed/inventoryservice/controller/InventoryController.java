package com.trilogyed.inventoryservice.controller;

import com.trilogyed.inventoryservice.exception.NotFoundException;
import com.trilogyed.inventoryservice.model.Inventory;
import com.trilogyed.inventoryservice.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RefreshScope
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    ServiceLayer service;

    @PostMapping
    @ResponseStatus(value = HttpStatus.OK)
    public Inventory createInventory(@RequestBody @Valid Inventory inventory){
        return inventory;
    }

    @GetMapping("/{inventoryId}")
    @ResponseStatus(value = HttpStatus.OK)
    public Inventory findInventory(@PathVariable int inventoryId)
            throws NotFoundException{
        Inventory inventory = service.getInventory(inventoryId);
        if (inventory == null){
            throw new NotFoundException("Inventory could not be retrieved for id: " + inventoryId);
        }
        return inventory;
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<Inventory> findAllInventories(@PathVariable int inventoryId){
        List<Inventory> inventoryList = service.getAllInventories();
        return inventoryList;
    }

    @PutMapping("/{inventoryId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void updateInventory(@PathVariable int inventoryId, @RequestBody @Valid Inventory inventory)
            throws IllegalArgumentException {
        if (inventory.getInventoryId() == 0) {
            inventory.setInventoryId(inventoryId);
        } else {
            throw new IllegalArgumentException("Inventory id must match the id in the Inventory object.");
        }
        service.amendInventory(inventory);
    }


    @DeleteMapping("/{inventoryId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteInventory(@PathVariable int inventoryId){
        service.deleteInventory(inventoryId);
    }
}
