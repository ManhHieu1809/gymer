package com.projectgym.Controller;

import com.projectgym.dto.ProductDTO;
import com.projectgym.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/gym/home/users")
public class HomeController {

    @Autowired
    private ProductService productService;

    // Lấy danh sách tất cả sản phẩm
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProduct() {
        List<ProductDTO> products = productService.getAllProduct();
        return ResponseEntity.ok(products);
    }

    // Tìm kiếm sản phẩm theo khoảng giá
    @GetMapping("/price-range")
    public ResponseEntity<List<ProductDTO>> searchProductsByPriceRange(@RequestParam double minPrice, @RequestParam double maxPrice) {
        return ResponseEntity.ok(productService.searchProductsByPriceRange(minPrice, maxPrice));
    }

}
