package com.neim.springboot.restservices.controllers;

import com.neim.springboot.restservices.entities.Order;
import com.neim.springboot.restservices.entities.User;
import com.neim.springboot.restservices.exceptions.UsernameNotFoundException;
import com.neim.springboot.restservices.repositories.OrderRepository;
import com.neim.springboot.restservices.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class OrderController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("/{userId}/orders")
    public List<Order> getAllOrders(@PathVariable Long userId) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findById(userId);

        if (user.isEmpty()) throw new UsernameNotFoundException("User Not Found");

        return user.get().getOrders();
    }

    @PostMapping("/{userId}/orders")
    public void createOrder(@PathVariable Long userId, @RequestBody Order order) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) throw new UsernameNotFoundException("User Not Found");

        User user = userOptional.get();
        order.setUser(user);
        orderRepository.save(order);
    }

    @GetMapping("/{userId}/orders/{orderId}")
    public Optional<Order> getOrderByOrderId(@PathVariable Long userId, @PathVariable Long orderId) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) throw new UsernameNotFoundException("User Not Found");

        return orderRepository.findById(orderId);
    }
}
