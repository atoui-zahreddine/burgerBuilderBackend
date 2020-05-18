package com.burgerbuilder.backend.Repository;

import com.burgerbuilder.backend.Model.PasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TokenRepository  extends JpaRepository<PasswordToken, Long> {
    Optional<PasswordToken> findByTokenAndExpireDateAfter(UUID token, Date currentDate);

    @Transactional
    @Modifying
    @Query(nativeQuery=true, value="delete from password_token where user_id=?1")
    void deleteByUserId(String userId);
}
