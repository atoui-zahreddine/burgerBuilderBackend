package com.burgerbuilder.backend.Service;

import com.burgerbuilder.backend.DTO.Request.OrderRequest;
import com.burgerbuilder.backend.DTO.Response.OrderResponse;
import com.burgerbuilder.backend.Exception.NotFoundException;
import com.burgerbuilder.backend.Model.*;
import com.burgerbuilder.backend.Repository.IngredientRepository;
import com.burgerbuilder.backend.Repository.OrderRepository;
import com.burgerbuilder.backend.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final IngredientRepository ingredientRepository;
    private final ProductRepository productRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, IngredientRepository ingredientRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.ingredientRepository = ingredientRepository;
        this.productRepository = productRepository;
    }

    public ResponseEntity<?> createOrder(OrderRequest request){

        Order order=new Order();
        Set<OrderedIngredients> orderedIngredients=new HashSet<>();

        for(Map.Entry<String, Integer> ingredient : request.getIngredients().entrySet()){
            Ingredient ing=ingredientRepository.findById(ingredient.getKey())
                    .orElseThrow(() -> new NotFoundException(212,"no ingredient with this name :"+ingredient.getKey()));
            orderedIngredients.add(new OrderedIngredients(ing,order,ingredient.getValue()));
        }

        Product product=productRepository.findProductByName("burger")
                .orElseThrow(() -> new NotFoundException(130,"no product with this name"));

        float price= product.getBasePrice()
                + (float) orderedIngredients.stream()
                .mapToDouble(ingredient -> ingredient.getIngredient().getPrice())
                .reduce(0,Double::sum);

        order.setIngredients(orderedIngredients);
        order.setUser((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        order.setPrice(price);
        order.setProduct(product);
        orderRepository.save(order);

        return new ResponseEntity<>(Map.of("status","created"), HttpStatus.OK);
    }

    public ResponseEntity<?> getAll(){
        var user=(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var orders=orderRepository.findAll()
                    .stream()
                    .map(order -> new OrderResponse(order,user.getId()))
                    .collect(Collectors.toList());
        return new ResponseEntity<>(orders,HttpStatus.OK);
    }

    public ResponseEntity<?> getOrderById(String id) {
        var order=orderRepository.findOrderById(UUID.fromString(id))
                .orElseThrow( () -> new NotFoundException(120,"no order with this id :"+id));

        var user=(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var response= new OrderResponse(order,user.getId()) ;
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
