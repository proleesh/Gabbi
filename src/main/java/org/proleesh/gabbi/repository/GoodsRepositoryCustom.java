package org.proleesh.gabbi.repository;

import org.proleesh.gabbi.dto.GoodsSearchDTO;
import org.proleesh.gabbi.entity.Goods;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GoodsRepositoryCustom {
    Page<Goods> getAdminGoodsPage(GoodsSearchDTO goodsSearchDTO, Pageable pageable);
}
