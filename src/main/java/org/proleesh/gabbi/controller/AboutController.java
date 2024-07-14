package org.proleesh.gabbi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
/**
 * @author sung-hyuklee
 * date: 2024.6.26
 * About Controller
 */
@Controller
public class AboutController {
    @GetMapping("/about")
    public String about() {
        return "others/about";
    }
}
