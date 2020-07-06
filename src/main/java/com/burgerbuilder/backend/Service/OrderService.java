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

import java.util.*;
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
        Optional<Product> product=productRepository.findProductByName("burger");
        float price = product.get().getBasePrice();

        for(Map.Entry<String, Integer> ingredient : request.getIngredients().entrySet()){

            Optional<Ingredient> ing=ingredientRepository.findById(ingredient.getKey());

            if (!ing.isPresent()) {
                throw  new NotFoundException(212,"no ingredient with this name :"+ingredient);
            }

            ing.get().setOrderedIngredients(orderedIngredients);
            orderedIngredients.add(new OrderedIngredients(ing.get(),order,ingredient.getValue()));
            price+=ing.get().getPrice()*ingredient.getValue();

        }


        order.setIngredients(orderedIngredients);
        User user=(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        order.setUser(user);
        order.setPrice(price);
        order.setProduct(product.get());
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
        var order=orderRepository.findOrderById(UUID.fromString(id));
        var user=(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!order.isPresent()){
            throw new NotFoundException(120,"no order with this id :"+id);
        }

        return new ResponseEntity<>(new OrderResponse(order.get(),user.getId()),HttpStatus.OK);
    }
}
