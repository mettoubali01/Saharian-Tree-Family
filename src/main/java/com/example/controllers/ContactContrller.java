package com.example.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ContactContrller {

    @RequestMapping("/contact")
    public String getContact(Model model){
        model.addAttribute("page_title", "Contact");
        return "contact";
    }
}
