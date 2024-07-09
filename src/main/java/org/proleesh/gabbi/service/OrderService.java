package org.proleesh.gabbi.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.proleesh.gabbi.dto.OrderDTO;
import org.proleesh.gabbi.dto.OrderGoodsDTO;
import org.proleesh.gabbi.dto.OrderHistoryDTO;
import org.proleesh.gabbi.entity.*;
import org.proleesh.gabbi.repository.GoodsImgRepository;
import org.proleesh.gabbi.repository.GoodsRepository;
import org.proleesh.gabbi.repository.MemberRepository;
import org.proleesh.gabbi.repository.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
    private final GoodsRepository goodsRepository;
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final GoodsImgRepository goodsImgRepository;

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

    @Transactional(readOnly = true)
    public Page<OrderHistoryDTO> getOrderList(String email, Pageable pageable){
        List<Order> orders = orderRepository.findOrders(email, pageable);
        Long totalCount = orderRepository.countOrder(email);

        List<OrderHistoryDTO> orderHistoryDTOs = new ArrayList<>();

        for(Order order : orders){
            OrderHistoryDTO orderHistoryDTO = new OrderHistoryDTO(order);
            List<OrderGoods> orderGoodsList = order.getOrderGoods();
            for(OrderGoods orderGoods : orderGoodsList){
                GoodsImg goodsImg = goodsImgRepository
                        .findByGoodsIdAndRepImgYn(orderGoods.getGoods().getId(), "Y");
                OrderGoodsDTO orderGoodsDTO = new OrderGoodsDTO(orderGoods, goodsImg.getImgUrl());
                orderHistoryDTO.addOrderGoodsDTO(orderGoodsDTO);
            }
            orderHistoryDTOs.add(orderHistoryDTO);
        }
        return new PageImpl<>(orderHistoryDTOs, pageable, totalCount);
    }

    @Transactional(readOnly = true)
    public boolean validateOrder(Long orderId, String email){
        Member curMember = memberRepository.findByEmail(email);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(EntityNotFoundException::new);
        Member savedMember = order.getMember();

        if(!StringUtils.equals(curMember.getEmail(), savedMember.getEmail())){
            return false;
        }

        return true;
    }
    public void cancelOrder(Long orderId){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(EntityNotFoundException::new);
        order.cancelOrder();
    }
}
