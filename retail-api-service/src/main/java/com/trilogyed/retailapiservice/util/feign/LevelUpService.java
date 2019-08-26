package com.trilogyed.retailapiservice.util.feign;

import com.trilogyed.retailapiservice.model.LevelUp;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name="levelup-service")
public interface LevelUpService {
   
   @RequestMapping(value = "/levelups", method = RequestMethod.POST)
   LevelUp createLevelUp(@Valid @RequestBody LevelUp levelUp);
   
   @RequestMapping(value = "/levelups/{levelUpId}", method = RequestMethod.GET)
   LevelUp getLevelUp(@Valid @PathVariable("levelUpId") int levelUpId);
   
   @RequestMapping(value = "/levelups/customer/{customerId}", method = RequestMethod.GET)
   LevelUp getLevelUpByCustomer(@Valid @PathVariable("customerId") int customerId);
   
   @RequestMapping(value = "/levelups", method = RequestMethod.GET)
   List<LevelUp> getAllLevelUp();
   
   @RequestMapping(value = "/levelups", method = RequestMethod.PUT)
   void amendLevelUp(@Valid @RequestBody LevelUp levelUp);
   
   @RequestMapping(value = "/levelups/{levelUpId}", method = RequestMethod.DELETE)
   void deleteLevelUp(@Valid @PathVariable("levelUpId") int levelUpId);
   
}
