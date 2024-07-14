package org.proleesh.gabbi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.proleesh.gabbi.dto.OrderDTO;
import org.proleesh.gabbi.dto.OrderHistoryDTO;
import org.proleesh.gabbi.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

/**
 * @author sung-hyuklee
 * date: 2024.7.9
 * Order Controller
 */
@Controller
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/order")
    public @ResponseBody ResponseEntity order(@RequestBody @Valid OrderDTO orderDTO,
                                              BindingResult bindingResult,
                                              Principal principal) {
        if (principal == null) {
            return new ResponseEntity<>("로그인 후 이용해주세요", HttpStatus.UNAUTHORIZED);
        }
        if(bindingResult.hasErrors()){
            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for(FieldError fieldError : fieldErrors){
                sb.append(fieldError.getDefaultMessage());
            }
            return new ResponseEntity<>(sb.toString(),
                    HttpStatus.BAD_REQUEST);
        }
        String email = principal.getName();
        Long orderId;
        try{
            orderId = orderService.order(orderDTO, email);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),
                    HttpStatus.BAD_REQUEST) ;
        }
        return new ResponseEntity<>(orderId, HttpStatus.OK);

    }
    @GetMapping({"/orders", "/orders/{page}"})
    public String orderHistory(@PathVariable("page")Optional<Integer> page, Principal principal, Model model) {
        if (principal == null) {
            return "redirect:/members/login";
        }
        Pageable pageable = PageRequest.of(page.isPresent()?page.get():0, 4);
        Page<OrderHistoryDTO> orderHistoryDTOList = orderService.getOrderList(principal.getName(),
                pageable);
        model.addAttribute("orders", orderHistoryDTOList);
        model.addAttribute("page", pageable.getPageNumber());
        model.addAttribute("maxPage", 5);
        return "order/orderHistory";
    }

    @PostMapping("/order/{orderId}/cancel")
    public @ResponseBody ResponseEntity orderCancel(@PathVariable("orderId") Long orderId,
                                                    Principal principal) {
        if(!orderService.validateOrder(orderId, principal.getName())){
            return new ResponseEntity<>("주문 취소 권한이 없습니다.",
                    HttpStatus.FORBIDDEN);
        }

        orderService.cancelOrder(orderId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
