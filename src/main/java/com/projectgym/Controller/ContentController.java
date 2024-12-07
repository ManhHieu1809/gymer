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
        return "admin/index";
    }

    @GetMapping("/trainer/home")
    public String trainerHome(){
        return "trainer/index";
    }

    @GetMapping("/customer/home")
    public String customerHome(){
        return "customer/homeCustomer";
    }

    @GetMapping("/admin/home/user")
    public String home1(){
        return "admin/accMng";
    }

    @GetMapping("/admin/home/user/user-add")
    public String user_add(){
        return "admin/add-user";
    }

    @GetMapping("/admin/home/user/user-edit/{id}")
    public String user_edit(){
        return "admin/edit-user";
    }

    @GetMapping("/admin/home/product")
    public String product(){
        return "admin/goodsMng";
    }

    @GetMapping("/admin/home/product/product-add")
    public String product_add(){
        return "admin/add-goods";
    }

    @GetMapping("/admin/home/product/product-edit/{id}")
    public String product_edit(){
        return "admin/edit-goods";
    }

    @GetMapping("/admin/home/order")
    public String order(){
        return "admin/orderMng";
    }

    @GetMapping("/admin/home/notification")
    public String notification(){
        return "admin/notification";
    }

    @GetMapping("/trainer/home/nutrition-plan")
    public String nutritionPlan(){
        return "trainer/nutritionPlan";
    }

    @GetMapping("/trainer/home/topic")
    public String topic(){
        return "trainer/topic";
    }

    @GetMapping("/trainer/home/workout-plan")
    public String workoutPlan(){
        return "trainer/workoutPlan";
    }

    @GetMapping("/trainer/home/Progress")
    public String progress(){
        return "trainer/progress";
    }
}
