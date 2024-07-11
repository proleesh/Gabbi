package org.proleesh.gabbi.controller;

import com.oracle.svm.core.annotate.Delete;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.proleesh.gabbi.dto.CartDetailDTO;
import org.proleesh.gabbi.dto.CartGoodsDTO;
import org.proleesh.gabbi.dto.CartOrderDTO;
import org.proleesh.gabbi.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping(value = "/cart")
    public @ResponseBody ResponseEntity order(@RequestBody @Valid CartGoodsDTO cartGoodsDTO,
                                              BindingResult bindingResult,
                                              Principal principal) {
        if (principal == null) {
            return new ResponseEntity<>("User is not authenticated", HttpStatus.UNAUTHORIZED);
        }
        if(bindingResult.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for(FieldError fieldError : fieldErrors) {
                sb.append(fieldError.getDefaultMessage());
            }
            return new ResponseEntity<>(sb.toString(), HttpStatus.BAD_REQUEST);
        }
        String email = principal.getName();
        Long cartItemId;
        try{
            cartItemId = cartService.addCart(cartGoodsDTO, email);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(cartItemId, HttpStatus.OK);
    }

    @GetMapping("/cart")
    public String orderHistory(Principal principal, Model model) {

        List<CartDetailDTO> cartDetailDTOList = cartService.getCartList(principal.getName());
        model.addAttribute("cartItems", cartDetailDTOList);
        return "cart/cartList";
    }

    @PatchMapping("/cartItem/{cartItemId}")
    public @ResponseBody ResponseEntity updateCartItem(@PathVariable("cartItemId") Long cartItemId,
                                                       int count, Principal principal) {
        if (principal == null) {
            return new ResponseEntity<>("User is not authenticated", HttpStatus.UNAUTHORIZED);
        }
        if(count <= 0){
            return new ResponseEntity<>("최소 1개 이상 담아주세요.", HttpStatus.BAD_REQUEST);
        }else if(!cartService.validateCartItem(cartItemId, principal.getName())){
            return new ResponseEntity<>("수정 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }

        cartService.updateCartItemCount(cartItemId, count);
        return new ResponseEntity<>(cartItemId,HttpStatus.OK);
    }

    @DeleteMapping("/cartItem/{cartItemId}")
    public @ResponseBody ResponseEntity deleteCartItem(
            @PathVariable("cartItemId") Long cartItemId, Principal principal) {
        if (principal == null) {
            return new ResponseEntity<>("User is not authenticated", HttpStatus.UNAUTHORIZED);
        }
        if(!cartService.validateCartItem(cartItemId, principal.getName())){
            return new ResponseEntity<String>("수정 권한이 없습니다.",
                    HttpStatus.FORBIDDEN);
        }
        cartService.deleteCartItem(cartItemId);
        return new ResponseEntity<>(cartItemId,HttpStatus.OK);
    }

    @PostMapping("/cart/orders")
    public @ResponseBody ResponseEntity orderCartItem(@RequestBody CartOrderDTO cartOrderDTO, Principal principal) {
        if (principal == null) {
            return new ResponseEntity<>("User is not authenticated", HttpStatus.UNAUTHORIZED);
        }
        List<CartOrderDTO> cartOrderDTOList = cartOrderDTO.getCartOrderDTOList();
        if(cartOrderDTOList == null || cartOrderDTOList.size() == 0){
            return new ResponseEntity<>("주문할 상품을 선택해주세요",
                    HttpStatus.FORBIDDEN);
        }
        for(CartOrderDTO cartOrder : cartOrderDTOList){
            if(!cartService.validateCartItem(cartOrder.getCartItemId(),
                    principal.getName())){
                return new ResponseEntity<>("주문 권한이 없습니다.",
                        HttpStatus.FORBIDDEN);
            }
        }
        Long orderid = cartService.orderCartItem(cartOrderDTOList, principal.getName());
        return new ResponseEntity<>(orderid, HttpStatus.OK);
    }
}
