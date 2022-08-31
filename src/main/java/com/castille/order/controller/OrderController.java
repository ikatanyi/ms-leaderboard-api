package com.castille.order.controller;

import com.castille.order.data.OrderDto;
import com.castille.order.model.Order;
import com.castille.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 *
 * @author Kelsas
 */
@RestController
@Slf4j
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService service;
    private final RabbitTemplate rabbitTemplate;
    @Autowired
    private Binding binding;
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);


    @PostMapping
    public ResponseEntity<?> createOrder(@Valid @RequestBody OrderDto orderDto) {
        LOGGER.info("Sending Order to the queue.");
        rabbitTemplate.convertAndSend(binding.getExchange(), binding.getRoutingKey(), orderDto);
        LOGGER.info("Message sent successfully to the queue!!!");
//        return "Great!! your message is sent";
//        OrderDto dto = service.createOrder(orderDto).toOrderDto();
        return ResponseEntity.ok("message is sent successfully to the queue");

    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable(value = "id") Long id) {
        OrderDto dto = service.fetchOrderOrThrow(id).toOrderDto();
        return ResponseEntity.ok(dto);
    }
    

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(@PathVariable(value = "id") Long id, @Valid @RequestBody OrderDto orderDto) {

        Order order = service.updateOrder(id, orderDto);
        return ResponseEntity.ok(order.toOrderDto());

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable(value = "id") Long id) {

        Boolean isDeleted = service.deleteOrder(id);
        return ResponseEntity.ok(isDeleted);

    }

    @GetMapping
    public ResponseEntity<?> getAllOrders(
            @RequestParam(value = "page", defaultValue = "0",required = false) Integer page,
            @RequestParam(value = "pageSize", defaultValue = "20",required = false) Integer size) {
        page = page>=1 ? page-1 : page;
        Pageable pageable = PageRequest.of(page, size);

        Page<OrderDto> pagedList = service.fetchOrders(pageable).map(u -> u.toOrderDto());
        return ResponseEntity.ok(pagedList);
    }

}
