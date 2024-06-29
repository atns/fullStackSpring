package com.atns.fullstackatns.home;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public String HomePage(){
        return "home";
    }



    @GetMapping("/login")
    public String LoginPage(){
        return "login";
    }

    @GetMapping("/error")
    public String ErrorPage(){
        return "error";
    }
}
