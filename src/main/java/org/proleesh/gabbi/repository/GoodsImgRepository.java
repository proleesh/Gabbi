package org.proleesh.gabbi.repository;

import org.proleesh.gabbi.entity.GoodsImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GoodsImgRepository extends JpaRepository<GoodsImg, Long> {
    List<GoodsImg> findByGoodsIdOrderByIdAsc(Long goodsId);
}
