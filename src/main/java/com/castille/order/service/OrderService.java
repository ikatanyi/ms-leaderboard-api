package com.castille.order.service;

import com.castille.customer.model.Customer;
import com.castille.customer.service.CustomerService;
import com.castille.exception.APIException;
import com.castille.order.data.OrderDto;
import com.castille.order.model.Order;
import com.castille.order.repository.OrderRepository;
import com.castille.pkg.model.ProductPackage;
import com.castille.pkg.service.ProductPackageService;
import com.castille.product.model.Product;
import com.castille.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 *
 * @author Kennedy Ikatanyi
 */
@Slf4j
@Service
@RequiredArgsConstructor
//@CacheConfig(cacheNames = {"Order"})
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final CustomerService customerService;
    private final ProductPackageService productPackageService;

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);

    private final Queue qu;

//
    @Transactional
    @RabbitListener(queues = "#{qu.getName()}")
    public void createOrder(OrderDto orderDto) {
        LOGGER.info("Getting Orders.....");
        LOGGER.info("Order received is..\n" + orderDto.toString());
        Order order = orderDto.toOrder();

//        Product product = productService.fetchProductOrThrow(orderDto.getProductId());
//        ProductPackage productPackage = productPackageService.fetchProductPackageOrThrow(orderDto.getProductPackageId());
//        Customer customer = customerService.fetchCustomerByIdOrThrow(orderDto.getCustomerId());
//
//        order.setProduct(product);
//        order.setProductPackage(productPackage);
//        order.setCustomer(customer);
//        return orderRepository.save(order);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Order updateOrder(Long id, OrderDto orderDto) {
        Order order = fetchOrderOrThrow(id);
        Product product = productService.fetchProductOrThrow(orderDto.getProductId());
        ProductPackage productPackage = productPackageService.fetchProductPackageOrThrow(orderDto.getProductPackageId());
        Customer customer = customerService.fetchCustomerByIdOrThrow(orderDto.getCustomerId());
        order.setProduct(product);
        order.setProductPackage(productPackage);
        order.setCustomer(customer);
        order.setInstallationAddress(orderDto.getInstallationAddress());
        order.setInstallationTime(orderDto.getInstallationTime());
        return orderRepository.save(order);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Boolean deleteOrder(Long id) {
        Order order = fetchOrderOrThrow(id);
        orderRepository.delete(order);
        return true;
    }

    public Page<Order> fetchOrders(Pageable pageable) {
        Page<Order> orders = orderRepository.findAll(pageable);
        return orders;
    }

    public Order fetchOrderOrThrow(Long id) {
        return this.orderRepository.findById(id)
                .orElseThrow(() -> APIException.notFound("Order id {0} not found.", id));
    }


}
