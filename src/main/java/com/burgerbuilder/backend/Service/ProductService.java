package com.burgerbuilder.backend.Service;

import com.burgerbuilder.backend.DTO.Request.ProductRequest;
import com.burgerbuilder.backend.DTO.Response.ApiResponse;
import com.burgerbuilder.backend.Exception.NotFoundException;
import com.burgerbuilder.backend.Exception.ResourceExistException;
import com.burgerbuilder.backend.Model.Product;
import com.burgerbuilder.backend.Repository.ProductRepository;
import com.burgerbuilder.backend.Utils.Enums.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
        var response=new ApiResponse<>(Status.SUCCESS,null);
            return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    public ResponseEntity<?> getAllProducts() {
        var response=new ApiResponse<>(Status.SUCCESS,productRepository.findAll());
        return new ResponseEntity<>(response,HttpStatus.OK);
    }


    public ResponseEntity<?> getProductById(String productId) {
        var product=productRepository.findById(UUID.fromString(productId))
                .orElseThrow(() -> new NotFoundException(404,"no product with this name"+productId));
        var response=new ApiResponse<>(Status.SUCCESS,product);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    public ResponseEntity<?> updateProductById(String productId,ProductRequest request) {
        var product = findProductById(productId);

        checkIfProductExist(request.getName());

        if((request.getBasePrice()) >= 1.00 )
            product.setBasePrice(request.getBasePrice());
        if(request.getName().trim().length()>1)
            product.setName(request.getName());

        productRepository.updateProduct(UUID.fromString(productId),product.getName(),product.getBasePrice());
        var response=new ApiResponse<>(Status.SUCCESS,null);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    public ResponseEntity<?> deleteProductById(String productId){
        if(!productRepository.existsById(UUID.fromString(productId)))
            throw new NotFoundException(404,"no product with this id"+productId);

        productRepository.deleteById(UUID.fromString(productId));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private Product findProductById(String productId) {
        return productRepository.findById(UUID.fromString(productId))
                .orElseThrow(() -> new NotFoundException(404,"no product with this name"+productId));
    }

    private void checkIfProductExist(String productName) {
        if(productRepository.existsByName(productName))
            throw new ResourceExistException(HttpStatus.CONFLICT.value(),"another product with this name exist .");
    }

}
