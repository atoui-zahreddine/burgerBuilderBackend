package com.burgerbuilder.backend.Repository;

import com.burgerbuilder.backend.Model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AddressRepository extends JpaRepository<Address, UUID> {
    @Query("select a from Address  a where a.user.id=:userId")
    List<Address> findAllByUserId(@Param("userId") UUID userId);

    @Query("select a from Address a where a.id=:addressId and a.user.id=:userId")
    Optional<Address> findByIdAndUserId(@Param("addressId") UUID addressId
            , @Param("userId") UUID userId);

    @Query("select count(a) as col_0_0 from Address a where a.id=:addressId and a.user.id=:userId")
    int existsByIdAndUserId(@Param("addressId") UUID addressId
            , @Param("userId") UUID userId);

    @Transactional
    @Modifying
    @Query("delete from Address a where a.id=:addressId")
    void deleteById(@Param("addressId") UUID addressId);
}
