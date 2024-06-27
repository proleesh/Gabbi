package org.proleesh.gabbi.repository;

import org.proleesh.gabbi.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
