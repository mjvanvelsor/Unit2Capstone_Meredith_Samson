package com.trilogyed.levelupservice;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.amqp.core.Queue;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class LevelupServiceApplication {
	
	public static final String EXCHANGE = "levelup-exchange";
	public static final String QUEUE_NAME = "levelup-create-queue";
	public static final String ROUTING_KEY = "levelup.create.#";
	
	@Bean
	Queue queue() {
		return new Queue(QUEUE_NAME, false);
	}
	
	@Bean
	TopicExchange exchange() {
		return new TopicExchange(EXCHANGE);
	}
	
	@Bean
	Binding binding(Queue queue, TopicExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
	}
	
	@Bean
	public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	public static void main(String[] args) {
		SpringApplication.run(LevelupServiceApplication.class, args);
	}

}
