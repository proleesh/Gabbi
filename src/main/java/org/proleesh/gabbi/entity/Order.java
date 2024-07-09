package org.proleesh.gabbi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.proleesh.gabbi.constant.OrderStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order extends BaseEntity{
    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private LocalDateTime orderDate; // 주문일

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus; // 주문상태

    @OneToMany(mappedBy = "order",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<OrderGoods> orderGoods = new ArrayList<>();

    public void addOrderGoods(OrderGoods orderGoods) {
        this.orderGoods.add(orderGoods);
        orderGoods.setOrder(this);
    }

    public static Order createOrder(Member member,
                                    List<OrderGoods> orderGoodsList){
        Order order = new Order();
        order.setMember(member);
        for(OrderGoods orderGoods : orderGoodsList){
            order.addOrderGoods(orderGoods);
        }
        order.setOrderStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    public int getTotalPrice(){
        int totalPrice = 0;
        for(OrderGoods orderGoods : this.orderGoods){
            totalPrice += orderGoods.getTotalPrice();
        }
        return totalPrice;
    }

    public void cancelOrder(){
        this.orderStatus = OrderStatus.CANCEL;

        for(OrderGoods orderGoods : this.orderGoods){
            orderGoods.cancel();
        }
    }
}
