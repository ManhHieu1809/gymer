package com.projectgym.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContentController {

    @GetMapping("/req/login")
    public String login(){
        return "login";
    }

    @GetMapping("/req/signup")
    public String signup(){
        return "signup";
    }
    @GetMapping("/index")
    public String home(){
        return "index";
    }

    @GetMapping("/admin/home")
    public String adminHome(){
        return "admin/homeAdmin";
    }

    @GetMapping("/trainer/home")
    public String trainerHome(){
        return "trainer/homeTrainer";
    }

    @GetMapping("/customer/home")
    public String customerHome(){
        return "customer/homeCustomer";
    }

}
