package org.proleesh.gabbi.repository;

import org.proleesh.gabbi.entity.OrderGoods;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderGoodsRepository extends JpaRepository<OrderGoods, Long> {
}
