package org.proleesh.gabbi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class D3Controller {

    @GetMapping("/d3view")
    public String showD3View(Model model){
        return "/d3view/data";
    }
}
