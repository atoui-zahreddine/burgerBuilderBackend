package com.burgerbuilder.backend.Repository;

import com.burgerbuilder.backend.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


@Repository
public interface UserRepository  extends JpaRepository<User, UUID> {
    Optional<User> getUserByEmail(String email);
    Optional<User> getUserByEmailVerificationToken(String token);
}
