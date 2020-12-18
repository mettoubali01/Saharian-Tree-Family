package com.example.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AboutController {

    @RequestMapping("/history")
    public String getHome(Model model){
        model.addAttribute("page_title", "History");
        return "about";
    }
}
