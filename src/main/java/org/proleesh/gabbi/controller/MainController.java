package org.proleesh.gabbi.controller;

import lombok.RequiredArgsConstructor;
import org.proleesh.gabbi.dto.GoodsSearchDTO;
import org.proleesh.gabbi.dto.MainGoodsDTO;
import org.proleesh.gabbi.service.GoodsService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;
/**
 * @author sung-hyuklee
 * date: 2024.6.24
 * Main Controller
 */
@Controller
@RequiredArgsConstructor
public class MainController {
    private final GoodsService goodsService;


    @GetMapping("/")
    public String index(GoodsSearchDTO goodsSearchDTO, Optional<Integer> page, Model model) {
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 6);
        Page<MainGoodsDTO> goods = goodsService.getMainGoodsPage(goodsSearchDTO, pageable);
        model.addAttribute("goodsAll", goods);
        model.addAttribute("goodsSearchDTO", goodsSearchDTO);
        model.addAttribute("maxPage", 5);
        return "index";
    }
}
