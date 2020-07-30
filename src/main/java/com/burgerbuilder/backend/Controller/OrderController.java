package com.burgerbuilder.backend.Controller;

import com.burgerbuilder.backend.DTO.Request.OrderRequest;
import com.burgerbuilder.backend.Model.User;
import com.burgerbuilder.backend.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/orders")
@RestController
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping({"/",""})
    public ResponseEntity<?> createOrder(@Valid @RequestBody OrderRequest request){
        return orderService.createOrder(request);
    }

    @GetMapping({"/",""})
    public ResponseEntity<?> getAllOrdersByUser(@AuthenticationPrincipal User user){
        return orderService.getAllUserOrders(user);
    }

    @GetMapping({"/{id}","/{id}/"})
    public ResponseEntity<?> getUserOrderById(@PathVariable("id") String id, @AuthenticationPrincipal User user){
        return orderService.getUserOrderById(id,user);
    }

    @PutMapping({"/{id}","/{id}/"})
    public ResponseEntity<?> updateOrderById(@PathVariable("id") String id , @Valid @RequestBody OrderRequest newOrder,
                                         @AuthenticationPrincipal User user){
        return orderService.updateOrderById(id , newOrder,user);
    }
}
