package org.proleesh.gabbi.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MainGoodsDTO {
    private Long id;
    private String goodsName;
    private String goodsDetail;
    private String imageUrl;
    private Integer price;

    @QueryProjection
    public MainGoodsDTO(Long id, String goodsName, String goodsDetail, String imageUrl, Integer price) {
        this.id = id;
        this.goodsName = goodsName;
        this.goodsDetail = goodsDetail;
        this.imageUrl = imageUrl;
        this.price = price;
    }
}
