package com.neim.springboot.restservices.controllers;

import com.neim.springboot.restservices.entities.Order;
import com.neim.springboot.restservices.entities.User;
import com.neim.springboot.restservices.exceptions.UsernameNotFoundException;
import com.neim.springboot.restservices.repositories.OrderRepository;
import com.neim.springboot.restservices.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/hateoas/users")
public class OrderHateoasController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("/{userId}/orders")
    public CollectionModel<Order> getAllOrders(@PathVariable Long userId) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findById(userId);

        if (user.isEmpty()) throw new UsernameNotFoundException("User Not Found");

        List<Order> allOrders = user.get().getOrders();

        return CollectionModel.of(allOrders);
    }
}
