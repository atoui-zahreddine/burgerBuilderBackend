package com.burgerbuilder.backend.Service;

import com.burgerbuilder.backend.DTO.Request.OrderRequest;
import com.burgerbuilder.backend.DTO.Response.OrderResponse;
import com.burgerbuilder.backend.Exception.BadRequestException;
import com.burgerbuilder.backend.Exception.NotFoundException;
import com.burgerbuilder.backend.Model.*;
import com.burgerbuilder.backend.Repository.IngredientRepository;
import com.burgerbuilder.backend.Repository.OrderRepository;
import com.burgerbuilder.backend.Repository.OrderedIngredientsRepository;
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
    private final OrderedIngredientsRepository orderedIngredientsRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, IngredientRepository ingredientRepository, ProductRepository productRepository, OrderedIngredientsRepository orderedIngredientsRepository) {
        this.orderRepository = orderRepository;
        this.ingredientRepository = ingredientRepository;
        this.productRepository = productRepository;
        this.orderedIngredientsRepository = orderedIngredientsRepository;
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

    public ResponseEntity<?> getAllUserOrders(User user){
        var orders=orderRepository.findAllByUser(user)
                    .stream()
                    .map(OrderResponse::new)
                    .collect(Collectors.toSet());
        return new ResponseEntity<>(orders,HttpStatus.OK);
    }

    public ResponseEntity<?> getUserOrderById(String id,User user) {
        var order=orderRepository.findOrderByIdAndUser(UUID.fromString(id),user)
                .orElseThrow( () -> new NotFoundException(120,"no order with this id :"+id));

        return new ResponseEntity<>(new OrderResponse(order),HttpStatus.OK);
    }

    public ResponseEntity<?> updateOrderById(String orderId, OrderRequest newOrder, User user) {
        var order=orderRepository.findOrderByIdAndUser(UUID.fromString(orderId),user)
                .orElseThrow(()-> new NotFoundException(404,"no order with this id "+orderId));

        if (order.getStatus().equals("preparing"))
            throw new BadRequestException("you can't update an order when his status=preparing",400);

        newOrder.getIngredients().forEach((ingredientName, newQuantity) -> {
            ingredientRepository.findById(ingredientName)
                    .orElseThrow(()->new NotFoundException(404,"no ingredient with this name:"+ingredientName));
            orderedIngredientsRepository.updateQuantityByOrderIdAndIngredientName(newQuantity,
                    orderId,ingredientName);
        });

        return new ResponseEntity<>(Map.of("status","updated"),HttpStatus.OK);
    }
}
