package org.proleesh.gabbi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GoodsController {
    @GetMapping("/admin/goods/new")
    public String goodsForm(){
        return "goods/goodsForm";
    }
}
