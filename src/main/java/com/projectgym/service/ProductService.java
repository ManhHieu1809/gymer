package com.projectgym.service;

import com.projectgym.Entity.Product;
import com.projectgym.Entity.User;
import com.projectgym.dto.ProductDTO;
import com.projectgym.dto.UserDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    List<ProductDTO> getAllProduct();

    ProductDTO getProductById(Long id);

    ProductDTO createProduct(Product product);

    ProductDTO updateProduct(Long id, Product product);

    void deleteProduct(Long id);

    List<ProductDTO> getLowStockProducts(int threshold); // lấy sản phầm có số lượng gần hết

    List<ProductDTO> searchByProductName(String productName);

    List<ProductDTO> searchProductsByPriceRange(double minPrice, double maxPrice); // Tìm kiếm sản phẩm theo khoảng giá

    Page<ProductDTO> getAllProduct(int page, int size);
}
