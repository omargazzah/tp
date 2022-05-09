package com.sip.tp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {



    @RequestMapping("/home")
    public String home() {
        return "home/home";
    }


}
