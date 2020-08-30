package com.burgerbuilder.backend.Controller;

import com.burgerbuilder.backend.DTO.Request.IngredientRequest;
import com.burgerbuilder.backend.Service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/ingredients")
public class IngredientController {

    private final IngredientService ingredientService;

    @Autowired
    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @PostMapping("")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> createIngredient(@RequestBody @Valid IngredientRequest request){
        return ingredientService.createIngredient(request);
    }

    @GetMapping("")
    public ResponseEntity<?> getAllIngredients(){
        return ingredientService.getAllIngredients();
    }

    @GetMapping("/{name}")
    public ResponseEntity<?> getIngredientByName(@PathVariable String name){
        return ingredientService.getIngredientByName(name);
    }

    @PutMapping("/{name}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public  ResponseEntity<?> updateIngredientByName(@PathVariable String name,@RequestBody @Valid IngredientRequest request){
        return ingredientService.updateIngredientByName(name,request);
    }

    @DeleteMapping("/{name}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteIngredientByName(@PathVariable String name){
        return ingredientService.deleteIngredientByName(name);
    }

}
