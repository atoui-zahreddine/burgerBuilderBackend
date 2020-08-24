package com.burgerbuilder.backend.Service;

import com.burgerbuilder.backend.DTO.Request.*;
import com.burgerbuilder.backend.DTO.Response.UserResponse;
import com.burgerbuilder.backend.Exception.*;
import com.burgerbuilder.backend.Model.PasswordToken;
import com.burgerbuilder.backend.Model.User;
import com.burgerbuilder.backend.Repository.TokenRepository;
import com.burgerbuilder.backend.Repository.UserRepository;
import com.burgerbuilder.backend.Utils.JwtUtils.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

import javax.mail.MessagingException;
import java.util.*;

@Service
public class UserService implements UserDetailsService {

    private final Logger logger= LoggerFactory.getLogger(UserService.class);

    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private  AuthenticationManager authenticationManager;
    @Autowired
    private  BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private  JwtUtils jwtUtils;
    @Autowired
    private EmailService emailService;

    @Value("${auth.expiration}")
    private String tokenExpiration;


    @Override
    public UserDetails loadUserByUsername(String email)  throws NotFoundException {
        Optional<User> user=userRepository.getUserByEmail(email);

        if(!user.isPresent())
            throw new NotFoundException(150,"no user with this email= "+email);

        return user.get();
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
        response.put("token", jwtUtils.generateToken((User)authentication.getPrincipal()));
        response.put("expiresIn",tokenExpiration);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<?> createUser(SignUpRequest signUpRequest) {

        if(userRepository.getUserByEmail(signUpRequest.getEmail()).isPresent()){
            throw new ResourceExistException(115,"email already exist !");
        }
        signUpRequest.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        var user =new User(signUpRequest);
        user.addAuthority("ROLE_USER");
        user =userRepository.save(user);
        try {
            emailService.sendEmailVerificationMail(user);
        } catch (MessagingException e) {
            logger.error("error with sending email verification mail .");
        }
        var response=new UserResponse(user);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<?> updatePassword(UpdatePasswordRequest request, User user) {
            if(!passwordEncoder.matches(request.getOldPassword(),user.getPassword()))
                throw new UpdatePasswordException("old password is not correct.",194);

            if(passwordEncoder.matches(request.getNewPassword(),user.getPassword()))
                throw new UpdatePasswordException("same password.",195);

            user.setPassword(passwordEncoder.encode(request.getNewPassword()));
            userRepository.save(user);
            return new ResponseEntity<>(Map.of("Status","Ok"),HttpStatus.OK);
    }

    public void resetPassword(ResetPasswordRequest request) {
        Optional<User> user=userRepository.getUserByEmail(request.getEmail());

        if(user.isPresent()){
            UUID token=UUID.randomUUID();
            Date expirationDate=new Date( System.currentTimeMillis() + 3600 * 24 * 1000 );
            PasswordToken passwordToken=new PasswordToken(token,expirationDate,user.get());
            tokenRepository.save(passwordToken);
            try {
                emailService.sendPasswordResetMail(user.get(),token.toString());
            } catch (MessagingException e) {
                logger.error("error with sending password reset mail .");
                throw new InternalServerException("server error",500);
            }
        }
    }

    public ResponseEntity<?> validatePasswordReset(UUID token, String newPassword){

        PasswordToken passwordToken=tokenRepository.findByTokenAndExpireDateAfter(token,new Date())
                            .orElseThrow(()->new NotFoundException(404,"the token is expired or invalid ."));

        userRepository.updateUserPassword(passwordEncoder.encode(newPassword));
        tokenRepository.deleteByUserId(passwordToken.getUser().getId());
        return new ResponseEntity<>(Map.of("Status","Ok"),HttpStatus.OK);
    }

    public ResponseEntity<?> sendEmailVerificationToken(User user){
        if(user.isEmailVerified() && user.getEmailVerificationToken()==null)
            throw new BadRequestException("this email is already verified.",400);

        try {
            emailService.sendEmailVerificationMail(user);
        } catch (MessagingException e) {
            logger.error("error with sending email verification mail .");
        }

    return new ResponseEntity<>(Map.of("Status","OK"),HttpStatus.OK);
    }

    public ResponseEntity<?> verifyEmailAddressToken(EmailValidationRequest request) {
        var user=userRepository.getUserByEmailVerificationToken(request.getToken())
                .orElseThrow(()->new NotFoundException(201,"this token doesn't exist " +
                        "or the email address is verified."));

        user.setEmailVerificationToken(null);
        user.setEmailVerified(true);

        userRepository.save(user);

        return new ResponseEntity<>(Map.of("Status","OK"),HttpStatus.OK);
    }
}
