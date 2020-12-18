package com.example.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminController {

    @RequestMapping("/admin")
    public String getAminsPage(Model model){
        model.addAttribute("page_title", "Admin Tool");

        return "home_admin";
    }
}
