package com.example.starbucksworker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

@Component
@RabbitListener(queues = "drink")
public class StarbucksWorker {

    private static final Logger log = LoggerFactory.getLogger(StarbucksWorker.class);

    @Autowired private StarbucksOrderRepository ordersRepository;
    @Autowired private StarbucksDrinkRepository drinksRepository;

    @RabbitHandler
    public void processStarbucksDrinks(Long orderId) {
        log.info( "Received  Order # " + orderId) ;

        // Sleeping to simulate buzy work
        try {
            Thread.sleep(60000); // 60 seconds
        } catch (Exception e) {}

        log.info( "Processed Order # " + orderId);

        StarbucksDrink drink = drinksRepository.findByOrderId(orderId);
        log.info(drink.getStatus());
        drink.setStatus("Fulfilled");
        drinksRepository.save(drink);

    }
}