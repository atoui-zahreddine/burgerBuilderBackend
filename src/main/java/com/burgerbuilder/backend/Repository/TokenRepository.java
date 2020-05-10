package com.burgerbuilder.backend.Repository;

import com.burgerbuilder.backend.Model.PasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TokenRepository  extends JpaRepository<PasswordToken, Long> {
    Optional<PasswordToken> findByTokenAndExpireDateAfter(UUID token, Date currentDate);

    void deleteByToken(UUID token);
}
