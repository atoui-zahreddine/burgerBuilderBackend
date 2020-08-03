package com.burgerbuilder.backend.Service;

import com.burgerbuilder.backend.DTO.Request.ProductRequest;
import com.burgerbuilder.backend.Exception.BadRequestException;
import com.burgerbuilder.backend.Exception.NotFoundException;
import com.burgerbuilder.backend.Exception.ResourceExistException;
import com.burgerbuilder.backend.Model.Product;
import com.burgerbuilder.backend.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;


@Service
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ResponseEntity<?> createProduct(ProductRequest request){
            checkIfProductExist(request.getName());

            productRepository.save(new Product(request.getName(),request.getBasePrice()));
            return new ResponseEntity<>(Map.of("status","success"),HttpStatus.CREATED);
    }

    public ResponseEntity<?> getAllProducts() {
        return new ResponseEntity<>(productRepository.findAll(),HttpStatus.OK);
    }

    public ResponseEntity<?> getProductByName(String productName){
        if(productName.trim().length() == 0)
            throw new BadRequestException("name must not be empty",400);

        var product= findProductByName(productName);

        return new ResponseEntity<>(product,HttpStatus.OK);
    }

    public ResponseEntity<?> getProductById(String productId) {
        var product=productRepository.findById(UUID.fromString(productId))
                .orElseThrow(() -> new NotFoundException(404,"no product with this name"+productId));

        return new ResponseEntity<>(product,HttpStatus.OK);
    }

    public ResponseEntity<?> updateProductByName(String productName,ProductRequest request) {
        var product = findProductByName(productName);

        checkIfProductExist(request.getName());
        product.setBasePrice(request.getBasePrice());
        product.setName(request.getName());

        productRepository.save(product);
        return new ResponseEntity<>(Map.of("status","updated"),HttpStatus.OK);
    }

    private void checkIfProductExist(String productName) {
        if(productRepository.existsByName(productName))
            throw new ResourceExistException(HttpStatus.CONFLICT.value(),"another product with this name exist .");
    }

    private Product findProductByName(String productName) {
        return productRepository.findProductByName(productName)
                .orElseThrow(() -> new NotFoundException(404, "no product with this name" + productName));
    }
}
