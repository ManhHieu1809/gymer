package com.projectgym.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
public class ContentController {

    @GetMapping("/trainer/home/users/user-info")
    public ResponseEntity<Map<String, String>> getUserInfo(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        // Tạo một map chứa thông tin người dùng
        Map<String, String> response = new HashMap<>();
        response.put("username", username);

        return ResponseEntity.ok(response);
    }
    // Trang chủ Gym
    @GetMapping("/gym/home")
    public String homePage(){
        return "Home/index";
    }

    @GetMapping("gym/home/shop")
    public String shop(){
        return "Home/shopHome";
    }

    @GetMapping("gym/home/shop/product-detail")
    public String productDetail(){
        return "/Home/productDetails";
    }

    @GetMapping("gym/home/shop/cart")
    public String cart(){
        return "cart";
    }

    @GetMapping("/gym/home/workout-plan")
    public String workoutPlan1(){
        return "Home/workoutPlan";
    }



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
        return "customer/index";
    }


    // Trang chủ Admin
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

    // Trang chủ Trainer
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

    @GetMapping("/admin/home/chat")
    public String chat(){
        return "admin/chat";
    }

    @GetMapping("customer/home/chat")
    public String chatCustomer(){
        return "customer/contact";
    }

    @GetMapping("/customer/home/useracc")
    public String useracc(){
        return "customer/account_manager/userAccMng";
    }

    @GetMapping("/customer/home/shop")
    public String shopCustomer(){
        return "customer/shop";
    }

    @GetMapping("/customer/home/shop/cart")
    public String cartCustomer(){
        return "customer/cart2";
    }



    @GetMapping("customer/home/shop/productDetail")
    public String productdetail(){
        return "/customer/productDetails";
    }

    @GetMapping("/customer/home/workout-plan")
    public String workoutPlanCustomer(){
        return "customer/WorkoutPlan";
    }

    @GetMapping("/customer/home/workout")
    public String workoutCustomer(){
        return "customer/workout1";
    }

    @GetMapping("/customer/home/nutrition-plan")
    public String nutritionPlanCustomer(){
        return "/customer/Nutrition";
    }

    @GetMapping("/customer/home/users")
    public String userInfoCustomer(){
        return "customer/userInfo";
    }

    @GetMapping("/customer/home/Progress")
    public String progressCustomer(){
        return "customer/Progress";
    }

    @GetMapping("/customer/home/users/update")
    public String updateUserInfo(){
        return "customer/update-info";
    }

    @GetMapping("/customer/home/users/notification")
    public String notificationCustomer(){
        return "customer/notification";
    }
}
