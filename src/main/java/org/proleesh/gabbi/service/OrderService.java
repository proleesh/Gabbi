package org.proleesh.gabbi.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.proleesh.gabbi.dto.OrderDTO;
import org.proleesh.gabbi.entity.Goods;
import org.proleesh.gabbi.entity.Member;
import org.proleesh.gabbi.entity.Order;
import org.proleesh.gabbi.entity.OrderGoods;
import org.proleesh.gabbi.repository.GoodsRepository;
import org.proleesh.gabbi.repository.MemberRepository;
import org.proleesh.gabbi.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
    private final GoodsRepository goodsRepository;
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;

    public Long order(OrderDTO orderDTO, String email){
        Goods goods = goodsRepository.findById(orderDTO.getGoodsId())
                .orElseThrow(EntityNotFoundException::new);
        Member member = memberRepository.findByEmail(email);

        List<OrderGoods> orderGoodsList = new ArrayList<>();
        OrderGoods orderGoods = OrderGoods.createOrderGoods(goods, orderDTO.getCount());
        orderGoodsList.add(orderGoods);

        Order order = Order.createOrder(member, orderGoodsList);
        orderRepository.save(order);

        return order.getId();
    }
}
