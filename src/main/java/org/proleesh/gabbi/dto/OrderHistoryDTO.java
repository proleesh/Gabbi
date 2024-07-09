package org.proleesh.gabbi.dto;

import lombok.Getter;
import lombok.Setter;
import org.proleesh.gabbi.constant.OrderStatus;
import org.proleesh.gabbi.entity.Order;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OrderHistoryDTO {
    private Long orderId;

    private String orderDate;

    private OrderStatus orderStatus;

    private List<OrderGoodsDTO> orderGoodsDTOList = new ArrayList<>();


    public OrderHistoryDTO(Order order) {
        this.orderId = order.getId();
        this.orderDate = order.getOrderDate()
                .format(DateTimeFormatter.ofPattern("yyyy년MM월dd일 HH시mm분"));
        this.orderStatus = order.getOrderStatus();
    }
    public void addOrderGoodsDTO(OrderGoodsDTO orderGoodsDTO) {
        orderGoodsDTOList.add(orderGoodsDTO);
    }


}
