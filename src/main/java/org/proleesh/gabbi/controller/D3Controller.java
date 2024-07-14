package org.proleesh.gabbi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
/**
 * @author sung-hyuklee
 * date: 2024.7.11
 * D3 Controller
 */
@Controller
public class D3Controller {

    @GetMapping("/d3view")
    public String showD3View(){
        return "/d3view/data";
    }

}
