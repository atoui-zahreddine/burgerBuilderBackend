package com.burgerbuilder.backend.Utils.Seeds;

import com.burgerbuilder.backend.BackendApplication;
import com.burgerbuilder.backend.Model.Address;
import com.burgerbuilder.backend.Model.Ingredient;
import com.burgerbuilder.backend.Model.Product;
import com.burgerbuilder.backend.Model.User;
import com.burgerbuilder.backend.Repository.*;
import com.burgerbuilder.backend.Service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Seeds implements CommandLineRunner {
    private Logger logger= LoggerFactory.getLogger(BackendApplication.class);
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final ProductRepository productRepository;
    private final IngredientRepository ingredientRepository;
    private final OrderRepository orderRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public Seeds(UserRepository userRepository, AddressRepository addressRepository, ProductRepository productRepository, IngredientRepository ingredientRepository, OrderRepository orderRepository, BCryptPasswordEncoder passwordEncoder, EmailService emailService) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.productRepository = productRepository;
        this.ingredientRepository = ingredientRepository;
        this.orderRepository = orderRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    @Override
    public void run(String... args)  {
        List<User> users=userRepository.findAll();
        if(users.isEmpty()) {
            logger.info("adding some data ...");
            User user = new User("at.zahreddine@gmail.com",passwordEncoder.encode("azerty123"),
                    "zahreddine","atoui","26945535");
            user.addAuthority("ROLE_USER");
            Address address=new Address();
            address.setCity("medenine");
            address.setStreet("26 rue imam chafei");
            address.setZipCode("4100");
            address.setUser(user);
            user.setAddresses(List.of(address));
            userRepository.save(user);
            List<Ingredient> ingredients =List.of(new Ingredient("bacon",800.00f),
                    new Ingredient("cheese",800.00f),
                    new Ingredient("meal",800.00f),
                    new Ingredient("salad",800.00f));
            ingredients.forEach(ingredientRepository::save);
            productRepository.save(new Product("burger",4000.00f));
        }

    }
}
