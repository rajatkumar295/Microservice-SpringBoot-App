package com.orderService.service;

import com.orderService.config.UserClient;
import com.orderService.entity.Order;
import com.orderService.exception.ResourceNotFoundException;
import com.orderService.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserClient userClient;

    public Order create(Order order) {
        try {
            userClient.getUserById(order.getUserId());
        } catch (Exception e) {
            throw new ResourceNotFoundException("User not found");
        }
        return orderRepository.save(order);
    }

    public Order get(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }

    public Order update(Long id, Order order) {
        Order existing = get(id);
        existing.setProduct(order.getProduct());
        existing.setQuantity(order.getQuantity());
        existing.setPrice(order.getPrice());
        return orderRepository.save(existing);
    }

    public void delete(Long id) {
        orderRepository.deleteById(id);
    }
}
