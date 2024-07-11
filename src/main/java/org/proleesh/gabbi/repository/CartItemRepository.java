package org.proleesh.gabbi.repository;

import org.proleesh.gabbi.dto.CartDetailDTO;
import org.proleesh.gabbi.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    CartItem findByCartIdAndGoodsId(Long cartId, Long goodsId);

    @Query("select new org.proleesh.gabbi.dto.CartDetailDTO(ci.id, i.goodsName, i.goodsPrice, ci.count, im.imgUrl) " +
    "from CartItem ci, GoodsImg im " +
    "join ci.goods i " +
    "where ci.cart.id = :cartId " +
    "and im.goods.id = ci.goods.id " +
    "and im.repImgYn = 'Y' " +
    "order by ci.registerTime desc")
    List<CartDetailDTO> findCartDetailsByCartId(Long cartId);
}
