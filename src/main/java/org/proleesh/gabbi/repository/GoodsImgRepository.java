package org.proleesh.gabbi.repository;

import org.proleesh.gabbi.entity.GoodsImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface GoodsImgRepository extends JpaRepository<GoodsImg, Long> {
    List<GoodsImg> findByGoodsIdOrderByIdAsc(Long goodsId);
    @Modifying
    @Transactional
    @Query("DELETE FROM GoodsImg gi WHERE gi.goods.id = :goodsId")
    void deleteByGoodsId(@Param("goodsId") Long goodsId);
}
