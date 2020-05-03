package com.burgerbuilder.backend.Service;

import com.burgerbuilder.backend.DTO.Request.SignInRequest;
import com.burgerbuilder.backend.DTO.Request.SignUpRequest;
import com.burgerbuilder.backend.DTO.Response.SignUpResponse;
import com.burgerbuilder.backend.Exception.BadCredentialsException;
import com.burgerbuilder.backend.Exception.NotFoundException;
import com.burgerbuilder.backend.Exception.ResourceExistException;
import com.burgerbuilder.backend.Model.User;
import com.burgerbuilder.backend.Repository.UserRepository;
import com.burgerbuilder.backend.Utils.JwtUtils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @Autowired
    public UserService(UserRepository userRepository, AuthenticationManager authenticationManager, BCryptPasswordEncoder passwordEncoder, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public UserDetails loadUserByUsername(String email)  throws NotFoundException {
        Optional<User> user=userRepository.getUserByEmail(email);

        if(!user.isPresent())
            throw new NotFoundException(150,"no user with this email= "+email);

        return user.get();
    }



    public ResponseEntity<?> save(SignUpRequest signUpRequest) {

        if(userRepository.getUserByEmail(signUpRequest.getEmail()).isPresent()){
            throw new ResourceExistException(115,"email already exist !");
        }
        signUpRequest.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        var response=new SignUpResponse(userRepository.save(new User(signUpRequest)));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<?> login(SignInRequest request) throws BadCredentialsException,NotFoundException{
        Authentication authentication;
        try{
            authentication=authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword())
            );
        }
        catch (org.springframework.security.authentication.BadCredentialsException ex){
            throw new BadCredentialsException(152,"bad credentials");
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Map<String,String> response=new HashMap<>();
        response.put("Token", jwtUtils.generateToken());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
