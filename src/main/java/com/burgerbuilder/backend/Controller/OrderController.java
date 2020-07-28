package com.burgerbuilder.backend.Controller;

import com.burgerbuilder.backend.DTO.Request.OrderRequest;
import com.burgerbuilder.backend.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/orders")
@RestController
public class OrderController {

    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping({"/",""})
    public ResponseEntity<?> createOrder(@Valid @RequestBody OrderRequest request){
        return orderService.createOrder(request);
    }

    @GetMapping({"/",""})
    public ResponseEntity<?> getAll(){
        return orderService.getAll();
    }

    @GetMapping({"/{id}","/{id}/"})
    public ResponseEntity<?> getOrderById(@PathVariable("id") String id){
        return orderService.getOrderById(id);
    }
}
