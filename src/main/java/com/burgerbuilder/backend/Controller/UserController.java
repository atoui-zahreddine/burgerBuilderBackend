package com.burgerbuilder.backend.Controller;

import com.burgerbuilder.backend.DTO.Response.UserDTOResponse;
import com.burgerbuilder.backend.Model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(@AuthenticationPrincipal User principal){
        var response=new UserDTOResponse(principal);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
