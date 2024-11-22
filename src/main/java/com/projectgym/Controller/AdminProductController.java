package com.projectgym.Controller;

import com.projectgym.Entity.Product;
import com.projectgym.Entity.User;
import com.projectgym.dto.ProductDTO;
import com.projectgym.dto.UserDTO;
import com.projectgym.service.ProductService;
import com.projectgym.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/admin/home/products")
public class AdminProductController {
    @Autowired
    private ProductService productService;

    // Lấy danh sách tất cả sản phẩm
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProduct() {
        List<ProductDTO> products = productService.getAllProduct();
        return ResponseEntity.ok(products);
    }


    // Lấy thông tin tài khoản theo ID
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        ProductDTO products = productService.getProductById(id);
        return ResponseEntity.ok(products);
    }

    // Tạo mới tài khoản
    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@RequestBody Product product) {
        return ResponseEntity.ok(productService.createProduct(product));
    }

    // Cập nhật thông tin tài khoản
    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        try {
            ProductDTO updatedProductDTO = productService.updateProduct(id, product);
            return new ResponseEntity<>(updatedProductDTO, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Xóa tài khoản
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    // Lấy danh sách sản phẩm sắp hết hàng
    @GetMapping("/low-stock")
    public ResponseEntity<List<ProductDTO>> getLowStockProducts(@RequestParam int threshold) {
        return ResponseEntity.ok(productService.getLowStockProducts(threshold));
    }
}