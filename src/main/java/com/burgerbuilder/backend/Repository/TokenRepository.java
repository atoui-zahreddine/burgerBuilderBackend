package com.burgerbuilder.backend.Repository;

import com.burgerbuilder.backend.Model.PasswordToken;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TokenRepository  extends JpaRepository<PasswordToken, Long> {
    @EntityGraph(attributePaths = {"user"})
    Optional<PasswordToken> findByTokenAndExpireDateAfter(UUID token, Date currentDate);

    @Transactional
    @Modifying
    @Query("delete from PasswordToken p where p.user.id=:userId")
    void deleteByUserId(@Param("userId") UUID userId);
}
