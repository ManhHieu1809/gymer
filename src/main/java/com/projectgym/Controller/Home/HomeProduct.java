package com.projectgym.Controller.Home;

import com.projectgym.dto.OrdersDTO;
import com.projectgym.dto.ProductDTO;
import com.projectgym.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gym/home/products")
public class HomeProduct {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getProducts() {
        List<ProductDTO> products = productService.getAllProduct();
        return ResponseEntity.ok(products);
    }

    // Lấy thông tin tài khoản theo ID
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        ProductDTO products = productService.getProductById(id);
        return ResponseEntity.ok(products);
    }

}
