package com.burgerbuilder.backend.Controller;

import com.burgerbuilder.backend.DTO.Request.ProductRequest;
import com.burgerbuilder.backend.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("")
    public ResponseEntity<?> createProduct( @RequestBody @Valid ProductRequest request){
        return productService.createProduct(request);
    }

    @GetMapping("")
    public ResponseEntity<?> getAllProducts(){
        return productService.getAllProducts();
    }

    @GetMapping("/{productId}")
    public ResponseEntity<?> getProductById(@PathVariable String productId){
        return productService.getProductById(productId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProductById(@RequestBody ProductRequest request,@PathVariable String id){
        return productService.updateProductById(id,request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProductById(@PathVariable String id){
        return productService.deleteProductById(id);
    }

}
