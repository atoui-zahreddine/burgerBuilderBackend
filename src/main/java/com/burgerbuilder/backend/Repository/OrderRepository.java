package com.burgerbuilder.backend.Repository;

import com.burgerbuilder.backend.Model.Order;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {

    @EntityGraph(attributePaths = {"ingredients","product"})
    List<Order> findAll();

    @EntityGraph(attributePaths = {"ingredients","product"})
    Optional<Order> findOrderById(UUID id);

}