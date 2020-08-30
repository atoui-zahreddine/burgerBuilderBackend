package com.burgerbuilder.backend.Repository;

import com.burgerbuilder.backend.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface UserRepository  extends JpaRepository<User, UUID> {
    @Query("select u from User u join fetch u.authorities where u.email=:email")
    Optional<User> getUserByEmail(@Param("email") String email);

    Optional<User> getUserByEmailVerificationToken(String token);

    @Transactional
    @Modifying
    @Query("update User u set u.password=:password")
    void updateUserPassword(@Param("password") String password);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "insert into authority values (?1)")
    void addAuthority(@Param("authority") String authority);
}
