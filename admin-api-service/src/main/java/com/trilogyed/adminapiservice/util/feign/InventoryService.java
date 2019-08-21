package com.trilogyed.adminapiservice.util.feign;

import com.trilogyed.adminapiservice.model.Inventory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name="inventory-service")
public interface InventoryService {
   
   @RequestMapping(value = "/inventory", method = RequestMethod.POST)
   Inventory createInventory(@Valid @RequestBody Inventory inventory);
   
   @RequestMapping(value = "/inventory/{inventoryId}", method = RequestMethod.GET)
   Inventory getInventory(@Valid @PathVariable("inventoryId") int inventoryId);
   
   @RequestMapping(value = "/inventory", method = RequestMethod.GET)
   List<Inventory> getAllInventory();
   
   @RequestMapping(value = "/inventory", method = RequestMethod.PUT)
   void amendInventory(@Valid @RequestBody Inventory inventory);
   
   @RequestMapping(value = "/inventory/{inventoryId}", method = RequestMethod.DELETE)
   void deleteInventory(@Valid @PathVariable("inventoryId") int inventoryId);
   
}
