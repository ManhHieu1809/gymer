package com.projectgym.repository;

import com.projectgym.Entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT u FROM Product u " +
            "WHERE (:productName IS NULL OR LOWER(u.productName) LIKE LOWER(CONCAT('%', :productName, '%'))) " )

    List<Product> searchByProductName(@Param("productName") String productName);
    List<Product> findByPriceBetween(double minPrice, double maxPrice); // Tìm kiếm sản phẩm theo khoảng giá
    Page<Product> findAll(Pageable pageable);
}
