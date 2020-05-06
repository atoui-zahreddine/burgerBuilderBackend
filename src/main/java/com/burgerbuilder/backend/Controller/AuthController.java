package com.burgerbuilder.backend.Controller;

import com.burgerbuilder.backend.DTO.Request.SignInRequest;
import com.burgerbuilder.backend.DTO.Request.SignUpRequest;
import com.burgerbuilder.backend.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping({"/signin","singin/"})
    public ResponseEntity<?> login(@Valid @RequestBody SignInRequest request){
        return userService.login(request);
    }



}
