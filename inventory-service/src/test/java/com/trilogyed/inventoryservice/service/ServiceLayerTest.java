package com.trilogyed.inventoryservice.service;

import com.trilogyed.inventoryservice.dao.InventoryDao;
import com.trilogyed.inventoryservice.dao.InventoryDaoJdbcTemplateImpl;
import com.trilogyed.inventoryservice.model.Inventory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceLayerTest {

    InventoryDao inventoryDao;
    ServiceLayer service;

    @Before
    public void setUp() throws Exception {
        setUpInventoryDaoMock();
        service = new ServiceLayer(inventoryDao);
    }

    private void setUpInventoryDaoMock(){
        inventoryDao = mock(InventoryDaoJdbcTemplateImpl.class);
        Inventory inventory = new Inventory();
        inventory.setProductId(1);
        inventory.setQuantity(5);

        Inventory inventory1 = new Inventory();
        inventory1.setInventoryId(1);
        inventory1.setProductId(1);
        inventory1.setQuantity(5);

        List<Inventory> inventoryList = new ArrayList<>();
        inventoryList.add(inventory1);

        doReturn(inventory1).when(inventoryDao).createInventory(inventory);
        doReturn(inventory1).when(inventoryDao).getInventory(1);
        doReturn(inventoryList).when(inventoryDao).getAllInventories();
    }

    @Test
    public void createGetInventory() {
        Inventory inventory1 = new Inventory();
        inventory1.setInventoryId(1);
        inventory1.setProductId(1);
        inventory1.setQuantity(5);

        Inventory inv1 = service.createInventory(inventory1);
        Inventory inv2 = service.getInventory(inv1.getInventoryId());
        assertEquals(inv1, inv2);
    }

    @Test
    public void getAllInventories() {
        List<Inventory> inventoryList = service.getAllInventories();
        assertEquals(inventoryList.size(), 1);
    }

    @Test
    public void amendInventory() {
        Inventory inventory1 = new Inventory();
//        inventory1.setInventoryId(1);
        inventory1.setProductId(1);
        inventory1.setQuantity(10);
        service.amendInventory(inventory1);

        ArgumentCaptor<Inventory> inventoryCaptor = ArgumentCaptor.forClass(Inventory.class);
        verify(inventoryDao).amendInventory(inventoryCaptor.capture());
        assertEquals(inventory1.getQuantity(), inventoryCaptor.getValue().getQuantity());
    }

    @Test
    public void deleteInventory() {
        service.deleteInventory(1);
        Inventory inventory = service.getInventory(1);
    }
}