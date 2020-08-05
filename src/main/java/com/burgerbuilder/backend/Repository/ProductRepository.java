package com.burgerbuilder.backend.Repository;


import com.burgerbuilder.backend.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository  extends JpaRepository<Product, UUID> {
    Optional<Product> findProductByName(String name);
    boolean existsByName(String name);

    @Transactional
    @Modifying
    @Query("delete from Product p where p.id=:productId")
    void deleteById(@Param("productId") UUID productId);
    @Transactional
    @Modifying
    @Query("update  Product p set p.name=:productName ,p.basePrice=:basePrice  where p.id=:productId")
    void updateProduct(@Param("productId") UUID productId,@Param("productName")String name,@Param("basePrice")float basePrice);
}
