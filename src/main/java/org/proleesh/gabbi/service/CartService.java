package org.proleesh.gabbi.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.proleesh.gabbi.dto.CartDetailDTO;
import org.proleesh.gabbi.dto.CartGoodsDTO;
import org.proleesh.gabbi.dto.CartOrderDTO;
import org.proleesh.gabbi.dto.OrderDTO;
import org.proleesh.gabbi.entity.Cart;
import org.proleesh.gabbi.entity.CartItem;
import org.proleesh.gabbi.entity.Goods;
import org.proleesh.gabbi.entity.Member;
import org.proleesh.gabbi.repository.CartItemRepository;
import org.proleesh.gabbi.repository.CartRepository;
import org.proleesh.gabbi.repository.GoodsRepository;
import org.proleesh.gabbi.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {
    private final GoodsRepository goodsRepository;
    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderService orderService;
    public Long addCart(CartGoodsDTO cartGoodsDTO, String email) {
        Goods goods = goodsRepository.findById(cartGoodsDTO.getGoodsId())
                .orElseThrow(EntityNotFoundException::new);
        Member member = memberRepository.findByEmail(email);

        Cart cart = cartRepository.findByMemberId(member.getId());
        if(cart == null){
            cart = Cart.createCart(member);
            cartRepository.save(cart);
        }
        CartItem savedCartItem =
                cartItemRepository.findByCartIdAndGoodsId(cart.getId(),
                        goods.getId());
        if(savedCartItem != null){
            savedCartItem.addCount(cartGoodsDTO.getCount());
            return savedCartItem.getId();
        }else{
            CartItem cartItem = CartItem.createCartItem(cart,goods,
                    cartGoodsDTO.getCount());
            cartItemRepository.save(cartItem);
            return cartItem.getId();
        }

    }

    @Transactional(readOnly = true)
    public List<CartDetailDTO> getCartList(String email){
        List<CartDetailDTO> cartDetailDTOList = new ArrayList<>();
        Member member = memberRepository.findByEmail(email);
        Cart cart = cartRepository.findByMemberId(member.getId());
        if(cart == null){
            return cartDetailDTOList;
        }
        cartDetailDTOList = cartItemRepository.findCartDetailsByCartId(cart.getId());
        return cartDetailDTOList;
    }

    @Transactional(readOnly = true)
    public boolean validateCartItem(Long cartItemId, String email){
        Member curMember = memberRepository.findByEmail(email);
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(EntityNotFoundException::new);
        Member savedmember = cartItem.getCart().getMember();

        if(!StringUtils.equals(curMember.getEmail(), savedmember.getEmail())){
            return false;
        }
        return true;

    }
    public void updateCartItemCount(Long cartItemId, int count){
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(EntityNotFoundException::new);
        cartItem.updateCount(count);
    }

    public void deleteCartItem(Long cartItemId){
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(EntityNotFoundException::new);

        cartItemRepository.delete(cartItem);
    }

    public Long orderCartItem(List<CartOrderDTO> cartOrderDTOList, String email){
        List<OrderDTO> orderDTOList = new ArrayList<>();
        for(CartOrderDTO cartOrderDTO : cartOrderDTOList){
            CartItem cartItem = cartItemRepository
                    .findById(cartOrderDTO.getCartItemId())
                    .orElseThrow(EntityNotFoundException::new);

            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setGoodsId(cartItem.getGoods().getId());
            orderDTO.setCount(cartItem.getCount());
            orderDTOList.add(orderDTO);
        }
        Long orderId = orderService.orders(orderDTOList, email);

        for(CartOrderDTO cartOrderDTO : cartOrderDTOList){
            CartItem cartItem = cartItemRepository
                    .findById(cartOrderDTO.getCartItemId())
                    .orElseThrow(EntityNotFoundException::new);
            cartItemRepository.delete(cartItem);
        }
        return orderId;
    }
}
