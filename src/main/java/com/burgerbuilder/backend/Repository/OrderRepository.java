package com.burgerbuilder.backend.Repository;

import com.burgerbuilder.backend.Model.Order;
import com.burgerbuilder.backend.Model.User;
import org.springframework.data.jpa.repository.EntityGraph;
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
public interface OrderRepository extends JpaRepository<Order, UUID> {

    @EntityGraph(attributePaths = {"ingredients","product","user"})
    List<Order> findAllByUser(User user);

    @EntityGraph(attributePaths = {"ingredients","product","user"})
    Optional<Order> findOrderByIdAndUser(UUID id,User user);

    @Transactional
    @Modifying
    @Query("delete from orders o where  o.id=:orderId")
    void deleteOrderById(@Param("orderId") UUID orderId);


}