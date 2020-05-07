package com.burgerbuilder.backend.Service;

import com.burgerbuilder.backend.DTO.Request.SignInRequest;
import com.burgerbuilder.backend.DTO.Request.SignUpRequest;
import com.burgerbuilder.backend.DTO.Request.UpdatePasswordDTORequest;
import com.burgerbuilder.backend.DTO.Response.UserDTOResponse;
import com.burgerbuilder.backend.Exception.BadCredentialsException;
import com.burgerbuilder.backend.Exception.UpdatePasswordException;
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

    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private  AuthenticationManager authenticationManager;
    @Autowired
    private  BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private  JwtUtils jwtUtils;


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
        var user =new User(signUpRequest);
        user.addAuthority("ROLE_USER");
        user =userRepository.save(user);
        var response=new UserDTOResponse(user);
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
        response.put("Token", jwtUtils.generateToken((User)authentication.getPrincipal()));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<?> updatePassword(UpdatePasswordDTORequest request, User user) {
            if(!passwordEncoder.matches(request.getOldPassword(),user.getPassword()))
                throw new UpdatePasswordException("old password is not correct.",194);

            if(passwordEncoder.matches(request.getNewPassword(),user.getPassword()))
                throw new UpdatePasswordException("same password.",195);

            user.setPassword(passwordEncoder.encode(request.getNewPassword()));
            userRepository.save(user);
            return new ResponseEntity<>(Map.of("Status","Ok"),HttpStatus.OK);
    }
}
