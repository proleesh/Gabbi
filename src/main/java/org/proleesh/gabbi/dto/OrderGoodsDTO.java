package org.proleesh.gabbi.dto;

import lombok.Getter;
import lombok.Setter;
import org.proleesh.gabbi.entity.OrderGoods;

@Getter
@Setter
public class OrderGoodsDTO {

    private String goodsName;

    private int count;

    private int orderPrice;

    private String imgUrl;

    public OrderGoodsDTO(OrderGoods orderGoods, String imgUrl){
        this.goodsName = orderGoods.getGoods().getGoodsName();
        this.count = orderGoods.getCount();
        this.orderPrice = orderGoods.getOrderPrice();
        this.imgUrl = imgUrl;

    }
}
