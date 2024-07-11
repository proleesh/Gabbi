package org.proleesh.gabbi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartDetailDTO {
    private Long cartItemId;
    private String goodsName;
    private int price;
    private int count;
    private String imgUrl;

    public CartDetailDTO(Long cartItemId, String goodsName, int price, int count, String imgUrl) {
        this.cartItemId = cartItemId;
        this.goodsName = goodsName;
        this.price = price;
        this.count = count;
        this.imgUrl = imgUrl;
    }
}
