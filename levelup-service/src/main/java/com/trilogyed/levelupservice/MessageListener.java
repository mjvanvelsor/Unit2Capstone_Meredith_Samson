package com.trilogyed.levelupservice;

import com.trilogyed.levelupservice.model.LevelUp;
import com.trilogyed.levelupservice.service.ServiceLayer;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageListener {
   
   @Autowired
   private ServiceLayer service;
   
   @RabbitListener(queues = LevelupServiceApplication.QUEUE_NAME)
   public void receiveMessage(LevelUp levelUp) {
      System.out.println(levelUp);
      System.out.println(service.createLevelUp(levelUp));
   }
}
