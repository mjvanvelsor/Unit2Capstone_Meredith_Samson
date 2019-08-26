package com.trilogyed.inventoryservice.dao;

import com.trilogyed.inventoryservice.model.Inventory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InventoryDaoJdbcTemplateImplTest {

    @Autowired
    InventoryDao dao;

    @Before
    public void setUp() throws Exception {
        List<Inventory> inventoryList = dao.getAllInventories();
        inventoryList.stream().forEach(inventory -> dao.deleteInventory(inventory.getInventoryId()));
    }

    @Test
    public void createGetDeleteInventory() {
        Inventory inventory = new Inventory();
        inventory.setInventoryId(1);
        inventory.setProductId(1);
        inventory.setQuantity(5);

        dao.createInventory(inventory);
        assertEquals(inventory, dao.getInventory(inventory.getInventoryId()));
        dao.deleteInventory(inventory.getInventoryId());
        assertNull(dao.getInventory(inventory.getInventoryId()));
    }

    @Test
    public void getAllInventories() {
        Inventory inventory = new Inventory();
        inventory.setInventoryId(1);
        inventory.setProductId(1);
        inventory.setQuantity(5);
        dao.createInventory(inventory);

        Inventory inventory1 = new Inventory();
        inventory1.setInventoryId(2);
        inventory1.setProductId(2);
        inventory1.setQuantity(10);
        dao.createInventory(inventory1);

        List<Inventory> inventoryList = dao.getAllInventories();
        assertEquals(inventoryList.size(), 2);
    }

    @Test
    public void amendInventory() {
        Inventory inventory = new Inventory();
        inventory.setInventoryId(1);
        inventory.setProductId(1);
        inventory.setQuantity(5);
        dao.createInventory(inventory);

        inventory.setQuantity(10);
        dao.amendInventory(inventory);

        assertEquals(inventory, dao.getInventory(inventory.getInventoryId()));
    }
}