package org.proleesh.gabbi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class OrderGoods extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "order_goods_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goods_id")
    private Goods goods;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice; // 주문가격

    private int count; // 수량

    public static OrderGoods createOrderGoods(Goods goods, int count){
        OrderGoods orderGoods = new OrderGoods();
        orderGoods.setGoods(goods);
        orderGoods.setCount(count);
        orderGoods.setOrderPrice(goods.getGoodsPrice());
        goods.removeStock(count);
        return orderGoods;
    }
    public int getTotalPrice(){
        return orderPrice * count;
    }
    public void cancel(){
        this.getGoods().addStock(count);
    }

}
