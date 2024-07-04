package org.proleesh.gabbi.repository;

import org.proleesh.gabbi.dto.GoodsSearchDTO;
import org.proleesh.gabbi.dto.MainGoodsDTO;
import org.proleesh.gabbi.entity.Goods;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GoodsRepositoryCustom {
    Page<Goods> getAdminGoodsPage(GoodsSearchDTO goodsSearchDTO, Pageable pageable);

    Page<MainGoodsDTO> getMainGoodsPage(GoodsSearchDTO goodsSearchDTO, Pageable pageable);

}
