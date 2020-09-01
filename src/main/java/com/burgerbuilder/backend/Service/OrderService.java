package com.burgerbuilder.backend.Service;

import com.burgerbuilder.backend.DTO.Request.OrderRequest;
import com.burgerbuilder.backend.DTO.Response.ApiResponse;
import com.burgerbuilder.backend.DTO.Response.OrderResponse;
import com.burgerbuilder.backend.Exception.AuthorizationException;
import com.burgerbuilder.backend.Exception.NotFoundException;
import com.burgerbuilder.backend.Model.*;
import com.burgerbuilder.backend.Repository.IngredientRepository;
import com.burgerbuilder.backend.Repository.OrderRepository;
import com.burgerbuilder.backend.Repository.OrderedIngredientsRepository;
import com.burgerbuilder.backend.Repository.ProductRepository;
import com.burgerbuilder.backend.Utils.Enums.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    public ResponseEntity<?> createOrder(OrderRequest request,User user){

        Order order=new Order();
        Set<OrderedIngredients> orderedIngredients=getOrderedIngredients(request, order);

        Product product=productRepository.findProductByName("burger")
                .orElseThrow(() -> new NotFoundException(130,"no product with this name"));

        float price= product.getBasePrice()
                + (float) orderedIngredients.stream()
                .mapToDouble(ingredient -> ingredient.getIngredient().getPrice())
                .reduce(0,Double::sum);

        order.setIngredients(orderedIngredients);
        order.setUser(user);
        order.setPrice(price);
        order.setProduct(product);
        orderRepository.save(order);
        var response=new ApiResponse<>(Status.SUCCESS,null);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    private Set<OrderedIngredients> getOrderedIngredients(OrderRequest request, Order order) throws NotFoundException{
        Set<OrderedIngredients> orderedIngredients = new HashSet<>() ;

        for(Map.Entry<String, Integer> ingredient : request.getIngredients().entrySet()){
            Ingredient ing=ingredientRepository.findById(ingredient.getKey())
                    .orElseThrow(() -> new NotFoundException(212,"no ingredient with this name :"+ingredient.getKey()));
            orderedIngredients.add(new OrderedIngredients(ing, order,ingredient.getValue()));
        }
        return orderedIngredients;
    }

    public ResponseEntity<?> getAllUserOrders(User user){
        var orders=orderRepository.findAllByUser(user)
                    .stream()
                    .map(OrderResponse::new)
                    .collect(Collectors.toSet());
        var response=new ApiResponse<>(Status.SUCCESS,orders);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    private Order getOrderByIdAndUser(String id, User user) {
        return orderRepository.findOrderByIdAndUser(UUID.fromString(id), user)
                .orElseThrow(() -> new NotFoundException(404, "no order with this id :" + id));
    }

    public ResponseEntity<?> getUserOrderById(String id,User user) {
        var order= getOrderByIdAndUser(id,user);
        var response=new ApiResponse<>(Status.SUCCESS,new OrderResponse(order));
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    public ResponseEntity<?> updateOrderById(String orderId, OrderRequest newOrder, User user) {
        var order= getOrderByIdAndUser(orderId, user);

        if (order.getStatus().equals("preparing"))
            throw new AuthorizationException("you can't update an order when his status=preparing",403);

        newOrder.getIngredients().forEach((ingredientName, newQuantity) -> {
            ingredientRepository.findById(ingredientName)
                    .orElseThrow(()->new NotFoundException(404,"no ingredient with this name:"+ingredientName));
            orderedIngredientsRepository.updateQuantityByOrderIdAndIngredientName(newQuantity,
                    orderId,ingredientName);
        });
        var response=new ApiResponse<>(Status.SUCCESS,null);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    public ResponseEntity<?> deleteOrderById(String id, User user) {
        var order = getOrderByIdAndUser(id,user);

        if(order.getStatus().equals("preparing"))
            throw new AuthorizationException("you can't delete an order when his status=preparing",403);
        orderRepository.deleteOrderById(order.getId());
        return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
